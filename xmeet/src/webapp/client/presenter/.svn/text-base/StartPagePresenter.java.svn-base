package webapp.client.presenter;

import webapp.client.event.XNameToken;
import webapp.client.presenter.content.ContentPresenterWidget;
import webapp.client.presenter.content.FooterPresenterWidget;
import webapp.client.presenter.xuser.ControlPanelPresenterWidget;
import webapp.client.presenter.xuser.RegisterWidgetPresenter;
import webapp.model.helper.XContentName;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.common.client.CodeSplitProvider;
import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

/**
 * the presenter for the start page
 *
 * @author David Pichsenmeister
 */
public class StartPagePresenter extends Presenter<StartPagePresenter.IView, StartPagePresenter.IProxy> {

	/**
	 * the interface for the start page view
	 *
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {

	}

	@ProxyCodeSplit
	@NameToken(XNameToken.START)
	@NoGatekeeper
	public interface IProxy extends ProxyPlace<StartPagePresenter> {

	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_CONTROL = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_LEFT = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_RIGHT = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_BOTTOM = new Type<RevealContentHandler<?>>();

	private final IndirectProvider<RegisterWidgetPresenter> registerPresenter_;
	private final IndirectProvider<ControlPanelPresenterWidget> controlPresenter_;
	private final IndirectProvider<ContentPresenterWidget> contentPresenter_;
	private final IndirectProvider<FooterPresenterWidget> footerPresenter_;

	/**
	 * the constructor
	 *
	 * @param rpcService
	 *            rpcService for database connection
	 * @param eventBus
	 *            the GWT EventBus
	 * @param view
	 *            the view to the presenter
	 * @param proxy
	 *            the proxy to the presenter
	 * @param registerPresenter
	 *            the RegisterPresenterWidget
	 * @param controlPresenter
	 *            the UserControlPresenterWidget
	 * @param footerPresenter
	 *            the FooterPresenterWidget
	 */
	@Inject
	public StartPagePresenter(EventBus eventBus, IView view, IProxy proxy,
			AsyncProvider<RegisterWidgetPresenter> registerPresenter,
			AsyncProvider<ControlPanelPresenterWidget> controlPresenter,
			AsyncProvider<ContentPresenterWidget> contentPresenter,
			AsyncProvider<FooterPresenterWidget> footerPresenter) {
		super(eventBus, view, proxy);
		
		registerPresenter_ = new CodeSplitProvider<RegisterWidgetPresenter>(registerPresenter);
		controlPresenter_ = new CodeSplitProvider<ControlPanelPresenterWidget>(controlPresenter);
		contentPresenter_ = new CodeSplitProvider<ContentPresenterWidget>(contentPresenter);
		footerPresenter_ = new CodeSplitProvider<FooterPresenterWidget>(footerPresenter);
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		controlPresenter_.get(new AsyncCallback<ControlPanelPresenterWidget>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO error handling
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(ControlPanelPresenterWidget result) {
				setInSlot(TYPE_CONTENT_CONTROL, result);
			}
		});

		registerPresenter_.get(new AsyncCallback<RegisterWidgetPresenter>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO error handling
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(RegisterWidgetPresenter result) {
				setInSlot(TYPE_CONTENT_RIGHT, result);
			}
		});

		contentPresenter_.get(new AsyncCallback<ContentPresenterWidget>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO error handling
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(ContentPresenterWidget result) {
				result.setContentName(XContentName.TEST);
				setInSlot(TYPE_CONTENT_LEFT, result);
			}
		});
		
		footerPresenter_.get(new AsyncCallback<FooterPresenterWidget>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO error handling
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(FooterPresenterWidget result) {
				setInSlot(TYPE_CONTENT_BOTTOM, result);
			}
		});
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}
}
