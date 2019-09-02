package com.xiupeilian.carpart.service.impl;/*
@Description TODO
@Author jiang xiaoyu
@Date 2019/8/30 11:19
@Version 1.0
*/

import com.xiupeilian.carpart.mapper.CompanyMapper;
import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.service.CompanyService;
import com.xiupeilian.carpart.vo.CompanyPictureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyMapper companyMapper;



    @Override
    public Company findCompanyByCompanyID(Integer id) {
        return companyMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updatecompany(Company company) {
        companyMapper.updateByPrimaryKey(company);
    }

    @Override
    public void updateCompanyPictureById(CompanyPictureVo vo) {
        companyMapper.updateCompanyMemoById(vo);
    }

    @Override
    public void updateCompanyPictureById2(CompanyPictureVo vo) {
        companyMapper.updateCompanyPictureById2(vo);
    }

    @Override
    public void updateCompanyPictureById3(CompanyPictureVo vo) {
        companyMapper.updateCompanyPictureById3(vo);
    }


}
