/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peregarcias.mightymotion.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import peregarcias.mightymotion.dto.Exercicis;
import peregarcias.mightymotion.dto.Usuario;
import peregarcias.mightymotion.dto.Workouts;

/**
 * <p><b>Clase DataAccess</b></p>
 * <p>Proporciona acceso a la base de datos de la aplicación.</p>
 * <ul>
 *  <li><b>Gestión de conexiones:</b> Se encarga de establecer y cerrar conexiones con la base de datos.</li>
 *  <li><b>Consultas de usuarios:</b> Permite obtener, insertar y actualizar datos relacionados con los usuarios.</li>
 *  <li><b>Consultas de workouts:</b> Proporciona acceso a los datos de los workouts asociados a usuarios.</li>
 *  <li><b>Consultas de ejercicios:</b> Facilita la obtención de datos sobre ejercicios y su asociación con workouts.</li>
 * </ul>
 * 
 * @author Perelluent
 * @since 23/10/2024
 */
public class DataAccess {
    
     /**
     * <p><b>Obtiene una conexión con la base de datos</b></p>
     * <p>Establece una conexión utilizando la cadena de conexión especificada.</p>
     * 
     * @return La conexión establecida, o <code>null</code> si no se puede conectar.
     */
    private Connection getConnection(){
    
        Connection connection = null;
    
        String connectionString = "jdbc:sqlserver://simulapsqlserver.database.windows.net:1433;database=simulapdb25;user=simulapdbadmin@simulapsqlserver;password=Pwd1234.;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }
    
    /**
     * <p><b>Obtiene una lista de todos los usuarios</b></p>
     * <p>Recupera todos los registros de la tabla "Usuaris".</p>
     * 
     * @return Una lista de usuarios registrados en la base de datos.
     * @throws SQLException Si ocurre un error en la consulta SQL.
     */
    public ArrayList<Usuario> getUsuarios() throws SQLException{
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuaris";
        
        Connection connection = getConnection();
        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            ResultSet resultset = selectStatement.executeQuery();
            while (resultset.next()){
                Usuario user = new Usuario();
                user.setId(resultset.getInt("Id"));
                user.setNom(resultset.getString("Nom"));
                user.setEmail(resultset.getString("Email"));
                user.setPasswordHash(resultset.getString("PasswordHash"));
                user.setInstructor(resultset.getBoolean("Instructor"));
                usuarios.add(user);
            }
            selectStatement.close();
            connection.close();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return usuarios;
    }
    
    /**
     * <p><b>Obtiene un usuario por su correo electrónico</b></p>
     * <p>Busca un usuario en la tabla "Usuaris" utilizando su dirección de correo electrónico.</p>
     * 
     * @param email Dirección de correo del usuario a buscar.
     * @return El objeto usuario encontrado, o <code>null</code> si no existe.
     */
     public Usuario getUsuario(String email){
        Usuario usuario = null;
        String sql = "SELECT * FROM Usuaris WHERE Email = ?";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            selectStatement.setString(1, email);
            ResultSet resultSet = selectStatement.executeQuery();
            
            if (resultSet.next()){
            usuario = new Usuario();
            usuario.setId(resultSet.getInt("Id"));
            usuario.setNom(resultSet.getString("Nom"));
            usuario.setEmail(resultSet.getString("Email"));
            usuario.setPasswordHash(resultSet.getString("PasswordHash"));
            usuario.setInstructor(resultSet.getBoolean("Instructor"));
            } else {
                System.out.println("No se encontró el usuario");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }
    
    /**
     * <p><b>Registra un nuevo usuario</b></p>
     * <p>Inserta un nuevo usuario en la tabla "Usuaris".</p>
     * 
     * @param u Objeto usuario con los datos a insertar.
     * @return El número de filas afectadas en la base de datos.
     */
    public int registrarUsuario(Usuario u) {
        
        int nuevoUsuarioId = 0;
        Connection connection = getConnection();
        String sql = "INSERT INTO Usuaris (Nom, Email, PasswordHash, Instructor)"
                + "VALUES (?,?,?,?)";
        try {
        PreparedStatement insertStatement = connection.prepareStatement(sql);
        insertStatement.setString(1,u.getNom());
        insertStatement.setString(2,u.getEmail());
        insertStatement.setString(3,u.getPasswordHash());
        insertStatement.setBoolean(4,u.isInstructor());
        nuevoUsuarioId = insertStatement.executeUpdate();
        
        insertStatement.close();
        connection.close();
        
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    return nuevoUsuarioId;
               
    }
   
    /**
     * <p><b>Obtiene el último ID registrado en la tabla "Usuaris"</b></p>
     * 
     * @return El último identificador registrado, o <code>0</code> si no se encuentra ninguno.
     */
    public int getUltimoIdRegistrado() {
        String sql = "SELECT * FROM Usuaris";
        int usuarioId=0;
        Connection connection = getConnection();
        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            ResultSet resultset = selectStatement.executeQuery();
            while (resultset.next()){
                usuarioId = resultset.getInt(1);
            }
            selectStatement.close();
            connection.close();
        }catch (SQLException ex) {
            ex.printStackTrace();
       
        }
        return usuarioId;
    }
    
    /**
     * <p><b>Obtiene una lista de workouts</b></p>
     * <p>Recupera todos los registros de la tabla "Workouts".</p>
     * 
     * @return Una lista de workouts registrados.
     * @throws SQLException Si ocurre un error en la consulta SQL.
     */
    public ArrayList<Workouts> getWorkouts() throws SQLException{
        ArrayList<Workouts> workouts = new ArrayList<>();
        String sql = "SELECT * FROM Workouts";
        
        Connection connection = getConnection();
        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            ResultSet resultset = selectStatement.executeQuery();
            while (resultset.next()){
                Workouts workout = new Workouts();
                workout.setId(resultset.getInt("Id"));
                workout.setForDate(resultset.getDate("forDate").toLocalDate());
                workout.setUserId(resultset.getInt("UserId"));
                workout.setComments(resultset.getString("Comments"));
                workouts.add(workout);
            }
            selectStatement.close();
            connection.close();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return workouts;
    }
    
    /**
     * <p><b>Obtiene usuarios asignados a un instructor</b></p>
     * <p>Recupera una lista de usuarios que están asignados a un instructor específico.</p>
     * 
     * @param idInstructor Identificador del instructor.
     * @return Una lista de usuarios asignados al instructor.
     */
    public  ArrayList<Usuario> getUsuariosByInstructor(int idInstructor) {
         ArrayList<Usuario> usuaris = new ArrayList<>();
        String sql = "SELECT * FROM Usuaris WHERE AssignedInstructor=?";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            selectStatement.setInt(1, idInstructor);
            ResultSet resultSet = selectStatement.executeQuery();
            
             if (!resultSet.next()) {
            System.out.println("No se encontraron alumnos para el instructor con ID: " + idInstructor);
        }
            while (resultSet.next()) {
                Usuario user = new Usuario();
                user.setId(resultSet.getInt("Id"));
                user.setNom(resultSet.getString("Nom"));
                user.setEmail(resultSet.getString("Email"));
                user.setPasswordHash(resultSet.getString("PasswordHash"));
                //user.setFoto(resultSet.getByte("Foto"));
                user.setInstructor(resultSet.getBoolean("Instructor"));
                user.setAssignedInstructor(resultSet.getString("AssignedInstructor"));
                usuaris.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuaris;
    }
    
    /**
     * <p><b>Obtiene workouts asociados a un usuario</b></p>
     * <p>Recupera todos los workouts pertenecientes al usuario con el ID especificado.</p>
     * 
     * @param userId Identificador del usuario.
     * @return Una lista de workouts asociados al usuario.
     */
    public ArrayList<Workouts> getWorkoutsByUser(int userId) {
        ArrayList<Workouts> workouts = new ArrayList<>();
        String sql = "SELECT * FROM Workouts WHERE userId = ?";
        Connection connection = getConnection();
        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            selectStatement.setInt(1, userId);
            ResultSet resultset = selectStatement.executeQuery();
            while (resultset.next()){
                Workouts workout = new Workouts();
                workout.setId(resultset.getInt("Id"));
                workout.setForDate(resultset.getDate("forDate").toLocalDate());
                workout.setUserId(resultset.getInt("UserId"));
                workout.setComments(resultset.getString("Comments"));
                workouts.add(workout);
            }
            selectStatement.close();
            connection.close();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return workouts;
    }
    
    /**
     * <p><b>Obtiene una lista de ejercicios</b></p>
     * <p>Recupera todos los registros de la tabla "Exercicis".</p>
     * 
     * @return Una lista de ejercicios registrados en la base de datos.
     */
    public ArrayList<Exercicis> getExercicis() {
        ArrayList<Exercicis> exercicis = new ArrayList<>();
        String sql = "SELECT Id, Exercicis.NomExercici, Exercicis.Descripcio, Exercicis.DemoFoto"
                + " FROM Exercicis";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Exercicis exercici = new Exercicis();
                exercici.setExerciciId(resultSet.getInt("Id"));
                exercici.setNomExercici(resultSet.getString("NomExercici"));
                exercici.setDescripcio(resultSet.getString("Descripcio"));

                exercicis.add(exercici);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercicis;
    }
    
    /**
     * <p><b>Obtiene los ejercicios asociados a un workout</b></p>
     * <p>Recupera los ejercicios que están vinculados a un workout en concreto</p>
     * 
     * @param workoutId Identificador del workout.
     * @return Una lista de ejercicios asociados al workout.
     */
    public ArrayList<Exercicis> getExercicisByWorkout (int workoutId) {
        ArrayList<Exercicis> exercicis = new ArrayList<>();
        String sql = "SELECT * FROM Exercicis WHERE Id = ?";
        Connection connection = getConnection();
        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            selectStatement.setInt(1,workoutId);
            ResultSet resultset = selectStatement.executeQuery();
            while (resultset.next()) {
                Exercicis exercici = new Exercicis();
                exercici.setExerciciId(resultset.getInt("Id"));
                exercici.setNomExercici(resultset.getString("NomExercici"));
                exercici.setDescripcio(resultset.getString("Descripcio"));
                exercicis.add(exercici);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return exercicis;
    }
}
        