import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSTerm;
import com.clusterpoint.api.request.CPSRetrieveRequest;
import com.clusterpoint.api.request.CPSSearchRequest;
import com.clusterpoint.api.response.CPSListLastRetrieveFirstResponse;
import com.clusterpoint.api.response.CPSSearchResponse;

public class DownloadServer {

	private static CPSConnection conn;
	private static CPSConnection conn1;

	public static void init() {
		try {
			conn = new CPSConnection("tcps://cloud-us-0.clusterpoint.com:9008",
					"Attendance", "USERNAME", "PASSWORD",
					"ID");
			conn = new CPSConnection("tcps://cloud-us-0.clusterpoint.com:9008",
					"Schedule", "USERNAME", "PASSWORD",
					"ID");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String[] getProblem(String id, int dataBaseNum)
			throws Exception

	{// Retrieve single document specified by document id

		CPSConnection connection = null;
		switch (dataBaseNum) {
		case 0:
			connection = conn;
			break;
		case 1:
			connection = conn1;
			break;
		default:
			System.err.println("Invald dataBaseNum: " + dataBaseNum);
			System.exit(1);
		}
		try {

			CPSRetrieveRequest retr_req = new CPSRetrieveRequest(id);
			CPSListLastRetrieveFirstResponse retr_resp = (CPSListLastRetrieveFirstResponse) connection
					.sendRequest(retr_req);

			List<Element> docs = retr_resp.getDocuments();
			Iterator<Element> it = docs.iterator();
			
			Element e = it.next();
			
			
			return e.getTextContent().split(":");
		} catch (java.net.ConnectException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;

	}

	public static List<Element> Search(String term, int dataBaseNum,  int offset, int docs,
			HashMap list) {
		
		CPSConnection connection = null;
		switch (dataBaseNum) {
		case 0:
			connection = conn;
			break;
		case 1:
			connection = conn1;
			break;
		default:
			System.err.println("Invald dataBaseNum: " + dataBaseNum);
			System.exit(1);
		}

		String query = CPSTerm.get(term, "");

		CPSSearchRequest searchRequest = null;
		try {
			searchRequest = new CPSSearchRequest(query, offset, docs, list);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		CPSSearchResponse searchResponse = null;
		try {
			searchResponse = (CPSSearchResponse) conn
					.sendRequest(searchRequest);
		} catch (TransformerFactoryConfigurationError | Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (searchResponse.getHits() > 0) {
			System.out.println("Found " + searchResponse.getHits()
					+ " documents");
			System.out.println("showing from " + searchResponse.getFrom()
					+ " to " + searchResponse.getTo());

			List<Element> results = searchResponse.getDocuments();

			// Iterator<Element> it = results.iterator();
			// ArrayList<String> results = new ArrayList<String>();
			// while (it.hasNext()) {
			// Element el = it.next();
			// System.out.println(el.getTextContent());
			// }

			return results;
		}

		return null;

	}

}