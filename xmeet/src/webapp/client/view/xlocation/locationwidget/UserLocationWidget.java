package webapp.client.view.xlocation.locationwidget;

import webapp.model.XLocationEntry;
import webapp.model.helper.XTime;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class UserLocationWidget extends Composite {

	private static UserLocationWidgetUiBinder uiBinder = GWT
			.create(UserLocationWidgetUiBinder.class);
	
	@UiField 
	Label date;
	@UiField 
	Label location;

	interface UserLocationWidgetUiBinder extends
			UiBinder<Widget, UserLocationWidget> {
	}

	public UserLocationWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setData(XLocationEntry loc) {		
		date.setText(XTime.getDateString(loc.getCreated()));
		location.setText(loc.getText());
	}
}
