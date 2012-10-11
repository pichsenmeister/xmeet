package webapp.client.view.entry;

import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.xevent.UsersEventsWidgetPresenter;
import webapp.model.XUser;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for user events, implements the interface declared in presenter
 * 
 * @author David Pichsenmeister
 */
public class UsersEventsWidget extends ViewImpl implements UsersEventsWidgetPresenter.IView {

	public static final String NO_EVENTS = "es sind keine entsprechenden events vorhanden";

	private static final String STYLE_MENU = "menu-event";
	private static final String STYLE_LOADING = "loading";

	private FlowPanel mainPanel_;
	private SimplePanel content_;
	private ITypedCallback<XUser> callbackUser_;

	private boolean changeWidget_ = true;

	/**
	 * constructor, contains the view
	 */
	public UsersEventsWidget() {
		mainPanel_ = new FlowPanel();

		FlowPanel menu = new FlowPanel();
		menu.setStyleName(STYLE_MENU);
		mainPanel_.add(menu);

		content_ = new SimplePanel();
		mainPanel_.add(content_);

		final Label loading = new Label();
		loading.setStyleName(STYLE_LOADING);

		content_.setWidget(loading);
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}

	@Override
	public void setCallbackUser(ITypedCallback<XUser> callback) {
		callbackUser_ = callback;
	}
}
