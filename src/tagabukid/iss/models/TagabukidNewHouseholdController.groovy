import com.rameses.rcp.annotations.*
import com.rameses.rcp.common.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import java.rmi.server.*;
import com.rameses.gov.etracs.bpls.controller.*;
import com.rameses.util.*;
import tagabukid.utils.*;
        
public class TagabukidNewHouseholdController extends PageFlowController {
            
    @Script("TagabukidHouseholdInfoUtil")
    def householdinfo
    
    @Service("TagabukidHouseholdService")
    def service
            
    @Service("TagabukidHouseholdHeadVerificationService")
    def verifySvc
            
    @Service('PersistenceService')
    def persistenceSvc; 
    
    @Service('LOVService')
    def lovService;
    
    def entity;
    boolean pass;
    def searchList;
    def verificationSelectedItem;
    def attachmentSelectedItem;
    def tag;
    def handler;
    def entityRelationList = LOV.TAGABUKID_ENTITY_RELATION;
    
    void init() {
        entity = service.initNew()
        memberListHandler.reload();
        addressreset();
       
        //        loadAttachments()
        //        reset();
    }
    void addressreset() {
        entity.household.address = null;
        entity.text = null;
    }
    def editAddress() {
        def h = { o->
            if (handler) handler(o);
            entity.household.address = o;
            binding.refresh();
        }
        return Inv.lookupOpener( "address:editor", [handler:h, entity:entity, tag: tag] );
    }
    
    def initHouseholdAddress(){
        if(entity.copyAddress){
            entity.household.address = entity.pangulo.address;
        }
    }
    
    void verifyaddress() {
        if( !entity.household.address?.type)
        throw new Exception("Error in household address type-empty. Please check 'type' in household address");
        entity.household.address.text = TemplateProvider.instance.getResult("templates/address/" + entity.household.address.type + ".htm", [entity: entity.household.address] );
        if(entity.household.address.text) {
            entity.household.address.text = entity.household.address.text.trim();
            entity.household.address.text = entity.household.address.text.replace(",\n", "\n");
        }   
        else {
            throw new Exception("Please specify an address");
        }
        entity.household.address = entity.household.address;
        entity.household.address.completed = true;
    }

    void check() {
        searchList  = verifySvc.getList(entity.pangulo.name); 
        if(searchList) {
            pass = false;
            verificationListModel.reload();
        }
        else {
            pass = true;
        }
    }
    
    //    void checkhin() {
    //        def dininv = service.verifydin(entity.hin); 
    //        entity.hin = dininv.hin
    //        entity.dininventoryid = dininv.inv.objid
    //        binding.refresh('entity.hin');
    //        pass = true
    //    }

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
            
    //    void loadAttachments(){
    //        entity.attachments = [];
    //        try{
    //            entity.attachments = TagabukidDBImageUtil.getInstance().getImages(entity?.objid);
    //        }
    //        catch(e){
    //            println 'Load Attachment error ============';
    //            e.printStackTrace();
    //        }
    //        attachmentListHandler?.load();
    //    }

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
            
    //    def getLookupDocumentType(){
    //        return Inv.lookupOpener('documenttype:lookup',[
    //                onselect :{
    //                    entity.documenttype = it;
    //                },
    //            ])
    //    }
    
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
            if (colname == 'member') { 
               
                if (entity.pangulo.objid ==  item.member.objid) throw new Exception('Dili pwede pilion ang PANGULO sa pamilya'); 
            }
            if (colname == 'relation') {
                if (item.realtion == 'PANGULO') throw new Exception('Dili pwede nga duha ang PANGULO sa pamilya.'); 
            }
        },
        onAddItem: {item-> 
            item.objid = 'MEM'+new UID();
            item.hhmid = item.member.objid;
            item.name = item.member.name;
            entity.members.add(item); 
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
    
    //    def listHandler = [
    //        fetchList : { return entity.child },
    //        onRemoveItem : {
    //            if (MsgBox.confirm('Delete item?')){                
    //                entity.child.remove(it)
    //                listHandler?.load();
    //                return true;
    //            }
    //            return false;
    //        },
    //        onColumnUpdate:{item,colName ->
    //            entity.child.each{y ->
    //                if (item.din == y.din){
    //                    y.message = item.remarks
    //                }
    //            }
    //        }
    //    ] as BasicListModel
            
    //    void updateInfo() {
    //                boolean test = false;
    //                docinfo.handler = {
    //                    test = true;
    //                }
    //                if(entity.documenttype.handler ){
    //                    Modal.show(docinfo.update());
    //                    if(!test) throw new BreakException();
    //                }
    //                //check if info is valid
    //                //docinfo.verify();
    //    }
    
    void updateInfo() {
       
        addpangulotomember();
        //if requierd na may at least 1 member ang household
        //membersverify();
        boolean test = false;
        householdinfo.handler = {
            test = true;
        }
        Modal.show(householdinfo.update());
        if(!test) throw new BreakException();
        //householdinfo.verify();
    }
    
    //     def membersverify() {
    //        if(!entity.members) 
    //            throw new Exception("Please specify at least one member of household");
    //        if(items.find{!it.lobid})
    //            throw new Exception("All lines of business must be specified. lobid is null");
    //    }
    void addpangulotomember(){
        if(!entity.members.find{ it.relation == 'PANGULO' } ){
            def schemaname = 'entity' + (entity.pangulo.type ? entity.pangulo.type :'').toLowerCase(); 
            def o = [:];
            o.member = persistenceSvc.read([ _schemaname: schemaname, objid: entity.pangulo.objid ]); 

            o.objid = 'MEM'+new UID();
            o.hhmid = o.member.objid;
            o.name = o.member.name;
            o.relation = 'PANGULO';
            entity.members.add(o);
            memberListHandler.reload();
        }
        
        
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
    
    void remove()  {
        if( !selectedMember ) return;
        entity.members.remove(selectedMember);
        memberListHandler.reload();
    }
    
}