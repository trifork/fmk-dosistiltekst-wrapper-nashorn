package dk.medicinkortet.fmkdosistiltekstwrapper.ns2009;

import dk.medicinkortet.fmkdosistiltekstwrapper.DosageType;
import dk.medicinkortet.fmkdosistiltekstwrapper.DosisTilTekstWrapperTestBase;
import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class MultipleDaysNonRepeatedConverterImplTest  extends DosisTilTekstWrapperTestBase  {

	@Test
	public void test1TabletMorgenDag1Dag3() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("tablet"), 
				StructureWrapper.makeStructure(
					0, "ved måltid", DateOrDateTimeWrapper.makeDate("2011-01-01"), null, 
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))),
					DayWrapper.makeDay(
						3, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))))));
		
		Assert.assertEquals(
				"MultipleDaysNonRepeatedConverterImpl", 
				DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals(
				"1 tablet morgen dag 1 og 3.\nBemærk: ved måltid", 
				DosisTilTekstWrapper.convertShortText(dosage));
		
		Assert.assertEquals(
				"DefaultLongTextConverterImpl", 
				DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011:\n" +
                        "Lørdag d. 1. jan. 2011: 1 tablet morgen\n" +
                        "Mandag d. 3. jan. 2011: 1 tablet morgen\n" +
                        "Bemærk: ved måltid",
                DosisTilTekstWrapper.convertLongText(dosage));
		
		Assert.assertEquals(
				2.0/3.0, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001);
		Assert.assertEquals(DosageType.OneTime, DosisTilTekstWrapper.getDosageType(dosage));
	}
	
}
