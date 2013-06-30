package ro.northpole.jnorthpole.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.entity.StringEntity;

import ro.northpole.jnorthpole.http.HttpWithEntity;
import ro.northpole.jnorthpole.http.NorthPoleHttpClient;
import ro.northpole.jnorthpole.http.methods.HttpDeleteWithEntity;
import ro.northpole.jnorthpole.http.methods.HttpGetWithEntity;
import ro.northpole.jnorthpole.http.methods.HttpPostWithEntity;
import ro.northpole.jnorthpole.http.methods.HttpPutWithEntity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class Resource {
	public JsonObject jsonObj;
	private NorthPoleHttpClient httpClient;

	public Resource(JsonObject jsonObj) {
		this.jsonObj = jsonObj;
		this.httpClient = new NorthPoleHttpClient();
	}

	public JsonElement create() throws IOException {
		return doIt(new HttpPostWithEntity());

	}

	public JsonElement find() throws IOException {
		return doIt(new HttpGetWithEntity());
	}

	public JsonElement update() throws IOException {
		return doIt(new HttpPutWithEntity());
	}

	public JsonElement delete() throws IOException {
		return doIt(new HttpDeleteWithEntity());
	}

	private URI getApiUrl() {
		try {
			return new URI(Settings.getInstance().url + getKlass());
		} catch (URISyntaxException e) {
			return null;
		}
	}

	protected abstract String getKlass();

	private JsonElement doIt(HttpWithEntity httpReq) throws IOException {
		httpReq.setEntity(new StringEntity(jsonObj.toString()));
		httpReq.setHeader("Content-Type", "application/json");
		httpReq.setURI(getApiUrl());
		String result = httpClient.request(httpReq);

		JsonParser parser = new JsonParser();
		JsonElement o = (JsonElement) parser.parse(result);

		return o;
	}
}
