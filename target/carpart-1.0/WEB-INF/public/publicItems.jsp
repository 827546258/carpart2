<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<title>公共货架</title>
<meta name="decorator" content="default" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<!-- 基于jquery的树形插件  ztree -->
<script src="${ctx}/js/treeTable/jquery.treeTable.js"
	type="text/javascript"></script>
<script src="${ctx}/js/zTree/js/jquery.ztree.core-3.5.js"
	type="text/javascript"></script>
<link href="${ctx}/js/zTree/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/js/treeTable/themes/vsStyle/treeTable.css"
	rel="stylesheet" type="text/css" />
<SCRIPT type="text/javascript">


        //配置信息
        var setting = {
            view: {
                dblClickExpand: false,
                selectedMulti:false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                //beforeClick: beforeClick,
                onClick: onClick
            }
        };
        //数据源 使用c标签循环拼接json
        var zNodes =[
            <c:forEach items="${brandList}" var="menu">{id:"${menu.id}", pId:"${menu.parentId}", name:"${menu.chineseName}"},
            </c:forEach>]


        //点击事件
        function onClick(e, treeId, treeNode) {
        	//获取整个树的对象
            var zTree = $.fn.zTree.getZTreeObj("menuTree"),
                    //获取被选中的节点对象
                    nodes = zTree.getSelectedNodes(),
                    v = "";

             //alert(nodes[0].name+" "+nodes[0].mid);
           var nName=nodes[0].name;
            var nId=nodes[0].id;


            nodes.sort(function compare(a,b){return a.id-b.id;});
            for (var i=0, l=nodes.length; i<l; i++) {
                v += nodes[i].name + ",";
            }
            if (v.length > 0 ) v = v.substring(0, v.length-1);
            //cityObj.attr("value", v);
			//存品牌名称的输入框
            var textInputBrand = $("#parentMenuObj");
            //存品牌id的隐藏域
            var hiddenInputBrandId = $("#parentId");

            textInputBrand.val(nName);
            hiddenInputBrandId.val(nId);

            hideMenu();
        }
         //展示菜单
        function showMenu() {
            var cityObj = $("#parentMenuObj");
            var cityOffset = $("#parentMenuObj").offset();
            $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

            $("body").bind("mousedown", onBodyDown);

        }
        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }
        function onBodyDown(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
                hideMenu();
            }
        }
         
        /**
         初始化ztree
        */
        $(document).ready(function(){
            var tree=$.fn.zTree.init($("#menuTree"), setting, zNodes);
            tree.expandAll(true);
            //找到上次查询的节点（）
             var node = tree.getNodeByParam("id", "${items.brandId}");
             //让上次选中的节点，被选中
            tree.selectNode(node); 
            
            //try{tree.checkNode(node, true, false);}catch(e){}
        });
        //-->
        
        
        function searchList(){
        var form = $("#searchPublicForm");
        form.submit();
    }

    </SCRIPT>

<script type="text/javascript">
    $('.hnavmore li a').click(function(e){
        var val = $(this).text();
        var data_val = $(this).attr('data-value');
        $(this).parents('.hnavmore').find('input').val(data_val);
        $(this).parents('.hnavmore').find('.cur-span').text(val);
    });
    
	function productgroupedit(id) {
			$.jBox("iframe:${ctx}/salecommodityDetatail.action?id="+id, {
			title : "商品详情",
			attach: $('#idett'),
			width : 1020,
			height : 700,
			buttons : { /*'关闭': true*/}
		});
	}
</script>


<style type="text/css">
ul.ztree {
	margin-top: 10px;
	border: 1px solid #617775;
	background: #f0f6e4;
	width: 220px;
	height: 230px;
	overflow-y: scroll;
	overflow-x: auto;
}

ul.log {
	border: 1px solid #617775;
	background: #f0f6e4;
	width: 300px;
	height: 170px;
	overflow: hidden;
}

ul.log.small {
	height: 45px;
}

ul.log li {
	color: #666666;
	list-style: none;
	padding-left: 10px;
}

