/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package peregarcias.mightymotion;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.miginfocom.swing.MigLayout;
import peregarcias.mightymotion.dataaccess.DataAccess;
import peregarcias.mightymotion.dto.Usuario;

/**
 * <p><b>Clase Inicio</b></p>
 * <p>Representa la ventana principal de la aplicación Mighty Motion.</p>
 * <ul>
 *  <li><b>Control de navegación:</b> Utiliza un sistema de tarjetas (CardLayout) para alternar entre diferentes paneles.</li>
 *  <li><b>Gestión de componentes:</b> Incluye menús, botones y un sistema de barra de herramientas.</li>
 *  <li><b>Paneles asociados:</b> Contiene paneles como Inicio, Iniciar Sesión, Crear Usuario, Pantalla Principal y Añadir Workout.</li>
 * </ul>
 * 
 * @author Perelluent
 * @since 23/10/2024
 */
public class Inicio extends javax.swing.JFrame {
    
    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    private final JPanel inicio;
    private final IniciarSesion iniciarSesion;
    private final CrearUsuario crearUsuario;
    private final PantallaPrincipal pantallaPrincipal;
    private final Usuario usuario;
    private final AddWorkout addWorkout;
    private final DataAccess da;
    private boolean isClicked = false;
  
    JMenuBar mnuBar = new JMenuBar();
    JMenu mnuArchivo = new JMenu();
    JMenu mnuHelp = new JMenu();
    JMenuItem mniAbout = new JMenuItem();
    JMenuItem mniIniciarSesion = new JMenuItem("Iniciar Sesión");
    JMenuItem mniAltaUsuarios = new JMenuItem("Alta Usuarios");
    JMenuItem mniCerrar = new JMenuItem("Cerrar");
    JSeparator separador = new JSeparator();
    
