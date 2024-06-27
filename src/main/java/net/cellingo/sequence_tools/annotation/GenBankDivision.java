package net.cellingo.sequence_tools.annotation;

import java.util.HashMap;
/**
 * 
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public enum GenBankDivision {
	UNK("UNK: unknown division"),
	PRI("PRI: primate sequences"),
	ROD("ROD: rodent sequences"),
	MAM("MAM: other mammalian sequences"),
	VRT("VRT: other vertebrate sequences"),
	INV("INV: invertebrate sequences"),
	PLN("PLN: plant, fungal, and algal sequences"),
	BCT("BCT: bacterial sequences"),
	VRL("VIR: viral sequences"),
	PHG("PHG: bacteriophage sequences"),
	SYN("SYN: synthetic and chimeric sequences"),
	UNA("UNA: unannotated sequences"),
	EST("EST: EST sequences (expressed sequence tags)"),
	PAT("PAT: patent sequences"),
	STS("STS: STS sequences (sequence tagged sites)"),
	GSS("GSS: GSS sequences (genome survey sequences)"),
	HTG("HTG: HTGS sequences (high throughput genomic sequencing data)"),
	HTC("HTC: HTC sequences (high throughput cDNA sequencing data)"),
	ENV("ENV: environmental samples");

	private String type;
	private static HashMap<String, GenBankDivision> genBankDivisionsMap;
	private static HashMap<Integer, GenBankDivision> genBankDivisionIds;
	
	static{
		genBankDivisionsMap = new HashMap<String, GenBankDivision>();
		genBankDivisionsMap.put( "UNK", GenBankDivision.UNK );
		genBankDivisionsMap.put( "PRI", GenBankDivision.PRI );
		genBankDivisionsMap.put( "ROD", GenBankDivision.ROD );
		genBankDivisionsMap.put( "MAM", GenBankDivision.MAM );
		genBankDivisionsMap.put( "VRT", GenBankDivision.VRT );
		genBankDivisionsMap.put( "INV", GenBankDivision.INV );
		genBankDivisionsMap.put( "PLN", GenBankDivision.PLN );
		genBankDivisionsMap.put( "BCT", GenBankDivision.BCT );
		genBankDivisionsMap.put( "VRL", GenBankDivision.VRL );
		genBankDivisionsMap.put( "PHG", GenBankDivision.PHG );
		genBankDivisionsMap.put( "SYN", GenBankDivision.SYN );
		genBankDivisionsMap.put( "UNA", GenBankDivision.UNA );
		genBankDivisionsMap.put( "EST", GenBankDivision.EST );
		genBankDivisionsMap.put( "PAT", GenBankDivision.PAT );
		genBankDivisionsMap.put( "STS", GenBankDivision.STS );
		genBankDivisionsMap.put( "GSS", GenBankDivision.GSS );
		genBankDivisionsMap.put( "HTG", GenBankDivision.HTG );
		genBankDivisionsMap.put( "HTC", GenBankDivision.HTC );
		genBankDivisionsMap.put( "ENV", GenBankDivision.ENV );
		
		genBankDivisionIds = new HashMap<Integer, GenBankDivision>(); 
		genBankDivisionIds.put( 5, GenBankDivision.PRI );
		genBankDivisionIds.put( 6, GenBankDivision.ROD );
		genBankDivisionIds.put( 2, GenBankDivision.MAM );
		genBankDivisionIds.put( 10, GenBankDivision.VRT );
		genBankDivisionIds.put( 1, GenBankDivision.INV );
		genBankDivisionIds.put( 4, GenBankDivision.PLN );
		genBankDivisionIds.put( 0, GenBankDivision.BCT );
		genBankDivisionIds.put( 9, GenBankDivision.VRL );
		genBankDivisionIds.put( 3, GenBankDivision.PHG );
		genBankDivisionIds.put( 7, GenBankDivision.SYN );
		genBankDivisionIds.put( 8, GenBankDivision.UNA );
		genBankDivisionIds.put( 11, GenBankDivision.ENV );
	}
	
	private GenBankDivision(String type){
		this.type = type;
	}
	
	public String toString(){
		return type;
	}
	
	/**
	 * get the type based on the abbreviation; if abbreviation is unknown,
	 * the type "GenBankDivision.UNK" (unknown) is returned
	 * @param abbreviation
	 * @return genBankDivision
	 */
	public static GenBankDivision getType( String abbreviation ){
		if( genBankDivisionsMap.containsKey(abbreviation) ){
			return genBankDivisionsMap.get(abbreviation);
		}
		else{
			return GenBankDivision.UNK; 
		}
	}
	
	/**
	 * get the type based on the TaxDB ID; if ID is unknown,
	 * the type "GenBankDivision.UNK" (unknown) is returned
	 * @param taxDB_ID
	 * @return genBankDivision
	 */
	public static GenBankDivision getType(int taxDB_ID){
		if( genBankDivisionIds.containsKey(taxDB_ID) ){
			return genBankDivisionIds.get(taxDB_ID);
		}
		else{
			return GenBankDivision.UNK; 
		}
	}
	
}
