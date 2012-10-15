package webapp.client.view.xuser;

import webapp.client.presenter.xuser.UserPagePresenter;
import webapp.client.presenter.xuser.UserPagePresenter.ICallbackListen;
import webapp.model.XUser;
import webapp.model.enums.XContactStatus;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for UserPage, implements the interface declared in presenter
 * 
 * @author David Pichsenmeister
 */
public class UserPageView extends ViewImpl implements UserPagePresenter.IView {

	private static final int MARGIN = 15;
	private static final int WIDTH_LEFT = 600;
	private static final int WIDTH_RIGHT = 320;
	private static final int TOP_CONTROL = 110;
	private static final int TOP_USER_CONTROL = 70;

	public static final String USER_WAIT_MSG = "anfrage widerrufen";
	public static final String USER_PLUS_MSG = "du lauschst";

	public static final String PLUS_SIGN = "+";
	public static final String MINUS_SIGN = "-";

	private static final String IMAGE_ALT = "user-profile-picture";
	private static final String STYLE_WRAPPER = "wrapper";
	private static final String STYLE_CONTENT = "user-content-wrapper";
	private static final String STYLE_CONTENT_LEFT = "content-left";
	private static final String STYLE_CONTENT_RIGHT = "content-right";
	private static final String STYLE_CONTROL = "control-panel";
	private static final String STYLE_USER_CONTROL = "user-control-panel";
	private static final String STYLE_USER_LISTEN = "user-panel-listen-text";
	private static final String STYLE_BUTTON = "xevent-button";
	private static final String STYLE_USER_USERNAME = "userpage-username";
	private static final String STYLE_USER_NAME = "userpage-name";
	private static final String STYLE_USER_PLACE = "userpage-place";
	private static final String STYLE_USER_WEBSITE = "userpage-website";
	private static final String STYLE_USER_IMAGE = "userpage-image";
	private static final String STYLE_FOOTER = "footer-panel";

	private FlowPanel mainPanel_;
	private SimplePanel top_;
	private FlowPanel content_;
	private SimplePanel eventCreator_;
	private SimplePanel myEvents_;
	private SimplePanel contacts_;
	private SimplePanel bottom_;
	private Label name_;
	private Image image_;
	private Label place_;
	private Label location_;
	private Anchor website_;
	private ICallbackListen callbackListen_;

