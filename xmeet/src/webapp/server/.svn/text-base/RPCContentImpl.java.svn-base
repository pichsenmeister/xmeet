package webapp.server;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.core.serialization.GwtProxySerialization;
import net.sf.gilead.core.store.stateless.StatelessProxyStore;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.hibernate.Session;

import webapp.client.rpc.xcontent.RPCContent;
import webapp.model.XContent;

/**
 * The server side implementation of the RPC service.
 */
public class RPCContentImpl extends PersistentRemoteService implements
		RPCContent {
	
	private static final long serialVersionUID = 1962838522760128810L;

	private HibernateUtil gileadHibernateUtil = new HibernateUtil();

	/**
	 * Constructor
	 */
	public RPCContentImpl() {
		gileadHibernateUtil.setSessionFactory(webapp.hibernate.HibernateUtil.getSessionFactory());

		PersistentBeanManager persistentBeanManager = new PersistentBeanManager();
		persistentBeanManager.setPersistenceUtil(gileadHibernateUtil);

		StatelessProxyStore sps = new StatelessProxyStore();
		sps.setProxySerializer(new GwtProxySerialization());
		persistentBeanManager.setProxyStore(sps);

		setBeanManager(persistentBeanManager);
	}
	
	@Override
	public XContent loadContentByName(String name) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		XContent content = (XContent) session.createQuery("FROM XContent as XContent WHERE XContent.contentName = ?")
				.setString(0, name).uniqueResult();

		session.getTransaction().commit();

		return content;
	}

}
