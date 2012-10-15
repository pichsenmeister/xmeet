package webapp.client.view.content;

import webapp.client.event.XNameToken;
import webapp.client.presenter.content.FooterPresenterWidget;
import webapp.model.helper.XAnchor;
import webapp.model.helper.XContentName;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * the view for the footer, implements the interface declared in presenter
 *
 * @author David Pichsenmeister
 */
public class FooterWidget extends ViewImpl implements FooterPresenterWidget.IView {

	public static final String ABOUTUS = "\u00FCber uns";
	public static final String IMPRINT = "impressum";
	public static final String RESOURCES = "ressourcen";
	public static final String HELP = "hilfe";
	public static final String TERMS = "bedingungen";
	public static final String HOME = "home";
	
	public static final String STYLE_LINK = "link-widget";
	
	
	private FlowPanel mainPanel_;
	private Anchor home_;

	/**
	 * constructor, contains the view
	 */
	@Inject
	public FooterWidget() {
		mainPanel_ = new FlowPanel();
		mainPanel_.setHeight("100%");
		
		SimplePanel pHome = new SimplePanel();
		pHome.setStyleName(STYLE_LINK);
		mainPanel_.add(pHome);
		
		home_ = new Anchor();
		home_.setText(HOME);
		pHome.setWidget(home_);
		
		SimplePanel pAbout = new SimplePanel();
		pAbout.setStyleName(STYLE_LINK);
		mainPanel_.add(pAbout);
		
		Anchor about = new Anchor();
		about.setText(ABOUTUS);
		about.setHref(XAnchor.FOOTER + XContentName.ABOUT);
		pAbout.setWidget(about);
		
		SimplePanel pHelp = new SimplePanel();
		pHelp.setStyleName(STYLE_LINK);
		mainPanel_.add(pHelp);
		
		Anchor help = new Anchor();
		help.setText(HELP);
		help.setHref(XAnchor.FOOTER + XContentName.HELP);
		pHelp.setWidget(help);
		
		SimplePanel pTerms = new SimplePanel();
		pTerms.setStyleName(STYLE_LINK);
		mainPanel_.add(pTerms);
		
		Anchor terms = new Anchor();
		terms.setText(TERMS);
		terms.setHref(XAnchor.FOOTER + XContentName.TERMS);
		pTerms.setWidget(terms);
		
		SimplePanel pResource = new SimplePanel();
		pResource.setStyleName(STYLE_LINK);
		mainPanel_.add(pResource);
		
		Anchor resource = new Anchor();
		resource.setText(RESOURCES);
		resource.setHref(XAnchor.FOOTER + XContentName.RESOURCES);
		pResource.setWidget(resource);
		
		SimplePanel pImprint = new SimplePanel();
		pImprint.setStyleName(STYLE_LINK);
		mainPanel_.add(pImprint);
		
		Anchor imprint = new Anchor();
		imprint.setText(IMPRINT);
		imprint.setHref(XAnchor.FOOTER + XContentName.IMPRINT);
		pImprint.setWidget(imprint);
	}
	
	@Override
	public Widget asWidget() {
		return mainPanel_;
	}

	@Override
	public void isUserLogged(boolean logged) {
		if(logged) {
			home_.setHref("#" + XNameToken.NEWS);
		} else {
			home_.setHref("#" + XNameToken.START);
		}
	}
}
