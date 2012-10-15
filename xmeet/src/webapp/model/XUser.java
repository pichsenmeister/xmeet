package webapp.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import net.sf.gilead.pojo.gwt.LightEntity;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class XUser extends LightEntity implements Serializable {

	private static final long serialVersionUID = 5670615498320799716L;

	@Id
	@GenericGenerator(name = "xuser_seq", strategy = "webapp.hibernate.XUserSeq")
	@GeneratedValue(generator = "xuser_seq")
	private Long userID;

	@Column(length = 50)
	private String name;

	@Column(length = 50, nullable = false, unique = true)
	private String email;

	@Column(length = 255, nullable = false)
	private String password;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "media_mediaid")
	private XMedia image;

	@OneToOne
	private XSettings settings;

	private String website;

	private String place;

	// ---------- constructor ----------
	/**
	 * default constructor
	 */
	public XUser() {

	}

	// ---------- Getter and Setter ----------
	@Deprecated
	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setImage(XMedia image) {
		this.image = image;
	}

	public XMedia getImage() {
		return image;
	}

	public XSettings getSettings() {
		return settings;
	}

	public void setSettings(XSettings settings) {
		this.settings = settings;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String url) {
		website = url;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Override
	public boolean equals(Object obj) {
		XUser user = (XUser) obj;
		if (this.getUserID().equals(user.getUserID())) {
			return true;
		}
		return false;
	}
}
