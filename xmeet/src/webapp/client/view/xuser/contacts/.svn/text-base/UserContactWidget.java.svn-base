package webapp.client.view.xuser.contacts;

import webapp.client.callback.ITypedCallback;
import webapp.model.XUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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

	private XUser user_;
	private ITypedCallback<XUser> callbackUser_;

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

	public void setRequest(boolean request) {
		if (request) {
			profilePicture.setUrl("");
			profilePicture.setAltText("request");
		} else {
			profilePicture.setUrl(user_.getImage().getMediaURL());
			profilePicture.setAltText("profile picture");
		}
	}
}
