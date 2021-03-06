import com.rameses.rcp.annotations.*
import com.rameses.rcp.common.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.common.*;
import com.rameses.seti2.models.*;
import com.rameses.util.*;
import java.rmi.server.*
import tagabukid.utils.*;
        
class  TagbalayHouseholdInfoController extends CrudFormModel{
    @Binding
    def binding;
    
    @Service('DateService')
    def dtSvc
    
    @Caller
    def maincontroller
    
    // @Env
	// def env;

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
    
    def isNotQualified = false
    // def state = "NOTQUALIFIED"
//    boolean isNotQualified() {
//        return false
//    }
    public void beforeOpen() {
        entity.putAll(parententity)
    }
    public void afterOpen(){
        loadtagbalayalinfo()
       
        if(entity.state == 'NOTQUALIFIED'){
            isNotQualified = true
        }

    }
    public void beforeSave(o){
        if (o == 'create'){
            binding.refresh();
            def individual = persistenceSvc.read([ _schemaname: "entity"+entity.tagbalay.type.toLowerCase(), objid: entity.tagbalay.objid]).findAll{it.value!=null};
            def tagbalay = entity.tagbalay;

            // if (!individual.middlename) throw new Exception('Middlename is required');
            
            entity.tagbalay = tagbalay + individual;
            entity.controlno = svc.getControlNo(entity);
            entity.activeaddress[0] = entity.tagbalay.address;
            entity.activemembers[0] = [
                objid: 'MEM' + new java.rmi.server.UID(),
                parentid: entity.objid,
                member : entity.tagbalay,
                height : entity.height,
                weight : entity.weight,
                relation: 'PANGULO'
            ]
            entity.verify[0] = [
                objid                           : 'VE' + new java.rmi.server.UID(),
                parentid                        : entity.objid,
                date                            : dtSvc.getServerDate(),
                state                           : 'NOTQUALIFIED',
                remarks                         : 'NEW MEMBER',
                recordlog   : [
                    datecreated : dtSvc.getServerDate(),
                    createdbyuser : OsirisContext.env.FULLNAME,
                    createdbyuserid : OsirisContext.env.USERID,
                    dateoflastupdate : dtSvc.getServerDate(),
                    lastupdatedbyuser : OsirisContext.env.FULLNAME,
                    lastupdatedbyuserid : OsirisContext.env.USERID,
                ]
            ]
            // verifyloglist()

        }else if (o == 'update'){
            def individual = persistenceSvc.read([ _schemaname: "entity"+entity.tagbalay.type.toLowerCase(), objid: entity.tagbalay.objid]).findAll{it.value!=null};
            def tagbalay = entity.tagbalay;
            
            if (!individual.middlename) throw new Exception('Middlename is required');
            
            entity.tagbalay = tagbalay + individual;
            
            if(!(entity.activemembers.findAll{it.relation == 'PANGULO'}.collect{it.member.objid}).contains(entity.tagbalay.objid))
            {   
                entity.activemembers.findAll{it.relation == 'PANGULO'}.each{
                    it._schemaname = 'tagbalay_active_member';
                    persistenceSvc.removeEntity(it);
                }
                entity.activemembers.removeAll(entity.activemembers.findAll{it.relation == 'PANGULO'}); 
                
                def newhead = [
                    objid: 'MEM' + new java.rmi.server.UID(),
                    parentid: entity.objid,
                    member : entity.tagbalay,
                    height : entity.height,
                    weight : entity.weight,
                    relation: 'PANGULO'
                ]
                entity.activemembers.add(newhead);
                // println entity.activemembers;
            }
        }
    }

    def settoqualifiedasmis(o){
        def reason = MsgBox.prompt('Enter Reason')
        if (reason){
            svc.updateASMISstate(entity,reason)
            verifyloglist(reason)
            MsgBox.alert("ASMIS profile qualified.");
            
            isNotQualified = false;
            
            binding.refresh();
        }
    }
    def settonotqualifiedasmis(o){
        def reason = MsgBox.prompt('Enter Reason')
        if (reason){
            svc.updateASMISstate(entity,reason)
            verifyloglist(reason)
            MsgBox.alert("ASMIS profile disqualified.");
            
            isNotQualified = true;
            
            binding.refresh();
        }
    }
    def state = "NOTQUALIFIED"
    def verifyloglist(reason){
        if (entity.state == "NOTQUALIFIED"){
            state = "QUALIFIED"
        }else {
            state = "NOTQUALIFIED"
        }
        def newverify = [
                objid                           : 'VE' + new java.rmi.server.UID(),
                parentid                        : entity.objid,
                date                            : dtSvc.getServerDate(),
                state                           : state,
                remarks                         : (reason?reason: 'NEW MEMBER'),
                recordlog   : [
                    datecreated : dtSvc.getServerDate(),
                    createdbyuser : OsirisContext.env.FULLNAME,
                    createdbyuserid : OsirisContext.env.USERID,
                    dateoflastupdate : dtSvc.getServerDate(),
                    lastupdatedbyuser : OsirisContext.env.FULLNAME,
                    lastupdatedbyuserid : OsirisContext.env.USERID,
                ]
            ]
        newverify._schemaname = 'tagbalay_verify'
        persistenceSvc.create(newverify)
    }

    public void afterCreate(){
        entity.putAll(parententity);
        
       
    }
    public void afterSave(){
  
        entity.tagbalay._schemaname = "entity"+entity.tagbalay.type.toLowerCase();
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
        def userlist = userroleSvc.getUsers([domain:'TAGBALAY', roles:'ENCODER'])
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
