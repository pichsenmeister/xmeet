package webapp.client.view.xevent;

import java.util.List;

import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.xevent.EventReminderWidgetPresenter;
import webapp.client.view.xevent.reminder.EventReminder;
import webapp.model.XUser;
import webapp.model.XUserEvent;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for event reminder, implements the interface declared in presenter
 * 
 * @author David Pichsenmeister
 */
public class EventReminderWidget extends ViewImpl implements EventReminderWidgetPresenter.IView {

	// public static final String IN = "in ";
	// public static final String HOUR = " stunden";
	// public static final String NOW = "jetzt";
	//
	// private static final String TIME_FORMAT = "HH:mm";
	// private static final String DATE_FORMAT = "dd.MM.yyyy";

	private static final int WIDTH = 295;

	private static final String STYLE_WIDGET = "decorated-widget";

	// private static final String STYLE = "reminder-panel";
	// private static final String STYLE_IMG = "reminder-panel-image";
	// private static final String STYLE_USERNAME = "reminder-panel-username";
	// private static final String STYLE_NAME = "reminder-panel-name";
	// private static final String STYLE_TITLE = "reminder-panel-title";
	// private static final String STYLE_PLACE = "reminder-panel-place";
	// private static final String STYLE_DATE = "reminder-panel-date";

	private FlowPanel mainPanel_;

	private ITypedCallback<XUser> callbackUser_;

	/**
	 * constructor, contains the view
	 */
	public EventReminderWidget() {
		mainPanel_ = new FlowPanel();
		mainPanel_.setStyleName(STYLE_WIDGET);
		mainPanel_.setWidth(WIDTH + "px");
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}

	@Override
	public void setEvents(List<XUserEvent> events) {
		for (XUserEvent userEvent : events) {
			EventReminder reminder = new EventReminder();
			reminder.setData(userEvent.getXEvent(), userEvent.getStatus());
			reminder.setCallbackUser(callbackUser_);
			mainPanel_.add(reminder);
		}
	}

	@Override
	public void setCallbackUser(ITypedCallback<XUser> callback) {
		callbackUser_ = callback;
	}

	// @Override
	// public void setEvents(List<XUserEvent> events) {
	// for (XUserEvent userEvent : events) {
	// XEvent event = userEvent.getXEvent();
	// FlowPanel reminder = new FlowPanel();
	// reminder.setStyleName(STYLE);
	// mainPanel_.add(reminder);
	//
	// XUser owner = event.getOwner();
	//
	// Image img = new Image();
	// img.setAltText(owner.getUserName());
	// img.setUrl(owner.getImage().getMediaURL());
	// img.setStyleName(STYLE_IMG);
	// reminder.add(img);
	//
	// Label username = new Label(owner.getUserName());
	// username.setStyleName(STYLE_USERNAME);
	// reminder.add(username);
	//
	// Label name = new Label(owner.getName());
	// name.setStyleName(STYLE_NAME);
	// reminder.add(name);
	//
	// for (String entry : event.getEventTitle()) {
	// Label title = new Label(entry);
	// title.setStyleName(STYLE_TITLE);
	// reminder.add(title);
	// }
	//
	// Label place = new Label(event.getPlace());
	// place.setStyleName(STYLE_PLACE);
	// reminder.add(place);
	//
	// DateTimeFormat formatDate = DateTimeFormat.getFormat(DATE_FORMAT);
	// Date date = new Date(event.getEventBegin());
	// DateTimeFormat formatTime = DateTimeFormat.getFormat(TIME_FORMAT);
	// Date time = new Date(event.getEventBegin());
	//
	// Label dateLabel = new Label(getDate(event.getEventBegin()) + "    " + "["
	// + formatDate.format(date) + "   "
	// + (formatTime.format(time)) + "]");
	// dateLabel.setStyleName(STYLE_DATE);
	// reminder.add(dateLabel);
	// }
	// }

	// private String getDate(Long time) {
	// long now = System.currentTimeMillis();
	// long diff = time - now;
	// int hour = Math.round((diff / 3600000));
	// if (hour > 0) {
	// return IN + hour + HOUR;
	// }
	// return NOW;
	// }
}
