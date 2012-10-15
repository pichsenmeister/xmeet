package webapp.client.presenter.xuser;

import java.util.List;

import webapp.client.event.XNameToken;
import webapp.client.presenter.content.FooterPresenterWidget;
import webapp.client.presenter.xevent.UsersEventsWidgetPresenter;
import webapp.client.rpc.xuser.RPCUserAsync;
import webapp.client.view.xuser.UserPageView;
import webapp.model.XUser;
import webapp.model.enums.XContactStatus;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.common.client.CodeSplitProvider;
import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

/**
 * the presenter for user page
 * 
 * @author David Pichsenmeister
 */
public class UserPagePresenter extends Presenter<UserPagePresenter.IView, UserPagePresenter.IProxy> {

	/**
	 * the interface for the userpage view
	 * 
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {
		void showListenButton(boolean listen, XContactStatus status);

		void setCallbackListen(ICallbackListen callback);

		void isUserLogged(boolean logged);

		void setUser(XUser user);
	}

	public interface ICallbackListen {
		void execute(HasText button, HasText msg);
	}

	@ProxyCodeSplit
	@NameToken(XNameToken.USER)
	@NoGatekeeper
	public interface IProxy extends ProxyPlace<UserPagePresenter> {

	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_CONTROL = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_MY_EVENTS = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_CONTACTS = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_LAST_LOCATIONS = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_FOOTER = new Type<RevealContentHandler<?>>();

	private final IndirectProvider<UserControlPanelWidgetPresenter> userControlPresenter_;
	private final IndirectProvider<ControlPanelPresenterWidget> controlPresenter_;
	private final IndirectProvider<UsersEventsWidgetPresenter> locationPresenter_;
	private final IndirectProvider<FooterPresenterWidget> footerPresenter_;

	private final RPCUserAsync rpcUser_;
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
	 * @param proxy
	 *            the proxy
	 * @param controlPresenter
	 *            the UserControlPresenterWidget
	 * @param eventsPresenter
	 *            the MyEventsPresenterWidget
	 * @param contactsPresenter
	 *            the ContactsPresenterWidget
	 * @param eventCreatorPresenter
	 *            the EventCreatorPresenterWidget
	 */
	@Inject
	public UserPagePresenter(RPCUserAsync rpcUser, EventBus eventBus, IView view, IProxy proxy,
			AsyncProvider<UserControlPanelWidgetPresenter> userControlPresenter,
			AsyncProvider<ControlPanelPresenterWidget> controlPresenter,
			AsyncProvider<UsersEventsWidgetPresenter> locationPresenter,
			AsyncProvider<FooterPresenterWidget> footerPresenter) {
		super(eventBus, view, proxy);

		rpcUser_ = rpcUser;
		view_ = view;

		userControlPresenter_ = new CodeSplitProvider<UserControlPanelWidgetPresenter>(
				userControlPresenter);
		controlPresenter_ = new CodeSplitProvider<ControlPanelPresenterWidget>(controlPresenter);
		locationPresenter_ = new CodeSplitProvider<UsersEventsWidgetPresenter>(locationPresenter);
		footerPresenter_ = new CodeSplitProvider<FooterPresenterWidget>(footerPresenter);
	}

	@Override
	public void onBind() {
		super.onBind();

		view_.setCallbackListen(new ICallbackListen() {

			@Override
			public void execute(HasText button, HasText msg) {
				if (button.getText().equals(UserPageView.PLUS_SIGN)) {
					saveListenTo(button, msg);
				} else {
					removeListenTo(button, msg);
				}
			}
		});
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		footerPresenter_.get(new AsyncCallback<FooterPresenterWidget>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(FooterPresenterWidget result) {
				setInSlot(TYPE_CONTENT_FOOTER, result);
			}
		});

