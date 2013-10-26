package edu.vt.ece4564.assignment2.Lim;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ParsedCaseDetails {
	public HashMap<String, String> parsedCases() {
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<String> details = new ArrayList<String>();
		HashMap<String, String> map = new HashMap<String, String>();

		try {
			String line;
			URL url = new URL(
					"http://www.legalzoom.com/intellectual-property-rights/intellectual-property-basics/top-5-intellectual-property-disputes/");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			StringBuilder sb = new StringBuilder();
			int startOfSpan = -1;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				System.out.println(line);
			}
			reader.close();

			String text = sb.toString();
			String anotherText = text;
			String subText;
			while ((startOfSpan = text.indexOf("<p><BR /> <b>1. ")) != -1) {
				subText = text.substring(startOfSpan
						+ "<p><BR /> <b>1. ".length());
				titles.add(subText.substring(0, subText.indexOf("</b>")));
				text = subText;
			}
			while ((startOfSpan = text.indexOf("2. ")) != -1) {
				subText = text.substring(startOfSpan + "2. ".length());
				titles.add(subText.substring(0, subText.indexOf("</b>")));
				text = subText;
			}

			while ((startOfSpan = text.indexOf("3. ")) != -1) {
				subText = text.substring(startOfSpan + "3. ".length());
				titles.add(subText.substring(0, subText.indexOf("</b>")));
				text = subText;
			}

			while ((startOfSpan = text.indexOf("4. ")) != -1) {
				subText = text.substring(startOfSpan + "4. ".length());
				titles.add(subText.substring(0, subText.indexOf("</b>")));
				text = subText;
			}

			while ((startOfSpan = text.indexOf("5. ")) != -1) {
				subText = text.substring(startOfSpan + "5. ".length());
				titles.add(subText.substring(0, subText.indexOf("</b>")));
				text = subText;
			}
			while ((startOfSpan = anotherText.indexOf("</b> <BR /> <BR />")) != -1) {
				subText = anotherText.substring(startOfSpan
						+ "</b> <BR /> <BR />".length());
				if (subText.contains(" <BR /> <BR /><b>")) {
					details.add(subText.substring(0,
							subText.indexOf(" <BR /> <BR /><b>")));
				} else {
					details.add(subText.substring(0,
							subText.indexOf(" <BR /></p>")));
				}
				anotherText = subText;
			}

			map.put(titles.get(0), details.get(0));
			map.put(titles.get(1), details.get(1));
			map.put(titles.get(2), details.get(2));
			map.put(titles.get(3), details.get(3));
			map.put(titles.get(4), details.get(4));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
