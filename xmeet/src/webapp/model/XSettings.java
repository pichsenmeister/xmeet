package webapp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.sf.gilead.pojo.gwt.LightEntity;

import org.hibernate.annotations.GenericGenerator;

import webapp.model.enums.XVisibility;

@Entity
public class XSettings extends LightEntity implements Serializable {

	private static final long serialVersionUID = -2380919309289365222L;

	@Id
	@GenericGenerator(name = "xsetting_seq", strategy = "webapp.hibernate.XSettingSeq")
	@GeneratedValue(generator = "xsetting_seq")
	private Long settingsId;

	private boolean publicAccount = true;

	private boolean notificationEvent = true;

	private boolean notificationMessage = true;

	private boolean notificationListener = true;

	private boolean notificationUpdates = true;

	@Enumerated(EnumType.STRING)
	private XVisibility visibility = XVisibility.PRIVATE;

	// ---------- constructor ----------
	/**
	 * default constructor
	 */
	public XSettings() {

	}

	// ---------- Getter and Setter ----------
	@Deprecated
	public void setSettingsID(Long settingsId) {
		this.settingsId = settingsId;
	}

	public Long getSettingsID() {
		return settingsId;
	}

	public boolean isPublicAccount() {
		return publicAccount;
	}

	public void setPublicAccount(boolean publicAccount) {
		this.publicAccount = publicAccount;
	}

	public void setNotificationEvent(boolean notificationEvent) {
		this.notificationEvent = notificationEvent;
	}

	public boolean isNotificationEvent() {
		return notificationEvent;
	}

	public void setNotificationMessage(boolean notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	public boolean isNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationListener(boolean notificationListener) {
		this.notificationListener = notificationListener;
	}

	public boolean isNotificationListener() {
		return notificationListener;
	}

	public void setNotificationUpdates(boolean notificationUpdates) {
		this.notificationUpdates = notificationUpdates;
	}

	public boolean isNotificationUpdates() {
		return notificationUpdates;
	}

	@Override
	public boolean equals(Object obj) {
		XSettings settings = (XSettings) obj;
		if (this.getSettingsID().equals(settings.getSettingsID())) {
			return true;
		}
		return false;
	}

	public XVisibility getVisibility() {
		return visibility;
	}

	public void setVisibility(XVisibility visibility) {
		this.visibility = visibility;
	}
}
