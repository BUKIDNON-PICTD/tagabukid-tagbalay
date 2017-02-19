import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import java.text.DecimalFormat;
import com.rameses.osiris2.reports.*;
        
public class TagabukidSubayDINInventoryModel extends CRUDController
{
    String serviceName = 'TagabukidSubayDINInventoryService'
    String entityName  = 'din'
    String prefixId    = 'DIN'

    def startseries;
    def endseries;
    boolean allowApprove = false;
    boolean allowEdit = false;
    boolean allowDelete = false;

    Map createEntity(){
        return [qty:0]
    }
    public void afterOpen(Object entity){
        entity.putAll(service.open(entity));
    }

    @PropertyChangeListener
    def listener = [
        'entity.qty':{
            generateDIN();
        },
    ]   

    void generateDIN(){
        def dinsequence = service.getNextSequence(entity.qty);
        entity.startseries = dinsequence.startseries
        entity.endseries = dinsequence.endseries
        binding.refresh('entity.startseries,entity.endseries');
    }

    def print() {
        def op = Inv.lookupOpener( "subay:din", [entity: entity] );
        op.target = 'self';
        return op;
    }
}