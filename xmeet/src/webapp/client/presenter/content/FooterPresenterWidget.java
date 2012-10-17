package webapp.client.presenter.content;

import webapp.client.presenter.xuser.LoggedInGatekeeper;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * the presenter for the footer
 * 
 * @author David Pichsenmeister
 */
public class FooterPresenterWidget extends
		PresenterWidget<FooterPresenterWidget.IView> {

	/**
	 * the interface for the footer view
	 * 
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {
		void isUserLogged(boolean logged);
	}

	private IView view_;

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
	public FooterPresenterWidget(EventBus eventBus, IView view) {
		super(eventBus, view);

		view_ = view;
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		if (pGatekeeper_.get().getLoggedInUser() != null) {
			view_.isUserLogged(true);
		} else {
			view_.isUserLogged(false);
		}
	}
}
