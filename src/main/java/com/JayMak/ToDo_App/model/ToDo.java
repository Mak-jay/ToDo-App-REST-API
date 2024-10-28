package com.JayMak.ToDo_App.model;

import jakarta.persistence.*;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Entity
@Table (name="TODO")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(nullable = false)
    private String title;
    private boolean completed;
    @Column(name="created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @Column(name="updated_at",nullable = false,updatable = false)
    private LocalDateTime updatedAt;

    public ToDo(){
    }
    public ToDo(String title)
    {
        this.title=title;
        this.completed=false;
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
