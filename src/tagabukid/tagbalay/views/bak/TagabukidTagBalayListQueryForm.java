/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tagabukid.tagbalay.views.bak;

/**
 *
 * @author rufino
 */
public class TagabukidTagBalayListQueryForm extends javax.swing.JPanel {

    /**
     * Creates new form TagabukidSubayListQueryForm
     */
    public TagabukidTagBalayListQueryForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        xFormPanel1 = new com.rameses.rcp.control.XFormPanel();
        xDateField1 = new com.rameses.rcp.control.XDateField();
        xDateField2 = new com.rameses.rcp.control.XDateField();
        xLookupField1 = new com.rameses.rcp.control.XLookupField();
        xActionTextField2 = new com.rameses.rcp.control.XActionTextField();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        xFormPanel1.setAddCaptionColon(false);
        xFormPanel1.setCaptionBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        xFormPanel1.setCaptionFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        xFormPanel1.setCaptionForeground(new java.awt.Color(80, 80, 80));
        xFormPanel1.setCaptionPadding(new java.awt.Insets(0, 5, 0, 5));
        xFormPanel1.setCaptionVAlignment(com.rameses.rcp.constant.UIConstants.CENTER);
        xFormPanel1.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);

        xDateField1.setActionCommand("search");
        xDateField1.setCaption("Date Created");
        xDateField1.setCaptionWidth(90);
        xDateField1.setName("params.dtfrom"); // NOI18N
        xDateField1.setPreferredSize(new java.awt.Dimension(100, 22));
        xFormPanel1.add(xDateField1);

        xDateField2.setActionCommand("search");
        xDateField2.setCaption("to");
        xDateField2.setCaptionWidth(25);
        xDateField2.setName("params.dtto"); // NOI18N
        xDateField2.setPreferredSize(new java.awt.Dimension(100, 22));
        xFormPanel1.add(xDateField2);

        xLookupField1.setCaption("Document Type");
        xLookupField1.setCaptionWidth(110);
        xLookupField1.setExpression("#{params.documenttype.name}");
        xLookupField1.setHandler("lookupDocumentType");
        xLookupField1.setHint("Select Documen Type");
        xLookupField1.setName("params.documenttype"); // NOI18N
        xLookupField1.setPreferredSize(new java.awt.Dimension(220, 22));
        xFormPanel1.add(xLookupField1);

        xActionTextField2.setActionName("search");
        xActionTextField2.setCaption("Search");
        xActionTextField2.setCaptionWidth(55);
        xActionTextField2.setFocusAccelerator('s');
        xActionTextField2.setFocusKeyStroke("F3");
        xActionTextField2.setName("params.searchtext"); // NOI18N
        xActionTextField2.setPreferredSize(new java.awt.Dimension(220, 22));
        xFormPanel1.add(xActionTextField2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(xFormPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 933, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(xFormPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.rameses.rcp.control.XActionTextField xActionTextField2;
    private com.rameses.rcp.control.XDateField xDateField1;
    private com.rameses.rcp.control.XDateField xDateField2;
    private com.rameses.rcp.control.XFormPanel xFormPanel1;
    private com.rameses.rcp.control.XLookupField xLookupField1;
    // End of variables declaration//GEN-END:variables
}
