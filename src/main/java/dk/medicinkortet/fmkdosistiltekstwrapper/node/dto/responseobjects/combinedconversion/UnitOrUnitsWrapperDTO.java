package dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.responseobjects.combinedconversion;

public class UnitOrUnitsWrapperDTO {
    private String unit;
    private String unitSingular;
    private String unitPlural;

    public UnitOrUnitsWrapperDTO() {
    }

    public UnitOrUnitsWrapperDTO(String unit, String unitSingular, String unitPlural) {
        this.unit = unit;
        this.unitSingular = unitSingular;
        this.unitPlural = unitPlural;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitSingular() {
        return unitSingular;
    }

    public void setUnitSingular(String unitSingular) {
        this.unitSingular = unitSingular;
    }

    public String getUnitPlural() {
        return unitPlural;
    }

    public void setUnitPlural(String unitPlural) {
        this.unitPlural = unitPlural;
    }
}
