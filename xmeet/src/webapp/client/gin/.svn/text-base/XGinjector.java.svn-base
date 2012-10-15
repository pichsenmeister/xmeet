package webapp.client.gin;

import webapp.client.presenter.StartPagePresenter;
import webapp.client.presenter.content.ContentPresenter;
import webapp.client.presenter.news.NewsPresenter;
import webapp.client.presenter.search.SearchResultPresenter;
import webapp.client.presenter.settings.SettingsPresenter;
import webapp.client.presenter.xlocation.CreatePresenter;
import webapp.client.presenter.xuser.ContactsPresenter;
import webapp.client.presenter.xuser.LoggedInGatekeeper;
import webapp.client.presenter.xuser.UserPagePresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * the GINjector, places are injected with async provider for code splitting
 * 
 * @author David Pichsenmeister
 */
@GinModules({ XModule.class })
public interface XGinjector extends Ginjector {

	PlaceManager getPlaceManager();

	EventBus getEventBus();

	LoggedInGatekeeper getLoggedInGatekeeper();

	AsyncProvider<StartPagePresenter> getStartPagePresenter();

	AsyncProvider<UserPagePresenter> getUserPagePresenter();

	AsyncProvider<SearchResultPresenter> getSearchResultPresenter();

	AsyncProvider<NewsPresenter> getNewsPresenter();

	AsyncProvider<CreatePresenter> getCreatePresenter();

	AsyncProvider<SettingsPresenter> getSettingsPresenter();

	AsyncProvider<ContentPresenter> getContentPresenter();

	AsyncProvider<ContactsPresenter> getContactsPresenter();
}
