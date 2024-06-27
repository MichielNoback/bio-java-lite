/**
 * 
 */
package net.cellingo.sequence_tools.annotation;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class LiteratureReference {
	private String authors;
	private int pubMedNumber;
	private String title;
	private String journal;

	public void setAuthors(String authors) {
		this.authors = authors;
	}
	/**
	 * @return the authors
	 */
	public String getAuthors() {
		return authors;
	}

	public void setPubMedNumber(int pubMedNumber) {
		this.pubMedNumber = pubMedNumber;
	}

	public void setTitle(String titleString) {
		this.title = titleString;
	}


	/**
	 * @return the pubMedNumber
	 */
	public int getPubMedNumber() {
		return pubMedNumber;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	public void setJournal(String journalString) {
		this.journal = journalString;
	}

	/**
	 * @return the journal
	 */
	public String getJournal() {
		return journal;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(" LiteratureReference: [ ");
		sb.append("PubMed=");
		sb.append(pubMedNumber);
		sb.append(": ");
		if( authors.length() > 30 ){
			String[] s = authors.split(",");
			sb.append(s[0] + " et al., ");
		}
		else sb.append(authors);
		sb.append(". ");
		sb.delete(sb.length()-2, sb.length());
		sb.append(" ");
		sb.append(title);
		sb.append(", ");
		sb.append(journal);
		sb.append(" ]");
		return sb.toString();
	}
	/*  AUTHORS   Torpey,L.E., Gibbs,P.E.,
            Nelson,J. and Lawrence,C.W.
  TITLE     Cloning and sequence of REV7, a gene whose function is required for
            DNA damage-induced mutagenesis in Saccharomyces cerevisiae
  JOURNAL   Yeast 10 (11), 1503-1509 (1994)
  PUBMED    7871890
*/
}
