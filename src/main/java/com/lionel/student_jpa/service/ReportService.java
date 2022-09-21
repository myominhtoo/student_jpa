package com.lionel.student_jpa.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class ReportService {

    private CourseService courseService;
    private StudentService studentService;
    private UserService useService;

    private JRBeanCollectionDataSource source = null;
    private JasperReport jasperReport = null;
    private JasperPrint jasperPrint = null;
    private final String PATH_TO_EXPORT = "D:\\datas\\reports";
    
    @Autowired
    public ReportService( CourseService courseService , StudentService studentService , UserService userService , HttpServletRequest request){
        this.courseService = courseService;
        this.studentService = studentService;
        this.useService = userService;
    }

    public void _export( String type , String target ) throws JRException{
         String path = this.getPathForTarget( target );
         System.out.println(path);
        this.source = new JRBeanCollectionDataSource(
            target.equals("course")
            ? this.courseService.findAll()
            : target.equals("student")
                ? this.studentService.findAll()
                : this.useService.findAll()
        );
        this.jasperReport = JasperCompileManager.compileReport( path );

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("List", "Test");

        this.jasperPrint = JasperFillManager.fillReport( this.jasperReport , parameters , this.source);

        switch( type ){
            case "pdf" : 
                this.exportPdf( target );
                break;
            case "excel" : 
                this.exportExcel( target );
                break;
           case "html" :
               this.exportHtml( target );
               break;
        }

    }

    private  void exportPdf( String target ) throws JRException{
        JasperExportManager.exportReportToPdfFile( this.jasperPrint , this.PATH_TO_EXPORT+"\\"+target+".pdf" );   
    }

    private void exportExcel( String target ) throws JRException{
       JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput( new SimpleExporterInput(this.jasperPrint)  );
        exporter.setExporterOutput( new SimpleOutputStreamExporterOutput( this.PATH_TO_EXPORT + "\\"+target+".xlsx" ));

        SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
        config.setOnePagePerSheet( true );
        config.setDetectCellType( true );
        exporter.setConfiguration( config );
        exporter.exportReport();
    }

   private void exportHtml( String target ) throws JRException{
       JasperExportManager.exportReportToHtmlFile( this.jasperPrint ,this.PATH_TO_EXPORT+"\\"+target+".html");
   }


    private String getPathForTarget( String target ){
        String path = "D:/_ojt/student_jpa/src/main/resources/jasper" ;
        switch( target ){
            case "course" : 
                path += "/course_reports.jrxml";
                break;
            case "student" : 
                path += "/student_reports.jrxml";
                break;
            case "user" : 
                path += "/user_reports.jrxml";
                break;
            default : 
                path += "";
        }
        return path;
    }

}
