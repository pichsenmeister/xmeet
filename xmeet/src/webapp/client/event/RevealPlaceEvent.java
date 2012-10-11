package webapp.client.event;

import java.util.Map;

import com.google.gwt.event.shared.GwtEvent;

/**
 * the event to reveal new place in application
 *
 * @author David Pichsenmeister
 */
public class RevealPlaceEvent extends GwtEvent<IRevealPlaceEventHandler> {

	public static Type<IRevealPlaceEventHandler> TYPE = new Type<IRevealPlaceEventHandler>();
	private String nameToken_ = null;
	private Map<String, String> params_ = null;

	/**
	 * constructor
	 *
	 * @param nameToken
	 *            the name token of the place to reveal
	 */
	public RevealPlaceEvent(String nameToken) {
		nameToken_ = nameToken;
	}
	
	/**
	 * constructor
	 *
	 * @param nameToken
	 *            the name token of the place to reveal
	 * @param params
	 *            the hashmap with the url parameters
	 */
	public RevealPlaceEvent(String nameToken, Map<String, String> params) {
		nameToken_ = nameToken;
		params_ = params;
	}

	/**
	 * returns the name token of place to reveal
	 *
	 * @return the XNameToken
	 */
	public String getXNameToken() {
		return nameToken_;
	}
	
	/**
	 * returns parameters of the place request
	 *
	 * @return the hashmap with the url parameters or <code>null</code> if no parameters exist
	 */
	public Map<String, String> getParameter() {
		return params_;
	}

	@Override
	public Type<IRevealPlaceEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(IRevealPlaceEventHandler handler) {
		handler.doRevealPlace(this);
	}
}

