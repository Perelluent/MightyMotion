/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peregarcias.mightymotion.dto;


/**
 * <p><b>Clase Usuario</b></p>
 * <p>Representa un usuario en la aplicación Mighty Motion.</p>
 * <ul>
 *  <li><b>Id:</b> Identificador único de cada usuario.</li>
 *  <li><b>Nom:</b> Nombre del usuario.</li>
 *  <li><b>Email:</b> Dirección de correo electrónico única para identificar al usuario.</li>
 *  <li><b>PasswordHash:</b> Hash seguro de la contraseña del usuario.</li>
 *  <li><b>Foto:</b> Imagen asociada al usuario.</li>
 *  <li><b>Instructor:</b> Define si el usuario tiene privilegios de instructor.</li>
 *  <li><b>AssignedInstructor:</b> Nombre del instructor asignado.</li>
 * </ul>
 * 
 * @author Perelluent
 * @since 23/10/2024
 */
public class Usuario {
    
    private int Id;
    private String Nom;
    private String Email;
    private String PasswordHash;
    private byte Foto;
    private boolean Instructor;
    private String AssignedInstructor;

    @Override
    public String toString() {
        return this.Nom; //+ "//" + Email + ", Instructor=" + Instructor + ", AssignedInstructor=" + AssignedInstructor + '}';
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public void setPasswordHash(String PasswordHash) {
        this.PasswordHash = PasswordHash;
    }

    public byte getFoto() {
        return Foto;
    }

    public void setFoto(byte Foto) {
        this.Foto = Foto;
    }

    public boolean isInstructor() {
        return Instructor;
    }

    public void setInstructor(boolean Instructor) {
        this.Instructor = Instructor;
    }

    public String getAssignedInstructor() {
        return AssignedInstructor;
    }

    public void setAssignedInstructor(String AssignedInstructor) {
        this.AssignedInstructor = AssignedInstructor;
    }
   
}

