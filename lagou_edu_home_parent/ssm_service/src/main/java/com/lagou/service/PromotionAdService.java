package com.lagou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;

import java.util.List;

public interface PromotionAdService {
    public PageInfo findAllAdByPage(PromotionAdVO adVo);
    public void updatePromotionAdStatus(int id, int status);
}
