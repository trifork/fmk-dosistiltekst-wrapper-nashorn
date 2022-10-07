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

import dk.medicinkortet.fmkdosistiltekstwrapper.DosageType;
import dk.medicinkortet.fmkdosistiltekstwrapper.DosisTilTekstWrapperTestBase;
import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class LongTextConverterTest extends DosisTilTekstWrapperTestBase {
	
	@Test
	public void testAdministrationAccordingToSchemeInLocalSystem() {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			AdministrationAccordingToSchemaWrapper.makeAdministrationAccordingToSchema(null, null));
		Assert.assertEquals("Dosering efter skriftlig anvisning",	DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals("Dosering efter skriftlig anvisning",	DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertTrue(DosisTilTekstWrapper.calculateDailyDosis(dosage).isNone());
		Assert.assertEquals(DosageType.Unspecified, DosisTilTekstWrapper.getDosageType(dosage));

	}
	
	@Test
	public void testFreeText() {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			FreeTextWrapper.makeFreeText(null, null, "Dosages in free text should always be avoided"));
		Assert.assertEquals("\"Dosages in free text should always be avoided\"", DosisTilTekstWrapper.convertLongText(dosage));
        Assert.assertTrue(DosisTilTekstWrapper.calculateDailyDosis(dosage).isNone());
		Assert.assertEquals(DosageType.Unspecified, DosisTilTekstWrapper.getDosageType(dosage));
	}

	@Test
	public void testNs2009DosageTimes() {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnit("ml"), 
					StructureWrapper.makeStructure(
						1, "ved måltid", DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-30"), 
						DayWrapper.makeDay(
							1, 
							MorningDoseWrapper.makeDose(new BigDecimal(1)), 
							EveningDoseWrapper.makeDose(new BigDecimal(2))))));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 30. jan. 2011:\n" +
                        "1 ml morgen og 2 ml aften - hver dag\n" +
                        "Bemærk: ved måltid",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals(
				3.0, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001); 				
		Assert.assertEquals(DosageType.Temporary, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test
	public void testNs2009Order() {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnit("ml"), 
					StructureWrapper.makeStructure(
						0, "før behandling", DateOrDateTimeWrapper.makeDate("2011-01-01"), null, 
						DayWrapper.makeDay(
							1, 
							TimedDoseWrapper.makeDose(new LocalTime(13,30,0), new BigDecimal(1.0), false)))));
		Assert.assertEquals(
                "Dosering kun d. 1. jan. 2011:\n" +
                        "1 ml kl. 13:30\n" +
                        "Bemærk: før behandling",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals(
				1.0, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001); 				
		Assert.assertEquals(DosageType.OneTime, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test
	public void testNs2009Order2() {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnit("ml"), 
					StructureWrapper.makeStructure(
						0, "før behandling", DateOrDateTimeWrapper.makeDate("2011-01-01"), null, 
						DayWrapper.makeDay(
							1, 
							TimedDoseWrapper.makeDose(new LocalTime(13,30,0), new BigDecimal(1.0), false),
							TimedDoseWrapper.makeDose(new LocalTime(14,30,0), new BigDecimal(2.0), false)))));
		Assert.assertEquals(
                "Dosering kun d. 1. jan. 2011:\n" +
                        "1 ml kl. 13:30 og 2 ml kl. 14:30\n" +
                        "Bemærk: før behandling",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals(
				3.0, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001); 				
		Assert.assertEquals(DosageType.Temporary, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
}
