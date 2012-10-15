package webapp.client.presenter.xuser;

import webapp.client.event.RevealPlaceEvent;
import webapp.client.event.XNameToken;
import webapp.client.event.xuser.IXUserLoginEventHandler;
import webapp.client.event.xuser.IXUserLogoutEventHandler;
import webapp.client.event.xuser.XUserLoginEvent;
import webapp.client.event.xuser.XUserLogoutEvent;
import webapp.client.rpc.xuser.RPCUserAsync;
import webapp.model.XUser;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;

@Singleton
public class LoggedInGatekeeper implements Gatekeeper {

	private XUser currentUser = null;

	@Inject
	public LoggedInGatekeeper(final RPCUserAsync rpcUser, final EventBus eventBus) {

		eventBus.addHandler(XUserLoginEvent.TYPE, new IXUserLoginEventHandler() {
			@Override
			public void onXUserLogin(XUserLoginEvent event) {
				currentUser = event.getXUser();
				eventBus.fireEvent(new RevealPlaceEvent(XNameToken.NEWS));

			}
		});

		eventBus.addHandler(XUserLogoutEvent.TYPE, new IXUserLogoutEventHandler() {
			@Override
			public void onXUserLogout(XUserLogoutEvent event) {
				currentUser = null;
				eventBus.fireEvent(new RevealPlaceEvent(XNameToken.START));
			}
		});
	}

	@Override
	public boolean canReveal() {
		if (currentUser != null) {
			return true;
		} else {
			return false;
		}
	}

	public XUser getLoggedInUser() {
		return currentUser;
	}

	public void setLoggedInUser(XUser user) {
		currentUser = user;
	}
}
