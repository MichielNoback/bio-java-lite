/**
 * 
 */
package net.cellingo.sequence_tools.alphabets;

/**
 * class represents the amino acids
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class AminoAcid extends AlphabetCharacter {
	public static final int SIZE_SMALL = 0;
	public static final int SIZE_MEDIUM = 1;
	public static final int SIZE_LARGE = 2;
	public static final int SIZE_VERY_LARGE = 3;

	private double hydrophobicity;
	private boolean isPolar;
	private double isoElectricPoint;
	private int size;
	private double alphaHelicalPreference;
	private double betaSheetPreference;
	private double turnPreference;
	private int charge;
	private double relativeAbundance;
	private boolean isHydrophobic;

	/**
	 * construct with a single-letter representation
	 * @param aminoAcid
	 * @throws IllegalCharacterException 
	 */
	public AminoAcid(char aminoAcid) throws IllegalCharacterException{
		super(aminoAcid);
		initialise();
	}

	private void initialise(){
		switch(super.getSingleCharacter()){
			//ALIPHATIC AMINO ACIDS
			case 'G':
				super.setFullName("Glycine");
				super.setAlternativeCode("Gly");
				super.setMolecularWeight(57);
				setSize(AminoAcid.SIZE_SMALL);
				setCharge(0);
				setHydrophobicity(0.67);
				setIsHydrophobic(false);
				setIsPolar(false);
				setIsoElectricPoint(5.97);
				setAlphaHelicalPreference(0.43);
				setBetaSheetPreference(0.58);
				setTurnPreference(1.77);
				setRelativeAbundance(0.075);
				break;
			case 'A':
				super.setFullName("Alanine");
				super.setAlternativeCode("Ala");
				super.setMolecularWeight(71);
				setSize(AminoAcid.SIZE_SMALL);
				setCharge(0);
				setHydrophobicity(1.0);
				setIsHydrophobic(true);
				setIsPolar(false);
				setIsoElectricPoint(6.01);
				setAlphaHelicalPreference(1.41);
				setBetaSheetPreference(0.72);
				setTurnPreference(0.82);
				setRelativeAbundance(0.090);
				break;
			case 'V':
				super.setFullName("Valine");
				super.setAlternativeCode("Val");
				super.setMolecularWeight(99);
				setSize(AminoAcid.SIZE_MEDIUM);
				setCharge(0);
				setHydrophobicity(2.30);
				setIsHydrophobic(true);
				setIsPolar(false);
				setIsoElectricPoint(5.97);
				setAlphaHelicalPreference(0.90);
				setBetaSheetPreference(1.87);
				setTurnPreference(0.41);
				setRelativeAbundance(0.069);
				break;
			case 'L':
				super.setFullName("Leucine");
				super.setAlternativeCode("Leu");
				super.setMolecularWeight(113);
				setSize(AminoAcid.SIZE_LARGE);
				setCharge(0);
				setHydrophobicity(2.2);
				setIsHydrophobic(true);
				setIsPolar(false);
				setIsoElectricPoint(5.98);
				setAlphaHelicalPreference(1.34);
				setBetaSheetPreference(1.22);
				setTurnPreference(0.57);
				setRelativeAbundance(0.075);
				break;
			case 'I':
				super.setFullName("Isoleucine");
				super.setAlternativeCode("Ile");
				super.setMolecularWeight(113);
				setSize(AminoAcid.SIZE_LARGE);
				setCharge(0);
				setHydrophobicity(3.1);
				setIsHydrophobic(true);
				setIsPolar(false);
				setIsoElectricPoint(6.02);
				setAlphaHelicalPreference(1.09);
				setBetaSheetPreference(1.67);
				setTurnPreference(0.47);
				setRelativeAbundance(0.046);
				break;
			case 'P':
				super.setFullName("Proline");
				super.setAlternativeCode("Pro");
				super.setMolecularWeight(97);
				setSize(AminoAcid.SIZE_MEDIUM);
				setCharge(0);
				setHydrophobicity(-0.29);
				setIsHydrophobic(false);
				setIsPolar(false);
				setIsoElectricPoint(6.48);
				setAlphaHelicalPreference(0.34);
				setBetaSheetPreference(0.31);
				setTurnPreference(1.32);
				setRelativeAbundance(0.046);
				break;
				
			//AROMATIC AMINO ACIDS
			case 'F':
				super.setFullName("Phenylalanine");
				super.setAlternativeCode("Phe");
				super.setMolecularWeight(147);
				setSize(AminoAcid.SIZE_LARGE);
				setCharge(0);
				setHydrophobicity(2.5);
				setIsHydrophobic(true);
				setIsPolar(false);
				setIsoElectricPoint(5.48);
				setAlphaHelicalPreference(1.16);
				setBetaSheetPreference(1.33);
				setTurnPreference(0.59);
				setRelativeAbundance(0.035);
				break;
			case 'Y':
				super.setFullName("Tyrosine");
				super.setAlternativeCode("Tyr");
				super.setMolecularWeight(163);
				setSize(AminoAcid.SIZE_LARGE);
				setCharge(0);
				setHydrophobicity(0.08);
				setIsHydrophobic(false);
				setIsPolar(true);
				setIsoElectricPoint(5.66);
				setAlphaHelicalPreference(0.74);
				setBetaSheetPreference(1.45);
				setTurnPreference(0.76);
				setRelativeAbundance(0.035);
				break;
			case 'W':
				super.setFullName("Tryptophan");
				super.setAlternativeCode("Trp");
				super.setMolecularWeight(186);
				setSize(AminoAcid.SIZE_VERY_LARGE);
				setCharge(0);
				setHydrophobicity(1.5);
				setIsHydrophobic(true);
				setIsPolar(true);
				setIsoElectricPoint(5.89);
				setAlphaHelicalPreference(1.02);
				setBetaSheetPreference(1.35);
				setTurnPreference(0.65);
				setRelativeAbundance(0.011);
				break;
				
			//POLAR BUT UNCHARGED
			case 'S':
				super.setFullName("Serine");
				super.setAlternativeCode("Ser");
				super.setMolecularWeight(87);
				setSize(AminoAcid.SIZE_SMALL);
				setCharge(0);
				setHydrophobicity(-1.1);
				setIsHydrophobic(false);
				setIsPolar(true);
				setIsoElectricPoint(5.68);
				setAlphaHelicalPreference(0.57);
				setBetaSheetPreference(0.96);
				setTurnPreference(1.22);
				setRelativeAbundance(0.071);
				break;
			case 'T':
				super.setFullName("Threonine");
				super.setAlternativeCode("Thr");
				super.setMolecularWeight(101);
				setSize(AminoAcid.SIZE_MEDIUM);
				setCharge(0);
				setHydrophobicity(-0.75);
				setIsHydrophobic(false);
				setIsPolar(true);
				setIsoElectricPoint(5.87);
				setAlphaHelicalPreference(0.76);
				setBetaSheetPreference(1.17);
				setTurnPreference(0.90);
				setRelativeAbundance(0.060);
				break;
			case 'N':
				super.setFullName("Asparagine");
				super.setAlternativeCode("Asn");
				super.setMolecularWeight(114);
				setSize(AminoAcid.SIZE_MEDIUM);
				setCharge(0);
				setHydrophobicity(-2.70);
				setIsHydrophobic(false);
				setIsPolar(true);
				setIsoElectricPoint(5.41);
				setAlphaHelicalPreference(0.76);
				setBetaSheetPreference(0.48);
				setTurnPreference(1.34);
				setRelativeAbundance(0.044);
				break;
			case 'Q':
				super.setFullName("Glutamine");
				super.setAlternativeCode("Gln");
				super.setMolecularWeight(128);
				setSize(AminoAcid.SIZE_LARGE);
				setCharge(0);
				setHydrophobicity(-2.90);
				setIsHydrophobic(false);
				setIsPolar(true);
				setIsoElectricPoint(5.65);
				setAlphaHelicalPreference(1.27);
				setBetaSheetPreference(0.98);
				setTurnPreference(0.84);
				setRelativeAbundance(0.039);
				break;
		
			//SULPHUR CONTAINING
			case 'C':
				super.setFullName("Cysteine");
				super.setAlternativeCode("Cys");
				super.setMolecularWeight(103);
				setSize(AminoAcid.SIZE_SMALL);
				setCharge(0);
				setHydrophobicity(0.17);
				setIsHydrophobic(false);
				setIsPolar(true);
				setIsoElectricPoint(5.07);
				setAlphaHelicalPreference(0.66);
				setBetaSheetPreference(1.40);
				setTurnPreference(0.54);
				setRelativeAbundance(0.028);
				break;
			case 'M':
				super.setFullName("Methionine");
				super.setAlternativeCode("Met");
				super.setMolecularWeight(131);
				setSize(AminoAcid.SIZE_LARGE);
				setCharge(0);
				setHydrophobicity(1.1);
				setIsHydrophobic(true);
				setIsPolar(false);
				setIsoElectricPoint(5.74);
				setAlphaHelicalPreference(1.30);
				setBetaSheetPreference(1.14);
				setTurnPreference(0.52);
				setRelativeAbundance(0.017);
				break;
			
			//CHARGED
			case 'D':
				super.setFullName("Aspartate");
				super.setAlternativeCode("Asp");
				super.setMolecularWeight(115);
				setSize(AminoAcid.SIZE_MEDIUM);
				setCharge(-1);
				setHydrophobicity(-3.0);
				setIsHydrophobic(false);
				setIsPolar(true);
				setIsoElectricPoint(2.77);
				setAlphaHelicalPreference(0.99);
				setBetaSheetPreference(0.39);
				setTurnPreference(1.24);
				setRelativeAbundance(0.055);
				break;
			case 'E':
				super.setFullName("Glutamate");
				super.setAlternativeCode("Glu");
				super.setMolecularWeight(129);
				setSize(AminoAcid.SIZE_LARGE);
				setCharge(-1);
				setHydrophobicity(-2.6);
				setIsHydrophobic(false);
				setIsPolar(true);
				setIsoElectricPoint(3.22);
				setAlphaHelicalPreference(1.59);
				setBetaSheetPreference(0.52);
				setTurnPreference(1.01);
				setRelativeAbundance(0.062);
				break;
			case 'H':
				super.setFullName("Histidine");
				super.setAlternativeCode("His");
				super.setMolecularWeight(137);
				setSize(AminoAcid.SIZE_VERY_LARGE);
				setCharge(0);
				setHydrophobicity(-1.7);
				setIsHydrophobic(false);
				setIsPolar(true);
				setIsoElectricPoint(7.59);
				setAlphaHelicalPreference(1.05);
				setBetaSheetPreference(0.80);
				setTurnPreference(0.81);
				setRelativeAbundance(0.021);
				break;
			case 'K':
				super.setFullName("Lysine");
				super.setAlternativeCode("Lys");
				super.setMolecularWeight(128);
				setSize(AminoAcid.SIZE_LARGE);
				setCharge(1);
				setHydrophobicity(-4.6);
				setIsHydrophobic(false);
				setIsPolar(true);
				setIsoElectricPoint(9.74);
				setAlphaHelicalPreference(1.23);
				setBetaSheetPreference(0.69);
				setTurnPreference(1.07);
				setRelativeAbundance(0.070);
				break;
			case 'R':
				super.setFullName("Arginine");
				super.setAlternativeCode("Arg");
				super.setMolecularWeight(159);
				setSize(AminoAcid.SIZE_VERY_LARGE);
				setCharge(1);
				setHydrophobicity(-7.5);
				setIsHydrophobic(false);
				setIsPolar(true);
				setIsoElectricPoint(10.76);
				setAlphaHelicalPreference(1.21);
				setBetaSheetPreference(0.84);
				setTurnPreference(0.90);
				setRelativeAbundance(0.047);
				break;
		}
	}
	
	/**
	 * returns whether the amino acid is hydrophobic
	 * @return hydrophobic status
	 */
	public boolean isHydrophobic() {
		return isHydrophobic;
	}
	
	private void setIsHydrophobic(boolean isHydrophobic) {
		this.isHydrophobic = isHydrophobic;
	}
	/**
	 * returns whetehr this amino acid is polar
	 * @return isPolar
	 */
	public boolean isPolar() {
		return isPolar;
	}
	
	private void setIsPolar(boolean isPolar) {
		this.isPolar = isPolar;
	}
	/**
	 * get the charge of this amino acid
	 * @return charge
	 */
	public int getCharge() {
		return charge;
	}
	
	private void setCharge(int charge) {
		this.charge = charge;
	}
	/**
	 * get the alpha helical preference
	 * @return alphaHelicalPreference
	 */
	public double getAlphaHelicalPreference() {
		return alphaHelicalPreference;
	}
	
	private void setAlphaHelicalPreference(double alphaHelicalPreference) {
		this.alphaHelicalPreference = alphaHelicalPreference;
	}
	/**
	 * get the beta sheet preference
	 * @return betaSheetPreference
	 */
	public double getBetaSheetPreference() {
		return betaSheetPreference;
	}
	
	private void setBetaSheetPreference(double betaSheetPreference) {
		this.betaSheetPreference = betaSheetPreference;
	}
	/**
	 * get the turn preference
	 * @return turnPreference
	 */
	public double getTurnPreference() {
		return turnPreference;
	}
	
	private void setTurnPreference(double turnPreference) {
		this.turnPreference = turnPreference;
	}
	/**
	 * get the hydrophobicity
	 * @return hydrophobicity
	 */
	public double getHydrophobicity() {
		return hydrophobicity;
	}
	private void setHydrophobicity(double hydrophobicity) {
		this.hydrophobicity = hydrophobicity;
	}
	/**
	 * get the size of this amino acid
	 * @return size
	 */
	public int getSize() {
		return size;
	}
	
	private void setSize(int size) {
		this.size = size;
	}
	/**
	 * get the relative abundance of this amino acid
	 * @return relativeAbundance
	 */
	public double getRelativeAbundance() {
		return relativeAbundance;
	}
	/**
	 * set the relative abundance of this amino acid
	 * @param fractionOfTotal
	 */
	public void setRelativeAbundance(double fractionOfTotal) {
		this.relativeAbundance = fractionOfTotal;
	}
	/**
	 * get the iso electric point
	 * @return isoElectricPoint
	 */
	public double getIsoElectricPoint() {
		return isoElectricPoint;
	}
	
	private void setIsoElectricPoint(double isoElectricPoint) {
		this.isoElectricPoint = isoElectricPoint;
	}
}
