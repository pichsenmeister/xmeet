package webapp.model.enums;

public enum XVisibility {
	PUBLIC(0), PRIVATE(1), RESTRICTED(2), ONLYME(3);

	private int index_;

	XVisibility(int index) {
		index_ = index;
	}

	public int getEnumIndex() {
		return index_;
	}

	public static XVisibility getEnumValue(String text) {
		if (text.equalsIgnoreCase(PUBLIC.name())) {
			return PUBLIC;
		} else if (text.equalsIgnoreCase(PRIVATE.name())) {
			return PRIVATE;
		} else if (text.equalsIgnoreCase(RESTRICTED.name())) {
			return RESTRICTED;
		} else {
			return ONLYME;
		}
	}
}
