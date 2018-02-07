package textSymplification;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextSymplifier {
	public static void main(String[] args) {
		Scanner input = null;
		FileWriter output = null;
		try {
			input = new Scanner(new FileReader("random-text-gen-master/src/main/java/Verb.txt"));
			output = new FileWriter("VerbNew.txt");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (input.hasNext()) {
			String in = input.nextLine();
			try {
				if(in.contains("/"))
				output.write(in.split(" ")[0] + " " + in.split(" ")[1]+ " "+ in.split(" ")[2].split("/")[0].split("-")[0] + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("completed");
	}
}
