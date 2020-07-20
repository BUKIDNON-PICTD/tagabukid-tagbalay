/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tagabukid.tagbalay.views;

import com.rameses.rcp.ui.annotations.StyleSheet;
import com.rameses.rcp.ui.annotations.Template;
import com.rameses.seti2.views.CrudFormPage;

/**
 *
 * @author user
 */
@StyleSheet
@Template(CrudFormPage.class)
public class TagbalayHouseholdMemberInfoPage extends javax.swing.JPanel {

    /**
     * Creates new form TagbalayHouseholdMemberProfilePage
     */
    public TagbalayHouseholdMemberInfoPage() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        xPanel3 = new com.rameses.rcp.control.XPanel();
        xHorizontalPanel1 = new com.rameses.rcp.control.XHorizontalPanel();
        xFormPanel1 = new com.rameses.rcp.control.XFormPanel();
        xTextField1 = new com.rameses.rcp.control.XTextField();
        xTextField4 = new com.rameses.rcp.control.XTextField();
        xTextField16 = new com.rameses.rcp.control.XTextField();
        xTextField17 = new com.rameses.rcp.control.XTextField();
        xDateField1 = new com.rameses.rcp.control.XDateField();
        jScrollPane2 = new javax.swing.JScrollPane();
        xTextArea1 = new com.rameses.rcp.control.XTextArea();
        civilStatusList1 = new com.rameses.enterprise.components.CivilStatusList();
        genderList1 = new com.rameses.enterprise.components.GenderList();
        xDecimalField1 = new com.rameses.rcp.control.XDecimalField();
        xDecimalField2 = new com.rameses.rcp.control.XDecimalField();
        xTextField7 = new com.rameses.rcp.control.XTextField();
        xTextField8 = new com.rameses.rcp.control.XTextField();
        xTextField9 = new com.rameses.rcp.control.XTextField();
        xTextField10 = new com.rameses.rcp.control.XTextField();
        xTextField11 = new com.rameses.rcp.control.XTextField();
        xTextField12 = new com.rameses.rcp.control.XTextField();
        xTextField13 = new com.rameses.rcp.control.XTextField();
        citizenshipSuggest1 = new com.rameses.enterprise.components.CitizenshipSuggest();
        xHorizontalPanel3 = new com.rameses.rcp.control.XHorizontalPanel();
        xFormPanel3 = new com.rameses.rcp.control.XFormPanel();
        entityAddress1 = new com.rameses.enterprise.components.EntityAddress();
        xHorizontalPanel2 = new com.rameses.rcp.control.XHorizontalPanel();
        xFormPanel2 = new com.rameses.rcp.control.XFormPanel();
        xTextField14 = new com.rameses.rcp.control.XTextField();
        xTextField15 = new com.rameses.rcp.control.XTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        xTextArea2 = new com.rameses.rcp.control.XTextArea();
        xPhoto2 = new com.rameses.rcp.control.XPhoto();
        xFormPanel4 = new com.rameses.rcp.control.XFormPanel();
        xCheckBox1 = new com.rameses.rcp.control.XCheckBox();
        xCheckBox2 = new com.rameses.rcp.control.XCheckBox();
        xCheckBox3 = new com.rameses.rcp.control.XCheckBox();
        xDateField2 = new com.rameses.rcp.control.XDateField();

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder1 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder1.setTitle("Personal");
        xHorizontalPanel1.setBorder(xTitledBorder1);

        xTextField1.setCaption("Maiden Name");
        xTextField1.setDisableWhen("");
        xTextField1.setName("entity.maidenname"); // NOI18N
        xTextField1.setCaptionWidth(135);
        xTextField1.setPreferredSize(new java.awt.Dimension(300, 20));
        xFormPanel1.add(xTextField1);

        xTextField4.setCaption("Extension (Jr., Sr)");
        xTextField4.setDisableWhen("");
        xTextField4.setName("entity.nameextension"); // NOI18N
        xTextField4.setCaptionWidth(135);
        xTextField4.setPreferredSize(new java.awt.Dimension(300, 20));
        xFormPanel1.add(xTextField4);

        xTextField16.setCaption("Pre Name Title");
        xTextField16.setDisableWhen("");
        xTextField16.setName("entity.prenametitle"); // NOI18N
        xTextField16.setCaptionFontStyle("");
        xTextField16.setCaptionWidth(135);
        xTextField16.setPreferredSize(new java.awt.Dimension(300, 20));
        xFormPanel1.add(xTextField16);

        xTextField17.setCaption("Post Name Title");
        xTextField17.setDisableWhen("");
        xTextField17.setName("entity.postnametitle"); // NOI18N
        xTextField17.setCaptionFontStyle("");
        xTextField17.setCaptionWidth(135);
        xTextField17.setInputFormatErrorMsg("");
        xTextField17.setPreferredSize(new java.awt.Dimension(300, 20));
        xFormPanel1.add(xTextField17);

