/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Vista;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.Arrays;  
/**
 *
 * @author mpqa1
 */
public class Backup extends javax.swing.JDialog {

    Backup(Sistema aThis, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Backup() {
        // Llama a un JDialog “huérfano” en modo modal
        super((java.awt.Frame) null, true);
        initComponents();
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel2 = new javax.swing.JLabel();
        btnexaminar = new javax.swing.JButton();
        txtrespaldar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnrespaldar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 51, 102));

        jDesktopPane1.setBackground(new java.awt.Color(0, 51, 102));

        jLabel2.setFont(new java.awt.Font("Eras Demi ITC", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("RUTA DE GUARDADO:");

        btnexaminar.setBackground(new java.awt.Color(0, 0, 0));
        btnexaminar.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 1, 24)); // NOI18N
        btnexaminar.setForeground(new java.awt.Color(255, 255, 255));
        btnexaminar.setText("examinar");
        btnexaminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexaminarActionPerformed(evt);
            }
        });

        txtrespaldar.setBackground(new java.awt.Color(0, 0, 0));
        txtrespaldar.setForeground(new java.awt.Color(255, 255, 255));
        txtrespaldar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrespaldarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Eras Bold ITC", 1, 29)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("REALIZAR BACKUP DE LA BASE DE DATOS");

        btnrespaldar.setBackground(new java.awt.Color(0, 0, 0));
        btnrespaldar.setFont(new java.awt.Font("Eras Demi ITC", 1, 24)); // NOI18N
        btnrespaldar.setForeground(new java.awt.Color(255, 255, 255));
        btnrespaldar.setText("RESPALDAR");
        btnrespaldar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrespaldarActionPerformed(evt);
            }
        });

        jDesktopPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(btnexaminar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtrespaldar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(btnrespaldar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addComponent(txtrespaldar, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(btnexaminar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(btnrespaldar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(381, Short.MAX_VALUE))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addGap(35, 35, 35)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnexaminar)
                    .addComponent(txtrespaldar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(btnrespaldar)
                .addContainerGap(356, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnrespaldarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrespaldarActionPerformed
String ruta = txtrespaldar.getText().trim();
        if (ruta.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Elija primero la ruta de destino usando 'examinar'",
                "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<String> cmd = Arrays.asList(
            "mysqldump",
            "--user=root",
            "--host=localhost",
            "proyecto"
        );
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.redirectOutput(new File(ruta));
        pb.redirectErrorStream(true);

        try {
            Process p = pb.start();
            int exitCode = p.waitFor();
            if (exitCode == 0) {
                JOptionPane.showMessageDialog(this,
                    "Respaldo creado en:\n" + ruta,
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String error = new String(p.getInputStream().readAllBytes());
                JOptionPane.showMessageDialog(this,
                    "Error al generar el respaldo:\n" + error,
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Excepción: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnrespaldarActionPerformed

    private void btnexaminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexaminarActionPerformed
       JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccione carpeta y nombre para el .sql");
        chooser.setSelectedFile(new File("respaldo_proyecto.sql"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            if (!f.getName().toLowerCase().endsWith(".sql")) {
                f = new File(f.getParentFile(), f.getName() + ".sql");
            }
            txtrespaldar.setText(f.getAbsolutePath());
        }
    }//GEN-LAST:event_btnexaminarActionPerformed

    private void txtrespaldarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrespaldarActionPerformed
    }//GEN-LAST:event_txtrespaldarActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnexaminar;
    private javax.swing.JButton btnrespaldar;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtrespaldar;
    // End of variables declaration//GEN-END:variables
}
