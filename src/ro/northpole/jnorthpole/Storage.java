package ro.northpole.jnorthpole;

import ro.northpole.jnorthpole.util.Resource;

import com.google.gson.JsonObject;

public class Storage extends Resource {

	public Storage(JsonObject a) {
		super(a);
	}

	@Override
	protected String getKlass() {
		return "storage";
	}
}
