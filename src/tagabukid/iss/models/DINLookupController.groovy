import com.rameses.rcp.common.*
import com.rameses.rcp.annotations.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*

class DINLookupController extends LookupController
{
    @Service("TagabukidSubayTransactionService")
    def svc

    def state;
    def onselect 
    def entity;
         
    Column[] getColumns() {
        return [
            new Column(name:"din", caption:"DIN", width:250),
            new Column(name:"title", caption:"Title", width:250),
        ]
    }
            
    List fetchList( Map params ) { 
        params.searchtext = entity.searchtext
        params.mode = entity.mode
        params.document = entity.document
        return svc.lookupDIN(params)
    }
           
}  