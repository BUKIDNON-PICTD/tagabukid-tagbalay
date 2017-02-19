import com.rameses.rcp.annotations.*
import com.rameses.rcp.common.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.common.*;
import java.rmi.server.*
import com.rameses.util.*;
        
class DocumentRedflagController {
        
    @Caller
    def caller;
            
    @Binding 
    def binding;
            
    @Script("User")
    def user;
            
    @Service("TagabukidSubayRedflagService")
    def service;
    
    def mode = 'create'
    def handler;

    def blockActions = ["send","archived"];
    def entity = [:];
            
    void init() {
        entity.mode = 'redflag';
        entity.message = '';
    }
            
    void open() {
        mode = 'update'
        entity = service.open( entity );
    }
            
    void resolve() {
        mode = 'resolve'
        entity = service.open( entity );
        entity.mode = 'receive';
    }
            
    void assigntome(){
        entity.filedby.objid = OsirisContext.env.USERID
        entity.filedby.name = OsirisContext.env.FULLNAME        
        entity.filedby.jobtitle = OsirisContext.env.JOBTITLE

    }
            
    def doOk() {
        if( mode == 'create' ) { 
            service.create( entity );
        }
        else if( mode == 'update' ) {
            service.update( entity );
        }
        else {
            if( OsirisContext.env.USERID != entity.filedby.objid )
            throw new Exception("You are not allowed to resolve this issue");
            service.resolve( entity );
        }
        if(handler) handler();
        return "_close";
    }
            
    def doCancel() {
        return "_close";
    }
            
    def getLookupOrg(){
       return Inv.lookupOpener('userorg:lookup',[
                onselect :{
                    entity.org = it.org
                },
            ])
    }
            
}
        