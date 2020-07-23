import com.rameses.rcp.common.*;
    import com.rameses.rcp.annotations.*;
    import com.rameses.osiris2.client.*;
    import com.rameses.osiris2.reports.*;

    class TagbalayListReportController extends com.rameses.gov.etracs.rpt.report.AsyncReportController
    {
        @Service('TagabukidTagbalayListReportService') 
        def svc 

        String title = 'Facility List'

        String reportName = 'test/maguuma/reports/list_of_facility.jasper';

        void buildReportData(entity, asyncHandler){
            svc.getTagbalayList(entity, asyncHandler)
        }

        Map getParameters(){
            data.parameters.BARANGAY = entity.barangay?.name
            return data.parameters;
        }
        
        def getState(){
            return ['QUALIFIED', 'NOTQUALIFIED'];
        }
        // def getPhf(){
        //     def c = svc.getFacility();
        //     if(c) return c;
        //     return [];
        // }
        
        // def getCommodity(){
        //     def c = svc.getCommodity();
        //     if(c) return c;
        //     return [];
        // }
        
        //  def getCommoditytype(){
        //     if (! entity.commodity)
        //         return [];
        //     return svc.getCommodityType(entity.commodity?.objid);
        // }

        // def getTs(){
        //     def ts = svc.getTenurialstatus();
        //     if(ts) return ts;
        //     return [];
        // }
        
        def formControl = [
            getFormControls: {
                return [
                    new FormControl( "combo", [captionWidth:100, caption:'LGU', name:'entity.lgu', items:'lgus', expression:'#{item.name}', preferredSize:'0,21', emptyText:'ALL']),
                    new FormControl( "combo", [captionWidth:100, caption:'Barangay', name:'entity.barangay', items:'barangays', expression:'#{item.name}', depends:'entity.lgu', dynamic:true, preferredSize:'0,21', emptyText:'ALL']),
                    // new FormControl( "combo", [captionWidth:100, caption:'Facility', name:'entity.phf', items:'phf', expression:'#{item.name}', preferredSize:'0,21', emptyText:'ALL']),
                    new FormControl( "combo", [captionWidth:100, caption:'Facility', name:'entity.state', items:'states', expression:'#{item.name}', preferredSize:'0,21', emptyText:'ALL']),
                 
                ]    
            },
        ] as FormPanelModel;
    }