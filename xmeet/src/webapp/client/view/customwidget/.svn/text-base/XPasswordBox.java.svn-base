package webapp.client.view.customwidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;

public class XPasswordBox extends Composite {

	private static XPasswordBoxUiBinder uiBinder = GWT
			.create(XPasswordBoxUiBinder.class);

	interface XPasswordBoxUiBinder extends UiBinder<Widget, XPasswordBox> {
	}

	@UiField
	PasswordTextBox input;
	@UiField
	XStyle style;

	interface XStyle extends CssResource {
		String xTextBox();

		String xTextBoxDark();

		String invalid();
	}

	boolean required = false;
	String validRegex;

	public XPasswordBox() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setRequired(boolean require) {
		required = require;
	}

	public void setValidator(String regex) {
		validRegex = regex;
	}

	public boolean validate() {
		if (validRegex != null && validRegex.matches(input.getValue())) {
			input.removeStyleName(style.invalid());
			return true;
		} else if (required && input.getValue().length() > 0) {
			input.removeStyleName(style.invalid());
			return true;
		} else {
			input.addStyleName(style.invalid());
			return false;
		}
	}

	@UiHandler("input")
	void onInputBlur(BlurEvent event) {
		if (required) {
			validate();
		}
	}

	public String getText() {
		return input.getText();
	}

	public void setMaxLength(int maxLen) {
		input.setMaxLength(maxLen);
	}

	public void setDarkBackground(boolean dark) {
		if (dark) {
			input.setStyleName(style.xTextBoxDark());
		} else {
			input.setStyleName(style.xTextBox());
		}
	}

	public void setPlaceholder(String placeholder) {
		getElement().setAttribute("placeholder", placeholder);
	}
}
