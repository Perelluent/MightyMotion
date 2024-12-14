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
 *
 * @author morda
 */
public class DataAccess {
    
    private Connection getConnection(){
    
        Connection connection = null;
    
        String connectionString = "jdbc:sqlserver://localhost;database=simulabdb"
                + ";user=sa;password=lluent3105;";
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }
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
    
     public Usuario getUsuario(String email){
        Usuario user = null;
        String sql = "SELECT * FROM Usuaris WHERE Email = ?";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            selectStatement.setString(1, email);
            ResultSet resultSet = selectStatement.executeQuery();
            
            if (resultSet.next()){
            user = new Usuario();
            user.setId(resultSet.getInt("Id"));
            user.setNom(resultSet.getString("Nom"));
            user.setEmail(resultSet.getString("Email"));
            user.setPasswordHash(resultSet.getString("PasswordHash"));
            user.setInstructor(resultSet.getBoolean("Instructor"));
            } else {
                System.out.println("No se encontró el usuario");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    
    
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
    
    public ArrayList<Exercicis> getExercicisByWorkout (int workoutId) {
        ArrayList<Exercicis> exercicis = new ArrayList<>();
        String sql = "SELECT ExercicisWorkouts.IdExercici,"
                + "Exercicis.NomExercici, Exercicis.Descripcio"
                + "FROM ExercicisWorkouts INNER JOIN Exercicis ON ExercicisWorkouts.idExercici=Exercicis.Id"
                + "WHERE ExercicisWorkouts.IdWorkout=?";
        Connection connection = getConnection();
        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            selectStatement.setInt(1,workoutId);
            ResultSet resultset = selectStatement.executeQuery();
            while (resultset.next()) {
                Exercicis exercici = new Exercicis();
                exercici.setExerciciId(resultset.getInt("IdExercici"));
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
        