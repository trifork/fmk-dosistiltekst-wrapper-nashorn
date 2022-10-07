package dk.medicinkortet.fmkdosistiltekstwrapper.ns20120601;

import dk.medicinkortet.fmkdosistiltekstwrapper.DosisTilTekstWrapperTestBase;
import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class DayInWeekConverterTest extends DosisTilTekstWrapperTestBase {
	
	// FMK-3273
	@Test
	public void testSupplText() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnits("tablet", "tabletter"), 
				StructureWrapper.makeStructure(
					14, 
					"ved måltid", 
					DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-30"), 
					DayWrapper.makeDay(
						3, MorningDoseWrapper.makeDose(new BigDecimal(1))),
					DayWrapper.makeDay(
						10, MorningDoseWrapper.makeDose(new BigDecimal(1))))
				));
		
		Assert.assertEquals(
				"DayInWeekConverterImpl", 
				DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals(
				"1 tablet morgen daglig mandag.\nBemærk: ved måltid", 
				DosisTilTekstWrapper.convertShortText(dosage));
		
		Assert.assertEquals(
				"DefaultLongTextConverterImpl", 
				DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 30. jan. 2011 - gentages hver 14. dag:\n" +
                        "Dag 3: 1 tablet morgen\n" +
                        "Dag 10: 1 tablet morgen\n" +
                        "Bemærk: ved måltid",
                DosisTilTekstWrapper.convertLongText(dosage));
	}
	
	// FMK-3273
		@Test
		public void testSupplTextWithPause() throws Exception {
			DosageWrapper dosage = DosageWrapper.makeDosage(
				StructuresWrapper.makeStructures(
					UnitOrUnitsWrapper.makeUnits("tablet", "tabletter"), 
					StructureWrapper.makeStructure(
						14, 
						"ved måltid", 
						DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-30"), 
						DayWrapper.makeDay(
							3, MorningDoseWrapper.makeDose(new BigDecimal(1))),
						DayWrapper.makeDay(
							10, MorningDoseWrapper.makeDose(new BigDecimal(1))))
					));
			
			Assert.assertEquals(
					"DayInWeekConverterImpl", 
					DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
			Assert.assertEquals(
					"1 tablet morgen daglig mandag.\nBemærk: ved måltid", 
					DosisTilTekstWrapper.convertShortText(dosage));
			
			Assert.assertEquals(
					"DefaultLongTextConverterImpl", 
					DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
			Assert.assertEquals(
                    "Dosering fra d. 1. jan. 2011 til d. 30. jan. 2011 - gentages hver 14. dag:\n" +
                            "Dag 3: 1 tablet morgen\n" +
                            "Dag 10: 1 tablet morgen\n" +
                            "Bemærk: ved måltid",
                    DosisTilTekstWrapper.convertLongText(dosage));
		}

}
