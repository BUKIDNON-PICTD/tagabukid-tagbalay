import com.rameses.rcp.common.*
import com.rameses.rcp.annotations.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*

class ParamterSendTypeController
{
    @Binding
    def binding;
    
    def entity;
    
    void init(){
        getSendtypeHandler()
    }
    
    void refresh() {
        if(binding) binding.refresh();
    } 
    
    def getSendtypeHandler(){
        return InvokerUtil.lookupOpener(entity.sendtype+':form',[
                entity: entity
            ])
    }
}  
