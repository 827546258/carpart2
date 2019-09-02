package com.xiupeilian.carpart.mapper;

import com.xiupeilian.carpart.base.BaseMapper;
import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.vo.CompanyPictureVo;

public interface CompanyMapper extends BaseMapper<Company> {

    Company findCompanyByName(String companyname);

    Company selectByPrimaryKey(Integer id);

    void updateCompanyMemoById(CompanyPictureVo vo);

    void updateCompanyPictureById2(CompanyPictureVo vo);

    void updateCompanyPictureById3(CompanyPictureVo vo);
}