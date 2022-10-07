package dk.medicinkortet.fmkdosistiltekstwrapper.ns2009;

import dk.medicinkortet.fmkdosistiltekstwrapper.DosageType;
import dk.medicinkortet.fmkdosistiltekstwrapper.DosisTilTekstWrapperTestBase;
import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class NumberOfWholeWeeksConverterTest extends DosisTilTekstWrapperTestBase {

    @Test
    public void test1stkMorgen1Uge1UgesPause() {
        DosageWrapper dosage = DosageWrapper.makeDosage(
                StructuresWrapper.makeStructures(
                        UnitOrUnitsWrapper.makeUnit("stk"),
                        StructureWrapper.makeStructure(
                                14, "ved måltid", DateOrDateTimeWrapper.makeDateTime("2014-02-07 07:19:00"), null,
                                DayWrapper.makeDay(1, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(2, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(3, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(4, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(5, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(6, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(7, NoonDoseWrapper.makeDose(new BigDecimal(1)))
                        )
                ));

        Assert.assertEquals("Dosering fra d. 7. feb. 2014 kl. 7:19 - gentages hver 14. dag:\n" +
                "Dag 1: 1 stk middag\n" +
                "Dag 2: 1 stk middag\n" +
                "Dag 3: 1 stk middag\n" +
                "Dag 4: 1 stk middag\n" +
                "Dag 5: 1 stk middag\n" +
                "Dag 6: 1 stk middag\n" +
                "Dag 7: 1 stk middag\n" +
                "Bemærk: ved måltid", DosisTilTekstWrapper.convertLongText(dosage));
        Assert.assertNull(DosisTilTekstWrapper.convertShortText(dosage));
        Assert.assertEquals("1 stk middag daglig i en uge, herefter en uges pause.\nBemærk: ved måltid", DosisTilTekstWrapper.convertShortText(dosage, 100));
        Assert.assertEquals(
                0.5,
                DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
                0.000000001);
        Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
    }

    @Test
    public void test1stkMorgenOgAften1Uge1UgesPause_IngenKortTekst() {
        DosageWrapper dosage = DosageWrapper.makeDosage(
                StructuresWrapper.makeStructures(
                        UnitOrUnitsWrapper.makeUnit("stk"),
                        StructureWrapper.makeStructure(
                                14, null, DateOrDateTimeWrapper.makeDateTime("2014-02-07 07:19:00"), null,
                                DayWrapper.makeDay(1, NoonDoseWrapper.makeDose(new BigDecimal(1)), EveningDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(2, NoonDoseWrapper.makeDose(new BigDecimal(1)), EveningDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(3, NoonDoseWrapper.makeDose(new BigDecimal(1)), EveningDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(4, NoonDoseWrapper.makeDose(new BigDecimal(1)), EveningDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(5, NoonDoseWrapper.makeDose(new BigDecimal(1)), EveningDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(6, NoonDoseWrapper.makeDose(new BigDecimal(1)), EveningDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(7, NoonDoseWrapper.makeDose(new BigDecimal(1)), EveningDoseWrapper.makeDose(new BigDecimal(1)))
                        )
                ));

        Assert.assertEquals("Dosering fra d. 7. feb. 2014 kl. 7:19 - gentages hver 14. dag:\n" +
                "Dag 1: 1 stk middag og 1 stk aften\n" +
                "Dag 2: 1 stk middag og 1 stk aften\n" +
                "Dag 3: 1 stk middag og 1 stk aften\n" +
                "Dag 4: 1 stk middag og 1 stk aften\n" +
                "Dag 5: 1 stk middag og 1 stk aften\n" +
                "Dag 6: 1 stk middag og 1 stk aften\n" +
                "Dag 7: 1 stk middag og 1 stk aften", DosisTilTekstWrapper.convertLongText(dosage));
        Assert.assertNull(DosisTilTekstWrapper.convertShortText(dosage));
        Assert.assertEquals(
                1.0,
                DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
                0.000000001);
        Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
    }

    @Test
    public void test1stk2GangeDaglig1Uge1UgesPause() {
        DosageWrapper dosage = DosageWrapper.makeDosage(
                StructuresWrapper.makeStructures(
                        UnitOrUnitsWrapper.makeUnit("stk"),
                        StructureWrapper.makeStructure(
                                14, null, DateOrDateTimeWrapper.makeDateTime("2014-02-07 07:19:00"), null,
                                DayWrapper.makeDay(1, PlainDoseWrapper.makeDose(new BigDecimal(1)), PlainDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(2, PlainDoseWrapper.makeDose(new BigDecimal(1)), PlainDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(3, PlainDoseWrapper.makeDose(new BigDecimal(1)), PlainDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(4, PlainDoseWrapper.makeDose(new BigDecimal(1)), PlainDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(5, PlainDoseWrapper.makeDose(new BigDecimal(1)), PlainDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(6, PlainDoseWrapper.makeDose(new BigDecimal(1)), PlainDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(7, PlainDoseWrapper.makeDose(new BigDecimal(1)), PlainDoseWrapper.makeDose(new BigDecimal(1)))
                        )
                ));

        Assert.assertEquals("Dosering fra d. 7. feb. 2014 kl. 7:19 - gentages hver 14. dag:\n" +
                "Dag 1: 1 stk 2 gange\n" +
                "Dag 2: 1 stk 2 gange\n" +
                "Dag 3: 1 stk 2 gange\n" +
                "Dag 4: 1 stk 2 gange\n" +
                "Dag 5: 1 stk 2 gange\n" +
                "Dag 6: 1 stk 2 gange\n" +
                "Dag 7: 1 stk 2 gange", DosisTilTekstWrapper.convertLongText(dosage));
        Assert.assertEquals("1 stk 2 gange daglig i en uge, herefter en uges pause", DosisTilTekstWrapper.convertShortText(dosage));
        Assert.assertEquals(
                1.0,
                DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
                0.000000001);
        Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
    }

    @Test
    public void test1stkMorgen1Uge5UgersPause() {
        DosageWrapper dosage = DosageWrapper.makeDosage(
                StructuresWrapper.makeStructures(
                        UnitOrUnitsWrapper.makeUnit("stk"),
                        StructureWrapper.makeStructure(
                                42, null, DateOrDateTimeWrapper.makeDateTime("2014-02-07 07:19:00"), null,
                                DayWrapper.makeDay(1, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(2, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(3, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(4, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(5, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(6, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(7, NoonDoseWrapper.makeDose(new BigDecimal(1)))
                        )
                ));

        Assert.assertEquals("Dosering fra d. 7. feb. 2014 kl. 7:19 - gentages hver 42. dag:\n" +
                "Dag 1: 1 stk middag\n" +
                "Dag 2: 1 stk middag\n" +
                "Dag 3: 1 stk middag\n" +
                "Dag 4: 1 stk middag\n" +
                "Dag 5: 1 stk middag\n" +
                "Dag 6: 1 stk middag\n" +
                "Dag 7: 1 stk middag", DosisTilTekstWrapper.convertLongText(dosage));
        Assert.assertEquals("1 stk middag daglig i en uge, herefter 5 ugers pause", DosisTilTekstWrapper.convertShortText(dosage));
        Assert.assertEquals(
                0.166666667,
                DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
                0.000000001);
        Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
    }

    @Test
    public void test1stkMorgen2Uge4UgersPause() {
        DosageWrapper dosage = DosageWrapper.makeDosage(
                StructuresWrapper.makeStructures(
                        UnitOrUnitsWrapper.makeUnit("stk"),
                        StructureWrapper.makeStructure(
                                42, null, DateOrDateTimeWrapper.makeDateTime("2014-02-07 07:19:00"), null,
                                DayWrapper.makeDay(1, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(2, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(3, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(4, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(5, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(6, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(7, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(8, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(9, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(10, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(11, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(12, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(13, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(14, NoonDoseWrapper.makeDose(new BigDecimal(1)))
                        )
                ));

        Assert.assertEquals("Dosering fra d. 7. feb. 2014 kl. 7:19 - gentages hver 42. dag:\n" +
                "Dag 1: 1 stk middag\n" +
                "Dag 2: 1 stk middag\n" +
                "Dag 3: 1 stk middag\n" +
                "Dag 4: 1 stk middag\n" +
                "Dag 5: 1 stk middag\n" +
                "Dag 6: 1 stk middag\n" +
                "Dag 7: 1 stk middag\n" +
                "Dag 8: 1 stk middag\n" +
                "Dag 9: 1 stk middag\n" +
                "Dag 10: 1 stk middag\n" +
                "Dag 11: 1 stk middag\n" +
                "Dag 12: 1 stk middag\n" +
                "Dag 13: 1 stk middag\n" +
                "Dag 14: 1 stk middag", DosisTilTekstWrapper.convertLongText(dosage));
        Assert.assertEquals("1 stk middag daglig i 2 uger, herefter 4 ugers pause", DosisTilTekstWrapper.convertShortText(dosage));
        Assert.assertEquals(
                0.333333333,
                DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
                0.000000001);
        Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
    }

    @Test
    public void test1stkMorgen2UgeIngenPause() {
        // This is really abuse og a bug structure
        // Equivalent with one day and iteration=1.
        DosageWrapper dosage = DosageWrapper.makeDosage(
                StructuresWrapper.makeStructures(
                        UnitOrUnitsWrapper.makeUnit("stk"),
                        StructureWrapper.makeStructure(
                                14, null, DateOrDateTimeWrapper.makeDateTime("2014-02-07 07:19:00"), null,
                                DayWrapper.makeDay(1, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(2, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(3, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(4, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(5, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(6, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(7, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(8, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(9, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(10, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(11, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(12, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(13, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(14, NoonDoseWrapper.makeDose(new BigDecimal(1)))
                        )
                ));

        Assert.assertEquals("Dosering fra d. 7. feb. 2014 kl. 7:19 - gentages hver 14. dag:\n" +
                "Dag 1: 1 stk middag\n" +
                "Dag 2: 1 stk middag\n" +
                "Dag 3: 1 stk middag\n" +
                "Dag 4: 1 stk middag\n" +
                "Dag 5: 1 stk middag\n" +
                "Dag 6: 1 stk middag\n" +
                "Dag 7: 1 stk middag\n" +
                "Dag 8: 1 stk middag\n" +
                "Dag 9: 1 stk middag\n" +
                "Dag 10: 1 stk middag\n" +
                "Dag 11: 1 stk middag\n" +
                "Dag 12: 1 stk middag\n" +
                "Dag 13: 1 stk middag\n" +
                "Dag 14: 1 stk middag", DosisTilTekstWrapper.convertLongText(dosage));
        Assert.assertEquals("1 stk middag daglig", DosisTilTekstWrapper.convertShortText(dosage));
        Assert.assertEquals(
                1.0,
                DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
                0.000000001);
        Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
    }

    @Test
    public void test1stkMorgen3dageHver6uger() {
        DosageWrapper dosage = DosageWrapper.makeDosage(
                StructuresWrapper.makeStructures(
                        UnitOrUnitsWrapper.makeUnit("stk"),
                        StructureWrapper.makeStructure(
                                42, null, DateOrDateTimeWrapper.makeDateTime("2014-02-07 07:19:00"), null,
                                DayWrapper.makeDay(1, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(2, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(3, NoonDoseWrapper.makeDose(new BigDecimal(1)))
                        )
                ));

        Assert.assertEquals("Dosering fra d. 7. feb. 2014 kl. 7:19 - gentages hver 42. dag:\n" +
                "Dag 1: 1 stk middag\n" +
                "Dag 2: 1 stk middag\n" +
                "Dag 3: 1 stk middag", DosisTilTekstWrapper.convertLongText(dosage));
        Assert.assertEquals("1 stk middag daglig i 3 dage, herefter 39 dages pause", DosisTilTekstWrapper.convertShortText(dosage));
        Assert.assertEquals(
                0.071428571,
                DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
                0.000000001);
        Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
    }

    @Test
    public void test1stkMorgenDag135Hver6uger_IngenKortTekst() {
        DosageWrapper dosage = DosageWrapper.makeDosage(
                StructuresWrapper.makeStructures(
                        UnitOrUnitsWrapper.makeUnit("stk"),
                        StructureWrapper.makeStructure(
                                42, null, DateOrDateTimeWrapper.makeDateTime("2014-02-07 07:19:00"), null,
                                DayWrapper.makeDay(1, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(3, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(5, NoonDoseWrapper.makeDose(new BigDecimal(1)))
                        )
                ));

        Assert.assertEquals("Dosering fra d. 7. feb. 2014 kl. 7:19 - gentages hver 42. dag:\n" +
                "Dag 1: 1 stk middag\n" +
                "Dag 3: 1 stk middag\n" +
                "Dag 5: 1 stk middag", DosisTilTekstWrapper.convertLongText(dosage));
        Assert.assertNull(DosisTilTekstWrapper.convertShortText(dosage));
        Assert.assertEquals(
                0.071428571,
                DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
                0.000000001);
        Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
    }

    @Test
    public void test1stkMorgenDag246Hver6uger_IngenKortTekst() {
        DosageWrapper dosage = DosageWrapper.makeDosage(
                StructuresWrapper.makeStructures(
                        UnitOrUnitsWrapper.makeUnit("stk"),
                        StructureWrapper.makeStructure(
                                42, null, DateOrDateTimeWrapper.makeDateTime("2014-02-07 07:19:00"), null,
                                DayWrapper.makeDay(2, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(4, NoonDoseWrapper.makeDose(new BigDecimal(1))),
                                DayWrapper.makeDay(6, NoonDoseWrapper.makeDose(new BigDecimal(1)))
                        )
                ));

        Assert.assertEquals("Dosering fra d. 7. feb. 2014 kl. 7:19 - gentages hver 42. dag:\n" +
                "Dag 2: 1 stk middag\n" +
                "Dag 4: 1 stk middag\n" +
                "Dag 6: 1 stk middag", DosisTilTekstWrapper.convertLongText(dosage));
        Assert.assertNull(DosisTilTekstWrapper.convertShortText(dosage));
        Assert.assertEquals(
                0.071428571,
                DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue().doubleValue(),
                0.000000001);
        Assert.assertEquals(DosageType.Fixed, DosisTilTekstWrapper.getDosageType(dosage));
    }

}
