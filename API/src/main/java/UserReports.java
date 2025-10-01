public class UserReports {

    private String Report;
    private int ReportID;
    private String ReportDescription;

    public UserReports(String report, int reportID, String reportDescription) {
        Report = report;
        ReportID = reportID;
        ReportDescription = reportDescription;
    }

    public String getReport() {
        return Report;
    }

    public void setReport(String report) {
        Report = report;
    }

    public int getReportID() {
        return ReportID;
    }

    public void setReportID(int reportID) {
        ReportID = reportID;
    }

    public String getReportDescription() {
        return ReportDescription;
    }

    public void setReportDescription(String reportDescription) {
        ReportDescription = reportDescription;
    }
}
