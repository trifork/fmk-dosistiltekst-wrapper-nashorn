package dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.responseobjects;

public class DosageProposalXmlDTO {
    private String _xml;
    private String _shortDosageTranslation;
    private String _longDosageTranslation;

    public DosageProposalXmlDTO() {
    }

    public String get_xml() {
        return _xml;
    }

    public void set_xml(String _xml) {
        this._xml = _xml;
    }

    public String get_shortDosageTranslation() {
        return _shortDosageTranslation;
    }

    public void set_shortDosageTranslation(String _shortDosageTranslation) {
        this._shortDosageTranslation = _shortDosageTranslation;
    }

    public String get_longDosageTranslation() {
        return _longDosageTranslation;
    }

    public void set_longDosageTranslation(String _longDosageTranslation) {
        this._longDosageTranslation = _longDosageTranslation;
    }
}
