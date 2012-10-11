package webapp.client.view.xevent;

import java.util.List;

import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.xevent.UsersEventsWidgetPresenter;
import webapp.client.presenter.xevent.UsersEventsWidgetPresenter.EnumEventWidget;
import webapp.client.view.customwidget.ListView;
import webapp.client.view.customwidget.ListView.IGenerator;
import webapp.client.view.xevent.eventwidget.EventWidget;
import webapp.model.XEvent;
import webapp.model.XUser;
import webapp.model.XUserEvent;
import webapp.model.enums.XEventStatus;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for user events, implements the interface declared in presenter
 * 
 * @author David Pichsenmeister
 */
public class UsersEventsWidget extends ViewImpl implements UsersEventsWidgetPresenter.IView {

	// public static final String COMMENT_TEXT_DEFAULT =
	// "poste ein kommentar...";
	// public static final String COMMENT_SHOW = "kommentare";
	// public static final String COMMENT_SUBMIT =
	// "bet\u00E4tige die eingabetaste, um dein kommentar zu posten.";
	public static final String NO_EVENTS = "es sind keine entsprechenden events vorhanden";

	public static final String CONFIRM = "zusagen";
	public static final String CREATED = "erstellt";
	public static final String NOT = "absagen";

	private static final String STYLE_MENU = "menu-event";
	private static final String STYLE_MENU_LABEL_ACTIVE = "menu-event-label-active";
	private static final String STYLE_MENU_LABEL_INACTIVE = "menu-event-label-inactive";
	private static final String STYLE_NO_EVENTS = "no-events";
	private static final String STYLE_LOADING = "loading";

	private FlowPanel mainPanel_;
	private SimplePanel content_;
	private ITypedCallback<XUser> callbackUser_;
	private ITypedCallback<XUserEvent> callbackEvent_;
	private ITypedCallback<EnumEventWidget> callbackWidget_;
	private Label confirmLabel_;
	private Label createdLabel_;
	private Label refuseLabel_;

	private ListView<XEvent> created_;
	private ListView<XUserEvent> declined_;
	private ListView<XUserEvent> joined_;

	private boolean changeWidget_ = true;

