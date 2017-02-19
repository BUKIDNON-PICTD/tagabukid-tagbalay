import com.rameses.rcp.annotations.*
import com.rameses.rcp.common.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import java.rmi.server.*;
import com.rameses.util.*;
import com.rameses.osiris2.reports.*;
import java.text.*;

public class OnlineTransactionController {
    @Binding
    def binding;
    
    @Service("TagabukidSubayTransactionService")
    def svc
    
    @Service("DateService")
    def dtsvc
    
    def din;
    def entity;
    def document;
    def mode = "INIT"
    def selectedItem;
    def stats;
    @FormTitle
    def title;
    def completed;
    def destination;
    def test;
    
    def df = new SimpleDateFormat("yyyy-MM-dd");
    def formatDate = { o->
        if(o instanceof java.util.Date) {
            return df.parse( df.format( o ));
        }
        return df.parse( o );     
    }
    
    void init(){
        mode= "INIT";
        title="Select Transaction Mode";
        document =[];
        din = "";
        entity = [:]
        listHandler.reload();
        stats = svc.getStats();
        entity.txndate = dtsvc.getServerDate();
        completed = false;
        destination = false;
        entity.preparedbyname = OsirisContext.env.FULLNAME;
    }
    
    def createnew(){
        init();
        return "default";
    }
    
    def listHandler = [
        fetchList : { return document },
        onRemoveItem : {
            if (MsgBox.confirm('Delete item?')){                
                document.remove(it)
                listHandler.reload();
                return true;
            }
            return false;
        },
        onColumnUpdate:{item,colName ->
            document.each{y ->
                if (item.din == y.din){
                    y.message = item.remarks
                }
            }
        }
    ] as EditorListModel
    
    def addtolist(def doc){
        checkDuplicate(document,doc);
        doc.message = "";
        document << doc;
        din = "";
        binding.refresh('din');
        binding.focus('din');
        listHandler.reload();
        
    }
    
    def searchdocument(){
        def doc = svc.lookupDIN(entity)
        if (!doc){
            throw new Exception("Invalid DIN");
        }
        
        if (doc.size == 1){
            addtolist(doc[0])
        }
        else if(doc.size > 1){
            return Inv.lookupOpener('din:lookup',[
                    entity: entity,
                    onselect :{
                        addtolist(it)
                    }
                ])
        }else{
            throw new Exception("Invalid DIN");
        }
    }

    public def barcodesearch(){
        //        if (!din){
        //            throw new Exception("Invalid DIN");
        //        }
        entity.searchtext = din
        entity.mode = mode
        entity.document = document
        searchdocument()
      
    }
    

    void receive (){
        mode = "receive"
        title= "RECEIVING"
        binding.refresh('din');
        binding.focus('din');
    }
    void outgoing (){
        mode = "outgoing"
        title= "OUTGOING"
        entity.sendtype = 'localoutgoing'
        binding.refresh('din');
        binding.focus('din');
    }
    void send (){
        mode = "send"
        title= "SENDING"
        entity.sendtype = 'local';
        binding.refresh('din');
        binding.focus('din');
    }
    void archived (){
        mode = "archived"
        title= "ARCHIVING"
        binding.refresh('din');
        binding.focus('din');
    }
   
    def getHandler(){
        if(mode!="INIT"){
            if(mode=="send" || mode=="outgoing"){
                return InvokerUtil.lookupOpener(entity.sendtype+':form',[
                        entity: entity
                    ]) 
            }else{
                return InvokerUtil.lookupOpener(mode+':form',[
                        entity: entity
                    ])  
            }
            
        }
    }
    
    def save(){
        if (document.size == 0)
        throw new Exception("Please add at least 1 document to process");
        
        if ((!entity.org && !entity.destinations) && mode.matches('send|archived|outgoing') && !destination){
             destination = true;
             return 'paramter';
        } else if((!entity.org && !entity.destinations) && mode.matches('send|archived') && destination){
             throw new Exception("Destination is Required");
             return 'paramter';
        }
        
        if (!entity.assignee && !entity.oic && mode=='send') 
        throw new Exception("OIC is Required");
        
        entity.document = document
        entity.mode = mode
        if( MsgBox.confirm( "You are about to " + mode + " this transaction. Proceed?")) {
            try{
                svc.processDocument(entity);
                completed = true;
                MsgBox.alert("Transaction Successfull");
                
                
            } catch(Warning w) {
                Modal.show( 'document_redflag:warning', [list: w.info.list] );
                throw new BreakException();
            } catch(e) {
                throw e;
            }
        }
        return "completed";
        
    }
    
    def getInfoHtml() {
        return TemplateProvider.instance.getResult( "tagabukid/subay/views/dtstransaction.gtpl", [entity:entity] );
    }
    
    def findReportOpener( reportData ) { 
        //check first if form handler exists. 
        def o = InvokerUtil.lookupOpener( "subaytransaction:receipt", [reportData:reportData] );
        if ( !o ) throw new Exception("Handler not found"); 

        return o.handle; 
    } 
    void print() {
        def handle = findReportOpener(entity);
        handle.viewReport();
        ReportUtil.print(handle.report,true);
    }
    
    void checkDuplicate(listtofilter,item){
        def data = listtofilter.find{it.din == item.din }
        if (data)
        throw new Exception("Duplicate item is not allowed.")
    }
    
    def close(){
        return '_close'
    }
}