package webapp.client.view.xevent.eventwidget;

import java.util.List;

import webapp.model.XEvent;
import webapp.model.XPosting;
import webapp.model.XUser;
import webapp.model.enums.XEventStatus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EventWidget extends Composite {
	
	public interface ICallbackSubscribing {
		void execute(EventWidget widget, XEventStatus status);
	}
	
	public interface ICallbackHeader {
		void execute(EventWidget widget);
	}
	
	public interface ICallbackPost {
		void execute(EventWidget widget, String text);
	}

	private static EventWidgetUiBinder uiBinder = GWT
			.create(EventWidgetUiBinder.class);
	
	@UiField 
	EventWidgetHeader header;
	@UiField 
	EventWidgetDescription description;
	@UiField 
	EventWidgetParticipant participant;
	@UiField 
	EventWidgetSubscribing subscribing;
	@UiField 
	EventWidgetPost post;
	@UiField 
	EventWidgetPostings postings;
	@UiField 
	EventWidgetExport export;
	
	XEvent event_;

	interface EventWidgetUiBinder extends UiBinder<Widget, EventWidget> {
	}

	public EventWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setData(XEvent event, XUser user, XEventStatus status) {
		event_ = event;
		
		header.setEvent(event);
		description.setDescription(event.getDescription());
		subscribing.setStatus(status);
		export.setEvent(event);
		post.setUser(user);
		postings.setPostings(event.getXPostings(), false);
		
		header.setEventWidget(this);
		subscribing.setEventWidget(this);
		post.setEventWidget(this);
	}

	public void setCallbackHeader(ICallbackHeader callback) {
		header.setCallback(callback);
	}

	public void setCallbackPost(ICallbackPost callback) {
		post.setCallback(callback);
	}

	public void setCallbackSubscribing(ICallbackSubscribing callback) {
		subscribing.setCallback(callback);		
	}
	
	public XEvent getEvent() {
		return event_;
	}
	
	public void setPostings(List<XPosting> posts) {
		postings.setPostings(posts, true);
	}
	
	public void setEventStatus(XEventStatus status) {
		subscribing.setStatus(status);	
	}
}
