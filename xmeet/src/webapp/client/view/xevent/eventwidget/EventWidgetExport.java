package webapp.client.view.xevent.eventwidget;

import webapp.client.view.qr.ICalender;
import webapp.model.XEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.ImageChart;

public class EventWidgetExport extends Composite {

	private static EventWidgetExportUiBinder uiBinder = GWT
			.create(EventWidgetExportUiBinder.class);
	
	@UiField SimplePanel panel;

	interface EventWidgetExportUiBinder extends
			UiBinder<Widget, EventWidgetExport> {
	}

	public EventWidgetExport() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setEvent(final XEvent event) {
		Runnable onLoadCallback = new Runnable() {
			@Override
			public void run() {
				ICalender ical = new ICalender(event);
				panel.setWidget(ical.getPanel());
		      }
		    };
		VisualizationUtils.loadVisualizationApi(onLoadCallback, ImageChart.PACKAGE);
	}
}
