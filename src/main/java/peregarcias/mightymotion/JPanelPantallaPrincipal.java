
package peregarcias.mightymotion;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import peregarcias.mightymotion.dataaccess.DataAccess;
import peregarcias.mightymotion.dto.Exercicis;
import peregarcias.mightymotion.dto.Usuario;
import peregarcias.mightymotion.dto.Workouts;

/**
 *
 * @author morda
 */
public class JPanelPantallaPrincipal extends javax.swing.JPanel {
    
    DataAccess da = new DataAccess();
    private Usuario usuarioLogueado;
    private Map<String, Usuario> mapUsuarios = new HashMap<>();
    private Map<String, Workouts> mapWorkouts = new HashMap<>();
    private Map<String, Exercicis> mapExercicis = new HashMap<>();
    private Map<String, String> ejercicioBlobMap = new HashMap<>();
    private final JPanelAddWorkout addWorkout;
        
    public JPanelPantallaPrincipal(Main jFrameMain, Usuario user) {
        initComponents();
        addWorkout = new JPanelAddWorkout(this,user,da);
        add(btnAddWorkout);
        add(btnDeleteWorkout);
        jListUsuarios.setModel(new DefaultListModel<>());
        jListWorkouts.setModel(new DefaultListModel<Workouts>());
        jListExercicis.setModel(new DefaultListModel<Exercicis>());
        setBackground(new Color(185,208,214));
        setSize(500, 600);
        setBounds(0, 0, 490, 590);

        if (user != null) {
            this.usuarioLogueado = user;
            lblBienvenida.setText("Bienvenido " + user.getNom() + "!");
        } else {
            lblBienvenida.setText("Bienvenido");
        }
        jListUsuarios.addListSelectionListener(new ListSelectionListener() {
              
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
                            Logger.getLogger(JPanelPantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            
        });
        definirMapaEjercicioBlob();
    }
    
    
    public void setUsuarioLogueado (Usuario usuario){
        this.usuarioLogueado = usuario;
    }
    
    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }
    
    private void cargarUsuariosParaInstructor(Usuario instructor) {
    List<Usuario> usuarios = da.getUsuariosByInstructor(instructor.getId());
    DefaultListModel<String> listModel = new DefaultListModel<>();
    mapUsuarios.clear(); // Limpia el mapa antes de añadir nuevos datos

    for (Usuario usuario : usuarios) {
        String nombreUsuario = usuario.getNom(); // O cualquier representación en String
        mapUsuarios.put(nombreUsuario, usuario); // Mapea el nombre con el objeto Usuario
        listModel.addElement(nombreUsuario);     // Añade el nombre al modelo
    }
    jListUsuarios.setModel(listModel);
    }

    // Método para cargar workouts asociados a un usuario en la tabla
    private void cargarWorkoutsParaUsuario(Usuario usuario) {
    List<Workouts> workouts = da.getWorkoutsByUser(usuario.getId());
    DefaultListModel<Workouts> listModel = new DefaultListModel<>();
    mapWorkouts.clear(); // Limpia el mapa antes de añadir nuevos datos

    for (Workouts workout : workouts) {
        mapWorkouts.put(workout.toString(), workout);  
        listModel.addElement(workout);     
    }
    jListWorkouts.setModel(listModel);
    }

