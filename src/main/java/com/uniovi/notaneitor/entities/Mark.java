package com.uniovi.notaneitor.entities;

public class Mark {

    private Long id;
    private String description;
    private Double score;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public Double getScore() {
        return score;
    }

    public String getDescription() {
        return description;
    }
}
