package org.gw.inputParser;

public class Classification {
	public static String POSITIVECLASS;
	public static String NEGATIVECLASS;

	public Classification(String pos, String neg) {
		POSITIVECLASS = pos;
		NEGATIVECLASS = neg;
	}
	@Override
	public boolean equals(Object obj) {
		String objclass = (String) obj;
		if (objclass.equals(this.POSITIVECLASS)) {
			return true;
		}
		return false;

	}

	@Override
	public String toString() {
		return "POS = "+POSITIVECLASS+" NEG = "+NEGATIVECLASS;
	}

}
