package webapp.client.presenter.content;

import webapp.client.rpc.xcontent.RPCContentAsync;
import webapp.model.XContent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * the presenter for html content
 *
 * @author David Pichsenmeister
 */
public class ContentPresenterWidget extends PresenterWidget<ContentPresenterWidget.IView> {

	/**
	 * the interface for the html content view
	 *
	 * @author David Pichsenmeister
	 */
	public interface IView extends View {
		void setHtmlContent(String content);
	}

	private RPCContentAsync rpcContent_;
	private IView view_;
	private String content_;

	/**
	 * the constructor
	 *
	 * @param rpcService
	 *            rpcService for database connection
	 * @param eventBus
	 *            the GWT EventBus
	 * @param view
	 *            the view
	 */
	@Inject
	public ContentPresenterWidget(RPCContentAsync rpcContent, EventBus eventBus, IView view) {
		super(eventBus, view);
		
		rpcContent_ = rpcContent;
		view_ = view;
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		rpcContent_.loadContentByName(content_, new AsyncCallback<XContent>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO error handling

			}
			@Override
			public void onSuccess(XContent result) {
				if(result != null) {
					view_.setHtmlContent(result.getContent());
				}
			}
		});
	}

	/**
	 * sets the <code>XContentName</code> which will be load from database
	 * 
	 * @param name
	 *			the XContentName
	 */
	public void setContentName(String name) {
		content_ = name;
	}
}
