import com.rameses.rcp.annotations.*
import com.rameses.rcp.common.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import java.rmi.server.*
import tagabukid.utils.*;
import com.rameses.common.*;
        
class  TagbalayGeneralInfoSectionController  {
    @Binding
    def binding;
    
    @Caller
    def caller
    
    @Service("DateService")
    def dtsvc
    
//    @Service("TagabukidSubayDocumentService")
//    def svc;
            
    String title = "Tagbalay Info";
   
    def entity;
 
    void init(){
////        isoffline = (entity.isoffline == 1 ? true : false);
////        isowner = svc.checkDocumentOwner(entity.dininventoryid)
//        loadAttachments()
//        listHandler?.load();
    }
    
    def popupChangeInfo(def inv) {
        def popupMenu = new PopupMenuOpener();
        def list = InvokerUtil.lookupOpeners( inv.properties.category).findAll{
            def vw = it.properties.visibleWhen;
            return  ((!vw)  ||  ExpressionResolver.getInstance().evalBoolean( vw, [entity:getEntity(), orgid:OsirisContext.env.ORGID] ));
        }
        list.each{
            popupMenu.add( it );
        }
        return popupMenu;
    }
            
    void refreshForm(){

        binding.refresh('entity.*');
        binding.refresh();
        memberListHandler.reload();
        tagbalayinfoListModel.reload();
    }  
    
    def refresh(){
        //listModel.reload()
        def newlogs = svc.lookupNode([refid:entity.objid,taskid:entity.taskid])
          
        if (newlogs.size == 1){
            entity = svc.open(newlogs[0]);
        }else if (newlogs.size > 1){
            return Inv.lookupOpener('node:lookup',[
                    entity: newlogs,
                    onselect :{
                        entity = svc.open(it)
                    }
                ])
        }
        
        isoffline = (entity.isoffline == 1 ? true : false);
        isowner = svc.checkDocumentOwner(entity.dininventoryid)
        loadAttachments()
        listHandler?.load();
        binding.refresh();  
        caller.entity = entity;
        caller.reloadSections();
    }

    
     def memberListHandler = [
        fetchList:{o-> 
            if (!entity) return null; 
            if (!entity.members) entity.members = [];
            return entity.members; 
        }
    ] as EditorListModel;
    
     def tagbalayinfoListModel = [
       fetchList: { o->
            def list = [];
            if( entity.tagbalayinfos ) {
                return entity.tagbalayinfos;
            }
            return list;
        }
    ] as BasicListModel;
}