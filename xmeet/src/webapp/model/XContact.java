package webapp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import net.sf.gilead.pojo.gwt.LightEntity;
import webapp.model.enums.XContactStatus;

@Entity
public class XContact extends LightEntity implements Serializable {

	private static final long serialVersionUID = 5350093417257740890L;

	@Id
	@ManyToOne
	private XUser listener;

	@Id
	@ManyToOne
	private XUser listenTo;

	@Enumerated(EnumType.STRING)
	private XContactStatus status;

	// ---------- constructor ----------
	/**
	 * default constructor
	 */
	public XContact() {

	}

	// ---------- Getter and Setter ----------
	@Deprecated
	public void setListener(XUser user) {
		this.listener = user;
	}

	public XUser getListener() {
		return listener;
	}

	@Deprecated
	public void setListenTo(XUser user) {
		this.listenTo = user;
	}

	public XUser getListenTo() {
		return listenTo;
	}

	public XContactStatus getStatus() {
		return status;
	}

	public void setStatus(XContactStatus status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		XContact contact = (XContact) obj;
		if (this.getListener().equals(contact.getListener())
				&& this.getListenTo().equals(contact.getListenTo())) {
			return true;
		}
		return false;
	}
}
