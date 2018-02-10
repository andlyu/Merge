package parsingOutput;

import java.io.*;
import java.util.*;

public class FindConnections2 {
	public static final String VERB_FILE = "ParsedTexts/Big Verbs.txt";
	public static final String NOUN_FILE = "ParsedTexts/Big Nouns.txt";
	public static final String ENCYCLOPEDIA = "ParsedTexts/World-Book-Encyclopedia.txt";
	public static final String ALL_WORD_OUT = "ParsedTexts/AllWords.txt";

	public static void main(String[] args) throws IOException {
		mainMethodised();
	}

	/**
	 * Just a method to make help methodise the main method
	 */
	public static void mainMethodised() {
		Scanner input = null;
		BufferedWriter writer = null;// new BufferedWriter(new FileWriter(ALL_WORD_OUT));
		Scanner nounInput = null;// new Scanner(new FileReader(NOUN_FILE));
		HashMap<String, NounWithVerbs> nouns = new HashMap<>();
		HashSet<String> wholeNounCollecton = new HashSet<>();
		Scanner verbInput = null; // new Scanner(new FileReader(VERB_FILE));
		HashSet<String> wholeVerbCollecton = new HashSet<>();

		try {
			writer = new BufferedWriter(new FileWriter(ALL_WORD_OUT));
			nounInput = new Scanner(new FileReader(NOUN_FILE));
			verbInput = new Scanner(new FileReader(VERB_FILE));
			input = new Scanner(new FileReader(ENCYCLOPEDIA));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String line = "";
		while (verbInput.hasNext()) {
			wholeVerbCollecton.add(verbInput.nextLine().split(" ")[5]);
		}
		while (nounInput.hasNext()) {
			wholeNounCollecton.add(nounInput.nextLine().split(" ")[5]);
		}
		while (input.hasNext()) {
			line = input.nextLine();

			Queue<Phrase> queue = new LinkedList();
			Tree<Phrase> tree = new Tree();
			int iD = 0;
			Phrase root = new Phrase(line, iD++);
			queue.add(root);
			tree.addPhrase(null, root);
			tree = createTreeFromLine(tree, queue); // tree of phrases
			nouns = fillNounsWithVerbs(tree, nouns, wholeNounCollecton, wholeVerbCollecton);

		}
		for (String a : nouns.keySet()) {
			System.out.println(nouns.get(a));
		}
		System.out.println(nouns.size());
	}

	public static HashMap infoFromParsed() {
		Scanner input = null;
		Scanner verbInput = null;
		BufferedWriter writer = null; // new BufferedWriter(new FileWriter(ALL_WORD_OUT));
		Scanner nounInput = null; // new Scanner(new FileReader(NOUN_FILE));
		HashMap<String, NounWithVerbs> nouns = new HashMap<>();
		HashSet<String> wholeNounCollecton = new HashSet<>();

		try {
			verbInput = new Scanner(new FileReader(VERB_FILE));
			writer = new BufferedWriter(new FileWriter(ALL_WORD_OUT));
			nounInput = new Scanner(new FileReader(NOUN_FILE));
			input = new Scanner(new FileReader(ENCYCLOPEDIA));

		} catch (Exception e) {
			e.printStackTrace();
		}
		while (nounInput.hasNext()) {
			wholeNounCollecton.add(nounInput.nextLine().split(" ")[5]);
		}
		HashSet<String> wholeVerbCollecton = new HashSet<>();
		while (verbInput.hasNext()) {
			wholeVerbCollecton.add(verbInput.nextLine().split(" ")[5]);
		}
		String line = "";

		while (input.hasNext()) {
			line = input.nextLine();

			Queue<Phrase> queue = new LinkedList();
			Tree<Phrase> tree = new Tree();
			int iD = 0;
			Phrase root = new Phrase(line, iD++);
			queue.add(root);
			tree.addPhrase(null, root);
			tree = createTreeFromLine(tree, queue);
			nouns = fillNounsWithVerbs(tree, nouns, wholeNounCollecton, wholeVerbCollecton);
		}
		// for (String a : nouns.keySet()) {
		// System.out.println(nouns.get(a));
		// }
		return nouns;
	}

	/**
	 * @param tree
	 *            one Phrase node pointing to it's children phrases (initially
	 *            empty)
	 * @param queue
	 *            the root is in the phrase, and then subphrases are added to it (it
	 *            is the line)
	 * @return returns the filled tree
	 */
	private static Tree<Phrase> createTreeFromLine(Tree<Phrase> tree, Queue queue) {
		int iD = 1;
		while (queue.size() != 0) {// What is happening
			{
				Phrase parent = (Phrase) queue.poll();
				String line = parent.getPhrase();
				int stop = line.length();
				int start = 0;// start of a phrase (after the opening parenthesy)
				int index = start; // to travers through the characters
				int countParen = 0;
				while (line.substring(index).indexOf("(") >= 0)// while has another phrase
				{

					if (countParen == 0) {
						while (line.charAt(index) != '(') {
							index++;
						}
						start = index++;
					} // finds the first "(" and sets start to it
					countParen = 1;
					while (countParen > 0 && index < stop) {
						if (line.charAt(index) == '(')
							countParen++;
						else if (line.charAt(index) == ')')
							countParen--;
						index++;
					} // finds where the phrase inds (the ending ")")
					String phraseString = "";
					if (countParen == 0)
						phraseString = line.substring(start, index);// next Line will crash if index>stop;
					Phrase phrase = new Phrase(phraseString, iD++);// phrase to be added to the stack
					tree.addPhrase(parent, phrase);
					if (phrase.getPhrase().contains("("))
						queue.add(phrase);
					else
						start = index;

				}
			}
		}
		return tree;
	}

	/**
	 * @param tree
	 *            A tree of phrases- a top phrases with sub-nodes of child phrases
	 * @param nouns
	 *            - a HashMap of String of nouns, with an attached NounWithVerbs
	 * @param wholeNounCollecton
	 *            - Info from Big Nouns FILE
	 * @param wholeVerbCollecton
	 *            - Info from Big VErbs FILE
	 * @return adds the information gathered from the tree to Nouns, checking with
	 *         wholeNoun&VerbCollections
	 */
	private static HashMap<String, NounWithVerbs> fillNounsWithVerbs(Tree<Phrase> tree, // TODO second methode
			HashMap<String, NounWithVerbs> nouns, HashSet<String> wholeNounCollecton,
			HashSet<String> wholeVerbCollecton) {
		for (Node a : tree.getArrayList()) {// What does this do??
			if ("S".equals(((Phrase) a.getPhrase()).getTag()) && !containsChildS(a)) {
				Node noun = getNounChild(a);
				Node verb = getVerbChild(a);
				while (noun != null && getNounChild(noun) != null) {
					noun = getNounChild(noun);
				}
				while (verb != null && getVerbChild(verb) != null)
					verb = getVerbChild(verb);
				if (noun != null && verb != null)
					if (!noun.toString().contains("(") && !verb.toString().contains("(")) {
						String wordNoun = noun.getPhrase().toString().toLowerCase();
						String wordVerb = verb.getPhrase().toString().toLowerCase();
						// System.out.print("Noun: " + noun.getPhrase());
						// System.out.println(" Verb: " + verb.getPhrase());
						if (wholeNounCollecton.contains(wordNoun) && wholeVerbCollecton.contains(wordVerb)) {
							if (nouns.get(wordNoun) != null) {
								NounWithVerbs curNoun = nouns.get(wordNoun);
								curNoun.addVerb(wordVerb);
								nouns.put(wordNoun, curNoun);
							} else {
								NounWithVerbs curNoun = new NounWithVerbs(wordNoun);
								curNoun.addVerb(wordVerb);
								nouns.put(wordNoun, curNoun);
							}
						}
					}
			}

		}
		return nouns;
	}

	// Pre: phrase is a Node, that is a sentance and should contain a noun
	// Post: returns the noun if there is only one. Otherwise returns null;
	public static Node getNounChild(Node phrase) {
		Node out = null;
		for (Node a : ((LinkedList<Node>) phrase.getChildren()))
			if (((Phrase) a.getPhrase()).getTag().charAt(0) == 'N')
				if (out == null)
					out = a;
				else
					out = null;
		return out;
	}

	// Pre: phrase is a Node, that is a sentance and should contain a verb
	// Post: returns the verb if there is only one. Otherwise returns null;
	public static Node getVerbChild(Node phrase) {
		Node out = null;
		for (Node a : ((LinkedList<Node>) phrase.getChildren()))
			if (((Phrase) a.getPhrase()).getTag().charAt(0) == 'V')
				if (out == null)
					out = a;
				else
					out = null;
		return out;
	}

	public static boolean containsChildS(Node phrase) {
		for (Node a : ((LinkedList<Node>) phrase.getChildren()))
			if (((Phrase) a.getPhrase()).getTag().charAt(0) == 'S')
				return true;
		return false;
	}

}