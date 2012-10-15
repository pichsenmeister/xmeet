package webapp.client.view.customwidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class XButton extends Composite {

	private static XButtonUiBinder uiBinder = GWT.create(XButtonUiBinder.class);

	interface XButtonUiBinder extends UiBinder<Widget, XButton> {
	}

	@UiField
	Button input;

	public XButton() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public XButton(String text) {
		this();
		input.setText(text);
	}

	public void setText(String text) {
		input.setText(text);
	}

	public HandlerRegistration addClickHandler(ClickHandler clickHandler) {
		return input.addClickHandler(clickHandler);
	}

}
