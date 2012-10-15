package webapp.client.event.xlocation;

import com.google.gwt.event.shared.GwtEvent;

/**
 * the event when a message dialog is shown
 *
 * @author David Pichsenmeister
 */
public class XLocationPopupEvent extends GwtEvent<IXLocationPopupEventHandler> {

	public static Type<IXLocationPopupEventHandler> TYPE = new Type<IXLocationPopupEventHandler>();

	/**
	 * constructor
	 */
	public XLocationPopupEvent() {
	}

	@Override
	public Type<IXLocationPopupEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(IXLocationPopupEventHandler handler) {
		handler.onXLocationPopupShow(this);
	}
}
