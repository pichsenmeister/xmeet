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
public class XListenTo extends LightEntity implements Serializable{

	private static final long serialVersionUID = 92067169529333857L;

	@Id
	@GenericGenerator(name = "xcontact_seq", strategy = "webapp.hibernate.XContactSeq")
	@GeneratedValue(generator = "xcontact_seq")
	private Long listenToId;

	@OneToOne
	private XUser user;

	@Enumerated(EnumType.STRING)
	private XContactStatus status;

	// ---------- constructor ----------
	/**
	 * default constructor
	 */
	public XListenTo() {

	}

	// ---------- Getter and Setter ----------
	@Deprecated
	public void setListenToID(Long contactId) {
		this.listenToId = contactId;
	}

	public Long getListenToID() {
		return listenToId;
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
