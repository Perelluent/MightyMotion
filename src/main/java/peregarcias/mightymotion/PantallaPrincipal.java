/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package peregarcias.mightymotion;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
public class PantallaPrincipal extends javax.swing.JPanel {
    
    DataAccess da = new DataAccess();
    private Inicio inicio;
    private Usuario instructorLogueado;
    private Map<String, Usuario> mapUsuarios = new HashMap<>();
    private Map<String, Workouts> mapWorkouts = new HashMap<>();
    private Map<String, Exercicis> mapExercicis = new HashMap<>();
    private Map<String, String> ejercicioBlobMap = new HashMap<>();
    

    JLabel lblLogo = new JLabel();
    ImageIcon logo = new ImageIcon("src\\main\\resources\\images\\MMFullTrans.png");
    JLabel lblBienvenida = new JLabel();
    JButton btnAlumnos = new JButton("ALUMNOS");
    JLabel lblWorkouts = new JLabel("WORKOUTS");
    JLabel lblExercicis = new JLabel("EXERCICIS");
    JList<String> jListAlumnos = new JList<>();
    JList<Workouts> jListWorkouts = new JList<>();
    JList<Exercicis> jListExercicis = new JList<>();
    JLabel lblimg = new JLabel();
    JLabel lblWarning = new JLabel();
    JButton btnAddWorkout = new JButton("AÑADIR WORKOUT");
    JButton btnDeleteWorkout = new JButton("ELIMINAR WORKOUT");
    JLabel lblWarning2 = new JLabel();
    
    

