package webapp.client.presenter.xuser;

import webapp.client.callback.ICallback;
import webapp.client.event.xuser.XUserLoginEvent;
import webapp.client.presenter.Password;
import webapp.client.rpc.xuser.RPCUserAsync;
import webapp.model.XUser;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * the presenter for control panel
 * 
 * @author David Pichsenmeister
 */
public class ControlPanelPresenterWidget extends PresenterWidget<ControlPanelPresenterWidget.IView> {

	/**
	 * the interface for the user's control panel view
	 * 
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {
		String getEmail();

		String getPassword();

		void setCallback(ICallback callback);
	}

	public static final int TIMER = 2 * 60 * 1000; // minute * seconds *
													// milliseconds
	private final RPCUserAsync rpcUser_;
	private final EventBus eventBus_;
	private final IView view_;

	/**
	 * the constructor
	 * 
	 * @param rpcService
	 *            rpcService for database connection
	 * @param eventBus
	 *            the GWT EventBus
	 * @param view
	 *            the view
	 */
	@Inject
	public ControlPanelPresenterWidget(RPCUserAsync rpcUser, EventBus eventBus, IView view) {
		super(eventBus, view);

		rpcUser_ = rpcUser;
		eventBus_ = eventBus;
		view_ = view;
	}

	@Override
	protected void onBind() {
		super.onBind();

		view_.setCallback(new ICallback() {
			@Override
			public void execute() {
				rpcUser_.loadXUser(view_.getEmail(), new
						AsyncCallback<XUser>() {
							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Failed to load User: " + caught.getMessage());
								// TODO error handling
							}

							@Override
							public void onSuccess(XUser user) {
								String password = Password.hashPassword(view_.getPassword());
								if ((user != null) && user.getPassword().equals(password)) {
									eventBus_.fireEvent(new XUserLoginEvent(user));
								} else {
									Window.alert("username or password incorrect");
									// TODO error handling
								}
							}
						});
			}
		});
	}
}
