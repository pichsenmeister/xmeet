package webapp.client.presenter.settings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import webapp.client.callback.ITypedCallback;
import webapp.client.event.XNameToken;
import webapp.client.presenter.Password;
import webapp.client.presenter.content.FooterPresenterWidget;
import webapp.client.presenter.xuser.LoggedInGatekeeper;
import webapp.client.presenter.xuser.UserControlPanelWidgetPresenter;
import webapp.client.rpc.xmedia.RPCMediaAsync;
import webapp.client.rpc.xuser.RPCUserAsync;
import webapp.model.XMedia;
import webapp.model.XSettings;
import webapp.model.XUser;
import webapp.model.enums.MenuIcon;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.common.client.CodeSplitProvider;
import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

/**
 * the presenter for the settings page
 * 
 * @author David Pichsenmeister
 */
public class SettingsPresenter extends
		Presenter<SettingsPresenter.IView, SettingsPresenter.IProxy> {

	public static final String NAME = "name";
	public static final String MAIL = "mail";
	public static final String LOCATION = "location";
	public static final String PRIVACY = "privacy";
	public static final String WEBSITE = "website";

	public static final String NEW_PW = "newpassword";
	public static final String OLD_PW = "oldpassword";
	public static final String REPEAT_PW = "repeatpassword";

	public static final String NOTE_EVENT = "note-event";
	public static final String NOTE_MSG = "note-message";
	public static final String NOTE_LISTENER = "note-listener";
	public static final String NOTE_UPDATE = "note-upates";

	/**
	 * the interface for the settings view
	 * 
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {
		void setProfileMap(HashMap<String, String> map);

		void setNotificationMap(HashMap<String, Boolean> map);

		void setImage(XMedia media);

		void setUser(XUser user);

		void setImageList(List<XMedia> images);

		void setCallbackProfile(ICallbackProfile callback);

		void setCallbackPassword(ICallbackPassword callback);

		void setCallbackNotification(ICallbackNotification callback);

		void setCallbackImage(ITypedCallback<XMedia> callback);

		void setCallbackSaveImage(ITypedCallback<XMedia> callback);
	}

	public interface ICallbackProfile {
		void execute(Map<String, String> params);
	}

	public interface ICallbackPassword {
		void execute(Map<String, String> params);
	}

	public interface ICallbackNotification {
		void execute(Map<String, Boolean> params);
	}

	@ProxyCodeSplit
	@NameToken(XNameToken.SETTINGS)
	@UseGatekeeper(LoggedInGatekeeper.class)
	public interface IProxy extends ProxyPlace<SettingsPresenter> {

	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_CONTROL = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_MAIN = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_FOOTER = new Type<RevealContentHandler<?>>();

	private final IndirectProvider<UserControlPanelWidgetPresenter> controlPresenter_;
	private final IndirectProvider<FooterPresenterWidget> footerPresenter_;

	private static final int MAX_RESULT = 10;

	private final RPCUserAsync rpcUser_;
	private final RPCMediaAsync rpcMedia_;
	private final IView view_;
	private XUser user_;
	private final int startMedia_ = 0;

	@Inject
	private Provider<LoggedInGatekeeper> pGatekeeper_;

	/**
	 * the constructor
	 * 
	 * @param rpcUser
	 *            rpc service for database connection
	 * @param rpcMedia
	 *            rpc service for database connection
	 * @param eventBus
	 *            the GWT EventBus
	 * @param view
	 *            the view to the presenter
	 * @param proxy
	 *            the proxy to the presenter
	 * @param controlPresenter
	 *            the UserControlPresenterWidget
	 * @param footerPresenter
	 *            the FooterPresenterWidget
	 */
	@Inject
	public SettingsPresenter(RPCUserAsync rpcUser, RPCMediaAsync rpcMedia,
			EventBus eventBus, IView view, IProxy proxy,
			AsyncProvider<UserControlPanelWidgetPresenter> controlPresenter,
			AsyncProvider<FooterPresenterWidget> footerPresenter) {
		super(eventBus, view, proxy);

		rpcUser_ = rpcUser;
		rpcMedia_ = rpcMedia;
		view_ = view;

		controlPresenter_ = new CodeSplitProvider<UserControlPanelWidgetPresenter>(
				controlPresenter);
		footerPresenter_ = new CodeSplitProvider<FooterPresenterWidget>(
				footerPresenter);
	}

	@Override
	protected void onBind() {
		super.onBind();

		view_.setCallbackProfile(new ICallbackProfile() {
			@Override
			public void execute(Map<String, String> params) {
				if (user_ != null) {
					saveUserProfile(params);
				}
			}
		});

		view_.setCallbackPassword(new ICallbackPassword() {
			@Override
			public void execute(Map<String, String> params) {
				if (user_ != null) {
					saveUserPassword(params);
				}
			}
		});

		view_.setCallbackNotification(new ICallbackNotification() {
			@Override
			public void execute(Map<String, Boolean> params) {
				if (user_ != null) {
					saveUserNotification(params);
				}
			}
		});

		view_.setCallbackImage(new ITypedCallback<XMedia>() {
			@Override
			public void execute(XMedia image) {
				if (user_ != null) {
					saveImage(image);
				}
			}
		});

		view_.setCallbackSaveImage(new ITypedCallback<XMedia>() {
			@Override
			public void execute(XMedia image) {
				if (user_ != null) {
					saveUserImage(image);
				}
			}
		});
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		user_ = pGatekeeper_.get().getLoggedInUser();

		controlPresenter_
				.get(new AsyncCallback<UserControlPanelWidgetPresenter>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO error handling
						Window.alert(caught.toString());
					}

					@Override
					public void onSuccess(UserControlPanelWidgetPresenter result) {
						setInSlot(TYPE_CONTENT_CONTROL, result);
						result.setActiveIcon(MenuIcon.SETTINGS);
					}
				});

		footerPresenter_.get(new AsyncCallback<FooterPresenterWidget>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO error handling
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(FooterPresenterWidget result) {
				setInSlot(TYPE_CONTENT_FOOTER, result);
			}
		});

		HashMap<String, String> profile = new HashMap<String, String>();
		profile.put(SettingsPresenter.NAME, user_.getName());
		profile.put(SettingsPresenter.MAIL, user_.getEmail());
		profile.put(SettingsPresenter.LOCATION, user_.getPlace());
		profile.put(SettingsPresenter.WEBSITE, user_.getWebsite());
		if (user_.getSettings().isPublicAccount()) {
			profile.put(SettingsPresenter.PRIVACY, "false");
		} else {
			profile.put(SettingsPresenter.PRIVACY, "true");
		}
		view_.setProfileMap(profile);

		HashMap<String, Boolean> notification = new HashMap<String, Boolean>();
		notification.put(SettingsPresenter.NOTE_EVENT, user_.getSettings()
				.isNotificationEvent());
		notification.put(SettingsPresenter.NOTE_MSG, user_.getSettings()
				.isNotificationMessage());
		notification.put(SettingsPresenter.NOTE_LISTENER, user_.getSettings()
				.isNotificationListener());
		notification.put(SettingsPresenter.NOTE_UPDATE, user_.getSettings()
				.isNotificationUpdates());
		view_.setNotificationMap(notification);

		view_.setImage(user_.getImage());
		view_.setUser(user_);

		rpcMedia_.loadXMediaList(user_, startMedia_, MAX_RESULT,
				new AsyncCallback<List<XMedia>>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onSuccess(List<XMedia> result) {
						view_.setImageList(result);
					}
				});
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	private void saveUserProfile(Map<String, String> params) {
		user_.setName(params.get(NAME));
		user_.setEmail(params.get(MAIL));
		user_.setPlace(params.get(LOCATION));
		user_.setWebsite(params.get(WEBSITE));
		if (params.get(PRIVACY).equals("true")) {
			user_.getSettings().setPublicAccount(false);
		} else {
			user_.getSettings().setPublicAccount(true);
		}
		rpcUser_.saveXUser(user_, new AsyncCallback<XUser>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("nicht gespeichert: profile");
			}

			@Override
			public void onSuccess(XUser result) {
				Window.alert("gespeichert: profile");
			}
		});
	}

	private void saveUserPassword(Map<String, String> params) {
		String old = params.get(OLD_PW);
		String new1 = params.get(NEW_PW);
		String new2 = params.get(REPEAT_PW);

		if (Password.hashPassword(old).equals(user_.getPassword())) {
			if (new1.equals(new2)) {
				String password = Password.hashPassword(new1);
				if (password != null) {
					user_.setPassword(password);
					rpcUser_.saveXUser(user_, new AsyncCallback<XUser>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("nicht gespeichert: password");
						}

						@Override
						public void onSuccess(XUser result) {
							Window.alert("gespeichert: password");
						}
					});
				} else {
					Window.alert("somethings wrong");
				}
			} else {
				Window.alert("passwords not equal");
			}
		} else {
			Window.alert("password incorrect");
		}

	}

	private void saveUserNotification(Map<String, Boolean> params) {
		XSettings settings = user_.getSettings();
		settings.setNotificationEvent(params.get(NOTE_EVENT));
		settings.setNotificationListener(params.get(NOTE_LISTENER));
		settings.setNotificationMessage(params.get(NOTE_MSG));
		settings.setNotificationUpdates(params.get(NOTE_UPDATE));

		rpcUser_.saveXUser(user_, new AsyncCallback<XUser>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("nicht gespeichert: notification");
			}

			@Override
			public void onSuccess(XUser result) {
				Window.alert("gespeichert: notification");
			}
		});
	}

	private void saveImage(XMedia image) {
		rpcMedia_.saveXMedia(image, user_, new AsyncCallback<XMedia>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(XMedia result) {
				view_.setImage(result);
			}
		});
	}

	private void saveUserImage(XMedia image) {
		user_.setImage(image);
		rpcUser_.saveXUser(user_, new AsyncCallback<XUser>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(XUser result) {
				user_ = result;
			}
		});
	}
}
