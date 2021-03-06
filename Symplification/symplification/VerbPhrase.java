package symplification;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;
import java.util.*;
import java.io.*;

// Mistake : only one verb
public class VerbPhrase {

	//private static final String VERB_FILE = "random-text-gen-master/src/main/java/VerbSymple.txt";
	private static final String VERB_FILE = "Text Files/Encyclopedia Verbs.txt";// "random-text-gen-master/src/main/java/VerbSymple.txt";

	private String classVerb;// long, with all of the information
	private String phrase;

	public void setClassVerb(String a) {
		classVerb = a;
	}

	public void setPhrase(String a) {
		phrase = a;
	}

	public String getClassVerb() {
		return classVerb;
	}

	public String getPhrase() {
		return phrase;
	}

	public String toString() {
		return realiser.realiseSentence(nlgFactory.createClause(null, phrase));
	}

	public boolean isTransitive() { // if Trasitive returns true
		if (classVerb.split(" ")[2].split("/")[0].contains("-"))
			return true;
		return false;
	}

	// part NLG
	private static Lexicon lexicon = Lexicon.getDefaultLexicon();
	private static NLGFactory nlgFactory = new NLGFactory(lexicon);
	private static Realiser realiser = new Realiser(lexicon);

	public VerbPhrase() {
		try {
			phrase = oneVerb();
		} catch ( Exception e) {
			e.printStackTrace();
		}
	}

	// pre:; a = whole verb
	public VerbPhrase(String a) {
		classVerb = a;
		phrase = a.split(" ")[1];

	}

	public static void main(String[] args){
		for (int i = 0; i < 100; i++) {
			VerbPhrase a = new VerbPhrase();
			SPhraseSpec p = nlgFactory.createClause(null, a.getPhrase());
			System.out.println(i + "   " + realiser.realiseSentence(p));
		}
	}

	private String oneVerb()   {
		//classVerb = ranVerb(new String[] { "1", "2" }, true);
		classVerb = ranVerb(new String[] { "0" }, true);

		return classVerb.split(" ")[1];
	}

	public static String[] getGroup(String a) {
		if (a.split(" ").length < 4)
			return null;
		return a.split(" ")[3].split(",");
	}

	// pre: noun is a whole noun
	// post: returns up-groups associated with the noun (groups which the noun must also
	// be)
	//?? does it count itself as well?
	public String[] upGroups(){
		String n = classVerb;
		String iD = n.split(" ")[0];
		String[] iDs = iD.split("\\.");// makes the array of the total number of IDs, not the right iDs
		int count = 0;
		iDs[count++] = iD;
		while (iD.contains(".")) {
			iD = iD.substring(0, iD.lastIndexOf("."));
			iDs[count++] = iD;
		}

		ArrayList<String> all = new ArrayList();// IMPROVE: all and some are extra
		Scanner input = null;
		try {
			input = new Scanner(new FileReader(VERB_FILE));
		} catch (FileNotFoundException e1) {
 			e1.printStackTrace();
		}
		{
			String a = null; // to store input
			String[] some = null; // some of the adjective IDs
			count = iDs.length - 1;
			while (input.hasNextLine() && count > -1) {
				a = input.nextLine();
				if (a.startsWith(iDs[count] + " ") || a.startsWith("-" + iDs[count] + " ")) {
					try {
						if (a.split(" ").length > 3 && a.split(" ")[3].charAt(0) != 'P')// P stands for pass
							some = a.split(" ")[3].split(",");
						if (some != null)
							for (int i = 0; i < some.length; i++)
								all.add(some[i]);
						some = null;
						count--;
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("WAZA NounPhrase.java");
					}

				}
			}
			if (all == null)
				return null;
		}
		return all.toArray(new String[all.size()]);

	}

	// Pre: a is a whole noun which a verb can describe doing an action
	// Post: randomly chooses a verb which that thing can be doing
	public void verbOnNoun(String a)  {
		Scanner input = null;
		try {
			input = new Scanner(new FileReader(VERB_FILE));
		} catch (FileNotFoundException e) {
 			e.printStackTrace();
		}
		ArrayList<String> verbs = new ArrayList();
		while (input.hasNext())
			verbs.add(input.nextLine());
		ArrayList<String> verb = new ArrayList();
		for (int i = 0; i < verbs.size(); i++) {
			if (verbs.get(i).charAt(0) != '-') {
				String nounID = a.split(" ")[0];
				String[] verbIDs = verbs.get(i).split(" ")[2].split("/")[0].split("-")[0].split(",");
				for (int j = 0; j < verbIDs.length; j++)
					if (nounID.length() >= verbIDs[j].length())
						if (verbIDs[j].equals(nounID.substring(0, verbIDs[j].length())))
							verb.add(verbs.get(i));
			}
		}
		classVerb = verb.get((int) (Math.random() * verb.size()));
		phrase = classVerb.split(" ")[1];
	}

	/**
	 * @return true if the verb (or higher-class-verbs) has information about what prepositions it can be used
	 */
	public boolean canHavePreposition() {
		if(classVerb.contains("/"))
			return true;
		for(String a: upGroups()) {
			if(a.contains("/"))
				return true;
		}
		return false;
	}
	
	// pre:: a are the id's of the beginning of ID's of verbs,
	// if whole then returns the whole identity of teh verb
	// retruns an verb out of an array of array a
	/**
	 * @param a are the id's of the beginning of ID's of verbs,
	 * @param whole if whole then returns the whole identity of teh verb
	 * @return an verb out of an array of array a
	 */
	public static String ranVerb(String[] a, boolean whole)   {
		Scanner input = null;
		try {
			input = new Scanner(new FileReader(VERB_FILE));
		} catch (FileNotFoundException e) {
 			e.printStackTrace();
		}
		ArrayList<String> verbs = new ArrayList();
		while (input.hasNext())
			verbs.add(input.nextLine());
		ArrayList<String> verb = new ArrayList();
		String g;
		for (int i = 0; i < verbs.size(); i++) {//////// Goes through verbs
			if (wordWorks(verbs.get(i), a))
				verb.add(verbs.get(i));
		} ///////////////// ends the verbs
		if (!whole)
			return verb.get((int) (Math.random() * verb.size())).split(" ")[1];
		return verb.get((int) (Math.random() * verb.size()));
	}

	// Pre:: a are all of the posible ID's of the Word, word is the word bieng
	// tested
	// returs true is a has the bigining of word
	private static boolean wordWorks(String word, String[] a) {
		for (int i = 0; i < a.length; i++) {
			if (!(word.length() < a[i].length()) && a[i].equals(word.substring(0, a[i].length())))
				return true;
		}
		return false;
	}

}