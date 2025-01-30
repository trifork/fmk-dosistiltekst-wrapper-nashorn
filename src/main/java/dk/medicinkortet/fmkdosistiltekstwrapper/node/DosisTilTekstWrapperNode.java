package dk.medicinkortet.fmkdosistiltekstwrapper.node;

import dk.medicinkortet.fmkdosistiltekstwrapper.*;
import dk.medicinkortet.fmkdosistiltekstwrapper.nashorn.DosisTilTekstWrapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.DTOHelper;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.requestobjects.*;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.responseobjects.DosageProposalXmlDTO;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.DosageWrapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.net.http.HttpClient;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DosisTilTekstWrapperNode implements DosisTilTekstService {
    private static final Logger logger = LogManager.getLogger(DosisTilTekstWrapperNode.class);
    private final RequestHelper requestHelper;
    private final String baseUrl;

    public DosisTilTekstWrapperNode(HttpClient client, String baseUrl) {
        this.requestHelper = new RequestHelper(client);
        this.baseUrl = ensureTrailingSlash(baseUrl);

        logger.info("Initialized DosisTilTekstWrapperNode with baseUrl: " + baseUrl);
    }

    private String ensureTrailingSlash(String url) {
        return (url.endsWith("/")) ? url : (url + "/");
    }

    @Override
    public DosageProposalResult getDosageProposalResult(String type, String iteration, String mapping, String unitTextSingular, String unitTextPlural, String supplementaryText, List<Date> beginDates, List<Date> endDates, FMKVersion version, int dosageProposalVersion, Integer shortTextMaxLength) {
        var endpoint = "getDosageProposalResult";

        var dto = new GetDosageProposalResultDTO(type, iteration, mapping, unitTextSingular, unitTextPlural, supplementaryText, beginDates, endDates, version, dosageProposalVersion, shortTextMaxLength);
        var inputJson = DTOHelper.convertDTOToJson(dto, "getDosageProposalResult");

        var responseBody = requestHelper.post(baseUrl + endpoint, inputJson, "getDosageProposalResult");

        if (responseBody == null || responseBody.isEmpty())
            return null;

        var responseDTO = DTOHelper.convertJsonToDTO(responseBody, DosageProposalXmlDTO.class, "getDosageProposalResult");
        return new DosageProposalResult(responseDTO.get_xml(), responseDTO.get_shortDosageTranslation(), responseDTO.get_longDosageTranslation());
    }

    @Override
    public DosageTranslationCombined convertCombined(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        var endpoint = "convertCombined";

        String inputJson = getInputJson(dosage, "convertCombined", options);

        var responseBody = requestHelper.post(baseUrl + endpoint, inputJson, "convertCombined");

        if (responseBody == null || responseBody.isEmpty()) {
            return new DosageTranslationCombined(new DosageTranslation(null, null, new DailyDosis()), new LinkedList<>());
        }

        return DTOHelper.convertJsonToDTO(responseBody, DosageTranslationCombined.class, "convertCombined");
    }

    private String getInputJson(DosageWrapper dosage, String methodName, DosisTilTekstWrapper.TextOptions options) {
        var dosageJson = DTOHelper.convertDosageWrapperToJson(dosage, methodName);
        var dto = new DosageWrapperWithOptionsDTO(dosageJson, options.toString());
        return DTOHelper.convertDTOToJson(dto, methodName);
    }

    @Override
    public String convertLongText(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        var endpoint = "convertLongText";

        String inputJson = getInputJson(dosage, "convertLongText", options);

        var result = requestHelper.post(baseUrl + endpoint, inputJson, "convertLongText");
        if (result == null || result.isEmpty())
            return null;
        return result;
    }

    @Override
    public String convertShortText(DosageWrapper dosage, DosisTilTekstWrapper.TextOptions options) {
        return convertShortText(dosage, null, options);
    }

    @Override
    public String convertShortText(DosageWrapper dosage, Integer maxLength, DosisTilTekstWrapper.TextOptions options) {
        var endpoint = "convertShortText";

        var dosageJson = DTOHelper.convertDosageWrapperToJson(dosage, "convertShortText");
        var dto = new DosageWrapperWithOptionsAndMaxLengthDTO(dosageJson, maxLength, options.toString());
        var inputJson = DTOHelper.convertDTOToJson(dto, "convertShortText");

        var result = requestHelper.post(baseUrl + endpoint, inputJson, "convertShortText");
        if (result == null || result.isEmpty())
            return null;
        return result;
    }

    @Override
    public String getShortTextConverterClassName(DosageWrapper dosage) {
        return getShortTextConverterClassName(dosage, null);
    }

    @Override
    public String getShortTextConverterClassName(DosageWrapper dosage, Integer maxLength) {
        var endpoint = "getShortTextConverterClassName";

        var dosageJson = DTOHelper.convertDosageWrapperToJson(dosage, "getShortTextConverterClassName");
        var dto = new DosageWrapperWithMaxLengthDTO(dosageJson, maxLength);
        var inputJson = DTOHelper.convertDTOToJson(dto, "getShortTextConverterClassName");

        var result = requestHelper.post(baseUrl + endpoint, inputJson, "getShortTextConverterClassName");
        if (result == null || result.isEmpty())
            return null;
        return result;
    }

    @Override
    public String getLongTextConverterClassName(DosageWrapper dosage) {
        var endpoint = "getLongTextConverterClassName";

        var dosageJson = DTOHelper.convertDosageWrapperToJson(dosage, "getLongTextConverterClassName");
        var dto = new DosageWrapperDTO(dosageJson);
        var inputJson = DTOHelper.convertDTOToJson(dto, "getLongTextConverterClassName");

        var result = requestHelper.post(baseUrl + endpoint, inputJson, "getLongTextConverterClassName");
        if (result == null || result.isEmpty())
            return null;
        return result;
    }

    @Override
    public DosageType getDosageType(DosageWrapper dosage) {
        var endpoint = "getDosageType";

        var dosageJson = DTOHelper.convertDosageWrapperToJson(dosage, "getDosageType");
        var dto = new DosageWrapperDTO(dosageJson);
        var inputJson = DTOHelper.convertDTOToJson(dto, "getDosageType");

        var responseBody = requestHelper.post(baseUrl + endpoint, inputJson, "getDosageType");
        return DosageType.fromInteger(Integer.parseInt(responseBody));
    }

    @Override
    public DosageType getDosageType144(DosageWrapper dosage) {
        var endpoint = "getDosageType144";

        var dosageJson = DTOHelper.convertDosageWrapperToJson(dosage, "getDosageType144");
        var dto = new DosageWrapperDTO(dosageJson);
        var inputJson = DTOHelper.convertDTOToJson(dto, "getDosageType144");

        var responseBody = requestHelper.post(baseUrl + endpoint, inputJson, "getDosageType144");
        return DosageType.fromInteger(Integer.parseInt(responseBody));
    }

    @Override
    public DailyDosis calculateDailyDosis(DosageWrapper dosage) {
        var endpoint = "calculateDailyDosis";

        var dosageJson = DTOHelper.convertDosageWrapperToJson(dosage, "calculateDailyDosis");
        var dto = new DosageWrapperDTO(dosageJson);
        var inputJson = DTOHelper.convertDTOToJson(dto, "calculateDailyDosis");

        var responseBody = requestHelper.post(baseUrl + endpoint, inputJson, "calculateDailyDosis");

        return DTOHelper.convertJsonToDTO(responseBody, DailyDosis.class, "calculateDailyDosis");
    }
}
