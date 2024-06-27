package net.cellingo.sequence_tools.gene_analysis;
import java.util.HashMap;

/**
 * Class to hold the codon translation table
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class CodonTable {
	public static final int CODON_TABLE_STANDARD = 1;
	public static final int CODON_TABLE_GENETIC_ALGORITHM_PROTEIN = 24;
	public static final int CODON_TABLE_GENETIC_ALGORITHM_STRUCTURES = 25;
	public static final int CODON_TABLE_GENETIC_ALGORITHM_DNA = 26;
	
	//codon table number
	private int codonTableNumber;
	//codon table name as string
	private String codonTableString;
	//String array to hold all codon table names
	private String[] codonTableStringArray;
	//int array of all current codon tables
	private int[] existingCodonTables = {1,2,3,4,5,6,9,10,11,12,13,14,15,16,21,22,23,24,25,26};
	private HashMap<String, String> codons = new HashMap<String, String>();
	private HashMap<String, String> startCodons = new HashMap<String, String>();
	private int codonSize;
	/**
	 *	no-arg constructor: codon-table == 1
	 */
	public CodonTable(){
		codonTableNumber = CODON_TABLE_STANDARD;
		initialise();
	}
	/**
	 * One-arg constructor indicating the codon table to be used 
	 */
	public CodonTable(int codonTableNo){
		codonTableNumber = codonTableNo;
		initialise();
	}
	public CodonTable(HashMap<String, String> codonTable){
		this.codons = codonTable;
	}
	/**
	 * Method returns the current codon table number
	 */
	public int getCodonTableNumber(){
		return codonTableNumber;
	}
	/**
	 * This method returns a String that holds the current codon table number and name
	 */
	public String getCodonTableString(int codonTableNo){
		return codonTableString;
	}
	/**
	 * This method returns the translation of a codon
	 */
	public String getCodonTranslation(String codon){
		//System.out.println( codon );
		if(codons.containsKey(codon)){
			return codons.get(codon);
		}
		return "X";
	}
	/**
	 * This method checks whether the supplied codon can be used as alternative initiation codon
	 */
	public boolean checkInitiationCodon(String codon){
		boolean isInitiationCodon = false;
		if(startCodons.containsKey(codon)){
			isInitiationCodon = true;
		}
		return isInitiationCodon;
	}
	/**
	 * This method returns a String array containing all current codon tables 
	 */
	public String[] getExistingCodonTablesList(){
		//int[] existingCodonTables = {1,2,3,4,5,6,9,10,11,12,13,14,15,16,21,22,23,24,25,26};
		String[] tablesList = new String[existingCodonTables.length];
		for(int index=0; index<existingCodonTables.length; index++){
			int codonTableNumber = existingCodonTables[index];
			String codonTableString = codonTableStringArray[codonTableNumber];
			tablesList[index] = codonTableString;
			//System.out.println("codon table: " + codonTableString);
		}
		return tablesList;
	}
	
	public String[] getCodonTableList(){
		return codonTableStringArray;
	}
	public HashMap<String, String> getCodonTable(){
		return codons;
	}
	public void setCodonTranslation(String codon, String translation){
		codons.put(codon, translation);
	}
	public int getCodonSize(){
		return codonSize;
	}
	
	/**
	 * Initialisation procedures are grouped here
	 */
	private void initialise(){
		createCodonTableList();
		codonTableString = codonTableStringArray[codonTableNumber];
		
		if(codonTableNumber<24){//biological codon tables
			codonSize = 3;
			createCodonTable();
			
			//now make adjustments for specific codon tables and 
			//create set of alternative start codons (including ATG) 
			switch(codonTableNumber){
				case 1: 
					startCodons.put("ATG","M");
					startCodons.put("TTG","M");
					startCodons.put("CTG","M");
					startCodons.put("GTG","M");
					break;
				case 2:
					startCodons.put("ATG","M");
					startCodons.put("ATT","M");
					startCodons.put("ATC","M");
					startCodons.put("ATA","M");
					startCodons.put("GTG","M");
					codons.put("AGA","*");
					codons.put("AGG","*");
					codons.put("ATA","M");
					codons.put("TGA","W");
					break;
				case 3:
					startCodons.put("ATG","M");
					startCodons.put("ATA","M");
					codons.put("ATA","M");
					codons.put("CTT","T");
					codons.put("CTC","T");
					codons.put("CTA","T");
					codons.put("CTG","T");
					codons.put("TGA","W");
					codons.remove("CGA");
					codons.remove("CGC");
					break;
				case 4:
					startCodons.put("ATG","M");
					startCodons.put("TTA","M");
					startCodons.put("TTG","M");
					startCodons.put("CTG","M");
					startCodons.put("ATT","M");
					startCodons.put("ATC","M");
					startCodons.put("ATA","M");
					startCodons.put("GTG","M");
					codons.put("TGA", "W");
					break;
				case 5:
					startCodons.put("ATG","M");
					startCodons.put("TTG","M");
					startCodons.put("ATT","M");
					startCodons.put("ATC","M");
					startCodons.put("ATA","M");
					startCodons.put("GTG","M");
					codons.put("AGG", "S");
					codons.put("ATA", "M");
					codons.put("TGA", "W");
					break;
				case 6:
					startCodons.put("ATG","M");
					codons.put("TAA", "Q");
					codons.put("TAG", "Q");
					break;
				case 9:
					startCodons.put("ATG","M");
					startCodons.put("GTG","M");
					codons.put("AAA", "N");
					codons.put("AGA", "S");
					codons.put("AGG", "S");
					codons.put("TGA", "W");
					break;
				case 10:
					startCodons.put("ATG","M");
					codons.put("TGA", "C");
					break;
				case 11:
					startCodons.put("ATG","M");
					startCodons.put("TTG","M");
					startCodons.put("CTG","M");
					startCodons.put("ATT","M");
					startCodons.put("ATC","M");
					startCodons.put("ATA","M");
					startCodons.put("GTG","M");
					break;
				case 12:
					startCodons.put("ATG","M");
					startCodons.put("CTG","M");
					codons.put("CTG", "S");
					break;
				case 13:
					startCodons.put("ATG","M");
					startCodons.put("TTG","M");
					startCodons.put("ATA","M");
					startCodons.put("GTG","M");
					codons.put("AGA", "G");
					codons.put("AGG", "G");
					codons.put("ATA", "M");
					codons.put("TGA", "W");
					break;
				case 14:
					startCodons.put("ATG","M");
					codons.put("AAA", "N");
					codons.put("AGA", "S");
					codons.put("AGG", "S");
					codons.put("TAA", "Y");
					codons.put("TGA", "W");
					break;
				case 15:
					startCodons.put("ATG","M");
					codons.put("TAG", "Q");
					break;
				case 16:
					startCodons.put("ATG","M");
					codons.put("TAG", "L");
					break;
				case 21:
					startCodons.put("ATG","M");
					startCodons.put("GTG","M");
					codons.put("TGA", "W");
					codons.put("ATA", "M");
					codons.put("AAA", "N");
					codons.put("AGA", "S");
					codons.put("AGG", "S");
					break;
				case 22:
					startCodons.put("ATG","M");
					codons.put("TCA", "*");
					codons.put("TAG", "L");
					break;
				case 23:
					startCodons.put("ATG","M");
					startCodons.put("ATT","M");
					startCodons.put("GTG","M");
					codons.put("TTA", "*");
					break;
			}
		}
		else if(codonTableNumber == 24){//non-biological codon tables
			codonSize = 3;
			createGeneticAlgorithmProteinTable();
		}
		else if(codonTableNumber == 25){//non-biological codon tables
			codonSize = 2;
			createGeneticAlgorithmStructureTable();
		}
		else if(codonTableNumber == 26){//non-biological codon tables
			codonSize = 2;
			createGeneticAlgorithmDnaTable();
		}
		
	}
	private void createCodonTableList(){
		codonTableStringArray = new String[27];
		codonTableStringArray[0] = "";
		codonTableStringArray[1] = "1 Standard";
		codonTableStringArray[2] = "2 Vertebrate mitochodrial";
		codonTableStringArray[3] = "3 Yeast mitochondrial";
		codonTableStringArray[4] = "4 Mold, Protozoan, and Coelenterate Mitochondrial and Mycoplasma/Spiroplasma";
		codonTableStringArray[5] = "5 Invertebrate mitochondrial";
		codonTableStringArray[6] = "6 Ciliate, Dasycladacean and Hexamita Nuclear";
		codonTableStringArray[7] = "7 deleted";
		codonTableStringArray[8] = "8 deleted";
		codonTableStringArray[9] = "9 Echinoderm and Flatworm Mitochondrial";
		codonTableStringArray[10] = "10 Euplotid Nuclear";
		codonTableStringArray[11] = "11 Bacterial and Plant Plastid";
		codonTableStringArray[12] = "12 Alternative Yeast Nuclear";
		codonTableStringArray[13] = "13 Ascidian Mitochondrial";
		codonTableStringArray[14] = "14 Alternative Flatworm Mitochondrial";
		codonTableStringArray[15] = "15 Blepharisma Nuclear";
		codonTableStringArray[16] = "16 Chlorophycean Mitochondrial";
		codonTableStringArray[17] = "17 not existing";
		codonTableStringArray[18] = "18 not existing";
		codonTableStringArray[19] = "19 not existing";
		codonTableStringArray[20] = "20 not existing";
		codonTableStringArray[21] = "21 Trematode Mitochondrial";
		codonTableStringArray[22] = "22 Scenedesmus obliquus mitochondrial";
		codonTableStringArray[23] = "23 Thraustochytrium Mitochondrial";
		
		codonTableStringArray[24] = "genetic algorithm translation code";
		codonTableStringArray[25] = "genetic algorithm textAlignment profile code";
		codonTableStringArray[26] = "genetic algorithm dna encoding";
	}
	/**
	 * This method creates the actual (universal) codon table with Hashtable
	 */
	private void createCodonTable(){
		codons.put("TTT", "F");
		codons.put("TTC", "F");
		codons.put("TTA", "L");
		codons.put("TTG", "L");
		codons.put("TCT", "S");
		codons.put("TCC", "S");
		codons.put("TCA", "S");
		codons.put("TCG", "S");
		codons.put("TAT", "Y");
		codons.put("TAC", "Y");
		codons.put("TAA", "*");
		codons.put("TAG", "*");
		codons.put("TGT", "C");
		codons.put("TGC", "C");
		codons.put("TGA", "*");
		codons.put("TGG", "W");
		codons.put("CTT", "L");
		codons.put("CTC", "L");
		codons.put("CTA", "L");
		codons.put("CTG", "L");
		codons.put("CCT", "P");
		codons.put("CCC", "P");
		codons.put("CCA", "P");
		codons.put("CCG", "P");
		codons.put("CAT", "H");
		codons.put("CAC", "H");
		codons.put("CAA", "Q");
		codons.put("CAG", "Q");
		codons.put("CGT", "R");
		codons.put("CGC", "R");
		codons.put("CGA", "R");
		codons.put("CGG", "R");
		codons.put("ATT", "I");
		codons.put("ATC", "I");
		codons.put("ATA", "I");
		codons.put("ATG", "M");
		codons.put("ACT", "T");
		codons.put("ACC", "T");
		codons.put("ACA", "T");
		codons.put("ACG", "T");
		codons.put("AAT", "N");
		codons.put("AAC", "N");
		codons.put("AAA", "K");
		codons.put("AAG", "K");
		codons.put("AGT", "S");
		codons.put("AGC", "S");
		codons.put("AGA", "R");
		codons.put("AGG", "R");
		codons.put("GTT", "V");
		codons.put("GTC", "V");
		codons.put("GTA", "V");
		codons.put("GTG", "V");
		codons.put("GCT", "A");
		codons.put("GCC", "A");
		codons.put("GCA", "A");
		codons.put("GCG", "A");
		codons.put("GAT", "D");
		codons.put("GAC", "D");
		codons.put("GAA", "E");
		codons.put("GAG", "E");
		codons.put("GGT", "G");
		codons.put("GGC", "G");
		codons.put("GGA", "G");
		codons.put("GGG", "G");
	}
	/**
	 * the translation for the genetic algorithm encoding.
	 * Gap characters (-) introduced at several codons:
	 * ("TTA", "L"); ("TTG", "L"); ("TAA", "X"); ("TAG", "X"); 
	 * Wildcard Characters (.) at ("TGA", "X"); ("AGA", "R");  ("AGG", "R");
	 */
	private void createGeneticAlgorithmProteinTable(){
		codons.put("TTT", "F");
		codons.put("TTC", "F");
		codons.put("TTA", "-");
		codons.put("TTG", "-");
		codons.put("TCT", "S");
		codons.put("TCC", "S");
		codons.put("TCA", "S");
		codons.put("TCG", "S");
		codons.put("TAT", "Y");
		codons.put("TAC", "Y");
		codons.put("TAA", "-");
		codons.put("TAG", "-");
		codons.put("TGT", "C");
		codons.put("TGC", "C");
		codons.put("TGA", ".");
		codons.put("TGG", "W");
		codons.put("CTT", "L");
		codons.put("CTC", "L");
		codons.put("CTA", "L");
		codons.put("CTG", "L");
		codons.put("CCT", "P");
		codons.put("CCC", "P");
		codons.put("CCA", "P");
		codons.put("CCG", "P");
		codons.put("CAT", "H");
		codons.put("CAC", "H");
		codons.put("CAA", "Q");
		codons.put("CAG", "Q");
		codons.put("CGT", "R");
		codons.put("CGC", "R");
		codons.put("CGA", "R");
		codons.put("CGG", "R");
		codons.put("ATT", "I");
		codons.put("ATC", "I");
		codons.put("ATA", "I");
		codons.put("ATG", "M");
		codons.put("ACT", "T");
		codons.put("ACC", "T");
		codons.put("ACA", "T");
		codons.put("ACG", "T");
		codons.put("AAT", "N");
		codons.put("AAC", "N");
		codons.put("AAA", "K");
		codons.put("AAG", "K");
		codons.put("AGT", "S");
		codons.put("AGC", "S");
		codons.put("AGA", ".");
		codons.put("AGG", ".");
		codons.put("GTT", "V");
		codons.put("GTC", "V");
		codons.put("GTA", "V");
		codons.put("GTG", "V");
		codons.put("GCT", "A");
		codons.put("GCC", "A");
		codons.put("GCA", "A");
		codons.put("GCG", "A");
		codons.put("GAT", "D");
		codons.put("GAC", "D");
		codons.put("GAA", "E");
		codons.put("GAG", "E");
		codons.put("GGT", "G");
		codons.put("GGC", "G");
		codons.put("GGA", "G");
		codons.put("GGG", "G");
	}
	private void createGeneticAlgorithmStructureTable(){
		//this is a 2-nucleotide code
		//Alphabet: A (AU), C (CG), G (Gap),M (mismatch, W (Wobble GU),
		codons.put("AA", "A");
		codons.put("AC", "A");
		codons.put("AG", "A");
		codons.put("AT", "C");
		
		codons.put("CA", "C");
		codons.put("CC", "C");
		codons.put("CG", "C");
		codons.put("CT", "G");
		
		codons.put("GA", "G");
		codons.put("GC", "G");
		codons.put("GG", "M");
		codons.put("GT", "M");

		codons.put("TA", "M");
		codons.put("TC", "W");
		codons.put("TG", "W");
		codons.put("TT", "W");
	}
	
	private void createGeneticAlgorithmDnaTable(){
		//this is a 2-nucleotide code
		//Alphabet: A (A), C (C), G (G), T (T)
		//P (purine), Y (pyrimidine),
		codons.put("AA", "A");
		codons.put("AC", "A");
		codons.put("AG", ".");
		codons.put("AT", "P");
		
		codons.put("CA", "C");
		codons.put("CC", "C");
		codons.put("CG", ".");
		codons.put("CT", "P");
		
		codons.put("GA", "G");
		codons.put("GC", "G");
		codons.put("GG", "-");
		codons.put("GT", "Y");

		codons.put("TA", "T");
		codons.put("TC", "T");
		codons.put("TG", "-");
		codons.put("TT", "Y");
		
	}
}
