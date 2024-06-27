package net.cellingo.sequence_tools.primer_eval;

import java.util.HashMap;

/**
 * class wrapping the properties and tests of the primer
 * @author Michiel Noback (m.a.noback@pl.hanze.nl)
 * @version 0.1
 *
 */
public class PrimerEvaluation{

	/**
	 * map with primer properties, to be filled when the evaluate() method is called
	 */
	private HashMap<PrimerEvaluationProperty, String> properties = new HashMap<PrimerEvaluationProperty, String>();
	/**
	 * map with primer test results, to be filled when the evaluate() method is called
	 */
	private HashMap<PrimerEvaluationTest, String> tests = new HashMap<PrimerEvaluationTest, String>();
	/**
	 * @return the properties
	 */
	public HashMap<PrimerEvaluationProperty, String> getProperties() {
		return properties;
	}
	/**
	 * @param properties the properties to set
	 */
	public void setProperties(HashMap<PrimerEvaluationProperty, String> properties) {
		this.properties = properties;
	}
	/**
	 * @return the tests
	 */
	public HashMap<PrimerEvaluationTest, String> getTests() {
		return tests;
	}
	/**
	 * @param tests the tests to set
	 */
	public void setTests(HashMap<PrimerEvaluationTest, String> tests) {
		this.tests = tests;
	}

}