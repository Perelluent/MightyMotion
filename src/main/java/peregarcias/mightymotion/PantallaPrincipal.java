/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package peregarcias.mightymotion;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;
import peregarcias.componenteBlobTracker.BlobTracker;
import static peregarcias.mightymotion.Inicio.redimensionarImagen;
import peregarcias.mightymotion.dataaccess.DataAccess;
import peregarcias.mightymotion.dto.Exercicis;
import peregarcias.mightymotion.dto.Usuario;
import peregarcias.mightymotion.dto.Workouts;

/**
 * <p><b>Clase PantallaPrincipal</b></p>
 * <p>Panel principal de la aplicación una vez iniciada la sesión.</p>
 * <ul>
 *  <li><b>Muestra un menú lateral (SideBarMenu) {@link peregarcias.mightymotion.SideBarMenu} con las diferentes opciones disponibles.</b></li>
 *  <li><b>Incluye listas de alumnos, workouts y ejercicios para facilitar la gestión de contenidos.</b></li>
 *  <li><b>Ofrece botones para añadir y eliminar workouts.</b></li>
 *  <li><b>Permite al usuario alternar entre modos claro y oscuro.</b></li>
 * </ul>
 * <p><i>Nota:</i> Requiere un usuario instructor previamente logueado para operar.</p>
 * 
 * @author Perelluent
 * @since 23/10/2024
 * 
 */
public class PantallaPrincipal extends javax.swing.JPanel {
    
    DataAccess da = new DataAccess();
    private Inicio inicio;
    private Usuario instructorLogueado;
    private SideBarMenu sideBarMenu;
    private final Map<String, Usuario> mapUsuarios = new HashMap<>();
    private final Map<String, Workouts> mapWorkouts = new HashMap<>();
    private final Map<String, Exercicis> mapExercicis = new HashMap<>();
    private final Map<String, String> ejercicioBlobMap = new HashMap<>();
    private final Map<Usuario, List<Workouts>> usuariosWorkout = new HashMap<>();
    private final Map<String, ImageIcon> cachedImages = new HashMap<>();
    private final String connectStr = "DefaultEndpointsProtocol=https;AccountName=lluentserver;AccountKey=kHji7NlQiOz6P6BVNSFRLSgKTk1DimN0kE72W3UP84qwMM2y2yyGkHNCsn3fOVX7jY88SCi9f1Yh+AStLlewcw==;EndpointSuffix=core.windows.net";
    private final String containerName = "lluentfotos";
    private final String tempDir = System.getProperty("java.io.tmpdir");
    private boolean isClicked = false;
    

    JLabel lblmenu = new JLabel();
    ImageIcon menu = new ImageIcon("src\\main\\resources\\images\\menu.png");
    JLabel lblOscuro = new JLabel();
    ImageIcon lblOscuroIcon = new ImageIcon("src\\main\\resources\\images\\mode_dark_icon_214378.png");
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
    BlobTracker blobTracker = new BlobTracker();
    
