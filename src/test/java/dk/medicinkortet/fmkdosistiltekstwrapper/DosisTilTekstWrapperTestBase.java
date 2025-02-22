package dk.medicinkortet.fmkdosistiltekstwrapper;

import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import org.junit.Before;

public abstract class DosisTilTekstWrapperTestBase {
	public static final String NODE_SERVER_BASE_URL = "http://dosis2text:8000/";
	static boolean isInitialized = false;
	
	@Before
	public void setUp() {
		if(!isInitialized) {
			DosisTilTekstWrapper.initializeAndUseNode(NODE_SERVER_BASE_URL);
			isInitialized = true;
		}
	}
}
