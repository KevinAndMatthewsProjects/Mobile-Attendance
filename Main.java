import java.util.Arrays;

import javax.xml.transform.TransformerFactoryConfigurationError;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

public class Main {

	public static void main(String[] args) {
		DownloadServer.init();
		UploadServer.init();
		try {
			for (Element e : DownloadServer.Search("Palani", 1, 0, 1, null)) {
				System.out.println(e.getTextContent());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.exit(0);
		PNConfiguration pnConfiguration = new PNConfiguration();
		pnConfiguration
				.setSubscribeKey("SUBSCRIBEKEY");
		pnConfiguration
				.setPublishKey("PUBLISHKEY");
		pnConfiguration.setSecure(false);

		PubNub pubnub = new PubNub(pnConfiguration);

		pubnub.addListener(new SubscribeCallback() {

			@Override
			public void status(PubNub arg0, PNStatus arg1) {
				System.out.println(arg0.toString() + " " + arg1.toString());

			}

			@Override
			public void presence(PubNub arg0, PNPresenceEventResult arg1) {
				System.out.println(arg0.toString() + " " + arg1.toString());

			}

			@Override
			public void message(PubNub arg0, PNMessageResult arg1) {
				String json = arg1.getMessage().toString();
				System.out.println(json);
				try {
					JSONObject obj = new JSONObject(json);
					double latHome = 37.323585;
					double lngHome = -121.778626;
					double latClass = 37.324313;
					double lngClass = -121.778932;
					
					String name = (obj.get("lastName")).toString();
					double lat = Double.valueOf((obj.get("lat")).toString());
					double lng = Double.valueOf((obj.get("lng")).toString());

					double distHome = distance(Math.abs(lat - latHome),
							Math.abs(lng - lngHome));
					double distClass = distance(Math.abs(lat - latClass),
							Math.abs(lng - lngClass));
					boolean atClass = false;
					atClass = distClass <= distHome;
					System.out.println(name);
					String data = DownloadServer
							.Search(":" + name, 1, 0, 1, null).get(0)
							.getTextContent();
					System.out.println(data);
					String[] datas = data.split(":");
					System.out.println("DistanceToClass: " + distClass
							+ " distToHome: " + distHome);
					System.out.println("IsAtClass: " + atClass);
					String inClassString = atClass ? "Present" : "Absent";
					UploadServer.changeDataWithoutBreak(new String[] { "id", "LName",
							"FName", "Grade", "P1", "P2", "P3" }, new String[] {
							datas[0], ":"+datas[1],":"+ datas[2],":"+ datas[3], ":"+inClassString,":Absent", ":Absent" }, 7, "");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TransformerFactoryConfigurationError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		pubnub.subscribe().channels(Arrays.asList("Channel5")) // subscribe to
																// channels
				.execute();

	}

	public static double distance(double d1, double d2) {
		return Math.sqrt(d1 * d1 + d2 * d2);
	}

}
