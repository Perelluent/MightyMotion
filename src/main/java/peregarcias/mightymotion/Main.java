/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package peregarcias.mightymotion;


import java.awt.Color;
import javax.swing.JOptionPane;
import peregarcias.mightymotion.dto.Usuario;

/**
 *
 * @author morda
 */
public class Main extends javax.swing.JFrame {

    private JPanelIniciarSesion iniciarSesion;
    private JPanelCrearUsuario crearUsuario;
    private JPanelPantallaPrincipal pantallaPrincipal;
    private Usuario usuario;
    
    
    public Main() {
        initComponents();
        pack();
        setBounds(0, 0, 500, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(185,208,214));
        
        iniciarSesion = new JPanelIniciarSesion(this);
        this.add(iniciarSesion);
        iniciarSesion.setBounds(0, 0, 490, 590);
        iniciarSesion.setVisible(false);
        getContentPane().add(iniciarSesion);
        
        crearUsuario = new JPanelCrearUsuario(this);
        crearUsuario.setBounds(0, 0, 490, 590);
        crearUsuario.setVisible(false);
        getContentPane().add(crearUsuario);
        
        pantallaPrincipal = new JPanelPantallaPrincipal(this, usuario);
        pantallaPrincipal.setBounds(0, 0, 490, 590);
        pantallaPrincipal.setVisible(false);
        getContentPane().add(pantallaPrincipal);

    }
    public void mostrarJpanelIniciarSesion(){
        iniciarSesion.setVisible(true);
        Main.setVisible(false);
    }
    
    public void mostrarJpanelCrearUsuario(){
        crearUsuario.setVisible(true);
        Main.setVisible(false);
    }
    public void mostrarJpanelPantallaPrincipal(Usuario usuario) {
        System.out.println("Mostrando pantalla principal con usuario: " + (usuario != null ? usuario.getNom() : "null"));
        JPanelPantallaPrincipal pantallaPrincipal = new JPanelPantallaPrincipal(this, usuario);
        setContentPane(pantallaPrincipal);
        pantallaPrincipal.setUsuarioLogueado(usuario);
        revalidate();
        repaint();
        iniciarSesion.setVisible(false);
        crearUsuario.setVisible(false);
        pantallaPrincipal.setVisible(true);
    }
    
    public void mostrarMain(){
       crearUsuario.setVisible(false);
       iniciarSesion.setVisible(false);
       pantallaPrincipal.setVisible(false);
       Main.setVisible(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Main = new javax.swing.JPanel();
        jLabelLogo = new javax.swing.JLabel();
        jLabelTitle = new javax.swing.JLabel();
        jButtonLogin = new javax.swing.JButton();
        jButtonAltaUsuarios = new javax.swing.JButton();
        lblWeb = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mniIniciarSesion = new javax.swing.JMenuItem();
        mniAltaUsuarios = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mniCerrar = new javax.swing.JMenuItem();
        mnuHelp = new javax.swing.JMenu();
        mniAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(185, 208, 214));
        setPreferredSize(new java.awt.Dimension(600, 600));
        setSize(new java.awt.Dimension(50, 75));
        getContentPane().setLayout(null);

        Main.setBackground(new java.awt.Color(185, 208, 214));
        Main.setMinimumSize(new java.awt.Dimension(500, 600));
        Main.setPreferredSize(new java.awt.Dimension(500, 600));

        jLabelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logoMM.jpg"))); // NOI18N
        jLabelLogo.setText("jLabel1");

        jLabelTitle.setFont(new java.awt.Font("Modern M", 1, 24)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(0, 44, 58));
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("Mighty Motion");

        jButtonLogin.setFont(new java.awt.Font("Modern M", 0, 18)); // NOI18N
        jButtonLogin.setForeground(new java.awt.Color(0, 44, 58));
        jButtonLogin.setText("INICIAR SESIÓN");
        jButtonLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        jButtonAltaUsuarios.setFont(new java.awt.Font("Modern M", 0, 18)); // NOI18N
        jButtonAltaUsuarios.setForeground(new java.awt.Color(0, 44, 58));
        jButtonAltaUsuarios.setText("ALTA USUARIOS");
        jButtonAltaUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAltaUsuariosActionPerformed(evt);
            }
        });

        lblWeb.setFont(new java.awt.Font("Modern M", 0, 18)); // NOI18N
        lblWeb.setForeground(new java.awt.Color(0, 44, 58));
        lblWeb.setText("www.mightymotion.com");
        lblWeb.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout MainLayout = new javax.swing.GroupLayout(Main);
        Main.setLayout(MainLayout);
        MainLayout.setHorizontalGroup(
            MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainLayout.createSequentialGroup()
                .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainLayout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonAltaUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(MainLayout.createSequentialGroup()
                                    .addGap(21, 21, 21)
                                    .addComponent(jLabelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(MainLayout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(lblWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(151, Short.MAX_VALUE))
        );
        MainLayout.setVerticalGroup(
            MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTitle)
                .addGap(69, 69, 69)
                .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButtonAltaUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95)
                .addComponent(lblWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        getContentPane().add(Main);
        Main.setBounds(0, 0, 500, 600);

        mnuFile.setText("File");

        mniIniciarSesion.setText("Iniciar Sesión");
        mniIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniIniciarSesionActionPerformed(evt);
            }
        });
        mnuFile.add(mniIniciarSesion);

        mniAltaUsuarios.setText("Alta Usuarios");
        mniAltaUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAltaUsuariosActionPerformed(evt);
            }
        });
        mnuFile.add(mniAltaUsuarios);
        mnuFile.add(jSeparator1);

        mniCerrar.setText("Cerrar");
        mniCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCerrarActionPerformed(evt);
            }
        });
        mnuFile.add(mniCerrar);

        jMenuBar1.add(mnuFile);

        mnuHelp.setText("Help");

        mniAbout.setText("About");
        mniAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAboutActionPerformed(evt);
            }
        });
        mnuHelp.add(mniAbout);

        jMenuBar1.add(mnuHelp);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAltaUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAltaUsuariosActionPerformed
        Main.setVisible(false);
        crearUsuario.setVisible(true);
        getContentPane().revalidate();
        getContentPane().repaint();
    }//GEN-LAST:event_jButtonAltaUsuariosActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        Main.setVisible(false);
        iniciarSesion.setVisible(true);
        getContentPane().revalidate();
        getContentPane().repaint();
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void mniIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniIniciarSesionActionPerformed
        Main.setVisible(false);
        iniciarSesion.setVisible(true);
        getContentPane().revalidate();
        getContentPane().repaint();
    }//GEN-LAST:event_mniIniciarSesionActionPerformed

    private void mniAltaUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAltaUsuariosActionPerformed
        Main.setVisible(false);
        crearUsuario.setVisible(true);
        getContentPane().revalidate();
        getContentPane().repaint();
    }//GEN-LAST:event_mniAltaUsuariosActionPerformed

    private void mniAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAboutActionPerformed
        JOptionPane.showMessageDialog(this, "Pere Garcias\nDesarrollo de interfaces. DAM.");
    }//GEN-LAST:event_mniAboutActionPerformed

    private void mniCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCerrarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mniCerrarActionPerformed

    /**
     * @param args the command line arguments
     */

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Main;
    private javax.swing.JButton jButtonAltaUsuarios;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblWeb;
    private javax.swing.JMenuItem mniAbout;
    private javax.swing.JMenuItem mniAltaUsuarios;
    private javax.swing.JMenuItem mniCerrar;
    private javax.swing.JMenuItem mniIniciarSesion;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenu mnuHelp;
    // End of variables declaration//GEN-END:variables
}
