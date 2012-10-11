package webapp.client.event.xmessage;

import webapp.model.XUser;

import com.google.gwt.event.shared.GwtEvent;

/**
 * the event when a message dialog is shown
 *
 * @author David Pichsenmeister
 */
public class XMessageDialogEvent extends GwtEvent<IXMessageDialogEventHandler> {

	public static Type<IXMessageDialogEventHandler> TYPE = new Type<IXMessageDialogEventHandler>();
	private XUser xuser = null;

	/**
	 * constructor
	 *
	 * @param user  the XUser to send message
	 */
	public XMessageDialogEvent(XUser user) {
		xuser = user;
	}

	/**
	 * returns the user to send message
	 *
	 * @return  the XUser to send message
	 */
	public XUser getXUser() {
		return xuser;
	}

	@Override
	public Type<IXMessageDialogEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(IXMessageDialogEventHandler handler) {
		handler.onXMessageDialogShow(this);
	}
}
