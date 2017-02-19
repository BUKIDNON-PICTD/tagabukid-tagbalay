import com.rameses.rcp.common.*
import com.rameses.rcp.annotations.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*

class DocumentChildController 
{
    @Binding
    def binding;
         
    @Service("TagabukidSubayTransactionService")
    def svc
         
    def entity;
    def din;
    def selectedItem;
    //@PropertyChangeListener
    //def listener = [
    //  
    //]
         
    void init(){
        // entity.child =[];
        din = "";
        listHandler?.load();
    }
    void refresh() {
        if(binding) binding.refresh();
    } 
         
    def listHandler = [
        fetchList : { return entity.child },
        onRemoveItem : {
            if (MsgBox.confirm('Delete item?')){                
                entity.child.remove(it)
                listHandler?.load();
                return true;
            }
            return false;
        },
        onColumnUpdate:{item,colName ->
            entity.child.each{y ->
                if (item.din == y.din){
                    y.message = item.remarks
                }
            }
        }
    ] as EditorListModel

    def addtolist(def doc){
        checkDuplicate(entity.child,doc);
        entity.child << doc;
        din = ""
//        binding.refresh('din');
//        binding.focus('din');
        listHandler?.load();
    }

    def searchdocument(){
        def doc = svc.lookupDIN(entity)
        if (!doc){
            throw new Exception("Invalid DIN");
        }
        
        if (doc.size == 1){
            addtolist(doc[0])
        }
        else if(doc.size > 1){
            return Inv.lookupOpener('din:lookup',[
                    entity: entity,
                    onselect :{
                        addtolist(it)
                    }
                ])
        }else{
            throw new Exception("Invalid DIN");
        }
    }

    public def barcodesearch(){
//        if (!din){
//            throw new Exception("Invalid DIN");
//        }
        entity.searchtext = din
        entity.mode = 'child'
        entity.document = entity.child
        searchdocument()
    }
            
    void checkDuplicate(listtofilter,item){
        def data = listtofilter.find{it.din == item.din }
        if (data)
        throw new Exception("Duplicate item is not allowed.")
    }
    
    def allowlink = { item ->
        if( selectedItem.state != 'attached' ) return true
        return false
    } as Map
} 