package ErrorFileTesting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ErrortestingbySet {
	
	
	public static void countFrequencies(ArrayList<String> list) {
  
        
        Set<String> st = new HashSet<String>(list);
        for (String s : st)
            System.out.println(s + ": " + Collections.frequency(list, s));
    }
  
    public static void main(String[] args)
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Geeks");
        list.add("for");
        list.add("Geeks");
        countFrequencies(list);

}
}
