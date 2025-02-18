package dk.medicinkortet.fmkdosistiltekstwrapper.nashorn;

import dk.medicinkortet.fmkdosistiltekstwrapper.*;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.DosisTilTekstWrapperNode;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.DosageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.net.http.HttpClient;
import java.util.Date;
import java.util.List;

public class DosisTilTekstWrapper {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // The DosisTilTekstWrapper class ought to be properly constructed, rather than have static methods, so e.g. the
    // HttpClient can be configured and lifetime-managed by the client code. But for now, we do it this way to keep
    // the client interface intact.
    private static DosisTilTekstService dosisTilTekstWrapper = null;

    public enum TextOptions {
        STANDARD,
        VKA
    }

    private static DosisTilTekstService getService() {
        //NOTE: shouldn't need synchronisation, as long as we only do this simple throw-or-return.
        if(dosisTilTekstWrapper == null) {
            throw new IllegalStateException("Attempt to use uninitialized DosisTilTekstWrapper");
        }
        return dosisTilTekstWrapper;
    }

    public static synchronized void initializeAndUseNode(String nodeServerBaseUrl) {
        //NOTE: method-level synchronised rather than double-checked locking should be fine, since
        // this is a seldom-called explicit initialisation, and not e.g. singleton initialisation
        // done in a getter.
        if(dosisTilTekstWrapper != null) {
            //NOTE: returning in this case prevents reinitialisation – we really shouldn't see reinitialisation in
            // production mode, and hopefully this won't cause issues in unit tests.
            logger.warn("Reinitializing DosisTilTekstWrapper - we should only see this during testing");
            return;
        }
        dosisTilTekstWrapper = new DosisTilTekstWrapperNode(HttpClient.newHttpClient(), nodeServerBaseUrl);
    }

    public static DosageProposalResult getDosageProposalResult(String type, String iteration, String mapping, String unitTextSingular, String unitTextPlural, String supplementaryText, List<Date> beginDates, List<Date> endDates, FMKVersion version, int dosageProposalVersion, Integer shortTextMaxLength) {
        return getService().getDosageProposalResult(type, iteration, mapping, unitTextSingular, unitTextPlural, supplementaryText, beginDates, endDates, version, dosageProposalVersion, shortTextMaxLength);
    }

    public static DosageProposalResult getDosageProposalResult(String type, String iteration, String mapping, String unitTextSingular, String unitTextPlural, String supplementaryText, List<Date> beginDates, List<Date> endDates, FMKVersion version, int dosageProposalVersion) {
        return getDosageProposalResult(type, iteration, mapping, unitTextSingular, unitTextPlural, supplementaryText, beginDates, endDates, version, dosageProposalVersion, null);
    }

    public static DosageTranslationCombined convertCombined(DosageWrapper dosage) {
        return convertCombined(dosage, DosisTilTekstWrapper.TextOptions.STANDARD);
    }

    public static DosageTranslationCombined convertCombined(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        return getService().convertCombined(dosage, options);
    }

    public static String convertLongText(DosageWrapper dosage) {
        return convertLongText(dosage, DosisTilTekstWrapper.TextOptions.STANDARD);
    }

    public static String convertLongText(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        return getService().convertLongText(dosage, options);
    }

    public static String convertShortText(DosageWrapper dosage) {
        return convertShortText(dosage, DosisTilTekstWrapper.TextOptions.STANDARD);
    }

    public static String convertShortText(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        return getService().convertShortText(dosage, options);
    }

    public static String convertShortText(DosageWrapper dosage, int maxLength) {
        return convertShortText(dosage, maxLength, DosisTilTekstWrapper.TextOptions.STANDARD);
    }

    public static String convertShortText(DosageWrapper dosage, int maxLength, DosisTilTekstWrapper.TextOptions options) {
        return getService().convertShortText(dosage, maxLength, options);
    }

    public static String getShortTextConverterClassName(DosageWrapper dosage) {
        return getService().getShortTextConverterClassName(dosage);
    }

    public static String getShortTextConverterClassName(DosageWrapper dosage, int maxLength) {
        return getService().getShortTextConverterClassName(dosage, maxLength);
    }

    public static String getLongTextConverterClassName(DosageWrapper dosage) {
        return getService().getLongTextConverterClassName(dosage);
    }

    public static DosageType getDosageType(DosageWrapper dosage) {
        return getService().getDosageType(dosage);
    }

    public static DosageType getDosageType144(DosageWrapper dosage) {
        return getService().getDosageType144(dosage);
    }

    public static DailyDosis calculateDailyDosis(DosageWrapper dosage) {
        return getService().calculateDailyDosis(dosage);
    }
}
