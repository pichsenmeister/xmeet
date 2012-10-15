package webapp.client.view.entry.restricted;

import java.util.ArrayList;
import java.util.List;

import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.xevent.invitation.InvitationPopupPresenter;
import webapp.client.view.customwidget.ListView;
import webapp.client.view.customwidget.ListView.IGenerator;
import webapp.client.view.customwidget.XButton;
import webapp.model.XUser;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewImpl;

/**
 * the view for the LocationPopup, implements the interface declared in
 * presenter
 * 
 * @author David Pichsenmeister
 */
public class InvitationPopup extends PopupViewImpl implements
		InvitationPopupPresenter.IView {

	public static final String INVITE = "invite";

	private static final String STYLE_POPUP = "qr-popup";

	private PopupPanel widget;
	private ListView<XUser> user_;
	private List<XUser> userList_;

	private ITypedCallback<List<XUser>> callback_;

	@Inject
	public InvitationPopup(EventBus eventBus) {
		super(eventBus);

		widget = new PopupPanel(true);
		widget.setAnimationEnabled(true);
		widget.setModal(true);
		widget.setGlassEnabled(true);
		widget.setAutoHideEnabled(false);
		widget.center();

		FlowPanel panel = new FlowPanel();
		panel.setStyleName(STYLE_POPUP);
		panel.setSize("700px", "450px");
		widget.setWidget(panel);

		user_ = new ListView<XUser>();
		user_.setGenerator(new IGenerator<XUser>() {

			@Override
			public Widget generateWidget(XUser model) {
				InvitationUser user = new InvitationUser();
				user.setUser(model);
				return user;
			}
		});
		panel.add(user_);

		XButton save = new XButton(INVITE);
		panel.add(save);
		save.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (callback_ != null) {
					callback_.execute(getCheckedUser());
				}
			}
		});
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setCallback(ITypedCallback<List<XUser>> callback) {
		callback_ = callback;
	}

	@Override
	public void setUser(List<XUser> user) {
		userList_ = user;
		user_.setStore(userList_);
	}

	private List<XUser> getCheckedUser() {
		List<XUser> checked = new ArrayList<XUser>();
		for (XUser u : userList_) {
			InvitationUser widget = (InvitationUser) user_.getWidget(u);
			if (widget.getChecked()) {
				checked.add(widget.getUser());
			}
		}
		return checked;
	}
}
