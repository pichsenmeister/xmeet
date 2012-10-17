package webapp.client.presenter.search;

import java.util.List;

import webapp.client.callback.ITypedCallback;
import webapp.client.event.XNameToken;
import webapp.client.presenter.content.FooterPresenterWidget;
import webapp.client.presenter.xuser.LoggedInGatekeeper;
import webapp.client.presenter.xuser.UserControlPanelWidgetPresenter;
import webapp.client.rpc.xuser.RPCUserAsync;
import webapp.model.XUser;
import webapp.model.enums.XContactStatus;

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
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

/**
 * the presenter for the search result page
 * 
 * @author David Pichsenmeister
 */
public class SearchResultPresenter extends
		Presenter<SearchResultPresenter.IView, SearchResultPresenter.IProxy> {

	/**
	 * the interface for the search result view
	 * 
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {
		void setXUserList(List<XUser> users, XUser user);

		void setCallbackUser(ITypedCallback<XUser> callback);
	}

	/**
	 * the interface for the user callback
	 * 
	 * @author David Pichsenmeister
	 */
	public interface ICallbackUser {
		void execute(XUser user, HasText button, HasText text);
	}

	@ProxyCodeSplit
	@NameToken(XNameToken.SEARCH)
	@UseGatekeeper(LoggedInGatekeeper.class)
	public interface IProxy extends ProxyPlace<SearchResultPresenter> {

	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_CONTROL = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_CONTENT_FOOTER = new Type<RevealContentHandler<?>>();

	private final IndirectProvider<UserControlPanelWidgetPresenter> controlPresenter_;
	private final IndirectProvider<FooterPresenterWidget> footerPresenter_;

	private static final int MAX_RESULT = 10;

	public static final String USER_WAIT_MSG = "anfrage widerrufen";
	public static final String USER_PLUS_MSG = "du lauschst";
	public static final String PLUS_SIGN = "+";
	public static final String MINUS_SIGN = "-";

	private final RPCUserAsync rpcUser_;
	private final IView view_;
	private XUser user_;
	private final int startUser_ = 0;
	private final int startEvent_ = 0;

	@Inject
	private Provider<LoggedInGatekeeper> pGatekeeper_;

	/**
	 * the constructor
	 * 
	 * @param rpcUser
	 *            rpc service for database connection
	 * @param eventBus
	 *            the GWT EventBus
	 * @param view
	 *            the view to the presenter
	 * @param proxy
	 *            the proxy to the presenter
	 * @param controlPresenter
	 *            the UserControlPanelWidgetPresenter
	 * @param footerPresenter
	 *            the FooterPresenterWidget
	 */
	@Inject
	public SearchResultPresenter(RPCUserAsync rpcUser, EventBus eventBus,
			IView view, IProxy proxy,
			AsyncProvider<UserControlPanelWidgetPresenter> controlPresenter,
			AsyncProvider<FooterPresenterWidget> footerPresenter) {
		super(eventBus, view, proxy);

		rpcUser_ = rpcUser;
		view_ = view;

		controlPresenter_ = new CodeSplitProvider<UserControlPanelWidgetPresenter>(
				controlPresenter);
		footerPresenter_ = new CodeSplitProvider<FooterPresenterWidget>(
				footerPresenter);
	}

	@Override
	protected void onBind() {
		super.onBind();

		view_.setCallbackUser(new ITypedCallback<XUser>() {
			@Override
			public void execute(XUser listen) {

			}
		});
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		controlPresenter_
				.get(new AsyncCallback<UserControlPanelWidgetPresenter>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO error handling
						Window.alert(caught.toString());
					}

					@Override
					public void onSuccess(UserControlPanelWidgetPresenter result) {
						setInSlot(TYPE_CONTENT_CONTROL, result);
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
				setInSlot(TYPE_CONTENT_FOOTER, result);
			}
		});
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);

		user_ = pGatekeeper_.get().getLoggedInUser();

		String search = request.getParameter("q", "");
		if (!search.isEmpty()) {
			doSearch(search);
		}
	}

	/**
	 * executes the search
	 * 
	 * @param search
	 *            the search string
	 */
	private void doSearch(final String search) {
		rpcUser_.loadListenTos(user_, XContactStatus.PERMIT,
				new AsyncCallback<List<XUser>>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO error handling
					}

					@Override
					public void onSuccess(List<XUser> result) {
						// user_ = result;
						searchUsers(search);
					}
				});
	}

	/**
	 * searches for XUsers in database
	 * 
	 * @param search
	 *            the search string
	 */
	private void searchUsers(String search) {
		rpcUser_.searchXUser(search, startUser_, MAX_RESULT,
				new AsyncCallback<List<XUser>>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO error handling
						Window.alert("cannot find users");
					}

					@Override
					public void onSuccess(List<XUser> result) {
						view_.setXUserList(result, user_);
					}
				});
	}

	/**
	 * adds a contact to logged in user
	 * 
	 * @param listen
	 *            the contact to add
	 */
	private void addContact(final XUser listen) {
		rpcUser_.addContact(user_, listen, new AsyncCallback<XUser>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO error handling
				Window.alert("cannot add user to listen list");
			}

			@Override
			public void onSuccess(XUser result) {
				user_ = result;
				Window.alert("added");
			}
		});
	}

	/**
	 * removes a contact to logged in user
	 * 
	 * @param listen
	 *            the contact to remove
	 */
	private void removeContact(XUser listen) {
		rpcUser_.removeContact(user_, listen, new AsyncCallback<XUser>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO error handling
				Window.alert("cannot add user to listen list");
			}

			@Override
			public void onSuccess(XUser result) {
				user_ = result;
				Window.alert("removed");
			}
		});
	}
}
