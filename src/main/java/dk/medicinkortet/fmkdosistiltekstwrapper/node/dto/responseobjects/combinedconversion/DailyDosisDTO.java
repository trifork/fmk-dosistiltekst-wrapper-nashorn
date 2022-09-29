package dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.responseobjects.combinedconversion;

import java.math.BigDecimal;

public class DailyDosisDTO {

    private BigDecimal value;
    private IntervalDTO interval;
    private UnitOrUnitsWrapperDTO unitOrUnits;

    public DailyDosisDTO() {
    }

    public DailyDosisDTO(BigDecimal value, IntervalDTO interval, UnitOrUnitsWrapperDTO unitOrUnits) {
        this.value = value;
        this.interval = interval;
        this.unitOrUnits = unitOrUnits;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
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
