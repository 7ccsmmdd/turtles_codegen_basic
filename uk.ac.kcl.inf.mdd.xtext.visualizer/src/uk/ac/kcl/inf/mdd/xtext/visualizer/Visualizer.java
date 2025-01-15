package uk.ac.kcl.inf.mdd.xtext.visualizer;

import java.awt.Dimension;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.ISetup;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultListenableGraph;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;

/**
 * A demo applet that shows how to use JGraphX to visualize JGraphT graphs.
 * Applet based on JGraphAdapterDemo.
 *
 */
@SuppressWarnings("deprecation")
public class Visualizer extends JApplet {
	private static final long serialVersionUID = 2202072534703043194L;

	private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

	private JGraphXAdapter<String, LabeledEdge> jgxAdapter;
	
	private static String fileName;
	private static ISetup setup;

	/**
	 * first parameter should be path to xtext file
	 * 
	 * @param args command line arguments
	 */
	public static void visualize(String fileName, ISetup setup) {
		Visualizer.fileName = fileName;
		Visualizer.setup = setup;
		
		Visualizer applet = new Visualizer();
		applet.init();

		JFrame frame = new JFrame();
		frame.getContentPane().add(applet);
		frame.setTitle("JGraphT Adapter to JGraphX Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void init() {
		// create a JGraphT graph
		ListenableGraph<String, LabeledEdge> g = new DefaultListenableGraph<>(
				new DefaultDirectedGraph<>(LabeledEdge.class));

		// create a visualization using JGraph, via an adapter
		jgxAdapter = new JGraphXAdapter<>(g);

		setPreferredSize(DEFAULT_SIZE);
		mxGraphComponent component = new mxGraphComponent(jgxAdapter);
		component.setConnectable(false);
		component.getGraph().setAllowDanglingEdges(false);
		getContentPane().add(component);
		resize(DEFAULT_SIZE);

		if (g != null) {
			populateGraph(g);
		}

		mxCompactTreeLayout layout = new mxCompactTreeLayout(jgxAdapter);
		layout.setHorizontal(false);
		layout.execute(jgxAdapter.getDefaultParent());
	}

	Map<EObject, String> nodeName = new LinkedHashMap<EObject, String>();

	private void populateGraph(ListenableGraph<String, LabeledEdge> g) {

		EObject o;
		try {
			Reader r = new FileReader(fileName);
			XtextParser p = new XtextParser(setup);
			o = p.parse(r);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (o != null) {
			addVertex(g, o);
		}
	}

	/**
	 * add an object to the graph and take care of its contained children and
	 * references
	 * 
	 * @param g
	 * @param o
	 */
	@SuppressWarnings("rawtypes")
	private void addVertex(ListenableGraph<String, LabeledEdge> g, EObject o) {
		if (!nodeName.containsKey(o)) {
			String node = getName(o);
			nodeName.put(o, node);
			g.addVertex(node);
		}
		for (EObject ch : o.eContents()) {
			addVertex(g, ch);
			addEdge(g, o, ch);
		}
		for (EReference r : o.eClass().getEReferences()) {
			Object target = o.eGet(r, true);
			if (target instanceof EList) {
				for (Object ro : (EList) target) {
					EObject ch = (EObject) ro;
					addVertex(g, ch);
					addEdge(g, o, ch);
				}
			} else {
				EObject ch = (EObject) target;
				if (ch != null) {
					addVertex(g, ch);
					addEdge(g, o, ch);
				}
			}
		}
	}

	/**
	 * add an edge between two objects and label it with the relation they have to
	 * each other
	 * 
	 * objects are expected to exist in the graph
	 * 
	 * @param g
	 * @param o
	 * @param ch
	 */
	private void addEdge(ListenableGraph<String, LabeledEdge> g, EObject o, EObject ch) {
		LabeledEdge e = g.addEdge(nodeName.get(o), nodeName.get(ch));
		if (e != null) {
			e.setLabel(ch.eContainingFeature().getName());
		}
	}

	/**
	 * compute a nice name for the object (include attributes and values)
	 * 
	 * @param o
	 * @return
	 */
	private String getName(EObject o) {
		String name = o.eClass().getName();
		name += "_" + getNumberOf(name);
		for (EAttribute a : o.eClass().getEAttributes()) {
			name += " " + a.getName() + ":" + o.eGet(a).toString();
		}
		return name;
	}

	/**
	 * calculate the number of the next instance of this class
	 * 
	 * @param name
	 * @return
	 */
	private int getNumberOf(String name) {
		int num = 0;
		for (String names : nodeName.values()) {
			if (names.startsWith(name)) {
				num++;
			}
		}
		return num;
	}
}
