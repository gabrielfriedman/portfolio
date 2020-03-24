package uitextlib;

public class ActionPackage<T>{
	private Actions action;
	private Scene<T> scene;
	private ActionPackage<T> followupAction;
	
	public static enum Actions {
		POP,
		REFRESH,
		PUSH,
		ACTION
	}
	
	/*
	 * scene is only needed for the PUSH action, otherwise no scene is needed
	 */
	public ActionPackage(Actions action) {
		this.action = action;
	}
	
	public ActionPackage(Actions action, Scene<T> scene) {
		this.action = action;
		this.scene = scene;
	}
	
	public ActionPackage(Actions action, ActionPackage<T> followupAction) {
		this.action = action;
		this.followupAction = followupAction;
	}

	public Actions getAction() {
		return action;
	}

	public Scene<T> getScene() {
		return scene;
	}

	public ActionPackage<T> getFollowupAction() {
		return followupAction;
	}

}
