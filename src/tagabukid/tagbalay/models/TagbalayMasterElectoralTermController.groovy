import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.seti2.models.*;
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*;
import com.rameses.util.*;

class TagbalayMasterElectoralTermController extends CrudFormModel{

    @Binding
    def binding;
    
    @Service('DateService')
    def dtSvc
    
    @Service("TagabukidTagBalayService") 
    def svc

    def node;
   
    boolean isAllowApprove() {
         return ( mode=='read' && entity.state.toString().matches('DRAFT|ACTIVE') ); 
    }

    public void beforeSave(o){
        // entity.state = "DRAFT";

        // entity.parentfund_objid = node.objid
        // println(entity.termbeginning);
        
        
        if(o == 'create'){
            def yearbeggin = dtSvc.getYear(entity.termbeginning);
            def yearend = dtSvc.getYear(entity.termend);
            // println(yearbeggin + "-" + yearend);

            entity.objid                         = 'ET' + new java.rmi.server.UID() +"-"+ dtSvc.getServerYear();
	        entity.acronym                       = yearbeggin + "-" + yearend;
            entity.recordlog_datecreated         = dtSvc.getServerDate();
            entity.recordlog_createdbyuser       = OsirisContext.env.FULLNAME;
            entity.recordlog_createdbyuserid     = OsirisContext.env.USERID;  
            entity.recordlog_dateoflastupdate    = dtSvc.getServerDate();
            entity.recordlog_lastupdatebyuser    = OsirisContext.env.FULLNAME;
            entity.recordlog_lastupdatebyuserid  = OsirisContext.env.USERID;
        }else{
            entity.recordlog_dateoflastupdate = dtSvc.getServerDate();
            entity.recordlog_lastupdatebyuser = OsirisContext.env.FULLNAME;
            entity.recordlog_lastupdatebyuserid = OsirisContext.env.USERID;
        }
    }
    
    void approve() { 
        if ( MsgBox.confirm('You are about to approve this information. Proceed?')) { 
            getPersistenceService().update([ 
               _schemaname: 'master_tblfinfund', 
               objid : entity.objid, 
               state : 'APPROVED' 
            ]); 
            loadData(); 
        }
    }

}