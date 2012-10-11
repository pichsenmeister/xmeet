package webapp.client.event.xuser;

import webapp.model.XUser;

import com.google.gwt.event.shared.GwtEvent;

/**
 * the event when a user login
 *
 * @author David Pichsenmeister
 */
public class XUserLoginEvent extends GwtEvent<IXUserLoginEventHandler> {

	public static Type<IXUserLoginEventHandler> TYPE = new Type<IXUserLoginEventHandler>();
	private XUser xuser = null;

	/**
	 * constructor
	 *
	 * @param user  the XUser to login
	 */
	public XUserLoginEvent(XUser user) {
		xuser = user;
	}

	/**
	 * returns the user to login
	 *
	 * @return  the XUser to login
	 */
	public XUser getXUser() {
		return xuser;
	}

	@Override
	public Type<IXUserLoginEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(IXUserLoginEventHandler handler) {
		handler.onXUserLogin(this);
	}
}
