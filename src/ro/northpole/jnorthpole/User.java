package ro.northpole.jnorthpole;

import ro.northpole.jnorthpole.util.Resource;

import com.google.gson.JsonObject;

public class User extends Resource {

	public User(JsonObject jsonObj) {
		super(jsonObj);
	}

	@Override
	protected String getKlass() {
		return "user";
	}
}
