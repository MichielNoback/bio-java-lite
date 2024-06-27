/**
 * 
 */
package net.cellingo.sequence_tools.organisms;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class Organism implements TaxonomicTreeMember{
	private String latinSpeciesName;
	private String latinGenusName;
	private String fullLatinName;
	private String engName;
	private String lineage;
	private TaxonomyRank taxLevel;
	private TaxonomicGroup parentGroup;
	private int taxID;
	
	public Organism(){
		taxLevel = TaxonomyRank.SPECIES;
	}
	
	public Organism(String fullLatinName){
		taxLevel = TaxonomyRank.SPECIES;
	}
	
	/**
	 * @return the latinName
	 */
	public String getFullLatinName() {
		return fullLatinName;
	}
	/**
	 * @param latinName the latinName to set
	 */
	public void setFullLatinName(String latinName) {
		this.latinSpeciesName = latinName;
	}
	/**
	 * @return the latinName
	 */
	public String getLatinSpeciesName() {
		return latinSpeciesName;
	}
	/**
	 * @param latinName the latinName to set
	 */
	public void setLatinSpeciesName(String latinName) {
		this.latinSpeciesName = latinName;
	}

	/**
	 * @return the latinGenusName
	 */
	public String getLatinGenusName() {
		return latinGenusName;
	}

	/**
	 * @param latinGenusName the latinGenusName to set
	 */
	public void setLatinGenusName(String latinGenusName) {
		this.latinGenusName = latinGenusName;
	}
	/**
	 * @return the engName
	 */
	public String getEnglishName() {
		return engName;
	}
	/**
	 * @param engName the engName to set
	 */
	public void setEnglishName(String engName) {
		this.engName = engName;
	}
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
	 * @return the taxID
	 */
	public int getTaxonID() {
		return taxID;
	}
	/**
	 * @param taxID the taxID to set
	 */
	public void setTaxonID(int taxID) {
		this.taxID = taxID;
	}

	public String getLatinName() {
		return getLatinSpeciesName();
	}

	public TaxonomyRank getTaxonomicLevel() {
		return taxLevel;
	}

	/**
	 * @return the lineage as a string
	 */
	public String getLineage() {
		return lineage;
	}

	/**
	 * @param lineage the lineage to set as string representation
	 */
	public void setLineage(String lineage) {
		this.lineage = lineage;
	}

	
	

}
