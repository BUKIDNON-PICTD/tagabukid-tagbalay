/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tagabukid.tagbalay.views;


import com.rameses.osiris2.themes.FormPage;
import com.rameses.rcp.ui.annotations.Template;

/**
 *
 * @author rufino
 */
@Template({FormPage.class})
public class EditHouseholdGeneralInfoPage extends javax.swing.JPanel {

    /**
     * Creates new form EditDocumentGeneralInfoPage
     */
    public EditHouseholdGeneralInfoPage() {
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
        xDateField2 = new com.rameses.rcp.control.XDateField();
        entityLookup2 = new com.rameses.entity.components.EntityLookup();
        xCheckBox1 = new com.rameses.rcp.control.XCheckBox();

        xFormPanel1.setCaptionPadding(new java.awt.Insets(0, 5, 0, 5));
        xFormPanel1.setCaptionWidth(180);

        xDateField2.setCaption("Petsa sa Pagpangutana");
        xDateField2.setName("entity.surveydate"); // NOI18N
        xDateField2.setRequired(true);
        xFormPanel1.add(xDateField2);

        entityLookup2.setCaption("Pangalan sa Pangulo sa Pamilya");
        entityLookup2.setExpression("#{entity.name}");
        entityLookup2.setName("entity.pangulo"); // NOI18N
        entityLookup2.setPreferredSize(new java.awt.Dimension(0, 21));
        entityLookup2.setRequired(true);
        xFormPanel1.add(entityLookup2);

        xCheckBox1.setCaption("");
        xCheckBox1.setName("entity.copyAddress"); // NOI18N
        xCheckBox1.setText("Pariha ang nahimutangan sa panimalay sa gi puyoan sa pangulo sa panimalay");
        xFormPanel1.add(xCheckBox1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xFormPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xFormPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(537, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.rameses.entity.components.EntityLookup entityLookup2;
    private com.rameses.rcp.control.XCheckBox xCheckBox1;
    private com.rameses.rcp.control.XDateField xDateField2;
    private com.rameses.rcp.control.XFormPanel xFormPanel1;
    // End of variables declaration//GEN-END:variables
}