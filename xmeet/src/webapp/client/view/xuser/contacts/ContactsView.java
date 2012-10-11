package webapp.client.view.xuser.contacts;

import webapp.client.presenter.xuser.ContactsPresenter;
import webapp.client.view.customwidget.MainWidget;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for UserPage, implements the interface declared in presenter
 * 
 * @author David Pichsenmeister
 */
public class ContactsView extends ViewImpl implements ContactsPresenter.IView {

	private MainWidget mainPanel_;

	/**
	 * constructor, contains the view
	 */
	@Inject
	public ContactsView() {
		mainPanel_ = new MainWidget();
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == ContactsPresenter.TYPE_CONTENT_CONTROL) {
			setContentUserControl(content);
		} else if (slot == ContactsPresenter.TYPE_CONTENT_CONTACTS) {
			setContentContacts(content);
		} else if (slot == ContactsPresenter.TYPE_CONTENT_FOOTER) {
			setContentFooter(content);
		} else {
			super.setInSlot(slot, content);
		}
	}

	private void setContentFooter(Widget content) {
		mainPanel_.setBottom(content);
	}

	private void setContentContacts(Widget content) {
		mainPanel_.setLeft(content);
	}

	private void setContentUserControl(Widget content) {
		mainPanel_.setTop(content);
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}
}
