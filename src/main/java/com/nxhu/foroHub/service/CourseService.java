package com.nxhu.foroHub.service;


import com.nxhu.foroHub.persistence.entity.CourseEntity;

import java.util.Optional;
import java.util.Set;

public interface CourseService
{
    void createCourse(CourseEntity aCourse);

    Set<CourseEntity> getCourse();

    Optional<CourseEntity> getCourseById(Long courseId);

    void deleteCourseById(Long courseId);
}
