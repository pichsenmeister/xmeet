package webapp.client.view.customwidget;


import com.google.gwt.user.client.ui.TextArea;

public class XTextAreaOld extends TextArea {

	public static final String STYLE = "x-text-box";

	public XTextAreaOld() {
		super();

		setStyleName(STYLE);
		setHeight(50 + "px");

	}
}
