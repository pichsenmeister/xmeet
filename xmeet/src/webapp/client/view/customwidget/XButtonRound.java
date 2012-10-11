package webapp.client.view.customwidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class XButtonRound extends Composite {

	private static XButtonRoundUiBinder uiBinder = GWT
			.create(XButtonRoundUiBinder.class);

	interface XButtonRoundUiBinder extends UiBinder<Widget, XButtonRound> {
	}

	@UiField 
	Button input;
	
	public XButtonRound(String text) {
		initWidget(uiBinder.createAndBindUi(this));
		
		input.setText(text);
	}
	
	public void setText(String text) {
		input.setText(text);
	}
	
	public HandlerRegistration addClickHandler(ClickHandler clickHandler) {
		return input.addClickHandler(clickHandler);
	}

	public void setEnabled(boolean enabled) {
		input.setEnabled(enabled);
	}
	
	public boolean isEnabled() {
		return input.isEnabled();
	}
}
