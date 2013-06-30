package ro.northpole.jnorthpole;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ro.northpole.jnorthpole.util.Util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class StorageTest {

	private String id;
	private final String TEST_KEY = "np_test_key";
	private final String TEST_VALUE = "np_test_value";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private JsonObject getAuthJson() {
		JsonObject json = new JsonObject();
		json.addProperty(Util.API_KEY, Util.GUEST_API_KEY);
		json.addProperty(Util.SECRET, Util.GUEST_SECRET);
		return json;
	}

	@Test
	public void testCreate() throws IOException {
		JsonObject json = getAuthJson();
		json.addProperty(TEST_KEY, TEST_VALUE);
		Storage storage = new Storage(json);
		JsonObject jo = (JsonObject) storage.create();
		id = jo.get(TEST_KEY).getAsString();
		assertEquals(TEST_VALUE, jo.get(TEST_KEY).getAsString());
	}
	
	@Test
	public void testFind() throws IOException {
		JsonObject json = getAuthJson();
		json.addProperty("id", id);
		Storage storage = new Storage(json);
		JsonArray ja = storage.find().getAsJsonArray();
		JsonObject jo = ja.get(0).getAsJsonObject();
		assertEquals(TEST_VALUE, jo.get(TEST_KEY).getAsString());
	}
}
