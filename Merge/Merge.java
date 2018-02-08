import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

import parsingOutput.FindConnections2;

public class Merge {
	public static final String NOUN_FILE = "ParsedTexts/Big Nouns.txt";
	public static final String VERB_FILE = "ParsedTexts/Big Verbs.txt";

	public static void main(String[] args) {
		HashMap<String, NounWithVerbs> connectionMap = FindConnections2.infoFromParsed();

		Scanner nounInput = null; // scanners
		Scanner verbInput = null;
		LinkedHashMap<String, String> nounLines = new LinkedHashMap<>(); // arrayList with all Nouns
		LinkedHashMap<String, String> verbLines = new LinkedHashMap<>();
		HashMap<String, Boolean> nounOutput = new HashMap<>();

		try {// Initiates scanners
			nounInput = new Scanner(new FileReader(NOUN_FILE));
			verbInput = new Scanner(new FileReader(VERB_FILE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (nounInput.hasNext()) {//// fills Arrays with all nouns and verbs from file
			String in = nounInput.nextLine();
			nounLines.put(in.split(" ")[5], in);
		}
		while (verbInput.hasNext()) {
			String in = verbInput.nextLine();
			verbLines.put(in.split(" ")[5], in);
		}

		for (String a : connectionMap.keySet()) {// add all nouns and their predisesors
			/*
			 * go through the nousn add the noun&predisecor to the output Nouns run through
			 * nouns, if the current is outNouns print it out: !!!
			 */
			if (nounLines.containsKey(a)) {
				String curNounID = nounLines.get(a).split(" ")[0];
				nounOutput.put(curNounID, true);
				while (curNounID.contains(".")) {
					curNounID = curNounID.substring(0, curNounID.lastIndexOf("."));
					if(!nounOutput.containsKey(curNounID))
					nounOutput.put(curNounID, false);
				}
			}
		}
		for (String noun : nounLines.keySet()) {
			String id = nounLines.get(noun).split(" ")[0];
			if (nounOutput.containsKey(id)) {
				String[] line = nounLines.get(noun).split(" ");
				if (!nounOutput.get(id)) {
					System.out.print("-");
				}
				System.out.println(line[0] + " " + line[5]);
			}
		}
	}
}
