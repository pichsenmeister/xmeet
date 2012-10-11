package webapp.client.view.xuser.register;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RegisterBox extends Composite {

	private static RegisterBoxUiBinder uiBinder = GWT.create(RegisterBoxUiBinder.class);

	interface RegisterBoxUiBinder extends UiBinder<Widget, RegisterBox> {
	}

	public RegisterBox() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
