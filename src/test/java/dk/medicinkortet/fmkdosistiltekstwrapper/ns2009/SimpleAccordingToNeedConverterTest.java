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

public class SimpleAccordingToNeedConverterTest extends DosisTilTekstWrapperTestBase {
	
	@Test
	public void test2StkEfterBehov() throws Exception {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnit("stk"), 
					StructureWrapper.makeStructure(
						0, null, DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-11"), 
						DayWrapper.makeDay(0,
							PlainDoseWrapper.makeDose(new BigDecimal(2), true)))));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 11. jan. 2011:\n" +
                        "2 stk efter behov",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals(
				"SimpleAccordingToNeedConverterImpl", 
				DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals(
				"2 stk efter behov", 
				DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertTrue(DosisTilTekstWrapper.calculateDailyDosis(dosage).isNone());
		Assert.assertEquals(DosageType.AccordingToNeed, DosisTilTekstWrapper.getDosageType(dosage));
	}

	@Test
	public void test2StkEfterBehovVedSmerter() throws Exception {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnit("stk"), 
					StructureWrapper.makeStructure(
						0, "ved smerter", DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-11"), 
						DayWrapper.makeDay(0,
							PlainDoseWrapper.makeDose(new BigDecimal(2), true)))));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 11. jan. 2011:\n" +
                        "2 stk efter behov\n" +
                        "Bemærk: ved smerter",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals(
				"SimpleAccordingToNeedConverterImpl", 
				DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals(
				"2 stk efter behov.\nBemærk: ved smerter", 
				DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertTrue(DosisTilTekstWrapper.calculateDailyDosis(dosage).isNone());
		Assert.assertEquals(DosageType.AccordingToNeed, DosisTilTekstWrapper.getDosageType(dosage));
	}

	@Test
	public void test1Til2StkEfterBehov() throws Exception {
		DosageWrapper dosage = 
			DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnit("stk"), 
					StructureWrapper.makeStructure(
						0, null, DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-11"), 
						DayWrapper.makeDay(0,
							PlainDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2), true)))));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 11. jan. 2011:\n" +
                        "1-2 stk efter behov",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals(
				"SimpleAccordingToNeedConverterImpl", 
				DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals(
				"1-2 stk efter behov", 
				DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertTrue(DosisTilTekstWrapper.calculateDailyDosis(dosage).isNone());
		Assert.assertEquals(DosageType.AccordingToNeed, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
}
