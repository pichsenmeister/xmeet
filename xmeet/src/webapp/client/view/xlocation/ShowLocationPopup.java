package webapp.client.view.xlocation;

import webapp.client.presenter.xlocation.ShowLocationPopupPresenter;
import webapp.model.XLocation;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.maps.client.MapType;
import com.google.gwt.maps.client.MapUIOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
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
public class ShowLocationPopup extends PopupViewImpl implements
		ShowLocationPopupPresenter.IView {

	public static final String NAME = "name";
	public static final String ADDRESS = "address";

	private static final String STYLE_POPUP = "qr-popup";
	private static final int ZOOMLEVEL = 18;
	private static final int ZOOMLEVEL_START = 10;

	private final PopupPanel widget;
	private final Label name_;
	private final Label address_;
	private MapWidget map_;

	@Inject
	public ShowLocationPopup(EventBus eventBus) {
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

		name_ = new Label();
		panel.add(name_);

		Label address = new Label(ADDRESS);
		panel.add(address);

		address_ = new Label();
		panel.add(address_);

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
	}

	@Override
	public void setLocation(XLocation location) {
		name_.setText(location.getName());
		address_.setText(location.getAddress() + ", " + location.getZip()
				+ ", " + location.getCity());
		LatLng point = LatLng.newInstance(location.getLatitude(),
				location.getLongitude());

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
		// location_ = new XLocation();
		// location_.setLatitude(point.getLatitude());
		// location_.setLongitude(point.getLongitude());
		// reverseGeocode(point);
	}

	private void reverseGeocode(LatLng point) {
		Geocoder geocoder = new Geocoder();
		geocoder.getLocations(point, new LocationCallback() {

			@Override
			public void onFailure(int statusCode) {

			}

			@Override
			public void onSuccess(JsArray<Placemark> locations) {
				if (locations.length() > 0) {
					Placemark mark = locations.get(0);
					// location_.setCountry(mark.getCountry());
					// location_.setCity(mark.getCity());
					// location_.setAddress(mark.getStreet());
					// location_.setZip(mark.getPostalCode());
				}
			}
		});
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

}
