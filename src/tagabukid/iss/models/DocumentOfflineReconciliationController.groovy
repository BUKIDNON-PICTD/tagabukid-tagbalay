import com.rameses.rcp.annotations.*
import com.rameses.rcp.common.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.common.*;
import java.rmi.server.*
import com.rameses.util.*;
        
class DocumentOfflineReconciliationController {
        
    @Caller
    def caller;
   
    @Binding 
    def binding;
   
    @Service("TagabukidSubayTransactionService")
    def service;
    def mode = 'create'
    def handler;

    def reconciliationActions = ["outgoing", "receive", "archived"];
    def entity = [:];
            
    void init() {
        entity.mode = 'offline';
    }
            
    def doOk() {
        if( mode == 'create' ) { 
            def offlinetask = service.createOfflineLog( entity );
        }
        if(handler) handler();
        return "_close";
    }
            
    def doCancel() {
        return "_close";
    }
            
}
        