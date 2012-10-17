package webapp.client.presenter.xuser;

import webapp.client.callback.ICallback;
import webapp.client.event.xuser.IXUserRegisterEventHandler;
import webapp.client.event.xuser.XUserLoginEvent;
import webapp.client.event.xuser.XUserRegisterEvent;
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
 * the presenter for XUser register
 * 
 * @author David Pichsenmeister
 */
public class RegisterWidgetPresenter extends
		PresenterWidget<RegisterWidgetPresenter.IView> {

	/**
	 * the interface for the register view
	 * 
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {
		void setCallback(ICallback callback);

		String getUserName();

		String getPassword();

		String getName();

		String getEmail();

		boolean validate();
	}

	private RPCUserAsync rpcUser_;
	private EventBus eventBus_;
	private IView view_;

	/**
	 * the constructor
	 * 
	 * @param rpcUser
	 *            rpc service for database connection
	 * @param eventBus
	 *            the GWT EventBus
	 * @param view
	 *            the view
	 */
	@Inject
	public RegisterWidgetPresenter(RPCUserAsync rpcUser, EventBus eventBus,
			IView view) {
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
				rpcUser_.loadXUser(view_.getEmail(),
						new AsyncCallback<XUser>() {
							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Failed to check username: "
										+ caught.getMessage());
							}

							@Override
							public void onSuccess(XUser user) {
								String password = Password.hashPassword(view_
										.getPassword());
								if ((user == null) && view_.validate()) {
									XUser tmpUser = new XUser();
									tmpUser.setName(view_.getName()
											.toLowerCase());
									tmpUser.setEmail(view_.getEmail()
											.toLowerCase());
									tmpUser.setPassword(password);
									eventBus_.fireEvent(new XUserRegisterEvent(
											tmpUser));
								} else {
									Window.alert("some information is missing");
								}
							}
						});
			}
		});

		eventBus_.addHandler(XUserRegisterEvent.TYPE,
				new IXUserRegisterEventHandler() {
					@Override
					public void onXUserRegister(XUserRegisterEvent event) {
						XUser tmpUser = event.getXUser();
						doXUserRegister(tmpUser);
					}
				});

	}

	private void doXUserRegister(final XUser user) {
		rpcUser_.registerUser(user, new AsyncCallback<XUser>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO error handling
				Window.alert("User can't be saved");
			}

			@Override
			public void onSuccess(XUser user) {
				eventBus_.fireEvent(new XUserLoginEvent(user));
			}
		});
	}
}
