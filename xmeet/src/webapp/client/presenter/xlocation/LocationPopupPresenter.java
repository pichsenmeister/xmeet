package webapp.client.presenter.xlocation;

import webapp.client.callback.ITypedCallback;
import webapp.client.rpc.xlocation.RPCLocationAsync;
import webapp.model.XLocation;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;

/**
 * 
 * the dialog presenter to send messages
 * 
 * @author David Pichsenmeister
 * 
 */
public class LocationPopupPresenter extends
		PresenterWidget<LocationPopupPresenter.IView> implements
		NavigationHandler {

	/**
	 * 
	 * the interface for the location popup view
	 * 
	 * @author David Pichsenmeister
	 * 
	 */
	public interface IView extends PopupView {

		void setCallback(ITypedCallback<XLocation> callback);

	}

	private final IView view_;
	private final RPCLocationAsync rpcLocation_;
	// private final EventBus eventBus_;
	private HandlerRegistration handlerRegistration;

	@Inject
	public LocationPopupPresenter(RPCLocationAsync rpcLocation,
			EventBus eventBus, IView view) {
		super(eventBus, view);

		view_ = view;
		// eventBus_ = eventBus;
		rpcLocation_ = rpcLocation;
	}

	@Override
	protected void onBind() {
		super.onBind();

		view_.setCallback(new ITypedCallback<XLocation>() {
			@Override
			public void execute(XLocation location) {
				doSaveLocation(location);
			}
		});
	}

	@Override
	public void onReveal() {
		super.onReveal();

		unregisterNavigationHandler();
		handlerRegistration = addHandler(NavigationEvent.getType(), this);
	}

	@Override
	public void onHide() {
		super.onHide();
	}

	@Override
	public void onNavigation(NavigationEvent navigationEvent) {
		view_.hide();
	}

	private void unregisterNavigationHandler() {
		if (handlerRegistration != null) {
			handlerRegistration.removeHandler();
			handlerRegistration = null;
		}
	}

	private void doSaveLocation(XLocation location) {
		rpcLocation_.saveXLocation(location, new AsyncCallback<XLocation>() {

			@Override
			public void onSuccess(XLocation result) {
				Window.alert("location saved");
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("save location failed");
			}
		});
	}
}