    public PantallaPrincipal(Usuario instructorLogueado) {
        
        this.instructorLogueado = instructorLogueado;
              
        initComponents();
        
        if (instructorLogueado != null) {

            lblBienvenida.setText("Bienvenido " + instructorLogueado.getNom() + "!");
        } else {
            lblBienvenida.setText("Bienvenido usuario desconocido");
        }
        
        JPanel contenido = new JPanel(new MigLayout("wrap 5", "[grow][grow][grow][grow][grow]", "[]10[][]10[]30[]"));
        
        lblLogo.setIcon(Inicio.redimensionarImagen(logo, 50, 50));
        contenido.add(lblLogo, "cell 0 0,align left");
        contenido.add(lblBienvenida, "cell 0 0, align right");
        lblBienvenida.setFont(new Font("Carlito", Font.PLAIN,20));
        contenido. add(btnAlumnos, "cell 0 1, align center");
        btnAlumnos.setPreferredSize(new Dimension(200,40));
        btnAlumnos.setHorizontalAlignment(SwingConstants.CENTER);
        btnAlumnos.setFont(new Font("Carlito", Font.PLAIN,16));
        contenido.add(lblWorkouts, "cell 1 1, align center");
        lblWorkouts.setFont(new Font("Carlito", Font.PLAIN,20));
        contenido.add(lblExercicis, "cell 2 1, align center");
        lblExercicis.setFont(new Font("Carlito", Font.PLAIN,20));
        contenido.add(jListAlumnos, "cell 0 2, align center");
        jListAlumnos.setPreferredSize(new Dimension(300,500));
        contenido.add(jListWorkouts, "cell 1 2, align center");
        jListWorkouts.setPreferredSize(new Dimension(300,500));
        contenido.add(jListExercicis, "cell 2 2, align center");
        jListExercicis.setPreferredSize(new Dimension(300,500));
        contenido.add(btnAddWorkout, "cell 1 3, align center");
        btnAddWorkout.setPreferredSize(new Dimension(200,40));
        btnAddWorkout.setHorizontalAlignment(SwingConstants.CENTER);
        btnAddWorkout.setFont(new Font("Carlito", Font.PLAIN,16));
        contenido.add(btnDeleteWorkout, "cell 1 3, align center");
        btnDeleteWorkout.setPreferredSize(new Dimension(200,40));
        btnDeleteWorkout.setHorizontalAlignment(SwingConstants.CENTER);
        btnDeleteWorkout.setFont(new Font("Carlito", Font.PLAIN,16));
        contenido.add(lblWarning2, "cell 1 4, align center");
        lblWarning2.setFont(new Font("Carlito", Font.PLAIN,20));
        lblWarning2.setFont(new Font("Carlito", Font.PLAIN,12));
        lblWarning2.setForeground(Color.red);
        contenido.add(lblimg, "cell 3 2, align center");
        lblimg.setText("imagen");
        
        
        JScrollPane scrollPane = new JScrollPane(contenido);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
         
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        contenido.setVisible(true);
        
        
        jListAlumnos.addListSelectionListener(new ListSelectionListener() {
              
          @Override
            public void valueChanged(ListSelectionEvent evt) {
                 jListUsuariosValueChanged(evt);
            }
        });
        jListWorkouts.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                if (!evt.getValueIsAdjusting()) {
                    Workouts selectedWorkout = jListWorkouts.getSelectedValue();
                    if (selectedWorkout != null) {
                        cargarEjerciciosParaWorkout(selectedWorkout);
                    }
                }
            }
            
        });
        jListExercicis.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                if (!evt.getValueIsAdjusting()) {
                    Exercicis selectedExercise = jListExercicis.getSelectedValue();
                    if (selectedExercise != null) {
                        try {
                            mostrarImagenEjercicio(selectedExercise.getDemoFoto());
                        } catch (IOException ex) {
                            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            
        });
        definirMapaEjercicioBlob();
        
        btnAlumnos.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
                cargarUsuariosParaInstructor();
            }
            
        });
        btnAddWorkout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAddWorkoutActionPerformed(e);
            }
            
        });
        btnDeleteWorkout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                btnDeleteWorkoutActionPerformed(e);
            }
            
        });
    }
    
    public void setInstructorLogueado(Usuario instructorLogueado) { 
        this.instructorLogueado = instructorLogueado; 
        if (instructorLogueado != null) { 
            System.out.println("ID de l'instructor en setInstructorLogueado: " + instructorLogueado.getId()); 
            System.out.println("Nom de l'instructor en setInstructorLogueado: " + instructorLogueado.getNom()); 
        } 
        if (lblBienvenida != null) { 
            lblBienvenida.setText("Bienvenido " + instructorLogueado.getNom() + "!"); 
        } 
    }
    
    public Usuario getInstructorLogueado() {
        return instructorLogueado;
    }
    
    private void cargarUsuariosParaInstructor() { 
        if (instructorLogueado == null) { 
            lblWarning.setText("No se encontraron usuarios.");
    return; 
    } 
    System.out.println("ID de l'instructor en cargarUsuariosParaInstructor: " + instructorLogueado.getId()); 
    System.out.println("Nom de l'instructor en cargarUsuariosParaInstructor: " + instructorLogueado.getNom()); 
    List<Usuario> usuarios = da.getUsuariosByInstructor(instructorLogueado.getId()); 
    System.out.println("Nombre d'usuaris trobats per l'instructor amb ID " + instructorLogueado.getId() + ": " + usuarios.size()); 
    DefaultListModel<String> model = new DefaultListModel<>(); 
    mapUsuarios.clear(); 
    for (Usuario alumno : usuarios) { 
        System.out.println("Usuari: " + alumno.getNom());
        model.addElement(alumno.getNom()); 
        mapUsuarios.put(alumno.getNom(), alumno); } 
    jListAlumnos.setModel(model); 
    if (model.getSize() == 0) { 
        lblWarning.setText("No tens alumnes assignats.");
     } else { 
        lblWarning.setText(""); 
        }
    }

    // Método para cargar workouts asociados a un usuario en la tabla
    private void cargarWorkoutsParaUsuario(Usuario usuario) {
        if (usuario == null) {
            lblWarning.setText("Usuario no válido.");
            return;
        }

        DefaultListModel<Workouts> model = new DefaultListModel<>();
        for (Workouts workout : da.getWorkoutsByUser(usuario.getId())) {
            model.addElement(workout);
            mapWorkouts.put(workout.getComments(), workout);
        }
        jListWorkouts.setModel(model);
    }

    // Método para cargar ejercicios asociados a un workout en la tabla
    public void cargarEjerciciosParaWorkout(Workouts workout) {
        if (workout == null) {
            lblWarning2.setText("Workout no válido.");
            return;
        }

        DefaultListModel<Exercicis> model = new DefaultListModel<>();
        for (Exercicis ejercicio : da.getExercicisByWorkout(workout.getId())) {
            model.addElement(ejercicio);
            mapExercicis.put(ejercicio.getNomExercici(), ejercicio);
        }
        jListExercicis.setModel(model);
    }
    private void mostrarListAddWorkout (Usuario user, DataAccess da) {
        if (!jListAlumnos.isSelectionEmpty()) {
        String nombreUsuario = jListAlumnos.getSelectedValue();
        Usuario usuarioSeleccionado = mapUsuarios.get(nombreUsuario);
        AddWorkout addworkout = new AddWorkout(this, user, da);
        
        revalidate();
        repaint();
        
        cargarWorkoutsParaUsuario(usuarioSeleccionado);

    } else {
        lblWarning2.setText("Por favor, selecciona un usuario primero.");
        }
    }
    public void addWorkoutToUser(Workouts workout) {
        DefaultListModel<Workouts> model = (DefaultListModel<Workouts>) jListWorkouts.getModel();
        model.addElement(workout);
        mapWorkouts.put(workout.toString(), workout);
    }
    public void eliminarWorkoutYExercicis() {
        int selectedIndex = jListWorkouts.getSelectedIndex();
        if(selectedIndex != -1) {
            Workouts selectedWorkout = jListWorkouts.getSelectedValue();
            ((DefaultListModel<Workouts>) jListWorkouts.getModel()).removeElementAt(selectedIndex);
            ((DefaultListModel<Exercicis>) jListExercicis.getModel()).clear();
        }
    }
    
    private void mostrarImagenEjercicio (String ejercicioNombre) throws IOException {
        String conexion = "DefaultEndpointsProtocol=https;AccountName=lluentserver;AccountKey=kHji7NlQiOz6P6BVNSFRLSgKTk1DimN0kE72W3UP84qwMM2y2yyGkHNCsn3fOVX7jY88SCi9f1Yh+AStLlewcw==;EndpointSuffix=core.windows.net";
        String containerName = "lluentfotos";
        String blobName = ejercicioBlobMap.get(ejercicioNombre);
        
        if (blobName == null || blobName.isEmpty()) {
            System.out.println("blob name is null or empty");
            lblimg.setIcon(null);
            lblimg.setText("Imagen no disponible");
            return;
        }
        
        BlobServiceClient bsc = new BlobServiceClientBuilder().connectionString(conexion).buildClient();
        BlobContainerClient bcc = bsc.getBlobContainerClient(containerName);
        BlobClient bc = bcc.getBlobClient(blobName);
        
        try (InputStream blobInputStream = bc.openInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            
            byte[] data = new byte[4096];
            int bytesRead;
            while ((bytesRead = blobInputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            
            ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer.toByteArray());
            BufferedImage img = ImageIO.read(inputStream);
            ImageIcon icon = new ImageIcon(img);
            lblimg.setIcon(icon);
            
        } catch (IOException e) {
            lblimg.setIcon(null);
            lblimg.setText("Imagen no disponible");
        }
    }
    private void definirMapaEjercicioBlob() {
        ejercicioBlobMap.put("Exercici 1", "zancadas.jpg"); 
        ejercicioBlobMap.put("Exercici 2", "dominadas.jpg");
        ejercicioBlobMap.put("Exercici 3", "fondos.jpg"); 
        ejercicioBlobMap.put("Exercici 4", "planchas.jpg"); 
        ejercicioBlobMap.put("Exercici 5", "crunch_lateral.jpg"); 
    }
    
    private void btnAddWorkoutActionPerformed(java.awt.event.ActionEvent evt) {                                              

        Inicio inicio = (Inicio) SwingUtilities.getWindowAncestor(this); 
        inicio.mostrarAddWorkout();
    }                                             

    private void btnDeleteWorkoutActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        int selectedIndex = jListWorkouts.getSelectedIndex();
        if(selectedIndex != -1) {
            Workouts selectedWorkout = jListWorkouts.getSelectedValue();
            ((DefaultListModel<Workouts>) jListWorkouts.getModel()).removeElementAt(selectedIndex);
            ((DefaultListModel<Exercicis>) jListExercicis.getModel()).clear();
        } else {
            lblWarning2.setText("Primero debes elegir un workout");
        }
    }                                                

    private void jListUsuariosValueChanged(javax.swing.event.ListSelectionEvent evt) {                                           
        if (!jListAlumnos.isSelectionEmpty()) {
            String alumnoSeleccionado = jListAlumnos.getSelectedValue();
            Usuario alumnoUsuario = mapUsuarios.get(alumnoSeleccionado);
            cargarWorkoutsParaUsuario(alumnoUsuario);
        }
    }                                          

    private void btnAlumnosActionPerformed(java.awt.event.ActionEvent evt) {                                           

        if (instructorLogueado != null && instructorLogueado.isInstructor()) {
            cargarUsuariosParaInstructor();
        } else {

            lblWarning.setText("Aún no tienes alumnos.");
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
