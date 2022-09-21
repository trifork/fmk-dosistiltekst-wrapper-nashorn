package dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.requestobjects;

public class DosageWrapperWithOptionsDTO implements DTO {
    String dosageJson;
    String options;

    public DosageWrapperWithOptionsDTO(String dosageJson, String options) {
        this.dosageJson = dosageJson;
        this.options = options;
    }

    public String getDosageJson() {
        return dosageJson;
    }

    public void setDosageJson(String dosageJson) {
        this.dosageJson = dosageJson;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
