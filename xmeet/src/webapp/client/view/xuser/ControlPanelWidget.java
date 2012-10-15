package webapp.client.view.xuser;

import webapp.client.callback.ICallback;
import webapp.client.presenter.xuser.ControlPanelPresenterWidget;
import webapp.client.view.customwidget.XButton;
import webapp.client.view.customwidget.XPasswordBox;
import webapp.client.view.customwidget.XTextBox;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for control panel, implements the interface declared in presenter
 * 
 * @author David Pichsenmeister
 */
@Singleton
public class ControlPanelWidget extends ViewImpl implements
		ControlPanelPresenterWidget.IView {

	public static final int WIDTH = 980;
	public static final int MARGIN = 15;
	public static final int MARGIN_TOP = 18;
	public static final int HEIGHT = 80;
	public static final String IMAGE = "xmeet/shraddle_48.png";
	public static final String IMAGE_ALT = "shraddle.com";
	public static final String LOGIN = "login";
	public static final int TEXT_PADDING = 4;
	public static final int TEXT_WIDTH = 120;
	public static final int TEXT_HEIGHT = 20;
	public static final int TEXT_MARGIN_TOP = 24;
	public static final int TEXT_MARGIN_RIGHT = 5;
	public static final int MARGIN_BUTTON_TOP = 27;
	public static final int MARGIN_BUTTON_RIGHT = 15;
	public static final String STYLE_TEXT = "x-text-box-dark";
	public static final String STYLE_PASS = "x-password-box-dark";

	public static final String USERNAME = "email";
	public static final String PASSWORD = "password";

	private AbsolutePanel noUserPanel_;
	private XTextBox email_;
	private XPasswordBox password_;
	private XButton login_;

	private ICallback callback_;

	/**
	 * constructor, contains the view
	 */
	public ControlPanelWidget() {
		noUserPanel_ = new AbsolutePanel();
		noUserPanel_.setWidth(WIDTH + "px");
		noUserPanel_.getElement().getStyle().setProperty("margin", "auto");
		noUserPanel_.setHeight(HEIGHT + "px");

		Image image = new Image();
		noUserPanel_.add(image);
		image.setUrl(IMAGE);
		image.setAltText(IMAGE_ALT);
		image.getElement().getStyle().setMarginLeft(MARGIN, Unit.PX);
		image.getElement().getStyle().setMarginTop(MARGIN_TOP, Unit.PX);
		image.getElement().getStyle().setFloat(Style.Float.LEFT);

		HorizontalPanel hPanel = new HorizontalPanel();
		noUserPanel_.add(hPanel);
		hPanel.getElement().getStyle().setMarginRight(MARGIN, Unit.PX);
		hPanel.getElement().getStyle().setFloat(Style.Float.RIGHT);

		email_ = new XTextBox();
		email_.setStyleName(STYLE_TEXT);
		email_.getElement().getStyle().setPadding(TEXT_PADDING, Unit.PX);
		email_.getElement().getStyle().setMarginTop(TEXT_MARGIN_TOP, Unit.PX);
		email_.getElement().getStyle()
				.setMarginRight(TEXT_MARGIN_RIGHT, Unit.PX);
		email_.setSize(TEXT_WIDTH + "px", TEXT_HEIGHT + "px");
		email_.setPlaceholder(USERNAME);
		hPanel.add(email_);

		password_ = new XPasswordBox();
		password_.setStyleName(STYLE_PASS);
		password_.getElement().getStyle().setPadding(TEXT_PADDING, Unit.PX);
		password_.getElement().getStyle()
				.setMarginTop(TEXT_MARGIN_TOP, Unit.PX);
		password_.getElement().getStyle()
				.setMarginRight(TEXT_MARGIN_RIGHT, Unit.PX);
		password_.setSize(TEXT_WIDTH + "px", TEXT_HEIGHT + "px");
		password_.setPlaceholder(PASSWORD);
		hPanel.add(password_);

		login_ = new XButton(LOGIN);
		login_.getElement().getStyle().setMarginTop(MARGIN_BUTTON_TOP, Unit.PX);
		login_.getElement().getStyle()
				.setMarginRight(MARGIN_BUTTON_RIGHT, Unit.PX);
		hPanel.add(login_);
		login_.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (callback_ != null) {
					callback_.execute();
				}
			}
		});
	}

	@Override
	public Widget asWidget() {
		return noUserPanel_;
	}

	@Override
	public void setCallback(ICallback callback) {
		callback_ = callback;
	}

	@Override
	public String getPassword() {
		return password_.getText();
	}

	@Override
	public String getEmail() {
		return email_.getText();
	}
}
