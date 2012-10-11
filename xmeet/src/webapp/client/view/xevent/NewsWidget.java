package webapp.client.view.xevent;

import java.util.Date;
import java.util.List;

import webapp.client.callback.ICallbackEvent;
import webapp.client.presenter.xevent.NewsWidgetPresenter;
import webapp.client.presenter.xevent.NewsWidgetPresenter.ICallbackPostingSave;
import webapp.client.view.customwidget.SlideOutAnimation;
import webapp.client.view.customwidget.XButtonRound;
import webapp.client.view.customwidget.XButtonSmall;
import webapp.client.view.qr.ICalender;
import webapp.model.XEvent;
import webapp.model.XPosting;
import webapp.model.XUser;
import webapp.model.enums.XEventStatus;

import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.ImageChart;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for news, implements the interface declared in presenter
 * 
 * @author David Pichsenmeister
 */
public class NewsWidget extends ViewImpl implements NewsWidgetPresenter.IView {

	public static final String TIME_FORMAT = "HH:mm";
	public static final String DATE_FORMAT = "dd.MM.yyyy";
	public static final String EMPTY = "";

	public static final int POST_WIDTH = 300;
	public static final int IMAGE_SIZE = 32;

	public static final String COMMENT_TEXT_DEFAULT = "poste ein kommentar...";
	public static final String COMMENT_SHOW = "kommentare";
	public static final String COMMENT_SUBMIT = "bet\u00E4tige die eingabetaste, um dein kommentar zu posten.";
	public static final String NO_EVENTS = "keine neuen events vorhanden";
	public static final String LOADING = "loading";
	public static final String PLUS = "plus";
	public static final String PLUS_SIGN = "+";
	public static final String PLUS_MSG = "du nimmst teil";
	public static final String TILDE = "tilde";
	public static final String TILDE_SIGN = "~";
	public static final String TILDE_MSG = "du nimmst vielleicht teil";
	public static final String MINUS = "minus";
	public static final String MINUS_SIGN = "-";
	public static final String MINUS_MSG = "du nimmst nicht teil";

	public static final String ID_EVENT = "xevent-";
	public static final String ID_POSTINGS = "postings-";
	public static final String STYLE_EVENT = "xevent";
	public static final String STYLE_EVENT_IMAGE = "xevent-image";
	public static final String STYLE_EVENT_USER_HOVER = "xevent-user-hover";
	public static final String STYLE_EVENT_USER = "xevent-user";
	public static final String STYLE_EVENT_NAME = "xevent-name";
	public static final String STYLE_EVENT_TITLE = "xevent-title";
	public static final String STYLE_EVENT_PLACE = "xevent-place";
	public static final String STYLE_EVENT_DATE = "xevent-date";
	public static final String STYLE_EVENT_DATE_DATE = "xevent-date-date";
	public static final String STYLE_EVENT_DESC = "xevent-description";
	public static final String STYLE_EVENT_BUTTON = "xevent-button";
	public static final String STYLE_EVENT_STATUS = "xevent-status";
	public static final String STYLE_POSTING_HEADER = "posting-header";
	public static final String STYLE_POSTING_BOX = "posting-box";
	public static final String STYLE_STATUS_TEXT = "status-text";
	public static final String STYLE_SINGLE_POST = "single-post";
	public static final String STYLE_SINGLE_POST_DATE = "single-post-date";
	public static final String STYLE_CLOSE = "close";
	public static final String STYLE_QR = "qr";
	public static final String STYLE_NO_EVENTS = "no-events";

	private FlowPanel mainPanel_;
	private FlowPanel news_;
	private SimplePanel content_;
	private ICallbackPostingSave callbackSave_;
	private ICallbackEvent callbackEvent_;
	private XUser loggedUser_;

	/**
	 * constructor, contains the view
	 */
	public NewsWidget() {
		mainPanel_ = new FlowPanel();

		content_ = new SimplePanel();
		mainPanel_.add(content_);

		news_ = new FlowPanel();

		final Label loading = new Label();
		loading.setStyleName(LOADING);

		content_.setWidget(loading);
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}

	@Override
	public void setEvents(List<XEvent> events) {
		boolean close = false;
		if ((events != null) && (events.size() > 0)) {
			news_.clear();
			content_.setWidget(news_);
			for (XEvent event : events) {
				AbsolutePanel eventPanel = paintEvent(event, close);
				DisclosurePanel postings = paintPostings(event);
				eventPanel.add(postings);
				if (event.getXPostings().size() == 0) {
					postings.setVisible(false);
				}
				news_.add(eventPanel);
			}
		} else {
			Label noevents = new Label(NO_EVENTS);
			noevents.setStyleName(STYLE_NO_EVENTS);
			content_.setWidget(noevents);
		}
	}

