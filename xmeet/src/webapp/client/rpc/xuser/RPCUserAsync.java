package webapp.client.rpc.xuser;

import java.util.List;

import webapp.model.XUser;
import webapp.model.enums.XContactStatus;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RPCUserAsync {

	/**
	 * registers a XUser instance
	 * 
	 * @param tmpUser
	 *            a temporary XUser instance, which holds username, name,
	 *            password and email
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void registerUser(XUser tmpUser, AsyncCallback<XUser> callback);

	/**
	 * saves a XUser instance
	 * 
	 * @param user
	 *            the XUser to save
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void saveXUser(XUser user, AsyncCallback<XUser> callback);

	/**
	 * loads a XUser by id
	 * 
	 * @param id
	 *            the id to load XUser
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadXUserByID(Long id, AsyncCallback<XUser> callback);

	/**
	 * loads a XUser by email
	 * 
	 * @param email
	 *            the email to load XUser
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadXUser(String email, AsyncCallback<XUser> callback);

	/**
	 * loads list with all listener of XUser
	 * 
	 * @param user
	 *            the XUser which listener are loaded
	 * @param status
	 *            the XContactStatus to load
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadListener(XUser user, XContactStatus status, AsyncCallback<List<XUser>> callback);

	/**
	 * loads list with all users which XUser is listen
	 * 
	 * @param user
	 *            the XUser which users are loaded
	 * @param status
	 *            the XContactStatus to load
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadListenTos(XUser user, XContactStatus status, AsyncCallback<List<XUser>> callback);

	/**
	 * searches for XUsers with given username or name
	 * 
	 * @param search
	 *            the search string (e.g. username or name)
	 * @param start
	 *            start index of query
	 * @param maxResult
	 *            end index of query
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void searchXUser(String search, int start, int maxResult,
			AsyncCallback<List<XUser>> callback);

	/**
	 * adds contact to XUser
	 * 
	 * @param listener
	 *            the user who is logged in
	 * @param listenTo
	 *            the user to add
	 * @param AsyncCallback
	 *            a new instance of AsyncCallback
	 */
	public void addContact(XUser listener, XUser listenTo, AsyncCallback<XUser> callback);

	/**
	 * verifies a contact request
	 * 
	 * @param listener
	 *            the user who send the contact request
	 * @param listenTo
	 *            the user who verifies the listener
	 * @param status
	 *            the contact status
	 * @param AsyncCallback
	 *            a new instance of AsyncCallback
	 */
	public void verifyContactRequest(XUser listener, XUser listenTo,
			AsyncCallback<XUser> callback);

	/**
	 * removes Contact from XUser
	 * 
	 * @param loggedUser
	 *            the user who is logged in
	 * @param contact
	 *            the user to remove
	 * @param AsyncCallback
	 *            a new instance of AsyncCallback
	 */
	public void removeContact(XUser loggedUser, XUser contact, AsyncCallback<XUser> callback);

}
