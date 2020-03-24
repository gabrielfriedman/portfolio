package uitextlib;

/*
 * Entry point to the application
 */
public class ScreenContainer<T> {
	private Screen<T> screen;
	private Scene<T> landingScene;
	private T object;
	private ObjectHandler<T> objectHandler;
	
	public ScreenContainer(ObjectHandler<T> objectHandler, Scene<T> landingScene) {
		this.objectHandler = objectHandler;
		this.landingScene = landingScene;
		this.object = objectHandler.getObject();
		init();
	}
	
	private void init() {
		screen = new Screen<T>(landingScene, object);
	}
	
	public void run() {
		while (true) {
			screen.refresh();
			String userInput = screen.request();
			Scene<T> actionScene = screen.performUserInput(userInput);
			objectHandler.performAction(actionScene, userInput);
		}
	}
}
