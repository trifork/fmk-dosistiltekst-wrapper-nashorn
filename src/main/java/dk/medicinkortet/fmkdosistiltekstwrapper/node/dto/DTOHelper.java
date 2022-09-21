package dk.medicinkortet.fmkdosistiltekstwrapper.node.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.DailyDosis;
import dk.medicinkortet.fmkdosistiltekstwrapper.DosageTranslation;
import dk.medicinkortet.fmkdosistiltekstwrapper.DosageTranslationCombined;
import dk.medicinkortet.fmkdosistiltekstwrapper.JSONHelper;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.requestobjects.DTO;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.responseobjects.combinedconversion.CombinedConversionDTO;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.responseobjects.combinedconversion.DailyDosisDTO;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.responseobjects.combinedconversion.IntervalDTO;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.responseobjects.combinedconversion.UnitOrUnitsWrapperDTO;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.DosageWrapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.Interval;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.UnitOrUnitsWrapper;

import java.math.BigDecimal;
import java.util.LinkedList;

public class DTOHelper {

    public static String convertDTOToJson(DTO dto, String methodName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JsonProcessingException in DosisTilTekstWrapperNode." + methodName + "()", e);
        }
    }

    public static String convertDosageWrapperToJson(DosageWrapper dosage, String methodName) {
        try {
            return JSONHelper.toJsonString(dosage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JsonProcessingException in DosisTilTekstWrapperNode." + methodName + "()", e);
        }
    }

    public static <T> T convertJsonToDTO(String json, Class<T> className, String methodName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, className);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JsonProcessingException in DosisTilTekstWrapperNode." + methodName + "()", e);
        }
    }

    public static DosageTranslationCombined convertDTOToDosageTranslationCombined(CombinedConversionDTO dto) {
        if (dto == null) {
            return null;
        }

        var combinedTranslation = new DosageTranslation(
                dto.getCombinedShortText(),
                dto.getCombinedLongText(),
                convertDTOToDailyDosis(dto.getCombinedDailyDosis())
        );

        var periodTranslations = new LinkedList<DosageTranslation>();
        if (dto.getPeriodTexts() != null) {

            for (var periodText : dto.getPeriodTexts()) {
                var translation = new DosageTranslation(
                        periodText.getShortText(),
                        periodText.getLongText(),
                        convertDTOToDailyDosis(periodText.getDailyDosis())
                );
                periodTranslations.add(translation);
            }
        }

        return new DosageTranslationCombined(combinedTranslation, periodTranslations);
    }

    public static DailyDosis convertDTOToDailyDosis(DailyDosisDTO dto) {
        if (dto.getValue() != null) {
            return new DailyDosis(
                    BigDecimal.valueOf(dto.getValue()),
                    convertDTOToUnitOrUnitsWrapper(dto.getUnitOrUnits())
            );
        }
        return new DailyDosis(
                convertDTOToInterval(dto.getInterval()),
                convertDTOToUnitOrUnitsWrapper(dto.getUnitOrUnits())
        );
    }

    private static UnitOrUnitsWrapper convertDTOToUnitOrUnitsWrapper(UnitOrUnitsWrapperDTO dto) {
        if (dto == null) {
            return null;
        }
        if (dto.getUnit() != null) {
            return UnitOrUnitsWrapper.makeUnit(dto.getUnit());
        }
        return UnitOrUnitsWrapper.makeUnits(dto.getUnitSingular(), dto.getUnitPlural());
    }

    private static Interval<BigDecimal> convertDTOToInterval(IntervalDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Interval<>(dto.getMinimum(), dto.getMaximum());
    }
}