    /**
     * <p><b>Constructor de la clase PantallaPrincipal</b></p>
     * <p>Inicializa el panel principal con los componentes gráficos y configura el menú lateral.</p>
     * 
     * @param inicio Instancia de Inicio para manejar la navegación entre pantallas.
     * @param instructorLogueado Usuario instructor actualmente logueado.
     */
    public PantallaPrincipal(Inicio inicio, Usuario instructorLogueado) {
        
        sideBarMenu = new SideBarMenu(inicio);
        this.instructorLogueado = instructorLogueado;
        this.inicio = inicio;
              
        initComponents();
        
        if (instructorLogueado != null) {

            lblBienvenida.setText("Bienvenido " + instructorLogueado.getNom() + "!");
        } else {
            lblBienvenida.setText("Bienvenido usuario desconocido");
        }
        
        JPanel contenido = new JPanel(new MigLayout("wrap 5", "[grow][grow][grow][grow][grow]", "[]10[][]10[]30[]"));
        
        contenido.add(lblOscuro, "cell 5 0, align right");
        lblOscuro.setIcon(redimensionarImagen(lblOscuroIcon, 30, 30));
        lblOscuro.setToolTipText("Modo Claro/Oscuro");
        lblmenu.setIcon(Inicio.redimensionarImagen(menu, 50, 50));
        contenido.add(lblmenu, "cell 0 0,align left");
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
        contenido.add(lblimg, "cell 3 2 1 3, align center, grow");
        lblimg.setPreferredSize(new Dimension(300, 300));
        lblimg.setMinimumSize(new Dimension(300, 300));
        lblimg.setMaximumSize(new Dimension(300, 300));
        contenido.add(blobTracker, "cell 3 0, align right");
        blobTracker.setRunning(true);
        blobTracker.setPollingInterval(3000);
        blobTracker.setVisible(false);
        
        
        JScrollPane scrollPane = new JScrollPane(contenido);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
         
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        contenido.setVisible(true);
        this.add(sideBarMenu, BorderLayout.WEST);
        
     /**
     * <p><b>Gestión del menú lateral (SideBarMenu)</b></p>
     * <p>Permite alternar la visibilidad del menú lateral al hacer clic en el icono del menú.</p>
     * <ul>
     *  <li><b>Revalida y repinta el contenido de la pantalla al cambiar el estado del menú.</b></li>
     * </ul>
     */
        lblmenu.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                sideBarMenu.toggleSidebar();
                contenido.revalidate();
                contenido.repaint();
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
        
       // Listeners para la selección de cada elemento de las listas
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
                        String ejercicioNombre = selectedExercise.getNomExercici();
                        System.out.println("Exercici sel.leccionat: " + ejercicioNombre);
                        jListExercicisValueChanged(evt);
                    }
                }
            }
            
        });
        definirMapaEjercicioBlob();
        
         // Listeners para cada tipo de botón
        btnAlumnos.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
                cargarUsuariosParaInstructor();
            }
        });
        btnAlumnos.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            
        });
        btnAddWorkout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAddWorkoutActionPerformed(e);
            }
        });
        btnAddWorkout.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            
        });
        btnDeleteWorkout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                btnDeleteWorkoutActionPerformed(e);
            }
        });
        btnDeleteWorkout.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            
        });
        
     /**
     * <p><b>Modo oscuro/claro</b></p>
     * <p>Permite alternar entre los modos claro y oscuro de la interfaz.</p>
     * <ul>
     *  <li><b>Aplica el modo oscuro o claro al hacer clic en el icono.</b></li>
     * </ul>
     */
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
    /**
     * <p><b>Set del instructor logueado</b></p>
     * <p>Asigna un instructor logueado a la pantalla principal y actualiza el mensaje de bienvenida.</p>
     * <ul>
     *  <li><b>Si el instructor es válido, muestra su nombre e ID en consola.</b></li>
     *  <li><b>Actualiza el texto del componente de bienvenida (lblBienvenida) con el nombre del instructor.</b></li>
     * </ul>
     * 
     * @param instructorLogueado Usuario instructor actualmente logueado.
     */
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
    
    public Usuario getSelectedUser() {
        String alumnoSeleccionado = jListAlumnos.getSelectedValue();
        return mapUsuarios.get(alumnoSeleccionado);
    }
    
    public Map<Usuario, List<Workouts>> getUsuariosWorkout() { 
        return usuariosWorkout; 
    }
    /**
     * <p><b>Carga los usuarios asignados al instructor y los muestra en un JList</b></p>
     * <p>Recupera y muestra los usuarios asociados al instructor logueado.</p>
     * <ul>
     *  <li><b>Actualiza la lista de alumnos (jListAlumnos).</b></li>
     *  <li><b>Muestra un mensaje si no hay alumnos asignados.</b></li>
     * </ul>
     */
    private void cargarUsuariosParaInstructor() { 
        if (instructorLogueado == null) { 
            lblWarning.setText("No se encontraron usuarios.");
    return; 
    } 

    List<Usuario> usuarios = da.getUsuariosByInstructor(instructorLogueado.getId()); 
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

    /**
     * <p><b>Carga workouts asociados a un usuario</b></p>
     * <p>Rellena la lista de workouts (jListWorkouts) del usuario seleccionado.</p>
     * 
     * @param usuario Usuario cuyo listado de workouts se cargará.
     */
    private void cargarWorkoutsParaUsuario(Usuario usuario) {
        if (usuario == null) {
            lblWarning.setText("Usuario no válido.");
            return;
        }

        DefaultListModel<Workouts> model = new DefaultListModel<>();
        List<Workouts> workouts = usuariosWorkout.getOrDefault(usuario, new ArrayList<>());
        for (Workouts workout : da.getWorkoutsByUser(usuario.getId())) {
            model.addElement(workout);
            mapWorkouts.put(workout.getComments(), workout);
        }
        jListWorkouts.setModel(model);
    }

    /**
     * <p><b>Carga ejercicios asociados a un workout</b></p>
     * <p>Rellena la lista de ejercicios (jListExercicis) asociados al workout seleccionado.</p>
     * 
     * @param workout El workout cuyos ejercicios se cargarán.
     */
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
    /**
     * <p><b>Muestra el panel para añadir workouts</b></p>
     * <p>Abre una ventana para añadir un nuevo workout al usuario seleccionado.</p>
     * <ul>
     *  <li><b>Rellena la lista de workouts después de añadir uno nuevo.</b></li>
     * </ul>
     * <p><i>Nota:</i> Muestra un mensaje de advertencia si no se ha seleccionado un usuario.</p>
     * 
     * @param user Usuario seleccionado en la lista.
     * @param da Instancia de DataAccess para interactuar con la base de datos.
     */
    private void mostrarListAddWorkout (Usuario user, DataAccess da) {
        if (!jListAlumnos.isSelectionEmpty()) {
        String nombreUsuario = jListAlumnos.getSelectedValue();
        Usuario usuarioSeleccionado = mapUsuarios.get(nombreUsuario);
        AddWorkout addworkout = new AddWorkout(inicio, this, user, da);
        
        revalidate();
        repaint();
        
        cargarWorkoutsParaUsuario(usuarioSeleccionado);

    } else {
        lblWarning2.setText("Por favor, selecciona un usuario primero.");
        }
    }
    /**
     * <p><b>Añade un workout al usuario</b></p>
     * <p>Inserta un nuevo workout en la lista de workouts del usuario seleccionado.</p>
     * 
     * @param workout Workout que se desea agregar.
     */
    public void addWorkoutToUser(Workouts workout) {
        DefaultListModel<Workouts> model = (DefaultListModel<Workouts>) jListWorkouts.getModel();
        model.addElement(workout);
        mapWorkouts.put(workout.toString(), workout);
    }
    /**
     * <p><b>Elimina un workout y sus ejercicios asociados</b></p>
     * <p>Borra el workout seleccionado de la lista y limpia los ejercicios asociados.</p>
     */
    public void eliminarWorkoutYExercicis() {
        int selectedIndex = jListWorkouts.getSelectedIndex();
        if(selectedIndex != -1) {
            Workouts selectedWorkout = jListWorkouts.getSelectedValue();
            ((DefaultListModel<Workouts>) jListWorkouts.getModel()).removeElementAt(selectedIndex);
            ((DefaultListModel<Exercicis>) jListExercicis.getModel()).clear();
        }
    }
    
    /**
     * <p><b>Define el mapa de blobs, albergados en un blob de Azure, para mostrar una imagen de los ejercicios</b></p>
     * <p>Asocia los nombres de los ejercicios con archivos de imagen.</p>
     */
    private void definirMapaEjercicioBlob() {
        ejercicioBlobMap.put("Exercici 1", "zancadas.png"); 
        ejercicioBlobMap.put("Exercici 2", "dominadas.png");
        ejercicioBlobMap.put("Exercici 3", "fondos.png"); 
        ejercicioBlobMap.put("Exercici 4", "plancha.png"); 
        ejercicioBlobMap.put("Exercici 5", "crunch_lateral.png"); 
    }
    
    /**
     * <p><b>Acción para añadir un workout</b></p>
     * <p>Abre la ventana de añadir workout.</p>
     * 
     * @param evt Evento de acción generado por el botón.
     */
    private void btnAddWorkoutActionPerformed(java.awt.event.ActionEvent evt) {                                              

        Inicio inicio = (Inicio) SwingUtilities.getWindowAncestor(this); 
        inicio.mostrarAddWorkout();
    }                                             

    /**
     * <p><b>Acción para eliminar un workout</b></p>
     * <p>Elimina el workout seleccionado de la lista.</p>
     * <ul>
     *  <li><b>Si no se selecciona un workout, muestra un mensaje de advertencia.</b></li>
     * </ul>
     * 
     * @param evt Evento de acción generado por el botón.
     */
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
     * <p><b>Cambio de selección en la lista de ejercicios</b></p>
     * <p>Carga una imagen del ejercicio seleccionado utilizando Azure Blob Storage.</p>
     * <ul>
     *  <li><b>Muestra mensajes si la imagen no está disponible.</b></li>
     *  <li><b>Descarga y escala la imagen para mostrarla en el panel.</b></li>
     * </ul>
     * 
     * @param evt Evento generado al cambiar la selección en la lista.
     */
    private void jListExercicisValueChanged(javax.swing.event.ListSelectionEvent evt) { 
        if (evt.getValueIsAdjusting()) { 
            return;
        }
        Exercicis selectedExercise = jListExercicis.getSelectedValue(); 
        String ejercicioNombre = selectedExercise.getNomExercici(); 
        String blobName = ejercicioBlobMap.get(ejercicioNombre); 
        if (blobName == null || blobName.isEmpty()) { 
            lblimg.setText("nombre del ejercicio inválido");
         return; 
        } if (cachedImages.containsKey(blobName)) { 
            lblimg.setBackground(Color.decode("#3c3f41"));
            lblimg.setIcon(cachedImages.get(blobName));
            lblimg.revalidate();
            lblimg.repaint();
        } else { 
            BlobClient blobClient = new BlobClientBuilder() 
                    .connectionString(connectStr) 
                    .blobName(blobName) 
                    .containerName(containerName) 
                    .buildClient();
        
        boolean blobExists = blobClient.exists(); 
        if (!blobExists) { 
            lblimg.setText("El blob no existe");
             return; 
        } 
        String downloadPath = tempDir + File.separator + blobName; 
        File downloadedFile = new File(downloadPath); 
        if (downloadedFile.exists()) { 
            downloadedFile.delete();
        } try { 
            blobClient.downloadToFile(downloadPath);
         
        ImageIcon imageIcon = new ImageIcon(downloadPath); 
        Image scaledImage = imageIcon.getImage().getScaledInstance( lblimg.getWidth(), lblimg.getHeight(), Image.SCALE_SMOOTH ); 
        ImageIcon scaledIcon = new ImageIcon(scaledImage); 
        cachedImages.put(blobName, scaledIcon);
        lblimg.setOpaque(true);
        lblimg.setBackground(Color.decode("#3c3f41"));
        lblimg.setIcon(scaledIcon);
        lblimg.revalidate();
        lblimg.repaint();
        } catch (Exception e) {
        System.out.println("Error al descargar o procesar la imagen: " + e.getMessage()); 
        e.printStackTrace(); 
        lblimg.setText("Imagen no disponible"); 
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
