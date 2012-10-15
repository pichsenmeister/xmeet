package webapp.client.view.customwidget;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.Widget;

public class SlideOutAnimation extends Animation {

	private double height_;
	private Widget widget_;
	private boolean remove_ = false;

	public void setStartHeight(double height) {
		height_ = height;
	}

	public void setWidget(Widget widget) {
		widget_ = widget;
	}

	public void removeFromParent(boolean remove) {
		remove_ = remove;
	}

	@Override
	protected void onUpdate(double progress) {
		widget_.setHeight((height_  * (1.0-progress)) + "px");
	}

	@Override
	protected void onComplete() {
		widget_.setHeight(0 + "px");
		if(remove_) {
			widget_.removeFromParent();
		}
	}

}

