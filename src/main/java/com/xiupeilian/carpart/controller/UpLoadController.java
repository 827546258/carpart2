package com.xiupeilian.carpart.controller;/*
@Description TODO
@Author jiang xiaoyu
@Date 2019/8/28 20:49
@Version 1.0
*/

import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.service.CompanyService;
import com.xiupeilian.carpart.service.ItemsService;
import com.xiupeilian.carpart.sysconstant.SysConstant;
import com.xiupeilian.carpart.util.AliyunOSSClientUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/upLoad")
public class UpLoadController {
    @Autowired
    private ItemsService itemService;
    @Autowired
    private CompanyService companyService;
    @RequestMapping("/myUpload")
    public String upload(HttpServletRequest request) {
        //Items item=itemService.findItemByid(1);
        //request.setAttribute("imgurl",item.getPicUrl());
        return "upload/index";
    }
    @RequestMapping(value = "photoupload", method = {RequestMethod.POST, RequestMethod.GET})
    public void myphotoupload(HttpServletRequest request, @RequestParam("file") MultipartFile file, HttpServletResponse response, Company company) throws IOException {
        CommonsMultipartFile cf = (CommonsMultipartFile) file;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File f = fi.getStoreLocation();
        System.err.println(AliyunOSSClientUtil.uploadObject2OSS(AliyunOSSClientUtil.getOSSClient(), f, SysConstant.BACKET_NAME, SysConstant.FOLDER));
        //String url = AliyunOSSClientUtil.uploadObject2OSS(AliyunOSSClientUtil.getOSSClient(), f, SysConstant.BACKET_NAME, SysConstant.FOLDER);
        //System.out.println(AliyunOSSClientUtil.getUrl((SysConstant.FOLDER+f.getName())));
        //System.out.println("ͼƬք؃ϊַ֘"+"https://"+SysConstant.BACKET_NAME+"."+SysConstant.ENDPOINT+"/"+SysConstant.FOLDER+f.getName());
        String imgUrl="https://"+SysConstant.BACKET_NAME+"."+SysConstant.ENDPOINT+"/"+SysConstant.FOLDER+f.getName();
        response.getWriter().write(AliyunOSSClientUtil.getUrl((SysConstant.FOLDER+f.getName())));
        System.out.println("ssssssssssssssssssssssssssssssssssss");
        System.out.println(AliyunOSSClientUtil.getUrl((SysConstant.FOLDER+f.getName())));
        System.out.println("ssssssssssssssssssssssssssssssssssss");
        System.out.println(imgUrl);
    }
}
