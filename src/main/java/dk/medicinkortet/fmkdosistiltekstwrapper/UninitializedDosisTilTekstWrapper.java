package dk.medicinkortet.fmkdosistiltekstwrapper;

import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.DosageWrapper;

import java.util.Date;
import java.util.List;

public class UninitializedDosisTilTekstWrapper implements DosisTilTekstService {
    private static final String UNINITIALIZED = "Attempt to use uninitialized DosisTilTekstWrapper";

    @Override
    public DosageProposalResult getDosageProposalResult(String type, String iteration, String mapping, String unitTextSingular, String unitTextPlural, String supplementaryText, List<Date> beginDates, List<Date> endDates, FMKVersion version, int dosageProposalVersion, Integer shortTextMaxLength) {
        throw new RuntimeException(UNINITIALIZED);
    }

    @Override
    public DosageTranslationCombined convertCombined(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        throw new RuntimeException(UNINITIALIZED);
    }

    @Override
    public String convertLongText(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        throw new RuntimeException(UNINITIALIZED);
    }

    @Override
    public String convertShortText(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        throw new RuntimeException(UNINITIALIZED);
    }

    @Override
    public String convertShortText(DosageWrapper dosage, Integer maxLength, DosisTilTekstWrapper.TextOptions options) {
        throw new RuntimeException(UNINITIALIZED);
    }

    @Override
    public String getShortTextConverterClassName(DosageWrapper dosage) {
        throw new RuntimeException(UNINITIALIZED);
    }

    @Override
    public String getShortTextConverterClassName(DosageWrapper dosage, Integer maxLength) {
        throw new RuntimeException(UNINITIALIZED);
    }

    @Override
    public String getLongTextConverterClassName(DosageWrapper dosage) {
        throw new RuntimeException(UNINITIALIZED);
    }

    @Override
    public DosageType getDosageType(DosageWrapper dosage) {
        throw new RuntimeException(UNINITIALIZED);
    }

    @Override
    public DosageType getDosageType144(DosageWrapper dosage) {
        throw new RuntimeException(UNINITIALIZED);
    }

    @Override
    public DailyDosis calculateDailyDosis(DosageWrapper dosage) {
        throw new RuntimeException(UNINITIALIZED);
    }
}
