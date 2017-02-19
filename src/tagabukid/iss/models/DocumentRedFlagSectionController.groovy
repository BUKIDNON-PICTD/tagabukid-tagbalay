import com.rameses.rcp.annotations.*
import com.rameses.rcp.common.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import java.rmi.server.*;
import com.rameses.osiris2.reports.*;
        
class  DocumentRedFlagSectionController  {
        
    def entity;
    def title = "Red Flags"
            
    @Service("TagabukidSubayRedflagService")
    def service;
            
    def selectedItem;
            
    def redflagListModel = [
        fetchList: { o->
            return service.getList( entity );
        }
    ] as BasicListModel;
            
    def addRedflag() {
        def h = {
            redflagListModel.reload();
        }
//        def rf = [documentid: entity.objid];
        return Inv.lookupOpener( "document_redflag:create", [entity: entity, handler: h] );
    }
            
    def openRedflag() {
        if(!selectedItem) throw new Exception('Please select an item');
        if(!service.isallowedtoopen(OsirisContext.env.USERID,selectedItem.filedby.objid)) throw new Exception('You are not allowed to edit this red flag.');
                
        def h = {
            redflagListModel.reload();
        }
        return Inv.lookupOpener( "document_redflag:open", [entity:selectedItem, handler: h] );
    }
            
    def resolveRedflag() {
        if(!selectedItem) throw new Exception('Please select an item');
        def h = {
            redflagListModel.reload();
        }
        return Inv.lookupOpener( "document_redflag:resolve", [entity:selectedItem, handler: h] );
    }
            
}