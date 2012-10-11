package webapp.client.view.xevent.eventwidget;

import webapp.client.view.xevent.eventwidget.EventWidget.ICallbackHeader;
import webapp.model.XEvent;
import webapp.model.XUser;
import webapp.model.helper.XTime;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EventWidgetHeader extends Composite {

	private static EventWidgetOwnerUiBinder uiBinder = GWT
			.create(EventWidgetOwnerUiBinder.class);
	
	public static final String TIME_FORMAT = "HH:mm";
	public static final String DATE_FORMAT = "dd.MM.yyyy";
	
	@UiField 
	Image image;
	@UiField 
	Label username;
	@UiField 
	Label name;
	@UiField 
	Label date;
	@UiField 
	FlowPanel userlink;
	@UiField Label time;
	
	ICallbackHeader callback_;
	EventWidget widget_;

	interface EventWidgetOwnerUiBinder extends
			UiBinder<Widget, EventWidgetHeader> {
	}

	public EventWidgetHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setEvent(XEvent event) {
		XUser owner = event.getOwner();
		
		image.setAltText(owner.getUserName());
		image.setUrl(owner.getImage().getMediaURL());
		
		username.setText(owner.getUserName());	
		name.setText(owner.getName());
		
		date.setText(XTime.getDateString(event.getEventBegin()));
		time.setText("(" + XTime.getTimeString(event.getEventBegin()) + ")");
	}
	
	@UiHandler(value={"image", "username", "name"})
	void handleClick(ClickEvent e) {
	    if(callback_ != null && widget_ != null) {
	    	callback_.execute(widget_);
	    }
	}

	public void setCallback(ICallbackHeader callback) {
		callback_ = callback;
	}
	
	public void setEventWidget(EventWidget widget) {
		widget_ = widget;	
	}
}