    /**
     * <p><b>Constructor de Inicio</b></p>
     * <p>Inicializa la ventana principal con todos los componentes gráficos.</p>
     */
    public Inicio() {

        initComponents();
        
        this.usuario = new Usuario();
        this.iniciarSesion = new IniciarSesion(this);
        this.crearUsuario = new CrearUsuario(this);
        this.inicio = crearPanelInicio();
        this.pantallaPrincipal = new PantallaPrincipal(this, usuario);
        this.da = new DataAccess();
        this.addWorkout = new AddWorkout(this, pantallaPrincipal, usuario, da);
        
        JScrollPane iniciarSesionScrollPane = new JScrollPane(iniciarSesion);
        iniciarSesionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        iniciarSesionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(inicio, "Inicio");
        cardPanel.add(iniciarSesionScrollPane, "IniciarSesion");
        cardPanel.add(crearUsuario, "CrearUsuario");
        cardPanel.add(pantallaPrincipal, "PantallaPrincipal");
        cardPanel.add(addWorkout, "AddWorkout");
        
        
        
        getContentPane().add(cardPanel);
        pack();
                
        setLocationRelativeTo(null);
        cardLayout.show(cardPanel, "Inicio");

        mnuArchivo.setText("Archivo");
        mniIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarIniciarSesion();
            }
        });
            
        mnuArchivo.add(mniIniciarSesion);
        mniAltaUsuarios.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "CrearUsuario");;
            }
        
        });
        mnuArchivo.add(mniAltaUsuarios);
        mnuArchivo.add(separador);
        mniCerrar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        
        });

        mnuArchivo.add(mniCerrar);
        mnuBar.add(mnuArchivo);
        mnuHelp.setText("Help");
        mniAbout.setText("About");
        mniAbout.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "www.mightymotion.com");
            }
        });
        mnuHelp.add(mniAbout);
        mnuBar.add(mnuHelp);
        setJMenuBar(mnuBar);
    }
    
    /**
     * <p><b>Crea el panel inicial de la aplicación</b></p>
     * <p>Genera el panel de bienvenida con opciones para iniciar sesión, registrar usuarios y cambiar el modo de apariencia.</p>
     * <ul>
     *  <li><b>Incluye un logotipo:</b> Representa visualmente la marca "Mighty Motion".</li>
     *  <li><b>Opciones de usuario:</b> Permite iniciar sesión o registrar nuevos usuarios.</li>
     *  <li><b>Modo oscuro/claro:</b> Opción interactiva para alternar entre modos de apariencia.</li>
     * </ul>
     * 
     * @return Un objeto JPanel configurado como el panel inicial.
     */
    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new MigLayout("wrap 1","[grow]", "[][][][]10[]10[]push[]10[][]"));
        
        setTitle("Mighty Motion");
        
        JLabel lblOscuro = new JLabel();
        panel.add(lblOscuro, "align right");
        ImageIcon lblOscuroIcon = new ImageIcon("src\\main\\resources\\images\\mode_dark_icon_214378.png");
        lblOscuro.setIcon(redimensionarImagen(lblOscuroIcon, 30, 30));
        JLabel lblLogo = new JLabel();
        ImageIcon logo = new ImageIcon("src\\main\\resources\\images\\MMFullTrans.png");
        lblLogo.setIcon(redimensionarImagen(logo, 500, 500));
        panel.add(lblLogo, "align center");
        
        JLabel lblMarca = new JLabel();
        lblMarca.setText("MIGHTY MOTION");
        lblMarca.setForeground(new Color(0x08242f));
        lblMarca.setFont(new Font("Modern M", Font.BOLD, 70));
        panel.add(lblMarca, "align center");
        
        JButton btnIniciarSesion = new JButton("INICIAR SESIÓN");
        btnIniciarSesion.setHorizontalAlignment(SwingConstants.CENTER);
        btnIniciarSesion.setPreferredSize(new Dimension(200, 40));
        btnIniciarSesion.setFont(new Font("Carlito", Font.PLAIN,16));
        panel.add(btnIniciarSesion, "align center");
        
        JButton btnAltaUsuarios = new JButton("ALTA USUARIOS");
        btnAltaUsuarios.setHorizontalAlignment(SwingConstants.CENTER);
        btnAltaUsuarios.setPreferredSize(new Dimension(200, 40));        
        btnAltaUsuarios.setFont(new Font("Carlito", Font.PLAIN,16));
        panel.add(btnAltaUsuarios, "align center");
        
        
        
        JLabel lblWeb = new JLabel("www.mightymotion.com");
        lblWeb.setFont(new Font("Carlito", Font.PLAIN,16));
        panel.add(lblWeb, "align center");
        
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
                modoOscuro();
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
        
        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarIniciarSesion();                
            }           
        });
        btnIniciarSesion.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            
        });
        
        btnAltaUsuarios.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "CrearUsuario");
            }
            
        });
        btnAltaUsuarios.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            
        });
        
        return panel;
    }
    
    /**
     * <p><b>Redimensiona una imagen</b></p>
     * <p>Escala una imagen al tamaño especificado.</p>
     * 
     * @param icono La imagen que se desea redimensionar.
     * @param ancho Ancho deseado para la imagen redimensionada.
     * @param alto Alto deseado para la imagen redimensionada.
     * @return Un nuevo objeto ImageIcon con la imagen escalada.
     */
    public static ImageIcon redimensionarImagen(ImageIcon icono, int ancho, int alto) {
        Image imagenOriginal = icono.getImage();
        Image imagenEscalada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
    
    /**
     * <p><b>Alterna entre paneles usando CardLayout</b></p>
     * <p>Muestra el panel correspondiente según la opción seleccionada.</p>
     */
    public void mostrarIniciarSesion(){
        cardLayout.show(cardPanel, "IniciarSesion");
    }
    /**
     * <p><b>Alterna entre paneles usando CardLayout</b></p>
     * <p>Muestra el panel correspondiente según la opción seleccionada.</p>
     */
    public void mostrarInicio(){
       cardLayout.show(cardPanel, "Inicio");
    }
    /**
     * <p><b>Alterna entre paneles usando CardLayout</b></p>
     * <p>Muestra el panel correspondiente según la opción seleccionada.</p>
     * 
     * @param usuarioLogueado usuario que se ha logueado.
     */
    public void mostrarPantallaPrincipal(Usuario usuarioLogueado) { 
        if (usuarioLogueado != null) { 
            System.out.println("Passant l'usuari a PantallaPrincipal: " + usuarioLogueado.getNom() + ", ID: " + usuarioLogueado.getId()); 
        } 
        pantallaPrincipal.setInstructorLogueado(usuarioLogueado); 
        cardLayout.show(cardPanel, "PantallaPrincipal"); 
    }
    /**
     * <p><b>Alterna entre paneles usando CardLayout</b></p>
     * <p>Muestra el panel correspondiente según la opción seleccionada.</p>
     */
    public void mostrarAddWorkout() {
        cardLayout.show(cardPanel, "AddWorkout");
    }
    
    /**
     * <p><b>Aplica el modo oscuro a la interfaz</b></p>
     * <p>Actualiza todos los componentes gráficos de la interfaz para reflejar el modo oscuro o claro.</p>
     * <ul>
     *  <li><b>Recorre todas las ventanas abiertas:</b> Actualiza y repinta la interfaz.</li>
     * </ul>
     */
    public void modoOscuro() {
    for (Window window : Window.getWindows()) {
        SwingUtilities.updateComponentTreeUI(window);
        window.validate();
        window.repaint();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                try {
                    UIManager.setLookAndFeel(new FlatLightLaf());
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                }
                new Inicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
