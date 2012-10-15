package webapp.client.presenter.xlocation;

import webapp.model.XLocation;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
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
public class ShowLocationPopupPresenter extends
		PresenterWidget<ShowLocationPopupPresenter.IView> implements
		NavigationHandler {

	/**
	 * 
	 * the interface for the location popup view
	 * 
	 * @author David Pichsenmeister
	 * 
	 */
	public interface IView extends PopupView {
		void setLocation(XLocation location);
	}

	private final IView view_;
	private HandlerRegistration handlerRegistration;

	@Inject
	public ShowLocationPopupPresenter(EventBus eventBus, IView view) {
		super(eventBus, view);

		view_ = view;
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

	public void setLocation(XLocation location) {
		view_.setLocation(location);
	}
}