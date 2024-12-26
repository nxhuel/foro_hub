package com.nxhu.foroHub.controller;

import com.nxhu.foroHub.dto.CourseDTO;
import com.nxhu.foroHub.persistence.entity.CourseEntity;
import com.nxhu.foroHub.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
@PreAuthorize("denyAll()")
public class CourseController
{
    @Autowired
    private CourseService courseService;

    @PostMapping("/create-course")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> createCourse(@RequestBody CourseEntity aCourse)
    {
        courseService.createCourse(aCourse);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/courses")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Set<CourseDTO>> getCourses()
    {
        boolean notFound = courseService.getCourse().isEmpty();
        if (notFound) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Set<CourseDTO> courses = courseService.getCourse().stream()
                .map(c -> new CourseDTO(c.getName(), c.getCategory()))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long courseId)
    {
        return courseService.getCourseById(courseId)
                .map(c -> new CourseDTO(c.getName(), c.getCategory()))
                .map(courseDTO -> new ResponseEntity<>(courseDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete-course/{courseId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId)
    {
        boolean notFound = courseService.getCourseById(courseId).isEmpty();
        if (notFound) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        courseService.deleteCourseById(courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
