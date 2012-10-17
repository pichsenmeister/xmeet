package webapp.client.presenter.content;

import webapp.client.event.XNameToken;
import webapp.client.presenter.xuser.ControlPanelPresenterWidget;
import webapp.client.presenter.xuser.LoggedInGatekeeper;
import webapp.client.presenter.xuser.UserControlPanelWidgetPresenter;
import webapp.model.XUser;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.common.client.CodeSplitProvider;
import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

/**
 * the presenter for content
 * 
 * @author David Pichsenmeister
 */
public class ContentPresenter extends
		Presenter<ContentPresenter.IView, ContentPresenter.IProxy> {

	/**
	 * the interface for the content view
	 * 
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {
		void isUserLogged(boolean logged);
	}

	public interface ICallbackListen {
		void execute(HasText button, HasText msg);
	}

	@ProxyCodeSplit
	@NameToken(XNameToken.CONTENT)
	@NoGatekeeper
	public interface IProxy extends ProxyPlace<ContentPresenter> {

	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_CONTROL = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_MAIN = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_FOOTER = new Type<RevealContentHandler<?>>();

	private final IndirectProvider<UserControlPanelWidgetPresenter> userControlPresenter_;
	private final IndirectProvider<ControlPanelPresenterWidget> controlPresenter_;
	private final IndirectProvider<ContentPresenterWidget> contentPresenter_;
	private final IndirectProvider<FooterPresenterWidget> footerPresenter_;

	private final IView view_;
	private XUser loggedUser_;
	private String content_;

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
	 * @param userControlPresenter
	 *            the UserControlPanelWidgetPresenter
	 * @param controlPresenter
	 *            the ControlPanelPresenterWidget
	 * @param contentPresenter
	 *            the ContentPresenterWidget
	 * @param footerPresenter
	 *            the FooterPresenterWidget
	 */
	@Inject
	public ContentPresenter(
			EventBus eventBus,
			IView view,
			IProxy proxy,
			AsyncProvider<UserControlPanelWidgetPresenter> userControlPresenter,
			AsyncProvider<ControlPanelPresenterWidget> controlPresenter,
			AsyncProvider<ContentPresenterWidget> contentPresenter,
			AsyncProvider<FooterPresenterWidget> footerPresenter) {
		super(eventBus, view, proxy);

		view_ = view;

		userControlPresenter_ = new CodeSplitProvider<UserControlPanelWidgetPresenter>(
				userControlPresenter);
		controlPresenter_ = new CodeSplitProvider<ControlPanelPresenterWidget>(
				controlPresenter);
		contentPresenter_ = new CodeSplitProvider<ContentPresenterWidget>(
				contentPresenter);
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

		loggedUser_ = pGatekeeper_.get().getLoggedInUser();

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

		if (loggedUser_ != null) {
			userControlPresenter_
					.get(new AsyncCallback<UserControlPanelWidgetPresenter>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert(caught.toString());
						}

						@Override
						public void onSuccess(
								UserControlPanelWidgetPresenter result) {
							setInSlot(TYPE_CONTENT_CONTROL, result);
						}
					});
		} else {
			controlPresenter_
					.get(new AsyncCallback<ControlPanelPresenterWidget>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert(caught.toString());
						}

						@Override
						public void onSuccess(ControlPanelPresenterWidget result) {
							setInSlot(TYPE_CONTENT_CONTROL, result);
						}
					});
		}
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@Override
	public boolean useManualReveal() {
		return true;
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);

		content_ = request.getParameter("about", "error");

		contentPresenter_.get(new AsyncCallback<ContentPresenterWidget>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(ContentPresenterWidget result) {
				result.setContentName(content_);
				setInSlot(TYPE_CONTENT_MAIN, result);
			}
		});

		loggedUser_ = pGatekeeper_.get().getLoggedInUser();

		if (loggedUser_ != null) {
			view_.isUserLogged(true);
		} else {
			view_.isUserLogged(false);
		}
		getProxy().manualReveal(this);
	}
}
