/**
 * 
 */
package net.cellingo.sequence_tools.sequence_matching;

import net.cellingo.sequence_tools.alphabets.SequenceAlphabet;
import net.cellingo.sequence_tools.annotation.NucleicAcidSequenceElement;
import net.cellingo.sequence_tools.annotation.SequenceCoordinates;
import net.cellingo.sequence_tools.annotation.SequenceElement;
import net.cellingo.sequence_tools.io.SequenceErrorMessage;
import net.cellingo.sequence_tools.sequences.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class takes care of all simple sequence pattern finding
 * operations based on the Java regex classes Pattern and Matcher.
 * In the constructor, a search sequence and query sequence (as regexp) 
 * are entered. Subsequently all ambiguous characters 
 * are translated in a set of possible characters and thus, a set 
 * of possible query sequences.
 * A match-start-position-ordered ArrayList of matches is returned after invoking the 
 * getMatches() method
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class SequencePatternFinder {
	private Sequence sequence;
	private String primaryQuery;
	private String primaryQueryComplement;
	private SequenceErrorMessage returnMessage;
	private int matchNumber;
	private ArrayList<SequenceElement> searchMatches;
	private boolean found;
	private boolean alsoComplementary;
	private boolean currentQueryIsForward;
	
	/**
	 * the default constructor takes in a Sequence to search against
	 * and the query sequence, with all possible ambiguity codes
	 * @param sequence
	 * @param query
	 */
	public SequencePatternFinder(Sequence sequence, String query, boolean alsoComplementary) {
		this.sequence = sequence;
		this.primaryQuery = query.toUpperCase();
		this.alsoComplementary = alsoComplementary;
		this.searchMatches = new ArrayList<SequenceElement>(); 
		//matches = new HashMap<String, ArrayList<Integer>>();
		found = false;
		primaryQueryComplement = null;
		//findPatterns();
	}
	
	public void findPatterns(){
		if(primaryQuery == null || primaryQuery.length()==0){
			returnMessage = SequenceErrorMessage.NO_SEQUENCE;
		}
		boolean validQuery = true;
		boolean isCoreAlphabet = true;
		SequenceAlphabet alphabet = sequence.getAlphabet();
		for( char character : primaryQuery.toCharArray() ){
			if(! alphabet.isValidAlphabetCharacter(character) ){
				validQuery = false;
				break;
			}
			if(! alphabet.isCoreCharacter(character) ){
				isCoreAlphabet = false;
				break;
			}
		}
		if(validQuery){	/*query consists of legal alphabet characters*/
			/*if also complement search, make a reverse complement copy of the query*/
			if(alsoComplementary){
				Sequence primaryQueryBiolSeq;
				try {
					primaryQueryBiolSeq = SequenceFactory.createSequence(primaryQuery);
					if(primaryQueryBiolSeq instanceof DnaSequence){
						DnaSequence primaryQueryDna = (DnaSequence) primaryQueryBiolSeq;
						primaryQueryDna.reverseComplement();
						primaryQueryComplement = primaryQueryDna.getSequenceString();
					}
					else if(primaryQueryBiolSeq instanceof RnaSequence){
						RnaSequence primaryQueryRna = (RnaSequence) primaryQueryBiolSeq;
						primaryQueryRna.reverseComplement();
						primaryQueryComplement = primaryQueryRna.getSequenceString();
					}
				} catch (SequenceCreationException e) {
					e.printStackTrace();
				}
			}
			
			/* check if it is a "core alphabet" sequence*/
			if(isCoreAlphabet){
				currentQueryIsForward = true;
				doSingleQuery(primaryQuery);
				//also complementary?
				if(alsoComplementary && primaryQueryComplement != null){
					currentQueryIsForward = false;
					doSingleQuery(primaryQueryComplement);
				}
		        if(!found){
		        	returnMessage = SequenceErrorMessage.NO_MATCH_FOUND;
		        }
		        else{
		        	returnMessage = SequenceErrorMessage.MATCHES_FOUND;
		        }
			}// end if(sequence.checkSequenceIsCoreAlphabet(primaryQuery))
			
			/*query consists of legal alphabet characters with ambiguities; 
			 * has to be split up in all possible matching queries.*/
			else{
				//ArrayList<StringBuilder> queryList = new ArrayList<StringBuilder>();
				currentQueryIsForward = true;
				ArrayList<StringBuilder> queryList = buildQueryList(primaryQuery);
				/*
				 * now all subqueries have been created and will be matched to the 
				 * search sequence one by one
				 */
				for(StringBuilder query: queryList){
					doSingleQuery(query.toString());
				}
				
				//also complementary?
				if(alsoComplementary && primaryQueryComplement != null){
					currentQueryIsForward = false;
					ArrayList<StringBuilder> queryListRev = buildQueryList(primaryQueryComplement);
//					buildQueryList(primaryQueryComplement);
					for(StringBuilder query: queryListRev){
						doSingleQuery(query.toString());
//						System.out.println("query: " + query.toString());
					}
				}
				
		        if(!found){
		        	returnMessage = SequenceErrorMessage.NO_MATCH_FOUND;
		        }
		        else{
		        	returnMessage = SequenceErrorMessage.MATCHES_FOUND;
		        }
			}// end if(sequence.checkSequenceIsCoreAlphabet(primaryQuery))
		}//end query consists of legal alphabet characters
		else{
			//query does not consist of legal alphabet characters
			returnMessage = SequenceErrorMessage.ILLEGAL_CHARACTER;
		}
	}//end findPatterns()
	
	/**
	 * do one query with a search string
	 * @param query
	 */
	private void doSingleQuery(String query){
		//do the exact pattern matching
        Pattern pattern = Pattern.compile(query);
        Matcher matcher = pattern.matcher(sequence.getSequenceString());
        while (matcher.find()) {
            matchNumber++;
//            SequenceGraphicsElement seqHighlight = new SequenceGraphicsElement(matcher.start()+1, query.length(), null, currentQueryIsForward);
            SequenceElement seqElement;

            if(sequence.getSequenceType() == SequenceType.DNA || sequence.getSequenceType() == SequenceType.RNA){
            	seqElement = new NucleicAcidSequenceElement(SequenceElement.getNextAutogeneratedId());
            	//((NucleicAcidSequenceElement)seqElement).setComplement( !currentQueryIsForward );
            }
            else{
                seqElement = new SequenceElement(SequenceElement.getNextAutogeneratedId());
            }
			SequenceCoordinates oc = new SequenceCoordinates( ( matcher.start()+1 ), ((matcher.start()+1) + query.length()), (!currentQueryIsForward), true );
			seqElement.addCoordinates(oc);
            
/*            seqElement.setParentStart( matcher.start()+1 );
            seqElement.setParentStop((matcher.start()+1) + query.length());
*/            seqElement.setParentSequence(sequence);
            searchMatches.add(seqElement);
            found = true;
        }
	}
	
	/**
	 * build a querylist from an ambiguous string
	 * @param ambiguousQuery
	 */
	private ArrayList<StringBuilder> buildQueryList(String ambiguousQuery){
		ArrayList<StringBuilder> queryList = new ArrayList<StringBuilder>();
		StringBuilder seedBuilder = new StringBuilder();
		queryList.add(seedBuilder);
		//the list of queries
		//make character array of primary query
		char[] queryArray = ambiguousQuery.toCharArray();
		//loop the characters and check which one is 
		for(char i: queryArray){
			if(sequence.getAlphabet().isCoreCharacter(i)){
			   /* character is core alphabet; append it to all existing queries
				* loop the queryList and append this character to all of them
				*/
				for(StringBuilder queryBuilder: queryList){
					queryBuilder.append(i);
				}
			}
			else{
				/* character is not core alphabet; get all matches 
				 * to this character and create new sequences for each 
				 * ambiguous match
				 */
				HashSet<Character> ambiguousMatchesSet = sequence.getAlphabet().getAmbiguousMatches(i);
				//convert to ArrayList for easy looping
				ArrayList<Character> ambiguousMatches = new ArrayList<Character>(ambiguousMatchesSet);
				
				/* for each character after the first one in the list,
				 * create a new sequence in the queryList, and append the
				 * character possibilities to the old and the new sequences
				 */
				int numberOfAmbiguousMatches = ambiguousMatches.size();
				//place to hold the new queries temporarily
				ArrayList<StringBuilder> newQueries = new ArrayList<StringBuilder>();
				//loop the ambiguous character
				for(int matchNumber=0; matchNumber<numberOfAmbiguousMatches; matchNumber++){
					//loop the existing queries
					for(StringBuilder existingQuery: queryList){
						if(matchNumber == numberOfAmbiguousMatches-1){//last ambiguous character appended to existing queries
							existingQuery.append(ambiguousMatches.get(matchNumber));
						}
						else{//not last ambiguous character; make new StringBuilder sequence and store in temp array
							StringBuilder newQuery = new StringBuilder(existingQuery);
							newQuery.append(ambiguousMatches.get(matchNumber));
							newQueries.add(newQuery);
						}
					}
					//query.append(ambiguousMatches.get(matchNumber));
				}
				queryList.addAll(newQueries);
			}
		}
		return queryList;
	}
	/**
	 * get the return error message of this analysis
	 * @return returnMessage
	 */
	public SequenceErrorMessage getReturnMessage() {
		return returnMessage;
	}
	/**
	 * get the number of queries that the original (primary) query sequence was 
	 * split up into.
	 * @return queryNumber
	 */ 
	public int getQueryNumber() {
		return searchMatches.size();
	}
	/**
	 * get the number of matches found to the query
	 * @return matchNumber
	 */
	public int getMatchNumber() {
		return matchNumber;
	}
	/**
	 * Get an ArrayList of match coordinates in the searched sequence.
	 * In the case of duplicate, ambiguous matches, these are not reported twice
	 * but collapsed to one position
	 * To get sub/doughter-query-specific lists, use getMatchCollection()
	 */
	public ArrayList<SequenceElement> getMatches(){
		//return as sorted list
		Collections.sort(searchMatches);
		return searchMatches;
	}
}
