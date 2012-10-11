package webapp.client.event.xlocation;

import com.google.gwt.event.shared.EventHandler;

/**
 * the event handling interface to show XLocation Popup
 *
 * @author David Pichsenmeister
 */
public interface IXLocationPopupEventHandler extends EventHandler {

	/**
	 * method called on eventhandling
	 *
	 * @param event the event
	 */
	void onXLocationPopupShow(XLocationPopupEvent event);

}
