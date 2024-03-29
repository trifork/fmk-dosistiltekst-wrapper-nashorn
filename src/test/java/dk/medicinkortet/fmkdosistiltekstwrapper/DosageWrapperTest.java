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

package dk.medicinkortet.fmkdosistiltekstwrapper;

import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class DosageWrapperTest extends DosisTilTekstWrapperTestBase {
	
	@Test
	public void testDaglig4StkModSmerter2Gange() throws Exception {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnit("stk"),
					StructureWrapper.makeStructure(
						1, "mod smerter", 
						DateOrDateTimeWrapper.makeDate("2011-01-01"), null,
						DayWrapper.makeDay(1,
							PlainDoseWrapper.makeDose(new BigDecimal(4)), 
							PlainDoseWrapper.makeDose(new BigDecimal(4))))));
		
		String shortText = DosisTilTekstWrapper.convertShortText(dosage);
		Assert.assertEquals("4 stk 2 gange daglig.\nBemærk: mod smerter", shortText);
		
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011:\n" +
                        "4 stk 2 gange hver dag\n" +
                        "Bemærk: mod smerter",
                DosisTilTekstWrapper.convertLongText(dosage));
		
		DosageTranslationCombined combined = DosisTilTekstWrapper.convertCombined(dosage);
        Assert.assertEquals("4 stk 2 gange daglig.\nBemærk: mod smerter", combined.getCombinedTranslation().getShortText());
        Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011:\n" +
                        "4 stk 2 gange hver dag\n" +
                        "Bemærk: mod smerter", combined.getCombinedTranslation().getLongText());
	}
	
	@Test
	public void testWithTreatmentStartEnd() throws Exception {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnit("stk"),
					DateOrDateTimeWrapper.makeDate("2011-01-01"), 
					DateOrDateTimeWrapper.makeDate("2011-01-05"),
					StructureWrapper.makeStructure(
						1, "mod smerter", 
						DateOrDateTimeWrapper.makeDate("2011-01-01"), null,
						DayWrapper.makeDay(1,
							PlainDoseWrapper.makeDose(new BigDecimal(4)), 
							PlainDoseWrapper.makeDose(new BigDecimal(4))))));
		
		String shortText = DosisTilTekstWrapper.convertShortText(dosage);
		Assert.assertEquals("4 stk 2 gange daglig.\nBemærk: mod smerter", shortText);
		
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011:\n" +
                        "4 stk 2 gange hver dag\n" +
                        "Bemærk: mod smerter",
                DosisTilTekstWrapper.convertLongText(dosage));
		
		DosageTranslationCombined combined = DosisTilTekstWrapper.convertCombined(dosage);
        Assert.assertEquals("4 stk 2 gange daglig.\nBemærk: mod smerter", combined.getCombinedTranslation().getShortText());
        Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011:\n" +
                        "4 stk 2 gange hver dag\n" +
                        "Bemærk: mod smerter", combined.getCombinedTranslation().getLongText());
	}

	@Test
	public void testDaglig4StkModSmerterPlus4StkEfterBehovModSmerter() throws Exception {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnit("stk"),
					StructureWrapper.makeStructure(
						1, "mod smerter", 
						DateOrDateTimeWrapper.makeDate("2011-01-01"), null,
						DayWrapper.makeDay(1,
								PlainDoseWrapper.makeDose(new BigDecimal(4)), 
								PlainDoseWrapper.makeDose(new BigDecimal(4), true)))));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011:\n" +
                        "4 stk og 4 stk efter behov hver dag\n" +
                        "Bemærk: mod smerter",
                DosisTilTekstWrapper.convertLongText(dosage));
	}

	@Test
	public void testHverAndenDagEtc() throws Exception {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnit("stk"),
					StructureWrapper.makeStructure(
						2, "mod smerter", 
						DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-14"), 
						DayWrapper.makeDay(1,
							PlainDoseWrapper.makeDose(new BigDecimal(1))), 
						DayWrapper.makeDay(2, 
							PlainDoseWrapper.makeDose(new BigDecimal(2))))));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 14. jan. 2011 - gentages hver 2. dag:\n" +
                        "Dag 1: 1 stk\n" +
                        "Dag 2: 2 stk\n" +
                        "Bemærk: mod smerter",
                DosisTilTekstWrapper.convertLongText(dosage));
	}

	@Test
	public void testMorgenMiddagAftenNat() throws Exception {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnit("stk"), 
					StructureWrapper.makeStructure(
					1, "mod smerter",
					DateOrDateTimeWrapper.makeDate("2011-01-01"),DateOrDateTimeWrapper.makeDate("2011-01-14"), 
					DayWrapper.makeDay(1,
						MorningDoseWrapper.makeDose(new BigDecimal(1)), 
						NoonDoseWrapper.makeDose(new BigDecimal(1)), 
						EveningDoseWrapper.makeDose(new BigDecimal(1)), 
						NightDoseWrapper.makeDose(new BigDecimal(1)))))); 
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 14. jan. 2011:\n" +
                        "1 stk morgen, 1 stk middag, 1 stk aften og 1 stk nat - hver dag\n" +
                        "Bemærk: mod smerter",
                DosisTilTekstWrapper.convertLongText(dosage));
	}
	
	@Test
	public void testMaxQuantityFullCiphers() throws Exception {
		  
		BigDecimal minimalQuantity = new BigDecimal("0.000000001");
		BigDecimal maximalQuantity = new BigDecimal("999999999.9999999");
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnit("stk"), 
					StructureWrapper.makeStructure(
					1, "mod smerter",
					DateOrDateTimeWrapper.makeDate("2011-01-01"),DateOrDateTimeWrapper.makeDate("2011-01-14"), 
					DayWrapper.makeDay(1,
						MorningDoseWrapper.makeDose(minimalQuantity, maximalQuantity)))));
		
		DailyDosis dd = DosisTilTekstWrapper.calculateDailyDosis(dosage);
		Assert.assertEquals(0, minimalQuantity.compareTo(dd.getInterval().getMinimum()));
		Assert.assertEquals(0, maximalQuantity.compareTo(dd.getInterval().getMaximum()));
	}
	
	@Test
	public void testCombinedWithFreeText() throws Exception {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(FreeTextWrapper.makeFreeText(DateOrDateTimeWrapper.makeDate("2011-01-01"), null, "Bare tag rigeligt"));
		
		DosageTranslationCombined combined = DosisTilTekstWrapper.convertCombined(dosage);
        Assert.assertEquals("Bare tag rigeligt", combined.getCombinedTranslation().getShortText());
        Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011:\n" +
                        "\"Bare tag rigeligt\"", combined.getCombinedTranslation().getLongText());
	}
	
	@Test
	public void testShortTextToLong() {
		DosageWrapper dosage = DosageWrapper.makeDosage(StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("stk"),
				StructureWrapper.makeStructure(7, "en meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget laaaang supplerende tekst",
						DateOrDateTimeWrapper.makeDate("2012-06-08"), DateOrDateTimeWrapper.makeDate("2012-12-31"),
						DayWrapper.makeDay(1, PlainDoseWrapper.makeDose(new BigDecimal(1.0))),
						DayWrapper.makeDay(3, PlainDoseWrapper.makeDose(new BigDecimal(1.0))),
						DayWrapper.makeDay(5, PlainDoseWrapper.makeDose(new BigDecimal(1.0))),
						DayWrapper.makeDay(7, PlainDoseWrapper.makeDose(new BigDecimal(1.0))))));
		Assert.assertEquals("WeeklyRepeatedConverterImpl", DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 8. juni 2012 til d. 31. dec. 2012 - gentages hver uge:\n" +
                        "Tirsdag: 1 stk\n" +
                        "Torsdag: 1 stk\n" +
                        "Fredag: 1 stk\n" +
                        "Søndag: 1 stk\n" +
                        "Bemærk: en meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget laaaang supplerende tekst",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals(
				"1 stk tirsdag, torsdag, fredag og søndag hver uge.\nBemærk: en meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget laaaang supplerende tekst",
				DosisTilTekstWrapper.convertShortText(dosage, 300));
		Assert.assertNull(DosisTilTekstWrapper.convertShortText(dosage));
		
		DosageTranslationCombined combined = DosisTilTekstWrapper.convertCombined(dosage); 
		Assert.assertNull(combined.getCombinedTranslation().getShortText());
		Assert.assertNull(combined.getPeriodTranslations().get(0).getShortText());
		Assert.assertEquals(
                "Dosering fra d. 8. juni 2012 til d. 31. dec. 2012 - gentages hver uge:\n" +
                        "Tirsdag: 1 stk\n" +
                        "Torsdag: 1 stk\n" +
                        "Fredag: 1 stk\n" +
                        "Søndag: 1 stk\n" +
                        "Bemærk: en meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget, meget laaaang supplerende tekst",
                combined.getCombinedTranslation().getLongText());
		
		
	}
	  
}

