package webapp.server;

import java.util.ArrayList;
import java.util.List;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.core.serialization.GwtProxySerialization;
import net.sf.gilead.core.store.stateless.StatelessProxyStore;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.hibernate.Session;

import webapp.client.rpc.xmedia.RPCMedia;
import webapp.model.XMedia;
import webapp.model.XUser;

/**
 * The server side implementation of the RPC service.
 */
public class RPCMediaImpl extends PersistentRemoteService implements RPCMedia {

	private static final long serialVersionUID = -8409799397904516250L;

	private final HibernateUtil gileadHibernateUtil = new HibernateUtil();

	/**
	 * Constructor
	 */
	public RPCMediaImpl() {
		gileadHibernateUtil.setSessionFactory(webapp.hibernate.HibernateUtil.getSessionFactory());

		PersistentBeanManager persistentBeanManager = new PersistentBeanManager();
		persistentBeanManager.setPersistenceUtil(gileadHibernateUtil);

		StatelessProxyStore sps = new StatelessProxyStore();
		sps.setProxySerializer(new GwtProxySerialization());
		persistentBeanManager.setProxyStore(sps);

		setBeanManager(persistentBeanManager);
	}

	@SuppressWarnings("deprecation")
	@Override
	public XMedia saveXMedia(XMedia media, XUser owner) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		media.setOwner(owner);
		media.setTime(System.currentTimeMillis());
		session.saveOrUpdate(media);

		session.getTransaction().commit();
		return media;
	}

	@Override
	public XMedia loadXMediaByID(Long id) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		XMedia media = (XMedia) session.get(XMedia.class, id);

		session.getTransaction().commit();

		return media;
	}

	@Override
	public List<XMedia> loadXMediaList(XUser user, int start, int maxResult) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		List<XMedia> list = new ArrayList<XMedia>(session
				.createQuery("FROM XMedia as XMedia WHERE XMedia.owner = ? ORDER BY XMedia.time DESC")
				.setEntity(0, user).setFirstResult(start).setMaxResults(maxResult).list());

		session.getTransaction().commit();

		return list;
	}
}
