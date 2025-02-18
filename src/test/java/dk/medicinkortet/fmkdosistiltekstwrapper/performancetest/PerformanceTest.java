package dk.medicinkortet.fmkdosistiltekstwrapper.performancetest;

import dk.medicinkortet.fmkdosistiltekstwrapper.DosageProposalResult;
import dk.medicinkortet.fmkdosistiltekstwrapper.DosageType;
import dk.medicinkortet.fmkdosistiltekstwrapper.FMKVersion;
import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Ignore
public class PerformanceTest {
    private final int REPETITIONS = 40;
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setUp() {

    }

    @Test
    public void testWithNode() throws ParseException {
        DosisTilTekstWrapper.initializeAndUseNode("http://localhost:8000/");
        overallTest();
    }

    private void overallTest() throws ParseException {
        for (int i = 0; i < REPETITIONS; i++) {
            testMultiPeriode();
            testMarevan14DagesSkema1_2Tablet();
            test1stkMorgen2Uge4UgersPause();
            testCombinedDifferentSupplText();
            testTwoFollwingPeriodesWithOverlappingPN();
        }
    }

    private void testMultiPeriode() throws ParseException {

        DosageProposalResult res = DosisTilTekstWrapper.getDosageProposalResult("{M+M+A+N}{PN}{N daglig}", "{1}{2}{1}",
                "{1+2+3+4}{dag 1: 2 dag 2: 3}{2}", "tablet", "tabletter", "tages med rigeligt vand",
                Arrays.asList(SIMPLE_DATE_FORMAT.parse("2010-01-01"), SIMPLE_DATE_FORMAT.parse("2010-02-01"), SIMPLE_DATE_FORMAT.parse("2010-03-01")),
                Arrays.asList(SIMPLE_DATE_FORMAT.parse("2010-01-31"), SIMPLE_DATE_FORMAT.parse("2010-02-28"), SIMPLE_DATE_FORMAT.parse("2010-03-31")),
                FMKVersion.FMK146, 1);
        assertNotNull(res);
        assertNotNull(res.getLongText());
        assertEquals("<m16:Dosage xsi:schemaLocation=\"http://www.dkma.dk/medicinecard/xml.schema/2015/06/01 ../../../2015/06/01/DosageForRequest.xsd\" xmlns:m16=\"http://www.dkma.dk/medicinecard/xml.schema/2015/06/01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                "<m16:UnitTexts source=\"Doseringsforslag\">" +
                "<m16:Singular>tablet</m16:Singular>" +
                "<m16:Plural>tabletter</m16:Plural>" +
                "</m16:UnitTexts>" +
                "<m16:StructuresFixed>" +
                "<m16:Structure>" +
                "<m16:IterationInterval>1</m16:IterationInterval>" +
                "<m16:StartDate>2010-01-01</m16:StartDate>" +
                "<m16:EndDate>2010-01-31</m16:EndDate>" +
                "<m16:SupplementaryText>tages med rigeligt vand</m16:SupplementaryText>" +
                "<m16:Day>" +
                "<m16:Number>1</m16:Number>" +
                "<m16:Dose>" +
                "<m16:Time>morning</m16:Time>" +
                "<m16:Quantity>1</m16:Quantity>" +
                "</m16:Dose>" +
                "<m16:Dose>" +
                "<m16:Time>noon</m16:Time>" +
                "<m16:Quantity>2</m16:Quantity>" +
                "</m16:Dose>" +
                "<m16:Dose>" +
                "<m16:Time>evening</m16:Time>" +
                "<m16:Quantity>3</m16:Quantity>" +
                "</m16:Dose>" +
                "<m16:Dose>" +
                "<m16:Time>night</m16:Time>" +
                "<m16:Quantity>4</m16:Quantity>" +
                "</m16:Dose>" +
                "</m16:Day>" +
                "</m16:Structure>" +
                "<m16:Structure>" +
                "<m16:IterationInterval>1</m16:IterationInterval>" +
                "<m16:StartDate>2010-03-01</m16:StartDate>" +
                "<m16:EndDate>2010-03-31</m16:EndDate>" +
                "<m16:SupplementaryText>tages med rigeligt vand</m16:SupplementaryText>" +
                "<m16:Day>" +
                "<m16:Number>1</m16:Number>" +
                "<m16:Dose><m16:Quantity>2</m16:Quantity></m16:Dose>" +
                "</m16:Day>" +
                "</m16:Structure>" +
                "</m16:StructuresFixed>" +
                "<m16:StructuresAccordingToNeed>" +
                "<m16:Structure>" +
                "<m16:IterationInterval>2</m16:IterationInterval>" +
                "<m16:StartDate>2010-02-01</m16:StartDate>" +
                "<m16:EndDate>2010-02-28</m16:EndDate>" +
                "<m16:SupplementaryText>tages med rigeligt vand</m16:SupplementaryText>" +
                "<m16:Day>" +
                "<m16:Number>1</m16:Number>" +
                "<m16:Dose><m16:Quantity>2</m16:Quantity></m16:Dose>" +
                "</m16:Day>" +
                "<m16:Day>" +
                "<m16:Number>2</m16:Number>" +
                "<m16:Dose><m16:Quantity>3</m16:Quantity></m16:Dose>" +
                "</m16:Day>" +
                "</m16:Structure>" +
                "</m16:StructuresAccordingToNeed>" +
                "</m16:Dosage>", res.getXmlSnippet());

        assertEquals("Dosering fra d. 1. jan. 2010 til d. 31. jan. 2010:\n" +
                "1 tablet morgen, 2 tabletter middag, 3 tabletter aften og 4 tabletter nat - hver dag\n" +
                "Bemærk: tages med rigeligt vand\n" +
                "\n" +
                "Dosering fra d. 1. feb. 2010 til d. 28. feb. 2010 - gentages hver 2. dag:\n" +
                "Dag 1: 2 tabletter efter behov, højst 1 gang dagligt\n" +
                "Dag 2: 3 tabletter efter behov, højst 1 gang dagligt\n" +
                "Bemærk: tages med rigeligt vand\n" +
                "\n" +
                "Dosering fra d. 1. mar. 2010 til d. 31. mar. 2010:\n" +
                "2 tabletter hver dag\n" +
                "Bemærk: tages med rigeligt vand", res.getLongText());
    }

