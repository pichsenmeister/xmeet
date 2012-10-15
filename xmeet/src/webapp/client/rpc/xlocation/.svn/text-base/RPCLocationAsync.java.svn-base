package webapp.client.rpc.xlocation;

import java.util.List;

import webapp.model.XLocation;
import webapp.model.XLocationEntry;
import webapp.model.XUser;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RPCLocationAsync {
	
	/**
	 * saves a XLocation instance
	 *
	 * @param location
	 *            the XLocation to save
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void saveXLocation(XLocation location, AsyncCallback<XLocation> callback);
	
	/**
	 * saves a XLocationEntry instance
	 *
	 * @param location
	 *            the XLocationEntry to save
	 * @param owner
	 *            the XUser who creates entry
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void saveXLocationEntry(XLocationEntry location, XUser user, AsyncCallback<XLocationEntry> callback);

	
	/**
	 * loads list with all XLocations suitable for the arguments
	 *
	 * @param location
	 *            the argument to search for location
	 * @param start
	 * 			  start index of query
	 * @param maxResult
	 * 			  end index of query
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void searchLocations(String location, int start, int maxResult, AsyncCallback<List<XLocation>> callback);
	
	/**
	 * loads list with all XLocationEntries which XUser visited
	 *
	 * @param user
	 *            the xuser which locations are loaded
	 * @param start
	 * 			  the start value
	 * @param maxResult
	 * 			  the size of locations which are loaded
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadLocationEntries(XUser user, int start, int maxResult, AsyncCallback<List<XLocationEntry>> callback);
	
}
