package webapp.client.view.xevent;

import java.util.List;
import java.util.Map;

import webapp.client.callback.ICallback;
import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.xevent.EventCreatorWidgetPresenter;
import webapp.client.view.TextProcessingEngine;
import webapp.client.view.customwidget.SlideInAnimation;
import webapp.client.view.customwidget.SlideOutAnimation;
import webapp.client.view.customwidget.XButtonSmall;
import webapp.client.view.customwidget.XTextAreaOld;
import webapp.model.XEvent;
import webapp.model.XLocation;
import webapp.model.XLocationEntry;
import webapp.model.enums.CreatorType;
import webapp.model.enums.XVisibility;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for XEvent creator, implements the interface declared in presenter
 * 
 * @author David Pichsenmeister
 */
public class EventCreatorWidgetV2 extends ViewImpl implements
		EventCreatorWidgetPresenter.IView {

	public static final int WIDTH = 500;
	public static Integer COUNT = 120;
	public static final int WIDTH_FIELD = 400;
	public static final int TIME = 2000;
	public static final String TIME_FORMAT = "dd.MM.yyyy";
	public static final String STYLE = "event-table";
	public static final String STYLE_ACTIVE = "event-field-active";
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
	public static final String STYLE_COUNTER = "counter";
	public static final String HASH = "#";
	public static final String AT = "@";
	public static final String PENCIL = "pencil";

	private static final long CALC_HOUR = 3600000;
	private static final long CALC_MINUTE = 60000;

	private AbsolutePanel mainPanel_;
	private XTextAreaOld textArea_;
	private TextBox title_;
	private TextBox place_;
	private DateBox begin_;
	private TextArea description_;
	private ListBox timeBeginHour_;
	private ListBox timeBeginMin_;
	private StringBuilder analyze_;
	private XButtonSmall submit_;
	private Label counter;
	private FlowPanel placePanel_;

	private ITypedCallback<CreatorType> callback_;
	private ITypedCallback<String> callbackLocation_;
	private ICallback callbackCreateLocation_;

	/**
	 * constructor, contains the view
	 */
	public EventCreatorWidgetV2() {
		mainPanel_ = new AbsolutePanel();
		mainPanel_.getElement().getStyle().setOverflowX(Overflow.VISIBLE);
		mainPanel_.getElement().getStyle().setOverflowY(Overflow.VISIBLE);

		analyze_ = new StringBuilder();

		textArea_ = new XTextAreaOld();
		mainPanel_.add(textArea_);
		textArea_.setWidth(WIDTH + "px");
		textArea_.setText(TEXT_DEFAULT);

		placePanel_ = new FlowPanel();
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

		final AbsolutePanel animat = new AbsolutePanel();
		animat.setWidth(WIDTH + "px");
		animat.setHeight(0 + "px");
		animat.getElement().getStyle().setMargin(5, Unit.PX);
		animat.getElement().getStyle().setPadding(15, Unit.PX);
		mainPanel_.add(animat);

		FlexTable table = new FlexTable();
		table.setCellSpacing(0);
		// TODO not visible for development
		// animat.add(table);
		table.setStyleName(STYLE);

		Label title = new Label(TITLE + "  " + HASH);
		table.setWidget(0, 0, title);
		table.getCellFormatter().setHeight(0, 0, "10px");
		title_ = new TextBox();
		title_.setWidth(WIDTH_FIELD + "px");
		table.setWidget(0, 1, title_);
		Label editTitle = new Label();
		editTitle.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				title_.setEnabled(true);
			}
		});
		editTitle.setStyleName(PENCIL);
		table.setWidget(0, 2, editTitle);

		Label place = new Label(PLACE + "  " + AT);
		table.setWidget(1, 0, place);
		place_ = new TextBox();
		place_.setWidth(WIDTH_FIELD + "px");
		table.setWidget(1, 1, place_);
		Label editPlace = new Label();
		editPlace.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				place_.setEnabled(true);
			}
		});
		editPlace.setStyleName(PENCIL);
		table.setWidget(1, 2, editPlace);

		DateTimeFormat dateFormat = DateTimeFormat.getFormat(TIME_FORMAT);

		Label begin = new Label(BEGIN);
		table.setWidget(2, 0, begin);
		begin_ = new DateBox();
		begin_.setFormat(new DateBox.DefaultFormat(dateFormat));

		table.setWidget(2, 1, begin_);

		timeBeginHour_ = new ListBox();
		for (int i = 0; i < 24; i++) {
			if (i < 10) {
				timeBeginHour_.addItem("0" + i);
			} else {
				timeBeginHour_.addItem(new Integer(i).toString());
			}
		}
		table.setWidget(2, 2, timeBeginHour_);

		timeBeginMin_ = new ListBox();
		for (int i = 0; i < 60; i = i + 5) {
			if (i < 10) {
				timeBeginMin_.addItem("0" + i);
			} else {
				timeBeginMin_.addItem(new Integer(i).toString());
			}
		}

		table.setWidget(2, 3, timeBeginMin_);

		Label editBegin = new Label();
		editBegin.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				begin_.setEnabled(true);
				timeBeginHour_.setEnabled(true);
				timeBeginMin_.setEnabled(true);
			}
		});
		editBegin.setStyleName(PENCIL);
		table.setWidget(2, 4, editBegin);

		Label desc = new Label(DESCR);
		table.setWidget(3, 0, desc);
		desc.setHeight("");
		table.getCellFormatter().setWidth(3, 0, "80px");
		description_ = new TextArea();
		description_.setWidth(WIDTH_FIELD + "px");
		description_.setHeight("40px");
		table.setWidget(3, 1, description_);
		Label editDesc = new Label();
		editDesc.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				description_.setEnabled(true);
			}
		});
		editDesc.setStyleName(PENCIL);
		table.setWidget(3, 2, editDesc);

		Label close = new Label("^");
		close.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SlideOutAnimation anim = new SlideOutAnimation();
				anim.setStartHeight(140);
				anim.setWidget(animat);
				anim.run(500);
			}
		});
		table.setWidget(4, 4, close);

		table.getFlexCellFormatter().setColSpan(0, 1, 3);
		table.getFlexCellFormatter().setColSpan(1, 1, 3);
		table.getFlexCellFormatter().setColSpan(3, 1, 3);
		table.getCellFormatter().setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_CENTER);
		table.getCellFormatter().setVerticalAlignment(0, 1,
				HasVerticalAlignment.ALIGN_MIDDLE);
		table.getCellFormatter().setHorizontalAlignment(1, 1,
				HasHorizontalAlignment.ALIGN_CENTER);
		table.getCellFormatter().setVerticalAlignment(1, 1,
				HasVerticalAlignment.ALIGN_MIDDLE);
		table.getCellFormatter().setHorizontalAlignment(3, 1,
				HasHorizontalAlignment.ALIGN_CENTER);
		table.getCellFormatter().setVerticalAlignment(3, 1,
				HasVerticalAlignment.ALIGN_MIDDLE);
		table.getCellFormatter().setHorizontalAlignment(2, 1,
				HasHorizontalAlignment.ALIGN_CENTER);
		table.getCellFormatter().setVerticalAlignment(2, 1,
				HasVerticalAlignment.ALIGN_MIDDLE);
		table.getCellFormatter().setHorizontalAlignment(2, 2,
				HasHorizontalAlignment.ALIGN_CENTER);
		table.getCellFormatter().setVerticalAlignment(2, 2,
				HasVerticalAlignment.ALIGN_MIDDLE);
		table.getCellFormatter().setVerticalAlignment(2, 3,
				HasVerticalAlignment.ALIGN_MIDDLE);
		table.getCellFormatter().setHorizontalAlignment(2, 3,
				HasHorizontalAlignment.ALIGN_CENTER);

		table.setHeight(0 + "px");

		counter = new Label();
		mainPanel_.add(counter);
		counter.setStyleName("counter");
		counter.setText(COUNT.toString());
		counter.setVisible(false);

		submit_ = new XButtonSmall(SUBMIT);
		mainPanel_.add(submit_);
		submit_.getElement().getStyle().setMarginLeft(15, Unit.PX);
		submit_.getElement().getStyle().setMarginTop(5, Unit.PX);
		submit_.setVisible(false);

		title_.setEnabled(false);
		place_.setEnabled(false);
		begin_.setEnabled(false);
		timeBeginHour_.setEnabled(false);
		timeBeginMin_.setEnabled(false);
		description_.setEnabled(false);

		textArea_.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				analyze_.delete(0, analyze_.length());
				analyze_.append(textArea_.getText());
			}
		});

		textArea_.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				// if ((KeyCodes.KEY_BACKSPACE == event.getNativeKeyCode())
				// || (KeyCodes.KEY_DELETE == event.getNativeKeyCode())) {
				// analyze_.delete(0, analyze_.length());
				// analyze_.append(textArea_.getText());
				// doAnalyze();
				// } else if (event.getNativeKeyCode() == 32) { // 32 = space
				// analyze_.delete(0, analyze_.length());
				// analyze_.append(textArea_.getText());
				// doAnalyze();
				// }
				doAnalyze();
				Integer count = COUNT - textArea_.getText().length();
				if (counter != null) {
					if (count < 0) {
						counter.getElement().getStyle().setColor("#000");
					} else {
						counter.getElement().getStyle().setColor("#666");
					}
					counter.setText(count.toString());
				}
			}
		});

		textArea_.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				if (textArea_.getText().equals(TEXT_DEFAULT)) {
					textArea_.setText("");
				}
				if (animat.getElement().getStyle().getHeight().equals(0 + "px")) {
					SlideInAnimation anim = new SlideInAnimation();
					anim.setStartHeight(0);
					anim.setFinishHeight(140);
					anim.setWidget(animat);
					anim.run(500);
				}
				submit_.setVisible(true);
				counter.setVisible(true);
			}
		});

		textArea_.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				if (textArea_.getText().equals("")) {
					textArea_.setText(TEXT_DEFAULT);
				} else {
					// doAnalyze();
				}
			}
		});

		submit_.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (callback_ != null) {
					textArea_.setHeight(40 + "px");
					submit_.setVisible(false);
					counter.setVisible(false);
					SlideOutAnimation anim = new SlideOutAnimation();
					anim.setStartHeight(140);
					anim.setWidget(animat);
					anim.run(500);
					callback_.execute(CreatorType.EVENT);
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
		// event.setEventTitle(title_.getText());
		event.setPlace(place_.getText());

		long hour = new Long(timeBeginHour_.getValue(timeBeginHour_
				.getSelectedIndex())) * CALC_HOUR;
		long minute = new Long(timeBeginMin_.getValue(timeBeginMin_
				.getSelectedIndex())) * CALC_MINUTE;

		long begin = begin_.getValue().getTime() + hour + minute;
		event.setEventBegin(begin);
		String description = description_.getText();
		if (description.length() > 120) {
			description = description.substring(0, 120);
		}
		event.setDescription(description);

		return event;
	}

	private void doAnalyze() {
		String[] analyse = TextProcessingEngine.splitString(analyze_.toString()
				.toLowerCase());
		TextProcessingEngine.setActualStringArray(analyse);

		String place = TextProcessingEngine.doAnalyzePlace();

		// title_.setText(TextProcessingEngine.doAnalyzeTitle());
		place_.setText(place);
		begin_.setValue(TextProcessingEngine.doAnalyzeDate());

		Map<String, Integer> map = TextProcessingEngine.doAnalyzeTime();
		if (map != null) {
			if (map.get(HOUR) != null) {
				timeBeginHour_.setItemSelected(map.get(HOUR), true);
			}
			if (map.get(MINUTE) != null) {
				timeBeginMin_.setItemSelected(map.get(MINUTE), true);
			} else {
				timeBeginMin_.setItemSelected(0, true);
			}
		}

		description_.setText(analyze_.toString().toLowerCase());

		if (place != null && !place.isEmpty() && callbackLocation_ != null) {
			callbackLocation_.execute(place);
			placePanel_.add(new Label("loading locations ..."));
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
		for (XLocation loc : locations) {
			HorizontalPanel panel = new HorizontalPanel();
			panel.add(new Label(loc.getName()));
			panel.add(new Label(loc.getCity()));
			panel.add(new Label(loc.getAddress()));
			placePanel_.add(panel);
		}
	}

	@Override
	public void setCallbackNewLocation(ICallback callback) {
		callbackCreateLocation_ = callback;
	}

	@Override
	public XLocationEntry getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCallbackVisibility(ITypedCallback<XVisibility> callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVisibility(XVisibility type) {
		// TODO Auto-generated method stub

	}
}
