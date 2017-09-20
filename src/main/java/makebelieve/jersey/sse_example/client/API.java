package makebelieve.jersey.sse_example.client;

/**
 * @author manhnt
 */
public class API {
	
	private static final String BASE_URL = "http://localhost:8080/api";
	
	public static String path(String path) {
		return BASE_URL + path;
	}
}
