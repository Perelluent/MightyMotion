/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peregarcias.mightymotion.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    
    public int registrarUsuario(Usuario u) throws SQLException {
        
        int nuevoUsuarioId = 0;
        Connection connection = getConnection();
        String sql = "INSERT INTO Usuaris (Nom, Email, PasswordHash, IsInstructor)"
                + "VALUES (?,?,?,?)"
                + "SELECT CAST(SCOPE_IDENTITY() AS INT)";
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
}


