package webapp.client.view.xevent;

import java.util.ArrayList;
import java.util.List;

import webapp.client.presenter.xevent.EventFinderWidgetPresenter;
import webapp.client.view.customwidget.ListView;
import webapp.client.view.customwidget.ListView.IGenerator;
import webapp.client.view.xevent.eventfinder.EventFinder;
import webapp.model.XEvent;
import webapp.model.XEventFinder;
import webapp.model.XUser;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class EventFinderWidget extends ViewImpl implements EventFinderWidgetPresenter.IView {

	private ListView<XEventFinder> mainPanel_;

	/**
	 * constructor, contains the view
	 */
	public EventFinderWidget() {
		mainPanel_ = new ListView<XEventFinder>();

		mainPanel_.setGenerator(new IGenerator<XEventFinder>() {

			@Override
			public Widget generateWidget(XEventFinder model) {
				EventFinder finder = new EventFinder();
				finder.setData(model);
				return finder;
			}
		});
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}

	@Override
	public void setData(XUser user) {
		XEvent event = new XEvent();
		event.setDescription("#test #bla @postgarage irgendwas text");
		event.setOwner(user);

		XEventFinder eventFinder = new XEventFinder();
		eventFinder.setEvent(event);

		List<XEventFinder> store = new ArrayList<XEventFinder>();
		store.add(eventFinder);

		mainPanel_.setStore(store);
	}

}
