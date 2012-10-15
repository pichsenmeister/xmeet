package webapp.client.view.customwidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class MainWidget extends Composite {

	private static MainWidgetUiBinder uiBinder = GWT.create(MainWidgetUiBinder.class);

	interface MainWidgetUiBinder extends UiBinder<Widget, MainWidget> {
	}

	@UiField
	SimplePanel top;
	@UiField
	SimplePanel left;
	@UiField
	SimplePanel right;
	@UiField
	SimplePanel bottom;

	public MainWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setTop(Widget widget) {
		top.setWidget(widget);
	}

	public void setLeft(Widget widget) {
		left.setWidget(widget);
	}

	public void setRight(Widget widget) {
		right.setWidget(widget);
	}

	public void setBottom(Widget widget) {
		bottom.setWidget(widget);
	}

}
