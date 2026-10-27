
package com.example.course_management.repository;

import com.example.course_management.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Aggregation;

import java.util.List;
import java.util.Optional;
import org.bson.Document;

public interface StudentRepository
        extends MongoRepository<Student, String> {

    // 1. Find by exact email
    Optional<Student> findByEmail(String email);

    // 2. Find by name
    List<Student> findByName(String name);

    // 3. Find by course ID
    List<Student> findByCourseId(String courseId);

    // 4. Find by name AND email
    List<Student> findByNameAndEmail(
            String name, String email);

    // 5. Find by name OR email
    List<Student> findByNameOrEmail(
            String name, String email);

    // 6. Find names containing a word (case-insensitive)
    List<Student> findByNameContainingIgnoreCase(String name);

    // 7. Find by email ending with a domain
    List<Student> findByEmailEndingWith(String domain);

    // 8. Check whether email exists
    boolean existsByEmail(String email);

    // 9. Count students in a course
    long countByCourseId(String courseId);

    // 10. Delete students by course ID
    long deleteByCourseId(String courseId);

    // 11. Custom query: exact email
    @Query("{ 'email': ?0 }")
    List<Student> searchByEmail(String email);

    // 12. Aggregation: count students per course
    @Aggregation(pipeline = {
        "{ '$group': { '_id': '$courseId', " +
        "'studentCount': { '$sum': 1 } } }"
    })
    List<Document> countStudentsByCourse();

    // 13. Aggregation: students belonging to a given course
    @Aggregation(pipeline = {
        "{ '$match': { 'courseId': ?0 } }"
    })
    List<Student> aggregateStudentsByCourse(String courseId);
}
