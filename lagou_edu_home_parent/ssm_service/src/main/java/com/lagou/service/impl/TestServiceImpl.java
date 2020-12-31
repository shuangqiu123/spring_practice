package com.lagou.service.impl;

import com.lagou.service.TestService;
import com.lagou.dao.TestDao;
import com.lagou.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDao testDao;


    @Override
    public List<Test> findAll() {
        return testDao.findAll();
    }
}