	@Override
	public void updateXPostings(XEvent event) {
		FlowPanel content = (FlowPanel) content_.getWidget();
		for (int i = 0; i < content.getWidgetCount(); i++) {
			try {
				AbsolutePanel widget = (AbsolutePanel) content.getWidget(i);
				if (widget.getElement().getId().equals(ID_EVENT + event.getEventID())) {
					for (int w = 0; w < widget.getWidgetCount(); w++) {
						if (widget.getWidget(w).getElement().getId().equals(ID_POSTINGS + event.getEventID())) {
							DisclosurePanel postPanel = (DisclosurePanel) widget.getWidget(w);
							postPanel.setVisible(true);
							Label header = new Label(COMMENT_SHOW + " (" + event.getXPostings().size() + ")");
							header.setStyleName(STYLE_POSTING_HEADER);
							postPanel.setHeader(header);
							postPanel.setContent(paintPostingsContent(event));
							postPanel.setOpen(true);
							return;
						}
					}
				}
			} catch (Exception e) {

			}
		}
	}

	@Override
	public void setCallbackEvent(ICallbackEvent callback) {
		callbackEvent_ = callback;
	}

	@Override
	public void updateXEvent(XEvent event, XEventStatus status) {
		FlowPanel content = (FlowPanel) content_.getWidget();
		for (int i = 0; i < content.getWidgetCount(); i++) {
			try {
				AbsolutePanel widget = (AbsolutePanel) content.getWidget(i);
				if (widget.getElement().getId().equals(ID_EVENT + event.getEventID())) {
					for (int w = 0; w < widget.getWidgetCount(); w++) {
						if (widget.getWidget(w).getElement().getId().equals(ID_EVENT + event.getEventID() + "-" + PLUS)) {
							XButtonRound button = (XButtonRound) widget.getWidget(w);
							if (status == XEventStatus.JOIN) {
								button.setEnabled(false);
							} else {
								button.setEnabled(true);
							}
						}
						if (widget.getWidget(w).getElement().getId()
								.equals(ID_EVENT + event.getEventID() + "-" + TILDE)) {
							XButtonRound button = (XButtonRound) widget.getWidget(w);
							if (status == XEventStatus.MAYBE) {
								button.setEnabled(false);
							} else {
								button.setEnabled(true);
							}
						}
						if (widget.getWidget(w).getElement().getId()
								.equals(ID_EVENT + event.getEventID() + "-" + MINUS)) {
							XButtonRound button = (XButtonRound) widget.getWidget(w);
							if (status == XEventStatus.DECLINE) {
								button.setEnabled(false);
								SlideOutAnimation slide = new SlideOutAnimation();
								slide.setWidget(widget);
								slide.setStartHeight(widget.getOffsetHeight());
								slide.removeFromParent(true);
								slide.run(700);
							} else {
								button.setEnabled(true);
							}
						}
						if (widget.getWidget(w).getElement().getId()
								.equals(ID_EVENT + event.getEventID() + "-" + STYLE_STATUS_TEXT)) {
							Label statusText = (Label) widget.getWidget(w);
							switch (status) {
							case JOIN:
								statusText.setText(PLUS_MSG);
								break;
							case MAYBE:
								statusText.setText(TILDE_MSG);
								break;
							case DECLINE:
								statusText.setText(MINUS_MSG);
								break;
							}
						}
					}
				}
			} catch (Exception e) {

			}
		}
	}

	@Override
	public void setCallbackSave(ICallbackPostingSave callback) {
		callbackSave_ = callback;
	}

