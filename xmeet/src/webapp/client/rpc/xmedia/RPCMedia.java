package webapp.client.rpc.xmedia;

import java.util.List;

import webapp.model.XMedia;
import webapp.model.XUser;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("greet/media")
public interface RPCMedia extends RemoteService {
	
	public XMedia saveXMedia(XMedia media, XUser owner);
	
	public XMedia loadXMediaByID(Long id);
	
	public List<XMedia> loadXMediaList(XUser user, int start, int maxResult);
	
}
