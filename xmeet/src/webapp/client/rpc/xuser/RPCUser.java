package webapp.client.rpc.xuser;

import java.util.List;

import webapp.model.XUser;
import webapp.model.enums.XContactStatus;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("greet/user")
public interface RPCUser extends RemoteService {

	public XUser registerUser(XUser tmpUser);

	public XUser saveXUser(XUser user);

	public XUser loadXUserByID(Long id);

	public XUser loadXUser(String email);

	public List<XUser> loadListener(XUser user, XContactStatus status);

	public List<XUser> loadListenTos(XUser user, XContactStatus status);

	public List<XUser> searchXUser(String search, int start, int maxResult);

	public XUser addContact(XUser listener, XUser listenTo);

	public XUser verifyContactRequest(XUser listener, XUser listenTo);

	public XUser removeContact(XUser listener, XUser listenTo);

}
