package webapp.client.view.news;

import java.util.List;

import webapp.client.callback.ICallback;
import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.news.NewsWidgetPresenter;
import webapp.client.view.customwidget.ListView;
import webapp.client.view.customwidget.ListView.IGenerator;
import webapp.client.view.entry.entrywidget.EntryWidget;
import webapp.model.XLocation;
import webapp.model.XLocationEntry;
import webapp.model.XUser;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for news, implements the interface declared in presenter
 * 
 * @author David Pichsenmeister
 */
public class NewsWidget extends ViewImpl implements NewsWidgetPresenter.IView {

	public static final String LOADING = "loading";
	public static final String NO_DATA = "no entries found";
	public static final String AT = "@";

	private static final String STYLE_AT = "atWidget";

	private FlowPanel mainPanel_;
	private ListView<XLocationEntry> news_;
	private SimplePanel content_;
	private Button more_;

	private ITypedCallback<XUser> callbackUser_;
	private ITypedCallback<XLocation> callbackLoc_;
	private ICallback callbackMore_;

	/**
	 * constructor, contains the view
	 */
	public NewsWidget() {
		mainPanel_ = new FlowPanel();

		content_ = new SimplePanel();
		mainPanel_.add(content_);

		news_ = new ListView<XLocationEntry>();
		news_.setGenerator(new IGenerator<XLocationEntry>() {

			@Override
			public Widget generateWidget(XLocationEntry model) {
				EntryWidget widget = new EntryWidget();
				widget.setCallbackUser(callbackUser_);
				widget.setCallbackLocation(callbackLoc_);
				widget.setData(model);
				return widget;
			}
		});

		final Label loading = new Label();
		loading.setStyleName(LOADING);

		content_.setWidget(loading);

		more_ = new Button("more");
		more_.setVisible(false);
		more_.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (callbackMore_ != null) {
					callbackMore_.execute();
				}
			}
		});
		mainPanel_.add(more_);

		FlowPanel end = new FlowPanel();
		Label at = new Label(AT);
		end.add(at);
		at.setStyleName(STYLE_AT);
		mainPanel_.add(end);
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}

	@Override
	public void setLocations(List<XLocationEntry> entries) {
		if (entries.size() > 0) {
			news_.setStore(entries);
			content_.setWidget(news_);
		} else {
			Label nodata = new Label(NO_DATA);
			content_.setWidget(nodata);
		}
	}

	@Override
	public void addLocations(List<XLocationEntry> entries) {
		news_.addAll(entries);
	}

	@Override
	public void setCallbackUser(ITypedCallback<XUser> callback) {
		callbackUser_ = callback;
	}

	@Override
	public void setCallbackLocation(ITypedCallback<XLocation> callback) {
		callbackLoc_ = callback;
	}

	@Override
	public void setCallbackMore(ICallback callback) {
		callbackMore_ = callback;
	}

	@Override
	public void enableShowMore(boolean enable) {
		if (enable) {
			more_.setVisible(true);
		} else {
			more_.setVisible(false);
		}
	}
}
