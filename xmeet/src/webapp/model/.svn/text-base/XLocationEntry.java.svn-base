package webapp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import net.sf.gilead.pojo.gwt.LightEntity;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class XLocationEntry extends LightEntity implements Serializable {

	private static final long serialVersionUID = 6933501016365057876L;
	
	@Id
	@GenericGenerator(name="xlocation_seq", strategy="webapp.hibernate.XLocationSeq")
	@GeneratedValue(generator="xlocation_seq")
	private Long entryID;
	
	private String locationName;
	
	@OneToOne
	private XLocation location;
	
	@OneToOne
	private XUser user;
	
	private Long created;
	
	private String text;
	
	// ---------- constructor ----------
	/**
	 *  default constructor
	 */
	public XLocationEntry() {

	}


	// ---------- Getter and Setter ----------

	@Deprecated
	public void setLocationEntryID(Long entryID) {
		this.entryID = entryID;
	}

	public Long getLocationEntryID() {
		return entryID;
	}

	public void setLocationName(String name) {
		this.locationName = name;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
	
	public void setLocation(XLocation location) {
		this.location = location;
	}

	public XLocation getLocation() {
		return location;
	}
	
	public void setUser(XUser user) {
		this.user = user;
	}

	public XUser getUser() {
		return user;
	}
	
	/**
	 * automatically set when saving
	 * 
	 * @param created
	 * 				time to set
	 */
	@Deprecated
	public void setCreated(Long created) {
		this.created = created;
	}

	public Long getCreated() {
		return created;
	}

	@Override
	public boolean equals(Object obj) {
		XLocationEntry location = (XLocationEntry) obj;
		if(this.getLocationEntryID().equals(location.getLocationEntryID())) {
			return true;
		}
		return false;
	}
}

