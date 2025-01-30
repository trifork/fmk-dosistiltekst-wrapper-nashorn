package dk.medicinkortet.fmkdosistiltekstwrapper;

import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.DosageWrapper;

import java.util.Date;
import java.util.List;

public interface DosisTilTekstService {
    DosageProposalResult getDosageProposalResult(String type, String iteration, String mapping, String unitTextSingular, String unitTextPlural, String supplementaryText, List<Date> beginDates, List<Date> endDates, FMKVersion version, int dosageProposalVersion, Integer shortTextMaxLength);

    DosageTranslationCombined convertCombined(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options);

    String convertLongText(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options);

    String convertShortText(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options);

    String convertShortText(DosageWrapper dosage, Integer maxLength, DosisTilTekstWrapper.TextOptions options);

    String getShortTextConverterClassName(DosageWrapper dosage);

    String getShortTextConverterClassName(DosageWrapper dosage, Integer maxLength);

    String getLongTextConverterClassName(DosageWrapper dosage);

    DosageType getDosageType(DosageWrapper dosage);

    DosageType getDosageType144(DosageWrapper dosage);

    DailyDosis calculateDailyDosis(DosageWrapper dosage);
}
