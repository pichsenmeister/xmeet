package webapp.client.view.xevent.eventwidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EventWidgetDescription extends Composite {

	private static EventWidgetDescriptionUiBinder uiBinder = GWT
			.create(EventWidgetDescriptionUiBinder.class);

	interface EventWidgetDescriptionUiBinder extends
			UiBinder<Widget, EventWidgetDescription> {
	}

	@UiField
	Label description;
	
	public EventWidgetDescription() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setDescription(String descr) {
		description.getElement().setInnerHTML(descr);
	}
}
