/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package peregarcias.mightymotion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;
import peregarcias.mightymotion.dataaccess.DataAccess;
import peregarcias.mightymotion.dto.Exercicis;
import peregarcias.mightymotion.dto.Usuario;
import peregarcias.mightymotion.dto.Workouts;

/**
 *
 * @author morda
 */
public class AddWorkout extends javax.swing.JPanel {
    
    private PantallaPrincipal pantallaPrincipal; 
    private DataAccess da = new DataAccess(); 
    private Map<String, Workouts> mapWorkouts = new HashMap<>(); 
    private Map<String, Exercicis> mapEjercicios = new HashMap<>();
    
    
    JLabel lblLogo = new JLabel();
    ImageIcon logo = new ImageIcon("src\\main\\resources\\images\\MMFullTrans.png");
    JLabel lblWorkouts = new JLabel("WORKOUTS DISPONIBLES");
    JLabel lblExercicis = new JLabel("EJERCICIOS");
    JList<String> lstWorkouts = new JList<>();
    JList<String> lstExercicis = new JList<>();
    JButton btnAdd = new JButton("AÑADIR");
    JButton btnVolver = new JButton("VOLVER");
    JLabel lblVolver = new JLabel();
    ImageIcon iconoFlecha = new ImageIcon("src\\main\\resources\\images\\goprevious_103394.png");
 
    
    public AddWorkout(PantallaPrincipal pantallaPrincipal, Usuario usuario, DataAccess da) {
        
        this.pantallaPrincipal = pantallaPrincipal;
        this.da = da;
        
        initComponents();
        
        JPanel contenido = new JPanel(new MigLayout("wrap 4", "[grow][grow][grow][grow]", "[]10[]10[]10[]"));
        
        lblLogo.setIcon(Inicio.redimensionarImagen(logo, 50, 50));
        contenido.add(lblLogo, "cell 0 0,align left");
        contenido.add(lblWorkouts, "cell 0 1, align center");
        lblWorkouts.setFont(new Font("Carlito", Font.PLAIN,20));
        contenido.add(lblExercicis, "cell 1 1, align left");
        lblExercicis.setFont(new Font("Carlito", Font.PLAIN,20));
        lstWorkouts.setPreferredSize(new Dimension(300,500));
        contenido.add(lstWorkouts, "cell 0 2, align center");
        lstExercicis.setPreferredSize(new Dimension(300,500));
        contenido.add(lstExercicis, "cell 1 2, align left");
//        contenido.add(btnVolver, "cell 0 4, align left");
//        btnVolver.setPreferredSize(new Dimension(200,40));
//        btnVolver.setHorizontalAlignment(SwingConstants.CENTER);
//        btnVolver.setFont(new Font("Carlito", Font.PLAIN,16));
        contenido.add(btnAdd, "cell 1 3, align left");
        btnAdd.setPreferredSize(new Dimension(200,40));
        btnAdd.setHorizontalAlignment(SwingConstants.CENTER);
        btnAdd.setFont(new Font("Carlito", Font.PLAIN,16));
        lblVolver.setIcon(Inicio.redimensionarImagen(iconoFlecha, 70, 70));
        lblVolver.setToolTipText("Atràs");
        contenido.add(lblVolver, "cell 0 4,align left, gapleft 100");
        
        JScrollPane scrollPane = new JScrollPane(contenido);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
         
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        contenido.setVisible(true);
        
        
     
        lstWorkouts.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                if (!evt.getValueIsAdjusting()) {
                    cargarEjerciciosParaWorkout();
                }
            }
        
        });
        
        lblVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnVolverActionPerformed(e); 
            }
            
        });
        
        btnAdd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAddActionPerformed(e);
            }
            
        });
        try {
            cargarWorkouts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     private void cargarWorkouts() throws SQLException {

        List<Workouts> workouts = da.getWorkouts();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        mapWorkouts.clear(); 

    for (Workouts workout : workouts) {
        String nombreWorkout = workout.toString();
        mapWorkouts.put(nombreWorkout, workout);  
        listModel.addElement(nombreWorkout);     
    }
        lstWorkouts.setModel(listModel);
    }
    private void cargarEjerciciosParaWorkout() {
        String selectedWorkout = lstWorkouts.getSelectedValue();
        if (selectedWorkout != null) {
            Workouts workout = mapWorkouts.get(selectedWorkout);
            
            List<Exercicis> ejercicios = da.getExercicisByWorkout(workout.getId());  
            
            DefaultListModel<String> model = new DefaultListModel<>();
            for (Exercicis ejercicio : ejercicios) {
                model.addElement(ejercicio.getDescripcio()); 
            }
            lstExercicis.setModel(model);
        }
    }
    
    private void btnVolverActionPerformed(java.awt.event.MouseEvent evt) {
         Inicio inicio = (Inicio) SwingUtilities.getWindowAncestor(this); 
         inicio.mostrarPantallaPrincipal(pantallaPrincipal.getInstructorLogueado());
    }
    
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {                                       
        String selectedWorkout = lstWorkouts.getSelectedValue();
        
        if (selectedWorkout != null) { 
            Workouts workout = mapWorkouts.get(selectedWorkout);
         // Obtenir l'usuari seleccionat des de pantallaPrincipal 
        Usuario selectedUser = pantallaPrincipal.getSelectedUser(); 
        if (selectedUser != null) { 
            List<Workouts> workouts = pantallaPrincipal.getUsuariosWorkout().getOrDefault(selectedUser, new ArrayList<>()); 
            workouts.add(workout); pantallaPrincipal.getUsuariosWorkout().put(selectedUser, workouts); 
            pantallaPrincipal.addWorkoutToUser(workout); 
            pantallaPrincipal.cargarEjerciciosParaWorkout(workout); 
        } 
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
