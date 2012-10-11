package webapp.client.view.xevent.reminder;

import webapp.client.callback.ITypedCallback;
import webapp.model.XEvent;
import webapp.model.XUser;
import webapp.model.enums.XEventStatus;
import webapp.model.helper.XTime;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EventReminder extends Composite {

	private static EventReminderUiBinder uiBinder = GWT.create(EventReminderUiBinder.class);

	interface EventReminderUiBinder extends UiBinder<Widget, EventReminder> {
	}

	@UiField
	Label username;
	@UiField
	Label name;
	@UiField
	Label title;
	@UiField
	Image image;
	@UiField
	Label date;
	@UiField
	Label time;

	private ITypedCallback<XUser> callbackUser_;
	private XUser owner_;

	public EventReminder() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setData(XEvent event, XEventStatus eventStatus) {
		owner_ = event.getOwner();

		image.setAltText(owner_.getUserName());
		image.setUrl(owner_.getImage().getMediaURL());

		username.setText(owner_.getUserName());
		name.setText(owner_.getName());

		date.setText(XTime.getShortDateString(event.getEventBegin()));
		time.setText("(" + XTime.getTimeString(event.getEventBegin()) + ")");

		StringBuilder titleStr = new StringBuilder();

		for (String str : event.getEventTitle()) {
			titleStr.append(str + ", ");
		}

		title.getElement().setInnerHTML(titleStr.toString());
	}

	public void setCallbackUser(ITypedCallback<XUser> callback) {
		callbackUser_ = callback;
	}

	@UiHandler(value = { "image", "username", "name" })
	void handleClick(ClickEvent e) {
		if (callbackUser_ != null) {
			callbackUser_.execute(owner_);
		}
	}
}
