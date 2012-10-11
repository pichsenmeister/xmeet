package webapp.client.event.search;

import com.google.gwt.event.shared.EventHandler;

/**
 * the event handling interface of search
 * 
 * @author David Pichsenmeister
 */
public interface ISearchEventHandler extends EventHandler {

	/**
	 * method called on eventhandling
	 * 
	 * @param event the event
	 */
	void onSearch(SearchEvent event);
}
