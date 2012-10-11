package webapp.server;

import java.util.ArrayList;
import java.util.List;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.core.serialization.GwtProxySerialization;
import net.sf.gilead.core.store.stateless.StatelessProxyStore;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.hibernate.Session;

import webapp.client.rpc.xlocation.RPCLocation;
import webapp.model.XLocation;
import webapp.model.XLocationEntry;
import webapp.model.XUser;

/**
 * The server side implementation of the RPC service.
 */
public class RPCLocationImpl extends PersistentRemoteService implements RPCLocation {

	private static final long serialVersionUID = 2635292065166603353L;

	private final HibernateUtil gileadHibernateUtil = new HibernateUtil();

	/**
	 * Constructor
	 */
	public RPCLocationImpl() {
		gileadHibernateUtil.setSessionFactory(webapp.hibernate.HibernateUtil.getSessionFactory());

		PersistentBeanManager persistentBeanManager = new PersistentBeanManager();
		persistentBeanManager.setPersistenceUtil(gileadHibernateUtil);

		StatelessProxyStore sps = new StatelessProxyStore();
		sps.setProxySerializer(new GwtProxySerialization());
		persistentBeanManager.setProxyStore(sps);

		setBeanManager(persistentBeanManager);
	}

	@Override
	public List<XLocation> searchLocations(String location, int start, int maxResult) {
		location = "%" + location + "%";

		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		List<XLocation> list = new ArrayList<XLocation>(session
				.createSQLQuery("SELECT * FROM xlocation WHERE xlocation.name ILIKE ?")
				.addEntity("xlocation", XLocation.class).setString(0, location).setFirstResult(start)
				.setMaxResults(maxResult).list());

		session.getTransaction().commit();

		return list;
	}

	@Override
	public XLocation saveXLocation(XLocation location) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		String name = location.getName();
		if (!name.startsWith("@")) {
			location.setName("@" + name);
		}

		session.saveOrUpdate(location);

		session.getTransaction().commit();
		return location;
	}

	@SuppressWarnings("deprecation")
	@Override
	public XLocationEntry saveXLocationEntry(XLocationEntry location, XUser user) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		location.setCreated(System.currentTimeMillis());
		location.setUser(user);
		session.saveOrUpdate(location);

		session.getTransaction().commit();
		return location;
	}

	@Override
	public List<XLocationEntry> loadLocationEntries(XUser user, int start, int max) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		XUser userLoaded = (XUser) session.get(XUser.class, user.getUserID());
		@SuppressWarnings("unchecked")
		List<XLocationEntry> locations = new ArrayList<XLocationEntry>(session
				.createQuery("FROM XLocationEntry WHERE user = ?").setEntity(0, userLoaded).setFirstResult(start)
				.setMaxResults(max).list());

		session.getTransaction().commit();

		return locations;
	}

}
