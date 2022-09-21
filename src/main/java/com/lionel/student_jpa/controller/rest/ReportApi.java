package com.lionel.student_jpa.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lionel.student_jpa.model.HttpResponse;
import com.lionel.student_jpa.service.ReportService;
import static com.lionel.student_jpa.controller.constants.Const.*;

@RestController
@RequestMapping( value = "/api" )
@CrossOrigin( origins = "http://localhost:3000" )
public class ReportApi
{
    private ReportService reportService;
    private final String EXPORTED_PATH = "D:\\datas\\reports\\";

    @Autowired
    public ReportApi( ReportService reportService ){
        this.reportService = reportService;
    }

    @GetMapping( value = "/report" )
    public ResponseEntity<HttpResponse> getReport(
        @RequestParam( value = "type" , required = true ) String type ,
        @RequestParam( value = "target" , required = true ) String target
     ){ 
        HttpResponse httpResponse = new HttpResponse();
        
        if( !this.isTypeValid( type ) || !this.isTargetValid( target ) ){
            httpResponse.setHttpStatus( HttpStatus.BAD_REQUEST );
            httpResponse.setOk( false );
            httpResponse.setStatusCode( HttpStatus.BAD_REQUEST.value() );
            
            if( !this.isTargetValid( target ) && !this.isTypeValid( type )) 
                httpResponse.setMsg("Invalid reporting!");
            
            if( this.isTargetValid( target ) && !this.isTypeValid( type ))
                httpResponse.setMsg( "Report is available only for pdf , excel & html formats!");
            
            if( !this.isTargetValid( target ) && this.isTypeValid( type ))
                httpResponse.setMsg( "Report is available for course , user & student!");                    
        }else{
            try{    
                this.reportService._export( type , target );
                httpResponse.setHttpStatus( HttpStatus.OK );
                httpResponse.setOk( true );
                httpResponse.setStatusCode( HttpStatus.OK.value() );
                httpResponse.setMsg( this.EXPORTED_PATH + target +"."+ type);
            }catch( Exception e ){
                httpResponse.setOk( false );
                httpResponse.setMsg("There was error in reporting!");
            }
        }

        return new ResponseEntity<HttpResponse>( httpResponse ,  httpResponse.getHttpStatus()  );
    }

    private boolean isTypeValid( String type ){
        boolean valid = false;
        if( REPORT_TYPES.contains( type ) ){
            valid = true;
        }
        return valid;
    }

    private boolean isTargetValid( String target  ){
        boolean valid = false;
        if( REPORT_TARGET.contains( target )){
            valid = true;
        }
        return valid;
    }
}
