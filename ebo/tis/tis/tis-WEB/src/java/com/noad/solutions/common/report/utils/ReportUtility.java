package com.noad.solutions.common.report.utils;

import com.noad.solutions.common.jsf.utils.JSFUtility;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;

/**
 *
 * @author Seth/Daud
 */
public class ReportUtility {

    public static final String REPORT_FOLDER = "/WEB-INF/reports/";
    public static final String REPORT_FILE_EXTENSION = ".jasper";
    public static final String PDF_FILE = "application/pdf";
    public static final String DOCX_FILE = "application/docx";
    public static final String HTML_FILE = "text/html";
    public static final String XSL_FILE = "xls/html";
    private static final String ATTACHEMENT_TYPE = "inline; filename=";
    private static final String[] VALID_REPORT_EXTENSION = {HTML_FILE, // Standard HTML representation
        PDF_FILE,// Portable Document Format
        DOCX_FILE//Word Document Format
    };
    private Map reportParams = new HashMap();          //Map all the parameters of the report
    private String reportFileName;     // The name of the report file
    private Collection dataCollection; // The data Collection to be displayed
    private String reportFileType;     //The export type for the report

    public ReportUtility() {
    }

    public ReportUtility(Map reportParams, String reportFileName, Collection dataCollection, String reportFileType) {
        this.reportFileName = reportFileName;
        this.reportParams = reportParams;
        this.dataCollection = dataCollection;
        this.reportFileType = reportFileType;
    }

    public ReportUtility(String reportFileName, Collection dataCollection, String reportFileType) {
        this.reportFileName = reportFileName;
        this.dataCollection = dataCollection;
        this.reportFileType = reportFileType;
    }

    public boolean validateReportExtension(String reportExtension) {
        for (int i = 0; i < VALID_REPORT_EXTENSION.length; i++) {
            if (VALID_REPORT_EXTENSION[i].equals(reportExtension)) {
                return true;
            }
        }
        return false;
    }

    public void generateReport() {
        if (validateReportExtension(getReportFileType())) {
            ExternalContext externalContext = JSFUtility.externalContext();
            try {
                String fullFileName = ReportUtility.REPORT_FOLDER + getReportFileName() + REPORT_FILE_EXTENSION;
                InputStream reportFileInput = externalContext.getResourceAsStream(fullFileName);
                JRBeanCollectionDataSource jRBeanCollectionDataSource = new JRBeanCollectionDataSource(getDataCollection());
                JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileInput, getReportParams(), jRBeanCollectionDataSource);

                reportExporter(externalContext, jasperPrint);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error in REPORT UTILITY : " + e.toString());
                // throw new FacesException(e);
            } finally {
            }
        }
    }

    public void reportExporter(ExternalContext externalContext, JasperPrint jasperPrint) {
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        JRExporter exporter;
        if (getReportFileType().equalsIgnoreCase(PDF_FILE)) {
            exporter = pdfExporter(jasperPrint, response);
        } else if (getReportFileType().equalsIgnoreCase(DOCX_FILE)) {
            exporter = docxExporter(jasperPrint, response);
        } else {
            exporter = htmlExporter(jasperPrint, response);
        }

        try {
            exporter.exportReport();
            JSFUtility.currentContext().responseComplete();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new FacesException(e);
        }
    }

    public JRExporter pdfExporter(JasperPrint jasperPrint, HttpServletResponse response) {
        JRExporter exporter = new JRPdfExporter();
        response.setContentType(PDF_FILE);
        //         httpServletResponse.addHeader("Content-disposition", "attachment; filename=report1.pdf");

        response.addHeader("Content-disposition", ATTACHEMENT_TYPE + reportFileName + PDF_FILE);

        try {
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
            exporter.exportReport();
            FacesContext.getCurrentInstance().responseComplete();

            System.out.println("---------------- PDF ----------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exporter;
    }

    //ADDITION OF WORD DOCUMENT FORMAT
    public JRExporter docxExporter(JasperPrint jasperPrint, HttpServletResponse response) {

        JRExporter exporter = new JRDocxExporter();
        response.setContentType(DOCX_FILE);
        try {

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
            System.out.println("----------------------DOCX--------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exporter;
    }

    public JRExporter htmlExporter(JasperPrint jasperPrint, HttpServletResponse response) {
        return null;
    }
    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">

    /**
     * @return the reportParams
     */
    public Map getReportParams() {
        return reportParams;
    }

    /**
     * @param reportParams the reportParams to set
     */
    public void setReportParams(Map reportParams) {
        this.reportParams = reportParams;
    }

    /**
     * @return the reportFileName
     */
    public String getReportFileName() {
        return reportFileName;
    }

    public static String getREPORT_FOLDER() {
        return REPORT_FOLDER;
    }

    public static String getREPORT_FILE_EXTENSION() {
        return REPORT_FILE_EXTENSION;
    }

    public static String getPDF_FILE() {
        return PDF_FILE;
    }

    public static String getDOCX_FILE() {
        return DOCX_FILE;
    }

    public static String getHTML_FILE() {
        return HTML_FILE;
    }

    public static String getXSL_FILE() {
        return XSL_FILE;
    }

    public static String getATTACHEMENT_TYPE() {
        return ATTACHEMENT_TYPE;
    }

    public static String[] getVALID_REPORT_EXTENSION() {
        return VALID_REPORT_EXTENSION;
    }

    /**
     * @param reportFileName the reportFileName to set
     */
    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    /**
     * @return the dataCollection
     */
    public Collection getDataCollection() {
        return dataCollection;
    }

    /**
     * @param dataCollection the dataCollection to set
     */
    public void setDataCollection(Collection dataCollection) {
        this.dataCollection = dataCollection;
    }

    /**
     * @return the reportFileType
     */
    public String getReportFileType() {
        return reportFileType;
    }

    /**
     * @param reportFileType the reportFileType to set
     */
    public void setReportFileType(String reportFileType) {
        this.reportFileType = reportFileType;
    }
    //</editor-fold>
}
