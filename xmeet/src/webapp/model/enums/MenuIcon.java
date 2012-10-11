package webapp.model.enums;

public enum MenuIcon {
	NEWS("xmeet/icon_home.png", "xmeet/icon_home_inactive.png"),
	CONTACTS("xmeet/icon_contacts.png", "xmeet/icon_contacts_inactive.png"),
	CREATE("xmeet/icon_create.png", "xmeet/icon_create_inactive.png"),
	SETTINGS("xmeet/icon_settings.png", "xmeet/icon_settings_inactive.png"),
	LOGOUT("xmeet/icon_logout.png", "xmeet/icon_logout_inactive.png");

	private String active_;
	private String inactive_;

	MenuIcon(String active, String inactive) {
		active_ = active;
		inactive_ = inactive;
	}

	public String getActive() {
		return active_;
	}

	public String getInactive() {
		return inactive_;
	}
}
