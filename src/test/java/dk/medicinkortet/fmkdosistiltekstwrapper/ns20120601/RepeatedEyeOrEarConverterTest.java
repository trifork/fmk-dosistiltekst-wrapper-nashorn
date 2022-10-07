package dk.medicinkortet.fmkdosistiltekstwrapper.ns20120601;

import dk.medicinkortet.fmkdosistiltekstwrapper.DosageType;
import dk.medicinkortet.fmkdosistiltekstwrapper.DosisTilTekstWrapperTestBase;
import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class RepeatedEyeOrEarConverterTest extends DosisTilTekstWrapperTestBase {

	@Test
	public void testOneInEachEyeTwice() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnits("dråbe", "dråber"), 
				StructureWrapper.makeStructure(
					1, "1 i hvert øje", DateOrDateTimeWrapper.makeDate("2011-01-01"), DateOrDateTimeWrapper.makeDate("2011-01-30"), 
					DayWrapper.makeDay(
						1, 
						PlainDoseWrapper.makeDose(new BigDecimal(2)), 
						PlainDoseWrapper.makeDose(new BigDecimal(2)))))); 
		Assert.assertEquals(
				"DailyRepeatedConverterImpl", 
				DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011 til d. 30. jan. 2011:\n" +
                        "2 dråber 2 gange hver dag\n" +
                        "Bemærk: 1 i hvert øje",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals(
			"RepeatedEyeOrEarConverterImpl", 
			DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals(
			"1 dråbe 2 gange daglig.\nBemærk: i begge øjne", 
			DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertEquals(
				4.0, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001); 			
		Assert.assertEquals(DosageType.Temporary, DosisTilTekstWrapper.getDosageType(dosage));
	}
}
