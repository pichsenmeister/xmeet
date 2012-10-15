package webapp.client.view.customwidget;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.Widget;

public class SlideInAnimation extends Animation {
	
	private double height_;
	private double heightFinish_;
	private double heightStart_;
	private Widget widget_;
	
	public void setStartHeight(double height) {
		height_ = heightStart_ = height;
	}
	
	public void setFinishHeight(double height) {
		heightFinish_ = height;
	}
	
	public void setWidget(Widget widget) {
		widget_ = widget;
	}

	@Override
	protected void onUpdate(double progress) {
		widget_.setHeight((height_ + ((heightFinish_ - heightStart_) * progress)) + "px");
	}

}
