package webapp.client.gin;

import webapp.client.AppController;
import webapp.client.presenter.StartPagePresenter;
import webapp.client.presenter.content.ContentPresenter;
import webapp.client.presenter.content.ContentPresenterWidget;
import webapp.client.presenter.content.FooterPresenterWidget;
import webapp.client.presenter.news.NewsPresenter;
import webapp.client.presenter.news.NewsWidgetPresenter;
import webapp.client.presenter.search.SearchResultPresenter;
import webapp.client.presenter.settings.SettingsPresenter;
import webapp.client.presenter.xevent.UsersEventsWidgetPresenter;
import webapp.client.presenter.xevent.invitation.InvitationPopupPresenter;
import webapp.client.presenter.xlocation.CreatePresenter;
import webapp.client.presenter.xlocation.CreateWidgetPresenter;
import webapp.client.presenter.xlocation.LocationPopupPresenter;
import webapp.client.presenter.xuser.ContactsPresenter;
import webapp.client.presenter.xuser.ContactsWidgetPresenter;
import webapp.client.presenter.xuser.ControlPanelPresenterWidget;
import webapp.client.presenter.xuser.LoggedInGatekeeper;
import webapp.client.presenter.xuser.RegisterWidgetPresenter;
import webapp.client.presenter.xuser.UserControlPanelWidgetPresenter;
import webapp.client.presenter.xuser.UserPagePresenter;
import webapp.client.view.StartPageView;
import webapp.client.view.content.ContentView;
import webapp.client.view.content.ContentWidget;
import webapp.client.view.content.FooterWidget;
import webapp.client.view.entry.UsersEventsWidget;
import webapp.client.view.entry.restricted.InvitationPopup;
import webapp.client.view.news.NewsView;
import webapp.client.view.news.NewsWidget;
import webapp.client.view.search.SearchResultView;
import webapp.client.view.settings.SettingsView;
import webapp.client.view.xlocation.CreateView;
import webapp.client.view.xlocation.CreateWidget;
import webapp.client.view.xlocation.LocationPopup;
import webapp.client.view.xuser.ContactsWidget;
import webapp.client.view.xuser.ControlPanelWidget;
import webapp.client.view.xuser.RegisterWidget;
import webapp.client.view.xuser.UserControlPanelWidget;
import webapp.client.view.xuser.UserPageView;
import webapp.client.view.xuser.contacts.ContactsView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.googleanalytics.GoogleAnalytics;
import com.gwtplatform.mvp.client.googleanalytics.GoogleAnalyticsImpl;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

/**
 * the module where all presenters are bind to view and proxy
 * 
 * @author David Pichsenmeister
 */
public class XModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		// install(new DefaultModule(AppController.class));
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
		bind(RootPresenter.class).asEagerSingleton();
		bind(PlaceManager.class).to(AppController.class).in(Singleton.class);
		bind(GoogleAnalytics.class).to(GoogleAnalyticsImpl.class).in(Singleton.class);
		bind(LoggedInGatekeeper.class).in(Singleton.class);

		// bind Presenter Widgets
		bindPresenterWidget(RegisterWidgetPresenter.class, RegisterWidgetPresenter.IView.class, RegisterWidget.class);
		bindPresenterWidget(ContactsWidgetPresenter.class, ContactsWidgetPresenter.IView.class, ContactsWidget.class);

		bindPresenterWidget(ControlPanelPresenterWidget.class, ControlPanelPresenterWidget.IView.class,
				ControlPanelWidget.class);
		bindPresenterWidget(CreateWidgetPresenter.class, CreateWidgetPresenter.IView.class,
				CreateWidget.class);
		bindPresenterWidget(UsersEventsWidgetPresenter.class, UsersEventsWidgetPresenter.IView.class,
				UsersEventsWidget.class);
		bindPresenterWidget(ContentPresenterWidget.class, ContentPresenterWidget.IView.class, ContentWidget.class);
		bindPresenterWidget(NewsWidgetPresenter.class, NewsWidgetPresenter.IView.class, NewsWidget.class);
		bindPresenterWidget(FooterPresenterWidget.class, FooterPresenterWidget.IView.class, FooterWidget.class);

		// bind Singleton Presenter Widgets
		bindSingletonPresenterWidget(LocationPopupPresenter.class, LocationPopupPresenter.IView.class,
				LocationPopup.class);
		bindSingletonPresenterWidget(InvitationPopupPresenter.class, InvitationPopupPresenter.IView.class,
				InvitationPopup.class);
		bindSingletonPresenterWidget(UserControlPanelWidgetPresenter.class,
				UserControlPanelWidgetPresenter.IView.class, UserControlPanelWidget.class);

		// bind Presenters
		bindPresenter(StartPagePresenter.class, StartPagePresenter.IView.class, StartPageView.class,
				StartPagePresenter.IProxy.class);
		bindPresenter(UserPagePresenter.class, UserPagePresenter.IView.class, UserPageView.class,
				UserPagePresenter.IProxy.class);
		bindPresenter(SearchResultPresenter.class, SearchResultPresenter.IView.class, SearchResultView.class,
				SearchResultPresenter.IProxy.class);
		bindPresenter(NewsPresenter.class, NewsPresenter.IView.class, NewsView.class, NewsPresenter.IProxy.class);
		bindPresenter(SettingsPresenter.class, SettingsPresenter.IView.class, SettingsView.class,
				SettingsPresenter.IProxy.class);
		bindPresenter(ContentPresenter.class, ContentPresenter.IView.class, ContentView.class,
				ContentPresenter.IProxy.class);
		bindPresenter(CreatePresenter.class, CreatePresenter.IView.class, CreateView.class,
				CreatePresenter.IProxy.class);
		bindPresenter(ContactsPresenter.class, ContactsPresenter.IView.class, ContactsView.class,
				ContactsPresenter.IProxy.class);

	}

}
