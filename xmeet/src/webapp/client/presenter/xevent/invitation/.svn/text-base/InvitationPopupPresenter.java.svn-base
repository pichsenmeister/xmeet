package webapp.client.presenter.xevent.invitation;

import java.util.List;

import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.xuser.LoggedInGatekeeper;
import webapp.client.rpc.xuser.RPCUserAsync;
import webapp.model.XLocationEntry;
import webapp.model.XUser;
import webapp.model.enums.XContactStatus;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
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
public class InvitationPopupPresenter extends PresenterWidget<InvitationPopupPresenter.IView>
		implements
		NavigationHandler {

	/**
	 * 
	 * the interface for the location popup view
	 * 
	 * @author David Pichsenmeister
	 * 
	 */
	public interface IView extends PopupView {

		void setCallback(ITypedCallback<List<XUser>> callback);

		void setUser(List<XUser> user);

	}

	@Inject
	private Provider<LoggedInGatekeeper> pGatekeeper_;

	private final IView view_;
	private final RPCUserAsync rpcUser_;
	private final EventBus eventBus_;
	private HandlerRegistration handlerRegistration;
	private XLocationEntry entry_;

	@Inject
	public InvitationPopupPresenter(RPCUserAsync rpcUser, EventBus eventBus, IView view) {
		super(eventBus, view);

		view_ = view;
		eventBus_ = eventBus;
		rpcUser_ = rpcUser;
	}

	@Override
	protected void onBind() {
		super.onBind();

		view_.setCallback(new ITypedCallback<List<XUser>>() {
			@Override
			public void execute(List<XUser> user) {
				doSave(user);
			}
		});
	}

	@Override
	public void onReveal() {
		super.onReveal();

		unregisterNavigationHandler();
		handlerRegistration = addHandler(NavigationEvent.getType(), this);

		rpcUser_.loadListener(pGatekeeper_.get().getLoggedInUser(), XContactStatus.PERMIT,
				new AsyncCallback<List<XUser>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("no listener loaded");
					}

					@Override
					public void onSuccess(List<XUser> result) {
						view_.setUser(result);
					}
				});
	}

	@Override
	public void onHide() {
		super.onHide();
	}

	@Override
	public void onNavigation(NavigationEvent navigationEvent) {
		view_.hide();
	}

	public void setXLocationEntry(XLocationEntry entry) {
		entry_ = entry;
	}

	private void doSave(List<XUser> user) {

	}

	private void unregisterNavigationHandler() {
		if (handlerRegistration != null) {
			handlerRegistration.removeHandler();
			handlerRegistration = null;
		}
	}
}