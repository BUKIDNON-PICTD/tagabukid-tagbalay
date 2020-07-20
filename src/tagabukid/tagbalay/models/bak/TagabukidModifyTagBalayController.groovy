import com.rameses.rcp.annotations.*
import com.rameses.rcp.common.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import java.rmi.server.*;
import com.rameses.gov.etracs.bpls.controller.*;
import com.rameses.util.*;
import tagabukid.utils.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TagabukidModifyTagBalayController extends PageFlowController {
       
    @Binding
    def binding;
    
    @Caller
    def caller;
   
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
    
    def entity;
    boolean pass =  true;
    boolean runinfo = false;
    def searchList;
    def verificationSelectedItem;
    def attachmentSelectedItem;
    def tag;
    def handler;
    def entityRelationList = LOV.TAGABUKID_ENTITY_RELATION;
    
    void init() {
        entity = caller.entity
        entity.members.each{
            def o = [:]
            o.member = persistenceSvc.read([ _schemaname: 'entityindividual', objid: it.memberid ])
            Calendar now = Calendar.getInstance();
            Calendar birthDay = Calendar.getInstance();
            birthDay.setTime(o.member.birthdate);
            int year1 = now.get(Calendar.YEAR);
            int year2 = birthDay.get(Calendar.YEAR);
            it.name = o.member.name;
            it.gender = o.member.gender;
            it.age = year1-year2
            it.member = it
           
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
            if(searchList && caller.entity.tagbalay.pangulo.objid != entity.tagbalay.pangulo.objid) {
                runinfo = true;
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
        entity = service.update(entity);
//        caller.entity.title = entity.title
//        caller.entity.description = entity.description
//        caller.entity.author = entity.author
//        caller.entity.documenttype = entity.documenttype
//        caller.entity.child = entity.child
        caller?.refreshForm();
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
            def schemaname = 'entity' + (item.member.type ? item.member.type :'').toLowerCase(); 
            item.member = persistenceSvc.read([ _schemaname: schemaname, objid: item.member.objid ]); 
            
            Calendar now = Calendar.getInstance();
            Calendar birthDay = Calendar.getInstance();
            birthDay.setTime(item.member.birthdate);
            int year1 = now.get(Calendar.YEAR);
            int year2 = birthDay.get(Calendar.YEAR);
         
            
            item.objid = 'MEM'+new UID();
            item.memberid = item.member.objid;
            item.name = item.member.name;
            item.gender = item.member.gender;
            item.age = year1-year2
            entity.members.add(item); 
//            println item.member
//            if (item.member.gender == "M"){
//                entity.membermale.add(item)
//            }else{
//                entity.memberfemale.add(item)
//            }
        }, 
        onRemoveItem: {item-> 
            if (item.relation == 'PANGULO') throw new Exception('Dili pwede i.delete ang pangulo.'); 
            if (!MsgBox.confirm('Are you sure you want to remove this item?')) return false;
            entity.members.remove(item); 
            return true;
        }
    ] as EditorListModel;
    
    def getLookupMember() {
        return InvokerUtil.lookupOpener('individualentitywide:lookup'); 
    }             
    
    List getSurveyors() {
        def userlist = userroleSvc.getUsers([domain:'TAGBALAY', roles:'SURVEYOR'])
        return userlist.collect{[ 
            objid:it.objid, name:it.name, title:it.title, 
            fullname: it.lastname +', '+ it.firstname +' '+ (it.middlename? it.middlename: '')  
        ]}
    }
    
    void updateInfo() {
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
    
//    void addcouplestomember(){
//        entity.couples.each{item->
//            def x = [:]
//            x = [_schemaname:'entitymultiple']
//            x.objid = item.objid;
//            x.members = []
//            if(item.male){
//               item.male = entity.members.find{it.name == item.male}
//               item.male.objid = 'MEM'+new UID();
//               item.male.entityid = item.male.objid ;
//               x.members.add([member:item.male]);
//            }
//            if(item.female){
//                item.female = entity.members.find{it.name == item.female}
//                item.female.objid = 'MEM'+new UID();
//                item.female.entityid = item.female.objid;
//                x.members.add([member:item.female]);
//            }
//            x.fullname = item.male.name;
//            x.address = entity.tagbalay.address
//            x.fullname = (item.couplesfullname ? item.couplesfullname + " AND " + (item.female.name? item.female.name: "") : item.female.name)            ;
//            persistenceSvc.create(x);
//            def o = [:];
//            o.member = persistenceSvc.read([ _schemaname: schemaname, objid: x.objid ]); 
//            o.objid = x.objid;
//            o.memberid = o.member.objid;
//            o.relation = 'COUPLE';
//            entity.members.add(o);
//            memberListHandler.reload();
//        }
//           
//    }
    void addpangulotomember(){
        
        def pangulo = entity.members.find{ it.relation == 'PANGULO' };
//        println pangulo
//        println caller.entity.pangulo.objid
        if (pangulo.memberid != caller.entity.tagbalay.pangulo.objid){
            if(pangulo){
                entity.members.remove(pangulo);
            }

            def schemaname = 'entity' + (entity.tagbalay.pangulo.type ? entity.tagbalay.pangulo.type :'').toLowerCase(); 
            def o = [:];
            o.member = persistenceSvc.read([ _schemaname: schemaname, objid: entity.tagbalay.pangulo.objid ]); 

            Calendar now = Calendar.getInstance();
            Calendar birthDay = Calendar.getInstance();
            birthDay.setTime(o.member.birthdate);

            int year1 = now.get(Calendar.YEAR);
            int year2 = birthDay.get(Calendar.YEAR);

            o.objid = 'MEM'+new UID();
            o.memberid = o.member.objid;
            o.name = o.member.name;
            o.gender = o.member.gender;
            o.age = year1-year2
            o.relation = 'PANGULO';
            entity.members.add(o);
        }
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