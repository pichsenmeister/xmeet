package webapp.client;

import java.util.Map;

import webapp.client.event.IRevealPlaceEventHandler;
import webapp.client.event.RevealPlaceEvent;
import webapp.client.event.XNameToken;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.proxy.AsyncCallFailEvent;
import com.gwtplatform.mvp.client.proxy.AsyncCallFailHandler;
import com.gwtplatform.mvp.client.proxy.AsyncCallStartEvent;
import com.gwtplatform.mvp.client.proxy.AsyncCallStartHandler;
import com.gwtplatform.mvp.client.proxy.AsyncCallSucceedEvent;
import com.gwtplatform.mvp.client.proxy.AsyncCallSucceedHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

/**
 * the application controller, implements the GWT Place Manager
 *
 * @author David Pichsenmeister
 */
@Singleton
public class AppController extends PlaceManagerImpl {

	private final EventBus eventBus_;

	/**
	 * the constructor, binds eventhandlers on eventbus
	 *
	 * @param eventBus
	 *            the GWT EventBus
	 * @param tokenFormatter
	 *            the TokenFormatter for PlaceRequests
	 */
	@Inject
	public AppController(EventBus eventBus, TokenFormatter tokenFormatter) {
		super(eventBus, tokenFormatter);
		eventBus_ = eventBus;

		bind();
	}

	/**
	 * this method is called when objects finishes
	 */
	private void bind() {

		eventBus_.addHandler(AsyncCallStartEvent.getType(), new AsyncCallStartHandler() {
			@Override
			public void onAsyncCallStart(AsyncCallStartEvent asyncCallStartEvent) {
				RootPanel.get().getElement().getStyle().setCursor(Cursor.WAIT);
			}
		});

		eventBus_.addHandler(AsyncCallSucceedEvent.getType(), new
				AsyncCallSucceedHandler() {
			@Override
			public void onAsyncCallSucceed(AsyncCallSucceedEvent asyncCallSucceedEvent) {
				RootPanel.get().getElement().getStyle().setCursor(Cursor.AUTO);
			}
		});

		eventBus_.addHandler(AsyncCallFailEvent.getType(), new AsyncCallFailHandler() {
			@Override
			public void onAsyncCallFail(AsyncCallFailEvent asyncCallFailEvent) {
				RootPanel.get().getElement().getStyle().setCursor(Cursor.AUTO);
			}
		});

		eventBus_.addHandler(RevealPlaceEvent.TYPE, new IRevealPlaceEventHandler() {
			@Override
			public void doRevealPlace(RevealPlaceEvent event) {
				PlaceRequest request = new PlaceRequest(event.getXNameToken());
				Map<String, String> map = event.getParameter();
				if(map != null) {
					for(String key : map.keySet()) {
					   request = request.with(key, map.get(key)); 
					}
				}
				revealPlace(request);
			}
		});
	}

	@Override
	public void revealDefaultPlace() {
		revealPlace(new PlaceRequest(XNameToken.START), false);
	}

	@Override
	public PlaceRequest getCurrentPlaceRequest() {
		return super.getCurrentPlaceRequest();
	}
}
