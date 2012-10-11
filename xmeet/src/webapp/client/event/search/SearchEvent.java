package webapp.client.event.search;

import com.google.gwt.event.shared.GwtEvent;

/**
 * the event of a search
 *
 * @author David Pichsenmeister
 */
public class SearchEvent extends GwtEvent<ISearchEventHandler> {

	public static Type<ISearchEventHandler> TYPE = new Type<ISearchEventHandler>();
	private String search_ = null;

	/**
	 * constructor
	 *
	 * @param search  the search string
	 */
	public SearchEvent(String search) {
		search_ = search;
	}

	/**
	 * returns the search string
	 *
	 * @return  the search string
	 */
	public String getSearchString() {
		return search_;
	}

	@Override
	public Type<ISearchEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ISearchEventHandler handler) {
		handler.onSearch(this);
	}

}
