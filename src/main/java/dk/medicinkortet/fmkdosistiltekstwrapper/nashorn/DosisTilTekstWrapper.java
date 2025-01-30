package dk.medicinkortet.fmkdosistiltekstwrapper.nashorn;

import dk.medicinkortet.fmkdosistiltekstwrapper.*;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.DosisTilTekstWrapperNode;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.DosageWrapper;

import java.net.http.HttpClient;
import java.util.Date;
import java.util.List;

public class DosisTilTekstWrapper {
    // The DosisTilTekstWrapper class ought to be properly constructed, rather than have static methods, so e.g. the
    // HttpClient can be configured and lifetime-managed by the client code. But for now, we do it this way to keep
    // the client interface intact.
    private static DosisTilTekstService dosisTilTekstWrapper = new UninitializedDosisTilTekstWrapper();

    public enum TextOptions {
        STANDARD,
        VKA
    }

    public static void initializeAndUseNode(String nodeServerBaseUrl) {
        dosisTilTekstWrapper = new DosisTilTekstWrapperNode(HttpClient.newHttpClient(), nodeServerBaseUrl);
    }

    public static DosageProposalResult getDosageProposalResult(String type, String iteration, String mapping, String unitTextSingular, String unitTextPlural, String supplementaryText, List<Date> beginDates, List<Date> endDates, FMKVersion version, int dosageProposalVersion, Integer shortTextMaxLength) {
        return dosisTilTekstWrapper.getDosageProposalResult(type, iteration, mapping, unitTextSingular, unitTextPlural, supplementaryText, beginDates, endDates, version, dosageProposalVersion, shortTextMaxLength);
    }

    public static DosageProposalResult getDosageProposalResult(String type, String iteration, String mapping, String unitTextSingular, String unitTextPlural, String supplementaryText, List<Date> beginDates, List<Date> endDates, FMKVersion version, int dosageProposalVersion) {
        return getDosageProposalResult(type, iteration, mapping, unitTextSingular, unitTextPlural, supplementaryText, beginDates, endDates, version, dosageProposalVersion, null);
    }

    public static DosageTranslationCombined convertCombined(DosageWrapper dosage) {
        return convertCombined(dosage, DosisTilTekstWrapper.TextOptions.STANDARD);
    }

    public static DosageTranslationCombined convertCombined(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        return dosisTilTekstWrapper.convertCombined(dosage, options);
    }

    public static String convertLongText(DosageWrapper dosage) {
        return convertLongText(dosage, DosisTilTekstWrapper.TextOptions.STANDARD);
    }

    public static String convertLongText(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        return dosisTilTekstWrapper.convertLongText(dosage, options);
    }

    public static String convertShortText(DosageWrapper dosage) {
        return convertShortText(dosage, DosisTilTekstWrapper.TextOptions.STANDARD);
    }

    public static String convertShortText(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        return dosisTilTekstWrapper.convertShortText(dosage, options);
    }

    public static String convertShortText(DosageWrapper dosage, int maxLength) {
        return convertShortText(dosage, maxLength, DosisTilTekstWrapper.TextOptions.STANDARD);
    }

    public static String convertShortText(DosageWrapper dosage, int maxLength, DosisTilTekstWrapper.TextOptions options) {
        return dosisTilTekstWrapper.convertShortText(dosage, maxLength, options);
    }

    public static String getShortTextConverterClassName(DosageWrapper dosage) {
        return dosisTilTekstWrapper.getShortTextConverterClassName(dosage);
    }

    public static String getShortTextConverterClassName(DosageWrapper dosage, int maxLength) {
        return dosisTilTekstWrapper.getShortTextConverterClassName(dosage, maxLength);
    }

    public static String getLongTextConverterClassName(DosageWrapper dosage) {
        return dosisTilTekstWrapper.getLongTextConverterClassName(dosage);
    }

    public static DosageType getDosageType(DosageWrapper dosage) {
        return dosisTilTekstWrapper.getDosageType(dosage);
    }

    public static DosageType getDosageType144(DosageWrapper dosage) {
        return dosisTilTekstWrapper.getDosageType144(dosage);
    }

    public static DailyDosis calculateDailyDosis(DosageWrapper dosage) {
        return dosisTilTekstWrapper.calculateDailyDosis(dosage);
    }
}
