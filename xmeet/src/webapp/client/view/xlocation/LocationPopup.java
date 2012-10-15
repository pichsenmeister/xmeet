package webapp.client.view.xlocation;

import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.xlocation.LocationPopupPresenter;
import webapp.client.view.customwidget.XButton;
import webapp.client.view.customwidget.XTextBox;
import webapp.model.XLocation;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.maps.client.MapType;
import com.google.gwt.maps.client.MapUIOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LatLngCallback;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewImpl;

/**
 * the view for the LocationPopup, implements the interface declared in
 * presenter
 * 
 * @author David Pichsenmeister
 */
public class LocationPopup extends PopupViewImpl implements
		LocationPopupPresenter.IView {

	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String SEARCH = "search";
	public static final String SAVE = "save";
	public static final String GEOCODE = "the address could not be geocoded...";

	private static final String STYLE_POPUP = "qr-popup";
	private static final int ZOOMLEVEL = 18;
	private static final int ZOOMLEVEL_START = 10;

	private final PopupPanel widget;
	private final XTextBox name_;
	private final XTextBox address_;
	private MapWidget map_;
	private final Label geocode_;

	private XLocation location_;
	private ITypedCallback<XLocation> callback_;

	@Inject
	public LocationPopup(EventBus eventBus) {
		super(eventBus);

		widget = new PopupPanel(true);
		widget.setAnimationEnabled(true);
		widget.setModal(true);
		widget.setGlassEnabled(true);
		widget.center();

		final FlowPanel panel = new FlowPanel();
		panel.setStyleName(STYLE_POPUP);
		panel.setSize("700px", "500px");
		widget.setWidget(panel);

		Label name = new Label(NAME);
		panel.add(name);

		name_ = new XTextBox();
		name_.setRequired(true);
		panel.add(name_);

		Label address = new Label(ADDRESS);
		panel.add(address);

		address_ = new XTextBox();
		address_.setRequired(true);
		panel.add(address_);

		XButton geocode = new XButton(SEARCH);
		panel.add(geocode);
		geocode.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				geocode(address_.getText());
			}
		});

		Maps.loadMapsApi("", "2", false, new Runnable() {
			@Override
			public void run() {
				map_ = new MapWidget();
				map_.setSize("650px", "350px");

				MapUIOptions options = map_.getDefaultUI();
				options.setSmallZoomControl3d(false);
				options.setLargeMapControl3d(true);
				map_.setUI(options);
				map_.setCurrentMapType(MapType.getSatelliteMap());
				map_.setZoomLevel(ZOOMLEVEL_START);

				panel.add(map_);
			}
		});

		XButton save = new XButton(SAVE);
		panel.add(save);
		save.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if ((callback_ != null) && validate()) {
					location_.setName(name_.getText());
					callback_.execute(location_);
				}
			}
		});

		geocode_ = new Label(GEOCODE);
		geocode_.setVisible(false);
	}

	private void geocode(String address) {
		Geocoder geocoder = new Geocoder();
		geocoder.getLatLng(address, new LatLngCallback() {

			@Override
			public void onSuccess(LatLng point) {
				if (map_ != null) {
					map_.setZoomLevel(ZOOMLEVEL);
					map_.panTo(point);
					map_.clearOverlays();
					map_.setCenter(point);
					Icon icon = Icon.newInstance(Icon.getDefaultIcon());
					MarkerOptions ops = MarkerOptions.newInstance(icon);
					Marker marker = new Marker(point, ops);
					map_.addOverlay(marker);
					map_.checkResizeAndCenter();
				}
				location_ = new XLocation();
				location_.setLatitude(point.getLatitude());
				location_.setLongitude(point.getLongitude());
				reverseGeocode(point);
			}

			@Override
			public void onFailure() {
				Window.alert(GEOCODE);
			}
		});
	}

	private void reverseGeocode(LatLng point) {
		Geocoder geocoder = new Geocoder();
		geocoder.getLocations(point, new LocationCallback() {

			@Override
			public void onFailure(int statusCode) {
				Window.alert(GEOCODE);
			}

			@Override
			public void onSuccess(JsArray<Placemark> locations) {
				if (locations.length() > 0) {
					Placemark mark = locations.get(0);
					location_.setCountry(mark.getCountry());
					location_.setCity(mark.getCity());
					location_.setAddress(mark.getStreet());
					location_.setZip(mark.getPostalCode());
				}
			}
		});
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setCallback(ITypedCallback<XLocation> callback) {
		callback_ = callback;
	}

	private boolean validate() {
		boolean name = name_.validate();
		boolean lat = location_.getLatitude() != null ? true : false;
		boolean lng = location_.getLongitude() != null ? true : false;
		if (!lat || !lng) {
			setGeocoded(false);
		} else {
			setGeocoded(true);
		}
		return name && lat && lng;
	}

	private void setGeocoded(boolean geocoded) {
		if (geocoded) {
			geocode_.setVisible(false);
		} else {
			geocode_.setVisible(true);
		}
	}

}
