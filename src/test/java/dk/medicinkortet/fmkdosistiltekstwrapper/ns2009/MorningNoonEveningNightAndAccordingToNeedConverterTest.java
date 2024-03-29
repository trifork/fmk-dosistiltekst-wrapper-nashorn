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

public class MorningNoonEveningNightAndAccordingToNeedConverterTest extends DosisTilTekstWrapperTestBase {
	
	@Test
	public void test1() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("stk"), 
				StructureWrapper.makeStructure(
					1, null, DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-30"),
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(2)), 
						NoonDoseWrapper.makeDose(new BigDecimal(2)), 
						EveningDoseWrapper.makeDose(new BigDecimal(2)), 
						NightDoseWrapper.makeDose(new BigDecimal(2)), 
						PlainDoseWrapper.makeDose(new BigDecimal(2), true)))));		
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 30. jan. 2011:\n" +
                        "2 stk efter behov, 2 stk morgen, 2 stk middag, 2 stk aften og 2 stk nat - hver dag",
                DosisTilTekstWrapper.convertLongText(dosage));
//		Assert.assertEquals(
//			"MorningNoonEveningNightAndAccordingToNeedConverterImpl", 
//			DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
//		Assert.assertEquals(
//			"2 stk morgen, middag, aften og nat, samt 2 stk efter behov, højst 1 gang daglig", 
//			DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertTrue(DosisTilTekstWrapper.calculateDailyDosis(dosage).isNone());
		Assert.assertEquals(DosageType.Combined, DosisTilTekstWrapper.getDosageType(dosage));
	}

	@Test
	public void test2() throws Exception {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnit("stk"), 
					StructureWrapper.makeStructure(
						1, null, DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-11"), 
						DayWrapper.makeDay(1,
							PlainDoseWrapper.makeDose(new BigDecimal(2), true),
							MorningDoseWrapper.makeDose(new BigDecimal(2), false)))));
		Assert.assertEquals(
				"DailyRepeatedConverterImpl", 
				DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 11. jan. 2011:\n" +
                        "2 stk efter behov og 2 stk morgen - hver dag",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals(
				"MorningNoonEveningNightAndAccordingToNeedConverterImpl", 
				DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals(
                "2 stk morgen, samt 2 stk efter behov, højst 1 gang dagligt",
                DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertTrue(DosisTilTekstWrapper.calculateDailyDosis(dosage).isNone());
		Assert.assertEquals(DosageType.Combined, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test
	public void test3() throws Exception {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnit("stk"), 
					StructureWrapper.makeStructure(
						1, null, DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-11"), 
						DayWrapper.makeDay(1,
							MorningDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2)), 
							NoonDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(1)), 
							EveningDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2)), 
							PlainDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2), true)))));		
		Assert.assertEquals(
				"DailyRepeatedConverterImpl", 
				DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 11. jan. 2011:\n" +
                        "1-2 stk efter behov, 1-2 stk morgen, 1-1 stk middag og 1-2 stk aften - hver dag",
                DosisTilTekstWrapper.convertLongText(dosage));
//		Assert.assertEquals(
//				"MorningNoonEveningNightAndAccordingToNeedConverterImpl", 
//				DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
//		Assert.assertEquals(
//				"1-2 stk morgen, 1-1 stk middag og 1-2 stk aften, samt 1-2 stk efter behov, højst 1 gang daglig", 
//				DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertTrue(DosisTilTekstWrapper.calculateDailyDosis(dosage).isNone());
		Assert.assertEquals(DosageType.Combined, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test
	public void test4() throws Exception {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnit("stk"), 
					StructureWrapper.makeStructure(
						1, null, DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-11"), 
						DayWrapper.makeDay(1,
							MorningDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2)), 
							NoonDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(1)), 
							EveningDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2)), 
							PlainDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2), true), 
							PlainDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2), true)))));		
		Assert.assertEquals(
				"DailyRepeatedConverterImpl", 
				DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 11. jan. 2011:\n" +
                        "1-2 stk efter behov, 1-2 stk efter behov, 1-2 stk morgen, 1-1 stk middag og 1-2 stk aften - hver dag",
                DosisTilTekstWrapper.convertLongText(dosage));
//		Assert.assertEquals(
//				"MorningNoonEveningNightAndAccordingToNeedConverterImpl", 
//				DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
//		Assert.assertEquals(
//				"1-2 stk morgen, 1-1 stk middag og 1-2 stk aften, samt 1-2 stk efter behov, højst 2 gange daglig", 
//				DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertTrue(DosisTilTekstWrapper.calculateDailyDosis(dosage).isNone());
		Assert.assertEquals(DosageType.Combined, DosisTilTekstWrapper.getDosageType(dosage));
	}
}
