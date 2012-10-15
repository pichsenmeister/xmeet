package webapp.client.view.xuser.contacts;

import webapp.client.callback.ITypedCallback;
import webapp.model.enums.EnumWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ContactsTabPanel extends Composite {

	private static ContactsTabPanelUiBinder uiBinder = GWT.create(ContactsTabPanelUiBinder.class);

	interface ContactsTabPanelUiBinder extends UiBinder<Widget, ContactsTabPanel> {
	}

	interface Style extends CssResource {
		String menuLabelActive();

		String menuLabelInactive();
	}

	public static final String STYLE_LOADING = "loading";

	@UiField
	Label request;
	@UiField
	Label follower;
	@UiField
	Label follow;
	@UiField
	FlowPanel tabPanel;
	@UiField
	SimplePanel content;
	@UiField
	Label loading;
	@UiField
	Style style;

	private ITypedCallback<EnumWidget> callbackSelect_;

	public ContactsTabPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setContent(Widget widget) {
		content.setWidget(widget);
	}

	public void setCallbackSelect(ITypedCallback<EnumWidget> callback) {
		callbackSelect_ = callback;
	}

	@UiHandler("follow")
	void onFollowClick(ClickEvent event) {
		if (callbackSelect_ != null) {
			setActiveTab(EnumWidget.SUBSCRIBEDTO);
			callbackSelect_.execute(EnumWidget.SUBSCRIBEDTO);
		}
	}

	@UiHandler("follower")
	void onFollowerClick(ClickEvent event) {
		if (callbackSelect_ != null) {
			setActiveTab(EnumWidget.SUBSCRIBER);
			callbackSelect_.execute(EnumWidget.SUBSCRIBER);
		}
	}

	@UiHandler("request")
	void onRequestClick(ClickEvent event) {
		if (callbackSelect_ != null) {
			setActiveTab(EnumWidget.REQUEST);
			callbackSelect_.execute(EnumWidget.REQUEST);
		}
	}

	public void setActiveTab(EnumWidget value) {
		switch (value) {
		case SUBSCRIBEDTO:
			follow.setStyleName(style.menuLabelActive());
			follower.setStyleName(style.menuLabelInactive());
			request.setStyleName(style.menuLabelInactive());
			break;
		case SUBSCRIBER:
			follow.setStyleName(style.menuLabelInactive());
			follower.setStyleName(style.menuLabelActive());
			request.setStyleName(style.menuLabelInactive());
			break;
		case REQUEST:
			follow.setStyleName(style.menuLabelInactive());
			follower.setStyleName(style.menuLabelInactive());
			request.setStyleName(style.menuLabelActive());
			break;
		}
		content.setWidget(loading);
	}
}
