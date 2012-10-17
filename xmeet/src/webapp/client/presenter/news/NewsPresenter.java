package webapp.client.presenter.news;

import webapp.client.event.XNameToken;
import webapp.client.presenter.content.FooterPresenterWidget;
import webapp.client.presenter.xuser.LoggedInGatekeeper;
import webapp.client.presenter.xuser.UserControlPanelWidgetPresenter;
import webapp.model.enums.MenuIcon;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.common.client.CodeSplitProvider;
import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

/**
 * the presenter for news page
 * 
 * @author David Pichsenmeister
 */
public class NewsPresenter extends
		Presenter<NewsPresenter.IView, NewsPresenter.IProxy> {

	/**
	 * the interface for the news view
	 * 
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {

	}

	@ProxyCodeSplit
	@NameToken(XNameToken.NEWS)
	@UseGatekeeper(LoggedInGatekeeper.class)
	public interface IProxy extends ProxyPlace<NewsPresenter> {

	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_CONTROL = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_NEWS = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_FOOTER = new Type<RevealContentHandler<?>>();

	private final IndirectProvider<UserControlPanelWidgetPresenter> controlPresenter_;
	private final IndirectProvider<NewsWidgetPresenter> newsPresenter_;
	private final IndirectProvider<FooterPresenterWidget> footerPresenter_;

	@Inject
	private Provider<LoggedInGatekeeper> pGatekeeper_;

	/**
	 * the constructor
	 * 
	 * @param eventBus
	 *            the GWT EventBus
	 * @param view
	 *            the view
	 * @param proxy
	 *            the proxy
	 * @param controlPresenter
	 *            the UserControlPanelWidgetPresenter
	 * @param newsPresenter
	 *            NewsWidgetPresenter
	 * @param footerPresenter
	 *            the FooterPresenterWidget
	 */
	@Inject
	public NewsPresenter(EventBus eventBus, IView view, IProxy proxy,
			AsyncProvider<UserControlPanelWidgetPresenter> controlPresenter,
			AsyncProvider<NewsWidgetPresenter> newsPresenter,
			AsyncProvider<FooterPresenterWidget> footerPresenter) {
		super(eventBus, view, proxy);

		controlPresenter_ = new CodeSplitProvider<UserControlPanelWidgetPresenter>(
				controlPresenter);
		newsPresenter_ = new CodeSplitProvider<NewsWidgetPresenter>(
				newsPresenter);
		footerPresenter_ = new CodeSplitProvider<FooterPresenterWidget>(
				footerPresenter);
	}

	@Override
	public void onBind() {
		super.onBind();
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		controlPresenter_
				.get(new AsyncCallback<UserControlPanelWidgetPresenter>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.toString());
					}

					@Override
					public void onSuccess(UserControlPanelWidgetPresenter result) {
						setInSlot(TYPE_CONTENT_CONTROL, result);
						result.setActiveIcon(MenuIcon.NEWS);
					}
				});

		newsPresenter_.get(new AsyncCallback<NewsWidgetPresenter>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(NewsWidgetPresenter result) {
				setInSlot(TYPE_CONTENT_NEWS, result);
			}
		});

		footerPresenter_.get(new AsyncCallback<FooterPresenterWidget>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(FooterPresenterWidget result) {
				setInSlot(TYPE_CONTENT_FOOTER, result);
			}
		});
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}
}
