package webapp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import net.sf.gilead.pojo.gwt.LightEntity;

import org.hibernate.annotations.GenericGenerator;

import webapp.model.enums.XContactStatus;

@Entity
public class XListener extends LightEntity implements Serializable{

	private static final long serialVersionUID = -2069465531370678024L;

	@Id
	@GenericGenerator(name = "xcontact_seq", strategy = "webapp.hibernate.XContactSeq")
	@GeneratedValue(generator = "xcontact_seq")
	private Long listenerId;

	@OneToOne
	private XUser user;

	@Enumerated(EnumType.STRING)
	private XContactStatus status;

	// ---------- constructor ----------
	/**
	 * default constructor
	 */
	public XListener() {

	}

	// ---------- Getter and Setter ----------
	@Deprecated
	public void setListenerID(Long contactId) {
		this.listenerId = contactId;
	}

	public Long getListenerID() {
		return listenerId;
	}

	public void setContact(XUser user) {
		this.user = user;
	}

	public XUser getContact() {
		return user;
	}

	public void setStatus(XContactStatus status) {
		this.status = status;
	}

	public XContactStatus getStatus() {
		return status;
	}
}
