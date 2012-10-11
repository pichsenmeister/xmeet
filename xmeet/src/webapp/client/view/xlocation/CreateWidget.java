package webapp.client.view.xlocation;

import java.util.List;

import webapp.client.callback.ICallback;
import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.xlocation.CreateWidgetPresenter;
import webapp.client.view.xlocation.create.CreateBox;
import webapp.model.XLocation;
import webapp.model.XLocationEntry;
import webapp.model.enums.XVisibility;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for XEvent creator, implements the interface declared in presenter
 * 
 * @author David Pichsenmeister
 */
public class CreateWidget extends ViewImpl implements CreateWidgetPresenter.IView {

	private CreateBox box_;

	/**
	 * constructor, contains the view
	 */
	public CreateWidget() {
		box_ = new CreateBox();
	}

	@Override
	public Widget asWidget() {
		return box_;
	}

	@Override
	public void setCallbackLocation(ITypedCallback<String> callback) {
		box_.setCallbackLocation(callback);
	}

	@Override
	public void setLocations(List<XLocation> locations) {
		box_.setStore(locations);
	}

	@Override
	public void setCallbackNewLocation(ICallback callback) {
		// TODO
	}

	@Override
	public void setCallbackVisibility(ITypedCallback<XVisibility> callback) {
		box_.setCallbackVisibility(callback);
	}

	@Override
	public void setVisibility(XVisibility type) {
		box_.setVisibility(type);
	}

	@Override
	public void setCallbackSubmit(ITypedCallback<XLocationEntry> callback) {
		box_.setCallbackSubmit(callback);
	}

	@Override
	public void setCallbackNext(ICallback callback) {
		box_.setCallbackNext(callback);
	}

	@Override
	public void setCallbackBack(ICallback callback) {
		box_.setCallbackBack(callback);
	}
}
