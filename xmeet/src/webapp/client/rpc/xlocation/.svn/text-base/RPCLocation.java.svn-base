package webapp.client.rpc.xlocation;

import java.util.List;

import webapp.model.XLocation;
import webapp.model.XLocationEntry;
import webapp.model.XUser;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("greet/location")
public interface RPCLocation extends RemoteService {
	
	public XLocation saveXLocation(XLocation location);
	
	public XLocationEntry saveXLocationEntry(XLocationEntry location, XUser user);
	
	public List<XLocation> searchLocations(String location,  int start, int maxResult);
	
	public List<XLocationEntry> loadLocationEntries(XUser user, int start, int maxResult);
	
}
