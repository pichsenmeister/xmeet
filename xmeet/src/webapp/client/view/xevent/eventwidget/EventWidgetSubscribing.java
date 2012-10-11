package webapp.client.view.xevent.eventwidget;

import webapp.client.view.xevent.eventwidget.EventWidget.ICallbackSubscribing;
import webapp.model.enums.XEventStatus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EventWidgetSubscribing extends Composite {

	private static EventWidgetSubscribingUiBinder uiBinder = GWT.create(EventWidgetSubscribingUiBinder.class);

	public static final String PLUS_SIGN = "+";
	public static final String PLUS_MSG = "du nimmst teil";
	public static final String HIDDEN_PLUS_SIGN = "(+)";
	public static final String HIDDEN_PLUS_MSG = "du nimmst teil (versteckt)";
	public static final String TILDE_SIGN = "~";
	public static final String TILDE_MSG = "du nimmst vielleicht teil";
	public static final String MINUS_SIGN = "-";
	public static final String MINUS_MSG = "du nimmst nicht teil";

	@UiField
	Button hiddenPlus;
	@UiField
	Button plus;
	@UiField
	Button tilde;
	@UiField
	Button minus;
	@UiField
	Label status;

	ICallbackSubscribing callback_;
	EventWidget widget_;

	interface EventWidgetSubscribingUiBinder extends UiBinder<Widget, EventWidgetSubscribing> {
	}

	public EventWidgetSubscribing() {
		initWidget(uiBinder.createAndBindUi(this));

		hiddenPlus.setText(HIDDEN_PLUS_SIGN);
		plus.setText(PLUS_SIGN);
		tilde.setText(TILDE_SIGN);
		minus.setText(MINUS_SIGN);
	}

	public void setStatus(XEventStatus eventStatus) {
		switch (eventStatus) {
		case HIDDENCONFIRM:
			hiddenPlus.setEnabled(false);
			plus.setEnabled(true);
			tilde.setEnabled(true);
			minus.setEnabled(true);
			status.setText(HIDDEN_PLUS_MSG);
			break;
		case CONFIRM:
			hiddenPlus.setEnabled(true);
			plus.setEnabled(false);
			tilde.setEnabled(true);
			minus.setEnabled(true);
			status.setText(PLUS_MSG);
			break;
		case MAYBE:
			hiddenPlus.setEnabled(true);
			plus.setEnabled(true);
			tilde.setEnabled(false);
			minus.setEnabled(true);
			status.setText(TILDE_MSG);
			break;
		case REFUSE:
			hiddenPlus.setEnabled(true);
			plus.setEnabled(true);
			tilde.setEnabled(true);
			minus.setEnabled(false);
			status.setText(MINUS_MSG);
			break;
		}
	}

	public void setCallback(ICallbackSubscribing callback) {
		callback_ = callback;
	}

	public void setEventWidget(EventWidget widget) {
		widget_ = widget;
	}

	@UiHandler(value = { "plus", "tilde", "minus", "hiddenPlus" })
	void handleClick(ClickEvent e) {
		if ((callback_ != null) && (widget_ != null)) {
			Element elem = Element.as(e.getNativeEvent().getEventTarget());
			String sign = elem.getInnerText().replaceAll(" ", "");
			if (sign.equals(PLUS_SIGN)) {
				callback_.execute(widget_, XEventStatus.CONFIRM);
			} else if (sign.equals(TILDE_SIGN)) {
				callback_.execute(widget_, XEventStatus.MAYBE);
			} else if (sign.equals(MINUS_SIGN)) {
				callback_.execute(widget_, XEventStatus.REFUSE);
			} else if (sign.equals(HIDDEN_PLUS_SIGN)) {
				callback_.execute(widget_, XEventStatus.HIDDENCONFIRM);
			}
		}
	}
}
