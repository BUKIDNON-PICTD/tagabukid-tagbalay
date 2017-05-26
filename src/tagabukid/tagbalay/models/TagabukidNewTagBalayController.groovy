import com.rameses.rcp.annotations.*
import com.rameses.rcp.common.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import java.rmi.server.*;
import com.rameses.gov.etracs.bpls.controller.*;
import com.rameses.util.*;
import tagabukid.utils.*;
        
public class TagabukidNewTagBalayController extends PageFlowController {
            
    @Script("TagabukidTagBalayInfoUtil")
    def tagbalayinfo
    
    @Service("TagabukidTagBalayService")
    def service
            
    @Service("TagabukidTagBalayHeadVerificationService")
    def verifySvc
            
    @Service('PersistenceService')
    def persistenceSvc; 
    
    @Service('UserRoleService')
    def userroleSvc
    
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
        entity.tagbalay.address = null;
        entity.text = null;
    }
    def editAddress() {
        def h = { o->
            if (handler) handler(o);
            entity.tagbalay.address = o;
            binding.refresh();
        }
        return Inv.lookupOpener( "address:editor", [handler:h, entity:entity, tag: tag] );
    }
    
    def initTagBalayAddress(){
        entity.tagbalay.address = null;
        entity.text = null;
        if(entity.copyAddress){
            entity.tagbalay.address = entity.tagbalay.pangulo.address;
        }
    }
    
    void verifyaddress() {
        if( !entity.tagbalay.address?.type)
        throw new Exception("Error in tagbalay address type-empty. Please check 'type' in tagbalay address");
        entity.tagbalay.address.text = TemplateProvider.instance.getResult("templates/address/" + entity.tagbalay.address.type + ".htm", [entity: entity.tagbalay.address] );
        if(entity.tagbalay.address.text) {
            entity.tagbalay.address.text = entity.tagbalay.address.text.trim();
            entity.tagbalay.address.text = entity.tagbalay.address.text.replace(",\n", "\n");
        }   
        else {
            throw new Exception("Please specify an address");
        }
//        entity.tagbalay.address = entity.tagbalay.address;
        entity.tagbalay.address.completed = true;
       
    }

    void check() {
        searchList  = verifySvc.getList(entity.tagbalay.pangulo.name); 
        if(searchList) {
            pass = false;
            verificationListModel.reload();
        }
        else {
            pass = true;
        }
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
        throw new Exception("Exact Household exists.");
    }
            
    def save(){
        //println entity.tagbalay
        entity = service.create(entity);
        return entity
    }
            
    def print() {
        def op = Inv.lookupOpener( "dts:din", [entity: entity] );
        op.target = 'self';
        return op;
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
            if (colname == 'member') { 
               
                if (entity.tagbalay.pangulo.objid ==  item.member.objid) throw new Exception('Dili pwede pilion ang PANGULO sa pamilya'); 
            }
            if (colname == 'relation') {
                if (item.relation == 'PANGULO') throw new Exception('Dili pwede nga duha ang PANGULO sa pamilya.'); 
            }
        },
        onAddItem: {item-> 
            item.objid = 'MEM'+new UID();
            item.memberid = item.member.objid;
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
    
    List getSurveyors() {
        def userlist = userroleSvc.getUsers([domain:'TAGBALAY', roles:'SURVEYOR'])
        return userlist.collect{[ 
            objid:it.objid, name:it.name, title:it.title, 
            fullname: it.lastname +', '+ it.firstname +' '+ (it.middlename? it.middlename: '')  
        ]}
    }
    
    void updateInfo() {
        addpangulotomember();
        //if requierd na may at least 1 member ang tagbalay
        //membersverify();
        boolean test = false;
        tagbalayinfo.handler = {
            test = true;
        }
        Modal.show(tagbalayinfo.update());
        if(!test) throw new BreakException();
        //tagbalayinfo.verify();
    }
    
    //     def membersverify() {
    //        if(!entity.members) 
    //            throw new Exception("Please specify at least one member of tagbalay");
    //        if(items.find{!it.lobid})
    //            throw new Exception("All lines of business must be specified. lobid is null");
    //    }
    void addpangulotomember(){
        def pangulo = entity.members.find{ it.relation == 'PANGULO' };
        if(pangulo){
            entity.members.remove(pangulo);
        }
         
        def schemaname = 'entity' + (entity.tagbalay.pangulo.type ? entity.tagbalay.pangulo.type :'').toLowerCase(); 
        def o = [:];
        o.member = persistenceSvc.read([ _schemaname: schemaname, objid: entity.tagbalay.pangulo.objid ]); 

        o.objid = 'MEM'+new UID();
        o.memberid = o.member.objid;
        o.name = o.member.name;
        o.relation = 'PANGULO';
        entity.members.add(o);
        memberListHandler.reload();
        
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