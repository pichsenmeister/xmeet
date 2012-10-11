package webapp.client.rpc.xposting;

import webapp.model.XEvent;
import webapp.model.XPosting;
import webapp.model.XUser;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("greet/posting")
public interface RPCPosting extends RemoteService {
	
	public XEvent saveXPosting(XPosting posting, XEvent event, XUser owner);

}
