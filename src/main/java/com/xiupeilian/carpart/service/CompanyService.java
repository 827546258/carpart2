package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.vo.CompanyPictureVo;

public interface CompanyService {


    Company findCompanyByCompanyID(Integer id);

    void updatecompany(Company company);


    void updateCompanyPictureById(CompanyPictureVo vo);

    void updateCompanyPictureById2(CompanyPictureVo vo);

    void updateCompanyPictureById3(CompanyPictureVo vo);
}
