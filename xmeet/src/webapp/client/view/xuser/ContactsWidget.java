package webapp.client.view.xuser;

import java.util.List;

import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.xuser.ContactsWidgetPresenter;
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
public class ContactsWidget extends ViewImpl implements
		ContactsWidgetPresenter.IView {

	public static final String LISTENER = "subscriber";
	public static final String LISTENTO = "subscribed to";
	public static final String REQUESTS = "requests";
	public static final String LISTENTO_DEFAULT = "not subscribed to anybody";
	public static final String LISTENER_DEFAULT = "no subscribers";
	public static final String REQUEST_DEFAULT = "no new requests";

	private static final String STYLE_TEXT = "text-no-contacts";

	private ContactsTabPanel mainPanel_;
	private ListView<XUser> listenTo_;
	private ListView<XUser> listener_;
	private ListView<XUser> requests_;
	private ITypedCallback<XUser> callbackUser_;
	private ITypedCallback<XUser> callbackAdd_;
	private ITypedCallback<XUser> callbackRemove_;
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
				widget.setCallbackAdd(callbackAdd_);
				widget.setCallbackRemove(callbackRemove_);
				widget.setUser(model);
				widget.enablePlus(false);
				widget.enableMinus(true);
				widget.enableRequest(false);
				return widget;
			}
		});

		listenTo_ = new ListView<XUser>();
		listenTo_.setGenerator(new IGenerator<XUser>() {

			@Override
			public Widget generateWidget(XUser model) {
				UserContactWidget widget = new UserContactWidget();
				widget.setCallbackUser(callbackUser_);
				widget.setCallbackAdd(callbackAdd_);
				widget.setCallbackRemove(callbackRemove_);
				widget.setUser(model);
				widget.enablePlus(false);
				widget.enableMinus(true);
				widget.enableRequest(false);
				return widget;
			}
		});

		requests_ = new ListView<XUser>();
		requests_.setGenerator(new IGenerator<XUser>() {

			@Override
			public Widget generateWidget(XUser model) {
				UserContactWidget widget = new UserContactWidget();
				widget.setCallbackUser(callbackUser_);
				widget.setCallbackAdd(callbackAdd_);
				widget.setCallbackRemove(callbackRemove_);
				widget.setUser(model);
				widget.enablePlus(true);
				widget.enableMinus(true);
				widget.enableRequest(false);
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
	public void setRequestEnabled(boolean enable) {
		requestLabel_.setVisible(enable);
	}

	@Override
	public void setCallbackSelect(ITypedCallback<EnumWidget> callback) {
		mainPanel_.setCallbackSelect(callback);
	}

	@Override
	public void setWidget(EnumWidget value, List<XUser> list) {
		mainPanel_.setActiveTab(value);

		switch (value) {
		case SUBSCRIBER:
			listener_.setStore(list);
			mainPanel_.setContent(listener_);
			break;
		case SUBSCRIBEDTO:
			listenTo_.setStore(list);
			mainPanel_.setContent(listenTo_);
			break;
		case REQUEST:
			requests_.setStore(list);
			mainPanel_.setContent(requests_);
			break;
		}
	}

	@Override
	public void setCallbackAdd(ITypedCallback<XUser> callback) {
		callbackAdd_ = callback;
	}

	@Override
	public void setCallbackRemove(ITypedCallback<XUser> callback) {
		callbackRemove_ = callback;
	}
}
