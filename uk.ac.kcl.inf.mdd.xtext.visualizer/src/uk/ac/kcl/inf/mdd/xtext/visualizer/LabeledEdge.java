package uk.ac.kcl.inf.mdd.xtext.visualizer;

import org.jgrapht.graph.DefaultEdge;

public class LabeledEdge extends DefaultEdge {

	private String label;
	private static final long serialVersionUID = 4850696354887980223L;
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	@Override
	public String toString() {
		return label;
	}

}
