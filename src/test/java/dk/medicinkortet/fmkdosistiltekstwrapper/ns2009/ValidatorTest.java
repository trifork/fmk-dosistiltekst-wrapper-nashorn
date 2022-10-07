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

package dk.medicinkortet.fmkdosistiltekstwrapper.ns2009;

import dk.medicinkortet.fmkdosistiltekstwrapper.DosisTilTekstWrapperTestBase;
import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ValidatorTest extends DosisTilTekstWrapperTestBase {
	
	@Test
	public void testJiraFMK903() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("stk"), 
				StructureWrapper.makeStructure(
				1, null, DateOrDateTimeWrapper.makeDateTime("2012-04-13 20:06:00"), null, 
				DayWrapper.makeDay(
					1, 
					MorningDoseWrapper.makeDose(new BigDecimal(2)), 
					EveningDoseWrapper.makeDose(new BigDecimal(0))))));
		StructureWrapper s = dosage.getStructures().getStructures().iterator().next();
		Assert.assertEquals(
				1, 
				s.getDays().get(0).getNumberOfDoses());
		Assert.assertEquals(
                "Dosering fra d. 13. apr. 2012 kl. 20:06:\n" +
                        "2 stk hver morgen",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals(
				"MorningNoonEveningNightConverterImpl", 
				DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals(
				"2 stk morgen", 
				DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertEquals(
				2.0, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001); 				
	}	

	@Test
	public void testJiraFMK903Variant() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("stk"), 
				StructureWrapper.makeStructure(
					1, null, DateOrDateTimeWrapper.makeDateTime("2012-04-13 20:06:00"), null, 
					DayWrapper.makeDay(
						1, 
						PlainDoseWrapper.makeDose(new BigDecimal(2), true), 
						PlainDoseWrapper.makeDose(new BigDecimal(0), true)))));
		StructureWrapper s = dosage.getStructures().getStructures().iterator().next();
		Assert.assertEquals(
				1, 
				s.getDays().get(0).getAccordingToNeedDoses().size());
		Assert.assertEquals(
                "Dosering fra d. 13. apr. 2012 kl. 20:06:\n" +
                        "2 stk efter behov, højst 1 gang dagligt",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals(
				"SimpleLimitedAccordingToNeedConverterImpl", 
				DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals(
                "2 stk efter behov, højst 1 gang dagligt",
                DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
	}	

}
