package webapp.server;

import java.util.ArrayList;
import java.util.List;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.core.serialization.GwtProxySerialization;
import net.sf.gilead.core.store.stateless.StatelessProxyStore;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.hibernate.Session;

import webapp.client.rpc.xuser.RPCUser;
import webapp.model.XContact;
import webapp.model.XMedia;
import webapp.model.XSettings;
import webapp.model.XUser;
import webapp.model.enums.XContactStatus;

/**
 * The server side implementation of the RPC service.
 */
public class RPCUserImpl extends PersistentRemoteService implements
		RPCUser {

	private static final long serialVersionUID = 1963342623063544100L;

	private HibernateUtil gileadHibernateUtil = new HibernateUtil();

	/**
	 * Constructor
	 */
	public RPCUserImpl() {
		gileadHibernateUtil.setSessionFactory(webapp.hibernate.HibernateUtil.getSessionFactory());

		PersistentBeanManager persistentBeanManager = new PersistentBeanManager();
		persistentBeanManager.setPersistenceUtil(gileadHibernateUtil);

		StatelessProxyStore sps = new StatelessProxyStore();
		sps.setProxySerializer(new GwtProxySerialization());
		persistentBeanManager.setProxyStore(sps);

		setBeanManager(persistentBeanManager);
	}

	@Override
	public XUser registerUser(XUser user) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.flush();

		XMedia media = (XMedia) session.get(XMedia.class, new Long(1));
		user.setImage(media);

		XSettings setting = new XSettings();
		setting.setPublicAccount(true);
		setting.setNotificationEvent(true);
		setting.setNotificationListener(true);
		setting.setNotificationMessage(true);
		setting.setNotificationUpdates(true);

		session.save(setting);

		user.setSettings(setting);

		session.save(user);

		session.getTransaction().commit();
		return user;
	}

	@Override
	public XUser saveXUser(XUser user) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		user.setEmail(user.getEmail().toLowerCase());
		user.setName(user.getName().toLowerCase());
		user.setPassword(user.getPassword().toLowerCase());
		if (user.getPlace() != null) {
			if (!user.getPlace().startsWith("@")) {
				user.setPlace("@" + user.getPlace());
			}
			user.setPlace(user.getPlace().toLowerCase());
		}
		String website = user.getWebsite();
		if (website != null) {
			if (!website.contains("http://")) {
				website = "http://" + website;
			}
			user.setWebsite(website.toLowerCase());
		}

		session.saveOrUpdate(user.getSettings());
		session.saveOrUpdate(user);

		session.getTransaction().commit();
		return user;
	}

	@Override
	public XUser loadXUserByID(Long id) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		XUser user = (XUser) session.get(XUser.class, id);

		session.getTransaction().commit();

		return user;
	}

	@Override
	public XUser loadXUser(String email) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		XUser user = (XUser) session.createQuery("FROM XUser WHERE email = ?")
				.setString(0, email).uniqueResult();

		session.getTransaction().commit();

		return user;
	}

	@Override
	public List<XUser> loadListener(XUser user, XContactStatus status) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		List<XUser> list = new ArrayList<XUser>(
				session.createQuery(
						"SELECT contact.listener FROM XContact contact " +
								"WHERE contact.listenTo = ? AND contact.status = ?")
						.setEntity(0, user).setString(1, status.toString()).list());

		session.getTransaction().commit();

		return list;
	}

	@Override
	public List<XUser> loadListenTos(XUser user, XContactStatus status) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		List<XUser> list = new ArrayList<XUser>(
				session.createQuery(
						"SELECT contact.listenTo FROM XContact contact " +
								"WHERE contact.listener = ? AND contact.status = ?")
						.setEntity(0, user).setString(1, status.toString()).list());

		session.getTransaction().commit();

		return list;
	}

	@Override
	public List<XUser> searchXUser(String search, int start, int maxResult) {
		search = "%" + search + "%";

		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		List<XUser> uList = new ArrayList<XUser>(session
				.createSQLQuery("SELECT * FROM xuser WHERE " +
						"xuser.userName ILIKE ? OR xuser.name ILIKE ?")
				.addEntity("xuser", XUser.class)
				.setString(0, search).setString(1, search)
				.setFirstResult(start).setMaxResults(maxResult)
				.list());

		session.getTransaction().commit();

		return uList;
	}

	@SuppressWarnings("deprecation")
	@Override
	public XUser addContact(XUser listener, XUser listenTo) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		// XUser loggedUserLoaded = (XUser) session.get(XUser.class,
		// loggedUser.getUserID());
		// XUser contactLoaded = (XUser) session.get(XUser.class,
		// contact.getUserID());
		XContact contact = new XContact();
		contact.setListener(listener);
		contact.setListenTo(listenTo);
		if (listenTo.getSettings().isPublicAccount()) {
			contact.setStatus(XContactStatus.REQUEST);
		} else {
			contact.setStatus(XContactStatus.PERMIT);
		}

		session.saveOrUpdate(contact);

		session.getTransaction().commit();

		return listener;
	}

	@Override
	public XUser verifyContactRequest(XUser listener, XUser listenTo, XContactStatus status) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		XContact contact = (XContact) session
				.createQuery("FROM XContact WHERE listener = ? AND listenTo = ?")
				.setEntity(0, listener).setEntity(1, listenTo).uniqueResult();

		contact.setStatus(status);
		session.update(contact);

		session.getTransaction().commit();

		return listenTo;
	}

	@Override
	public XUser removeContact(XUser listener, XUser listenTo) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		XContact contact = (XContact) session
				.createQuery("FROM XContact WHERE listener = ? AND listenTo = ?")
				.setEntity(0, listener).setEntity(1, listenTo).uniqueResult();

		session.delete(contact);

		session.getTransaction().commit();

		return listener;
	}
}
