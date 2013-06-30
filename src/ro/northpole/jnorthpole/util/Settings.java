package ro.northpole.jnorthpole.util;

public class Settings {

	private static Settings instance;
	public String url = "http://northpole.ro/";

	public static Settings getInstance() {
		if (instance == null) {
			instance = new Settings();
		}
		return instance;
	}

	private Settings() {
	}

	public void setURL(String s) {
		this.url = s;
	}
}
