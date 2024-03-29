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
import dk.medicinkortet.fmkdosistiltekstwrapper.DosisTilTekstWrapperTestBase;
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
public class MorningNoonEveningNightConverterTest extends DosisTilTekstWrapperTestBase {
	
	@Test
	public void testUnits() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnits("tablet", "tabletter"), 
				StructureWrapper.makeStructure(
					1, "ved måltid", DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-30"), 
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(1)), 
						EveningDoseWrapper.makeDose(new BigDecimal(2)))))); 
		Assert.assertEquals(
				"DailyRepeatedConverterImpl", 
				DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 30. jan. 2011:\n" +
                        "1 tablet morgen og 2 tabletter aften - hver dag\n" +
                        "Bemærk: ved måltid",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals(
			"MorningNoonEveningNightConverterImpl", 
			DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals(
			"1 tablet morgen og 2 tabletter aften.\nBemærk: ved måltid", 
			DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertEquals(
				3.0, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001); 			
		Assert.assertEquals(DosageType.Temporary, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test
	public void testWhereEquals() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnits("tablet", "tabletter"), 
				StructureWrapper.makeStructure(
					1, "ved måltid", DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-30"), 
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(2)), 
						EveningDoseWrapper.makeDose(new BigDecimal(2)))))); 
		Assert.assertEquals(
				"DailyRepeatedConverterImpl", 
				DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 30. jan. 2011:\n" +
                        "2 tabletter morgen og 2 tabletter aften - hver dag\n" +
                        "Bemærk: ved måltid",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals(
			"MorningNoonEveningNightConverterImpl", 
			DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals(
			"2 tabletter morgen og aften.\nBemærk: ved måltid", 
			DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertEquals(
				4.0, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001); 			
		Assert.assertEquals(DosageType.Temporary, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test
	public void testTooLong() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnits("tablet", "tabletter"), 
				StructureWrapper.makeStructure(
					1, "ved måltid og hvornår man ellers skulle føle trang", DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-30"), 
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(1.5)), 
						NoonDoseWrapper.makeDose(new BigDecimal(2.5)),
						EveningDoseWrapper.makeDose(new BigDecimal(3.5)),
						NightDoseWrapper.makeDose(new BigDecimal(4.5)))))); 
		Assert.assertEquals(
				"DailyRepeatedConverterImpl", 
				DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 30. jan. 2011:\n" +
                        "1,5 tabletter morgen, 2,5 tabletter middag, 3,5 tabletter aften og 4,5 tabletter nat - hver dag\n" +
                        "Bemærk: ved måltid og hvornår man ellers skulle føle trang",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertNull(DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertEquals(
				12.0, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001); 			
		Assert.assertEquals(DosageType.Temporary, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test
	public void testAccordingToNeed() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnits("mg", "mg"),  
				StructureWrapper.makeStructure(
					1, "ved måltid", DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-30"), 
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(1), true), 
						EveningDoseWrapper.makeDose(new BigDecimal(2), true))))); 
		Assert.assertEquals(
				"DailyRepeatedConverterImpl", 
				DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 30. jan. 2011:\n" +
                        "1 mg morgen efter behov og 2 mg aften efter behov\n" +
                        "Bemærk: ved måltid",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
		Assert.assertEquals(DosageType.AccordingToNeed, DosisTilTekstWrapper.getDosageType(dosage));
	}
}
