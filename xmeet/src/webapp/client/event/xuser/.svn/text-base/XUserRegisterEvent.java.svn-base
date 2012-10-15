package webapp.client.event.xuser;

import webapp.model.XUser;

import com.google.gwt.event.shared.GwtEvent;

/**
 * the event when a user registers
 * 
 * @author David Pichsenmeister
 */
public class XUserRegisterEvent extends GwtEvent<IXUserRegisterEventHandler>{
	
	public static Type<IXUserRegisterEventHandler> TYPE = new Type<IXUserRegisterEventHandler>();
	private XUser xuser = null;
	
	/**
	 * constructor
	 * 
	 * @param user the XUser to register
	 */
	public XUserRegisterEvent(XUser user) {
		xuser = user;
	}
	
	/**
	 * returns the user which registers
	 * 
	 * @return the XUser to register
	 */
	public XUser getXUser() {
		return xuser;
	}

	@Override
	public Type<IXUserRegisterEventHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(IXUserRegisterEventHandler handler) {
		handler.onXUserRegister(this);
	}

}
