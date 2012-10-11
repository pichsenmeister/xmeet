package webapp.client.rpc.xmessage;

import java.util.List;

import webapp.model.XMessage;
import webapp.model.XUser;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RPCMessageAsync {
	
	/**
	 * saves a XMessage instance
	 *
	 * @param msg
	 *            the XMessage to save
	 * @param sender
	 *            the XUser who sends message
	 * @param reciever
	 *            the XUser who recieves message
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void saveXMessage(XMessage msg, XUser sender, XUser reciever, AsyncCallback<XMessage> callback);

	/**
	 * loads all new XMessages to XUser
	 *
	 * @param user
	 *            the XUser which messages are loaded
	 * @param start
	 * 			  start index of query
	 * @param maxResult
	 * 			  end index of query
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadNewMessages(XUser user, int start, int maxResult, AsyncCallback<List<XMessage>> callback);

	/**
	 * loads all new XMessages to XUser
	 *
	 * @param user
	 *            the XUser which messages are loaded
	 * @param start
	 * 			  start index of query
	 * @param maxResult
	 * 			  end index of query
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void loadOldMessages(XUser user, int start, int maxResult, AsyncCallback<List<XMessage>> callback);
}
