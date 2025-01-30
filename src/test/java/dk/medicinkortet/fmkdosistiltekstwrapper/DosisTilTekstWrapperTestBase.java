package dk.medicinkortet.fmkdosistiltekstwrapper;

import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import org.junit.Before;

import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

public abstract class DosisTilTekstWrapperTestBase {

	static boolean isInitialized = false;
	
	@Before
	public void setUp() throws FileNotFoundException, ScriptException {
		if(!isInitialized) {
			String jsLocation = System.getProperty("dosistiltekstJSlocation");
			if (jsLocation == null) {
				jsLocation = "node_modules/fmk-dosis-til-tekst-ts/dist/dosistiltekst.js"; //  "../fmk-dosis-til-tekst-ts/target/dosistiltekst.js";    // For typical local developer use. Property value usually set on jenkins
			}
			DosisTilTekstWrapper.initializeAndUseNode("http://localhost:8000/"); // run fmk-dosis-til-tekst-node locally to test using node server
			isInitialized = true;
		}
	}
}
