package uitextlib;

public abstract class ObjectHandler<T> {
	protected T object;
	public abstract void performAction(Scene<T> scene, String userInput);
	
	public ObjectHandler(T object) {
		this.object = object;
	}
	
	public T getObject() {
		return object;
	}
}
