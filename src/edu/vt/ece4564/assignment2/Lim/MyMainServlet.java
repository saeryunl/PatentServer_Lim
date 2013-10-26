package edu.vt.ece4564.assignment2.Lim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

public class MyMainServlet extends HttpServlet {
	private ParsedCaseDetails caseDetails_ = new ParsedCaseDetails();
	private JSONObject object_ = new JSONObject();

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);

		WebAppContext context = new WebAppContext();
		context.setWar("war");
		context.setContextPath("/");
		server.setHandler(context);

		server.start();
		server.join();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String type = req.getParameter("json");
		System.out.println("POST type: " + type);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					req.getInputStream()));
			JSONParser parser = new JSONParser();
			ContainerFactory containerFactory = new ContainerFactory() {
				public List creatArrayContainer() {
					return new LinkedList();
				}

				public Map createObjectContainer() {
					return new LinkedHashMap();
				}
			};
			Map json = (Map) parser.parse(reader, containerFactory);
			Iterator iter = json.entrySet().iterator();

			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				object_.put(entry.getKey().toString(), entry.getValue()
						.toString());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("doPost called with URI: " + req.getRequestURI());
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Iterator<String> iterator = caseDetails_.parsedCases().keySet()
				.iterator();
		while (iterator.hasNext()) {
			String title = iterator.next().toString();
			String detail = caseDetails_.parsedCases().get(title).toString();

			object_.put(title, detail);
		}
		System.out.println("JSON Objects: \n" + object_);

		System.out.println("doGet called with URI: " + req.getRequestURI());
		resp.getWriter().write(object_.toString());
	}
}
