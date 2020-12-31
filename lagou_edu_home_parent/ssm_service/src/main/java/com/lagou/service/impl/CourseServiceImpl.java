package com.lagou.service.impl;

import com.github.pagehelper.PageInfo;
import com.lagou.dao.CourseMapper;
import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;
import com.lagou.domain.Teacher;
import com.lagou.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public List<Course> findCourseByCondition(CourseVO courseVO) {
       return courseMapper.findCourseByCondition(courseVO);
    }

    @Override
    public void saveCourseOrTeacher(CourseVO courseVO) {
        Course course = new Course();
        //将名称相同的属性值封装到course属性中
        BeanUtils.copyProperties(courseVO,course);
        Date date = new Date();
        course.setCreateTime(date);
        course.setUpdateTime(date);

        courseMapper.saveCourse(course);
        int id = course.getId();
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(courseVO,teacher);
        teacher.setCreateTime(date);
        teacher.setUpdateTime(date);
        teacher.setIsDel(0);
        teacher.setCourseId(id);
        courseMapper.saveTeacher(teacher);
    }

    @Override
    public void updateCourseOrTeacher(CourseVO courseVO) {
        Course course = new Course();
        //将名称相同的属性值封装到course属性中
        BeanUtils.copyProperties(courseVO,course);
        Date date = new Date();
        course.setUpdateTime(date);
        courseMapper.updateCourse(course);

        System.out.println(course);
        int id = course.getId();
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(courseVO,teacher);
        teacher.setUpdateTime(date);
        teacher.setCourseId(id);
        courseMapper.updateTeacher(teacher);
        System.out.println(teacher);
    }

    @Override
    public CourseVO findCourseById(Integer id) {
        return courseMapper.findCourseById(id);
    }

    @Override
    public void updateCourseStatus(int courseId, int status) {
        Course course = new Course();
        course.setId(courseId);
        course.setStatus(status);
        course.setUpdateTime(new Date());

        courseMapper.updateCourseStatus(course);


    }
}
