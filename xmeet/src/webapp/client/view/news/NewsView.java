package webapp.client.view.news;

import webapp.client.presenter.news.NewsPresenter;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for News View, implements the interface declared in presenter
 * 
 * @author David Pichsenmeister
 */
public class NewsView extends ViewImpl implements NewsPresenter.IView {

	public static final String HEADER = "wer ist @?";

	private static final String STYLE_CONTENT = "user-content-wrapper";
	private static final String STYLE_CONTENT_LEFT = "content-left";
	private static final String STYLE_CONTENT_RIGHT = "content-right";
	private static final String STYLE_WRAPPER = "wrapper";
	private static final String STYLE_CONTROL = "user-control-panel";
	private static final String STYLE_FOOTER = "footer-panel";
	private static final String STYLE_HEADER = "header";

	private FlowPanel mainPanel_;
	private SimplePanel top_;
	private SimplePanel content_;
	private SimplePanel bottom_;

	/**
	 * constructor, contains the view
	 */
	@Inject
	public NewsView() {
		mainPanel_ = new FlowPanel();
		mainPanel_.setStyleName(STYLE_WRAPPER);

		top_ = new SimplePanel();
		mainPanel_.add(top_);
		top_.setStyleName(STYLE_CONTROL);

		FlowPanel content = new FlowPanel();
		mainPanel_.add(content);
		content.setStyleName(STYLE_CONTENT);

		FlowPanel left = new FlowPanel();
		content.add(left);
		left.setStyleName(STYLE_CONTENT_LEFT);

		FlowPanel right = new FlowPanel();
		content.add(right);
		right.setStyleName(STYLE_CONTENT_RIGHT);

		Label header = new Label(HEADER);
		left.add(header);
		header.setStyleName(STYLE_HEADER);

		content_ = new SimplePanel();
		left.add(content_);

		bottom_ = new SimplePanel();
		bottom_.setStyleName(STYLE_FOOTER);
		content.add(bottom_);
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == NewsPresenter.TYPE_CONTENT_CONTROL) {
			setContentUserControl(content);
		} else if (slot == NewsPresenter.TYPE_CONTENT_NEWS) {
			setContent(content);
		} else if (slot == NewsPresenter.TYPE_CONTENT_FOOTER) {
			setContentFooter(content);
		} else {
			super.setInSlot(slot, content);
		}
	}

	private void setContent(Widget content) {
		if (content_.getWidget() != null) {
			content_.clear();
		}
		content_.add(content);
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
}
