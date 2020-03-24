# Text UI Library

    The purpose of this library is to provide a generic way to implement a text-based UI mimicking a phone 
    application style Screen.



1. A ScreenContainer should be instantiated and passed in the parameters of an object handler (which is passed in the argument of the object)
	and the first screen that will open.
	*The object handler is where you will define the behaviors of what happens to the object after the action is requested, and the object itself 
	will define the methods of what can happen to it.



2. The screen contains a stack of scenes that can be pushed on popped with the ScreenContainer running the following loop:
    >Refresh the screen to show the most current scene
    >Request the user input from the current scene
    >Perform actions on the current screen that can be done
    >Perform actions on the object handler which determines what to do to the object itself



3. Each scene must be built extending the abstract class Scene.
    *Overriding the init() method dictates the appearance of the scene. This must be built using the Labels 
    provided (TextLabel, TitleLabel, TextResponseLabel, MultipleChoiceLabel) then added to the 
    scene using the addNewElement(Showable) method.

        >   @Override
        >   protected void init(){
        >      TextLabel titleTextLabel = new TextLabel("Hello world!");
        >      addNewElement(titleTextLabel);
        >   }


    *Each scene MUST contain AT LEAST one Requestable object (TextResponseLabel or MultipleChoiceLabel).
     More can be added but only the last added will be recorded as the Requestable object. The behavior of 
     the scene on when the user inputs an appropriate answer is to be defined in the scene by overriding the 
     performAction method and returning an appropriate ActionPackage.

    *ActionPackages are objects that bundle an Action and Scene together. The following are possible screen actions:
        >PUSH
        >POP
        >REFRESH
        >ACTION

    *The PUSH method requires the ActionPackage constructor that takes in a Scene argument. 
    Otherwise, it can simply take one of the action enums as an argument.
    
    *The ACTION method requires another ActionPackage as its second argument to describe what it will do to the 
    current scene (PUSH/POP/REFRESH).



4. The ObjectHandler object that is passed in as an argument to the ScreenContainer overrides the performAction 
    method which is performed only when the ActionPackage passes up an ACTION enum. This action can defined through 
    a series of if/else statements inside of the object itself which receives the scene that called the ACTION as 
    well as the user input when they called the ACTION. The ObjectHandler is an abstract class that will be overridden
    to perform appropriate actions based on the scenes/object needs
    
    TODO: figure a way to make more generic maybe? not really readable in this way.