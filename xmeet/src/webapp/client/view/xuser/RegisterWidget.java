package webapp.client.view.xuser;

import webapp.client.callback.ICallback;
import webapp.client.presenter.xuser.RegisterWidgetPresenter;
import webapp.client.view.customwidget.XButton;
import webapp.client.view.customwidget.XPasswordBox;
import webapp.client.view.customwidget.XTextBox;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for XUser register, implements the interface declared in presenter
 * 
 * @author David Pichsenmeister
 */
public class RegisterWidget extends ViewImpl implements
		RegisterWidgetPresenter.IView {

	public static final int WIDTH_TOTAL = 300;
	public static final int WIDTH = 300;
	public static final int WIDTH_BUTTON = 120;
	public static final int HEIGHT = 30;
	public static final int FONT_SIZE = 20;
	public static final int PADDING = 5;

	public static final String REGISTER = "register";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String USERNAME_MSG = "username not valid";
	public static final String PASSWORD_MSG = "password too short";
	public static final String NAME_MSG = "username already taken";
	public static final String EMAIL_MSG = "email not valid";

	private XButton register_;
	private XTextBox userName_;
	private XPasswordBox password_;
	private XTextBox name_;
	private XTextBox email_;
	private VerticalPanel mainPanel_;

	private ICallback callback_;

	/**
	 * constructor, contains the view
	 */
	@Inject
	public RegisterWidget() {
		mainPanel_ = new VerticalPanel();
		mainPanel_.setSpacing(15);
		mainPanel_.setWidth(WIDTH_TOTAL + "px");
		mainPanel_.getElement().getStyle().setPosition(Position.RELATIVE);

		userName_ = new XTextBox();
		userName_.setMaxLength(15);
		userName_.setValidator("(\\w|-){1,15}");
		userName_.setRequired(true);
		userName_.setPlaceholder(USERNAME);
		mainPanel_.add(userName_);
		mainPanel_.setCellHorizontalAlignment(userName_,
				HasHorizontalAlignment.ALIGN_CENTER);

		name_ = new XTextBox();
		name_.setPlaceholder(NAME);
		mainPanel_.add(name_);
		mainPanel_.setCellHorizontalAlignment(name_,
				HasHorizontalAlignment.ALIGN_CENTER);

		email_ = new XTextBox();
		email_.setRequired(true);
		email_.setPlaceholder(EMAIL);
		mainPanel_.add(email_);
		mainPanel_.setCellHorizontalAlignment(email_,
				HasHorizontalAlignment.ALIGN_CENTER);

		password_ = new XPasswordBox();
		password_.setRequired(true);
		password_.setPlaceholder(PASSWORD);
		mainPanel_.add(password_);
		mainPanel_.setCellHorizontalAlignment(password_,
				HasHorizontalAlignment.ALIGN_CENTER);

		register_ = new XButton(REGISTER);
		register_.setWidth(WIDTH_BUTTON + "px");
		mainPanel_.add(register_);
		mainPanel_.setCellHorizontalAlignment(register_,
				HasHorizontalAlignment.ALIGN_CENTER);
		register_.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (callback_ != null) {
					callback_.execute();
				}
			}
		});

		userName_.setSize(WIDTH + "px", HEIGHT + "px");
		userName_.getElement().getStyle().setFontSize(FONT_SIZE, Unit.PX);
		userName_.getElement().getStyle().setPadding(PADDING, Unit.PX);

		name_.setSize(WIDTH + "px", HEIGHT + "px");
		name_.getElement().getStyle().setFontSize(FONT_SIZE, Unit.PX);
		name_.getElement().getStyle().setPadding(PADDING, Unit.PX);

		email_.setSize(WIDTH + "px", HEIGHT + "px");
		email_.getElement().getStyle().setFontSize(FONT_SIZE, Unit.PX);
		email_.getElement().getStyle().setPadding(PADDING, Unit.PX);

		password_.setSize(WIDTH + "px", HEIGHT + "px");
		password_.getElement().getStyle().setFontSize(FONT_SIZE, Unit.PX);
		password_.getElement().getStyle().setPadding(PADDING, Unit.PX);
	}

	@Override
	public String getEmail() {
		return email_.getText();
	}

	@Override
	public String getName() {
		return name_.getText();
	}

	@Override
	public String getPassword() {
		return password_.getText();
	}

	@Override
	public void setCallback(ICallback callback) {
		callback_ = callback;
	}

	@Override
	public String getUserName() {
		return userName_.getText();
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}

	@Override
	public boolean validate() {
		return userName_.validate() && email_.validate()
				&& password_.validate();
	}

}
