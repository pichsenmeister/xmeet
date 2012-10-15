package webapp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.sf.gilead.pojo.gwt.LightEntity;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class XLocation extends LightEntity implements Serializable {

	private static final long serialVersionUID = 6933501016365057876L;

	@Id
	@GenericGenerator(name = "xlocation_seq", strategy = "webapp.hibernate.XLocationSeq")
	@GeneratedValue(generator = "xlocation_seq")
	private Long locationID;

	private String name;

	private String country;

	private String city;

	private String zip;

	private String address;

	private Double longitude;

	private Double latitude;

	// ---------- constructor ----------
	/**
	 * default constructor
	 */
	public XLocation() {

	}

	// ---------- Getter and Setter ----------

	@Deprecated
	public void setLocationID(Long locationID) {
		this.locationID = locationID;
	}

	public Long getLocationID() {
		return locationID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getZip() {
		return zip;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	@Override
	public boolean equals(Object obj) {
		XLocation location = (XLocation) obj;
		if (this.getLocationID().equals(location.getLocationID())) {
			return true;
		}
		return false;
	}
}
