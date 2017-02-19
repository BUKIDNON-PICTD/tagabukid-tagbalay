import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import tagabukid.utils.*;
public class DocumentTaskListSectionController  {
            
    @Service("TagabukidSubayTaskListService")
    def service;
    
    @Service("TagabukidSubayTransactionService")
    def svctransaction;
            
    @Service("TagabukidSubayDocumentService")
    def svc
            
    @Service('DateService')
    def dtSvc;
        
    def entity;
    def selectedItem;     
        
    @Controller
    def controller;
            
    String title = "Task History";
            
    def tasks;
    def duration;
    
    def refresh(){
        //listModel.reload()
        def newlog = svc.lookupNode([refid:entity.objid,taskid:entity.taskid])
                
        if (newlog.size == 1){
            entity = newlog[0]
        }else if (newlog.size > 1){
            return Inv.lookupOpener('node:lookup',[
                    entity: entity,
                    onselect :{
                        entity = it
                        listModel.reload()
                    }
                ])
        }
        listModel.reload()
                
    }
    def listModel = [
        fetchList: {
            String processname = (String) controller.workunit.workunit.properties.processname;
            tasks = service.getList( [processname:processname, refid: entity.objid, taskid: entity.taskid] );
            computeDurations();
            return tasks;
        }
    ] as BasicListModel;
            
            
    void computeDurations(){
        def currdate = dtSvc.getServerDate();
        tasks.each{
            it.mins = timeDiffInMinutes( it.enddate, it.startdate, currdate)
            it.duration = durationToStr(it.mins)
            if(it.state == "offline"){
                it.offlinemins = timeDiffInMinutes( it.enddate, it.startdate, currdate)
            }else{
                it.offlinemins = 0
            }
        }
        duration = '';
        if (tasks){
            duration = durationToStr(tasks.mins.sum() - tasks.offlinemins.sum());
            //duration = durationToStr(tasks.offlinemins.sum());
            //println tasks
        }
    }
            
    def durationToStr(totalduration){
        def hours = (int) (totalduration / 60);
        def mins = totalduration - (hours * 60);

        def days = 0;
        if (hours >= 24 ){
            days = (int) (hours / 24)
            hours = hours - (days * 24)
        }

        def s = '';

        if (days > 0)
        s += (days + ' day' + (days <= 1 ? '' : 's'));

        if (hours > 0){
            if ( s.length() > 0 )
            s += ' ';
            s += (hours + ' hr' + (hours <= 1 ? '' : 's'));
        }

        if ( mins > 0){
            if (s.length()>0)
            s += ' and ';
            s += (mins + ' min' + (mins <= 1 ? '.' : 's.'));
        }
        return s;
    }
            
    def timeDiffInMinutes( enddate, startdate, currdate ) {
        if (enddate == null) 
        enddate = currdate 
        return ( ( enddate.time - startdate.time) / 1000.0 / 60.0 ) as int;
    }
            
    def offlineReconciliation() {
//        if(!selectedItem) throw new Exception('Please select an item');
        def h = {
            listModel.reload();
        }
//        def rf = [documentid: entity.objid,task:selectedItem];
        return Inv.lookupOpener( "subaydocument_offlinereconciliation:create", [entity: entity, handler: h] );
    }
            
    def viewFlowChart() {
        entity.tasks = tasks
        return InvokerUtil.lookupOpener( "flowchart:view", [entity: entity] );
    }
            
    def cancelsend() {
        if( MsgBox.confirm( "You are about to cancel this transaction. Proceed?")) {
            try{
                def doc = [objid: entity.objid,taskid:entity.taskid];
                svctransaction.cancelSend(doc)
                MsgBox.alert("Transaction Cancelled. DIN: " + entity.din + " change state.")
            }catch(e){
                MsgBox.alert("ERROR IN PROCESSING PLEASE CONTACT PICTD.")
            }
        }
                
    }
            
            
}