package com.lionel.student_jpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lionel.student_jpa.model.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student,String>{

    String joinQuery = " LEFT JOIN students_attend_courses atts ON stus.id = atts.student_id "
                        +" LEFT JOIN courses c ON c.id = atts.course_id ";
    
    @Query( value = "SELECT MAX(id) FROM students" , nativeQuery =  true)
    String getMaxId();

    @Query( value = "SELECT * FROM students s WHERE s.id LIKE :id " , nativeQuery = true)
    List<Student> findWithId( @Param("id") String id );

    @Query( value = "SELECT * FROM students s WHERE s.name LIKE :name " , nativeQuery = true )
    List<Student> findWithName( @Param("name") String name );

    @Query( value = "SELECT DISTINCT(stus.id) , stus.* FROM students stus "+joinQuery+" WHERE c.name LIKE :course" , nativeQuery = true)
    List<Student> findWithCourse( @Param("course") String course );

    @Query( value = "SELECT * FROM students s WHERE s.id LIKE :id AND s.name LIKE :name " , nativeQuery = true )
    List<Student> findWithIdAndName( @Param("id") String id , @Param("name") String name );

    @Query( value = "SELECT DISTINCT(stus.id) , stus.* FROM students stus "+joinQuery+" WHERE stus.id LIKE :id AND c.name LIKE :course" , nativeQuery = true )
    List<Student> findWithIdAndCourse( @Param("id") String id , @Param("course") String course );

    @Query( value = "SELECT DISTINCT(stus.id) , stus.* FROM students stus "+joinQuery+" WHERE stus.name LIKE :name AND c.name LIKE :course" , nativeQuery = true)
    List<Student> findWithNameAndCourse( @Param("name") String name , @Param("course") String course );

    @Query( value = "SELECT DISTINCT(stus.id) , stus.* FROM students stus "+joinQuery+" WHERE stus.id LIKE :id AND c.name LIKE :course AND stus.name LIKE :name" , nativeQuery = true)
    List<Student> findWithIdAndNameAndCourse( @Param("id") String id , @Param("name") String name , @Param("course") String course );


}
