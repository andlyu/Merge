import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Scanner;

import parsingOutput.FindConnections2;

public class Merge {
	public static final String NOUN_FILE = "ParsedTexts/Big Nouns.txt";
	public static final String VERB_FILE = "ParsedTexts/Big Verbs.txt";
	public static final String NOUN_OUT_FILE = "Text Files/Encyclopedia Nouns.txt";
	public static final String VERB_OUT_FILE = "Text Files/Encyclopedia Verbs.txt";

	


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<String, NounWithVerbs> connectionMap = FindConnections2.infoFromParsed();

		Scanner nounInput = null; // scanners
		Scanner verbInput = null;
		PrintWriter NounOutput = null;
		LinkedHashMap<String, String> nounLines = new LinkedHashMap<>(); // arrayList with all Nouns
		LinkedList<String> allNounLines = new LinkedList<>();
		LinkedHashMap<String, String> verbLines = new LinkedHashMap<>();
		HashMap<String, Boolean> nounOutput = new HashMap<>();
		ArrayList<String> repeatingNouns = new ArrayList<>();
		ArrayList<String> repeatingVerbs = new ArrayList<>();

		try {// Initiates scanners
			nounInput = new Scanner(new FileReader(NOUN_FILE));
			verbInput = new Scanner(new FileReader(VERB_FILE));
			NounOutput =  new PrintWriter(NOUN_OUT_FILE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (nounInput.hasNext()) {//// fills Arrays with all nouns and verbs from file
			String in = nounInput.nextLine();
			if(!nounLines.containsKey(in.split(" ")[5]))
			nounLines.put(in.split(" ")[5], in);
			allNounLines.add(in);
		}
		while (verbInput.hasNext()) {
			String in = verbInput.nextLine();
			if(!verbLines.containsKey(in.split(" ")[5]))
			verbLines.put(in.split(" ")[5], in);

		}

		for (String a : connectionMap.keySet()) {// add all nouns and their predisesors
			/*
			 * go through the nousn add the noun&predisecor to the output Nouns run through
			 * nouns, if the current is outNouns print it out: !!!
			 */// TODO SOME NOUNS REPEAT IN BIG NOUNS
				// TODO what if some stuff already has a negative: double negative
			if (nounLines.containsKey(a)) {
				String curNounID = nounLines.get(a).split(" ")[0];
				nounOutput.put(curNounID, true);
				while (curNounID.contains(".")) {

					curNounID = curNounID.substring(0, curNounID.lastIndexOf("."));
					if (!nounOutput.containsKey(curNounID))
						nounOutput.put(curNounID, false);
				}
			}
		}
		for (Object noun : allNounLines.toArray()) {
			String id = ((String)noun).split(" ")[0];
			if (nounOutput.containsKey(id)) {
				String[] line = ((String)noun).split(" ");
				if (!nounOutput.get(id)) {
					NounOutput.write("-");
				}
				NounOutput.write(line[0] + " " + line[5] + "\n");
			}
		}
		NounOutput.close();
		System.out.println("finished");
	}
}
