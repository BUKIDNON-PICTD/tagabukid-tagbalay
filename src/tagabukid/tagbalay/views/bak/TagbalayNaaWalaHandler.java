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
public class TagbalayNaaWalaHandler extends javax.swing.JPanel {

    /**
     * Creates new form TagbalayBooleanHandler
     */
    public TagbalayNaaWalaHandler() {
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

        xRadio1 = new com.rameses.rcp.control.XRadio();
        xRadio2 = new com.rameses.rcp.control.XRadio();

        setLayout(new com.rameses.rcp.control.layout.XLayout());

        xRadio1.setName("item.value"); // NOI18N
        xRadio1.setOptionValue(Boolean.valueOf(true));
        xRadio1.setText("NAA");
        add(xRadio1);

        xRadio2.setName("item.value"); // NOI18N
        xRadio2.setOptionValue(Boolean.valueOf(false));
        xRadio2.setText("WALA");
        add(xRadio2);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.rameses.rcp.control.XRadio xRadio1;
    private com.rameses.rcp.control.XRadio xRadio2;
    // End of variables declaration//GEN-END:variables
}
