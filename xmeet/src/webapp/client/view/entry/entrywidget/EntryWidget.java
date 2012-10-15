package webapp.client.view.entry.entrywidget;

import webapp.client.callback.ITypedCallback;
import webapp.model.XLocation;
import webapp.model.XLocationEntry;
import webapp.model.XUser;

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

public class EntryWidget extends Composite {

	private static EntryWidgetUiBinder uiBinder = GWT
			.create(EntryWidgetUiBinder.class);

	interface EntryWidgetUiBinder extends UiBinder<Widget, EntryWidget> {
	}

	@UiField
	Image profilePicture;
	@UiField
	Label description;
	@UiField
	FlowPanel userLink;
	@UiField
	Label name;

	XLocationEntry entry_;

	ITypedCallback<XUser> callbackUser_;
	ITypedCallback<XLocation> callbackLoc_;

	public EntryWidget() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	public void setData(XLocationEntry entry) {
		entry_ = entry;

		XUser owner = entry.getUser();

		profilePicture.setAltText(owner.getName());
		profilePicture.setUrl(owner.getImage().getMediaURL());

		name.setText(owner.getName());

		description.getElement().setInnerHTML(entry.getText());

		// Runnable onLoadCallback = new Runnable() {
		// @Override
		// public void run() {
		// ICalender ical = new ICalender(event_);
		// qr.setWidget(ical.getPanel());
		// }
		// };
		// VisualizationUtils.loadVisualizationApi(onLoadCallback,
		// ImageChart.PACKAGE);
	}

	public void setCallbackUser(ITypedCallback<XUser> callback) {
		callbackUser_ = callback;
	}

	public void setCallbackLocation(ITypedCallback<XLocation> callback) {
		callbackLoc_ = callback;
	}

	public XLocationEntry getLocationEntry() {
		return entry_;
	}

	@UiHandler(value = { "profilePicture", "name" })
	void handleClick(ClickEvent e) {
		if (callbackUser_ != null) {
			callbackUser_.execute(entry_.getUser());
		}
	}

	@UiHandler("description")
	void handleDescriptionClick(ClickEvent e) {
		if ((callbackLoc_ != null) && (entry_.getLocation() != null)) {
			callbackLoc_.execute(entry_.getLocation());
		}
	}
}
