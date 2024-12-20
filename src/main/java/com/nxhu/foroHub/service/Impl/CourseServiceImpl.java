package com.nxhu.foroHub.service.Impl;

import com.nxhu.foroHub.persistence.entity.CourseEntity;
import com.nxhu.foroHub.persistence.repository.CourseRepository;
import com.nxhu.foroHub.service.CourseService;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService
{
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void createCourse(CourseEntity aCourse)
    {
        try
        {
            courseRepository.save(aCourse);
        } catch (DuplicateRequestException e)
        {
            System.err.println("Error, course already exists: " + e.getMessage());
        }
    }

    @Override
    public Set<CourseEntity> getCourse()
    {
        try
        {
            return new HashSet<>(courseRepository.findAll());
        } catch (DataAccessException e)
        {
            System.err.println("Error getting themes: " + e.getMessage());
            return Collections.emptySet();
        }
    }

    @Override
    public Optional<CourseEntity> getCourseById(Long courseId)
    {
        return courseRepository.findById(courseId);
    }

    @Override
    public void deleteCourseById(Long courseId)
    {
        if (this.getCourseById(courseId).isPresent())
        {
            courseRepository.deleteById(courseId);
        } else
        {
            throw new EntityNotFoundException("Topic with ID " + courseId + " no exists");
        }
    }
}
