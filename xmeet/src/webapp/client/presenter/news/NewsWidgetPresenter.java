package webapp.client.presenter.news;

import java.util.HashMap;
import java.util.List;

import webapp.client.callback.ICallback;
import webapp.client.callback.ITypedCallback;
import webapp.client.event.RevealPlaceEvent;
import webapp.client.event.XNameToken;
import webapp.client.presenter.xlocation.ShowLocationPopupPresenter;
import webapp.client.presenter.xuser.LoggedInGatekeeper;
import webapp.client.rpc.xlocation.RPCLocationAsync;
import webapp.model.XLocation;
import webapp.model.XLocationEntry;
import webapp.model.XUser;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.RevealRootPopupContentEvent;

/**
 * the presenter for news
 * 
 * @author David Pichsenmeister
 */
public class NewsWidgetPresenter extends
		PresenterWidget<NewsWidgetPresenter.IView> {

	/**
	 * the interface for the news view
	 * 
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {
		void setLocations(List<XLocationEntry> entries);

		void addLocations(List<XLocationEntry> entries);

		void setCallbackUser(ITypedCallback<XUser> callback);

		void setCallbackLocation(ITypedCallback<XLocation> callback);

		void setCallbackMore(ICallback callback);

		void enableShowMore(boolean enable);
	}

	private static final int MAX_RESULT = 10;

	private final IView view_;
	private EventBus eventBus_;
	private XUser loggedUser_;
	private RPCLocationAsync rpcLocation_;

	private int start_ = 0;

	@Inject
	private Provider<LoggedInGatekeeper> pGatekeeper_;
	@Inject
	private Provider<ShowLocationPopupPresenter> pLocation_;

	/**
	 * the constructor
	 * 
	 * @param rpcLocation
	 *            rpc service for database connection
	 * @param eventBus
	 *            the GWT EventBus
	 * @param view
	 *            the view
	 */
	@Inject
	public NewsWidgetPresenter(RPCLocationAsync rpcLocation, EventBus eventBus,
			IView view) {
		super(eventBus, view);

		view_ = view;
		eventBus_ = eventBus;
		rpcLocation_ = rpcLocation;
	}

	@Override
	protected void onBind() {
		super.onBind();

		view_.setCallbackUser(new ITypedCallback<XUser>() {

			@Override
			public void execute(XUser type) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("id", loggedUser_.getUserID().toString());
				eventBus_.fireEvent(new RevealPlaceEvent(XNameToken.USER,
						params));
			}
		});

		view_.setCallbackLocation(new ITypedCallback<XLocation>() {

			@Override
			public void execute(XLocation type) {
				showLocation(type);
			}
		});

		view_.setCallbackMore(new ICallback() {

			@Override
			public void execute() {
				loadLocationEntries();
			}
		});
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		start_ = 0;

		loggedUser_ = pGatekeeper_.get().getLoggedInUser();

		loadLocationEntries();
	}

	private void loadLocationEntries() {
		rpcLocation_.loadLocationEntries(loggedUser_, start_, MAX_RESULT,
				new AsyncCallback<List<XLocationEntry>>() {

					@Override
					public void onSuccess(List<XLocationEntry> result) {
						if (start_ == 0) {
							view_.setLocations(result);
						} else {
							view_.addLocations(result);
						}
						if (result.size() < MAX_RESULT) {
							view_.enableShowMore(false);
						} else {
							view_.enableShowMore(true);
						}
						start_ += MAX_RESULT;
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out
								.println("error loading location entries for user: "
										+ loggedUser_.getName());
					}
				});
	}

	private void showLocation(XLocation location) {
		ShowLocationPopupPresenter loc = pLocation_.get();
		loc.setLocation(location);
		RevealRootPopupContentEvent.fire(this, loc);
	}

}
