package ro.northpole.jnorthpole.http;

import org.apache.http.HttpException;

@SuppressWarnings("serial")
public class NorthPoleException extends HttpException {

	public NorthPoleException(String string) {
		super(string);
	}
}
