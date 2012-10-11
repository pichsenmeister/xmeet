package webapp.client.view.xlocation.locationwidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LocationCreateWidget extends Composite {

	private static LocationCreateWidgetUiBinder uiBinder = GWT.create(LocationCreateWidgetUiBinder.class);

	interface LocationCreateWidgetUiBinder extends UiBinder<Widget, LocationCreateWidget> {
	}

	public LocationCreateWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
