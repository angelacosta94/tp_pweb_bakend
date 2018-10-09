package py.com.progweb.prueba.rest;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;

import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportUtils {

    @SuppressWarnings("unchecked")
    public static Response crearPDF(String nombreTemplate, String tituloReporte, List valores, String nombreArchivo) {
        InputStream templateJrxml;
        Map parametros = new HashMap();
        parametros.put("TITULO_REPORTE", tituloReporte);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(valores);

        OutputStream outputStream = new ByteArrayOutputStream();

        try {
            templateJrxml = ExportUtils.class.getClassLoader().getResourceAsStream("reportes/" + nombreTemplate + ".jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(templateJrxml);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (JRException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

        return Response.ok(outputStream)
                .header("content-disposition", "attachment; filename=\"" + nombreArchivo + ".pdf\"")
                .type("application/pdf")
                .build();
    }

    @SuppressWarnings("unchecked")
    public static Response crearExcel(String nombreTemplate, String tituloReporte, List valores, String nombreArchivo) {
        InputStream templateJrxml;
        Map parametros = new HashMap();
        parametros.put("TITULO_REPORTE", tituloReporte);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(valores);

        OutputStream outputStream = new ByteArrayOutputStream();

        try {
            templateJrxml = ExportUtils.class.getClassLoader().getResourceAsStream("reportes/" + nombreTemplate + ".jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(templateJrxml);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

            JRXlsExporter xlsExporter = new JRXlsExporter();
            xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            SimpleXlsExporterConfiguration exporterConfiguration = new SimpleXlsExporterConfiguration();

            xlsExporter.setConfiguration(exporterConfiguration);

            xlsExporter.exportReport();
        } catch (JRException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

        return Response.ok(outputStream)
                .header("content-disposition", "attachment; filename=\"" + nombreArchivo + ".xls\"")
                .type("application/vnd.ms-excel")
                .build();
    }
}


