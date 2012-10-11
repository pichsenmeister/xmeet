package webapp.client.rpc.xmedia;

import java.util.List;

import webapp.model.XMedia;
import webapp.model.XUser;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface RPCMediaAsync {
	
	/**
	 * saves a XMedia instance to XUser
	 *
	 * @param media
	 *            the XMedia to save
	 * @param owner
	 *            the XUser as Owner
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void saveXMedia(XMedia media, XUser owner, AsyncCallback<XMedia> callback);
	
	/**
	 * loads a XMedia by id
	 *
	 * @param id
	 *            the id to load XMedia
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadXMediaByID(Long id, AsyncCallback<XMedia> callback);
	
	/**
	 * loads the XMedia list to user
	 *
	 * @param user
	 *            the user which media is load
	 * @param start
	 * 			  start index of query
	 * @param maxResult
	 * 			  end index of query
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadXMediaList(XUser user, int start, int maxResult, AsyncCallback<List<XMedia>> callback);

}