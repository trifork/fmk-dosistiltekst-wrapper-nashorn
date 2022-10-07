package dk.medicinkortet.fmkdosistiltekstwrapper.node.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.medicinkortet.fmkdosistiltekstwrapper.JSONHelper;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.requestobjects.DTO;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.DosageWrapper;

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
}
