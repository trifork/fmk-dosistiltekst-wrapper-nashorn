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

public class MorningNoonEveningNightInNDaysConverterTest extends DosisTilTekstWrapperTestBase {
	
	@Test
	public void test1TabletMorgenI5Dage() throws Exception {
		DosageWrapper dosage = DosageWrapper.makeDosage(
			StructuresWrapper.makeStructures(
				UnitOrUnitsWrapper.makeUnit("tabletter"), 
				StructureWrapper.makeStructure(
					0, "ved måltid", DateOrDateTimeWrapper.makeDate("2011-01-01"), null, 
					DayWrapper.makeDay(
						1, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))),
					DayWrapper.makeDay(
						2, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))),
					DayWrapper.makeDay(
						3, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))),
					DayWrapper.makeDay(
						4, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))),
					DayWrapper.makeDay(
						5, 
						MorningDoseWrapper.makeDose(new BigDecimal(1))))));				
		Assert.assertEquals(
				"DefaultLongTextConverterImpl", 
				DosisTilTekstWrapper.getLongTextConverterClassName(dosage));
		Assert.assertEquals(
                "Dosering fra d. 1. jan. 2011:\n" +
                        "Lørdag d. 1. jan. 2011: 1 tablet morgen\n" +
                        "Søndag d. 2. jan. 2011: 1 tablet morgen\n" +
                        "Mandag d. 3. jan. 2011: 1 tablet morgen\n" +
                        "Tirsdag d. 4. jan. 2011: 1 tablet morgen\n" +
                        "Onsdag d. 5. jan. 2011: 1 tablet morgen\n" +
                        "Bemærk: ved måltid",
                DosisTilTekstWrapper.convertLongText(dosage));
		Assert.assertEquals(
				"MorningNoonEveningNightInNDaysConverterImpl", 
				DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
		Assert.assertEquals(
				"1 tablet morgen i 5 dage.\nBemærk: ved måltid", 
				DosisTilTekstWrapper.convertShortText(dosage));
		Assert.assertEquals(
				1.0, 
				DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
				0.000000001);
		Assert.assertEquals(DosageType.OneTime, DosisTilTekstWrapper.getDosageType(dosage));
	}
		
}
