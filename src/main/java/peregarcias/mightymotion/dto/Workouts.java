/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peregarcias.mightymotion.dto;

import java.time.LocalDate;



/**
 *
 * @author morda
 */
public class Workouts {
    
    private int id;
    private LocalDate forDate;
    private int userId;
    private String comments;

    public Workouts(int id, LocalDate forDate, int userId, String comments) {
        this.id = id;
        this.forDate = forDate;
        this.userId = userId;
        this.comments = comments;
    }
    public Workouts (){
        
    }

    public int getId() { 
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getForDate() {
        return forDate;
    }

    public void setForDate(LocalDate forDate) {
        this.forDate = forDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    
}
