package webapp.client.view.xevent.eventfinder;

import webapp.client.callback.ITypedCallback;
import webapp.client.view.customwidget.ListView;
import webapp.model.XEventFinder;
import webapp.model.XEventFinderUser;
import webapp.model.XUser;
import webapp.model.XUserEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EventFinder extends Composite {

	private static EventFinderUiBinder uiBinder = GWT.create(EventFinderUiBinder.class);

	interface EventFinderUiBinder extends UiBinder<Widget, EventFinder> {
	}

	public static final String NEW_ENTRY = "+ neuer termin vorschlag";

	@UiField
	Image profilePicture;
	@UiField
	Label description;
	@UiField
	FlowPanel userLink;
	@UiField
	Label username;
	@UiField
	Label name;
	@UiField
	ListView<XEventFinderUser> timeList;
	@UiField
	DisclosurePanel newTime;

	XEventFinder eventFinder_;

	ITypedCallback<XUser> callbackUser_;
	ITypedCallback<XUserEvent> callbackStatus_;

	public EventFinder() {
		initWidget(uiBinder.createAndBindUi(this));

		newTime.setAnimationEnabled(true);
		newTime.setOpen(false);
		newTime.setHeader(new Label(NEW_ENTRY));

		newTime.setContent(new EventDatePicker());
	}

	public void setData(XEventFinder eventFinder) {
		eventFinder_ = eventFinder;

		XUser owner = eventFinder.getEvent().getOwner();

		profilePicture.setAltText(owner.getUserName());
		profilePicture.setUrl(owner.getImage().getMediaURL());

		username.setText(owner.getUserName());
		name.setText(owner.getName());

		description.getElement().setInnerHTML(eventFinder_.getEvent().getDescription());
	}

	public void setCallbackUser(ITypedCallback<XUser> callback) {
		callbackUser_ = callback;
	}

	public void setCallbackStatus(ITypedCallback<XUserEvent> callback) {
		callbackStatus_ = callback;
	}

	public XEventFinder getEventFinder() {
		return eventFinder_;
	}

	@UiHandler(value = { "profilePicture", "username", "name" })
	void handleClick(ClickEvent e) {
		if (callbackUser_ != null) {
			callbackUser_.execute(eventFinder_.getEvent().getOwner());
		}
	}
}
