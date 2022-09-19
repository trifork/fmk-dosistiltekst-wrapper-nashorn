package dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.requestobjects;

import dk.medicinkortet.fmkdosistiltekstwrapper.FMKVersion;

import java.util.Date;
import java.util.List;

public class GetDosageProposalResultDTO implements DTO {
    private String type;
    private String iteration;
    private String mapping;
    private String unitTextSingular;
    private String unitTextPlural;
    private String supplementaryText;
    private List<Date> beginDates;
    private List<Date> endDates;
    private FMKVersion fmkversion;
    private int dosageProposalVersion;
    private Integer shortTextMaxLength;

    public GetDosageProposalResultDTO(String type, String iteration, String mapping, String unitTextSingular, String unitTextPlural, String supplementaryText, List<Date> beginDates, List<Date> endDates, FMKVersion fmkversion, int dosageProposalVersion, Integer shortTextMaxLength) {
        this.type = type;
        this.iteration = iteration;
        this.mapping = mapping;
        this.unitTextSingular = unitTextSingular;
        this.unitTextPlural = unitTextPlural;
        this.supplementaryText = supplementaryText;
        this.beginDates = beginDates;
        this.endDates = endDates;
        this.fmkversion = fmkversion;
        this.dosageProposalVersion = dosageProposalVersion;
        this.shortTextMaxLength = shortTextMaxLength;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIteration() {
        return iteration;
    }

    public void setIteration(String iteration) {
        this.iteration = iteration;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getUnitTextSingular() {
        return unitTextSingular;
    }

    public void setUnitTextSingular(String unitTextSingular) {
        this.unitTextSingular = unitTextSingular;
    }

    public String getUnitTextPlural() {
        return unitTextPlural;
    }

    public void setUnitTextPlural(String unitTextPlural) {
        this.unitTextPlural = unitTextPlural;
    }

    public String getSupplementaryText() {
        return supplementaryText;
    }

    public void setSupplementaryText(String supplementaryText) {
        this.supplementaryText = supplementaryText;
    }

    public List<Date> getBeginDates() {
        return beginDates;
    }

    public void setBeginDates(List<Date> beginDates) {
        this.beginDates = beginDates;
    }

    public List<Date> getEndDates() {
        return endDates;
    }

    public void setEndDates(List<Date> endDates) {
        this.endDates = endDates;
    }

    public FMKVersion getFmkversion() {
        return fmkversion;
    }

    public void setFmkversion(FMKVersion fmkversion) {
        this.fmkversion = fmkversion;
    }

    public int getDosageProposalVersion() {
        return dosageProposalVersion;
    }

    public void setDosageProposalVersion(int dosageProposalVersion) {
        this.dosageProposalVersion = dosageProposalVersion;
    }

    public Integer getShortTextMaxLength() {
        return shortTextMaxLength;
    }

    public void setShortTextMaxLength(Integer shortTextMaxLength) {
        this.shortTextMaxLength = shortTextMaxLength;
    }
}
