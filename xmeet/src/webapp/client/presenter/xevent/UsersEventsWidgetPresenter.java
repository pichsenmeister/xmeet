package webapp.client.presenter.xevent;

import java.util.HashMap;

import webapp.client.callback.ITypedCallback;
import webapp.client.event.RevealPlaceEvent;
import webapp.client.event.XNameToken;
import webapp.client.presenter.xuser.LoggedInGatekeeper;
import webapp.model.XUser;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * the presenter for user's events
 * 
 * @author David Pichsenmeister
 */
public class UsersEventsWidgetPresenter extends
		PresenterWidget<UsersEventsWidgetPresenter.IView> {

	/**
	 * the interface for the user's events view
	 * 
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {
		void setCallbackUser(ITypedCallback<XUser> callback);
	}

	private static final int MAX_RESULT = 5;

	private EventBus eventBus_;
	private IView view_;
	private XUser loggedUser_;
	private XUser user_;

	@Inject
	private Provider<LoggedInGatekeeper> pGatekeeper_;

	/**
	 * the constructor
	 * 
	 * @param eventBus
	 *            the GWT EventBus
	 * @param view
	 *            the view
	 */
	@Inject
	public UsersEventsWidgetPresenter(EventBus eventBus, IView view) {
		super(eventBus, view);

		eventBus_ = eventBus;
		view_ = view;
	}

	@Override
	protected void onBind() {
		super.onBind();

		view_.setCallbackUser(new ITypedCallback<XUser>() {
			@Override
			public void execute(XUser user) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("id", user.getUserID().toString());
				eventBus_.fireEvent(new RevealPlaceEvent(XNameToken.USER,
						params));
			}
		});
	}

	/**
	 * sets the users, which events are loaded
	 * 
	 * @param user
	 *            the XUser
	 */
	public void setUser(XUser user) {
		loggedUser_ = pGatekeeper_.get().getLoggedInUser();
		user_ = user;
	}
}
