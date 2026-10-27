
package com.example.course_management.repository;

import com.example.course_management.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Aggregation;

import java.util.List;
import java.util.Optional;
import org.bson.Document;

public interface CourseRepository
        extends MongoRepository<Course, String> {

    // 1. Find by course name
    List<Course> findByName(String name);

    // 2. Find by department
    List<Course> findByDepartment(String department);

    // 3. Find by exact fee
    List<Course> findByFee(double fee);

    // 4. Find courses with fee greater than a value
    List<Course> findByFeeGreaterThan(double fee);

    // 5. Find courses with fee less than a value
    List<Course> findByFeeLessThan(double fee);

    // 6. Find courses with fee between two values
    List<Course> findByFeeBetween(double min, double max);

    // 7. Find course names containing a word
    List<Course> findByNameContainingIgnoreCase(String name);

    // 8. Find by department AND fee
    List<Course> findByDepartmentAndFeeGreaterThan(
            String department, double fee);

    // 9. Check whether a course name exists
    boolean existsByName(String name);

    // 10. Count courses in a department
    long countByDepartment(String department);

    // 11. Custom query: fee greater than a value
    @Query("{ 'fee': { '$gt': ?0 } }")
    List<Course> searchByMinimumFee(double fee);

    // 12. Aggregation: courses with enrolled students
    @Aggregation(pipeline = {
        "{ '$lookup': { " +
        "'from': 'students', " +
        "'localField': '_id', " +
        "'foreignField': 'courseId', " +
        "'as': 'enrolledStudents' " +
        "} }"
    })
    List<Document> findCoursesWithStudents();

    // 13. Aggregation: filter expensive courses
    @Aggregation(pipeline = {
        "{ '$match': { 'fee': { '$gt': ?0 } } }"
    })
    List<Course> aggregateCoursesAboveFee(double fee);

    // 14. Aggregation: calculate average fee
    @Aggregation(pipeline = {
        "{ '$group': { '_id': null, " +
        "'averageFee': { '$avg': '$fee' } } }"
    })
    List<Document> calculateAverageFee();
}
