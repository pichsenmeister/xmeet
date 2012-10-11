package webapp.client.view.xevent.eventwidget;

import webapp.model.XPosting;
import webapp.model.helper.XTime;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EventWidgetSinglePost extends Composite {

	private static EventWidgetSinglePostUiBinder uiBinder = GWT
			.create(EventWidgetSinglePostUiBinder.class);
	
	@UiField 
	Label date;
	@UiField 
	Label text;
	@UiField 
	Label name;
	@UiField 
	Image image;

	interface EventWidgetSinglePostUiBinder extends
			UiBinder<Widget, EventWidgetSinglePost> {
	}

	public EventWidgetSinglePost() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setPosting(XPosting posting) {
		image.setUrl(posting.getOwner().getImage().getMediaURL());
		image.setAltText(posting.getOwner().getUserName());

		name.setText(posting.getOwner().getUserName());
		text.setText(posting.getText());

		date.setText(XTime.getDateString(posting.getTime()));
	}
}
