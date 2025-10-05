package main.java.org.travel.API.src.main.java;

import java.util.Scanner;

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

    public static UserReports Reporter() {
        Scanner Reporter = new Scanner(System.in);

        System.out.println("Hva rapporterer du");
        String report = Reporter.nextLine();

        System.out.println("Hva slags type problem er det");
        int reportID = Reporter.nextInt();

        System.out.println("Forklar problemet");
        String reportDescription = Reporter.nextLine();

        return new UserReports(report, reportID, reportDescription);
    }

}
