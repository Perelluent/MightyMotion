/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package peregarcias.mightymotion;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import net.miginfocom.swing.MigLayout;
import static peregarcias.mightymotion.Inicio.redimensionarImagen;

/**
 *
 * @author morda
 */
public class SideBarMenu extends javax.swing.JPanel {
    
    private Inicio inicio;
    
    private final JPanel sidebarMenu = new JPanel();
    private boolean isSideBarVisible;
    private final int sidebarWidth = 200;
    private final Timer timer;
    private final int animStep = 10;
    private final JLabel lblBienvenida = new JLabel();
    private final JLabel lblLogo = new JLabel();
    private final ImageIcon logo = new ImageIcon("src\\main\\resources\\images\\MMFullTrans.png");
    private final JLabel lblHome = new JLabel("Home");
    private final JLabel lblIniciarSesion = new JLabel("Inciar Sesión");
    private final JLabel lblAddWorkout = new JLabel("Añadir Workout");
    private final JLabel lblMarca = new JLabel("MIGHTY MOTION");
    private final JLabel lblHomeIcon = new JLabel();
    private final ImageIcon homeIcon = new ImageIcon("src\\main\\resources\\images\\house.png");
    JLabel lblCerrarSesion = new JLabel();
    ImageIcon cerrarSesionIcon = new ImageIcon("src\\main\\resources\\images\\interruptor.png");

    /**
     * Creates new form SideBarMenu
     */
    public SideBarMenu(Inicio inicio) {
        this.inicio = inicio;
        this.isSideBarVisible = false;
        
        initComponents();
        sidebarMenu.setLayout(new MigLayout("wrap 2", "[grow]", "[]10[]10[]10[]10[]"));
        sidebarMenu.setSize(new Dimension(0,600));
        sidebarMenu.setVisible(false);
        sidebarMenu.add(lblBienvenida, "cell 0 0, align center, grow");
        
        sidebarMenu.add(lblLogo, "cell 0 0, align center, grow");
        sidebarMenu.add(lblMarca, "cell 0 1, align center, wrap 30");
        lblMarca.setFont(new Font("Modern M", Font.BOLD, 20));
        lblLogo.setIcon(Inicio.redimensionarImagen(logo, 50, 50));
        sidebarMenu.add(lblHome, "grow");
        lblHome.setIcon(Inicio.redimensionarImagen(homeIcon, 15, 15));
        sidebarMenu.add(lblHomeIcon, "align left");
        lblHome.setFont(new Font("Carlito", Font.PLAIN, 14));
        sidebarMenu.add(lblIniciarSesion, "grow, span");
        lblIniciarSesion.setFont(new Font("Carlito", Font.PLAIN, 14));
        sidebarMenu.add(lblAddWorkout, "grow, span");
        lblAddWorkout.setFont(new Font("Carlito", Font.PLAIN, 14));
        sidebarMenu.add(lblCerrarSesion, "wrap, push, aligny bottom");
        lblCerrarSesion.setIcon(redimensionarImagen(cerrarSesionIcon, 50, 50));
        lblCerrarSesion.setToolTipText("Cerrar Sesión");
        
        this.setLayout(new BorderLayout());
        this.add(sidebarMenu, BorderLayout.WEST);
        
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animateSidebar();
            }
        });
        
        lblCerrarSesion.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                inicio.mostrarInicio();
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
        lblAddWorkout.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                inicio.mostrarAddWorkout();
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
    public void toggleSidebar() {
        isSideBarVisible = !isSideBarVisible;
        sidebarMenu.setVisible(true);
        timer.start();
    }
    public boolean isSideBarVisible() {
        return isSideBarVisible;
    }
    private void animateSidebar() {
        int currentWidth = sidebarMenu.getPreferredSize().width;
        if (isSideBarVisible) {
            if (currentWidth < sidebarWidth) {
                sidebarMenu.setPreferredSize(new Dimension(currentWidth + animStep, 600));
            } else {
                sidebarMenu.setPreferredSize(new Dimension(sidebarWidth, 600));
                timer.stop();
            }
        } else {
            if (currentWidth > 0) {
                sidebarMenu.setPreferredSize(new Dimension(currentWidth - animStep, 600));
            } else {
                sidebarMenu.setPreferredSize(new Dimension(0, 600));
                timer.stop();
                sidebarMenu.setVisible(false);
            }
        }
        sidebarMenu.revalidate();
        sidebarMenu.repaint();
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
