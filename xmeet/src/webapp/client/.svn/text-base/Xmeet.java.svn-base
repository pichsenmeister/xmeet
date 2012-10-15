package webapp.client;

import webapp.client.gin.XGinjector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Xmeet implements EntryPoint {
	public final XGinjector ginjector = GWT.create(XGinjector.class);

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {

		DelayedBindRegistry.bind(ginjector);

		ginjector.getPlaceManager().revealCurrentPlace();
	}
}
