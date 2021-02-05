package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

}