		setUpUserPage(user_);
		collectPresenterWidgets();
	}

	private void collectPresenterWidgets() {
		locationPresenter_.get(new AsyncCallback<UsersEventsWidgetPresenter>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(UsersEventsWidgetPresenter result) {
				result.setUser(user_);
				setInSlot(TYPE_CONTENT_MY_EVENTS, result);
			}
		});

		if (loggedUser_ != null) {
			if (!loggedUser_.equals(user_)) {
				view_.showListenButton(false, null);
				loadListenTo();
				loadRequestTo();
			}

			userControlPresenter_.get(new AsyncCallback<UserControlPanelWidgetPresenter>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.toString());
				}

				@Override
				public void onSuccess(UserControlPanelWidgetPresenter result) {
					setInSlot(TYPE_CONTENT_CONTROL, result);
				}
			});
		} else {
			controlPresenter_.get(new AsyncCallback<ControlPanelPresenterWidget>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.toString());
				}

				@Override
				public void onSuccess(ControlPanelPresenterWidget result) {
					setInSlot(TYPE_CONTENT_CONTROL, result);
				}
			});
		}
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@Override
	public boolean useManualReveal() {
		return true;
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);

		XUser oldUser = user_;

		loggedUser_ = pGatekeeper_.get().getLoggedInUser();

		if (loggedUser_ != null) {
			view_.isUserLogged(true);
		} else {
			view_.isUserLogged(false);
		}

		String id = request.getParameter("id", null);

		if ((loggedUser_ != null)
				&& (id.equals(loggedUser_.getUserID().toString()) || id
						.equals(loggedUser_.getUserID().toString()))) {
			user_ = loggedUser_;
			if ((oldUser != null) && !user_.getUserID().equals(oldUser.getUserID())) {
				setUpUserPage(user_);
				collectPresenterWidgets();
			}
			getProxy().manualReveal(getPresenter());
		} else if ((oldUser != null) && !oldUser.getUserID().toString().equals(id)) {
			rpcUser_.loadXUserByID(new Long(id), new AsyncCallback<XUser>() {
				@Override
				public void onFailure(Throwable caught) {
					// TODO error handling
					Window.alert("failure user loading");
				}

				@Override
				public void onSuccess(XUser result) {
					user_ = result;
					if (user_ != null) {
						setUpUserPage(user_);
						collectPresenterWidgets();
						getProxy().manualReveal(getPresenter());
					} else {
						Window.alert("no user found");
					}
				}
			});
		}
	}

	private void setUpUserPage(XUser user) {
		view_.setUser(user);
	}

	private void loadListenTo() {
		rpcUser_.loadListenTos(loggedUser_, XContactStatus.PERMIT,
				new AsyncCallback<List<XUser>>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(List<XUser> result) {
						if (result.contains(user_)) {
							view_.showListenButton(true, XContactStatus.PERMIT);
						}
					}
				});
	}

	private void loadRequestTo() {
		rpcUser_.loadListenTos(loggedUser_, XContactStatus.REQUEST,
				new AsyncCallback<List<XUser>>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(List<XUser> result) {
						if (result.contains(user_)) {
							view_.showListenButton(true, XContactStatus.REQUEST);
						}
					}
				});
	}

	private void saveListenTo(final HasText button, final HasText label) {
		rpcUser_.addContact(loggedUser_, user_, new AsyncCallback<XUser>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO error handdling
			}

			@Override
			public void onSuccess(XUser result) {
				button.setText(UserPageView.MINUS_SIGN);
				// label.setText(msg);
			}
		});
	}

	private void removeListenTo(final HasText button, final HasText label) {
		rpcUser_.removeContact(loggedUser_, user_, new AsyncCallback<XUser>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO error handdling
			}

			@Override
			public void onSuccess(XUser result) {
				button.setText(UserPageView.PLUS_SIGN);
				label.setText(null);
			}
		});
	}

	private Presenter<UserPagePresenter.IView, UserPagePresenter.IProxy> getPresenter() {
		return this;
	}
}
