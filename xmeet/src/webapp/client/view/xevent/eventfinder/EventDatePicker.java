package webapp.client.view.xevent.eventfinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EventDatePicker extends Composite {

	private static EventDatePickerUiBinder uiBinder = GWT.create(EventDatePickerUiBinder.class);

	interface EventDatePickerUiBinder extends UiBinder<Widget, EventDatePicker> {
	}

	public static final String HOUR = "stunde";
	public static final String MINUTE = "minute";

	@UiField
	Label hour;
	@UiField
	Label minute;
	@UiField
	Grid minuteGrid;
	@UiField
	Grid hourGrid;

	public EventDatePicker() {
		initWidget(uiBinder.createAndBindUi(this));

		hour.setText(HOUR);
		minute.setText(MINUTE);

	}

	@UiHandler("hourGrid")
	void onHourGridClick(ClickEvent event) {
		Cell cell = hourGrid.getCellForEvent(event);
		Window.alert(cell.getCellIndex() + "");
	}
}
