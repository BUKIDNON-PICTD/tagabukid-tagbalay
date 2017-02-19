import com.rameses.rcp.common.*
import com.rameses.rcp.annotations.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*

class ParameterSendNonLocalController
{
    @Binding						
    def binding;
       
    
    def entity;
            
    void init(){
        entity.org = [
            objid : "NONLOCAL",
            name  : "",
            code  : ""
        ];
        entity.destinations = null;
        entity.assignee = [name:"",jobtitle:""];
    }
         
    void refresh() {
        if(binding) binding.refresh();
    } 
           
}  