    private void testMarevan14DagesSkema1_2Tablet() {
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

    private void test1stkMorgen2Uge4UgersPause() {
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

    private void testCombinedDifferentSupplText() {
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
                "2 dråber morgen og aften i 3 dage, herefter 1 dråbe morgen og aften.\n" +
                        "Bemærk: tages med rigeligt vand.\n" +
                        "Bemærk: ved måltid",
                DosisTilTekstWrapper.convertShortText(dosage, 200));
        Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
        Assert.assertEquals(DosageType.Combined, DosisTilTekstWrapper.getDosageType(dosage));
    }

    private void testTwoFollwingPeriodesWithOverlappingPN() {
        DosageWrapper dosage = DosageWrapper.makeDosage(
                StructuresWrapper.makeStructures(
                        UnitOrUnitsWrapper.makeUnits("tablet", "tabletter"),
                        StructureWrapper.makeStructure(
                                0, null, DateOrDateTimeWrapper.makeDate("2013-06-01"), DateOrDateTimeWrapper.makeDate("2013-06-3"),
                                DayWrapper.makeDay(
                                        1,
                                        MorningDoseWrapper.makeDose(new BigDecimal(2)),
                                        NoonDoseWrapper.makeDose(new BigDecimal(2)),
                                        EveningDoseWrapper.makeDose(new BigDecimal(2))),
                                DayWrapper.makeDay(
                                        2,
                                        MorningDoseWrapper.makeDose(new BigDecimal(2)),
                                        EveningDoseWrapper.makeDose(new BigDecimal(2))),
                                DayWrapper.makeDay(
                                        3,
                                        MorningDoseWrapper.makeDose(new BigDecimal(2)))),


                        StructureWrapper.makeStructure(
                                1, null, DateOrDateTimeWrapper.makeDate("2013-06-04"), null,
                                DayWrapper.makeDay(
                                        1,
                                        MorningDoseWrapper.makeDose(new BigDecimal(1)))),


                        StructureWrapper.makeStructure(
                                0, "ved smerter", DateOrDateTimeWrapper.makeDate("2013-06-01"), null,
                                DayWrapper.makeDay(
                                        0,
                                        PlainDoseWrapper.makeDose(new BigDecimal(2), true))))

        );

        Assert.assertEquals(
                "Dosering fra d. 1. juni 2013 til d. 3. juni 2013:\n" +
                        "Lørdag d. 1. juni 2013: 2 tabletter morgen, 2 tabletter middag og 2 tabletter aften\n" +
                        "Søndag d. 2. juni 2013: 2 tabletter morgen og 2 tabletter aften\n" +
                        "Mandag d. 3. juni 2013: 2 tabletter morgen\n" +
                        "\n" +
                        "Dosering fra d. 1. juni 2013:\n" +
                        "2 tabletter efter behov\n" +
                        "Bemærk: ved smerter\n" +
                        "\n" +
                        "Dosering fra d. 4. juni 2013:\n" +
                        "1 tablet hver morgen",
                DosisTilTekstWrapper.convertLongText(dosage));
        Assert.assertNull(DosisTilTekstWrapper.getShortTextConverterClassName(dosage));
        Assert.assertNull(DosisTilTekstWrapper.calculateDailyDosis(dosage).getValue());
        Assert.assertEquals(DosageType.Combined, DosisTilTekstWrapper.getDosageType(dosage));
    }
}
