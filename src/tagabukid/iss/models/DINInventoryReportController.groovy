import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.osiris2.reports.*;

class TagabukidSubayDINInventoryReportModel extends ReportController {

    @FormId
    String formId;

    @FormTitle
    String formTitle;
        
        
    @Service("TagabukidSubayDINInventoryService")
    def svc;

    def entity;
    def title = "Document Identification Number";

    final String REPORT_PATH = "tagabukid/dts/reports/"; 
    String reportName = REPORT_PATH + 'dinmain5.jasper'; 

    def getReportData() { 
//        println entity;
        //return [[din:entity.din,barcode:"71007:"+entity.din],[din:entity.din,barcode:"71007:"+entity.din]]; 
        return svc.getDINs(entity.objid);
    } 
} 