package webapp.client.view.entry.restricted;

import webapp.model.XUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class InvitationUser extends Composite {

	private static InvitationUserUiBinder uiBinder = GWT.create(InvitationUserUiBinder.class);

	interface InvitationUserUiBinder extends UiBinder<Widget, InvitationUser> {
	}

	@UiField
	LayoutPanel panel;
	@UiField
	Image image;
	@UiField
	CheckBox checkBox;
	@UiField
	Label username;
	@UiField
	Label name;

	private XUser user_;

	public InvitationUser() {
		initWidget(uiBinder.createAndBindUi(this));

		panel.addDomHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (checkBox.getValue()) {
					checkBox.setValue(false);
				} else {
					checkBox.setValue(true);
				}
			}
		}, ClickEvent.getType());
	}

	public void setUser(XUser user) {
		user_ = user;
		username.setText(user_.getName());
		name.setText(user_.getName());
		image.setUrl(user_.getImage().getMediaURL());
		image.setAltText(user_.getName());
	}

	public XUser getUser() {
		return user_;
	}

	public void setChecked(boolean checked) {
		checkBox.setValue(checked);
	}

	public boolean getChecked() {
		return checkBox.getValue();
	}
}
