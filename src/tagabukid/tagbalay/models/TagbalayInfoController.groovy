import com.rameses.rcp.annotations.*
import com.rameses.rcp.common.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.common.*;
import com.rameses.seti2.models.*;
import com.rameses.util.*;

class TagbalayInfoController{
    @Binding
    def binding;
    
    @Controller
    def workunit;
    
    @Service("TagabukidTagbalayService")
    def svc;

    @Service("PersistenceService")
    def persistenceSvc;
 
    @FormTitle
    def title
    
    @Service('QueryService')
    def querySvc

    def sections;
    def currentSection;
    def entity;
    def barcodeid;

    public void beforeSave(o){
//        if (o == 'create'){
//            entity.controlno = svc.getControlno();
//            entity.entityid = entity.person.objid
//            entity.name = entity.person.name;
//            entity.versions.each{
//                it.versionno = svc.getVersionNo();
//                entity.currentversionno = it.versionno
//                it.person.putAll(entity.person)
//            }
//        }
    }
//    public void afterSave(){
//        loadSections();
//    }

    // boolean isAllowApprove() {
    //     return ( mode=='read' && entity.state.toString().matches('DRAFT|ACTIVE') ); 
    // }
    
    // boolean isAllowPreviewAppointment() {
    //     return ( mode=='read'); 
    // }
    def preview(){
       def op = Inv.lookupOpener("pds:report",[entity: entity]);
       op.target = 'self';
       return op;
    }
    def verify(){
       def op = Inv.lookupOpener("verification:report",[entity: entity]);
       op.target = 'self';
       return op;
    }
    
    public void approve(){
       loadSections('open');
    }

    public void create(){
        title = "New Household Info";
        entity = svc.initCreate();
        loadSections('create');
    }
    public void open(){
//        entity = service.open( [barcodeid: barcodeid,taskid:entity?.taskid,objid:entity?.objid ] );
        if(barcodeid){
            def p = [_schemaname: 'tagbalay'];
            p.findBy = [ 'hin':barcodeid];
            entity =  querySvc.findFirst( p );
        }
        title = entity.hin + " - " + entity.tagbalay.name
        entity = persistenceSvc.read([ _schemaname: 'tagbalay', objid: entity.objid])
        entity.tagbalay.putAll(persistenceSvc.read([ _schemaname: "entity"+entity.tagbalay.type.toLowerCase(), objid: entity.tagbalay.objid])) 
        loadSections('open');
    }
//    public void afterCreate (){
//       
//    }
//    public void afterOpen(){
//        loadSections();
//    }
    void reloadSections(action) {
//        binding.refresh("subform");
       
        entity = persistenceSvc.read([ _schemaname: 'tagbalay', objid: entity.objid])
        entity.tagbalay.putAll(persistenceSvc.read([ _schemaname: "entity"+entity.tagbalay.type.toLowerCase(), objid: entity.tagbalay.objid])) 
        title = entity.hin + " - " + entity.tagbalay.name
        def handlers = Inv.lookupOpeners("tagbalay:section:"+action,[parententity:entity,svc:svc]);
        def selitemid = currentSection?.id; 
        sections.clear();
        sections.addAll( 
            handlers.findAll {
                def vw = it.properties.visibleWhen;
                return  ((!vw)  ||  ExpressionResolver.getInstance().evalBoolean( vw, [parententity:entity,svc:svc] ));     
            }
        ); 

        currentSection = sections.find{ it.id == selitemid } 
        if ( sections && currentSection==null ) {
            currentSection = sections.first(); 
        }
        binding.refresh();
    }

    void loadSections(action) {
        sections = InvokerUtil.lookupOpeners( "tagbalay:section:"+action,[parententity:entity,svc:svc]).findAll {
            def vw = it.properties.visibleWhen;
            return  ((!vw)  ||  ExpressionResolver.getInstance().evalBoolean( vw, [parententity:entity,svc:svc] ));
        }
        if( sections.size()>0 ) {
            currentSection = sections[0];
        }  
    }
    
    void reloadCurrentSection() {
        MsgBox.alert( currentSection.name );
    }
    
    void reloadphoto(tagbalay) {
        entity.tagbalay = tagbalay
        binding.refresh("entity.tagbalay.*");
    }

}