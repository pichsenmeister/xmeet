package webapp.client.view.content;

import webapp.client.presenter.content.ContentPresenter;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for content, implements the interface declared in presenter
 *
 * @author David Pichsenmeister
 */
public class ContentView extends ViewImpl implements ContentPresenter.IView {

	public static final int MARGIN = 15;
	public static final int WIDTH = 980;
	public static final int WIDTH_LEFT = (WIDTH / 2) - (2 * MARGIN);
	public static final int WIDTH_RIGHT = WIDTH - (WIDTH / 2) - (2 * MARGIN);
	public static final int HEIGHT_CONTROL = 110;
	public static final int HEIGHT_USER_CONTROL = 70;
	
	public static final String STYLE_WRAPPER = "wrapper";
	public static final String STYLE_CONTENT = "user-content-wrapper";
	public static final String STYLE_CONTENT_HTML = "html-content";
	public static final String STYLE_CONTROL = "control-panel";
	public static final String STYLE_USER_CONTROL = "user-control-panel";
	public static final String STYLE_FOOTER = "footer-panel";

	private FlowPanel mainPanel_;
	private FlowPanel content_;
	private SimplePanel top_;
	private SimplePanel html_;
	private SimplePanel bottom_;

	/**
	 * constructor, contains the view
	 */
	@Inject
	public ContentView() {
		mainPanel_ = new FlowPanel();
		mainPanel_.setStyleName(STYLE_WRAPPER);

		top_ = new SimplePanel();
		top_.setStyleName(STYLE_CONTROL);
		mainPanel_.add(top_);

		content_ = new FlowPanel();
		content_.getElement().getStyle().setMarginTop(HEIGHT_CONTROL, Unit.PX);
		content_.getElement().getStyle().setBottom(15, Unit.PX);
		content_.setStyleName(STYLE_CONTENT);
		mainPanel_.add(content_);

		html_ = new SimplePanel();
		html_.getElement().getStyle().setMargin(MARGIN, Unit.PX);
		html_.setStyleName(STYLE_CONTENT_HTML);
		content_.add(html_);
		
		bottom_ = new SimplePanel();
		bottom_.setStyleName(STYLE_FOOTER);
		content_.add(bottom_);
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == ContentPresenter.TYPE_CONTENT_CONTROL) {
			setContentControl(content);
		} else if (slot == ContentPresenter.TYPE_CONTENT_MAIN) {
			setContentMain(content);
		} else if (slot == ContentPresenter.TYPE_CONTENT_FOOTER) {
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

	private void setContentMain(Widget content) {
		if (html_.getWidget() != null) {
			html_.clear();
		}
		html_.setWidget(content);
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

	@Override
	public void isUserLogged(boolean logged) {
		if(logged) {
			top_.setStyleName(STYLE_USER_CONTROL);
			content_.getElement().getStyle().setMarginTop(HEIGHT_USER_CONTROL, Unit.PX);
		} else {
			top_.setStyleName(STYLE_CONTROL);
			content_.getElement().getStyle().setMarginTop(HEIGHT_CONTROL, Unit.PX);
		}
	}
}