    // Método para cargar ejercicios asociados a un workout en la tabla
    void cargarEjerciciosParaWorkout(Workouts workout) {
    List<Exercicis> ejercicios = da.getExercicisByWorkout(workout.getId()); // Llama al método en DataAccess
    DefaultListModel<Exercicis> model = new DefaultListModel<>();
    mapExercicis.clear();

    for (Exercicis ejercicio : ejercicios) {
        model.addElement(ejercicio);
        mapExercicis.put(ejercicio.getNomExercici(), ejercicio);
    }
    jListExercicis.setModel(model);
}
    private void mostrarJpanelAddWorkout (Usuario user, DataAccess da) {
        if (!jListUsuarios.isSelectionEmpty()) {
        String nombreUsuario = jListUsuarios.getSelectedValue();
        Usuario usuarioSeleccionado = mapUsuarios.get(nombreUsuario);
        JPanelAddWorkout addworkout = new JPanelAddWorkout(this, user, da);
        
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
        blobName = URLEncoder.encode(blobName, StandardCharsets.UTF_8.toString());
        
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
        ejercicioBlobMap.put("Exercici 1", "Legs.jpg"); 
        ejercicioBlobMap.put("Exercici 2", "Foto 3.png");
        ejercicioBlobMap.put("Exercici 3", "Foto 1.png"); 
        ejercicioBlobMap.put("Exercici 4", "Img situps.png"); 
        ejercicioBlobMap.put("Exercici 5", "Srpint.png"); 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblBienvenida = new javax.swing.JLabel();
        btnAlumnos = new javax.swing.JButton();
        lblExercicis = new javax.swing.JLabel();
        lblWarning = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListUsuarios = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListWorkouts = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListExercicis = new javax.swing.JList<>();
        lblWorkouts = new javax.swing.JLabel();
        btnDeleteWorkout = new javax.swing.JButton();
        btnAddWorkout = new javax.swing.JButton();
        lblWarning2 = new javax.swing.JLabel();
        lblimg = new javax.swing.JLabel();

        setBackground(new java.awt.Color(185, 208, 214));
        setLayout(null);

        lblBienvenida.setFont(new java.awt.Font("Modern M", 0, 24)); // NOI18N
        lblBienvenida.setForeground(new java.awt.Color(0, 44, 58));
        add(lblBienvenida);
        lblBienvenida.setBounds(10, 10, 320, 50);

        btnAlumnos.setFont(new java.awt.Font("Modern M", 0, 14)); // NOI18N
        btnAlumnos.setForeground(new java.awt.Color(0, 44, 58));
        btnAlumnos.setText("ALUMNOS");
        btnAlumnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlumnosActionPerformed(evt);
            }
        });
        add(btnAlumnos);
        btnAlumnos.setBounds(30, 70, 190, 50);

        lblExercicis.setFont(new java.awt.Font("Modern M", 0, 18)); // NOI18N
        lblExercicis.setForeground(new java.awt.Color(0, 44, 58));
        lblExercicis.setText("EXERCICIS");
        lblExercicis.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(lblExercicis);
        lblExercicis.setBounds(650, 130, 90, 19);
        add(lblWarning);
        lblWarning.setBounds(30, 130, 190, 30);

        jListUsuarios.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListUsuariosValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListUsuarios);

        add(jScrollPane1);
        jScrollPane1.setBounds(30, 170, 190, 410);

        jScrollPane2.setViewportView(jListWorkouts);

        add(jScrollPane2);
        jScrollPane2.setBounds(260, 170, 310, 410);

        jScrollPane3.setViewportView(jListExercicis);

        add(jScrollPane3);
        jScrollPane3.setBounds(600, 170, 180, 410);

        lblWorkouts.setFont(new java.awt.Font("Modern M", 0, 18)); // NOI18N
        lblWorkouts.setForeground(new java.awt.Color(0, 44, 58));
        lblWorkouts.setText("WORKOUTS");
        lblWorkouts.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(lblWorkouts);
        lblWorkouts.setBounds(360, 130, 90, 19);

        btnDeleteWorkout.setFont(new java.awt.Font("Modern M", 0, 12)); // NOI18N
        btnDeleteWorkout.setForeground(new java.awt.Color(0, 44, 58));
        btnDeleteWorkout.setText("ELIMINAR WORKOUT");
        btnDeleteWorkout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteWorkoutActionPerformed(evt);
            }
        });
        add(btnDeleteWorkout);
        btnDeleteWorkout.setBounds(420, 590, 150, 30);

        btnAddWorkout.setFont(new java.awt.Font("Modern M", 0, 12)); // NOI18N
        btnAddWorkout.setForeground(new java.awt.Color(0, 44, 58));
        btnAddWorkout.setText("AÑADIR WORKOUT");
        btnAddWorkout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddWorkoutActionPerformed(evt);
            }
        });
        add(btnAddWorkout);
        btnAddWorkout.setBounds(260, 590, 160, 30);
        add(lblWarning2);
        lblWarning2.setBounds(270, 630, 300, 40);

        lblimg.setText("imagen");
        add(lblimg);
        lblimg.setBounds(810, 180, 310, 300);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAlumnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlumnosActionPerformed
        
        // Comprueba que el usuario logueado es un instructor
    if (usuarioLogueado != null && usuarioLogueado.isInstructor()) {
        cargarUsuariosParaInstructor(usuarioLogueado);
    } else {
        // Mensaje de error si no es instructor
        lblWarning.setText("Aún no tienes alumnos.");
    }
    }//GEN-LAST:event_btnAlumnosActionPerformed

    private void jListUsuariosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListUsuariosValueChanged
        if (!jListUsuarios.isSelectionEmpty()) {
        String nombreSeleccionado = jListUsuarios.getSelectedValue(); // Obtener el nombre
        Usuario alumnoSeleccionado = mapUsuarios.get(nombreSeleccionado); // Recuperar el objeto Usuario
        cargarWorkoutsParaUsuario(alumnoSeleccionado);
        }
    }//GEN-LAST:event_jListUsuariosValueChanged

    private void btnAddWorkoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddWorkoutActionPerformed
        
        Main mainFrame = (Main) SwingUtilities.getWindowAncestor(this);
        mainFrame.setContentPane(addWorkout);
        mainFrame.revalidate();
        mainFrame.repaint();

    }//GEN-LAST:event_btnAddWorkoutActionPerformed

    private void btnDeleteWorkoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteWorkoutActionPerformed
        eliminarWorkoutYExercicis();
    }//GEN-LAST:event_btnDeleteWorkoutActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddWorkout;
    private javax.swing.JButton btnAlumnos;
    private javax.swing.JButton btnDeleteWorkout;
    private javax.swing.JList<Exercicis> jListExercicis;
    private javax.swing.JList<String> jListUsuarios;
    private javax.swing.JList<Workouts> jListWorkouts;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblExercicis;
    private javax.swing.JLabel lblWarning;
    private javax.swing.JLabel lblWarning2;
    private javax.swing.JLabel lblWorkouts;
    private javax.swing.JLabel lblimg;
    // End of variables declaration//GEN-END:variables


}
