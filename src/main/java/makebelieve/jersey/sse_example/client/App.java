package makebelieve.jersey.sse_example.client;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.glassfish.jersey.media.sse.EventListener;
import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.Scanner;

/**
 * @author manhnt
 */
public class App {
	public static void main(String[] args) throws UnirestException {
//		System.out.println(Unirest.get(API.path("/greeting")).asString().getBody());
		
		Client client = ClientBuilder
				.newBuilder()
				.register(SseFeature.class)
				.build();
		
		final WebTarget resourceTarget = client
				.target(API.path("/sse/register"));
		
		
		new Thread(() -> {
			EventSource eventSource = listen(resourceTarget);
			while (true) {
				try {
					Thread.sleep(500);
					if (!eventSource.isOpen()) {
						eventSource = listen(resourceTarget);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private static EventSource listen(WebTarget resourceTarget) {
		EventSource eventSource = EventSource.target(resourceTarget)
				.usePersistentConnections()
				.build();
		
		eventSource.register(inboundEvent -> {
			String rawData = inboundEvent.readData(String.class);
			System.out.println(rawData);
		}, "msg");
		eventSource.open();
		return eventSource;
	}
}
