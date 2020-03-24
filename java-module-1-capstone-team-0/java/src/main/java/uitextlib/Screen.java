package uitextlib;

import java.util.Stack;

import uitextlib.ActionPackage.*;

public class Screen <T>{
	private Stack<Scene <T>> sceneStack = new Stack<Scene <T>>();
	private T object; //generic object for the screen to interface with
	
	public Screen(Scene <T> currentScene, T parsableObject) {
		this.object = parsableObject;
		pushNewScene(currentScene);
	}
	
	public void refresh() {
		Configs.newBorder();
		getCurrentScene().redraw();
		getCurrentScene().show();
		Configs.newBorder();
	}
	
	public String request() {
		return getCurrentScene().request();
	}
	
	//receives the input from the VendingMachine object and performs an action
	// returns a Scene which will indicate whether or not to do an action on the physical machine
	public Scene<T> performUserInput(String input) {
		ActionPackage<T> action = getCurrentScene().processRequest(input);
		return performAction(action);
	}
	
	public Scene<T> performAction(ActionPackage<T> action) {
		Scene<T> actionScene = getCurrentScene();
		if (action == null) { return null; }
		if (action.getAction() == Actions.REFRESH) {
			//nothing to do
		}else if(action.getAction() == Actions.POP) {
			popScene();
		}else if(action.getAction() == Actions.PUSH) {
			Scene<T> newScene = action.getScene();
			pushNewScene(newScene);
		}else if(action.getAction() == Actions.ACTION) {
			performAction(action.getFollowupAction());
			return actionScene;
		}
		return null;
	}

	//chooses the next scene to display when refreshed
	public void pushNewScene(Scene<T> newScene) {
		sceneStack.push(newScene);
		newScene.initScreen(object);
		getCurrentScene().redraw();
	}

	//chooses the next scene to display when refreshed
	public void popScene() {
		sceneStack.pop();
	}
	
	public Scene<T> getCurrentScene() {
		return sceneStack.peek();
	}
}
