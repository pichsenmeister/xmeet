package webapp.client.presenter.xuser;

import webapp.client.event.XNameToken;
import webapp.client.presenter.content.FooterPresenterWidget;
import webapp.client.rpc.xuser.RPCUserAsync;
import webapp.model.XUser;

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
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

/**
 * the presenter for user's contacts
 * 
 * @author David Pichsenmeister
 */
public class ContactsPresenter extends Presenter<ContactsPresenter.IView, ContactsPresenter.IProxy> {

	/**
	 * the interface for the userpage view
	 * 
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {

	}

	@ProxyCodeSplit
	@NameToken(XNameToken.CONTACTS)
	@NoGatekeeper
	public interface IProxy extends ProxyPlace<ContactsPresenter> {

	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_CONTROL = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_CONTACTS = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_FOOTER = new Type<RevealContentHandler<?>>();

	private final IndirectProvider<UserControlPanelWidgetPresenter> userControlPresenter_;
	private final IndirectProvider<ContactsWidgetPresenter> contactsPresenter_;
	private final IndirectProvider<FooterPresenterWidget> footerPresenter_;

	private final RPCUserAsync rpcUser_;
	private final IView view_;
	private XUser loggedUser_;

	@Inject
	private Provider<LoggedInGatekeeper> pGatekeeper_;

	/**
	 * the constructor
	 * 
	 * @param rpcService
	 *            rpcService for database connection
	 * @param eventBus
	 *            the GWT EventBus
	 * @param view
	 *            the view
	 * @param proxy
	 *            the proxy
	 * @param controlPresenter
	 *            the UserControlPresenterWidget
	 * @param eventsPresenter
	 *            the MyEventsPresenterWidget
	 * @param contactsPresenter
	 *            the ContactsPresenterWidget
	 * @param eventCreatorPresenter
	 *            the EventCreatorPresenterWidget
	 */
	@Inject
	public ContactsPresenter(RPCUserAsync rpcUser, EventBus eventBus, IView view, IProxy proxy,
			AsyncProvider<UserControlPanelWidgetPresenter> userControlPresenter,
			AsyncProvider<ContactsWidgetPresenter> contactsPresenter,
			AsyncProvider<FooterPresenterWidget> footerPresenter) {
		super(eventBus, view, proxy);

		rpcUser_ = rpcUser;
		view_ = view;

		userControlPresenter_ = new CodeSplitProvider<UserControlPanelWidgetPresenter>(userControlPresenter);
		contactsPresenter_ = new CodeSplitProvider<ContactsWidgetPresenter>(contactsPresenter);
		footerPresenter_ = new CodeSplitProvider<FooterPresenterWidget>(footerPresenter);
	}

	@Override
	public void onBind() {
		super.onBind();

	}

	@Override
	protected void onReveal() {
		super.onReveal();

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

		contactsPresenter_.get(new AsyncCallback<ContactsWidgetPresenter>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(ContactsWidgetPresenter result) {
				setInSlot(TYPE_CONTENT_CONTACTS, result);
			}
		});

		userControlPresenter_.get(new AsyncCallback<UserControlPanelWidgetPresenter>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(UserControlPanelWidgetPresenter result) {
				setInSlot(TYPE_CONTENT_CONTROL, result);
			}
		});
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}
}
