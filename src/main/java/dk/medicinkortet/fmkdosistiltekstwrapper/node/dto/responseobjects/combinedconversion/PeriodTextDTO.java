package dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.responseobjects.combinedconversion;

public class PeriodTextDTO {
    private String shortText;
    private String longText;
    private DailyDosisDTO dailyDosis;

    public PeriodTextDTO(String shortText, String longText, DailyDosisDTO dailyDosis) {
        this.shortText = shortText;
        this.longText = longText;
        this.dailyDosis = dailyDosis;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public DailyDosisDTO getDailyDosis() {
        return dailyDosis;
    }

    public void setDailyDosis(DailyDosisDTO dailyDosis) {
        this.dailyDosis = dailyDosis;
    }
}
