package webapp.client.presenter.xlocation;

import java.util.List;

import webapp.client.callback.ICallback;
import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.xevent.invitation.InvitationPopupPresenter;
import webapp.client.presenter.xuser.LoggedInGatekeeper;
import webapp.client.rpc.xlocation.RPCLocationAsync;
import webapp.client.rpc.xuser.RPCUserAsync;
import webapp.model.XLocation;
import webapp.model.XLocationEntry;
import webapp.model.XUser;
import webapp.model.enums.XVisibility;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.RevealRootPopupContentEvent;

/**
 * the presenter for the event creator
 * 
 * @author David Pichsenmeister
 */
public class CreateWidgetPresenter extends
		PresenterWidget<CreateWidgetPresenter.IView> {

	/**
	 * the interface for the event creator view
	 * 
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {
		void setCallbackLocation(ITypedCallback<String> callback);

		void setCallbackNewLocation(ICallback callback);

		void setCallbackVisibility(ITypedCallback<XVisibility> callback);

		void setCallbackSubmit(ITypedCallback<XLocationEntry> callback);

		void setCallbackNext(ICallback callback);

		void setCallbackBack(ICallback callback);

		void setLocations(List<XLocation> locations);

		void setVisibility(XVisibility type);
	}

	private static final int MAX_RESULT = 5;

	private final EventBus eventBus_;
	private final RPCLocationAsync rpcLocation_;
	private final RPCUserAsync rpcUser_;
	private final IView view_;

	private XUser user_;
	private int startLoc_ = 0;
	private String search_;

	@Inject
	private Provider<LoggedInGatekeeper> pGatekeeper_;
	@Inject
	private Provider<LocationPopupPresenter> pLocation_;
	@Inject
	private Provider<InvitationPopupPresenter> pInvitation_;

	/**
	 * the constructor
	 * 
	 * @param rpcLocation
	 *            rpc service for database connection
	 * @param rpcUser
	 *            rpc service for database connection
	 * @param eventBus
	 *            the GWT EventBus
	 * @param view
	 *            the view
	 */
	@Inject
	public CreateWidgetPresenter(RPCLocationAsync rpcLocation,
			RPCUserAsync rpcUser, EventBus eventBus, IView view) {
		super(eventBus, view);

		eventBus_ = eventBus;
		rpcUser_ = rpcUser;
		rpcLocation_ = rpcLocation;
		view_ = view;
	}

	@Override
	protected void onBind() {
		super.onBind();

		view_.setCallbackLocation(new ITypedCallback<String>() {
			@Override
			public void execute(String location) {
				search_ = location;
				doSearchLocation(location);
			}
		});

		view_.setCallbackNewLocation(new ICallback() {
			@Override
			public void execute() {
				createLocation();
			}
		});

		view_.setCallbackVisibility(new ITypedCallback<XVisibility>() {
			@Override
			public void execute(XVisibility type) {
				doSaveSetting(type);
			}
		});

		view_.setCallbackSubmit(new ITypedCallback<XLocationEntry>() {

			@Override
			public void execute(XLocationEntry entry) {
				doSaveLocationEntry(entry);
			}
		});

		view_.setCallbackBack(new ICallback() {

			@Override
			public void execute() {
				startLoc_ -= 5;
				doSearchLocation(search_);
			}
		});

		view_.setCallbackBack(new ICallback() {

			@Override
			public void execute() {
				startLoc_ += 5;
				doSearchLocation(search_);
			}
		});
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		user_ = pGatekeeper_.get().getLoggedInUser();
		view_.setVisibility(user_.getSettings().getVisibility());
	}

	/**
	 * saves a XLocationEntry
	 * 
	 * @param location
	 *            the location entry to save
	 */
	private void doSaveLocationEntry(XLocationEntry location) {
		rpcLocation_.saveXLocationEntry(location, user_,
				new AsyncCallback<XLocationEntry>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO error handling
						Window.alert("fail location entry create");
					}

					@Override
					public void onSuccess(XLocationEntry result) {
						Window.alert("location entry saved");
					}
				});
	}

	/**
	 * searches for an XLocations
	 * 
	 * @param search
	 *            the location string to search
	 */
	private void doSearchLocation(String search) {
		rpcLocation_.searchLocations(search, startLoc_, MAX_RESULT,
				new AsyncCallback<List<XLocation>>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("search fail");
					}

					@Override
					public void onSuccess(List<XLocation> result) {
						view_.setLocations(result);
					}
				});
	}

	private void doSaveSetting(XVisibility type) {
		user_.getSettings().setVisibility(type);
		rpcUser_.saveXUser(user_, new AsyncCallback<XUser>() {

			@Override
			public void onFailure(Throwable caught) {
				// do nothing
			}

			@Override
			public void onSuccess(XUser result) {
				// do nothing
			}
		});
	}

	private void createLocation() {
		LocationPopupPresenter loc = pLocation_.get();
		RevealRootPopupContentEvent.fire(this, loc);
	}

	// private void showInvitationDialog(XEvent event) {
	// InvitationPopupPresenter loc = pInvitation_.get();
	// loc.setXEvent(event);
	// RevealRootPopupContentEvent.fire(this, loc);
	// }
}
