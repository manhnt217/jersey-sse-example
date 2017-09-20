package makebelieve.jersey.sse_example.server;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author manhnt
 */
@Path("/sse")
public class SSEService {
	
	private static final SseBroadcaster broadcaster = new SseBroadcaster();
	
	static {
		new Thread(() -> {
			OutboundEvent.Builder builder = new OutboundEvent.Builder();
			int i = 0;
			try {
				while (i++ > -1) {
					OutboundEvent outboundEvent = builder.name("msg").data("Message " + i).build();
					broadcaster.broadcast(outboundEvent);
					Thread.sleep(2000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	public SSEService() {
		System.out.println("New Instance");
	}
	
	@GET
	@Path("/register")
	@Produces("text/event-stream")
	public EventOutput getEvents() {
		final EventOutput eventOutput = new EventOutput();
		broadcaster.add(eventOutput);
		return eventOutput;
	}
}
