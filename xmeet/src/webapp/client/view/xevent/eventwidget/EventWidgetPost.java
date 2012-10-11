package webapp.client.view.xevent.eventwidget;

import webapp.client.view.xevent.eventwidget.EventWidget.ICallbackPost;
import webapp.model.XUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EventWidgetPost extends Composite {

	private static EventWidgetPostUiBinder uiBinder = GWT
			.create(EventWidgetPostUiBinder.class);
	
	public static final String COMMENT_TEXT_DEFAULT = "poste ein kommentar...";
	public static final String EMPTY = "";
	public static final String COMMENT_SUBMIT = "bet\u00E4tige die eingabetaste, um dein kommentar zu posten.";
	
	@UiField Label info;
	@UiField TextBox posting;
	@UiField Image image;
	
	ICallbackPost callback_;
	EventWidget widget_;

	interface EventWidgetPostUiBinder extends UiBinder<Widget, EventWidgetPost> {
	}

	public EventWidgetPost() {
		initWidget(uiBinder.createAndBindUi(this));
		
		info.setText(COMMENT_SUBMIT);
		posting.setText(COMMENT_TEXT_DEFAULT);
		info.setVisible(false);
		image.setVisible(false);
	}
	
	public void setUser(XUser user) {
		image.setAltText(user.getUserName());
		image.setUrl(user.getImage().getMediaURL());
	}
	
	@UiHandler("posting")
	void handleFocus(FocusEvent e) {
		if(posting.getText().equals(COMMENT_TEXT_DEFAULT)) {
			posting.setText(EMPTY);
			info.setVisible(true);
			image.setVisible(true);
		}
	}
	
	@UiHandler("posting")
	void handleBlur(BlurEvent e) {
		if(posting.getText().equals(EMPTY)) {
			posting.setText(COMMENT_TEXT_DEFAULT);
			info.setVisible(false);
			image.setVisible(false);
		}
	}
	
	@UiHandler("posting")
	void handleKeyUp(KeyUpEvent e) {
		if((e.getNativeKeyCode() == KeyCodes.KEY_ENTER) && (callback_ != null) && (widget_ != null)) {
			callback_.execute(widget_, posting.getText());
			posting.setText(COMMENT_TEXT_DEFAULT);
			info.setVisible(false);
			image.setVisible(false);
		}
	}

	public void setCallback(ICallbackPost callback) {
		callback_ = callback;		
	}
	
	public void setEventWidget(EventWidget widget) {
		widget_ = widget;
	}
}