ul.log li.dark {
	background-color: #E3E3E3;
}

#menuEdit td {
	line-height: 32px;
}
</style>

</head>
<body>

	<div class="bg_color1" id="idett">
		<!--公共货架-->
		<div class="pagebody1" style="height:700px">




			<div class="wid">
				<ul class="ht1">
					<li class="big3" style="display:inline;"><a
						href="${ctx}/public/publicItems">配件市场</a></li>
					<li class="big4" style="display:inline;"><a
						href="${ctx}">精品市场</a></li>
				</ul>
				<div></div>
				<div class="pagebody1_right">
					<form id="searchPublicForm" name="searchPublicForm"
					
						action="${ctx}/public/publicItems" method="post">
						<input name="title" type="text" class="input4" placeholder="标题"
							value="${items.title}" /> <input name="content" type="text"
							class="input4" placeholder="描述" value="${items.content}" /> <input
							type="text" class="input4" id="parentMenuObj" name="brandName"
							value="${brandName}" readonly="true"
							onclick="showMenu(); return false;" placeholder="汽车品牌"></span> <input
							type="hidden" id="parentId" name="brandId"
							value="${items.brandId}"> <select class="input1"
								id="partId" name="partId" placeholder="配件类别">
									<option value="">请选择</option>
									<c:forEach var="cp" items="${partsList}">
										<c:if test="${cp.id!=items.partId}">
											<option value="${cp.id}">${cp.name}</option>
										</c:if>
										<c:if test="${cp.id==items.partId}">
											<option value="${cp.id}" selected>${cp.name}</option>
										</c:if>

									</c:forEach>
							</select> <input name="" onclick="searchList();" type="button"
								value="搜&nbsp;&nbsp;索" class="pagebody1_btn" />
								<div id="menuContent" class="menuContent"
									style="display:none; position: absolute;">
									<ul id="menuTree" class="ztree" style="margin-top:0;"></ul>
								</div>
					</form>

				</div>

				<div class="clear"></div>
			</div>
			<div class="pagebody2 mr1 wid">
				<div class="wid2">
					<c:forEach var="sale" items="${page.list}">
						<ul class="qps">
							<a href="#" onclick="productgroupedit('${sale.id}');"
								class="fban">
								<div class="goods">
									<img src="${sale.picUrl}" />
									<div class="goods1">
										<h3>
											<span title="${sale.title}" style="cursor:pointer;">
												${sale.title} </span>
										</h3>
										<p>
											<span title="${sale.content}" style="cursor:pointer;">

												${sale.content} </span>
										</p>
										<p>
											<fmt:formatDate value="${sale.updateTime}"
												pattern="yyyy-MM-dd HH:mm:ss" />
										</p>
									</div>
								</div>
							</a>
						</ul>
					</c:forEach>
					<div class="clear"></div>
				</div>
			</div>
			
			<div class="clear"></div>

			 <!--翻页按钮-->
			<ul class="page">
				
			
						<c:forEach begin="1" end="${page.pages}" var="pageSize">
					    <c:choose>
					    <c:when test="${pageSize==page.pageNum}">
					    <a href="${ctx}/public/publicItems?pageNo=${pageSize}&pageSize=8&title=${items.title}&content=${items.content}&brandId=${items.brandId}&brandName=${brandName}&partId=${items.partId}" style="background: #C30D23 none repeat scroll 0% 0%;color:#ffffff;">${pageSize}</a>
					     </c:when>
					   <c:otherwise>
					   <a href="${ctx}/public/publicItems?pageNo=${pageSize}&pageSize=8&title=${items.title}&content=${items.content}&brandId=${items.brandId}&brandName=${brandName}&partId=${items.partId}">${pageSize}</a>
					   </c:otherwise>
					   </c:choose>
						
						</c:forEach>
				
			</ul> 
			<div class="clear"></div>
		</div>
	</div>
	<div class="bg_color2">
		<div class="footer wid">Copyright © 2014-2024 www.xiupeilian.com
			All Rights Reserved. 修配连 版权所有</div>
	</div>

</body>
</html>
