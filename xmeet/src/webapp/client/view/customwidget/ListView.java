package webapp.client.view.customwidget;

import java.util.ArrayList;
import java.util.List;

import net.sf.gilead.pojo.gwt.LightEntity;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class ListView<T extends LightEntity> extends FlowPanel {

	public interface IGenerator<M extends LightEntity> {
		Widget generateWidget(M model);
	}

	private List<T> store_;
	private List<Widget> widgets_;
	private IGenerator<T> generator_;

	public ListView() {
		store_ = new ArrayList<T>();
		widgets_ = new ArrayList<Widget>();
	}

	public void setStore(List<T> store) {
		widgets_.clear();
		store_.clear();
		this.clear();
		for (T model : store) {
			Widget widget = generator_.generateWidget(model);
			add(widget);
			store_.add(model);
			widgets_.add(widget);
		}
	}

	public void add(T model) {
		Widget widget = generator_.generateWidget(model);
		add(widget);
		store_.add(model);
		widgets_.add(widget);
	}

	public void addAll(List<T> list) {
		for (T model : list) {
			Widget widget = generator_.generateWidget(model);
			add(widget);
			store_.add(model);
			widgets_.add(widget);
		}
	}

	public void insert(int pos, T model) {
		Widget widget = generator_.generateWidget(model);
		insert(widget, pos);
		store_.add(pos, model);
		widgets_.add(pos, widget);
	}

	public Widget getWidget(T model) {
		return widgets_.get(store_.indexOf(model));
	}

	public T getModel(Widget widget) {
		return store_.get(widgets_.indexOf(widget));
	}

	public void setGenerator(IGenerator<T> generator) {
		generator_ = generator;
	}
}
