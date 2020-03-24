package uitextlib.Labels;

import uitextlib.ActionPackage;

public interface Requestable<T> {
	public String request();
	public ActionPackage<T> processRequest(String userInput);
}
