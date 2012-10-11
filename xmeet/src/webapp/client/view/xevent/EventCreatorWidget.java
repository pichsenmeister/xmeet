package webapp.client.view.xevent;

import java.util.Date;
import java.util.List;
import java.util.Map;

import webapp.client.callback.ICallback;
import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.xevent.EventCreatorWidgetPresenter;
import webapp.client.view.TextProcessingEngine;
import webapp.client.view.customwidget.ListView;
import webapp.client.view.customwidget.ListView.IGenerator;
import webapp.client.view.customwidget.XButton;
import webapp.client.view.customwidget.XTextArea;
import webapp.model.XEvent;
import webapp.model.XLocation;
import webapp.model.XLocationEntry;
import webapp.model.enums.CreatorType;
import webapp.model.enums.XVisibility;
import webapp.model.helper.XTime;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for XEvent creator, implements the interface declared in presenter
 * 
 * @author David Pichsenmeister
 */
public class EventCreatorWidget extends ViewImpl implements EventCreatorWidgetPresenter.IView {

	public static final String TITLE = "titel";
	public static final String PLACE = "ort";
	public static final String BEGIN = "beginn";
	public static final String END = "end";
	public static final String DESCR = "info";
	public static final String HOUR = "hour";
	public static final String MINUTE = "minute";
	public static final String TEXT_DEFAULT = "#titel morgen um 15 uhr @ort";
	public static final String TEXT_CREATE_LOCATION = "erstelle eine neue location";
	public static final String SUBMIT = "erstellen";

	private static final int WIDTH = 500;
	private static Integer COUNT = 120;
	private static final int WIDTH_FIELD = 400;
	private static final String STYLE_COUNTER = "counter";

	private final AbsolutePanel mainPanel_;
	private final XTextArea textArea_;
	private final TextBox title_;
	private final Label lblTitle_;
	private final TextBox place_;
	private final Label lblPlace_;
	private final DateBox begin_;
	private final Label lblDate_;
	private final ListBox timeBeginHour_;
	private final ListBox timeBeginMin_;
	private final StringBuilder analyze_;
	private final XButton submit_;
	private final Label counter_;
	private final ListView<XLocation> placePanel_;
	private final ListBox typeBox_;

	private ITypedCallback<CreatorType> callback_;
	private ITypedCallback<String> callbackLocation_;
	private ICallback callbackCreateLocation_;
	private ITypedCallback<XVisibility> callbackVisibility_;
	private XVisibility type_;
	private XLocation location_;
	private boolean searchLocations_ = true;

