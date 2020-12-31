package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;

import java.util.List;

public interface CourseService {

    public List<Course> findCourseByCondition(CourseVO courseVO);

    public void saveCourseOrTeacher(CourseVO courseVO);
    public void updateCourseOrTeacher(CourseVO courseVO);
    public CourseVO findCourseById(Integer id);

    public void updateCourseStatus(int courseId, int status);
}
