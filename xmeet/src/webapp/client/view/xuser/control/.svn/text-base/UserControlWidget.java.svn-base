package webapp.client.view.xuser.control;

import java.util.HashMap;
import java.util.Map;

import webapp.client.callback.ICallback;
import webapp.client.callback.ITypedCallback;
import webapp.model.XUser;
import webapp.model.enums.MenuIcon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserControlWidget extends Composite {

	private static UserControlWidgetUiBinder uiBinder = GWT
			.create(UserControlWidgetUiBinder.class);

	interface UserControlWidgetUiBinder extends UiBinder<Widget, UserControlWidget> {
	}

	@UiField
	Image create;
	@UiField
	Image logo;
	@UiField
	Image settings;
	@UiField
	Image logout;
	@UiField
	Label name;
	@UiField
	Image profilePicture;
	@UiField
	LayoutPanel userPanel;
	@UiField
	Image news;
	@UiField
	Image contacts;

	FlowPanel accountsPanel;

	private ITypedCallback<String> callback_;
	private ICallback callbackHome_;
	private ICallback callbackUser_;
	private ICallback callbackContacts_;
	private ICallback callbackNews_;
	private ICallback callbackCreate_;
	private ICallback callbackSettings_;
	private ICallback callbackLogout_;
	private MenuIcon activeIcon_;

	private Map<MenuIcon, Image> icons_;

	public UserControlWidget() {
		initWidget(uiBinder.createAndBindUi(this));

		userPanel.addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (callbackUser_ != null) {
					callbackUser_.execute();
				}
			}
		}, ClickEvent.getType());

		icons_ = new HashMap<MenuIcon, Image>();
		icons_.put(MenuIcon.NEWS, news);
		icons_.put(MenuIcon.CONTACTS, contacts);
		icons_.put(MenuIcon.CREATE, create);
		icons_.put(MenuIcon.SETTINGS, settings);
		icons_.put(MenuIcon.LOGOUT, logout);
	}

	public void setCallbackSearch(ITypedCallback<String> callback) {
		this.callback_ = callback;
	}

	public void setCallbackHome_(ICallback callbackHome) {
		callbackHome_ = callbackHome;
	}

	public void setCallbackUser(ICallback callbackUser) {
		callbackUser_ = callbackUser;
	}

	public void setCallbackContacts(ICallback callbackMsg) {
		callbackContacts_ = callbackMsg;
	}

	public void setCallbackNews(ICallback callbackNews) {
		callbackNews_ = callbackNews;
	}

	public void setCallbackCreate(ICallback callbackCreate) {
		callbackCreate_ = callbackCreate;
	}

	public void setCallbackSettings(ICallback callbackSettings) {
		callbackSettings_ = callbackSettings;
	}

	public void setCallbackLogout(ICallback callbackLogout) {
		callbackLogout_ = callbackLogout;
	}

	@UiHandler("logo")
	void onLogoClick(ClickEvent event) {
		if (callbackHome_ != null) {
			callbackHome_.execute();
			setActiveIcon(MenuIcon.NEWS);
		}
	}

	@UiHandler("settings")
	void onSettingsClick(ClickEvent event) {
		if (callbackSettings_ != null) {
			callbackSettings_.execute();
			setActiveIcon(MenuIcon.SETTINGS);
		}
	}

	@UiHandler("logout")
	void onLogoutClick(ClickEvent event) {
		if (callbackLogout_ != null) {
			callbackLogout_.execute();
		}
	}

	@UiHandler("news")
	void onNewsClick(ClickEvent event) {
		if (callbackNews_ != null) {
			callbackNews_.execute();
			setActiveIcon(MenuIcon.NEWS);
		}
	}

	@UiHandler("create")
	void onCreateClick(ClickEvent event) {
		if (callbackCreate_ != null) {
			callbackCreate_.execute();
			setActiveIcon(MenuIcon.CREATE);
		}
	}

	@UiHandler("contacts")
	void onContactsClick(ClickEvent event) {
		if (callbackContacts_ != null) {
			callbackContacts_.execute();
			setActiveIcon(MenuIcon.CONTACTS);
		}
	}

	public void setUser(XUser user) {
		name.setText(user.getName());
		profilePicture.setUrl(user.getImage().getMediaURL());
		profilePicture.setAltText(user.getName());
	}

	public void setActiveIcon(MenuIcon icon) {
		activeIcon_ = icon;
		for (MenuIcon ic : icons_.keySet()) {
			if (ic.equals(icon)) {
				icons_.get(icon).setUrl(icon.getActive());
			} else {
				icons_.get(ic).setUrl(ic.getInactive());
			}
		}
	}

	@UiHandler("news")
	void onNewsMouseOver(MouseOverEvent event) {
		news.setUrl(MenuIcon.NEWS.getActive());
	}

	@UiHandler("news")
	void onNewsMouseOut(MouseOutEvent event) {
		if (!icons_.get(activeIcon_).equals(news)) {
			news.setUrl(MenuIcon.NEWS.getInactive());
		}
	}

	@UiHandler("contacts")
	void onContactsMouseOver(MouseOverEvent event) {
		contacts.setUrl(MenuIcon.CONTACTS.getActive());
	}

	@UiHandler("contacts")
	void onContactsMouseOut(MouseOutEvent event) {
		if (!icons_.get(activeIcon_).equals(contacts)) {
			contacts.setUrl(MenuIcon.CONTACTS.getInactive());
		}
	}

	@UiHandler("create")
	void onCreateMouseOver(MouseOverEvent event) {
		create.setUrl(MenuIcon.CREATE.getActive());
	}

	@UiHandler("create")
	void onCreateMouseOut(MouseOutEvent event) {
		if (!icons_.get(activeIcon_).equals(create)) {
			create.setUrl(MenuIcon.CREATE.getInactive());
		}
	}

	@UiHandler("settings")
	void onSettingsMouseOver(MouseOverEvent event) {
		settings.setUrl(MenuIcon.SETTINGS.getActive());
	}

	@UiHandler("settings")
	void onSettingsMouseOut(MouseOutEvent event) {
		if (!icons_.get(activeIcon_).equals(settings)) {
			settings.setUrl(MenuIcon.SETTINGS.getInactive());
		}
	}

	@UiHandler("logout")
	void onLogoutMouseOver(MouseOverEvent event) {
		logout.setUrl(MenuIcon.LOGOUT.getActive());
	}

	@UiHandler("logout")
	void onLogoutMouseOut(MouseOutEvent event) {
		if (!icons_.get(activeIcon_).equals(logout)) {
			logout.setUrl(MenuIcon.LOGOUT.getInactive());
		}
	}
}
