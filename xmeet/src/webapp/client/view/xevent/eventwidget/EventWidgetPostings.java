package webapp.client.view.xevent.eventwidget;

import java.util.List;

import webapp.model.XPosting;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EventWidgetPostings extends Composite {

	private static EventWidgetPostingsUiBinder uiBinder = GWT
			.create(EventWidgetPostingsUiBinder.class);

	interface EventWidgetPostingsUiBinder extends
			UiBinder<Widget, EventWidgetPostings> {
	}
	
	public static final String COMMENT_SHOW = "kommentare";
	public static final String STYLE_POSTING_HEADER = "posting-header";
	
	@UiField
	DisclosurePanel postPanel;

	public EventWidgetPostings() {
		initWidget(uiBinder.createAndBindUi(this));
		
		postPanel.setAnimationEnabled(true);	
		postPanel.setOpen(false);
		postPanel.setVisible(false);
	}

	public void setPostings(List<XPosting> postings, boolean open) {
		if(postings != null && postings.size() > 0) {
			postPanel.setVisible(true);
			Label header = new Label(COMMENT_SHOW + " (" + postings.size() +")");
			header.setStyleName(STYLE_POSTING_HEADER);
			postPanel.setHeader(header);

			postPanel.setContent(paintPostingsContent(postings));
			
			if(open) {
				postPanel.setOpen(true);
			} else {
				postPanel.setOpen(false);
			}
		}
	}
	
	private AbsolutePanel paintPostingsContent(List<XPosting> postings) {
		AbsolutePanel panel = new AbsolutePanel();
		panel.getElement().getStyle().setMarginLeft(15, Unit.PX);
		panel.getElement().getStyle().setFontSize(11, Unit.PX);

		for(XPosting post : postings) {
			EventWidgetSinglePost singlePost = new EventWidgetSinglePost();
			singlePost.setPosting(post);
			panel.add(singlePost);
		}
		return panel;
	}

}
