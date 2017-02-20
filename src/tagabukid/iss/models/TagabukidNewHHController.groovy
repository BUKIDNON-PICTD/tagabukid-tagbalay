import com.rameses.rcp.annotations.*
import com.rameses.rcp.common.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import java.rmi.server.*;
import com.rameses.gov.etracs.bpls.controller.*;
import com.rameses.util.*;
import tagabukid.utils.*;
        
public class TagabukidNewHHController extends PageFlowController {
            
    @Script("TagabukidSubayDocumentInfoUtil")
    def docinfo
            
    @Service("TagabukidSubayDocumentService")
    def service
            
    @Service("TagabukidSubayTitleVerificationService")
    def verifySvc
            
    @Service('PersistenceService')
    def persistenceSvc; 
    
    def entity;
    boolean pass;
    def searchList;
    def verificationSelectedItem;
    def attachmentSelectedItem;
    
    void init() {
        entity = service.initNew(entity)
        loadAttachments()
        reset();
    }
    
    void initAddressType() {
        owner.reload(); //so we can reload address
        if( application.copyOwnerAddress ) {
            def map = [:];
            map.putAll( entity.business.owner.address ); 
            map.objid = null; 

            entity.business.address = map; 
            address.addressType = entity.business.address.type;
        }
        else {
            address.addressType = application.addressType;
        }
    }

    void check() {
        searchList  = verifySvc.getList(entity.title); 
        if(searchList) {
            pass = false;
            verificationListModel.reload();
        }
        else {
            pass = true;
        }
    }
    
    void checkdin() {
        def dininv = service.verifydin(entity.din); 
        entity.din = dininv.din
        entity.dininventoryid = dininv.inv.objid
        binding.refresh('entity.din');
        pass = true
    }

    def verificationListModel = [
        fetchList: { o-> return searchList;}
    ] as BasicListModel;

    void reset() {
        searchList = [];
        verificationListModel.reload();
    }

    void verify() {
        if( searchList.find{ it.weight == 100 } )
        throw new Exception("Exact document title already exists. Please choose another document title");
    }
            
    def save(){
        entity = service.create(entity);
        return entity
    }
            
    def attachmentListHandler = [
        fetchList : { return entity.attachments },
    ] as BasicListModel
            
    void loadAttachments(){
        entity.attachments = [];
        try{
            entity.attachments = TagabukidDBImageUtil.getInstance().getImages(entity?.objid);
        }
        catch(e){
            println 'Load Attachment error ============';
            e.printStackTrace();
        }
        attachmentListHandler?.load();
    }

    def addAttachment(){
        return InvokerUtil.lookupOpener('upload:attachment', [
                entity : entity,
                afterupload: {
                    loadAttachments();
                }
            ]);
    }

    void deleteAttachment(){
        if (!attachmentSelectedItem) return;
        if (MsgBox.confirm('Delete selected Attachment?')){
            TagabukidDBImageUtil.getInstance().deleteImage(attachmentSelectedItem.objid);
            loadAttachments();
        }
    }


    def viewAttachment(){
        if (!attachmentSelectedItem) return null;

        if (attachmentSelectedItem.extension.contains("pdf")){
            return InvokerUtil.lookupOpener('attachmentpdf:view', [
                    entity : attachmentSelectedItem,
                ]); 
        }else{
            return InvokerUtil.lookupOpener('attachment:view', [
                    entity : attachmentSelectedItem,
                ]); 
        }

    }
            
    def print() {
        def op = Inv.lookupOpener( "dts:din", [entity: entity] );
        op.target = 'self';
        return op;
    }
            
    def getLookupDocumentType(){
        return Inv.lookupOpener('documenttype:lookup',[
                onselect :{
                    entity.documenttype = it;
                },
            ])
    }
    
     def memberListHandler = [
        fetchList:{o-> 
            if (!entity) return null; 
            if (!entity.members) entity.members = [];
            return entity.members; 
        },
        onColumnUpdate: {item,colname-> 
            if (colname == 'member') { 
                def o = entity.members.find{ it.member.objid == item.member.objid } 
                if (o) throw new Exception('This member already exist in the list. Please select another one.'); 
            } 
        },
        onAddItem: {item-> 
            item.objid = 'MEM'+new UID();
            item.entityid = entity.objid; 
            entity.members.add(item); 
            println entity.members
        }, 
        onRemoveItem: {item-> 
            if (!MsgBox.confirm('Are you sure you want to remove this item?')) return false;
            entity.members.remove(item); 
            return true;
        }
    ] as EditorListModel;
    
     def getLookupMember() {
        return InvokerUtil.lookupOpener('entity:lookup', ['query.type': 'INDIVIDUAL','allowSelectEntityType' : false]); 
    }             
            
    def getDetailHandler(){
        if(entity.documenttype.haschild){
            return InvokerUtil.lookupOpener('documentchild:form',[
                    entity: entity
                ])
        }
    }
    
    def listHandler = [
        fetchList : { return entity.child },
        onRemoveItem : {
            if (MsgBox.confirm('Delete item?')){                
                entity.child.remove(it)
                listHandler?.load();
                return true;
            }
            return false;
        },
        onColumnUpdate:{item,colName ->
            entity.child.each{y ->
                if (item.din == y.din){
                    y.message = item.remarks
                }
            }
        }
    ] as BasicListModel
            
    void updateInfo() {
        boolean test = false;
        docinfo.handler = {
            test = true;
        }
        if(entity.documenttype.handler ){
            Modal.show(docinfo.update());
            if(!test) throw new BreakException();
        }
        //check if info is valid
        //docinfo.verify();
    }
    
    public def addEntity() {
//        def stype = getEntityType(); 
//        if ( !stype || stype=='entity' ) stype = 'entityindividual';
        def stype = 'entityindividual';
        
        def params = [:]; 
        params.onselect = { o-> 
            fireOnselect( o ); 
        };
        params.allowSelect = true;
        def opener = null; 
        try {
            opener = Inv.lookupOpener( stype +':create', params );
        } catch(Throwable t) {;}
        if ( !opener )
            throw new Exception("No sufficient permission to add entity");
        return opener; 
    } 

    void fireOnselect( o ) { 
        def schemaname = 'entity' + (o.type ? o.type :'').toLowerCase(); 
        o.member = persistenceSvc.read([ _schemaname: schemaname, objid: o.objid ]); 
        
        o.member.objid = 'MEM'+new UID();
        o.member.entityid = entity.objid; 
        entity.members.add(o);
        memberListHandler.reload();
        
    }
    
}