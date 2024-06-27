/**
 * 
 */
package net.cellingo.sequence_tools.organisms;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class TaxonomicGroup implements TaxonomicTreeMember{
	private TaxonomicGroup parentGroup;
	private TaxonomyRank level;
	private String latinName;
	private String englishName;
	private int taxonID;
	/**
	 * @return the parentGroup
	 */
	public TaxonomicGroup getParentGroup() {
		return parentGroup;
	}
	/**
	 * @param parentGroup the parentGroup to set
	 */
	public void setParentGroup(TaxonomicGroup parentGroup) {
		this.parentGroup = parentGroup;
	}
	/**
	 * @return the latinName
	 */
	public String getLatinName() {
		return latinName;
	}
	/**
	 * @param latinName the latinName to set
	 */
	public void setLatinName(String latinName) {
		this.latinName = latinName;
	}
	/**
	 * @return the englishName
	 */
	public String getEnglishName() {
		return englishName;
	}
	/**
	 * @param englishName the englishName to set
	 */
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	/**
	 * @param level the level to set
	 */
	public void setTaxonomicLevel(TaxonomyRank level) {
		this.level = level;
	}
	
	public TaxonomyRank getTaxonomicLevel() {
		return level;
	}
	/**
	 * @return the taxonID
	 */
	public int getTaxonID() {
		return taxonID;
	}
	/**
	 * @param taxonID the taxonID to set
	 */
	public void setTaxonID(int taxonID) {
		this.taxonID = taxonID;
	}
	
	
}
