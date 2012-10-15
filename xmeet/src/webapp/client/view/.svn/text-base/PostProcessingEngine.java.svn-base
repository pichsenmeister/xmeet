package webapp.client.view;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PostProcessingEngine {
	
	private static final String LINK_1 = "<a href='#!search;q=";
	private static final String LINK_2 = "'>";
	private static final String LINK_3 = "</a>";
	
	private static final String REGEX_TITLE = "#\\w*";
	private static final String REGEX_LOCATION = "@\\w*";
	

//	private static boolean isSmaller(int isSmaller, int than) {
//		if(isSmaller < than) {
//			return true;
//		}
//		return false;
//	}
//	
//	public static List<Pair<Integer, Integer>> getIntArray(String text) {
//		List<Pair<Integer, Integer>> list = new ArrayList<Pair<Integer, Integer>>();
//		int start = -1;
//		do {
//			int at = text.indexOf(AT);
//			int hash = text.indexOf(HASH);
//			if(at >= 0 && at < hash) {
//				start = at;
//			} else if(hash >= 0) {
//				start = hash;
//			} else {
//				start = -1;
//			}
//			if(start >= 0) {	
//				int end = getNextSeparator(text.substring(start));
//				text = text.substring(end);
//				Pair<Integer, Integer> pair = new Pair<Integer, Integer>(start, end);
//				list.add(pair);
//			}
//		} while(start >= 0);
//		
//		return list;
//	}
//	
//	//TODO replacing doesn't work
//	private static int getNextSeparator(String text) {
//		int space = text.indexOf(" ");
//		int nosign = text.indexOf("\\W");
//		int underscore = text.indexOf("_");
//		return space != -1 ? space : (nosign != -1 ? nosign : (underscore != -1 ? underscore : text.length()));
//	}
	
	/**
	 * replaces the hashtags with a link
	 * @param text
	 * 			the text where hashtags are replaced
	 * @return
	 * 			the inner html
	 */
	public static String replaceHashes(String text) {
		List<String> hashes = getFound(text, REGEX_TITLE);
		for(String hash : hashes) {
			text = text.replaceAll(hash, LINK_1 + hash + LINK_2 + hash + LINK_3);
		}
		return text;
	}
	
	/**
	 * replaces the locations with a link
	 * @param text
	 * 			the text where locations are replaced
	 * @return
	 * 			the inner html
	 */
	public static String replaceLocations(String text) {
		List<String> ats = getFound(text, REGEX_LOCATION);
		for(String at : ats) {
			text = text.replaceAll(at, LINK_1 + at + LINK_2 + at + LINK_3);
		}
		return text;
	}
	
	private static List<String> getFound(String contents, String regex) {
		 List<String> results = new ArrayList<String>();
		 Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CASE);
		 Matcher matcher = pattern.matcher(contents);
	      
		 while (matcher.find()) {
			 if (matcher.groupCount() > 0) {
				 results.add(matcher.group(1));
			 } else {
				 results.add(matcher.group());
			 }
		 }
		 return results;
	  }
}
