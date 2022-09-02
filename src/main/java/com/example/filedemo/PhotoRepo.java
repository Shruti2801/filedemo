package com.example.filedemo;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PhotoRepo extends JpaRepository<Photo, String>{
	Photo findByName(String name);
}
