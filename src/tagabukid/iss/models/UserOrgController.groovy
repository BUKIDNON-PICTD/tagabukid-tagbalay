import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
        
public class TagabukidSubayUserOrgModel extends CRUDController
{
    String serviceName = 'TagabukidSubayUserOrgService'
    String entityName  = 'userorg'
    String prefixId    = 'OC'
            
    public void afterOpen(Object entity){
        println entity
    }
    def getLookupUser(){
        return Inv.lookupOpener('etracsuser:lookup',[
                onselect :{
                    entity = it;
                },
            ])
    }
        
    def getLookupOrg(){
        return Inv.lookupOpener('hrisorg:lookup',[
                onselect :{
                    def org = [
                        objid   :   it.OrgUnitId,
                        name    :   it.Entity.Name,
                        code    :   it.Entity.AcronymAbbreviation,
                    ]
                    entity.org = org;

                },
            ])
    }
            
}