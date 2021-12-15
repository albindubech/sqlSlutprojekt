package org.example.daos;

import org.example.App;
import org.example.Student;

import javax.persistence.TypedQuery;
import java.util.List;

public class StudentDao implements MainDao<Student> {
    DatabaseConnection dc = new DatabaseConnection();
    App app = new App();

    public StudentDao() {
    }

    @Override
    public void add(Student student) {
        dc.begin();
        dc.em.persist(student);
        dc.commit();
    }

    @Override
    public void update(Student student) {
        dc.begin();
        dc.em.merge(student);
        dc.commit();
    }

    @Override
    public void remove(Student student) {
        dc.begin();
        dc.em.remove(student);
        dc.commit();
    }

    @Override
    public List<Student> showSpecificInfo(int id) {
        TypedQuery<Student> query = dc.em.createQuery("SELECT s FROM Student s WHERE studentId = :id", Student.class);
        return query.setParameter("id", id).getResultList();
    }

    @Override
    public List<Student> showAll() {
        TypedQuery<Student> query = dc.em.createQuery("SELECT s FROM Student s", Student.class);
        return query.getResultList();
    }

    @Override
    public Student getById(String question) {
        int id = app.verifyInteger(question);
        return dc.em.find(Student.class, id);
    }

    public void numberOfStudentsInSchool(){
        TypedQuery<Long> query = dc.em.createQuery("SELECT COUNT(s) FROM Student s", Long.class);
        System.out.println("Antal elever i skolan: " + query.getResultList());
    }

    public void numberOfStudentsInProgramme(int programmeId){
        TypedQuery<Long> query = dc.em.createQuery("SELECT COUNT(s) FROM Student s WHERE s.programmeId = :programmeId", Long.class);
        query.setParameter("programmeId", programmeId);
        System.out.println("Antal elever i program " + programmeId + " är: " + query.getResultList());
    }
}
