package webapp.client.view.xuser;

import webapp.client.callback.ICallback;
import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.xuser.UserControlPanelWidgetPresenter;
import webapp.client.view.xuser.control.UserControlWidget;
import webapp.model.XUser;
import webapp.model.enums.MenuIcon;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for XUser's control panel, implements the interface declared in
 * presenter
 * 
 * @author David Pichsenmeister
 */
@Singleton
public class UserControlPanelWidget extends ViewImpl implements UserControlPanelWidgetPresenter.IView {

	FlowPanel mainPanel_;
	UserControlWidget widget_;

	/**
	 * constructor, contains the view
	 */
	public UserControlPanelWidget() {
		mainPanel_ = new FlowPanel();

		widget_ = new UserControlWidget();
		mainPanel_.add(widget_);
	}

	@Override
	public void setUser(XUser user) {
		widget_.setUser(user);
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}

	@Override
	public void setCallbackLogout(ICallback callback) {
		widget_.setCallbackLogout(callback);
	}

	@Override
	public void setCallbackSearch(ITypedCallback<String> callback) {
		widget_.setCallbackSearch(callback);
	}

	@Override
	public void setCallbackUser(ICallback callback) {
		widget_.setCallbackUser(callback);
	}

	@Override
	public void setCallbackNews(ICallback callback) {
		widget_.setCallbackNews(callback);
	}

	@Override
	public void setCallbackCreate(ICallback callback) {
		widget_.setCallbackCreate(callback);
	}

	@Override
	public void setCallbackContacts(ICallback callback) {
		widget_.setCallbackContacts(callback);
	}

	@Override
	public void setCallbackSettings(ICallback callback) {
		widget_.setCallbackSettings(callback);
	}

	@Override
	public void setActiveIcon(MenuIcon icon) {
		widget_.setActiveIcon(icon);
	}

}
