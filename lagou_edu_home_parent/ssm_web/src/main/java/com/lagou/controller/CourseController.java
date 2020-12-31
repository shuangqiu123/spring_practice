package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping("/findCourseByCondition")
    public ResponseResult findCourseByCondition(@RequestBody CourseVO courseVO){
        List<Course> courseList = courseService.findCourseByCondition(courseVO);

        ResponseResult responseResult = new ResponseResult(true, 200, "相应成功", courseList);
        return responseResult;
    }

    @RequestMapping("/courseUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException();
        }
        System.out.println("..");
        String realPath = httpServletRequest.getServletContext().getRealPath("/");
        String substring = realPath.substring(0, realPath.indexOf("ssm_web"));
        String originalFilename = file.getOriginalFilename();

        String s = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

        String uploadPath = substring + "upload\\";
        File filePath = new File(uploadPath, s);

        if (!filePath.getParentFile().exists()){
            filePath.getParentFile().mkdirs();
        }

        file.transferTo(filePath);

        Map<String,String> map = new HashMap<>();
        map.put("fileName",s);
        map.put("filePath","http://localhost/upload"+s);
        return new ResponseResult(true,200,"上传成功",map);
    }


    @RequestMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVO courseVO) {
        System.out.println(courseVO);
        if (courseVO.getId() == null){
            courseService.saveCourseOrTeacher(courseVO);
        } else {
            courseService.updateCourseOrTeacher(courseVO);
        }

        return new ResponseResult(true,200,"响应成功",null);
    }

    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(Integer id){
        CourseVO courseVO = courseService.findCourseById(id);
        return new ResponseResult(true,200,"响应成功",courseVO);
    }

    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(Integer id, Integer status) {
        courseService.updateCourseStatus(id,status);
        Map<String,Integer> map = new HashMap<>();
        map.put("status",status);
        return new ResponseResult(true,200,"相应成功",map);
    }
}
