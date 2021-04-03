package ErrorFileTesting;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class csvreading {

	public static void main(String[] args) throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(  new InputStreamReader(new FileInputStream("C:\\WorkingfolderPB\\Error Summary POC\\jobResponse1510072_Phone Info.csv")));
		

	}

}
