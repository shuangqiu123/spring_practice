package com.lagou.dao;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;
import com.lagou.domain.Teacher;

import java.util.List;

public interface CourseMapper {

    public List<Course> findCourseByCondition(CourseVO courseVo);

    public void saveCourse(Course course);

    public void saveTeacher(Teacher teacher);

    public void updateCourse(Course course);

    public void updateTeacher(Teacher teacher);

    public CourseVO findCourseById(Integer id);

    public void updateCourseStatus(Course course);


}
