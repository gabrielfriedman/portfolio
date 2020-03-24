package uitextlib;

import java.util.ArrayList;
import java.util.List;

import uitextlib.Labels.Requestable;
import uitextlib.Labels.Showable;

public abstract class Scene<T>{
	
	private List<Showable> sceneList = new ArrayList<Showable>();
	private Requestable<T> requestableElement = null;
	protected T object;
	
	public abstract ActionPackage<T> processRequest(String request);
	protected abstract void init();

	/*
	 * should be called when the screen first starts up to connect all the scenes to the object to interface with
	 */
	
	public void initScreen(T object) {
		this.object = object;
	}
	
	@SuppressWarnings("unchecked")
	public void addNewElement(Showable element) {
		sceneList.add(element);
		requestableElement = element instanceof Requestable ? (Requestable<T>) element : requestableElement;
	}

	public void show() {
		for (Showable element: sceneList) {
			element.show();
		}
	}

	public String request() {
		if (requestableElement != null) {
			return requestableElement.request();
		}
		return null;
	}
	
	public void clear() {
		sceneList.clear();
	}
	
	public void redraw() {
		clear();
		init();
	}
}
