/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peregarcias.mightymotion.dto;

/**
 *
 * @author morda
 */
public class Exercicis {
    
    private int exerciciId;
    private String nomExercici;
    private String descripcio;

    public Exercicis(int exerciciId, String nomExercici, String descripcio) {
        this.exerciciId = exerciciId;
        this.nomExercici = nomExercici;
        this.descripcio = descripcio;
    }
    public Exercicis (){
        this.exerciciId = exerciciId;
        this.nomExercici = nomExercici;
        this.descripcio = descripcio;
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
    
}
