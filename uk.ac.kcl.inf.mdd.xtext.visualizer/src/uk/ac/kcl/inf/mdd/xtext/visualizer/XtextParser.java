package uk.ac.kcl.inf.mdd.xtext.visualizer;

import java.io.IOException;
import java.io.Reader;
import javax.inject.Inject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.parser.ParseException;

import com.google.inject.Injector;

public class XtextParser {

	    @Inject
	    private IParser parser;

	    public XtextParser(ISetup setup) {
	        setupParser(setup);
	    }

	    private void setupParser(ISetup setup) {
	        Injector injector = setup.createInjectorAndDoEMFRegistration();
	        injector.injectMembers(this);
	    }

	    /**
	     * Parses data provided by an input reader using Xtext and returns the root node of the resulting object tree.
	     * @param reader Input reader
	     * @return root object node
	     * @throws IOException when errors occur during the parsing process
	     */
	    public EObject parse(Reader reader) throws IOException
	    {
	        IParseResult result = parser.parse(reader);
	        if(result.hasSyntaxErrors())
	        {
	            throw new ParseException("Provided input contains syntax errors.");
	        }
	        return result.getRootASTElement();
	    }
	}

