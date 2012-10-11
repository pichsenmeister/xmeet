package webapp.client.view.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import webapp.client.callback.ITypedCallback;
import webapp.client.presenter.settings.SettingsPresenter;
import webapp.client.presenter.settings.SettingsPresenter.ICallbackNotification;
import webapp.client.presenter.settings.SettingsPresenter.ICallbackPassword;
import webapp.client.presenter.settings.SettingsPresenter.ICallbackProfile;
import webapp.client.view.customwidget.XButton;
import webapp.client.view.customwidget.XPasswordBox;
import webapp.client.view.customwidget.XTextBox;
import webapp.model.XMedia;
import webapp.model.XUser;
import webapp.model.enums.XMediaType;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for XUser settings, implements the interface declared in presenter
 * 
 * @author David Pichsenmeister
 */
public class SettingsView extends ViewImpl implements SettingsPresenter.IView {

	public static final int WIDTH = 980;
	public static final int HEIGHT = 669;
	public static final int WIDTH_TEXTBOX = 300;

	public static final String PROFILE = "profil";
	public static final String PASSWORD = "passwort";
	public static final String NOTIFICATION = "benachrichtigungen";
	public static final String IMAGE = "profilbild";
	public static final String EXPORT_IMPORT = "import & export";
	public static final String UPLOAD = "upload";
	public static final String SAVE = "speichern";

	public static final String NAME = "vollst\u00E4ndiger name";
	public static final String MAIL = "email";
	public static final String LOCATION = "ort";
	public static final String PRIVACY = "privatsph\u00E4re";
	public static final String WEBSITE = "webseite";

	public static final String OLD_PW = "altes passwort";
	public static final String NEW_PW = "neues passwort";
	public static final String REPEAT_PW = "neues passwort best\u00E4tigen";

	public static final String NOTIFICATE = "benachrichtige mich per email ...";
	public static final String NEW_MSG = "... bei neuen nachrichten";
	public static final String NEW_EVENT = "... bei neuen events";
	public static final String UPDATES = "... bei updates der plattform";
	public static final String NEW_LISTENER = "... wenn mir neue benutzer lauschen";

	public static final String IMAGE_INFO = "max 500kb. gif, jpg, png. ";

	public static final String STYLE_WRAPPER = "wrapper";
	public static final String STYLE_CONTENT = "user-content-wrapper";
	public static final String STYLE_CONTROL = "user-control-panel";
	public static final String STYLE_LABEL = "settings-label";
	public static final String STYLE_CHECKBOX = "settings-checkbox";
	public static final String STYLE_FOOTER = "footer-panel";
	public static final String STYLE_IMAGE = "userpage-image";
	public static final String STYLE_IMAGE_LIST = "userpage-image-list";
	public static final String STYLE_IMAGE_LIST_SELECTED = "userpage-image-list-selected";

	private FlowPanel mainPanel_;
	private FlowPanel content_;
	private SimplePanel top_;
	private SimplePanel bottom_;
	private TabLayoutPanel tabPanel_;

	private XTextBox name_;
	private XTextBox mail_;
	private XTextBox location_;
	private CheckBox privacy_;
	private XTextBox website_;

	private XPasswordBox oldPassword_;
	private XPasswordBox newPassword1_;
	private XPasswordBox newPassword2_;

	private CheckBox newEvents_;
	private CheckBox newMessages_;
	private CheckBox newListener_;
	private CheckBox platformUpdates_;

	private Image image_;
	private XMedia media_;
	private List<XMedia> imageList_;
	private FlowPanel imageListView_;
	private Hidden user_;

	private ICallbackProfile callbackProfile_;
	private ICallbackPassword callbackPassword_;
	private ICallbackNotification callbackNotification_;
	private ITypedCallback<XMedia> callbackImage_;
	private ITypedCallback<XMedia> callbackSaveImage_;

