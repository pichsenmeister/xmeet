package webapp.client.view.qr;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.ImageChart;

public class VCard extends SimplePanel {

	public VCard() {
	    ImageChart.Options options = ImageChart.Options.create();    
	    options.set("cht", "qr");
	    options.set("chs", "100x100");
	    options.set("chld", "0");
	      
//	    options.set("chl", vcard);

	    DataTable dataTable = DataTable.create();

	    setSize("100px", "100px");
	    add(new ImageChart(dataTable, options));
	}
	
	private String setVCardData(String name, String email, String phone) {
		String newline = "\n";
		StringBuilder card = new StringBuilder();
		card.append("BEGIN:VCARD");
		card.append(newline);
		card.append("VERSION:3.0");
		card.append(newline);
		card.append("N:" + name);
		card.append(newline);
//		FN:Max Mustermann
//		ORG:Wikipedia
//		URL:http://de.wikipedia.org/
		card.append("EMAIL;TYPE=INTERNET:" + email);
		card.append(newline);
		card.append("TEL;VOICE:" + phone);
		card.append(newline);
//		ADR;TYPE=intl,work,postal,parcel:;;Musterstraﬂe 1;Musterstadt;;12345;Germany
		card.append("END:VCARD");
		
		return card.toString();
	}
	
	public String setICalData(String eventname, String eventdesc) {
		String newline = "\n";
		StringBuilder cal = new StringBuilder();
		cal.append("BEGIN:VCALENDAR");
		cal.append(newline);
		cal.append("VERSION:3.0");
		cal.append(newline);
//		PRODID:http://www.example.com/calendarapplication/
		cal.append("METHOD:PUBLISH");
		cal.append(newline);
		cal.append("BEGIN:VEVENT");
		cal.append(newline);
//		UID:461092315540@example.com
//		ORGANIZER;CN="Alice Balder, Example Inc.":MAILTO:alice@example.com
		cal.append("SUMMARY:" + eventname);
		cal.append(newline);
		cal.append("DESCRIPTION:" + eventdesc);
		cal.append(newline);
		cal.append("CLASS:PUBLIC");
		cal.append(newline);
		cal.append("DTSTART:20110629T220000Z");
		cal.append(newline);
		cal.append("DTEND:20110629T223000Z");
		cal.append(newline);
		cal.append("DTSTAMP:20110629T163000Z");
		cal.append(newline);
		cal.append("END:VEVENT");
		cal.append(newline);
		cal.append("END:VCALENDAR");
		
		return cal.toString();
	}

	public SimplePanel getPanel() {
	    return this;
	}

}
