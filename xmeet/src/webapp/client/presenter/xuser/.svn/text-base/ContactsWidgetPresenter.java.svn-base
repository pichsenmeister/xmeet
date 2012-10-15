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
		void setListenTo(List<XUser> user);

		void setListener(List<XUser> user);

		void setRequests(List<XUser> user);

		void setCallbackUser(ITypedCallback<XUser> callback);

		void setCallbackSelect(ITypedCallback<EnumWidget> callback);

		void setCallbackRequest(ICallbackRequest callback);

		void setWidget(EnumWidget value);

		void setRequestEnabled(boolean enable);
	}

	public interface ICallbackRequest {
		void execute(XUser user, XContactStatus status);
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
				view_.setWidget(type);
			}
		});

		view_.setCallbackRequest(new ICallbackRequest() {
			@Override
			public void execute(XUser user, XContactStatus status) {
				saveUser(user, status);
			}
		});
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		user_ = pGatekeeper_.get().getLoggedInUser();

		if (listener_ == null) {
			loadListener();
		}
		if (listenTo_ == null) {
			loadListenTos();
		}
		if (request_ == null) {
			loadRequests();
		}
	}

	private void loadListener() {
		rpcUser_.loadListener(user_, XContactStatus.PERMIT, new AsyncCallback<List<XUser>>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("fail load contacts");
			}

			@Override
			public void onSuccess(List<XUser> result) {
				listener_ = result;
				view_.setListener(result);
			}
		});
	}

	private void loadListenTos() {
		rpcUser_.loadListenTos(user_, XContactStatus.PERMIT, new AsyncCallback<List<XUser>>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("fail load contacts");
			}

			@Override
			public void onSuccess(List<XUser> result) {
				listenTo_ = result;
				view_.setListenTo(result);
			}
		});
	}

	private void loadRequests() {
		rpcUser_.loadListener(user_, XContactStatus.REQUEST, new AsyncCallback<List<XUser>>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("fail load contacts");
			}

			@Override
			public void onSuccess(List<XUser> result) {
				request_ = result;
				view_.setRequests(result);
			}
		});
	}

	/**
	 * saves user request status
	 * 
	 * @param listener
	 *            the user who requested
	 * @param status
	 *            the XContactStatus
	 */
	private void saveUser(XUser listener, XContactStatus status) {
		rpcUser_.verifyContactRequest(listener, user_, status, new AsyncCallback<XUser>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail");
			}

			@Override
			public void onSuccess(XUser result) {
				loadListenTos();
				loadRequests();
			}
		});
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
