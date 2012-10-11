package webapp.client.rpc.xmessage;

import java.util.List;

import webapp.model.XMessage;
import webapp.model.XUser;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("greet/message")
public interface RPCMessage extends RemoteService {
	
	public XMessage saveXMessage(XMessage msg, XUser sender, XUser reciever);
	
	public List<XMessage> loadNewMessages(XUser user, int start, int maxResult);
	
	public List<XMessage> loadOldMessages(XUser user, int start, int maxResult);

}
