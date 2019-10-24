
import com.rameses.rcp.annotations.*
import com.rameses.rcp.common.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.common.*;
import com.rameses.seti2.models.*;
import com.rameses.util.*;
import java.rmi.server.UID


class  TagbalayHouseholdMemberInfoController extends CrudFormModel{
    @Binding
    def binding;

    def parententity
    def svc
        
    @Service("DateService")
    def dtSvc
    
    @Service("PersistenceService")
    def persistenceSvc;
    
    @FormTitle
    def title
    
    @Service('QueryService') 
    def querySvc; 
    
//    @Service('LOVService')
//    def lovService;
    
//    @Script("TagabukidASMISInfoUtil")
//    def asmisinfo
    
    def selectedMember;
    def currententity;
    def entityRelationList = LOV.TAGABUKID_ENTITY_RELATION;
    def member
    boolean isCreateAllowed(){
        return false
    }
    
    boolean isViewReportAllowed(){
        return false
    }

    boolean isPrintReportAllowed(){
        return false
    }
    
    boolean isShowNavigation(){
        return false
    }
    
    boolean isAllowCapture(){
        return selectedAssistanceItem.state == 'DRAFT';
    }
   
    public void beforeOpen() {
        entity.objid = member.objid
    }
    
    public void afterOpen() {
        entity.member = persistenceSvc.read([ _schemaname: 'entityindividual', objid: entity.member.objid ]); 
        Calendar now = Calendar.getInstance();
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTime(entity.member.birthdate);
        int year1 = now.get(Calendar.YEAR);
        int year2 = birthDay.get(Calendar.YEAR);
        entity.member.age = year1-year2
        title = entity.member.age  + " - " + entity.member.name;
        
    }
    
    public void afterSave(){
        entity.member._schemaname = "entityindividual"
        persistenceSvc.update(entity.member);
        entity.member = persistenceSvc.read([ _schemaname: 'entityindividual', objid: entity.member.objid ]); 
    }
   

}
