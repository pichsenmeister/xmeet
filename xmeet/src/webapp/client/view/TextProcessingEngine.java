package webapp.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextProcessingEngine {

	private static final String REGEX_PLACE = "\\W?@\\w*(\\W)?";
	private static final String REGEX_REPLACE_PLACE = "\\W";
	private static final String SPACE = " ";
	private static final String ENTER1 = "\\n";
	private static final String ENTER2 = "\\r";
	private static final String ENTER3 = "\\r\\n";
	private static final String AT = "@";
	private static final String EMPTY = "";

	private static List<String> split_ = null;

	public static String doAnalyzePlace() {
		if (split_.size() > 0) {
			for (String element : split_) {
				if (element.matches(REGEX_PLACE)) {
					element = element.replaceAll(REGEX_REPLACE_PLACE, EMPTY);
					return AT + element.toLowerCase();
				}
			}
		}
		return EMPTY;
	}

	public static String doAnalyzeAddress() {
		return EMPTY;
	}

	public static String[] splitString(String str) {
		return str.split(SPACE + "|" + ENTER1 + "|" + ENTER2 + "|" + ENTER3);
	}

	public static void setActualStringArray(String[] strArr) {
		List<String> split = new ArrayList<String>(Arrays.asList(strArr));
		if (split_ == null) {
			split_ = new ArrayList<String>();
		} else {
			split_.clear();
		}
		for (String str : split) {
			if (!(str.equals(EMPTY))) {
				split_.add(str);
			}
		}
	}
}
