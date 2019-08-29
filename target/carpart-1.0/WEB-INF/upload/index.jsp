<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
         <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
</head>
<body>
 
<form id="uploadForm" enctype="multipart/form-data">
        <input id="file" type="file" name="file"/>
        <br><br><br>
        <button id="upload" type="button"  onclick="uploadImg()" >测试上传图片到阿里云</button>

</form>
<img src="${imgurl}" alt="" width="100px" height="100px" id="img"/>

<script>
    function uploadImg() {
        //debugger
        $.ajax({
            type: "post",
            url: '${ctx}/upLoad/photoupload',
            data: new FormData($('#uploadForm')[0]),//https://developer.mozilla.org/zh-CN/docs/Web/API/FormData/Using_FormData_Objects
            processData: false,//不希望转换的信息  https://segmentfault.com/q/1010000007410014
            contentType: false,//默认情况下会将发送的数据序列化contentType = "application/x-www-form-urlencoded"https://segmentfault.com/q/1010000007410014
            success: function (data) {
                $("#img").html("上传成功");
                alert(data);
                $("#img").attr("src", data);
            }

        });
    }
</script>
<div style="float: left;">
           
        </div>
</body>
</html>