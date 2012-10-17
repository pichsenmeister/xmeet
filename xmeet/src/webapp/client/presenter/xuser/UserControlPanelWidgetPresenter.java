package webapp.client.presenter.xuser;

import java.util.HashMap;

import webapp.client.callback.ICallback;
import webapp.client.callback.ITypedCallback;
import webapp.client.event.RevealPlaceEvent;
import webapp.client.event.XNameToken;
import webapp.client.event.xuser.IXUserLoginEventHandler;
import webapp.client.event.xuser.XUserLoginEvent;
import webapp.client.event.xuser.XUserLogoutEvent;
import webapp.client.rpc.xmedia.RPCMediaAsync;
import webapp.client.rpc.xuser.RPCUserAsync;
import webapp.model.XUser;
import webapp.model.enums.MenuIcon;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * the presenter for XUser's control panel
 * 
 * @author David Pichsenmeister
 */
public class UserControlPanelWidgetPresenter extends
		PresenterWidget<UserControlPanelWidgetPresenter.IView> {

	/**
	 * the interface for the user's control panel view
	 * 
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {
		void setUser(XUser user);

		void setActiveIcon(MenuIcon icon);

		void setCallbackNews(ICallback callback);

		void setCallbackContacts(ICallback callback);

		void setCallbackCreate(ICallback callback);

		void setCallbackSearch(ITypedCallback<String> callback);

		void setCallbackUser(ICallback callback);

		void setCallbackSettings(ICallback callback);

		void setCallbackLogout(ICallback callback);
	}

	private final RPCMediaAsync rpcMedia_;
	private final RPCUserAsync rpcUser_;
	private final EventBus eventBus_;
	private final IView view_;
	private XUser parent_;
	private XUser user_;

	@Inject
	private Provider<LoggedInGatekeeper> pGatekeeper_;

	/**
	 * the constructor
	 * 
	 * @param rpcMedia
	 *            rpc service for database connection
	 * @param rpcUser
	 *            rpc service for database connection
	 * @param gatekeeper
	 *            the gatekeeper for logged user
	 * @param eventBus
	 *            the GWT EventBus
	 * @param view
	 *            the view
	 */
	@Inject
	public UserControlPanelWidgetPresenter(RPCMediaAsync rpcMedia,
			RPCUserAsync rpcUser, EventBus eventBus, IView view,
			LoggedInGatekeeper gatekeeper) {
		super(eventBus, view);

		rpcMedia_ = rpcMedia;
		rpcUser_ = rpcUser;
		eventBus_ = eventBus;
		view_ = view;

		parent_ = gatekeeper.getLoggedInUser();
		user_ = parent_;
	}

	@Override
	protected void onBind() {
		super.onBind();

		view_.setUser(user_);

		registerHandler(eventBus_.addHandler(XUserLoginEvent.TYPE,
				new IXUserLoginEventHandler() {

					@Override
					public void onXUserLogin(XUserLoginEvent event) {
						parent_ = pGatekeeper_.get().getLoggedInUser();
						user_ = parent_;
						view_.setUser(user_);
					}
				}));

		view_.setCallbackNews(new ICallback() {
			@Override
			public void execute() {
				eventBus_.fireEvent(new RevealPlaceEvent(XNameToken.NEWS));
			}
		});

		view_.setCallbackContacts(new ICallback() {

			@Override
			public void execute() {
				eventBus_.fireEvent(new RevealPlaceEvent(XNameToken.CONTACTS));
			}
		});

		view_.setCallbackCreate(new ICallback() {
			@Override
			public void execute() {
				eventBus_.fireEvent(new RevealPlaceEvent(XNameToken.CREATE));
			}
		});

		view_.setCallbackSearch(new ITypedCallback<String>() {
			@Override
			public void execute(String search) {
				if ((search != null) && !search.isEmpty()) {
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("q", search);
					eventBus_.fireEvent(new RevealPlaceEvent(XNameToken.SEARCH,
							params));
				}
			}
		});

		view_.setCallbackUser(new ICallback() {
			@Override
			public void execute() {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("id", user_.getUserID().toString());
				eventBus_.fireEvent(new RevealPlaceEvent(XNameToken.USER,
						params));
			}
		});

		view_.setCallbackSettings(new ICallback() {
			@Override
			public void execute() {
				eventBus_.fireEvent(new RevealPlaceEvent(XNameToken.SETTINGS));
			}
		});

		view_.setCallbackLogout(new ICallback() {
			@Override
			public void execute() {
				eventBus_.fireEvent(new XUserLogoutEvent());
			}
		});
	}

	public void setActiveIcon(MenuIcon icon) {
		view_.setActiveIcon(icon);
	}
}
