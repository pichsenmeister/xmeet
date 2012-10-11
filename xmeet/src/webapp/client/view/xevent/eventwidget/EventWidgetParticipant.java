package webapp.client.view.xevent.eventwidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EventWidgetParticipant extends Composite {

	private static EventWidgetParticipantUiBinder uiBinder = GWT
			.create(EventWidgetParticipantUiBinder.class);

	interface EventWidgetParticipantUiBinder extends
			UiBinder<Widget, EventWidgetParticipant> {
	}

	public EventWidgetParticipant() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
