package net.cellingo.sequence_tools.organisms;


/**
 * enum to represent the domains of life
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public enum LifeDomains {
	BACTERIA("bacteria"),
	ARCHAEA("archaea"),
	EUKARYA("eukarya"),
	UNKNOWN("unknown");
	
	String domainName;
	
	private LifeDomains(String domain){
		this.domainName = domain;
	}
	
	public String getDomain(){
		return domainName;
	}
	
}
