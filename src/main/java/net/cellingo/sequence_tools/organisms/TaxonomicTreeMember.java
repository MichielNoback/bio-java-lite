/**
 * 
 */
package net.cellingo.sequence_tools.organisms;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public interface TaxonomicTreeMember {
	public String getLatinName();
	public String getEnglishName();
	public TaxonomyRank getTaxonomicLevel();
	public TaxonomicGroup getParentGroup();
	public int getTaxonID();
	
}
