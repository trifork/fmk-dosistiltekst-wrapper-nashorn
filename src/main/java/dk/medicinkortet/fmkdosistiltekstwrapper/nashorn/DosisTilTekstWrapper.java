package dk.medicinkortet.fmkdosistiltekstwrapper.nashorn;

import dk.medicinkortet.fmkdosistiltekstwrapper.*;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.DosisTilTekstWrapperNode;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.DosageWrapper;

import javax.script.ScriptException;
import java.io.Reader;
import java.util.Date;
import java.util.List;

public class DosisTilTekstWrapper {
    private static boolean usesNashorn = true;

    public enum TextOptions {
        STANDARD,
        VKA
    }

    public static void initializeAndUseNode(String nodeServerBaseUrl) {
        usesNashorn = false;
        DosisTilTekstWrapperNode.initialize(nodeServerBaseUrl);
    }

    public static void initializeNashorn(Reader javascriptFileReader) throws ScriptException {
        DosisTilTekstWrapperNashorn.initialize(javascriptFileReader);
    }

    public static DosageProposalResult getDosageProposalResult(String type, String iteration, String mapping, String unitTextSingular, String unitTextPlural, String supplementaryText, List<Date> beginDates, List<Date> endDates, FMKVersion version, int dosageProposalVersion, Integer shortTextMaxLength) {
        if (!usesNashorn) {
            try {
                return DosisTilTekstWrapperNode.getDosageProposalResult(type, iteration, mapping, unitTextSingular, unitTextPlural, supplementaryText, beginDates, endDates, version, dosageProposalVersion, shortTextMaxLength);
            } catch (RuntimeException ignored) {
            }
        }
        return DosisTilTekstWrapperNashorn.getDosageProposalResult(type, iteration, mapping, unitTextSingular, unitTextPlural, supplementaryText, beginDates, endDates, version, dosageProposalVersion, shortTextMaxLength);
    }

    public static DosageProposalResult getDosageProposalResult(String type, String iteration, String mapping, String unitTextSingular, String unitTextPlural, String supplementaryText, List<Date> beginDates, List<Date> endDates, FMKVersion version, int dosageProposalVersion) {
        return getDosageProposalResult(type, iteration, mapping, unitTextSingular, unitTextPlural, supplementaryText, beginDates, endDates, version, dosageProposalVersion, null);
    }

    public static DosageTranslationCombined convertCombined(DosageWrapper dosage) {
        return convertCombined(dosage, DosisTilTekstWrapper.TextOptions.STANDARD);
    }

    public static DosageTranslationCombined convertCombined(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        if (!usesNashorn) {
            try {
                return DosisTilTekstWrapperNode.convertCombined(dosage, options);
            } catch (RuntimeException ignored) {
            }
        }
        return DosisTilTekstWrapperNashorn.convertCombined(dosage, options);
    }

    public static String convertLongText(DosageWrapper dosage) {
        return convertLongText(dosage, DosisTilTekstWrapper.TextOptions.STANDARD);
    }

    public static String convertLongText(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        if (!usesNashorn) {
            try {
                return DosisTilTekstWrapperNode.convertLongText(dosage, options);
            } catch (RuntimeException ignored) {
            }
        }
        return DosisTilTekstWrapperNashorn.convertLongText(dosage, options);
    }

    public static String convertShortText(DosageWrapper dosage) {
        return convertShortText(dosage, DosisTilTekstWrapper.TextOptions.STANDARD);
    }

    public static String convertShortText(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        if (!usesNashorn) {
            try {
                return DosisTilTekstWrapperNode.convertShortText(dosage, options);
            } catch (RuntimeException ignored) {
            }
        }
        return DosisTilTekstWrapperNashorn.convertShortText(dosage, options);
    }

    public static String convertShortText(DosageWrapper dosage, int maxLength) {
        return convertShortText(dosage, maxLength, DosisTilTekstWrapper.TextOptions.STANDARD);
    }

    public static String convertShortText(DosageWrapper dosage, int maxLength, DosisTilTekstWrapper.TextOptions options) {
        if (!usesNashorn) {
            try {
                return DosisTilTekstWrapperNode.convertShortText(dosage, maxLength, options);
            } catch (RuntimeException ignored) {
            }
        }
        return DosisTilTekstWrapperNashorn.convertShortText(dosage, maxLength, options);
    }

    public static String getShortTextConverterClassName(DosageWrapper dosage) {
        if (!usesNashorn) {
            try {
                return DosisTilTekstWrapperNode.getShortTextConverterClassName(dosage);
            } catch (RuntimeException ignored) {
            }
        }
        return DosisTilTekstWrapperNashorn.getShortTextConverterClassName(dosage);
    }

    public static String getShortTextConverterClassName(DosageWrapper dosage, int maxLength) {
        if (!usesNashorn) {
            try {
                return DosisTilTekstWrapperNode.getShortTextConverterClassName(dosage, maxLength);
            } catch (RuntimeException ignored) {
            }
        }
        return DosisTilTekstWrapperNashorn.getShortTextConverterClassName(dosage, maxLength);
    }


    public static String getLongTextConverterClassName(DosageWrapper dosage) {
        if (!usesNashorn) {
            try {
                return DosisTilTekstWrapperNode.getLongTextConverterClassName(dosage);
            } catch (RuntimeException ignored) {
            }
        }
        return DosisTilTekstWrapperNashorn.getLongTextConverterClassName(dosage);
    }

    public static DosageType getDosageType(DosageWrapper dosage) {
        if (!usesNashorn) {
            try {
                return DosisTilTekstWrapperNode.getDosageType(dosage);
            } catch (RuntimeException ignored) {
            }
        }
        return DosisTilTekstWrapperNashorn.getDosageType(dosage);
    }

    public static DosageType getDosageType144(DosageWrapper dosage) {
        if (!usesNashorn) {
            try {
                return DosisTilTekstWrapperNode.getDosageType144(dosage);
            } catch (RuntimeException ignored) {
            }
        }
        return DosisTilTekstWrapperNashorn.getDosageType144(dosage);
    }

    public static DailyDosis calculateDailyDosis(DosageWrapper dosage) {
        if (!usesNashorn) {
            try {
                return DosisTilTekstWrapperNode.calculateDailyDosis(dosage);
            } catch (RuntimeException ignored) {
            }
        }
        return DosisTilTekstWrapperNashorn.calculateDailyDosis(dosage);
    }

}
