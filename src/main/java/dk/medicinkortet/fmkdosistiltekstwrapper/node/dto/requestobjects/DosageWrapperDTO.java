package dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.requestobjects;

public class DosageWrapperDTO implements DTO {
    String dosageJson;

    public DosageWrapperDTO(String dosageJson) {
        this.dosageJson = dosageJson;
    }

    public String getDosageJson() {
        return dosageJson;
    }

    public void setDosageJson(String dosageJson) {
        this.dosageJson = dosageJson;
    }
}
