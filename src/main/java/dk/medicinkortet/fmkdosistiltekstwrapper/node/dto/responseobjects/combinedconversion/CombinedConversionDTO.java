package dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.responseobjects.combinedconversion;

import java.util.List;

public class CombinedConversionDTO {
    private String combinedShortText;
    private String combinedLongText;
    private DailyDosisDTO combinedDailyDosis;
    private List<PeriodTextDTO> periodTexts;

    public CombinedConversionDTO() {

    }

    public String getCombinedShortText() {
        return combinedShortText;
    }

    public void setCombinedShortText(String combinedShortText) {
        this.combinedShortText = combinedShortText;
    }

    public String getCombinedLongText() {
        return combinedLongText;
    }

    public void setCombinedLongText(String combinedLongText) {
        this.combinedLongText = combinedLongText;
    }

    public DailyDosisDTO getCombinedDailyDosis() {
        return combinedDailyDosis;
    }

    public void setCombinedDailyDosis(DailyDosisDTO combinedDailyDosis) {
        this.combinedDailyDosis = combinedDailyDosis;
    }

    public List<PeriodTextDTO> getPeriodTexts() {
        return periodTexts;
    }

    public void setPeriodTexts(List<Object> periodTexts) {
        this.periodTexts = null;
    }
}