	/**
	 * constructor, contains the view
	 */
	@Inject
	public UserPageView() {
		mainPanel_ = new FlowPanel();
		mainPanel_.setWidth("100%");
		mainPanel_.setStyleName(STYLE_WRAPPER);

		top_ = new SimplePanel();
		mainPanel_.add(top_);

		content_ = new FlowPanel();
		mainPanel_.add(content_);
		content_.getElement().getStyle().setBottom(15, Unit.PX);
		content_.setStyleName(STYLE_CONTENT);

		FlowPanel left = new FlowPanel();
		content_.add(left);
		left.getElement().getStyle().setMargin(MARGIN, Unit.PX);
		left.setStyleName(STYLE_CONTENT_LEFT);
		left.setWidth(WIDTH_LEFT + "px");

		FlowPanel right = new FlowPanel();
		content_.add(right);
		right.getElement().getStyle().setMargin(MARGIN, Unit.PX);
		right.setStyleName(STYLE_CONTENT_RIGHT);
		right.setWidth(WIDTH_RIGHT + "px");

		SimplePanel user = new SimplePanel();
		left.add(user);

		eventCreator_ = new SimplePanel();
		left.add(eventCreator_);

		myEvents_ = new SimplePanel();
		left.add(myEvents_);
		myEvents_.getElement().getStyle().setMarginTop(30, Unit.PX);

		contacts_ = new SimplePanel();
		right.add(contacts_);

		HorizontalPanel hPanel = new HorizontalPanel();
		image_ = new Image();
		image_.setStyleName(STYLE_USER_IMAGE);
		image_.setAltText(IMAGE_ALT);
		hPanel.add(image_);

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.getElement().getStyle().setPaddingLeft(20, Unit.PX);
		hPanel.add(vPanel);

		name_ = new Label();
		name_.setStyleName(STYLE_USER_NAME);
		vPanel.add(name_);

		location_ = new Label();
		vPanel.add(location_);

		place_ = new Label();
		place_.setStyleName(STYLE_USER_PLACE);
		vPanel.add(place_);

		SimplePanel link = new SimplePanel();
		link.getElement().getStyle().setMarginTop(5, Unit.PX);
		vPanel.add(link);
		website_ = new Anchor();
		website_.setStyleName(STYLE_USER_WEBSITE);
		link.add(website_);

		user.setWidget(hPanel);

		bottom_ = new SimplePanel();
		bottom_.setStyleName(STYLE_FOOTER);
		content_.add(bottom_);
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == UserPagePresenter.TYPE_CONTENT_CONTROL) {
			setContentUserControl(content);
		} else if (slot == UserPagePresenter.TYPE_CONTENT_CONTACTS) {
			setContentContacts(content);
		} else if (slot == UserPagePresenter.TYPE_CONTENT_MY_EVENTS) {
			setContentMyEvents(content);
		} else if (slot == UserPagePresenter.TYPE_CONTENT_LAST_LOCATIONS) {
			setContentLastLocation(content);
		} else if (slot == UserPagePresenter.TYPE_CONTENT_FOOTER) {
			setContentFooter(content);
		} else {
			super.setInSlot(slot, content);
		}
	}

	/**
	 * sets content in slot
	 * 
	 * @param content
	 *            content to set in slot
	 */
	private void setContentLastLocation(Widget content) {
		if (eventCreator_.getWidget() != null) {
			eventCreator_.clear();
		}
		content.getElement().getStyle().setPaddingTop(15, Unit.PX);
		content.getElement().getStyle().setPaddingLeft(20, Unit.PX);
		eventCreator_.add(content);
	}

	/**
	 * sets content in slot
	 * 
	 * @param content
	 *            content to set in slot
	 */
	private void setContentMyEvents(Widget content) {
		if (myEvents_.getWidget() != null) {
			myEvents_.clear();
		}
		myEvents_.add(content);
	}

	/**
	 * sets content in slot
	 * 
	 * @param content
	 *            content to set in slot
	 */
	private void setContentContacts(Widget content) {
		if (contacts_.getWidget() != null) {
			contacts_.clear();
		}
		contacts_.add(content);
	}

	/**
	 * sets content in slot
	 * 
	 * @param content
	 *            content to set in slot
	 */
	private void setContentUserControl(Widget content) {
		if (top_.getWidget() != null) {
			top_.clear();
		}
		top_.add(content);
	}

	/**
	 * sets content in slot
	 * 
	 * @param content
	 *            content to set in slot
	 */
	private void setContentFooter(Widget content) {
		if (bottom_.getWidget() != null) {
			bottom_.clear();
		}
		bottom_.add(content);
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}

	@Override
	public void setUser(XUser user) {
		name_.setText(user.getName());
		image_.setUrl(user.getImage().getMediaURL());
		place_.setText(user.getPlace());

		String web = user.getWebsite();
		website_.setTarget("_blank");
		website_.setText(web.replaceFirst("http://", ""));
		website_.setHref(web);
	}

	@Override
	public void showListenButton(boolean listen, XContactStatus status) {
		FlowPanel panel = new FlowPanel();
		panel.getElement().getStyle().setMarginTop(30, Unit.PX);
		panel.getElement().getStyle().setMarginLeft(15, Unit.PX);

		final Button button = new Button(PLUS_SIGN);
		button.setStyleName(STYLE_BUTTON);
		panel.add(button);

		final Label msg = new Label();
		msg.setStyleName(STYLE_USER_LISTEN);
		panel.add(msg);

		if (listen) {
			switch (status) {
			case PERMIT:
				button.setText(MINUS_SIGN);
				msg.setText(USER_PLUS_MSG);
				msg.setVisible(true);
				break;
			case REQUEST:
				button.setText(MINUS_SIGN);
				msg.setText(USER_WAIT_MSG);
				msg.setVisible(true);
				break;
			default:
				break;
			}
		} else {
			button.setText(PLUS_SIGN);
			msg.setText("");
			msg.setVisible(false);
		}

		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (callbackListen_ != null) {
					callbackListen_.execute(button, msg);
				}
			}
		});

		eventCreator_.clear();
		eventCreator_.setWidget(panel);
	}

	@Override
	public void setCallbackListen(ICallbackListen callback) {
		callbackListen_ = callback;
	}

	@Override
	public void isUserLogged(boolean logged) {
		if (logged) {
			top_.setStyleName(STYLE_USER_CONTROL);
			content_.getElement().getStyle().setMarginTop(TOP_USER_CONTROL, Unit.PX);
		} else {
			top_.setStyleName(STYLE_CONTROL);
			content_.getElement().getStyle().setMarginTop(TOP_CONTROL, Unit.PX);
		}
	}
}
