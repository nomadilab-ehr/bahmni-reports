package org.bahmni.reports.web;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import org.bahmni.reports.template.Templates;
import org.bahmni.reports.BahmniReportsProperties;

import java.text.SimpleDateFormat;
import java.util.Date;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;

public class ReportHeader {
    private BahmniReportsProperties bahmniReportsProperties;

    public JasperReportBuilder add(JasperReportBuilder jasperReportBuilder, String reportName, String startDate, String endDate) {
        bahmniReportsProperties = new BahmniReportsProperties("bahmni-reports.properties");

        HorizontalListBuilder headerList = cmp.horizontalList();

        addTitle(reportName, headerList);

        addVerticalGap(headerList, 10);

        addTitle("Facilty: " + bahmniReportsProperties.getFacilityName(), headerList);

        addInfoHeader("MFL Code: " + bahmniReportsProperties.getFacilityCode(), headerList);
        addInfoHeader("State: " + bahmniReportsProperties.getFacilityState(), headerList);
        addInfoHeader("Region: " + bahmniReportsProperties.getFacilityRegion(), headerList);
        addInfoHeader("District: " + bahmniReportsProperties.getFacilityDistrict(), headerList);

        addVerticalGap(headerList, 5);

        addInfoHeader("Version: " + bahmniReportsProperties.getReportVersion(), headerList);

        addVerticalGap(headerList, 15);

        addDatesSubHeader(startDate, endDate, headerList);

        addReportGeneratedDateSubHeader(headerList);

        addVerticalGap(headerList, 10);

        jasperReportBuilder.addTitle(headerList);

        return jasperReportBuilder;
    }

    private void addVerticalGap(HorizontalListBuilder headerList, int gap) {
        headerList.add(cmp.verticalGap(gap));
    }

    private void addInfoHeader(String text, HorizontalListBuilder headerList) {
        headerList.add(cmp.text(text)
                .setStyle(Templates.bold12CenteredStyle)
                .setHorizontalAlignment(HorizontalAlignment.CENTER))
                .newRow()
                .add(cmp.verticalGap(5));
    }

    private void addInfoTitle(String text, HorizontalListBuilder headerList) {
        headerList.add(cmp.text(text)
                .setStyle(Templates.bold18CenteredStyle)
                .setHorizontalAlignment(HorizontalAlignment.CENTER))
                .newRow()
                .add(cmp.verticalGap(5));
    }

    private void addTitle(String reportName, HorizontalListBuilder headerList) {
        headerList.add(cmp.text(reportName)
                .setStyle(Templates.bold18CenteredStyle)
                .setHorizontalAlignment(HorizontalAlignment.CENTER))
                .newRow()
                .add(cmp.verticalGap(5));
    }

    private void addDatesSubHeader(String startDate, String endDate, HorizontalListBuilder headerList) {
        if (startDate.equalsIgnoreCase("null") || endDate.equalsIgnoreCase("null")) return;

        headerList.add(cmp.text("From " + startDate + " to " + endDate)
                .setStyle(Templates.bold12CenteredStyle)
                .setHorizontalAlignment(HorizontalAlignment.CENTER))
                .newRow();
    }

    private void addReportGeneratedDateSubHeader(HorizontalListBuilder headerList) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateString = simpleDateFormat.format(new Date());
        headerList.add(cmp.text("Report Generated On: " + dateString)
                .setStyle(Templates.bold12CenteredStyle)
                .setHorizontalAlignment(HorizontalAlignment.CENTER))
                .newRow();
    }
}
