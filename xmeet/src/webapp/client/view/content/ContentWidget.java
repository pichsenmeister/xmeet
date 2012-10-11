package webapp.client.view.content;

import webapp.client.presenter.content.ContentPresenterWidget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for html content, implements the interface declared in presenter
 *
 * @author David Pichsenmeister
 */
public class ContentWidget extends ViewImpl implements ContentPresenterWidget.IView {

	private FlowPanel mainPanel_;

	/**
	 * constructor, contains the view
	 */
	@Inject
	public ContentWidget() {
		mainPanel_ = new FlowPanel();
	}

	@Override
	public void setHtmlContent(String content) {
		HTML html = new HTML(content);
		mainPanel_.add(html);
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}
}
