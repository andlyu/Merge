# Merge
Merging NLP with the Random-Text-Gen 

### Description
As I was working on [random-text-gen](https://github.com/andlyu/random-text-gen), I ran into the issue that hard coding a database for words would have too many human errors and would take too long. My goal with this project was to have NLP process throuhg texts to create a database that would work with my random-text-gen program. Used [Word Net's](https://wordnet.princeton.edu/) organisation to organise words with their iDs to create the database format. Then I used [Stanford's NLP](https://nlp.stanford.edu/) to process the Harry Potter Series and World Book Encyclopedias for their subject of each sentance and for the verb. When I added that data into my database, the DEMO shows what I got. The next step would be to find the objects, but that would increase inacuracy because many subjects and objects come in phrases, and only the nouns makes the sentance nonsencial. <br/>
This could be fun to play around with, but I think using Tensorflow would be more practical. 
	
### DEMO
Symplification>Symplification>Clause.java<br/>

	A spell reawakens.
	A smirk spreads.
	A mouth and a mouth bone.	
	The minister noses.
	A hurry changes.
	An enemy turns.
	A doge decides.
	A stomach and the stomach disappear.
	A none hurts.
	The smirk falters.
	A right and the day want.
	The troll and the troll notice.
	The horror stretches.
	The word, the word and the word reassure.
	An insult and the insult front.
	The school and a school knock.
	A reverse, a reverse and a reverse spell.
	A something and the something explode.
	A smirk spreads.
	The hurry changes.
	The tom looks.
	The people panices.
	A tune saves.
	The mirror drives.
	The stand pokes.
	A son amounts.
	A word expresses.
	The sound carries.
	A charm and the door appeal.
	The sleet meets.
	A hand and the hand twitch.
	A wand gives.
	A ministry, a ministry and the ministry punish.
	The latter involves.
	A head lies.
	A mouth and a mouth bone.
	A diary and the none tell.
	The stand pokes.
	The homework, the homework and the homework compose.
	A time leaves.
	A nose, a bridge and an ear lie.
	The intensity breaks.	
	A something and the mum stand.
	The death and a death bring.
	A cloak falls.
	The conference gives.
	The mirror drives.
	The eye sees.
	A chest explodes.
	A moment and a question wait.
	A need and a need arise.
	A banquet begins.
	The smirk falters.
	The latter and the latter involve.
	A one gets.
	A mind wanders.
	The number and a dead tarnish.
	
	
### Work Cited
I used Stanford's NLP to phrase structure trees, to extract nouns and verbs. (great school, wish I got in)<br/>
I used Princeton's Word Net 2.1 for a large database of organised words<br/>
I parsed through the JK Rowling's Harry Potter series as my sample text.

### instalation
This project is intended to run in Eclipse, but I encountered some challanges with instalation<br/>
Here are some possible errors are if the project doesn't build correctly....<br/>
	
	1) right click project -> Properties -> Java Build Path -> Libraries
	2) remove Java JDK
	3) step 1, import java JDK
	4) right click project -> build path
	