        xDateField1.setCaption("Date of Birth");
        xDateField1.setDisableWhen("");
        xDateField1.setName("entity.member.birthdate"); // NOI18N
        xDateField1.setCaptionWidth(135);
        xDateField1.setRequired(true);
        xFormPanel1.add(xDateField1);

        xTextArea1.setCaption("Birth Place");
        xTextArea1.setName("entity.member.birthplace"); // NOI18N
        xTextArea1.setCaptionWidth(135);
        xTextArea1.setPreferredSize(new java.awt.Dimension(300, 61));
        xTextArea1.setRequired(true);
        xTextArea1.setTextCase(com.rameses.rcp.constant.TextCase.UPPER);
        jScrollPane2.setViewportView(xTextArea1);

        xFormPanel1.add(jScrollPane2);

        civilStatusList1.setCaption("Civil Status");
        civilStatusList1.setCaptionWidth(135);
        civilStatusList1.setName("entity.member.civilstatus"); // NOI18N
        civilStatusList1.setRequired(true);
        xFormPanel1.add(civilStatusList1);

        genderList1.setCaption("Gender");
        genderList1.setCaptionWidth(135);
        genderList1.setName("entity.member.gender"); // NOI18N
        genderList1.setRequired(true);
        xFormPanel1.add(genderList1);

        xDecimalField1.setCaption("Height (m)");
        xDecimalField1.setName("entity.height"); // NOI18N
        xDecimalField1.setCaptionWidth(135);
        xDecimalField1.setRequired(true);
        xFormPanel1.add(xDecimalField1);

        xDecimalField2.setCaption("Weight (kg)");
        xDecimalField2.setName("entity.weight"); // NOI18N
        xDecimalField2.setCaptionWidth(135);
        xDecimalField2.setRequired(true);
        xFormPanel1.add(xDecimalField2);

        xTextField7.setCaption("Blood Type");
        xTextField7.setName("entity.bloodtype"); // NOI18N
        xTextField7.setCaptionWidth(135);
        xTextField7.setPreferredSize(new java.awt.Dimension(150, 20));
        xFormPanel1.add(xTextField7);

        xTextField8.setCaption("GSIS ID No.");
        xTextField8.setName("entity.gsisid"); // NOI18N
        xTextField8.setCaptionWidth(135);
        xTextField8.setPreferredSize(new java.awt.Dimension(200, 20));
        xFormPanel1.add(xTextField8);

        xTextField9.setCaption("PAG-IBIG ID No.");
        xTextField9.setName("entity.pagibigid"); // NOI18N
        xTextField9.setCaptionWidth(135);
        xTextField9.setPreferredSize(new java.awt.Dimension(200, 20));
        xFormPanel1.add(xTextField9);

        xTextField10.setCaption("PHILHEALTH No.");
        xTextField10.setName("entity.philhealth"); // NOI18N
        xTextField10.setCaptionWidth(135);
        xTextField10.setPreferredSize(new java.awt.Dimension(200, 20));
        xFormPanel1.add(xTextField10);

        xTextField11.setCaption("SSS No.");
        xTextField11.setName("entity.sss"); // NOI18N
        xTextField11.setCaptionWidth(135);
        xTextField11.setPreferredSize(new java.awt.Dimension(200, 20));
        xFormPanel1.add(xTextField11);

        xTextField12.setCaption("TIN No.");
        xTextField12.setName("entity.tin"); // NOI18N
        xTextField12.setCaptionWidth(135);
        xTextField12.setPreferredSize(new java.awt.Dimension(200, 20));
        xFormPanel1.add(xTextField12);

        xTextField13.setCaption("Employee No");
        xTextField13.setName("entity.employeeno"); // NOI18N
        xTextField13.setCaptionWidth(135);
        xTextField13.setPreferredSize(new java.awt.Dimension(200, 20));
        xFormPanel1.add(xTextField13);

        citizenshipSuggest1.setCaption("Citizenship");
        citizenshipSuggest1.setCaptionWidth(135);
        citizenshipSuggest1.setName("entity.member.citizenship"); // NOI18N
        citizenshipSuggest1.setPreferredSize(new java.awt.Dimension(0, 20));
        xFormPanel1.add(citizenshipSuggest1);

        xHorizontalPanel1.add(xFormPanel1);

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder2 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder2.setTitle("Address");
        xHorizontalPanel3.setBorder(xTitledBorder2);

        entityAddress1.setCaption("Residential Address");
        entityAddress1.setCaptionWidth(135);
        entityAddress1.setName("entity.member.address"); // NOI18N
        entityAddress1.setPreferredSize(new java.awt.Dimension(0, 86));
        entityAddress1.setRequired(true);
        xFormPanel3.add(entityAddress1);

        xHorizontalPanel3.add(xFormPanel3);

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder3 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder3.setTitle("Contact Info");
        xHorizontalPanel2.setBorder(xTitledBorder3);

        xTextField14.setCaption("Telephone No.");
        xTextField14.setEditable(false);
        xTextField14.setName("entity.member.phoneno"); // NOI18N
        xTextField14.setCaptionWidth(135);
        xTextField14.setNullWhenEmpty(false);
        xTextField14.setPreferredSize(new java.awt.Dimension(175, 20));
        xFormPanel2.add(xTextField14);

