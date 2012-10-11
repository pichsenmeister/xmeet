package webapp.client.view.customwidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class XTextArea extends Composite {

	private static XTextAreaUiBinder uiBinder = GWT.create(XTextAreaUiBinder.class);

	interface XTextAreaUiBinder extends UiBinder<Widget, XTextArea> {
	}

	@UiField
	TextArea input;

	public XTextArea() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return input.addFocusHandler(handler);
	}

	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return input.addBlurHandler(handler);
	}

	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		return input.addKeyUpHandler(handler);
	}

	public String getText() {
		return input.getText();
	}

	public void setText(String value) {
		input.setText(value);
	}

	public void clear() {
		input.setText("");
	}

	public void setPlaceholder(String placeholder) {
		getElement().setAttribute("placeholder", placeholder);
	}
}
