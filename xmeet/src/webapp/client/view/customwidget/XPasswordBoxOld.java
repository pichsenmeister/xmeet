package webapp.client.view.customwidget;

import com.google.gwt.user.client.ui.PasswordTextBox;

public class XPasswordBoxOld extends PasswordTextBox {

	public static final String STYLE = "x-password-box";

	public XPasswordBoxOld() {
		super();

		setMaxLength(20);
		setStyleName(STYLE);
	}
}