        xTextField15.setCaption("Mobile No.");
        xTextField15.setEditable(false);
        xTextField15.setName("entity.member.mobileno"); // NOI18N
        xTextField15.setCaptionWidth(135);
        xTextField15.setPreferredSize(new java.awt.Dimension(175, 20));
        xFormPanel2.add(xTextField15);

        xTextArea2.setCaption("E-mail Address (if any)");
        xTextArea2.setName("entity.member.email"); // NOI18N
        xTextArea2.setCaptionWidth(135);
        xTextArea2.setPreferredSize(new java.awt.Dimension(175, 61));
        jScrollPane3.setViewportView(xTextArea2);

        xFormPanel2.add(jScrollPane3);

        xHorizontalPanel2.add(xFormPanel2);

        xPhoto2.setName("entity.member.photo"); // NOI18N
        xPhoto2.setText("xPhoto2");

        xCheckBox1.setCaption("is Pregnant?");
        xCheckBox1.setName("entity.ispregnant"); // NOI18N
        xCheckBox1.setCaptionWidth(150);
        xFormPanel4.add(xCheckBox1);

        xCheckBox2.setCaption("is Emergency Contact?");
        xCheckBox2.setName("entity.isemergencycontact"); // NOI18N
        xCheckBox2.setCaptionWidth(150);
        xFormPanel4.add(xCheckBox2);

        xCheckBox3.setCaption("is Dead?");
        xCheckBox3.setName("entity.isdead"); // NOI18N
        xCheckBox3.setCaptionWidth(150);
        xFormPanel4.add(xCheckBox3);

        xDateField2.setCaption("Date of Death");
        xDateField2.setDepends(new String[] {"entity.isdead"});
        xDateField2.setName("entity.deathdate"); // NOI18N
        xDateField2.setVisibleWhen("#{entity.isdead == true}");
        xDateField2.setCaptionWidth(150);
        xFormPanel4.add(xDateField2);

        javax.swing.GroupLayout xPanel3Layout = new javax.swing.GroupLayout(xPanel3);
        xPanel3.setLayout(xPanel3Layout);
        xPanel3Layout.setHorizontalGroup(
            xPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(xPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(xPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xFormPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(xPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(xPhoto2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(xHorizontalPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(xHorizontalPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(xHorizontalPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        xPanel3Layout.setVerticalGroup(
            xPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(xPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xPhoto2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xFormPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xHorizontalPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xHorizontalPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xHorizontalPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(481, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(xPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.rameses.enterprise.components.CitizenshipSuggest citizenshipSuggest1;
    private com.rameses.enterprise.components.CivilStatusList civilStatusList1;
    private com.rameses.enterprise.components.EntityAddress entityAddress1;
    private com.rameses.enterprise.components.GenderList genderList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private com.rameses.rcp.control.XCheckBox xCheckBox1;
    private com.rameses.rcp.control.XCheckBox xCheckBox2;
    private com.rameses.rcp.control.XCheckBox xCheckBox3;
    private com.rameses.rcp.control.XDateField xDateField1;
    private com.rameses.rcp.control.XDateField xDateField2;
    private com.rameses.rcp.control.XDecimalField xDecimalField1;
    private com.rameses.rcp.control.XDecimalField xDecimalField2;
    private com.rameses.rcp.control.XFormPanel xFormPanel1;
    private com.rameses.rcp.control.XFormPanel xFormPanel2;
    private com.rameses.rcp.control.XFormPanel xFormPanel3;
    private com.rameses.rcp.control.XFormPanel xFormPanel4;
    private com.rameses.rcp.control.XHorizontalPanel xHorizontalPanel1;
    private com.rameses.rcp.control.XHorizontalPanel xHorizontalPanel2;
    private com.rameses.rcp.control.XHorizontalPanel xHorizontalPanel3;
    private com.rameses.rcp.control.XPanel xPanel3;
    private com.rameses.rcp.control.XPhoto xPhoto2;
    private com.rameses.rcp.control.XTextArea xTextArea1;
    private com.rameses.rcp.control.XTextArea xTextArea2;
    private com.rameses.rcp.control.XTextField xTextField1;
    private com.rameses.rcp.control.XTextField xTextField10;
    private com.rameses.rcp.control.XTextField xTextField11;
    private com.rameses.rcp.control.XTextField xTextField12;
    private com.rameses.rcp.control.XTextField xTextField13;
    private com.rameses.rcp.control.XTextField xTextField14;
    private com.rameses.rcp.control.XTextField xTextField15;
    private com.rameses.rcp.control.XTextField xTextField16;
    private com.rameses.rcp.control.XTextField xTextField17;
    private com.rameses.rcp.control.XTextField xTextField4;
    private com.rameses.rcp.control.XTextField xTextField7;
    private com.rameses.rcp.control.XTextField xTextField8;
    private com.rameses.rcp.control.XTextField xTextField9;
    // End of variables declaration//GEN-END:variables
}