	/**
	 * constructor, contains the view
	 */
	public EventCreatorWidget() {
		mainPanel_ = new AbsolutePanel();
		mainPanel_.getElement().getStyle().setOverflowX(Overflow.VISIBLE);
		mainPanel_.getElement().getStyle().setOverflowY(Overflow.VISIBLE);

		analyze_ = new StringBuilder();

		textArea_ = new XTextArea();
		mainPanel_.add(textArea_);
		textArea_.setWidth(WIDTH + "px");

		placePanel_ = new ListView<XLocation>();
		placePanel_.setGenerator(new IGenerator<XLocation>() {

			@Override
			public Widget generateWidget(final XLocation model) {
				HorizontalPanel panel = new HorizontalPanel();
				panel.add(new Label(model.getName()));
				panel.add(new Label(model.getCity()));
				panel.add(new Label(model.getAddress()));
				panel.addDomHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						location_ = model;
						place_.setText(model.getName());
						String value = textArea_.getText();
						value = value.replaceFirst("@\\w*", model.getName());
						textArea_.setText(value);
						searchLocations_ = false;
					}
				}, ClickEvent.getType());
				return panel;
			}
		});
		mainPanel_.add(placePanel_);

		FlowPanel createLocationPanel = new FlowPanel();
		mainPanel_.add(createLocationPanel);
		Label newLoc = new Label(TEXT_CREATE_LOCATION);
		createLocationPanel.add(newLoc);
		newLoc.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (callbackCreateLocation_ != null) {
					callbackCreateLocation_.execute();
				}
			}
		});

		final AbsolutePanel data = new AbsolutePanel();
		data.setWidth(WIDTH + "px");
		data.getElement().getStyle().setMargin(5, Unit.PX);
		data.getElement().getStyle().setPadding(15, Unit.PX);
		mainPanel_.add(data);

		lblTitle_ = new Label(TITLE);
		data.add(lblTitle_);
		title_ = new TextBox();
		data.add(title_);
		title_.setWidth(WIDTH_FIELD + "px");

		lblPlace_ = new Label(PLACE);
		data.add(lblPlace_);
		place_ = new TextBox();
		data.add(place_);
		place_.setWidth(WIDTH_FIELD + "px");

		DateTimeFormat dateFormat = DateTimeFormat.getFormat(XTime.FORMAT_DMY);

		lblDate_ = new Label(BEGIN);
		data.add(lblDate_);
		begin_ = new DateBox();
		data.add(begin_);
		begin_.setFormat(new DateBox.DefaultFormat(dateFormat));

		timeBeginHour_ = new ListBox();
		data.add(timeBeginHour_);
		for (int i = 0; i < 24; i++) {
			if (i < 10) {
				timeBeginHour_.addItem("0" + i);
			} else {
				timeBeginHour_.addItem(new Integer(i).toString());
			}
		}

		timeBeginMin_ = new ListBox();
		data.add(timeBeginMin_);
		for (int i = 0; i < 60; i = i + 5) {
			if (i < 10) {
				timeBeginMin_.addItem("0" + i);
			} else {
				timeBeginMin_.addItem(new Integer(i).toString());
			}
		}

		typeBox_ = new ListBox();
		for (XVisibility vi : XVisibility.values()) {
			typeBox_.addItem(vi.name().toLowerCase());
		}
		typeBox_.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				String type = typeBox_.getValue(typeBox_.getSelectedIndex());
				type_ = XVisibility.getEnumValue(type);
			}
		});
		mainPanel_.add(typeBox_);
		typeBox_.setVisible(false);

		counter_ = new Label();
		mainPanel_.add(counter_);
		counter_.setStyleName(STYLE_COUNTER);
		counter_.setText(COUNT.toString());
		counter_.getElement().getStyle().setVisibility(Visibility.HIDDEN);

		submit_ = new XButton(SUBMIT);
		mainPanel_.add(submit_);
		submit_.getElement().getStyle().setMarginLeft(15, Unit.PX);
		submit_.getElement().getStyle().setMarginTop(5, Unit.PX);
		submit_.setVisible(false);

		title_.setVisible(false);
		place_.setVisible(false);
		begin_.setVisible(false);
		timeBeginHour_.setVisible(false);
		timeBeginMin_.setVisible(false);
		lblTitle_.setVisible(false);
		lblPlace_.setVisible(false);
		lblDate_.setVisible(false);

		textArea_.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				analyze_.delete(0, analyze_.length());
				analyze_.append(textArea_.getText());
				doAnalyze();
				Integer count = COUNT - textArea_.getText().length();
				if (counter_ != null) {
					if (count < 20) {
						counter_.getElement().getStyle().setVisibility(Visibility.VISIBLE);
					} else {
						counter_.getElement().getStyle().setVisibility(Visibility.HIDDEN);
					}
					counter_.setText(count.toString());
				}
				if ((event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE)
						&& !place_.getText().equals(location_.getName())) {
					searchLocations_ = true;
				}
			}
		});

		textArea_.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				doAnalyze();
			}
		});

		textArea_.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				submit_.setVisible(true);
				typeBox_.setVisible(true);
			}
		});

		submit_.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (callback_ != null) {
					submit_.setVisible(false);
					counter_.setVisible(false);
					typeBox_.setVisible(false);
					data.clear();
					CreatorType type;
					if ((title_.getText() != null) && !title_.getText().isEmpty()) {
						type = CreatorType.EVENT;
					} else if ((place_.getText() != null) && !place_.getText().isEmpty()) {
						type = CreatorType.LOCATION;
					} else {
						type = CreatorType.NONE;
					}
					callback_.execute(type);
				}
			}
		});
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}

	@Override
	public XEvent getEvent() {
		XEvent event = new XEvent();
		String titles = title_.getText().replaceAll(" ", "");
		event.setEventTitle(titles.split(","));
		event.setPlace(place_.getText());
		event.setLocation(location_);

		long hour = new Long(timeBeginHour_.getValue(timeBeginHour_.getSelectedIndex())) * XTime.HOUR_1;
		long minute = new Long(timeBeginMin_.getValue(timeBeginMin_.getSelectedIndex())) * XTime.MIN_1;

		long begin = begin_.getValue().getTime() + hour + minute;
		event.setEventBegin(begin);
		String description = textArea_.getText();
		if (description.length() > 120) {
			description = description.substring(0, 120);
		}
		event.setDescription(description.toLowerCase());
		event.setEventType(type_);

		return event;
	}

	@Override
	public XLocationEntry getLocation() {
		XLocationEntry location = new XLocationEntry();
		location.setLocationName(place_.getText());
		String description = textArea_.getText();
		if (description.length() > 120) {
			description = description.substring(0, 120);
		}
		location.setText(description.toLowerCase());

		return location;
	}

	private void doAnalyze() {
		String[] analyse = TextProcessingEngine.splitString(analyze_.toString().toLowerCase());
		TextProcessingEngine.setActualStringArray(analyse);

		String place = TextProcessingEngine.doAnalyzePlace();
		String[] title = TextProcessingEngine.doAnalyzeTitle();
		Date begin = TextProcessingEngine.doAnalyzeDate();
		Map<String, Integer> map = TextProcessingEngine.doAnalyzeTime();

		if ((title != null) && (title.length > 0)) {
			title_.setVisible(true);
			String text = new String();
			for (String t : title) {
				text += "," + t;
			}
			title_.setText(text);
			lblTitle_.setVisible(true);
		} else {
			title_.setVisible(false);
			title_.setText("");
			lblTitle_.setVisible(false);
		}

		if ((place != null) && !place.isEmpty()) {
			place_.setVisible(true);
			place_.setText(place);
			lblPlace_.setVisible(true);
			if ((callbackLocation_ != null) && searchLocations_) {
				callbackLocation_.execute(place);
			}
			placePanel_.add(new Label("loading locations ..."));
		} else {
			place_.setVisible(false);
			place_.setText("");
			lblPlace_.setVisible(false);
		}

		if (begin != null) {
			begin_.setVisible(true);
			begin_.setValue(begin);
			lblDate_.setVisible(true);
		} else {
			begin_.setVisible(false);
			begin_.setValue(new Date(System.currentTimeMillis()));
			lblDate_.setVisible(false);
		}

		if (map != null) {
			timeBeginHour_.setVisible(true);
			timeBeginMin_.setVisible(true);
			if (map.get(HOUR) != null) {
				timeBeginHour_.setItemSelected(map.get(HOUR), true);
			}
			if (map.get(MINUTE) != null) {
				timeBeginMin_.setItemSelected(map.get(MINUTE), true);
			} else {
				timeBeginMin_.setItemSelected(0, true);
			}
		} else {
			timeBeginHour_.setVisible(false);
			timeBeginMin_.setVisible(false);
		}
	}

	@Override
	public void setCallback(ITypedCallback<CreatorType> callback) {
		callback_ = callback;
	}

	@Override
	public void setCallbackLocation(ITypedCallback<String> callback) {
		callbackLocation_ = callback;
	}

	@Override
	public void setLocations(List<XLocation> locations) {
		placePanel_.clear();
		placePanel_.setStore(locations);
	}

	@Override
	public void setCallbackNewLocation(ICallback callback) {
		callbackCreateLocation_ = callback;
	}

	@Override
	public void setCallbackVisibility(ITypedCallback<XVisibility> callback) {
		callbackVisibility_ = callback;
	}

	@Override
	public void setVisibility(XVisibility type) {
		type_ = type;
		typeBox_.setSelectedIndex(type_.getEnumIndex());
	}
}
