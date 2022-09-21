package dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.requestobjects;

public class DosageWrapperWithOptionsAndMaxLengthDTO implements DTO {
    String dosageJson;
    Integer maxLength;
    String options;

    public DosageWrapperWithOptionsAndMaxLengthDTO(String dosageJson, Integer maxLength, String options) {
        this.dosageJson = dosageJson;
        this.maxLength = maxLength;
        this.options = options;
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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
