import com.rameses.rcp.annotations.*
import com.rameses.rcp.common.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.common.*;
import java.rmi.server.*
import com.rameses.util.*;
import com.rameses.gov.etracs.bpls.business.*;

class  DocumentInfoController {
    @Script("TagabukidSubayDocumentInfoUtil")
    def docinfo
    
    @Service("TagabukidSubayDocumentService")
    def service;

    @FormId
    def formId

    @FormTitle
    def title

    @Binding
    def binding;

    def entityName = "documentinfo";
    def entity;

    def sections;
    def currentSection;
    def barcodeid;
    def startstep;

    def openByBIN() {
        MsgBox.alert( 'open business by BIN '+barcodeid ); 
    }
            
    void open() {
        if (entity?.filetype=='document_incoming'){
//            entity.objid = entity.data.objid;
            entity.taskid = entity.data.taskid;
        }
        entity = service.open( [barcodeid: barcodeid,taskid:entity?.taskid,objid:entity?.objid ] );
        title = entity.title + ' (' + entity.din + ')';
        loadSections();
        formId = entity.objid;
//        println entity
    }

    void reloadSection() {
        binding.refresh("subform");
    }

    void loadSections() {
        sections = InvokerUtil.lookupOpeners( "subaydocument:section", [entity: entity ] ).findAll {
            def vw = it.properties.visibleWhen;
            return  ((!vw)  ||  ExpressionResolver.getInstance().evalBoolean( vw, [entity:entity] ));
        }
        if( sections.size()>0 ) {
            currentSection = sections[0];
        }  
    }

    void reloadCurrentSection() {
        MsgBox.alert( currentSection.name );
    }
                
    def showParent() {
        if( !entity.parentid )
        throw new Exception("No parent document");
        def parent = [:]
        parent.objid = entity.parentid 
        return Inv.lookupOpener( "subaydocument:open", [entity: parent] ); 
    }
            
    def showDocInfo() {
        boolean test = false;
        docinfo.handler = {
            test = true;
        }
        try{
            Modal.show(docinfo.update());
            if(!test) throw new BreakException();
        }catch(e){

        }
    }

}