package webapp.client.rpc.xevent;

import java.util.List;

import webapp.model.XEvent;
import webapp.model.XRestrictedEvent;
import webapp.model.XUser;
import webapp.model.XUserEvent;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RPCEventAsync {

	/**
	 * saves a XEvent instance
	 * 
	 * @param event
	 *            the XEvent to save
	 * @param owner
	 *            the XUser as owner
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void saveXEvent(XEvent event, XUser owner, AsyncCallback<XEvent> callback);

	/**
	 * saves a XRestrictedEvent instance
	 * 
	 * @param event
	 *            the XEvent
	 * @param user
	 *            the list of XUser
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void saveRestrictedEvent(XEvent event, List<XUser> user, AsyncCallback<XRestrictedEvent> callback);

	/**
	 * reloads a XEvent
	 * 
	 * @param event
	 *            the XEvent to reload
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void reloadXEvent(XEvent event, AsyncCallback<XEvent> callback);

	/**
	 * loads list with all XUsers to XEvent
	 * 
	 * @param event
	 *            the XEvent which XUsers are loaded
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadXUserList(XEvent event, AsyncCallback<List<XUser>> callback);

	/**
	 * loads list with all XPostings
	 * 
	 * @param event
	 *            the XEvent which XPostings are loaded
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadXPostingList(XEvent event, AsyncCallback<XEvent> callback);

	/**
	 * searches for XEvents with given title or place
	 * 
	 * @param search
	 *            the search string (e.g. title or place)
	 * @param start
	 *            start index of query
	 * @param maxResult
	 *            end index of query
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void searchXEvent(String search, int start, int maxResult, AsyncCallback<List<XEvent>> callback);

	/**
	 * adds XUser to XEvent
	 * 
	 * @param userEvent
	 *            the XUserEvent, contains event, user and status
	 * @param AsyncCallback
	 *            a new instance of AsyncCallback
	 */
	public void addXUser(XUserEvent userEvent, AsyncCallback<XUserEvent> callback);

	/**
	 * loads list with all joined XEvents to XUser
	 * 
	 * @param user
	 *            the XUser which XEvents are loaded
	 * @param start
	 *            start index of query
	 * @param maxResult
	 *            end index of query
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadJoinedEvents(XUser user, int start, int maxResult, AsyncCallback<List<XUserEvent>> callback);

	/**
	 * loads list with all maybe XEvents to XUser
	 * 
	 * @param user
	 *            the XUser which XEvents are loaded
	 * @param start
	 *            start index of query
	 * @param maxResult
	 *            end index of query
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadMaybeEvents(XUser user, int start, int maxResult, AsyncCallback<List<XUserEvent>> callback);

	/**
	 * loads list with all XEvents created of XUser
	 * 
	 * @param user
	 *            the XUser which XEvents are loaded
	 * @param start
	 *            start index of query
	 * @param maxResult
	 *            end index of query
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadCreatedEvents(XUser user, int start, int maxResult, AsyncCallback<List<XEvent>> callback);

	/**
	 * loads list with all declined XEvents to XUser
	 * 
	 * @param user
	 *            the XUser which denied XEvents are loaded
	 * @param start
	 *            start index of query
	 * @param maxResult
	 *            end index of query
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadDeclinedEvents(XUser user, int start, int maxResult, AsyncCallback<List<XUserEvent>> callback);

	/**
	 * loads list with events to remind user
	 * 
	 * @param user
	 *            the XUser to remind
	 * @param start
	 *            start index of query
	 * @param maxResult
	 *            end index of query
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadReminderEvents(XUser user, int start, int maxResult, AsyncCallback<List<XUserEvent>> callback);

	/**
	 * loads list with new events to user
	 * 
	 * @param user
	 *            the XUser to which events were loaded
	 * @param start
	 *            start index of query
	 * @param maxResult
	 *            end index of query
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadNewEvents(XUser user, int start, int maxResult, AsyncCallback<List<XEvent>> callback);

}
