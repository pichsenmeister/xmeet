package webapp.client.rpc.xposting;

import webapp.model.XEvent;
import webapp.model.XPosting;
import webapp.model.XUser;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RPCPostingAsync {
	
	/**
	 * saves a XPosting instance
	 *
	 * @param posting
	 *            the XPosting to save
	 * @param event
	 *            the XEvent where the XPosting is added
	 * @param owner
	 *            the XUser as owner
	 * @param callback
	 *            a new instance of AsyncCallback<T>
	 */
	public void saveXPosting(XPosting posting, XEvent event, XUser owner, AsyncCallback<XEvent> callback);

}
