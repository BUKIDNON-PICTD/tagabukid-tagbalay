import com.rameses.rcp.common.*
import com.rameses.rcp.annotations.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*

class ParamterSendLocalController
{
    @Binding
    def binding;
    
    def entity;
            
    void init(){
        entity.org = [:];
        entity.destinations = null;
        entity.assignee = null;
    }
         
    void refresh() {
        if(binding) binding.refresh();
    } 
         
    def getLookupOrg(){
        return Inv.lookupOpener('userorg:lookup',[
                onselect :{
                    entity.org = it.org
                },
            ])
    }
            
    def getLookupAssignee(){
        return Inv.lookupOpener('etracsuserorg:lookup',[
                onselect :{
                    entity.assignee = it;
                },
            ])
    }

           
}  