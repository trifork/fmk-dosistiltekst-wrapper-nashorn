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

/**
 * Examples of translation to long dosage text, as discussed on the FMK-teknik forum: 
 * http://www.fmk-teknik.dk/index.php?topic=135.0
 */
public class LongTextComplexConverterTest extends DosisTilTekstWrapperTestBase {

	@Test /* Dosage "1 tablet morgen" */
	public void test1TabletMorgen() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("tablet"), 
				StructureWrapper.makeStructure(
					1, null, DateOrDateTimeWrapper.makeDate("2012-04-18"), null, 
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))))));
		Assert.assertEquals(
                "Dosering fra d. 18. apr. 2012:\n" +
                        "1 tablet hver morgen",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals("1 tablet morgen", DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertEquals(
				1, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001);
		Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
	}

	@Test /* Dosage "1 tablet morgen" with datetimes */
	public void test1TabletMorgenWithDatetimes() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("tablet"), 
				StructureWrapper.makeStructure(
				1, null, DateOrDateTimeWrapper.makeDateTime("2012-04-18 08:30:00"), null,
				DayWrapper.makeDay(
					1, 
					MorningDoseWrapper.makeDose(new BigDecimal(1))))));
		Assert.assertEquals(
                "Dosering fra d. 18. apr. 2012 kl. 8:30:\n" +
                        "1 tablet hver morgen",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals("1 tablet morgen", DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertEquals(
				1, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001); 			
		Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
	}		
	
	@Test /* Dosage like the "Hjerdyl"-example */
	public void testHjerdyl() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("tablet"), 
				StructureWrapper.makeStructure(
				2, null, DateOrDateTimeWrapper.makeDate("2012-04-18"), null, 
				DayWrapper.makeDay(
					1, 
					MorningDoseWrapper.makeDose(new BigDecimal(1))), 
				DayWrapper.makeDay(
					2, 
					MorningDoseWrapper.makeDose(new BigDecimal(1)), 
					EveningDoseWrapper.makeDose(new BigDecimal(1))))));
		Assert.assertEquals(
                "Dosering fra d. 18. apr. 2012 - gentages hver 2. dag:\n" +
                        "Dag 1: 1 tablet morgen\n" +
                        "Dag 2: 1 tablet morgen og 1 tablet aften",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertEquals(
				1.5, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001); 				
		Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
	}		
	
	@Test /* Dosage like the "Alendronat" example */
	public void testAledronat() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("tablet"), 
				StructureWrapper.makeStructure(
				7, null, DateOrDateTimeWrapper.makeDate("2012-04-18"), null, 
				DayWrapper.makeDay(
					1, 
					MorningDoseWrapper.makeDose(new BigDecimal(1))))));
		Assert.assertEquals(
                "Dosering fra d. 18. apr. 2012 - gentages hver uge:\n" +
                        "Onsdag: 1 tablet morgen",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals("WeeklyMorningNoonEveningNightConverterImpl", DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals("1 tablet morgen onsdag hver uge", DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertEquals(
				1/7.0, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001); 				
		Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test /* Dosage like the "Marevan 14-dages skema 1+2 stk" example */
	public void testMarevan14DagesSkema1_2Tablet() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("stk"), 
				StructureWrapper.makeStructure(
					14, null, DateOrDateTimeWrapper.makeDate("2012-04-19"), null, 
					DayWrapper.makeDay(
						1, // torsdag
						MorningDoseWrapper.makeDose(new BigDecimal(2))), 
					DayWrapper.makeDay(
						2, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))), 
					DayWrapper.makeDay(
						3, // lørdag
						MorningDoseWrapper.makeDose(new BigDecimal(2))), 
					DayWrapper.makeDay(
						4, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))), 
					DayWrapper.makeDay(
						5, // mandag
						MorningDoseWrapper.makeDose(new BigDecimal(2))), 
					DayWrapper.makeDay(
						6, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))), 
					DayWrapper.makeDay(
						7, // onsdag
						MorningDoseWrapper.makeDose(new BigDecimal(2))),	
					DayWrapper.makeDay(
						8, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))),	
					DayWrapper.makeDay(
						9, // fredag
						MorningDoseWrapper.makeDose(new BigDecimal(2))),	
					DayWrapper.makeDay(
						10, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))),	
					DayWrapper.makeDay(
						11, // søndag
						MorningDoseWrapper.makeDose(new BigDecimal(2))),	
					DayWrapper.makeDay(
						12, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))),	
					DayWrapper.makeDay(
						13, // tirsdag
						MorningDoseWrapper.makeDose(new BigDecimal(2))),	
					DayWrapper.makeDay(
						14, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))))));
		Assert.assertEquals(
                "Dosering fra d. 19. apr. 2012 - gentages hver 14. dag:\n" +
                        "Dag 1: 2 stk morgen\n" +
                        "Dag 2: 1 stk morgen\n" +
                        "Dag 3: 2 stk morgen\n" +
                        "Dag 4: 1 stk morgen\n" +
                        "Dag 5: 2 stk morgen\n" +
                        "Dag 6: 1 stk morgen\n" +
                        "Dag 7: 2 stk morgen\n" +
                        "Dag 8: 1 stk morgen\n" +
                        "Dag 9: 2 stk morgen\n" +
                        "Dag 10: 1 stk morgen\n" +
                        "Dag 11: 2 stk morgen\n" +
                        "Dag 12: 1 stk morgen\n" +
                        "Dag 13: 2 stk morgen\n" +
                        "Dag 14: 1 stk morgen",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertEquals(
				1.5, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001); 		
		Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test /* Dosage like the "Marevan ugeskema 1+2 stk" example */
	public void testMarevanUgeskema1_2Tabletter() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("stk"), 
				StructureWrapper.makeStructure(
					7, null, DateOrDateTimeWrapper.makeDate("2012-04-19"), null, 
					DayWrapper.makeDay(
						1, // torsdag
						MorningDoseWrapper.makeDose(new BigDecimal(2))), 
					DayWrapper.makeDay(
						2, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))), 
					DayWrapper.makeDay(
						3, // lørdag
						MorningDoseWrapper.makeDose(new BigDecimal(2))), 
					DayWrapper.makeDay(
						4, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))), 
					DayWrapper.makeDay(
						5, // mandag
						MorningDoseWrapper.makeDose(new BigDecimal(2))), 
					DayWrapper.makeDay(
						6, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))), 
					DayWrapper.makeDay(
						7, // onsdag
						MorningDoseWrapper.makeDose(new BigDecimal(1))))));
		Assert.assertEquals(
                "Dosering fra d. 19. apr. 2012 - gentages hver uge:\n" +
                        "Mandag: 2 stk morgen\n" +
                        "Tirsdag: 1 stk morgen\n" +
                        "Onsdag: 1 stk morgen\n" +
                        "Torsdag: 2 stk morgen\n" +
                        "Fredag: 1 stk morgen\n" +
                        "Lørdag: 2 stk morgen\n" +
                        "Søndag: 1 stk morgen",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertEquals(
				10/7.0, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001); 		
		Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
	}	
	
	@Test /* Dosage like the "Naragan ugeskema 1 tablet" example */
	public void testNaraganUgeskema1Tablet() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("tablet"), 
				StructureWrapper.makeStructure(
					7, null, DateOrDateTimeWrapper.makeDate("2012-04-18"), null, 
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))), 
					DayWrapper.makeDay(
						3, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))), 
					DayWrapper.makeDay(
						5, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))), 
					DayWrapper.makeDay(
						7, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))))));
		Assert.assertEquals(
                "Dosering fra d. 18. apr. 2012 - gentages hver uge:\n" +
                        "Tirsdag: 1 tablet morgen\n" +
                        "Onsdag: 1 tablet morgen\n" +
                        "Fredag: 1 tablet morgen\n" +
                        "Søndag: 1 tablet morgen",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals("WeeklyMorningNoonEveningNightConverterImpl", DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals("1 tablet morgen tirsdag, onsdag, fredag og søndag hver uge", DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertEquals(
				4/7.0, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001); 			
		Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test /* Dosage like the "Naragan ugeskema 2 tabletter" example */
	public void testNaraganUgeskema2Tabletter() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("tabletter"), 
				StructureWrapper.makeStructure(
					7, null, DateOrDateTimeWrapper.makeDate("2012-04-18"), null, 
					DayWrapper.makeDay(
						2, 
						MorningDoseWrapper.makeDose(new BigDecimal(2))), 
					DayWrapper.makeDay(
						4, 
						MorningDoseWrapper.makeDose(new BigDecimal(2))), 
					DayWrapper.makeDay(
						6, 
						MorningDoseWrapper.makeDose(new BigDecimal(2))))));
		Assert.assertEquals(
                "Dosering fra d. 18. apr. 2012 - gentages hver uge:\n" +
                        "Mandag: 2 tabletter morgen\n" +
                        "Torsdag: 2 tabletter morgen\n" +
                        "Lørdag: 2 tabletter morgen",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals("WeeklyMorningNoonEveningNightConverterImpl", DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals("2 tabletter morgen mandag, torsdag og lørdag hver uge", DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertEquals(
				6/7.0, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001); 
		Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
	}	
	
	@Test /* Dosage like the "Morfin nedtrapning" example */
	public void testMorfinNedtrapning() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("stk"), 
				StructureWrapper.makeStructure(
					0, null, DateOrDateTimeWrapper.makeDate("2012-04-18"), null, 
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(2)), 
						NoonDoseWrapper.makeDose(new BigDecimal(2)),
						EveningDoseWrapper.makeDose(new BigDecimal(2))), 
					DayWrapper.makeDay(
						2, 
						MorningDoseWrapper.makeDose(new BigDecimal(2)), 
						NoonDoseWrapper.makeDose(new BigDecimal(1)),
						EveningDoseWrapper.makeDose(new BigDecimal(2))), 
					DayWrapper.makeDay(
						3, 
						MorningDoseWrapper.makeDose(new BigDecimal(1)), 
						NoonDoseWrapper.makeDose(new BigDecimal(1)),
						EveningDoseWrapper.makeDose(new BigDecimal(2))), 
					DayWrapper.makeDay(
						4, 
						MorningDoseWrapper.makeDose(new BigDecimal(1)), 
						EveningDoseWrapper.makeDose(new BigDecimal(1))), 
					DayWrapper.makeDay(
						5, 
						MorningDoseWrapper.makeDose(new BigDecimal(1)), 
						EveningDoseWrapper.makeDose(new BigDecimal(1))), 
					DayWrapper.makeDay(
						6, 
						EveningDoseWrapper.makeDose(new BigDecimal(1))))));
		Assert.assertEquals(
                "Dosering fra d. 18. apr. 2012:\n" +
                        "Onsdag d. 18. apr. 2012: 2 stk morgen, 2 stk middag og 2 stk aften\n" +
                        "Torsdag d. 19. apr. 2012: 2 stk morgen, 1 stk middag og 2 stk aften\n" +
                        "Fredag d. 20. apr. 2012: 1 stk morgen, 1 stk middag og 2 stk aften\n" +
                        "Lørdag d. 21. apr. 2012: 1 stk morgen og 1 stk aften\n" +
                        "Søndag d. 22. apr. 2012: 1 stk morgen og 1 stk aften\n" +
                        "Mandag d. 23. apr. 2012: 1 stk aften",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertNull(
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
		Assert.assertEquals(DosageType.Temporary, DosisTilTekstWrapper.getDosageType(dosage));
	}		
	
	@Test /* Dosage like the "Pulmicort" example */ 
	public void testDag0Iterationsinterval0() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("sug"), 
				StructureWrapper.makeStructure(
					0, "ved anstrengelse", DateOrDateTimeWrapper.makeDate("2012-04-18"), null, 
					DayWrapper.makeDay(
						0, 
						PlainDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2), true)))));
		Assert.assertEquals(
				"DefaultLongTextConverterImpl",
				DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 18. apr. 2012:\n" +
                        "1-2 sug efter behov\n" +
                        "Bemærk: ved anstrengelse",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals(
				"SimpleAccordingToNeedConverterImpl", 
				DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals(
				"1-2 sug efter behov.\nBemærk: ved anstrengelse", 
				DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
		Assert.assertEquals(DosageType.AccordingToNeed, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test /* Dosage like the "Ipren" example */
	public void testDag1Iterationsinterval1() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("tabletter"), 
				StructureWrapper.makeStructure(
					1, "ved smerter", DateOrDateTimeWrapper.makeDate("2012-04-18"), null, 
					DayWrapper.makeDay(
						1, 
						PlainDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2), true), 
						PlainDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2), true), 
						PlainDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2), true)))));
		Assert.assertEquals(
                "Dosering fra d. 18. apr. 2012:\n" +
                        "1-2 tabletter efter behov, højst 3 gange dagligt\n" +
                        "Bemærk: ved smerter",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
		Assert.assertEquals(DosageType.AccordingToNeed, DosisTilTekstWrapper.getDosageType(dosage));
	}	

	@Test /* Dosage like the "Ipren" example, with a minor variation */
	public void testDag0Iterationsinterval1() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("tabletter"), 
				StructureWrapper.makeStructure(
					1, "ved smerter", DateOrDateTimeWrapper.makeDate("2012-04-18"), null, 
					DayWrapper.makeDay(
						0, 
						PlainDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2), true)))));
		Assert.assertEquals(
                "Dosering fra d. 18. apr. 2012:\n" +
                        "1-2 tabletter efter behov, højst 1 gang dagligt\n" +
                        "Bemærk: ved smerter",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
		Assert.assertEquals(DosageType.AccordingToNeed, DosisTilTekstWrapper.getDosageType(dosage));
	}
		
	@Test /* Test dosage without meaning, this dosage must still be translated */
	public void test012Iterationsinterval0() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("ml"), 
				StructureWrapper.makeStructure(
					0, "mod smerter", DateOrDateTimeWrapper.makeDate("2012-04-18"), null, 
					DayWrapper.makeDay(
						0, 
						PlainDoseWrapper.makeDose(new BigDecimal(1), true)),
					DayWrapper.makeDay(
						1, 
						PlainDoseWrapper.makeDose(new BigDecimal(20), true),
						PlainDoseWrapper.makeDose(new BigDecimal(20), true)),
					DayWrapper.makeDay(
						2, 
						PlainDoseWrapper.makeDose(new BigDecimal(20), true),
						PlainDoseWrapper.makeDose(new BigDecimal(20), true)))));
		Assert.assertEquals(
                "Dosering fra d. 18. apr. 2012:\n" +
                        "1 ml efter behov\n" +
                        "Onsdag d. 18. apr. 2012: 20 ml efter behov, højst 2 gange dagligt\n" +
                        "Torsdag d. 19. apr. 2012: 20 ml efter behov, højst 2 gange dagligt\n" +
                        "Bemærk: mod smerter",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
		Assert.assertEquals(DosageType.AccordingToNeed, DosisTilTekstWrapper.getDosageType(dosage));
	}

	@Test /* Test dosage without meaning, must still be translated */
	public void testDag012Iterationsinterval2() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("sug"), 
				StructureWrapper.makeStructure(
					2, "ved anstrengelse", DateOrDateTimeWrapper.makeDate("2012-04-18"), null, 
					DayWrapper.makeDay(
						0, 
						PlainDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2), true)),
					DayWrapper.makeDay(
						1, 
						PlainDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2), true)),
					DayWrapper.makeDay(
						2, 
						PlainDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2), true)))));
		Assert.assertEquals(
                "Dosering fra d. 18. apr. 2012 - gentages hver 2. dag:\n" +
                        "1-2 sug efter behov, højst 1 gang hver 2. dag\n" +
                        "Dag 1: 1-2 sug efter behov, højst 1 gang dagligt\n" +
                        "Dag 2: 1-2 sug efter behov, højst 1 gang dagligt\n" +
                        "Bemærk: ved anstrengelse",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
		Assert.assertEquals(DosageType.AccordingToNeed, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test /* Weekly dosage */ 
	public void testDag0Iterationsinterval7() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("tabletter"), 
				StructureWrapper.makeStructure(
					7, "ved smerter", DateOrDateTimeWrapper.makeDate("2012-04-18"), null, 
					DayWrapper.makeDay(
						0, 
						PlainDoseWrapper.makeDose(new BigDecimal(1), new BigDecimal(2), true)))));
		Assert.assertEquals(
                "Dosering fra d. 18. apr. 2012 - gentages hver 7. dag:\n" +
                        "1-2 tabletter efter behov, højst 1 gang om ugen\n" +
                        "Bemærk: ved smerter",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
		Assert.assertEquals(DosageType.AccordingToNeed, DosisTilTekstWrapper.getDosageType(dosage));
	}	
	
	
	@Test /* Pure PN dosage */
	public void test1TabletEfterBehov() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("tablet"), 
				StructureWrapper.makeStructure(
					0, null, DateOrDateTimeWrapper.makeDate("2012-05-29"), null, 
					DayWrapper.makeDay(
						0, 
						PlainDoseWrapper.makeDose(new BigDecimal(1), true)))));
		Assert.assertEquals(
                "Dosering fra d. 29. maj 2012:\n" +
                        "1 tablet efter behov",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals("1 tablet efter behov", DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
		Assert.assertEquals(DosageType.AccordingToNeed, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test /* Pure PN dosage with max */
	public void test1TabletEfterBehovHoejstEnGangDaglig() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("tablet"), 
				StructureWrapper.makeStructure(
				1, null, DateOrDateTimeWrapper.makeDate("2012-05-29"), null, 
				DayWrapper.makeDay(
					1, 
					PlainDoseWrapper.makeDose(new BigDecimal(1), true)))));
        Assert.assertEquals(
                "Dosering fra d. 29. maj 2012:\n" +
                        "1 tablet efter behov, højst 1 gang dagligt",
                DosisTilTekstWrapper.convertLongText(dosage));
        Assert.assertEquals("1 tablet efter behov, højst 1 gang dagligt", DosisTilTekstWrapper.convertShortText(dosage));
        Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
		Assert.assertEquals(DosageType.AccordingToNeed, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test /* Dosage "2 stk efter behov højst 1 gang daglig", see https://jira.trifork.com/browse/FMK-784*/
	public void testJiraFMK784() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("stk"), 
				StructureWrapper.makeStructure(
					1, null, DateOrDateTimeWrapper.makeDateTime("2012-04-13 20:06:01"), null,
					DayWrapper.makeDay(
						1, 
						PlainDoseWrapper.makeDose(new BigDecimal(2), true)))));
		Assert.assertEquals(
                "Dosering fra d. 13. apr. 2012 kl. 20:06:01:\n" +
                        "2 stk efter behov, højst 1 gang dagligt",
                DosisTilTekstWrapper.convertLongText(dosage));
        Assert.assertEquals("SimpleLimitedAccordingToNeedConverterImpl", DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
        Assert.assertEquals("2 stk efter behov, højst 1 gang dagligt", DosisTilTekstWrapper.convertShortText(dosage));
        Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
		Assert.assertEquals(DosageType.AccordingToNeed, DosisTilTekstWrapper.getDosageType(dosage));
	}	
	
	@Test /* Dosage "2 stk efter behov højst 2 gange daglig", see https://jira.trifork.com/browse/FMK-784*/
	public void testJiraFMK784Variant() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("stk"), 
				StructureWrapper.makeStructure(
					1, null, DateOrDateTimeWrapper.makeDateTime("2012-04-13 20:06:00"), null, 
					DayWrapper.makeDay(
						1, 
						PlainDoseWrapper.makeDose(new BigDecimal(2), true), 
						PlainDoseWrapper.makeDose(new BigDecimal(2), true)))));
		Assert.assertEquals(
                "Dosering fra d. 13. apr. 2012 kl. 20:06:\n" +
                        "2 stk efter behov, højst 2 gange dagligt",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals("SimpleLimitedAccordingToNeedConverterImpl", DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals("2 stk efter behov, højst 2 gange daglig", DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
		Assert.assertEquals(DosageType.AccordingToNeed, DosisTilTekstWrapper.getDosageType(dosage));
	}

    @Test /* Jira FMK-784 */
    public void testSecondsAreNotIncluded() {
        DosageWrapper dosage = DosageWrapper.makeDosage(
                StructuresWrapper.makeStructures(
                        UnitOrUnitsWrapper.makeUnit("stk"),
                        StructureWrapper.makeStructure(
                                1, null, DateOrDateTimeWrapper.makeDateTime("2012-04-13 20:06:00"), null,
                                DayWrapper.makeDay(
                                        1,
                                        PlainDoseWrapper.makeDose(new BigDecimal(2), true),
                                        PlainDoseWrapper.makeDose(new BigDecimal(2), true)))));
        Assert.assertEquals(
                "Dosering fra d. 13. apr. 2012 kl. 20:06:\n" +
                        "2 stk efter behov, højst 2 gange dagligt",
                DosisTilTekstWrapper.convertLongText(dosage));
    }

    @Test /* Jira FMK-784 */
    public void testSecondsAreIncludedWhenProvided() {
        DosageWrapper dosage = DosageWrapper.makeDosage(
                StructuresWrapper.makeStructures(
                        UnitOrUnitsWrapper.makeUnit("stk"),
                        StructureWrapper.makeStructure(
                                1, null, DateOrDateTimeWrapper.makeDateTime("2012-04-13 20:06:10"), null,
                                DayWrapper.makeDay(
                                        1,
                                        PlainDoseWrapper.makeDose(new BigDecimal(2), true),
                                        PlainDoseWrapper.makeDose(new BigDecimal(2), true)))));
        Assert.assertEquals(
                "Dosering fra d. 13. apr. 2012 kl. 20:06:10:\n" +
                        "2 stk efter behov, højst 2 gange dagligt",
                DosisTilTekstWrapper.convertLongText(dosage));
    }
		
}
