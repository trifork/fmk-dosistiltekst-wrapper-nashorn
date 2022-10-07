package dk.medicinkortet.fmkdosistiltekstwrapper.node;

import dk.medicinkortet.fmkdosistiltekstwrapper.*;
import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.DTOHelper;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.requestobjects.*;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.responseobjects.DosageProposalXmlDTO;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.DosageWrapper;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DosisTilTekstWrapperNode {
    private static String baseUrl;

    public static void initialize(String url) {
        if (url.endsWith("/"))
            baseUrl = url;
        else
            baseUrl = url + "/";
    }

    private static void checkIfInitialized() {
        if(baseUrl == null) {
            throw new RuntimeException("DosisTilTekstWrapper not initialized - call initialize() method before invoking any of the methods");
        }
    }

    public static DosageProposalResult getDosageProposalResult(String type, String iteration, String mapping, String unitTextSingular, String unitTextPlural, String supplementaryText, List<Date> beginDates, List<Date> endDates, FMKVersion version, int dosageProposalVersion, Integer shortTextMaxLength) {
        checkIfInitialized();

        var endpoint = "getDosageProposalResult";

        var dto = new GetDosageProposalResultDTO(type, iteration, mapping, unitTextSingular, unitTextPlural, supplementaryText, beginDates, endDates, version, dosageProposalVersion, shortTextMaxLength);
        var inputJson = DTOHelper.convertDTOToJson(dto, "getDosageProposalResult");

        var responseBody = RequestHelper.post(baseUrl + endpoint, inputJson, "getDosageProposalResult");

        if (responseBody == null || responseBody.isEmpty())
            return null;

        var responseDTO = DTOHelper.convertJsonToDTO(responseBody, DosageProposalXmlDTO.class, "getDosageProposalResult");
        return new DosageProposalResult(responseDTO.get_xml(), responseDTO.get_shortDosageTranslation(), responseDTO.get_longDosageTranslation());
    }

    public static DosageTranslationCombined convertCombined(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        checkIfInitialized();

        var endpoint = "convertCombined";

        var dosageJson = DTOHelper.convertDosageWrapperToJson(dosage, "convertCombined");
        var dto = new DosageWrapperWithOptionsDTO(dosageJson, options.toString());
        var inputJson = DTOHelper.convertDTOToJson(dto, "convertCombined");

        var responseBody = RequestHelper.post(baseUrl + endpoint, inputJson, "convertCombined");

        if (responseBody == null || responseBody.isEmpty()) {
            return new DosageTranslationCombined(new DosageTranslation(null, null, new DailyDosis()), new LinkedList<>());
        }

        return DTOHelper.convertJsonToDTO(responseBody, DosageTranslationCombined.class, "convertCombined");
    }

    public static String convertLongText(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        checkIfInitialized();

        var endpoint = "convertLongText";

        var dosageJson = DTOHelper.convertDosageWrapperToJson(dosage, "convertLongText");
        var dto = new DosageWrapperWithOptionsDTO(dosageJson, options.toString());
        var inputJson = DTOHelper.convertDTOToJson(dto, "convertLongText");

        var result = RequestHelper.post(baseUrl + endpoint, inputJson, "convertLongText");
        if (result == null || result.isEmpty())
            return null;
        return result;
    }

    public static String convertShortText(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        return convertShortText(dosage, null, options);
    }

    public static String convertShortText(DosageWrapper dosage, Integer maxLength, DosisTilTekstWrapper.TextOptions options) {
        checkIfInitialized();

        var endpoint = "convertShortText";

        var dosageJson = DTOHelper.convertDosageWrapperToJson(dosage, "convertShortText");
        var dto = new DosageWrapperWithOptionsAndMaxLengthDTO(dosageJson, maxLength, options.toString());
        var inputJson = DTOHelper.convertDTOToJson(dto, "convertShortText");

        var result = RequestHelper.post(baseUrl + endpoint, inputJson, "convertShortText");
        if (result == null || result.isEmpty())
            return null;
        return result;
    }

    public static String getShortTextConverterClassName(DosageWrapper dosage) {
        return getShortTextConverterClassName(dosage, null);
    }

    public static String getShortTextConverterClassName(DosageWrapper dosage, Integer maxLength) {
        checkIfInitialized();

        var endpoint = "getShortTextConverterClassName";

        var dosageJson = DTOHelper.convertDosageWrapperToJson(dosage, "getShortTextConverterClassName");
        var dto = new DosageWrapperWithMaxLengthDTO(dosageJson, maxLength);
        var inputJson = DTOHelper.convertDTOToJson(dto, "getShortTextConverterClassName");

        var result = RequestHelper.post(baseUrl + endpoint, inputJson, "getShortTextConverterClassName");
        if (result == null || result.isEmpty())
            return null;
        return result;
    }

    public static String getLongTextConverterClassName(DosageWrapper dosage) {
        checkIfInitialized();

        var endpoint = "getLongTextConverterClassName";

        var dosageJson = DTOHelper.convertDosageWrapperToJson(dosage, "getLongTextConverterClassName");
        var dto = new DosageWrapperDTO(dosageJson);
        var inputJson = DTOHelper.convertDTOToJson(dto, "getLongTextConverterClassName");

        var result = RequestHelper.post(baseUrl + endpoint, inputJson, "getLongTextConverterClassName");
        if (result == null || result.isEmpty())
            return null;
        return result;
    }

    public static DosageType getDosageType(DosageWrapper dosage) {
        checkIfInitialized();

        var endpoint = "getDosageType";

        var dosageJson = DTOHelper.convertDosageWrapperToJson(dosage, "getDosageType");
        var dto = new DosageWrapperDTO(dosageJson);
        var inputJson = DTOHelper.convertDTOToJson(dto, "getDosageType");

        var responseBody = RequestHelper.post(baseUrl + endpoint, inputJson, "getDosageType");
        return DosageType.fromInteger(Integer.parseInt(responseBody));
    }

    public static DosageType getDosageType144(DosageWrapper dosage) {
        checkIfInitialized();

        var endpoint = "getDosageType144";

        var dosageJson = DTOHelper.convertDosageWrapperToJson(dosage, "getDosageType144");
        var dto = new DosageWrapperDTO(dosageJson);
        var inputJson = DTOHelper.convertDTOToJson(dto, "getDosageType144");

        var responseBody = RequestHelper.post(baseUrl + endpoint, inputJson, "getDosageType144");
        return DosageType.fromInteger(Integer.parseInt(responseBody));
    }

    public static DailyDosis calculateDailyDosis(DosageWrapper dosage) {
        checkIfInitialized();

        var endpoint = "calculateDailyDosis";

        var dosageJson = DTOHelper.convertDosageWrapperToJson(dosage, "calculateDailyDosis");
        var dto = new DosageWrapperDTO(dosageJson);
        var inputJson = DTOHelper.convertDTOToJson(dto, "calculateDailyDosis");

        var responseBody = RequestHelper.post(baseUrl + endpoint, inputJson, "calculateDailyDosis");

        return DTOHelper.convertJsonToDTO(responseBody, DailyDosis.class, "calculateDailyDosis");
    }
}
