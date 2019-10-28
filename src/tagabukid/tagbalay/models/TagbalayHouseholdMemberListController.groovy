
import com.rameses.rcp.annotations.*
import com.rameses.rcp.common.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.common.*;
import com.rameses.seti2.models.*;
import com.rameses.util.*;
import java.rmi.server.UID


class  TagbalayHouseholdMemberListController{
    @Binding
    def binding;

    def parententity
    def svc
        
    @Service("DateService")
    def dtSvc
    
    @Service("PersistenceService")
    def persistenceSvc;
    
    String title = "Household Member Info";
    
    @Service('QueryService') 
    def querySvc; 
    
//    @Service('LOVService')
//    def lovService;
    
//    @Script("TagabukidASMISInfoUtil")
//    def asmisinfo
    
    def selectedMember;
    def currententity;
    def entityRelationList = LOV.TAGABUKID_ENTITY_RELATION;
    def entity = [:]
//    boolean isCreateAllowed(){
//        return false
//    }
//    
//    boolean isViewReportAllowed(){
//        return false
//    }
//
//    boolean isPrintReportAllowed(){
//        return false
//    }
//    
//    boolean isShowNavigation(){
//        return false
//    }
//    
//    boolean isAllowCapture(){
//        return selectedAssistanceItem.state == 'DRAFT';
//    }
   
    void init() {
     
        entity.putAll(parententity)
        
        memberListHandler.load();
        currententity = entity
    }
    
    def getOpener(){
        if (! selectedMember) return null;
        return InvokerUtil.lookupOpener('tagbalay:member:open', [member: [objid:selectedMember.objid]]);
    }
    
    public void beforeSave(o){
       
    }
    public void afterSave(){
        currententity = entity
        
        entity.activemembers.each{
            it.member._schemaname = "entityindividual"
            persistenceSvc.update(it.member)
        }
        memberListHandler.load();
    }
    
   def memberListHandler = [
        fetchList:{
            entity?.activemembers.each{
                   def schemaname = 'entityindividual'; 
                   it.member = persistenceSvc.read([ _schemaname: schemaname, objid: it.member.objid ]); 
                   it.member.entityname = it.member.name
                   if (!it.height) it.height = 0;
                   if (!it.weight) it.weight = 0;
                    Calendar now = Calendar.getInstance();
                    Calendar birthDay = Calendar.getInstance();
                    birthDay.setTime(it.member.birthdate);
                    int year1 = now.get(Calendar.YEAR);
                    int year2 = birthDay.get(Calendar.YEAR);
                    it.member.age = year1-year2
            }
            return entity?.activemembers
        },
        createItem:{
            return [
                objid: 'MEM' + new java.rmi.server.UID(),
                parentid: entity.objid,
                height: 0,
                weight: 0
            ]
        },
//        onColumnUpdate: {item,colname-> 
//            if (colname == 'member') { 
//                def o = entity.activemembers.find{ it.member.objid == item.member.objid } 
//                if (o) throw new Exception('This member already exist in the list. Please select another one.'); 
//            } 
//            if (colname == 'member') { 
//                if (entity.tagbalay.pangulo.objid ==  item.member.objid) throw new Exception('Dili pwede pilion ang PANGULO sa pamilya'); 
//            }
//            if (colname == 'relation') {
//                if (item.relation == 'PANGULO') throw new Exception('Dili pwede nga duha ang PANGULO sa pamilya.'); 
//            }
//        },
        onAddItem: {item-> 
            //specific lang ang member dapat individual lang
            def schemaname = 'entityindividual'; 
            item.member = persistenceSvc.read([ _schemaname: schemaname, objid: item.member.objid ]); 
            
            if (item.member.birthdate){
                Calendar now = Calendar.getInstance();
                Calendar birthDay = Calendar.getInstance();
                birthDay.setTime(item.member.birthdate);
                int year1 = now.get(Calendar.YEAR);
                int year2 = birthDay.get(Calendar.YEAR);
                item.member.age = year1-year2
            }
//            item.objid = 'MEM' + new UID();
//            item.memberid = item.member.objid;
//            item.name = item.member.name;
//            item.gender = item.member.gender;
            entity.activemembers.add(item); 
            item._schemaname = 'tagbalay_active_member'
            persistenceSvc.create(item);
            memberListHandler?.load();
            binding.refresh('membercount');
//            println item.member
//            if (item.member.gender == "M"){
//                entity.membermale.add(item)
//            }else{
//                entity.memberfemale.add(item)
//            }
        }, 
        onRemoveItem: {item ->
            if (item.relation == 'PANGULO') throw new Exception('Dili pwede i.delete ang pangulo.'); 
            if (MsgBox.confirm('Are you sure you want to remove this item?')){       
             
                entity.activemembers.remove(entity.activemembers.find{it.objid == item.objid}); 
                persistenceSvc.removeEntity([_schemaname:'tagbalay_active_member',objid:item.objid])
                memberListHandler?.load();
                binding.refresh('membercount');
                return true;
            }
            return false;
        }
    ] as EditorListModel;
    
    
    void checkDuplicateName(listtofilter,item){
      def data = listtofilter.find{it.assistancetype.objid == item.assistancetype.objid }
      def databasedup = persistenceSvc.read([ _schemaname: 'master_assistance_type', objid:item.assistancetype.objid]);

      if (data)
        throw new Exception("Duplicate item is not allowed.")
    }
    
    def infoListModel = [
        fetchList: { selectedAssistanceItem?.infos },
//        createItem : {
//            return[
//               emergency:[address:[:]]
//            ]
//        },
//        onRemoveItem : {
//            if (MsgBox.confirm('Delete item?')){                
//                //service.deleteLanguageAndDialectItem(it)               
//                entity.activeassistances.remove(it)
//                assistanceListHandler.reload();
//                return true;
//            }
//            return false;
//        },
//        onAddItem : {
//            it.objid = 'AA'+new UID();
////            def schemaname = 'entityindividual'; 
////            it.citizen = persistenceSvc.read([ _schemaname: schemaname, objid: it.citizen.objid ]); 
//////            it.emergency = persistenceSvc.read([ _schemaname: schemaname, objid: it.emergency.objid ]); 
//            entity.activeassistances << it; 
//           
//        },
//        validate:{li->
////            println li.item.assista
//            def item=li.item.assistance;
//            checkDuplicateName(entity.activeassistances,item.assistance);
//        }
    ] as EditorListModel;
    
    void updateInfo() {
        boolean test = false;
        asmisinfo.handler = {
            test = true;
        }
        
        Modal.show(asmisinfo.update());
        if(!test) throw new BreakException();
        //check if info is valid
        //docinfo.verify();
    }
    
    def getMembercount(){
        return entity.activemembers.size();
    }
    
    
}
