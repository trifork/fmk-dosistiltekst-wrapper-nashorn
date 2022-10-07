package dk.medicinkortet.fmkdosistiltekstwrapper.ns20120601;

import dk.medicinkortet.fmkdosistiltekstwrapper.DosageType;
import dk.medicinkortet.fmkdosistiltekstwrapper.DosisTilTekstWrapperTestBase;
import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class CombinedTwoPeriodesConverterTest extends DosisTilTekstWrapperTestBase {
	
	@Test
	public void testCombined1() {
		// 1 dråbe i hvert øje 4 gange 1. dag, derefter 1 dråbe 2 x daglig
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnits("dråbe", "dråber"), 
				StructureWrapper.makeStructure(
					0, 
					null, 
					DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-01"), 
					DayWrapper.makeDay(
						1, 
						PlainDoseWrapper.makeDose(new BigDecimal(1)), 
						PlainDoseWrapper.makeDose(new BigDecimal(1)), 
						PlainDoseWrapper.makeDose(new BigDecimal(1)), 
						PlainDoseWrapper.makeDose(new BigDecimal(1)))),
				StructureWrapper.makeStructure(
					1,
					null, 
					DateOrDateTimeWrapper.makeDate("2011-01-02"), null, 
					DayWrapper.makeDay(
						1, 
						PlainDoseWrapper.makeDose(new BigDecimal(1)), 
						PlainDoseWrapper.makeDose(new BigDecimal(1))))));
		Assert.assertEquals(
			"DefaultMultiPeriodeLongTextConverterImpl", 
			DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering kun d. 1. jan. 2011:\n" +
                        "1 dråbe 4 gange\n" +
                        "\n" +
                        "Dosering fra d. 2. jan. 2011:\n" +
                        "1 dråbe 2 gange hver dag",
                DosisTilTekstWrapper.convertLongText(dosage));
			Assert.assertEquals(
					"CombinedTwoPeriodesConverterImpl", 
					DosisTilTekstWrapper.getShortTextConverterClassName(dosage, 100));
			Assert.assertEquals(
					"Første dag 1 dråbe 4 gange, herefter 1 dråbe 2 gange daglig", 
					DosisTilTekstWrapper.convertShortText(dosage, 100));
			Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
			Assert.assertEquals(DosageType.Combined, DosisTilTekstWrapper.getDosageType(dosage));
	}

	@Test
	public void testCombinedWithTwoNotIteratedPeriods() {
		// 1 dråbe i hvert øje 4 gange 1. dag, derefter 1 dråbe 2 x daglig
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnits("dråbe", "dråber"), 
				StructureWrapper.makeStructure(
					0, 
					null, 
					DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-01"), 
					DayWrapper.makeDay(
						1, 
						PlainDoseWrapper.makeDose(new BigDecimal(1)), 
						PlainDoseWrapper.makeDose(new BigDecimal(1)), 
						PlainDoseWrapper.makeDose(new BigDecimal(1)), 
						PlainDoseWrapper.makeDose(new BigDecimal(1)))),
				StructureWrapper.makeStructure(
					1,
					null, 
					DateOrDateTimeWrapper.makeDate("2011-01-02"), null, 
					DayWrapper.makeDay(
						1, 
						PlainDoseWrapper.makeDose(new BigDecimal(1)), 
						PlainDoseWrapper.makeDose(new BigDecimal(1))))));
		Assert.assertEquals(
			"DefaultMultiPeriodeLongTextConverterImpl", 
			DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		/*Assert.assertEquals(
			"Doseringen indeholder flere perioder:\n"+
			"\n"+
			"Doseringen foretages kun lørdag den 1. januar 2011:\n"+
			"   Doseringsforløb:\n"+
			"   Lørdag den 1. januar 2011: 1 dråbe 4 gange\n"+
			"\n"+
			"Doseringsforløbet starter søndag den 2. januar 2011 og ophører efter det angivne forløb:\n"+
			"   Doseringsforløb:\n"+
			"   Søndag den 2. januar 2011: 1 dråbe 2 gange",
			DosisTilTekstWrapper.convertLongText(dosage));*/
			Assert.assertEquals(
					"CombinedTwoPeriodesConverterImpl", 
					DosisTilTekstWrapper.getShortTextConverterClassName(dosage, 100));
			String convertShortText = DosisTilTekstWrapper.convertShortText(dosage, 100);
			Assert.assertEquals(
					"Første dag 1 dråbe 4 gange, herefter 1 dråbe 2 gange daglig", 
					convertShortText);
			Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
			Assert.assertEquals(DosageType.Combined, DosisTilTekstWrapper.getDosageType(dosage));
	}

	
	@Test
	public void testCombined2() {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnits("dråbe", "dråber"), 
				StructureWrapper.makeStructure(
					0, 
					null, 
					DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-03"), 
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(2)), 
						EveningDoseWrapper.makeDose(new BigDecimal(2))),
					DayWrapper.makeDay(
						2, 
						MorningDoseWrapper.makeDose(new BigDecimal(2)), 
						EveningDoseWrapper.makeDose(new BigDecimal(2))),
					DayWrapper.makeDay(
						3, 
						MorningDoseWrapper.makeDose(new BigDecimal(2)), 
						EveningDoseWrapper.makeDose(new BigDecimal(2)))),
				StructureWrapper.makeStructure(
					1,
					null, 
					DateOrDateTimeWrapper.makeDate("2011-01-04"), null, 
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(1)), 
						EveningDoseWrapper.makeDose(new BigDecimal(1))))));
		Assert.assertEquals(
			"DefaultMultiPeriodeLongTextConverterImpl", 
			DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 3. jan. 2011:\n" +
                        "Lørdag d. 1. jan. 2011: 2 dråber morgen og 2 dråber aften\n" +
                        "Søndag d. 2. jan. 2011: 2 dråber morgen og 2 dråber aften\n" +
                        "Mandag d. 3. jan. 2011: 2 dråber morgen og 2 dråber aften\n" +
                        "\n" +
                        "Dosering fra d. 4. jan. 2011:\n" +
                        "1 dråbe morgen og 1 dråbe aften - hver dag",
                DosisTilTekstWrapper.convertLongText(dosage));
			Assert.assertEquals(
					"CombinedTwoPeriodesConverterImpl", 
					DosisTilTekstWrapper.getShortTextConverterClassName(dosage, 100));
			Assert.assertEquals(
					"2 dråber morgen og aften i 3 dage, herefter 1 dråbe morgen og aften", 
					DosisTilTekstWrapper.convertShortText(dosage, 100));
			Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
			Assert.assertEquals(DosageType.Combined, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test
	public void testCombinedSameSupplText() {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnits("dråbe", "dråber"), 
				StructureWrapper.makeStructure(
					0, 
					"tages med rigeligt vand", 
					DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-03"), 
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(2)), 
						EveningDoseWrapper.makeDose(new BigDecimal(2))),
					DayWrapper.makeDay(
						2, 
						MorningDoseWrapper.makeDose(new BigDecimal(2)), 
						EveningDoseWrapper.makeDose(new BigDecimal(2))),
					DayWrapper.makeDay(
						3, 
						MorningDoseWrapper.makeDose(new BigDecimal(2)), 
						EveningDoseWrapper.makeDose(new BigDecimal(2)))),
				StructureWrapper.makeStructure(
					1,
					"tages med rigeligt vand", 
					DateOrDateTimeWrapper.makeDate("2011-01-04"), null, 
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(1)), 
						EveningDoseWrapper.makeDose(new BigDecimal(1))))));
		Assert.assertEquals(
			"DefaultMultiPeriodeLongTextConverterImpl", 
			DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 3. jan. 2011:\n" +
                        "Lørdag d. 1. jan. 2011: 2 dråber morgen og 2 dråber aften\n" +
                        "Søndag d. 2. jan. 2011: 2 dråber morgen og 2 dråber aften\n" +
                        "Mandag d. 3. jan. 2011: 2 dråber morgen og 2 dråber aften\n" +
                        "Bemærk: tages med rigeligt vand\n" +
                        "\n" +
                        "Dosering fra d. 4. jan. 2011:\n" +
                        "1 dråbe morgen og 1 dråbe aften - hver dag\n" +
                        "Bemærk: tages med rigeligt vand",
                DosisTilTekstWrapper.convertLongText(dosage));
			Assert.assertEquals("CombinedTwoPeriodesConverterImpl", DosisTilTekstWrapper.getShortTextConverterClassName(dosage, 200));
			Assert.assertEquals(
					"2 dråber morgen og aften i 3 dage, herefter 1 dråbe morgen og aften.\nBemærk: tages med rigeligt vand", 
					DosisTilTekstWrapper.convertShortText(dosage, 200));
			Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
			Assert.assertEquals(DosageType.Combined, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
	@Test
	public void testCombinedDifferentSupplText() {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnits("dråbe", "dråber"), 
				StructureWrapper.makeStructure(
					0, 
					"tages med rigeligt vand", 
					DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-03"), 
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(2)), 
						EveningDoseWrapper.makeDose(new BigDecimal(2))),
					DayWrapper.makeDay(
						2, 
						MorningDoseWrapper.makeDose(new BigDecimal(2)), 
						EveningDoseWrapper.makeDose(new BigDecimal(2))),
					DayWrapper.makeDay(
						3, 
						MorningDoseWrapper.makeDose(new BigDecimal(2)), 
						EveningDoseWrapper.makeDose(new BigDecimal(2)))),
				StructureWrapper.makeStructure(
					1,
					"ved måltid", 
					DateOrDateTimeWrapper.makeDate("2011-01-04"), null, 
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(1)), 
						EveningDoseWrapper.makeDose(new BigDecimal(1))))));
		Assert.assertEquals(
			"DefaultMultiPeriodeLongTextConverterImpl", 
			DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 3. jan. 2011:\n" +
                        "Lørdag d. 1. jan. 2011: 2 dråber morgen og 2 dråber aften\n" +
                        "Søndag d. 2. jan. 2011: 2 dråber morgen og 2 dråber aften\n" +
                        "Mandag d. 3. jan. 2011: 2 dråber morgen og 2 dråber aften\n" +
                        "Bemærk: tages med rigeligt vand\n" +
                        "\n" +
                        "Dosering fra d. 4. jan. 2011:\n" +
                        "1 dråbe morgen og 1 dråbe aften - hver dag\n" +
                        "Bemærk: ved måltid",
                DosisTilTekstWrapper.convertLongText(dosage));

			Assert.assertEquals(
					"2 dråber morgen og aften i 3 dage, herefter 1 dråbe morgen og aften.\n"+
							"Bemærk: tages med rigeligt vand.\n"+
							"Bemærk: ved måltid",
					DosisTilTekstWrapper.convertShortText(dosage, 200));
			Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
			Assert.assertEquals(DosageType.Combined, DosisTilTekstWrapper.getDosageType(dosage));
	}
	

	
}
