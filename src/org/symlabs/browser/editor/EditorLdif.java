package org.symlabs.browser.editor;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;
import org.symlabs.actions.DsAction;
import org.symlabs.clipboard.TextTransfer;
import org.symlabs.util.Utils;

/**
 * <p>Titulo: LdifEditor </p>
 * <p>Descripcion: Editor that shows the attribute and values in ldif format. </p>
 * <p>Copyright: Emilio Fernandez  2009</p>
 * @author Emilio J. Fernandez Rey
 * @version 1.0
 * @id $Id: EditorLdif.java,v 1.7 2009-08-24 09:01:06 efernandez Exp $
 */
public class EditorLdif extends EditorPanel {

    /**Attribute that stores the default value for this editor*/
    public static final String DEFAULT_EDITOR_TITLE = "Ldif";
    /**Attribute that contains the ldif text*/
    private JTextArea ldifTextArea;
    /**Attribute used to show the ldif JTextArea in this panel*/
    private JScrollPane ldifScrollPane;
    /**Attribute used to copy the ldif text to the clipboard*/
    private TextTransfer textTransfer;
    /**Attribute used to display the debug message*/
    private static Logger logger = Logger.getLogger(EditorLdif.class);
    

    /** Creates new form LdifEditor */
    public EditorLdif() {
//        initComponents();
        this.initProperties();
        this.editorType = EditorLdif.DEFAULT_EDITOR_TITLE;
    }

    @Override
    public void initProperties() {
        //We get the current node
        this.ldapNode = Utils.getMainWindow().getCurrentBrowserPanel().getSelectedNode();

        //We create the textTransfer
        this.textTransfer = new TextTransfer();
        
        //We remove the containerPanel because we do not need it
        this.remove(this.containerPanel);
        this.remove(this.containerScrollPane);

        // <editor-fold defaultstate="collapsed" desc=" Sets and Adds the LDIF TextArea and ScrollPane">
        //We set and add the Text Area
        this.ldifScrollPane= new javax.swing.JScrollPane();
        
        this.ldifTextArea = new JTextArea("");
        this.ldifTextArea.setEditable(true);
        
        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        
        //If the current node is not null then we will add the ldif 
        if (ldapNode != null) {
            this.attributes = ldapNode.getAttributes();
            this.dnLabel.setText(ldapNode.myDN);
            String ldif = Utils.getLdifFromLDAPEntry(this.ldapNode);
            this.ldifTextArea.append(ldif);
        }
        
        this.ldifTextArea.setEditable(false);
        this.ldifScrollPane.setViewportView(ldifTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 4);
        this.add(this.ldifScrollPane, gridBagConstraints);
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc=" We create and add the popup menu to the ldif text area ">
        final JPopupMenu popup = new JPopupMenu();
        Action action = new DsAction("Copy All LDIF", java.awt.event.KeyEvent.VK_C, Utils.createImageIcon(Utils.ICON_SELECT_TEXT)){

            public void actionPerformed(ActionEvent arg0) {
                logger.trace("Copy to clipboard:" + ldifTextArea.getText());
                textTransfer.setClipboardContents(ldifTextArea.getText());
            }
        };
        popup.add(action);

        MouseListener listener = (new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                logger.trace("Mouse Pressed LDIF Editor 1");
                if (e.isPopupTrigger()) {
                    logger.trace("Mouse Pressed LDIF Editor 2");
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mousePressed(e);
                }
            }
        });

        this.ldifTextArea.addMouseListener(listener);

        // </editor-fold>
        
        //We set the buttons enabled = false
        this.editButton.setEnabled(false);
        this.saveButton.setEnabled(false);
        this.cancelButton.setEnabled(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getEditorContainerDelegate());
        getEditorContainerDelegate().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    /**Method that sets this editor to edit
     * 
     * @param editable boolean. This parameter indicates if the editor is setted as editable
     */
    @Override
    public void setEditableEditor(boolean editable) {

    }

    /**Method that returns the editor type. This field contains the title of the editor displayed
     * 
     * @return String. This is the title of the editor displayed
     */
    @Override
    public String getEditorType() {
        return DEFAULT_EDITOR_TITLE;
    }
}