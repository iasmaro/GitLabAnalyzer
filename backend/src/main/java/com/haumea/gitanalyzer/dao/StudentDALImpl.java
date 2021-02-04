package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDALImpl implements StudentDAL{

    private final MongoTemplate mongoTemplate;

    @Autowired
    public StudentDALImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Student> getAllStudents() {
        return mongoTemplate.findAll(Student.class);
    }

    @Override
    public Student addNewStudent(Student student){
        // with mongoTemplte, ID is auto generated
        mongoTemplate.save(student);
        return student;
    }

}
