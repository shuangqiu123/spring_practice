package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {
    @Autowired
    private PromotionAdService promotionAdService;


    @RequestMapping("/findAllPromotionAdByPage")
    public ResponseResult findAllPromotionAdByPage(PromotionAdVO promotionAdVO) {
        PageInfo page = promotionAdService.findAllAdByPage(promotionAdVO);
        return new ResponseResult(true,200,"响应成功",page);
    }

    @RequestMapping("/PromotionAdUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException();
        }
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

    @RequestMapping("/updatePromotionAdStatus")
    public ResponseResult updateCourseStatus(@RequestParam int id, @RequestParam int status) {
        try {
            if (status == 1) {
                promotionAdService.updatePromotionAdStatus(id, status);
            } else {
                promotionAdService.updatePromotionAdStatus(id, 0);
            }
            Map<String, Integer> map = new HashMap<>();
            map.put("status", status);
            ResponseResult result = new ResponseResult(true, 200, "响应成功", map);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
