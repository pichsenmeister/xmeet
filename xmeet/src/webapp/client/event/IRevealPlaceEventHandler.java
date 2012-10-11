package webapp.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * the event handling interface to reveal new place
 *
 * @author David Pichsenmeister
 */
public interface IRevealPlaceEventHandler extends EventHandler {

	/**
	 * method called on eventhandling
	 *
	 * @param event
	 *            the event
	 */
	void doRevealPlace(RevealPlaceEvent event);

}
