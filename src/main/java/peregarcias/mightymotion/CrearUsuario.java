/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package peregarcias.mightymotion;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import peregarcias.mightymotion.dataaccess.DataAccess;
import peregarcias.mightymotion.dto.Usuario;

/**
 *
 * @author morda
 */
public class CrearUsuario extends JPanel {
    
    private final Inicio inicio;
    
    JLabel lblLogo = new JLabel();
    ImageIcon logo = new ImageIcon("src\\main\\resources\\images\\MMFullTrans.png");
    JLabel lblMarca = new JLabel();
    JLabel lblNombre = new JLabel("Nombre");
    JTextField txtNombre = new JTextField();
    JLabel lblEmail = new JLabel("E-mail");
    JTextField txtEmail = new JTextField();
    JLabel lblContrasena = new JLabel("Contraseña");
    JPasswordField txtContrasena = new JPasswordField();
    JCheckBox chkInstructor = new JCheckBox("Soy Instructor");
    JButton btnCrearUsuario = new JButton("CREAR USUARIO");
    JButton btnVolver = new JButton("VOLVER");
    JLabel lblMensaje = new JLabel();
  
    /**
     * Creates new form CrearUsuario
     * @param inicio
     */
    public CrearUsuario(Inicio inicio) {
        
        this.inicio = inicio;
        initComponents();
        
        JPanel contenido = new JPanel(new MigLayout("wrap 2", "[grow, right]10[grow]", "[][]30[][][]30[]70[][][]"));
        
        lblLogo.setIcon(Inicio.redimensionarImagen(logo, 500, 500));
        contenido.add(lblLogo, "span, align center, wrap");
        contenido.add(lblMarca, "span, align center, wrap");
        lblMarca.setText("MIGHTY MOTION");
        lblMarca.setForeground(new Color(0x08242f));
        lblMarca.setFont(new Font("Modern M", Font.BOLD, 70));
        lblNombre.setFont(new Font("Carlito", Font.PLAIN,20));
        contenido.add(lblNombre);
        txtNombre.setPreferredSize(new Dimension(450,40));
        contenido.add(txtNombre, "wrap");
        lblEmail.setFont(new Font("Carlito", Font.PLAIN,20));
        contenido.add(lblEmail);
        txtEmail.setPreferredSize(new Dimension(450,40));
        contenido.add(txtEmail, "wrap");
        lblContrasena.setFont(new Font("Carlito", Font.PLAIN,20));
        contenido.add(lblContrasena);
        txtContrasena.setPreferredSize(new Dimension(450,40));
        contenido.add(txtContrasena, "wrap");
        chkInstructor.setFont(new Font("Carlito", Font.PLAIN,20));
        contenido.add(chkInstructor, "cell 1 5, gapx 12");
        contenido.add(btnCrearUsuario, "align center, wrap, span");
        contenido.add(btnVolver, "align center, span");
        btnCrearUsuario.setPreferredSize(new Dimension(200,40));
        btnCrearUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        btnCrearUsuario.setFont(new Font("Carlito", Font.PLAIN,16));
        btnVolver.setPreferredSize(new Dimension(200,40));
        btnVolver.setHorizontalAlignment(SwingConstants.CENTER);
        btnVolver.setFont(new Font("Carlito", Font.PLAIN,16));
        contenido.add(lblMensaje, "align center");
        lblMensaje.setFont(new Font("Carlito", Font.PLAIN,14));
        
         JScrollPane scrollPane = new JScrollPane(contenido);
         scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
         scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
         
         setLayout(new BorderLayout());
         add(scrollPane, BorderLayout.CENTER);
         contenido.setVisible(true);
        
        btnVolver.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                inicio.mostrarInicio();
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
        
        btnCrearUsuario.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCrearUsuarioActionPerformed(e);
            }    
        });
        btnCrearUsuario.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            
        });
        chkInstructor.addMouseListener(new MouseAdapter(){
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
    
    private void btnCrearUsuarioActionPerformed(java.awt.event.ActionEvent evt) {                                                
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNom(txtNombre.getText());
        nuevoUsuario.setEmail(txtEmail.getText());
        String passwordHash = BCrypt.withDefaults().hashToString(12, txtContrasena.getPassword());
        nuevoUsuario.setPasswordHash(passwordHash);
        nuevoUsuario.setInstructor(chkInstructor.isSelected());

        DataAccess da = new DataAccess();
        int nuevoUsuarioId = da.registrarUsuario(nuevoUsuario);
        nuevoUsuarioId = da.getUltimoIdRegistrado();
        nuevoUsuario.setId(nuevoUsuarioId);

        lblMensaje.setText("Usuario registrado correctamente con el ID = " + nuevoUsuarioId);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
