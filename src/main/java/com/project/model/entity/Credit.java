package com.project.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Credit {

    @Id
    private int id;

    private int score;

    public Credit(){}

    public Credit(int id, int score) {
        this.id = id;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
