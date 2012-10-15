package webapp.client.presenter.xuser;

import java.util.HashMap;
import java.util.List;

import webapp.client.callback.ITypedCallback;
import webapp.client.event.RevealPlaceEvent;
import webapp.client.event.XNameToken;
import webapp.client.rpc.xuser.RPCUserAsync;
import webapp.model.XUser;
import webapp.model.enums.EnumWidget;
import webapp.model.enums.XContactStatus;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * the presenter for user's contacts
 * 
 * @author David Pichsenmeister
 */
public class ContactsWidgetPresenter extends PresenterWidget<ContactsWidgetPresenter.IView> {

	/**
	 * the interface for the user's contacts view
	 * 
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {
		void setCallbackUser(ITypedCallback<XUser> callback);

		void setCallbackSelect(ITypedCallback<EnumWidget> callback);

		void setCallbackAdd(ITypedCallback<XUser> callback);

		void setCallbackRemove(ITypedCallback<XUser> callback);

		void setWidget(EnumWidget value, List<XUser> list);

		void setRequestEnabled(boolean enable);
	}

	private final RPCUserAsync rpcUser_;
	private final EventBus eventBus_;
	private final IView view_;
	private XUser user_;
	private List<XUser> listener_;
	private List<XUser> listenTo_;
	private List<XUser> request_;

	@Inject
	private Provider<LoggedInGatekeeper> pGatekeeper_;

	/**
	 * the constructor
	 * 
	 * @param rpcService
	 *            rpcService for database connection
	 * @param eventBus
	 *            the GWT EventBus
	 * @param view
	 *            the view
	 */
	@Inject
	public ContactsWidgetPresenter(RPCUserAsync rpcService, EventBus eventBus, IView view) {
		super(eventBus, view);

		rpcUser_ = rpcService;
		eventBus_ = eventBus;
		view_ = view;
	}

	@Override
	protected void onBind() {
		super.onBind();

		view_.setCallbackUser(new ITypedCallback<XUser>() {
			@Override
			public void execute(XUser user) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("id", user.getUserID().toString());
				eventBus_.fireEvent(new RevealPlaceEvent(XNameToken.USER, params));
			}
		});

		view_.setCallbackSelect(new ITypedCallback<EnumWidget>() {

			@Override
			public void execute(EnumWidget type) {
				setWidget(type);
			}
		});

		view_.setCallbackAdd(new ITypedCallback<XUser>() {

			@Override
			public void execute(XUser user) {
				verifyContact(user);
			}
		});

		view_.setCallbackRemove(new ITypedCallback<XUser>() {

			@Override
			public void execute(XUser user) {
				removeContact(user);
			}
		});
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		user_ = pGatekeeper_.get().getLoggedInUser();

		setWidget(EnumWidget.SUBSCRIBER);
	}

	private void loadListener(final boolean showWidget) {
		rpcUser_.loadListener(user_, XContactStatus.PERMIT, new AsyncCallback<List<XUser>>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("fail load contacts");
			}

			@Override
			public void onSuccess(List<XUser> result) {
				listener_ = result;
				if (showWidget) {
					view_.setWidget(EnumWidget.SUBSCRIBER, listener_);
				}
			}
		});
	}

	private void loadListenTos(final boolean showWidget) {
		rpcUser_.loadListenTos(user_, XContactStatus.PERMIT, new AsyncCallback<List<XUser>>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("fail load contacts");
			}

			@Override
			public void onSuccess(List<XUser> result) {
				listenTo_ = result;
				if (showWidget) {
					view_.setWidget(EnumWidget.SUBSCRIBEDTO, listenTo_);
				}
			}
		});
	}

	private void loadRequests(final boolean showWidget) {
		rpcUser_.loadListener(user_, XContactStatus.REQUEST, new AsyncCallback<List<XUser>>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("fail load contacts");
			}

			@Override
			public void onSuccess(List<XUser> result) {
				request_ = result;
				if (showWidget) {
					view_.setWidget(EnumWidget.REQUEST, request_);
				}
			}
		});
	}

	private void verifyContact(XUser listener) {
		rpcUser_.verifyContactRequest(listener, user_, new AsyncCallback<XUser>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail");
			}

			@Override
			public void onSuccess(XUser result) {
				// loadListenTos(false);
				// loadRequests(false);
				Window.alert("success");
			}
		});
	}

	private void removeContact(XUser listener) {
		rpcUser_.removeContact(listener, user_, new AsyncCallback<XUser>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail");
			}

			@Override
			public void onSuccess(XUser result) {
				Window.alert("success");
				// loadListener(false);
				// loadListenTos(false);
				// loadRequests(false);
			}
		});
	}

	private void setWidget(EnumWidget widget) {
		switch (widget) {
		case SUBSCRIBER:
			if (listener_ == null) {
				loadListener(true);
			} else {
				view_.setWidget(widget, listener_);
			}
			break;
		case SUBSCRIBEDTO:
			if (listenTo_ == null) {
				loadListenTos(true);
			} else {
				view_.setWidget(widget, listenTo_);
			}
			break;
		case REQUEST:
			if (request_ == null) {
				loadRequests(true);
			} else {
				view_.setWidget(widget, request_);
			}
			break;
		}
	}
	// /**
	// * set user whos contacts are loaded
	// *
	// * @param user
	// * the user whos contacts are loaded
	// */
	// public void setXUser(XUser user) {
	// user_ = user;
	// loggedUser_ = pGatekeeper_.get().getLoggedInUser();
	// if ((loggedUser_ != null) &&
	// loggedUser_.getUserID().equals(user_.getUserID())) {
	// user_ = loggedUser_;
	// if (!user_.getSettings().isPublicAccount()) {
	// view_.setRequestEnabled(true);
	// }
	// }
	// }
}
