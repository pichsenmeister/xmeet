package webapp.client.presenter.xuser;

import java.util.HashMap;
import java.util.List;

import webapp.client.callback.ITypedCallback;
import webapp.client.event.RevealPlaceEvent;
import webapp.client.event.XNameToken;
import webapp.client.rpc.xuser.RPCUserAsync;
import webapp.model.XUser;
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
public class ContactsPresenterWidget extends PresenterWidget<ContactsPresenterWidget.IView> {

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

		void setCallbackRequest(ICallbackRequest callback);

		void setRequestEnabled(boolean enable);
	}

	public interface ICallbackRequest {
		void execute(XUser user, XContactStatus status);
	}

	private final RPCUserAsync rpcUser_;
	private final EventBus eventBus_;
	private final IView view_;
	private XUser loggedUser_;
	private XUser user_;

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
	public ContactsPresenterWidget(RPCUserAsync rpcService, EventBus eventBus, IView view) {
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

		loadContacts();
	}

	/**
	 * reloads the contacts of current user
	 */
	private void loadContacts() {
		rpcUser_.loadListener(user_, new AsyncCallback<XUser>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("fail load contacts");
			}

			@Override
			public void onSuccess(XUser result) {
				view_.setListener(result.getListenerList());
			}
		});

		rpcUser_.loadListenTos(user_, new AsyncCallback<XUser>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("fail load contacts");
			}

			@Override
			public void onSuccess(XUser result) {
				view_.setListenTo(result.getListenToList());
			}
		});

		rpcUser_.loadRequestFrom(user_, new AsyncCallback<XUser>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("fail load contacts");
			}

			@Override
			public void onSuccess(XUser result) {
				view_.setRequests(result.getRequestFromList());
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
		rpcUser_.addContact(user_, listener, status, new AsyncCallback<XUser>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail");
			}

			@Override
			public void onSuccess(XUser result) {
				loadContacts();
			}
		});
	}

	/**
	 * set user whos contacts are loaded
	 * 
	 * @param user
	 *            the user whos contacts are loaded
	 */
	public void setXUser(XUser user) {
		user_ = user;
		loggedUser_ = pGatekeeper_.get().getLoggedInUser();
		if ((loggedUser_ != null) && loggedUser_.getUserID().equals(user_.getUserID())) {
			user_ = loggedUser_;
			if (!user_.getSettings().isPublicAccount()) {
				view_.setRequestEnabled(true);
			}
		}
	}
}
