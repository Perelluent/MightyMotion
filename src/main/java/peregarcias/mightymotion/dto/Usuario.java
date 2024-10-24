/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peregarcias.mightymotion.dto;

/**
 *
 * @author morda
 */
public class Usuario {
    
    private int Id;
    private String Nom;
    private String Email;
    private String PasswordHash;
    private byte Foto;
    private boolean Instructor;
    private String AssignedInstructor;

    public Usuario(int Id, String Nom, String Email, String PasswordHash, byte Foto, boolean Instructor, String AssignedInstructor) {
        this.Id = Id;
        this.Nom = Nom;
        this.Email = Email;
        this.PasswordHash = PasswordHash;
        this.Foto = Foto;
        this.Instructor = Instructor;
        this.AssignedInstructor = AssignedInstructor;
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

