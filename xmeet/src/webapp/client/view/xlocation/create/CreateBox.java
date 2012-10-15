package webapp.client.view.xlocation.create;

import java.util.List;

import webapp.client.callback.ICallback;
import webapp.client.callback.ITypedCallback;
import webapp.client.view.TextProcessingEngine;
import webapp.client.view.customwidget.ListView;
import webapp.client.view.customwidget.ListView.IGenerator;
import webapp.client.view.customwidget.XButton;
import webapp.client.view.customwidget.XTextArea;
import webapp.model.XLocation;
import webapp.model.XLocationEntry;
import webapp.model.enums.XVisibility;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class CreateBox extends Composite {

	private static CreateBoxUiBinder uiBinder = GWT
			.create(CreateBoxUiBinder.class);

	interface CreateBoxUiBinder extends UiBinder<Widget, CreateBox> {
	}

	private static Integer COUNT = 100;
	public static final String SUBMIT = "save";
	public static final String NEW_LOC = "new location";

	@UiField
	ListView<XLocation> locations;
	@UiField
	Image back;
	@UiField
	Image next;
	@UiField
	ListBox visibility;
	@UiField
	XTextArea text;
	@UiField
	XButton submit;
	@UiField
	XButton newloc;
	@UiField
	Label counter;
	@UiField
	Label location;
	@UiField
	Label loading;
	@UiField
	LayoutPanel searchPanel;

	ICallback backCallback;
	ICallback nextCallback;
	ICallback callbackNewLocation;
	ITypedCallback<XVisibility> callbackVisibility;
	ITypedCallback<String> callbackLocation;
	ITypedCallback<XLocationEntry> callbackSubmit;

	XVisibility type;
	XLocation locationModel;
	boolean search = true;

	public CreateBox() {
		initWidget(uiBinder.createAndBindUi(this));

		for (XVisibility vi : XVisibility.values()) {
			visibility.addItem(vi.name().toLowerCase());
		}

		loading.setVisible(false);
		locations.setVisible(false);
		back.setVisible(false);
		searchPanel.setVisible(false);

		counter.setText(COUNT.toString());
		submit.setText(SUBMIT);
		newloc.setText(NEW_LOC);
		newloc.setPixelSize(90, 25);

		locations.setGenerator(new IGenerator<XLocation>() {

			@Override
			public Widget generateWidget(XLocation model) {
				Label lbl = new Label(model.getName());
				return lbl;
			}
		});
	}

	public void setCallbackNext(ICallback callback) {
		nextCallback = callback;
	}

	public void setCallbackBack(ICallback callback) {
		backCallback = callback;
	}

	public void setCallbackVisibility(ITypedCallback<XVisibility> callback) {
		callbackVisibility = callback;
	}

	public void setCallbackLocation(ITypedCallback<String> callback) {
		callbackLocation = callback;
	}

	public void setCallbackNewLocation(ICallback callback) {
		callbackNewLocation = callback;
	}

	public void setCallbackSubmit(ITypedCallback<XLocationEntry> callback) {
		callbackSubmit = callback;
	}

	public void setVisibility(XVisibility value) {
		type = value;
		visibility.setSelectedIndex(type.getEnumIndex());
	}

	public void setStore(List<XLocation> store) {
		if (store.size() < 5) {
			next.setVisible(false);
		} else {
			next.setVisible(true);
		}
		locations.setVisible(true);
		locations.setStore(store);
		loading.setVisible(false);
	}

	private void doAnalyze() {
		String[] analyse = TextProcessingEngine.splitString(text.getText()
				.toLowerCase());
		TextProcessingEngine.setActualStringArray(analyse);

		String place = TextProcessingEngine.doAnalyzePlace();

		if ((place != null) && !place.isEmpty()) {
			location.setText(place);
			if ((callbackLocation != null) && search) {
				searchPanel.setVisible(true);
				locations.setVisible(true);
				loading.setVisible(true);
				callbackLocation.execute(place);
			}
		} else {
			location.setText("");
		}
	}

	private XLocationEntry getLocationEntry() {
		XLocationEntry entry = new XLocationEntry();
		entry.setLocation(locationModel);
		entry.setLocationName(location.getText());
		entry.setText(text.getText());
		return entry;
	}

	@UiHandler("back")
	void onBackClick(ClickEvent event) {
		if (backCallback != null) {
			back.setVisible(false);
			next.setVisible(true);
			backCallback.execute();
		}
	}

	@UiHandler("next")
	void onNextClick(ClickEvent event) {
		if (nextCallback != null) {
			back.setVisible(true);
			next.setVisible(false);
			nextCallback.execute();
		}
	}

	@UiHandler("submit")
	void onSubmitClick(ClickEvent event) {
		if (callbackSubmit != null) {
			callbackSubmit.execute(getLocationEntry());
			searchPanel.setVisible(false);
			counter.setText(COUNT.toString());
			text.clear();
		}
	}

	@UiHandler("newloc")
	void onNewLocClick(ClickEvent event) {
		if (callbackNewLocation != null) {
			callbackNewLocation.execute();
		}
	}

	@UiHandler("visibility")
	void onVisibilityChange(ChangeEvent event) {
		String typeStr = visibility.getValue(visibility.getSelectedIndex());
		type = XVisibility.getEnumValue(typeStr);
		if (callbackVisibility != null) {
			callbackVisibility.execute(type);
		}
	}

	@UiHandler("text")
	void onTextKeyUp(KeyUpEvent event) {
		doAnalyze();
		Integer count = COUNT - text.getText().length();
		if (counter != null) {
			counter.setText(count.toString());
		}
		if ((event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE)
				&& (locationModel != null)
				&& !(location.getText().equals(locationModel.getName()))) {
			search = true;
		}
	}
}