	/**
	 * constructor, contains the view
	 */
	public UsersEventsWidget() {
		mainPanel_ = new FlowPanel();

		FlowPanel menu = new FlowPanel();
		menu.setStyleName(STYLE_MENU);
		mainPanel_.add(menu);

		confirmLabel_ = new Label(CONFIRM);
		confirmLabel_.setStyleName(STYLE_MENU_LABEL_INACTIVE);
		menu.add(confirmLabel_);

		createdLabel_ = new Label(CREATED);
		createdLabel_.setStyleName(STYLE_MENU_LABEL_INACTIVE);
		menu.add(createdLabel_);

		refuseLabel_ = new Label(NOT);
		refuseLabel_.setStyleName(STYLE_MENU_LABEL_INACTIVE);
		menu.add(refuseLabel_);

		content_ = new SimplePanel();
		mainPanel_.add(content_);

		joined_ = new ListView<XUserEvent>();
		joined_.setGenerator(new IGenerator<XUserEvent>() {
			@Override
			public Widget generateWidget(XUserEvent model) {
				EventWidget eventPanel = new EventWidget();
				eventPanel.setData(model.getXEvent(), XEventStatus.JOIN);
				eventPanel.setCallbackUser(callbackUser_);
				eventPanel.setCallbackStatus(callbackEvent_);
				return eventPanel;
			}
		});

		declined_ = new ListView<XUserEvent>();
		declined_.setGenerator(new IGenerator<XUserEvent>() {
			@Override
			public Widget generateWidget(XUserEvent model) {
				EventWidget eventPanel = new EventWidget();
				eventPanel.setData(model.getXEvent(), XEventStatus.DECLINE);
				eventPanel.setCallbackUser(callbackUser_);
				eventPanel.setCallbackStatus(callbackEvent_);
				return eventPanel;
			}
		});

		created_ = new ListView<XEvent>();
		created_.setGenerator(new IGenerator<XEvent>() {
			@Override
			public Widget generateWidget(XEvent model) {
				EventWidget eventPanel = new EventWidget();
				eventPanel.setData(model, XEventStatus.JOIN);
				eventPanel.setCallbackUser(callbackUser_);
				eventPanel.setCallbackStatus(callbackEvent_);
				return eventPanel;
			}
		});

		final Label loading = new Label();
		loading.setStyleName(STYLE_LOADING);

		content_.setWidget(loading);

		confirmLabel_.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (confirmLabel_.getStyleName().equals(STYLE_MENU_LABEL_INACTIVE)) {
					if (callbackWidget_ != null) {
						callbackWidget_.execute(EnumEventWidget.CONFIRM);
					}
					content_.setWidget(loading);
					confirmLabel_.setStyleName(STYLE_MENU_LABEL_ACTIVE);
					refuseLabel_.setStyleName(STYLE_MENU_LABEL_INACTIVE);
					createdLabel_.setStyleName(STYLE_MENU_LABEL_INACTIVE);
				}
			}
		});

		createdLabel_.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (createdLabel_.getStyleName().equals(STYLE_MENU_LABEL_INACTIVE)) {
					if (callbackWidget_ != null) {
						callbackWidget_.execute(EnumEventWidget.CREATED);
					}
					content_.setWidget(loading);
					confirmLabel_.setStyleName(STYLE_MENU_LABEL_INACTIVE);
					refuseLabel_.setStyleName(STYLE_MENU_LABEL_INACTIVE);
					createdLabel_.setStyleName(STYLE_MENU_LABEL_ACTIVE);
				}
			}
		});

		refuseLabel_.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (refuseLabel_.getStyleName().equals(STYLE_MENU_LABEL_INACTIVE)) {
					if (callbackWidget_ != null) {
						callbackWidget_.execute(EnumEventWidget.REFUSE);
					}
					content_.setWidget(loading);
					confirmLabel_.setStyleName(STYLE_MENU_LABEL_INACTIVE);
					refuseLabel_.setStyleName(STYLE_MENU_LABEL_ACTIVE);
					createdLabel_.setStyleName(STYLE_MENU_LABEL_INACTIVE);
				}
			}
		});
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}

	@Override
	public void setCallbackWidget(ITypedCallback<EnumEventWidget> callback) {
		callbackWidget_ = callback;
	}

	@Override
	public void setActiveWidget(EnumEventWidget widget) {
		if (changeWidget_) {
			switch (widget) {
			case CONFIRM:
				confirmLabel_.setStyleName(STYLE_MENU_LABEL_ACTIVE);
				content_.setWidget(joined_);
				break;
			case CREATED:
				createdLabel_.setStyleName(STYLE_MENU_LABEL_ACTIVE);
				content_.setWidget(created_);
				break;
			case REFUSE:
				refuseLabel_.setStyleName(STYLE_MENU_LABEL_ACTIVE);
				content_.setWidget(declined_);
				break;
			}
		}
	}

	@Override
	public void setCallbackUser(ITypedCallback<XUser> callback) {
		callbackUser_ = callback;
	}

	@Override
	public void setCallbackEvent(ITypedCallback<XUserEvent> callback) {
		callbackEvent_ = callback;
	}

	@Override
	public void setCreatedEvents(List<XEvent> events) {
		if ((events != null) && (events.size() > 0)) {
			created_.setStore(events);
		} else {
			Label noevents = new Label(NO_EVENTS);
			noevents.setStyleName(STYLE_NO_EVENTS);
			content_.setWidget(noevents);
		}
	}

	@Override
	public void setConfirmedEvents(List<XUserEvent> events) {
		if ((events != null) && (events.size() > 0)) {
			joined_.setStore(events);
		} else {
			Label noevents = new Label(NO_EVENTS);
			noevents.setStyleName(STYLE_NO_EVENTS);
			content_.setWidget(noevents);
		}
	}

	@Override
	public void setRefusedEvents(List<XUserEvent> events) {
		if ((events != null) && (events.size() > 0)) {
			declined_.setStore(events);
		} else {
			Label noevents = new Label(NO_EVENTS);
			noevents.setStyleName(STYLE_NO_EVENTS);
			content_.setWidget(noevents);
		}
	}

	@Override
	public void updateEvent(XUserEvent userEvent) {
		@SuppressWarnings("unchecked")
		ListView<XEvent> content = (ListView<XEvent>) content_.getWidget();
		EventWidget widget = (EventWidget) content.getWidget(userEvent.getXEvent());
		widget.setEventStatus(userEvent.getStatus());
		changeWidget_ = true;
	}

	@Override
	public void setChangeWidget(boolean change) {
		changeWidget_ = change;
	}
}
