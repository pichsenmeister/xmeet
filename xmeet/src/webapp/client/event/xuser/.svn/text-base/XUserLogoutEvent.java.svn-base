package webapp.client.event.xuser;

import com.google.gwt.event.shared.GwtEvent;

/**
 * the event when a user logout
 * 
 * @author David Pichsenmeister
 */
public class XUserLogoutEvent extends GwtEvent<IXUserLogoutEventHandler> {

	public static Type<IXUserLogoutEventHandler> TYPE = new Type<IXUserLogoutEventHandler>();
	
	/**
	 * constructor
	 */
	public XUserLogoutEvent() {
		
	}
	
	@Override
	protected void dispatch(IXUserLogoutEventHandler handler) {
		handler.onXUserLogout(this);	
	}

	@Override
	public Type<IXUserLogoutEventHandler> getAssociatedType() {
		return TYPE;
	}

}
