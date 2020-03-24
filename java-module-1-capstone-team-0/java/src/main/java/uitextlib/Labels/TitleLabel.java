package uitextlib.Labels;

import uitextlib.Configs;

public class TitleLabel implements Showable{
	private String text;

	public TitleLabel(String text) {
		this.text = text;
	}
	
	@Override
	public void show() {
		String centeredInt = Integer.toString(Configs.SCREEN_WIDTH/2 + text.length()/2);
		String centeredText = String.format("%"+centeredInt+"s", text);
		System.out.println(centeredText);
	}

}
