package ro.northpole.jnorthpole.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

public class NorthPoleHttpClient {

	private DefaultHttpClient httpClient;

	public NorthPoleHttpClient() {
		httpClient = new DefaultHttpClient();
	}

	public String request(HttpRequestBase baseReq)
			throws ClientProtocolException, IOException {
		HttpResponse response = httpClient.execute(baseReq);
		return read(response);
	}

	private String read(HttpResponse response) throws IllegalStateException,
			IOException {

		InputStream ips = response.getEntity().getContent();
		BufferedReader buf = new BufferedReader(new InputStreamReader(ips,
				"UTF-8"));

		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			// throw new
			// NorthPoleException(response.getStatusLine().getReasonPhrase());
		}
		StringBuilder sb = new StringBuilder();
		String s;
		while (true) {
			s = buf.readLine();
			if (s == null || s.length() == 0)
				break;
			sb.append(s);

		}
		buf.close();
		ips.close();
		return sb.toString();
	}
}
