package uitextlib.Labels;

public class TextLabel implements Showable{
	private String text;
	
	public TextLabel(String text) {
		this.text = text;
	}
	
	@Override
	public void show() {
		System.out.println(this.text);
	}
}
