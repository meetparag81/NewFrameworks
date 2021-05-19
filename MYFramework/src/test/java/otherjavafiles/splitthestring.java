package otherjavafiles;

public class splitthestring {

	public static void main(String[] args) {
		String  input="(en_US) English (United States)";
		int startindex = input.indexOf("(");
		int endindex = input.indexOf(")");
		input = input.substring(startindex, endindex);

	}

}
