package webapp.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import net.sf.gilead.pojo.gwt.LightEntity;

import org.hibernate.annotations.GenericGenerator;

import webapp.model.enums.XMediaType;

@Entity
public class XMedia extends LightEntity implements Serializable {

	private static final long serialVersionUID = 4200402361546862829L;

	@Id
	@GenericGenerator(name = "xmedia_seq", strategy = "webapp.hibernate.XMediaSeq")
	@GeneratedValue(generator = "xmedia_seq")
	private Long mediaID;

	private String mediaName;

	private String mediaURL;

	@Enumerated(EnumType.STRING)
	private XMediaType mediaType;

	@OneToOne(targetEntity = XUser.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	private XUser owner;

	private Long time;

	// ---------- constructor ----------
	/**
	 * default constructor
	 */
	public XMedia() {

	}

	// ---------- Getter and Setter ----------
	@Deprecated
	public void setMediaID(Long mediaID) {
		this.mediaID = mediaID;
	}

	public Long getMediaID() {
		return mediaID;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaURL(String mediaURL) {
		this.mediaURL = mediaURL;
	}

	public String getMediaURL() {
		return mediaURL;
	}

	public void setOwner(XUser owner) {
		this.owner = owner;
	}

	public XUser getOwner() {
		return owner;
	}

	public void setMediaType(XMediaType mediaType) {
		this.mediaType = mediaType;
	}

	public XMediaType getMediaType() {
		return mediaType;
	}

	public Long getTime() {
		return time;
	}

	@Deprecated
	public void setTime(Long time) {
		this.time = time;
	}

	@Override
	public boolean equals(Object obj) {
		XMedia media = (XMedia) obj;
		if (this.getMediaID().equals(media.getMediaID())) {
			return true;
		}
		return false;
	}
}
