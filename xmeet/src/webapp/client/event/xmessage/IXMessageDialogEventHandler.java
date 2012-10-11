package webapp.client.event.xmessage;

import com.google.gwt.event.shared.EventHandler;

/**
 * the event handling interface to show XMessage Dialog
 *
 * @author David Pichsenmeister
 */
public interface IXMessageDialogEventHandler extends EventHandler {

	/**
	 * method called on eventhandling
	 *
	 * @param event the event
	 */
	void onXMessageDialogShow(XMessageDialogEvent event);

}
