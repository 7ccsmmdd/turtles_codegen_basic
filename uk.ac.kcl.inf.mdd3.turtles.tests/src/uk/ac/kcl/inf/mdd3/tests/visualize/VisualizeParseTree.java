package uk.ac.kcl.inf.mdd3.tests.visualize;

import uk.ac.kcl.inf.mdd.xtext.visualizer.Visualizer;
import uk.ac.kcl.inf.mdd3.TurtlesStandaloneSetup;

public class VisualizeParseTree {

	public static void main(String[] args) {
		Visualizer.visualize("model/exampleFromSlides.turtles", new TurtlesStandaloneSetup());
	}

}
