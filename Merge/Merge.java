import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Scanner;

import parsingOutput.FindConnections2;
import parsingOutput.NounWithVerbs;

public class Merge {
	public static final String NOUN_FILE = "ParsedTexts/Big Nouns.txt";
	public static final String VERB_FILE = "ParsedTexts/Big Verbs.txt";
	public static final String NOUN_OUT_FILE = "Text Files/Encyclopedia Nouns.txt";
	public static final String VERB_OUT_FILE = "Text Files/Encyclopedia Verbs.txt";
	public static final String HARRY_POTTER = "ParsedTexts/texts/All JK Rowling";

	/**
	 * @param args
	 *            just the main runner class
	 */
	public static void main(String[] args) {
		HashMap<String, NounWithVerbs> connectionMap = FindConnections2.infoFromParsed(HARRY_POTTER, false);

		Scanner nounInput = null; // scanners
		Scanner verbInput = null;
		PrintWriter nounWriter = null;
		PrintWriter verbWriter = null;
		LinkedHashMap<String, String> nounLines = new LinkedHashMap<>(); // arrayList with all Nouns
		LinkedHashMap<String, String> verbLines = new LinkedHashMap<>();
		LinkedList<String> allNounLines = new LinkedList<>();
		LinkedList<String> allVerbLines = new LinkedList<>();
		HashMap<String, Boolean> nounOutput = new HashMap<>();// Noun is a bit more symple but will change later
		HashMap<String, String> verbOutput = new HashMap<>();

		try {// Initiates scanners
			nounInput = new Scanner(new FileReader(NOUN_FILE));
			verbInput = new Scanner(new FileReader(VERB_FILE));
			verbWriter = new PrintWriter(new FileWriter(VERB_OUT_FILE));
			nounWriter = new PrintWriter(new FileWriter(NOUN_OUT_FILE));
		} catch (Exception  e) {
			e.printStackTrace();
		}

		while (nounInput.hasNext()) {//// fills Arrays with all nouns and verbs from file
			String in = nounInput.nextLine();
			if (!nounLines.containsKey(in.split(" ")[5]))
				nounLines.put(in.split(" ")[5], in);
			allNounLines.add(in);
		}
		while (verbInput.hasNext()) {
			String in = verbInput.nextLine();
			if (!verbLines.containsKey(in.split(" ")[5]))
				verbLines.put(in.split(" ")[5], in);
			allVerbLines.add(in);

		}

		for (String a : connectionMap.keySet()) {// add all nouns and their predisesors
			/*
			 * go through the nousn add the noun&predisecor to the output Nouns run through
			 * nouns, if the current is outNouns print it out: !!!
			 */
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
		
		
		//NOW

			for (Object noun : allNounLines.toArray()) {
				String id = ((String) noun).split(" ")[0];
				if (nounOutput.containsKey(id)) {
					String[] line = ((String) noun).split(" ");
					if (!nounOutput.get(id)) {
						nounWriter.write("-");
					}
					nounWriter.write(line[0] + " " + line[5] + "\n");
				}
			}
		
		//NOW
		for (String a : connectionMap.keySet()) {// a is a noun in a connection
			// add all verbs and their predisesors
			/*
			 * go through the connections add the verb&predisecor to the verbOutput
			 *
			 */// TODO what if some stuff already has a negative: double negative
			Object[] verbs = connectionMap.get(a).getVerbs().toArray(); // verbs coorelating with the noun
			for (Object verb : verbs) {
				if (verbLines.containsKey(verb)) {
					String[] verbLineFrags = verbLines.get(verb).split(" "); // verbLineFragments
					String curVerbID = verbLineFrags[0];
					String nounID = nounLines.get(a).split(" ")[0];
					String wholeVerbDef = verbLineFrags[0] + " " + verbLineFrags[5] + " " + nounID;
					if (!verbOutput.containsKey(curVerbID) || verbOutput.get(curVerbID) == null)
						verbOutput.put(curVerbID, wholeVerbDef);
					else
						verbOutput.put(curVerbID, verbOutput.get(curVerbID) + "," + nounID);
					while (curVerbID.contains(".")) {
						curVerbID = curVerbID.substring(0, curVerbID.lastIndexOf("."));
						if (!nounOutput.containsKey(curVerbID))
							verbOutput.put(curVerbID, null);
					}
				}
			}
		}
		for (Object verb : allVerbLines.toArray()) {// verb is a long String
			String id = ((String) verb).split(" ")[0];
			if (verbOutput.containsKey(id)) {
				if (verbOutput.get(id) == null) {
					String[] verbFragments = ((String) verb).split(" ");
					verbWriter.write("-" + verbFragments[0] + " " + verbFragments[5] + "\n");
				} else
					verbWriter.write(verbOutput.get(id) + "\n");
			}
		}
		verbWriter.close();
		nounWriter.close();
		System.out.println("Success");
	}
}
