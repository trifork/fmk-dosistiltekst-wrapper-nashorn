/**
* The contents of this file are subject to the Mozilla Public
* License Version 1.1 (the "License"); you may not use this file
* except in compliance with the License. You may obtain a copy of
* the License at http://www.mozilla.org/MPL/
*
* Software distributed under the License is distributed on an "AS
* IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
* implied. See the License for the specific language governing
* rights and limitations under the License.
*
* Contributor(s): Contributors are attributed in the source code
* where applicable.
*
* The Original Code is "Dosis-til-tekst".
*
* The Initial Developer of the Original Code is Trifork Public A/S.
*
* Portions created for the FMK Project are Copyright 2011,
* National Board of e-Health (NSI). All Rights Reserved.
*/

package dk.medicinkortet.fmkdosistiltekstwrapper.ns20120601;

import dk.medicinkortet.fmkdosistiltekstwrapper.DosageType;
import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * The purpose of this test class is to test new functionality added in FMK 1.4 (2012/06/01 namespace). 
 * The test of the general functionality is done in the testclass of the same name in the 
 * dk.medicinkortet.fmkdosistiltekstwrapper.ns2009 package. 
 */
public class SimpleLimitedAccordingToNeedConverterTest {
	
	@Test
	public void testSimple() throws Exception {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnits("påsmøring", "påsmøringer"), 
					StructureWrapper.makeStructure(
						1, null, DateOrDateTimeWrapper.makeDate("2014-01-01"), DateOrDateTimeWrapper.makeDate("2014-12-31"),
						DayWrapper.makeDay(1,
							PlainDoseWrapper.makeDose(new BigDecimal(1), true)))));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2014 til d. 31. dec. 2014:\n" +
                        "1 påsmøring efter behov, højst 1 gang dagligt",
                DosisTilTekstWrapper.convertLongText(dosage));
        Assert.assertEquals(
                "SimpleLimitedAccordingToNeedConverterImpl",
                DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
        Assert.assertEquals("1 påsmøring efter behov, højst 1 gang dagligt", DosisTilTekstWrapper.convertShortText(dosage));
        Assert.assertTrue(DosisTilTekstWrapper.calculateDailyDosis(dosage).isNone());
		Assert.assertEquals(DosageType.AccordingToNeed, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
}
