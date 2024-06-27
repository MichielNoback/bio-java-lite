package net.cellingo.sequence_tools.primer_eval;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author michiel
 *
 */
public class PrimerEvaluator {

    public PrimerEvaluator() {

    }

    /**
     * returns a list of primers for testing purposes
     *
     * @return
     */
    public static ArrayList<Primer> getTestingPrimers() {
        /*
         * >reverse
         aacagctatgaccatg
         >T3
         attaaccctcactaaag
         >KS
         cgaggtcgacggtatcg
         >SK
         tctagaactagtggatc
         >T7
         aatacgactcactatag
         >-40
         gttttcccagtcacgac
         >Sp6
         atttaggtgacactatag
         */
        ArrayList<Primer> primers = new ArrayList<Primer>();
        primers.add(new Primer("atttaggtgacactatag", "self_annealing1"));
        primers.add(new Primer("ggagctgcatgtgtcagagg", "self_annealing2"));

        //primers.add(new Primer("ggagctgcatgtgtcagagg","pGEX rev"));
        //primers.add(new Primer("ctggcaagccacgtttggtg","pGEX for"));
        //primers.add(new Primer("tagaaggcacagtcgagg","BGH rev"));
        //primers.add(new Primer("cacacaggaaacagctatgaccat","M13 rev"));
        //primers.add(new Primer("gtaaaacgacggccagt","M13 for"));
        //primers.add(new Primer("cgaggtcgacggtatcg","KS"));
        return primers;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<Primer> primers = null;
        if (args.length == 0) {
            primers = getTestingPrimers();
        } else {
            throw new UnsupportedOperationException("sorry, reading from file not implemented yet");
        }

        PrimerEvaluator pe = new PrimerEvaluator();
        ArrayList<PrimerEvaluation> peList = pe.evaluatePrimers(primers);
        for (PrimerEvaluation peval : peList) {
            Map<PrimerEvaluationProperty, String> properties = peval.getProperties();
            Map<PrimerEvaluationTest, String> tests = peval.getTests();
            System.out.println("\n----------------------------------------------------------------------------");
            System.out.println("\n############################ General properties ############################");
            for (PrimerEvaluationProperty pep : PrimerEvaluationProperty.values()) {
                String prop = properties.get(pep) == null ? "NA" : properties.get(pep);
                System.out.println(String.format("%32s: %s", pep, prop));
            }
            System.out.println("\n################## PCR suitability tests (Pass / Warning) ##################");
            for (PrimerEvaluationTest pet : PrimerEvaluationTest.values()) {
                String prop = tests.get(pet) == null ? "NA" : tests.get(pet);
                System.out.println(String.format("%32s: %s", pet, prop));
            }
        }
    }

    /**
     * evaluates a list of primers and attaches a PrimerEvaluation object to
     * them
     *
     * @param primers
     * @return
     */
    public ArrayList<PrimerEvaluation> evaluatePrimers(ArrayList<Primer> primers) {
        ArrayList<PrimerEvaluation> peList = new ArrayList<PrimerEvaluation>();
        for (Primer p : primers) {
            PrimerEvaluation pe = p.evaluate();
            peList.add(pe);
        }
        return peList;
    }

}
