package com.JayMak.ToDo_App.repository;

import com.JayMak.ToDo_App.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TODO_Repo extends JpaRepository<ToDo,Long> {
}
