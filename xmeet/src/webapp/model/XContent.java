package webapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import net.sf.gilead.pojo.gwt.LightEntity;

@Entity
public class XContent extends LightEntity implements Serializable {

	private static final long serialVersionUID = 4200402361546862829L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long contentID;

	@Column(nullable = false, unique = true)
	private String contentName;

	private String content;


	// ---------- constructor ----------
	/**
	 *  default constructor
	 */
	public XContent() {

	}


	// ---------- Getter and Setter ----------
	@Deprecated
	public void setContentID(Long contentID) {
		this.contentID = contentID;
	}

	public Long getContentID() {
		return contentID;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContent(String html) {
		this.content = html;
	}

	public String getContent() {
		return content;
	}
	
	@Override
	public boolean equals(Object obj) {
		XContent content = (XContent) obj;
		if(this.getContentID().equals(content.getContentID())) {
			return true;
		}
		return false;
	}
}