	/**
	 * constructor, contains the view
	 */
	@Inject
	public SettingsView() {
		mainPanel_ = new FlowPanel();
		mainPanel_.setStyleName(STYLE_WRAPPER);

		imageList_ = new ArrayList<XMedia>();

		top_ = new SimplePanel();
		mainPanel_.add(top_);
		top_.setStyleName(STYLE_CONTROL);

		content_ = new FlowPanel();
		mainPanel_.add(content_);
		content_.getElement().getStyle().setMarginTop(70, Unit.PX);
		content_.getElement().getStyle().setBottom(15, Unit.PX);
		content_.getElement().getStyle().setPadding(15, Unit.PX);
		content_.getElement().getStyle().setPaddingBottom(0, Unit.PX);
		content_.setStyleName(STYLE_CONTENT);

		tabPanel_ = new TabLayoutPanel(30, Unit.PX);
		content_.add(tabPanel_);
		tabPanel_.setSize(WIDTH + "px", HEIGHT + "px");

		tabPanel_.setAnimationDuration(700);

		tabPanel_.add(paintProfileTab(), PROFILE);
		tabPanel_.add(paintPasswordTab(), PASSWORD);
		tabPanel_.add(paintNotificationTab(), NOTIFICATION);
		tabPanel_.add(paintImageTab(), IMAGE);
		tabPanel_.add(paintExportImportTab(), EXPORT_IMPORT);

		bottom_ = new SimplePanel();
		bottom_.setStyleName(STYLE_FOOTER);
		content_.add(bottom_);
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == SettingsPresenter.TYPE_CONTENT_CONTROL) {
			setContentControl(content);
		} else if (slot == SettingsPresenter.TYPE_CONTENT_MAIN) {
			setContentMain(content);
		} else if (slot == SettingsPresenter.TYPE_CONTENT_FOOTER) {
			setContentFooter(content);
		} else {
			super.setInSlot(slot, content);
		}
	}

	private void setContentControl(Widget content) {
		if (top_.getWidget() != null) {
			top_.clear();
		}
		top_.setWidget(content);
	}

	private void setContentMain(Widget content) {
		content_.clear();
		content_.add(content);
	}

	/**
	 * sets content in slot
	 * 
	 * @param content
	 *            content to set in slot
	 */
	private void setContentFooter(Widget content) {
		if (bottom_.getWidget() != null) {
			bottom_.clear();
		}
		bottom_.add(content);
	}

	private Widget paintProfileTab() {
		FlexTable panel = new FlexTable();
		panel.setCellPadding(5);

		Label name = new Label(NAME);
		panel.setWidget(0, 0, name);
		name.setWidth("130px");
		name_ = new XTextBox();
		name_.setMaxLength(30);
		panel.setWidget(0, 1, name_);
		name_.setWidth(WIDTH_TEXTBOX + "px");

		Label mail = new Label(MAIL);
		panel.setWidget(1, 0, mail);
		mail_ = new XTextBox();
		mail_.setMaxLength(50);
		panel.setWidget(1, 1, mail_);
		mail_.setWidth(WIDTH_TEXTBOX + "px");

		Label location = new Label(LOCATION);
		panel.setWidget(2, 0, location);
		location_ = new XTextBox();
		location_.setMaxLength(50);
		panel.setWidget(2, 1, location_);
		location_.setWidth(WIDTH_TEXTBOX + "px");

		Label website = new Label(WEBSITE);
		panel.setWidget(3, 0, website);
		website_ = new XTextBox();
		website_.setMaxLength(50);
		panel.setWidget(3, 1, website_);
		website_.setWidth(WIDTH_TEXTBOX + "px");

		privacy_ = new CheckBox();
		panel.setWidget(4, 0, privacy_);
		Label privacy = new Label(PRIVACY);
		panel.setWidget(4, 1, privacy);

		XButton saveProfile = new XButton(SAVE);
		saveProfile.setWidth(90 + "px");
		saveProfile.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (callbackProfile_ != null) {
					callbackProfile_.execute(getProfileMap());
				}
			}
		});
		panel.setWidget(5, 1, saveProfile);

		panel.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		panel.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		panel.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		panel.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		panel.getCellFormatter().setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		panel.getCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_CENTER);
		panel.getCellFormatter().setHorizontalAlignment(5, 1, HasHorizontalAlignment.ALIGN_RIGHT);

		return panel;
	}

	private Widget paintPasswordTab() {
		FlowPanel panel = new FlowPanel();

		oldPassword_ = new XPasswordBox();
		panel.add(oldPassword_);
		oldPassword_.setWidth(WIDTH_TEXTBOX + "px");
		Label oldPassword = new Label(OLD_PW);
		panel.add(oldPassword);

		newPassword1_ = new XPasswordBox();
		panel.add(newPassword1_);
		newPassword1_.setWidth(WIDTH_TEXTBOX + "px");
		newPassword1_.getElement().getStyle().setMarginTop(20, Unit.PX);
		Label newPassword1 = new Label(NEW_PW);
		panel.add(newPassword1);

		newPassword2_ = new XPasswordBox();
		panel.add(newPassword2_);
		newPassword2_.setWidth(WIDTH_TEXTBOX + "px");
		newPassword2_.getElement().getStyle().setMarginTop(20, Unit.PX);
		Label newPassword2 = new Label(REPEAT_PW);
		panel.add(newPassword2);

		XButton savePassword = new XButton(SAVE);
		savePassword.setWidth(90 + "px");
		savePassword.getElement().getStyle().setMarginLeft(225, Unit.PX);
		savePassword.getElement().getStyle().setMarginTop(30, Unit.PX);
		savePassword.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (callbackPassword_ != null) {
					callbackPassword_.execute(getPasswordMap());
				}
			}
		});
		panel.add(savePassword);

		return panel;
	}

	private Widget paintNotificationTab() {
		FlowPanel panel = new FlowPanel();

		Label note = new Label(NOTIFICATE);
		panel.add(note);
		note.getElement().getStyle().setFontSize(18, Unit.PX);

		newEvents_ = new CheckBox();
		newEvents_.setStyleName(STYLE_CHECKBOX);
		panel.add(newEvents_);
		Label newEvents = new Label(NEW_EVENT);
		newEvents.setStyleName(STYLE_LABEL);
		panel.add(newEvents);

		newMessages_ = new CheckBox();
		newMessages_.setStyleName(STYLE_CHECKBOX);
		panel.add(newMessages_);
		Label newMessages = new Label(NEW_MSG);
		newMessages.setStyleName(STYLE_LABEL);
		panel.add(newMessages);

		newListener_ = new CheckBox();
		newListener_.setStyleName(STYLE_CHECKBOX);
		panel.add(newListener_);
		Label newListener = new Label(NEW_LISTENER);
		newListener.setStyleName(STYLE_LABEL);
		panel.add(newListener);

		platformUpdates_ = new CheckBox();
		platformUpdates_.setStyleName(STYLE_CHECKBOX);
		panel.add(platformUpdates_);
		Label platformUpdates = new Label(UPDATES);
		platformUpdates.setStyleName(STYLE_LABEL);
		panel.add(platformUpdates);

		XButton saveNotification = new XButton(SAVE);
		saveNotification.setWidth(90 + "px");
		saveNotification.getElement().getStyle().setMarginLeft(100, Unit.PX);
		saveNotification.getElement().getStyle().setMarginTop(30, Unit.PX);
		saveNotification.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (callbackNotification_ != null) {
					callbackNotification_.execute(getNotificationMap());
				}
			}
		});
		panel.add(saveNotification);

		return panel;
	}

	private Widget paintImageTab() {
		FlowPanel imagePanel = new FlowPanel();

		final FormPanel form = new FormPanel();
		imagePanel.add(form);
		form.setAction("/xmeet/upload");

		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);

		FlowPanel panel = new FlowPanel();
		form.setWidget(panel);

		user_ = new Hidden();
		user_.setName("userid");
		panel.add(user_);

		image_ = new Image();
		image_.setStyleName(STYLE_IMAGE);
		panel.add(image_);

		final FileUpload upload = new FileUpload();
		upload.setName("uploadFormElement");
		upload.getElement().getStyle().setDisplay(Display.BLOCK);
		panel.add(upload);

		Label info = new Label(IMAGE_INFO);
		panel.add(info);

		XButton submit = new XButton(UPLOAD);
		submit.setWidth(90 + "px");
		submit.getElement().getStyle().setMarginTop(10, Unit.PX);
		panel.add(submit);

		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				form.submit();
			}
		});

		form.addSubmitHandler(new SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				String filetype = upload.getFilename().toLowerCase();
				filetype = filetype.substring(filetype.lastIndexOf(".") + 1);
				if (!(filetype.equals("gif") || filetype.equals("jpg") || filetype.equals("jpeg") || filetype
						.equals("png"))) {
					event.cancel();
				}
			}
		});

		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				if (callbackImage_ != null) {
					String result = event.getResults();
					String name = result.substring(0, result.indexOf(";"));
					String url = result.substring(result.indexOf(";") + 1);
					XMedia media = new XMedia();
					media.setMediaName(name);
					media.setMediaType(XMediaType.IMAGE);
					media.setMediaURL(url + name);
					callbackImage_.execute(media);
				}
			}
		});

		imageListView_ = new FlowPanel();
		imageListView_.getElement().getStyle().setMarginTop(10, Unit.PX);
		imagePanel.add(imageListView_);

		XButton save = new XButton(SAVE);
		imagePanel.add(save);
		save.setWidth(90 + "px");
		save.getElement().getStyle().setMarginTop(30, Unit.PX);
		save.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (callbackSaveImage_ != null) {
					callbackSaveImage_.execute(media_);
				}
			}
		});

		return imagePanel;
	}

	private void paintImageList() {
		imageListView_.clear();
		for (final XMedia media : imageList_) {
			final Image image = new Image();
			image.setStyleName(STYLE_IMAGE_LIST);
			image.setUrl(media.getMediaURL());
			image.setAltText(media.getMediaName());
			image.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					image_.setUrl(image.getUrl());
					image_.setAltText(image.getAltText());
					media_ = media;
					for (int i = 0; i < imageListView_.getWidgetCount(); i++) {
						imageListView_.getWidget(i).removeStyleName(STYLE_IMAGE_LIST_SELECTED);
					}
					image.addStyleName(STYLE_IMAGE_LIST_SELECTED);
				}
			});
			if (media.getMediaID().equals(media_.getMediaID())) {
				image.addStyleName(STYLE_IMAGE_LIST_SELECTED);
			}
			imageListView_.add(image);
		}
	}

	private Widget paintExportImportTab() {
		HTML html = new HTML("export und import kommt hier hin");
		return html;
	}

	@Override
	public Widget asWidget() {
		return mainPanel_;
	}

	private HashMap<String, Boolean> getNotificationMap() {
		HashMap<String, Boolean> map = new HashMap<String, Boolean>();
		map.put(SettingsPresenter.NOTE_EVENT, newEvents_.getValue());
		map.put(SettingsPresenter.NOTE_MSG, newMessages_.getValue());
		map.put(SettingsPresenter.NOTE_LISTENER, newListener_.getValue());
		map.put(SettingsPresenter.NOTE_UPDATE, platformUpdates_.getValue());

		return map;
	}

	private HashMap<String, String> getPasswordMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(SettingsPresenter.OLD_PW, oldPassword_.getText());
		map.put(SettingsPresenter.NEW_PW, newPassword1_.getText());
		map.put(SettingsPresenter.REPEAT_PW, newPassword2_.getText());

		return map;
	}

	private HashMap<String, String> getProfileMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(SettingsPresenter.NAME, name_.getText().toLowerCase());
		map.put(SettingsPresenter.MAIL, mail_.getText().toLowerCase());
		map.put(SettingsPresenter.LOCATION, location_.getText().toLowerCase());
		String website = website_.getText().toLowerCase();
		if (website.startsWith("http://")) {
			map.put(SettingsPresenter.WEBSITE, website);
		} else {
			map.put(SettingsPresenter.WEBSITE, "http://" + website);
		}
		if (privacy_.getValue()) {
			map.put(SettingsPresenter.PRIVACY, "true");
		} else {
			map.put(SettingsPresenter.PRIVACY, "false");
		}

		return map;
	}

	@Override
	public void setNotificationMap(HashMap<String, Boolean> map) {
		newEvents_.setValue(map.get(SettingsPresenter.NOTE_EVENT));
		newMessages_.setValue(map.get(SettingsPresenter.NOTE_MSG));
		newListener_.setValue(map.get(SettingsPresenter.NOTE_LISTENER));
		platformUpdates_.setValue(map.get(SettingsPresenter.NOTE_UPDATE));
	}

	@Override
	public void setProfileMap(HashMap<String, String> map) {
		name_.setText(map.get(SettingsPresenter.NAME));
		mail_.setText(map.get(SettingsPresenter.MAIL));
		location_.setText(map.get(SettingsPresenter.LOCATION));
		String website = map.get(SettingsPresenter.WEBSITE);
		if ((website != null) && !website.isEmpty()) {
			website_.setText(website);
		} else {
			website_.setText("http://");
		}
		if (map.get(SettingsPresenter.PRIVACY).equals("true")) {
			privacy_.setValue(true);
		} else {
			privacy_.setValue(false);
		}
	}

	@Override
	public void setCallbackNotification(ICallbackNotification callback) {
		callbackNotification_ = callback;
	}

	@Override
	public void setCallbackPassword(ICallbackPassword callback) {
		callbackPassword_ = callback;
	}

	@Override
	public void setCallbackProfile(ICallbackProfile callback) {
		callbackProfile_ = callback;
	}

	@Override
	public void setImage(XMedia media) {
		media_ = media;
		imageList_.add(media_);
		paintImageList();
		image_.setUrl(media_.getMediaURL());
		image_.setAltText(media_.getMediaName());
	}

	@Override
	public void setImageList(List<XMedia> images) {
		imageList_ = images;
		paintImageList();
	}

	@Override
	public void setUser(XUser user) {
		user_.setValue(user.getUserID().toString());
	}

	@Override
	public void setCallbackImage(ITypedCallback<XMedia> callback) {
		callbackImage_ = callback;
	}

	@Override
	public void setCallbackSaveImage(ITypedCallback<XMedia> callback) {
		callbackSaveImage_ = callback;
	}
}