	private AbsolutePanel paintEvent(final XEvent xevent, boolean closeAble) {
		XUser owner = xevent.getOwner();
		final AbsolutePanel eventPanel = new AbsolutePanel();
		eventPanel.setStyleName(STYLE_EVENT);
		eventPanel.getElement().setId(ID_EVENT + xevent.getEventID());

		Image image = new Image();
		image.setStyleName(STYLE_EVENT_IMAGE);
		image.setAltText(owner.getUserName());
		image.setUrl(owner.getImage().getMediaURL());
		eventPanel.add(image);

		FlowPanel hover = new FlowPanel();
		hover.setStyleName(STYLE_EVENT_USER_HOVER);
		eventPanel.add(hover);

		Label username = new Label(owner.getUserName());
		username.setStyleName(STYLE_EVENT_USER);
		hover.add(username);

		Label name = new Label(owner.getName());
		name.setStyleName(STYLE_EVENT_NAME);
		hover.add(name);

		FlowPanel header = new FlowPanel();
		header.getElement().getStyle().setMarginTop(5, Unit.PX);
		eventPanel.add(header);
		for (String entry : xevent.getEventTitle()) {
			Label title = new Label(entry);
			title.setStyleName(STYLE_EVENT_TITLE);
			header.add(title);
		}
		Label place = new Label(xevent.getPlace());
		place.setStyleName(STYLE_EVENT_PLACE);
		header.add(place);

		FlowPanel datePanel = new FlowPanel();
		datePanel.setStyleName(STYLE_EVENT_DATE);
		eventPanel.add(datePanel);
		DateTimeFormat formatDate = DateTimeFormat.getFormat(DATE_FORMAT);
		Date date = new Date(xevent.getEventBegin());
		Label dateLabel = new Label(formatDate.format(date));
		dateLabel.setStyleName(STYLE_EVENT_DATE_DATE);
		datePanel.add(dateLabel);
		DateTimeFormat formatTime = DateTimeFormat.getFormat(TIME_FORMAT);
		Date time = new Date(xevent.getEventBegin());
		datePanel.add(new Label(formatTime.format(time)));

		final Label description = new Label(xevent.getDescription());
		description.setStyleName(STYLE_EVENT_DESC);
		eventPanel.add(description);

		if (closeAble) {
			Label close = new Label("x");
			eventPanel.add(close);
			close.setStyleName(STYLE_CLOSE);
			close.getElement().getStyle().setTop(5, Unit.PX);
			close.getElement().getStyle().setRight(5, Unit.PX);
			close.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					SlideOutAnimation out = new SlideOutAnimation();
					out.setStartHeight(eventPanel.getOffsetHeight() - 10);
					out.setWidget(eventPanel);
					out.removeFromParent(true);
					out.run(700);
					if (callbackEvent_ != null) {
						callbackEvent_.execute(xevent, XEventStatus.CLOSE);
					}
				}
			});
		}

		paintSubscriberPanel(eventPanel, xevent);

		paintQRCode(eventPanel, xevent);

		paintUserPostingPanel(eventPanel, xevent);

		return eventPanel;
	}

	private void paintSubscriberPanel(AbsolutePanel eventPanel, final XEvent xevent) {
		XButtonSmall plus = new XButtonSmall(PLUS_SIGN);
		plus.setStyleName(STYLE_EVENT_BUTTON);
		eventPanel.add(plus);
		plus.getElement().setId(ID_EVENT + xevent.getEventID() + "-" + PLUS);
		plus.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (callbackEvent_ != null) {
					callbackEvent_.execute(xevent, XEventStatus.JOIN);
				}
			}
		});
		XButtonSmall tilde = new XButtonSmall(TILDE_SIGN);
		tilde.setStyleName(STYLE_EVENT_BUTTON);
		eventPanel.add(tilde);
		tilde.getElement().setId(ID_EVENT + xevent.getEventID() + "-" + TILDE);
		tilde.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (callbackEvent_ != null) {
					callbackEvent_.execute(xevent, XEventStatus.MAYBE);
				}
			}
		});
		XButtonSmall minus = new XButtonSmall(MINUS_SIGN);
		minus.setStyleName(STYLE_EVENT_BUTTON);
		eventPanel.add(minus);
		minus.getElement().setId(ID_EVENT + xevent.getEventID() + "-" + MINUS);
		minus.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (callbackEvent_ != null) {
					callbackEvent_.execute(xevent, XEventStatus.DECLINE);
				}
			}
		});

		Label statusText = new Label();
		statusText.setStyleName(STYLE_EVENT_STATUS);
		statusText.getElement().setId(ID_EVENT + xevent.getEventID() + "-" + STYLE_STATUS_TEXT);
		eventPanel.add(statusText);
	}

	private void paintQRCode(AbsolutePanel eventPanel, final XEvent event) {
		final SimplePanel panel = new SimplePanel();
		panel.setStyleName(STYLE_QR);
		eventPanel.add(panel);
		Runnable onLoadCallback = new Runnable() {
			@Override
			public void run() {
				ICalender ical = new ICalender(event);
				panel.setWidget(ical.getPanel());
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, ImageChart.PACKAGE);
	}

	private void paintUserPostingPanel(AbsolutePanel eventPanel, final XEvent xevent) {
		FlowPanel postingPanel = new FlowPanel();
		eventPanel.add(postingPanel);
		final Image imagePosting = new Image(loggedUser_.getImage().getMediaURL());
		imagePosting.setSize(IMAGE_SIZE + "px", IMAGE_SIZE + "px");
		imagePosting.getElement().getStyle().setFloat(Float.LEFT);
		imagePosting.getElement().getStyle().setMarginRight(5, Unit.PX);
		imagePosting.setVisible(false);
		postingPanel.add(imagePosting);

		final TextBox posting = new TextBox();
		postingPanel.add(posting);
		posting.setStyleName(STYLE_POSTING_BOX);
		posting.setText(COMMENT_TEXT_DEFAULT);

		final Label info = new Label(COMMENT_SUBMIT);
		info.getElement().getStyle().setFontSize(10, Unit.PX);
		info.getElement().getStyle().setMarginTop(3, Unit.PX);
		info.getElement().getStyle().setColor("#888");
		info.setVisible(false);
		postingPanel.add(info);

		posting.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				if (posting.getText().equals(COMMENT_TEXT_DEFAULT)) {
					posting.setText(EMPTY);
					info.setVisible(true);
					imagePosting.setVisible(true);
				}
			}
		});
		posting.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				if (posting.getText().equals(EMPTY)) {
					posting.setText(COMMENT_TEXT_DEFAULT);
					info.setVisible(false);
					imagePosting.setVisible(false);
				}
			}
		});
		posting.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if ((event.getNativeKeyCode() == KeyCodes.KEY_ENTER) && (callbackSave_ != null)) {
					callbackSave_.execute(xevent, posting.getText());
					posting.setText(COMMENT_TEXT_DEFAULT);
					info.setVisible(false);
					imagePosting.setVisible(false);
				}
			}
		});
	}

	private DisclosurePanel paintPostings(XEvent xevent) {
		DisclosurePanel postPanel = new DisclosurePanel();
		postPanel.setWidth("100%");
		postPanel.getElement().getStyle().setMarginTop(10, Unit.PX);
		postPanel.getElement().setId(ID_POSTINGS + xevent.getEventID());
		postPanel.setAnimationEnabled(true);
		Label header = new Label(COMMENT_SHOW + " (" + xevent.getXPostings().size() + ")");
		header.setStyleName(STYLE_POSTING_HEADER);
		postPanel.setHeader(header);

		postPanel.setContent(paintPostingsContent(xevent));

		return postPanel;
	}

	private AbsolutePanel paintPostingsContent(XEvent xevent) {
		AbsolutePanel postings = new AbsolutePanel();
		postings.getElement().getStyle().setMarginLeft(15, Unit.PX);
		postings.getElement().getStyle().setFontSize(11, Unit.PX);

		for (XPosting post : xevent.getXPostings()) {
			FlowPanel singlePost = new FlowPanel();
			singlePost.setStyleName(STYLE_SINGLE_POST);

			Image image = new Image(post.getOwner().getImage().getMediaURL());
			image.setSize(IMAGE_SIZE + "px", IMAGE_SIZE + "px");
			image.getElement().getStyle().setFloat(Float.LEFT);
			image.getElement().getStyle().setMarginRight(5, Unit.PX);
			singlePost.add(image);

			Label username = new Label(post.getOwner().getUserName());
			username.getElement().getStyle().setFontWeight(FontWeight.BOLD);
			username.getElement().getStyle().setFloat(Float.LEFT);
			username.getElement().getStyle().setMarginRight(5, Unit.PX);
			singlePost.add(username);

			Label text = new Label(post.getText());
			singlePost.add(text);

			DateTimeFormat formatDate = DateTimeFormat.getFormat(DATE_FORMAT);
			Date date = new Date(xevent.getEventBegin());
			Label dateLabel = new Label(formatDate.format(date));
			dateLabel.getElement().getStyle().setFloat(Float.LEFT);
			dateLabel.setStyleName(STYLE_SINGLE_POST_DATE);
			singlePost.add(dateLabel);
			DateTimeFormat formatTime = DateTimeFormat.getFormat(TIME_FORMAT);
			Date time = new Date(xevent.getEventBegin());
			Label timeLabel = new Label(formatTime.format(time));
			timeLabel.setStyleName(STYLE_SINGLE_POST_DATE);
			singlePost.add(timeLabel);

			postings.add(singlePost);
		}
		return postings;
	}

	@Override
	public void setLoggedUser(XUser user) {
		loggedUser_ = user;
	}
}
