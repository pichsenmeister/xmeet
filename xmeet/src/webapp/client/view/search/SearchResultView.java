package webapp.client.view.search;

import java.util.List;

import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.search.SearchResultPresenter;
import webapp.client.presenter.xuser.UserPagePresenter;
import webapp.model.XUser;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for Search Result Page, implements the interface declared in
 * presenter
 * 
 * @author David Pichsenmeister
 */
public class SearchResultView extends ViewImpl implements SearchResultPresenter.IView {

	public static final String STYLE_WRAPPER = "wrapper";
	public static final String STYLE_CONTENT = "user-content-wrapper";
	public static final String STYLE_CONTROL = "user-control-panel";
	public static final String STYLE_FOOTER = "footer-panel";

	private FlowPanel mainPanel_;
	private SimplePanel top_;
	private FlowPanel content_;
	private SimplePanel bottom_;
	private ITypedCallback<XUser> callbackUser_;

	/**
	 * constructor, contains the view
	 */
	@Inject
	public SearchResultView() {
		mainPanel_ = new FlowPanel();
		mainPanel_.setStyleName(STYLE_WRAPPER);

		top_ = new SimplePanel();
		mainPanel_.add(top_);
		top_.setStyleName(STYLE_CONTROL);

		content_ = new FlowPanel();
		mainPanel_.add(content_);
		content_.getElement().getStyle().setMarginTop(70, Unit.PX);
		content_.getElement().getStyle().setBottom(15, Unit.PX);
		content_.setStyleName(STYLE_CONTENT);

		bottom_ = new SimplePanel();
		bottom_.setStyleName(STYLE_FOOTER);
		content_.add(bottom_);
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == SearchResultPresenter.TYPE_CONTENT_CONTROL) {
			setContentUserControl(content);
		} else if (slot == UserPagePresenter.TYPE_CONTENT_FOOTER) {
			setContentFooter(content);
		} else {
			super.setInSlot(slot, content);
		}
	}

	private void setContentUserControl(Widget content) {
		if (top_.getWidget() != null) {
			top_.clear();
		}
		top_.add(content);
	}

	/**
	 * sets content in slot
	 * 
	 * @param content
	 *            content to set in slot
	 */
	private void setContentFooter(Widget content) {
		if (bottom_.getWidget() != null) {
			bottom_.clear();
		}
		bottom_.add(content);
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}

	@Override
	public void setXUserList(List<XUser> users, XUser user) {
		for (XUser u : users) {
			content_.add(new Label(u.getName()));
		}
	}

	@Override
	public void setCallbackUser(ITypedCallback<XUser> callback) {
		callbackUser_ = callback;
	}
}
