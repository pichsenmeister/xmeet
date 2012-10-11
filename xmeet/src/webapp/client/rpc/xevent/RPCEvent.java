package webapp.client.rpc.xevent;

import java.util.List;

import webapp.model.XEvent;
import webapp.model.XRestrictedEvent;
import webapp.model.XUser;
import webapp.model.XUserEvent;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("greet/event")
public interface RPCEvent extends RemoteService {

	public XEvent saveXEvent(XEvent event, XUser owner);

	public XRestrictedEvent saveRestrictedEvent(XEvent event, List<XUser> user);

	public XEvent reloadXEvent(XEvent event);

	public List<XUser> loadXUserList(XEvent event);

	public XEvent loadXPostingList(XEvent event);

	public List<XEvent> searchXEvent(String search, int start, int maxResult);

	public XUserEvent addXUser(XUserEvent userEvent);

	public List<XUserEvent> loadJoinedEvents(XUser user, int start, int maxResult);

	public List<XUserEvent> loadMaybeEvents(XUser user, int start, int maxResult);

	public List<XEvent> loadCreatedEvents(XUser user, int start, int maxResult);

	public List<XUserEvent> loadDeclinedEvents(XUser user, int start, int maxResult);

	public List<XUserEvent> loadReminderEvents(XUser user, int start, int maxResult);

	public List<XEvent> loadNewEvents(XUser user, int start, int maxResult);

}
