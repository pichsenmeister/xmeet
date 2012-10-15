package webapp.client.view.xuser.contacts;

import webapp.client.callback.ITypedCallback;
import webapp.client.view.customwidget.XButtonRound;
import webapp.model.XUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserContactWidget extends Composite {

	private static UserContactWidgetUiBinder uiBinder = GWT.create(UserContactWidgetUiBinder.class);

	interface UserContactWidgetUiBinder extends UiBinder<Widget, UserContactWidget> {
	}

	@UiField
	Image profilePicture;
	@UiField
	Label name;
	@UiField
	LayoutPanel panel;
	@UiField
	XButtonRound plus;
	@UiField
	XButtonRound minus;
	@UiField
	XButtonRound request;

	private XUser user_;
	private ITypedCallback<XUser> callbackUser_;
	private ITypedCallback<XUser> callbackAdd_;
	private ITypedCallback<XUser> callbackRemove_;

	public UserContactWidget() {
		initWidget(uiBinder.createAndBindUi(this));

		panel.addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (callbackUser_ != null) {
					callbackUser_.execute(user_);
				}
			}
		}, ClickEvent.getType());

		plus.setText("+");
		minus.setText("-");
		request.setText("?");
	}

	public void setUser(XUser user) {
		this.user_ = user;
		name.setText(user_.getName());
		profilePicture.setUrl(user_.getImage().getMediaURL());
		profilePicture.setAltText("profile picture");
	}

	public void setCallbackUser(ITypedCallback<XUser> callback) {
		callbackUser_ = callback;
	}

	public void enablePlus(boolean enable) {
		if (!enable) {
			plus.removeFromParent();
		}
	}

	public void enableMinus(boolean enable) {
		if (!enable) {
			minus.removeFromParent();
		}
	}

	public void enableRequest(boolean enable) {
		if (!enable) {
			request.removeFromParent();
		}
	}

	public void setCallbackAdd(ITypedCallback<XUser> callback) {
		callbackAdd_ = callback;
	}

	public void setCallbackRemove(ITypedCallback<XUser> callback) {
		callbackRemove_ = callback;
	}

	@UiHandler("plus")
	void onPlusClick(ClickEvent event) {
		if (callbackAdd_ != null) {
			callbackAdd_.execute(user_);
		}
	}

	@UiHandler("minus")
	void onMinusClick(ClickEvent event) {
		if (callbackRemove_ != null) {
			callbackRemove_.execute(user_);
		}
	}

	@UiHandler("request")
	void onRequestClick(ClickEvent event) {
		if (callbackRemove_ != null) {
			callbackRemove_.execute(user_);
		}
	}
}
