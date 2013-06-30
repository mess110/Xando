package ro.northpole.jnorthpole.http;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class HttpWithEntity extends HttpEntityEnclosingRequestBase {
	public static String METHOD_NAME;

	public HttpWithEntity(String methodName) {
		super();
		METHOD_NAME = methodName;
	}

	@Override
	public String getMethod() {
		return METHOD_NAME;
	}
}
