package webapp.client.view;

import webapp.client.presenter.StartPagePresenter;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for XUser Login, implements the interface declared in presenter
 *
 * @author David Pichsenmeister
 */
public class StartPageView extends ViewImpl implements StartPagePresenter.IView {

	public static final int MARGIN = 15;
	public static final int WIDTH = 980;
	public static final int WIDTH_LEFT = (WIDTH / 2) - (2 * MARGIN);
	public static final int WIDTH_RIGHT = WIDTH - (WIDTH / 2) - (2 * MARGIN);
	
	public static final String STYLE_CONTENT = "user-content-wrapper";
	public static final String STYLE_CONTENT_LEFT = "content-left";
	public static final String STYLE_CONTENT_RIGHT = "content-right";
	public static final String STYLE_WRAPPER = "wrapper";
	public static final String STYLE_CONTROL = "control-panel";
	public static final String STYLE_FOOTER = "footer-panel";

	private FlowPanel mainPanel_;
	private SimplePanel top_;
	private SimplePanel left_;
	private SimplePanel right_;
	private SimplePanel bottom_;

	/**
	 * constructor, contains the view
	 */
	@Inject
	public StartPageView() {
		mainPanel_ = new FlowPanel();
		mainPanel_.setStyleName(STYLE_WRAPPER);

		top_ = new SimplePanel();
		top_.setStyleName(STYLE_CONTROL);
		mainPanel_.add(top_);

		FlowPanel content = new FlowPanel();
		content.getElement().getStyle().setMarginTop(110, Unit.PX);
		content.getElement().getStyle().setBottom(15, Unit.PX);
		content.setStyleName(STYLE_CONTENT);
		mainPanel_.add(content);

		left_ = new SimplePanel();
		left_.getElement().getStyle().setMargin(MARGIN, Unit.PX);
		left_.setStyleName(STYLE_CONTENT_LEFT);
		left_.setWidth(WIDTH_LEFT + "px");
		content.add(left_);

		right_ = new SimplePanel();
		right_.getElement().getStyle().setMargin(MARGIN, Unit.PX);
		right_.setStyleName(STYLE_CONTENT_RIGHT);
		right_.setWidth(WIDTH_RIGHT + "px");
		content.add(right_);
		
		bottom_ = new SimplePanel();
		bottom_.setStyleName(STYLE_FOOTER);
		content.add(bottom_);
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == StartPagePresenter.TYPE_CONTENT_CONTROL) {
			setContentControl(content);
		} else if (slot == StartPagePresenter.TYPE_CONTENT_LEFT) {
			setContentLeft(content);
		} else if (slot == StartPagePresenter.TYPE_CONTENT_RIGHT) {
			setContentRight(content);
		} else if (slot == StartPagePresenter.TYPE_CONTENT_BOTTOM) {
			setContentBottom(content);
		} else {
			super.setInSlot(slot, content);
		}
	}
	
	private void setContentControl(Widget content) {
		if (top_.getWidget() != null) {
			top_.clear();
		}
		top_.setWidget(content);
	}

	private void setContentLeft(Widget content) {
		if (left_.getWidget() != null) {
			left_.clear();
		}
		left_.setWidget(content);
	}

	private void setContentRight(Widget content) {
		if (right_.getWidget() != null) {
			right_.clear();
		}
		right_.setWidget(content);
	}
	
	private void setContentBottom(Widget content) {
		if (bottom_.getWidget() != null) {
			bottom_.clear();
		}
		bottom_.setWidget(content);
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}
}
