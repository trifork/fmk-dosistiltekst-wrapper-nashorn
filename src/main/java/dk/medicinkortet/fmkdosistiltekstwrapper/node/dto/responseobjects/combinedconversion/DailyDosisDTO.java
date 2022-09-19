package dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.responseobjects.combinedconversion;

public class DailyDosisDTO {

    private Integer value;
    private IntervalDTO interval;
    private UnitOrUnitsWrapperDTO unitOrUnits;

    public DailyDosisDTO() {
    }

    public DailyDosisDTO(Integer value, IntervalDTO interval, UnitOrUnitsWrapperDTO unitOrUnits) {
        this.value = value;
        this.interval = interval;
        this.unitOrUnits = unitOrUnits;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public IntervalDTO getInterval() {
        return interval;
    }

    public void setInterval(IntervalDTO interval) {
        this.interval = interval;
    }

    public UnitOrUnitsWrapperDTO getUnitOrUnits() {
        return unitOrUnits;
    }

    public void setUnitOrUnits(UnitOrUnitsWrapperDTO unitOrUnits) {
        this.unitOrUnits = unitOrUnits;
    }
}
