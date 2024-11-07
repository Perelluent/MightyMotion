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
import java.util.logging.Level;
import java.util.logging.Logger;
import peregarcias.mightymotion.dto.Usuario;

/**
 *
 * @author morda
 */
public class DataAccess {
    
    private Connection getConnection() throws SQLException {
    
        Connection connection = null;
    
        String connectionString = "jdbc:sqlserver://localhost;database=simulabdb"
                + ";user=sa;password=lluent3105;";
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }
    
    public int registrarUsuario(Usuario u) throws SQLException {
        
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
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    return nuevoUsuarioId;
               
    }
    
    public int getUltimoIdRegistrado() throws SQLException {
        String sql = "SELECT MAX(Id) FROM Usuaris";
        int userId = 0;
        
        Connection connection = getConnection();
        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            ResultSet resultset = selectStatement.executeQuery();
            while (resultset.next()){
                userId = resultset.getInt(1);
               
            }
            selectStatement.close();
            connection.close();
        }catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userId;
    }
}
    



