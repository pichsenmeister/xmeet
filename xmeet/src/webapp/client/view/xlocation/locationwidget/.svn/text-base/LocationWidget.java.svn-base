package webapp.client.view.xlocation.locationwidget;

import webapp.model.XLocation;
import webapp.model.XUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LocationWidget extends Composite {

	private static LocationWidgetUiBinder uiBinder = GWT
			.create(LocationWidgetUiBinder.class);

	interface LocationWidgetUiBinder extends UiBinder<Widget, LocationWidget> {
	}

	@UiField
	Label name;
	@UiField
	Label location;

	private XLocation xlocation_;
	private XUser user_;

	public LocationWidget() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	public void setLocation(XLocation loc) {
		xlocation_ = loc;
		location.setText(xlocation_.getName());
	}

	public void setUser(XUser user) {
		user_ = user;
		name.setText(user_.getName());
	}

}
