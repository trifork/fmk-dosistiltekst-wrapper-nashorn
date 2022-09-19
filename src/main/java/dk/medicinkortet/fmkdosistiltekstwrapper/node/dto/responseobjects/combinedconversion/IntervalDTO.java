package dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.responseobjects.combinedconversion;

import java.math.BigDecimal;

public class IntervalDTO {
    private BigDecimal minimum;
    private BigDecimal maximum;

    public IntervalDTO() {
    }

    public IntervalDTO(BigDecimal minimum, BigDecimal maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public BigDecimal getMinimum() {
        return minimum;
    }

    public void setMinimum(BigDecimal minimum) {
        this.minimum = minimum;
    }

    public BigDecimal getMaximum() {
        return maximum;
    }

    public void setMaximum(BigDecimal maximum) {
        this.maximum = maximum;
    }
}
