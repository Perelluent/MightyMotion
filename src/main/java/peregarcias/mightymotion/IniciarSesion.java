/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package peregarcias.mightymotion;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.miginfocom.swing.MigLayout;
import static peregarcias.mightymotion.Inicio.redimensionarImagen;
import peregarcias.mightymotion.dataaccess.DataAccess;
import peregarcias.mightymotion.dto.Usuario;

/**
 *
 * @author morda
 */
public class IniciarSesion extends javax.swing.JPanel {
    
    private Inicio inicio;
    DataAccess da = new DataAccess();
    private Usuario usuario;
    private boolean isClicked = false;
    
    JLabel lblLogo = new JLabel();
    ImageIcon logo = new ImageIcon("src\\main\\resources\\images\\MMFullTrans.png");
    JLabel lblOscuro = new JLabel();
    ImageIcon lblOscuroIcon = new ImageIcon("src\\main\\resources\\images\\mode_dark_icon_214378.png");
    JLabel lblMarca = new JLabel();
    JLabel lblUsuario = new JLabel("Usuario");
    JTextField txtUsuario = new JTextField("a@b.c");
    JLabel lblContrasena = new JLabel("Contraseña");
    JPasswordField txtContrasena = new JPasswordField("string");
    JButton btnEntrar = new JButton("ENTRAR");
    JButton btnVolver = new JButton("VOLVER");
    JLabel lblError = new JLabel();
    
    /**
     * Creates new form IniciarSesion
     * @param inicio
     */
    public IniciarSesion(Inicio inicio) {
        
        this.inicio = inicio;
        
        initComponents();
              
        setLayout(new MigLayout("wrap 1","[grow]", "[][]70[]10[]10[]10[]10[]"));
        add(lblOscuro, "align right");
        lblOscuro.setIcon(redimensionarImagen(lblOscuroIcon, 30, 30));
        lblLogo.setIcon(inicio.redimensionarImagen(logo, 500, 500));
        add(lblLogo, "align center");
        add(lblMarca, "align center");
        lblMarca.setText("MIGHTY MOTION");
        lblMarca.setForeground(new Color(0x08242f));
        lblMarca.setFont(new Font("Modern M", Font.BOLD, 70));
        lblUsuario.setFont(new Font("Carlito", Font.PLAIN,20));
        lblContrasena.setFont(new Font("Carlito", Font.PLAIN,20));
        add(lblUsuario,"align center");
        add(txtUsuario, "align center");
        txtUsuario.setPreferredSize(new Dimension(380,40));
        add(lblContrasena, "align center");
        add(txtContrasena, "align center");
        txtContrasena.setPreferredSize(new Dimension(380,40));
        add(btnEntrar, "align center");
        add(btnVolver, "align center");
        btnEntrar.setPreferredSize(new Dimension(180,30));
        btnVolver.setPreferredSize(new Dimension(180,30));
        btnEntrar.setFont(new Font("Carlito", Font.PLAIN,16));
        btnVolver.setFont(new Font("Carlito", Font.PLAIN,16));
        add(lblError, "align center");
        lblError.setFont(new Font("Carlito", Font.PLAIN,12));
        lblError.setForeground(Color.red);
        
        
        setVisible(true);
          
        btnVolver.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAlInicio();
            }
        });
        btnVolver.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            
        });
        
        btnEntrar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }            
        });
        btnEntrar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            
        });
        
        lblOscuro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {       
                try {
                    if (!isClicked) {
                        UIManager.setLookAndFeel(new FlatDarkLaf());
                        isClicked = true;
                    } else {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                        isClicked = false;
                    }
                inicio.modoOscuro();
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            
    });
        
    }
   
    private void volverAlInicio() {
        inicio.mostrarInicio();
    }
    
    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) { 
        Usuario usuario = da.getUsuario(txtUsuario.getText()); 
        if (usuario == null) { 
            lblError.setText("El usuario o la contraseña no son válidos"); 
            lblError.setVisible(true); lblError.repaint(); 
            return; 
        } 
        if (!usuario.isInstructor()) { 
            lblError.setText("Solo los instructores pueden iniciar sesión."); 
            lblError.setVisible(true); 
            lblError.repaint(); 
            return; 
        } 
        char[] passwordToVerify = txtContrasena.getPassword(); 
        String userPasswordHashInDatabase = usuario.getPasswordHash(); 
        var result = BCrypt.verifyer().verify(passwordToVerify, userPasswordHashInDatabase); 
        if (result.verified) { 
            System.out.println("Usuario logueado: " + usuario.getNom()); 
            System.out.println("Id del usuario: " + usuario.getId()); 
            inicio.mostrarPantallaPrincipal(usuario); 
        } else { 
            lblError.setText("El usuario o la contraseña no son válidos"); 
            lblError.setVisible(true); 
            lblError.repaint(); 
        } 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
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
}
