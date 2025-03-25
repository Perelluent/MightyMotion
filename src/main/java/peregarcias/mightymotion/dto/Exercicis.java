/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peregarcias.mightymotion.dto;

/**
 * <p><b>Clase Exercicis</b></p>
 * <p>Representa un ejercicio en la aplicación Mighty Motion.</p>
 * <ul>
 *  <li><b>Identificador único:</b> Cada ejercicio tiene un ID único para su identificación.</li>
 *  <li><b>Nombre del ejercicio:</b> Describe el nombre del ejercicio.</li>
 *  <li><b>Descripción:</b> Proporciona detalles adicionales sobre el ejercicio.</li>
 *  <li><b>Demo Foto:</b> Contiene la ruta o referencia de la imagen de demostración del ejercicio.</li>
 * </ul>
 * 
 * @author Perelluent
 * @since 23/10/2024
 */
public class Exercicis {
    
    private int exerciciId;
    private String nomExercici;
    private String descripcio;
    private String demoFoto;

    public Exercicis(int exerciciId, String nomExercici, String descripcio) {
        this.exerciciId = exerciciId;
        this.nomExercici = nomExercici;
        this.descripcio = descripcio;
        this.demoFoto = demoFoto;
    }
    public Exercicis (){
        this.exerciciId = exerciciId;
        this.nomExercici = nomExercici;
        this.descripcio = descripcio;
        this.demoFoto = demoFoto;
    }

    public int getExerciciId() {
        return exerciciId;
    }

    public void setExerciciId(int exerciciId) {
        this.exerciciId = exerciciId;
    }

    public String getNomExercici() {
        return nomExercici;
    }

    public void setNomExercici(String nomExercici) {
        this.nomExercici = nomExercici;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getDemoFoto() {
        return demoFoto;
    }

    public void setDemoFoto(String demoFoto) {
        this.demoFoto = demoFoto;
    }

    @Override
    public String toString() {
        return descripcio;
    }
    
    
}
