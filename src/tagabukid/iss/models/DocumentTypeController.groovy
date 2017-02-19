import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
        
public class TagabukidSubayDocumentTypeModel extends CRUDController
{
    String serviceName = 'TagabukidSubayDocumentTypeService'
    String entityName  = 'documenttype'
    String prefixId    = 'DT'
            
    def selectedItem;
        
    Map createEntity(){
        return [haschild:false,org:[]]
    }
            
    public void afterOpen(Object entity){
        entity.putAll(service.open(entity));
    }
            
    def getLookupOrg(){
        return Inv.lookupOpener('hrisorg:lookup',[
                onselect :{
                    selectedItem.OrgUnitId = it.OrgUnitId
                    selectedItem.name = it.Entity.Name
                    selectedItem.code = it.Entity.AcronymAbbreviation
                }
            ])
    }
        
    def listHandler = [
        getRows : {entity.org.size() + 1 },
        fetchList: { entity.org },
        onRemoveItem : {
            if (MsgBox.confirm('Delete item?')){    
                service.deleteDocumentTypeOrg(it)              
                entity.org.remove(it)
                listHandler?.load();
                return true;
            }
            return false;
        },
        onAddItem : {
            entity.org << it;
        },
        validate:{li->
            def item=li.item;
            checkDuplicate(entity.org,item);
        }
    ] as EditorListModel


    void checkDuplicate(listtofilter,item){
        def data = listtofilter.find{it.OrgUnitId == item.OrgUnitId }
        if (data)
        throw new Exception("Duplicate item is not allowed.")
    }   
            
}