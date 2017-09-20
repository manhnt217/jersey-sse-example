package makebelieve.jersey.sse_example.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author manhnt
 */
@Path("/greeting")
public class GreetingService {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String greeting() {
		return "Hello from the other side";
	}
}
