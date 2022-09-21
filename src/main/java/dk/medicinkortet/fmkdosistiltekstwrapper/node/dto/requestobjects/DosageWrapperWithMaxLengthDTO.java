package dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.requestobjects;

public class DosageWrapperWithMaxLengthDTO implements DTO {
    String dosageJson;
    Integer maxLength;

    public DosageWrapperWithMaxLengthDTO(String dosageJson, Integer maxLength) {
        this.dosageJson = dosageJson;
        this.maxLength = maxLength;
    }

    public String getDosageJson() {
        return dosageJson;
    }

    public void setDosageJson(String dosageJson) {
        this.dosageJson = dosageJson;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }
}
