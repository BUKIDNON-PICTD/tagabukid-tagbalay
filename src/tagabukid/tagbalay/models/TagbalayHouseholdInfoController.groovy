import com.rameses.rcp.annotations.*
import com.rameses.rcp.common.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.common.*;
import com.rameses.seti2.models.*;
import com.rameses.util.*;
        
class  TagbalayHouseholdInfoController extends CrudFormModel{
    @Binding
    def binding;
    
    @Service('DateService')
    def dtSvc
    
    @Caller
    def maincontroller
    
        
    @Service("PersistenceService")
    def persistenceSvc;
    
    String title = "Household Infomration";
    
    def parententity
    
    @Service("TagabukidTagbalayService")
    def svc;
    
    @Service('QueryService') 
    def querySvc; 
    
    @Service('UserRoleService')
    def userroleSvc
    
    @PropertyChangeListener
    def listener = [
        'entity.tagbalay' : { 
       
            if(!isCitizenExist()){
                entity.tagbalay.address.text = svc.formatAddress(entity.tagbalay.address,"\n")
                
                binding.refresh();
                maincontroller.reloadphoto(entity.tagbalay);
            }else{
                 MsgBox.err("Profile for " + entity.tagbalay.name + " already exist.");
                 entity.tagbalay = [:]
                 binding.refresh();
            }
        }
        
    ]
    
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
    public void beforeOpen() {
        entity.putAll(parententity)
    }
        
    public void afterOpen(){
        loadtagbalayalinfo()
    }
    public void beforeSave(o){
        if (o == 'create'){
            
            entity.hin = svc.getHIN(entity);
            entity.activeaddress[0] = entity.tagbalay.address;
//            def newsurvey = svc.initSurvey(entity);
//            entity.surveys[0] = newsurvey
        }
    }
    public void afterCreate(){
        entity.putAll(parententity);
        
       
    }
    public void afterSave(){
  
        entity.tagbalay._schemaname = "entity"+entity.tagbalay.type.toLowerCase()
        // entity.tagbalay.putAll(persistenceSvc.read([ _schemaname: 'hrmis_pds', objid: entity.tagbalay.objid])) 
        persistenceSvc.update(entity.tagbalay);
        maincontroller.entity = entity
        maincontroller.reloadSections('open');
     
        // loadBTACSidIncrement()
    }
    
    def loadtagbalayalinfo(){
        entity = persistenceSvc.read([ _schemaname: 'tagbalay', objid: entity.objid])
        entity.tagbalay.putAll(persistenceSvc.read([ _schemaname: "entity"+entity.tagbalay.type.toLowerCase(), objid: entity.tagbalay.objid])) 
//        println entity.tagbalay
    }
    
    def isCitizenExist(){
        def p = [_schemaname: 'tagbalay'];
        def params = [:]
        p.findBy = [tagbalay_objid:entity.tagbalay.objid];
        return querySvc.findFirst(p)
    }
    
    List getSurveyors() {
        def userlist = userroleSvc.getUsers([domain:'TAGBALAY', roles:'SURVEYOR'])
        return userlist.collect{[ 
            objid:it.objid, name:it.name, title:it.title, 
            fullname: it.lastname +', '+ it.firstname +' '+ (it.middlename? it.middlename: '')  
        ]}
    }
    
    def tagbalayinfoListModel = [
       fetchList: { o->
            def list = [];
            if( entity.tagbalayinfos ) {
                return entity.tagbalayinfos;
            }
            return list;
        }
    ] as BasicListModel;
    // PDS to BTACS
    // def loadBTACSidIncrement(){
    //     println "btacsuserid";
    //     def btacsuserid = svc.findbtacsid();
    //     btacsuserid = btacsuserid + 1;
    //     // println btacsuserid;
    // }

    
    //    def getResidentialAddress(){
    ////        if (entity.copyresidential){
    ////            entity.residential.address = entity.tagbalay.address
    ////            return entity.residential.address
    ////        }
    //        if (entity.residential){
    //            entity.residential.address.text = svc.formatAddress(entity.residential.address,"\n")
    //        }else{
    //            entity.tagbalay.address.text = svc.formatAddress(entity.tagbalay.address,"\n")
    //            entity.residential.address = entity.tagbalay.address
    //        }
    //        
    //        return entity.residential.address
    //    }
    //    def getPermanentAddress(){
    ////        if (entity.copypermanent){
    ////            entity.permanent.address = entity.residential.address
    ////            return entity.permanent.address
    ////        }
    //        
    //        if (entity.permanent.address != entity.tagbalay.address){
    //            entity.permanent.address.text = svc.formatAddress(entity.permanent.address,"\n")
    //        }else{
    //            entity.tagbalay.address.text = svc.formatAddress(entity.tagbalay.address,"\n")
    //            entity.permanent.address = entity.tagbalay.address
    //        }
    //        
    //        println entity.permanent.address
    //        return entity.permanent.address
    //    }
    //    def getPhoto() {
    //        return selectedItem.benificiary.photo;
    //    }
}
