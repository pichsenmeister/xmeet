package webapp.server;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.core.serialization.GwtProxySerialization;
import net.sf.gilead.core.store.stateless.StatelessProxyStore;
import net.sf.gilead.gwt.PersistentRemoteService;
import webapp.client.rpc.news.RPCNews;

/**
 * The server side implementation of the RPC service.
 */
public class RPCNewsImpl extends PersistentRemoteService implements
		RPCNews {

	private static final long serialVersionUID = -7783600117146307850L;

	private HibernateUtil gileadHibernateUtil = new HibernateUtil();

	/**
	 * Constructor
	 */
	public RPCNewsImpl() {
		gileadHibernateUtil.setSessionFactory(webapp.hibernate.HibernateUtil.getSessionFactory());

		PersistentBeanManager persistentBeanManager = new PersistentBeanManager();
		persistentBeanManager.setPersistenceUtil(gileadHibernateUtil);

		StatelessProxyStore sps = new StatelessProxyStore();
		sps.setProxySerializer(new GwtProxySerialization());
		persistentBeanManager.setProxyStore(sps);

		setBeanManager(persistentBeanManager);
	}

}
