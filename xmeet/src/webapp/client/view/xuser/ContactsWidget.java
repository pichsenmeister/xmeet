package webapp.client.view.xuser;

import java.util.List;

import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.xuser.ContactsWidgetPresenter;
import webapp.client.presenter.xuser.ContactsWidgetPresenter.ICallbackRequest;
import webapp.client.view.customwidget.ListView;
import webapp.client.view.customwidget.ListView.IGenerator;
import webapp.client.view.xuser.contacts.ContactsTabPanel;
import webapp.client.view.xuser.contacts.UserContactWidget;
import webapp.model.XUser;
import webapp.model.enums.EnumWidget;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for XUser's contacts, implements the interface declared in presenter
 * 
 * @author David Pichsenmeister
 */
public class ContactsWidget extends ViewImpl implements ContactsWidgetPresenter.IView {

	public static final String LISTENER = "lauscher";
	public static final String LISTENTO = "du lauschst";
	public static final String REQUESTS = "anfragen";
	public static final String LISTENTO_DEFAULT = "du lauschst noch niemanden";
	public static final String LISTENER_DEFAULT = "niemand lauscht dir";
	public static final String REQUEST_DEFAULT = "keine anfragen vorhanden";

	private static final String STYLE_TEXT = "text-no-contacts";

	private ContactsTabPanel mainPanel_;
	private ListView<XUser> listenTo_;
	private ListView<XUser> listener_;
	private ListView<XUser> requests_;
	private ITypedCallback<XUser> callbackUser_;
	private ICallbackRequest callbackRequest_;
	private Label requestLabel_;

	/**
	 * constructor, contains the view
	 */
	public ContactsWidget() {
		mainPanel_ = new ContactsTabPanel();

		listener_ = new ListView<XUser>();
		listener_.setGenerator(new IGenerator<XUser>() {

			@Override
			public Widget generateWidget(XUser model) {
				UserContactWidget widget = new UserContactWidget();
				widget.setCallbackUser(callbackUser_);
				widget.setUser(model);
				return widget;
			}
		});

		listenTo_ = new ListView<XUser>();
		listenTo_.setGenerator(new IGenerator<XUser>() {

			@Override
			public Widget generateWidget(XUser model) {
				UserContactWidget widget = new UserContactWidget();
				widget.setCallbackUser(callbackUser_);
				widget.setUser(model);
				return widget;
			}
		});

		requests_ = new ListView<XUser>();
		requests_.setGenerator(new IGenerator<XUser>() {

			@Override
			public Widget generateWidget(XUser model) {
				UserContactWidget widget = new UserContactWidget();
				widget.setCallbackUser(callbackUser_);
				widget.setUser(model);
				widget.setRequest(true);
				return widget;
			}
		});
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}

	@Override
	public void setCallbackUser(ITypedCallback<XUser> callback) {
		callbackUser_ = callback;
	}

	@Override
	public void setListenTo(List<XUser> users) {
		listenTo_.setStore(users);
	}

	@Override
	public void setListener(List<XUser> users) {
		listener_.setStore(users);
	}

	@Override
	public void setRequests(List<XUser> users) {
		requests_.setStore(users);
	}

	@Override
	public void setRequestEnabled(boolean enable) {
		requestLabel_.setVisible(enable);
	}

	@Override
	public void setCallbackRequest(ICallbackRequest callback) {
		callbackRequest_ = callback;
	}

	@Override
	public void setCallbackSelect(ITypedCallback<EnumWidget> callback) {
		mainPanel_.setCallbackSelect(callback);
	}

	@Override
	public void setWidget(EnumWidget value) {
		switch (value) {
		case LISTENER:
			mainPanel_.setContent(listener_);
			break;
		case LISTENTO:
			mainPanel_.setContent(listenTo_);
			break;
		case REQUEST:
			mainPanel_.setContent(requests_);
			break;
		}
	}
}
