package ro.northpole.jnorthpole.util;

public class Util {
	public static final String API_KEY = "api_key";
	public static final String SECRET = "secret";
	public static final String GUEST_API_KEY = "guest@northpole.ro";
	public static final String GUEST_SECRET = "guest";

	public static long getTime() {
		return System.currentTimeMillis() / 1000L;
	}
}
