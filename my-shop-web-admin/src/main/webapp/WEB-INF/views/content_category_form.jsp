<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>

<!DOCTYPE html>
<html>
<head>
    <title>我的商城 | 内容管理</title>
    <jsp:include page="../includes/header.jsp" />
</head>
<body class="hold-transition skin-blue sidebar-mini">
<jsp:include page="../includes/nav.jsp" />
<jsp:include page="../includes/menu.jsp" />
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            内容管理
            <small></small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li class="active">控制面板</li>
        </ol>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="row">
            <div class="col-xs-12">
                <c:if test="${baseResult.message != null}">
                    <div class="alert alert-${baseResult.status == 200 ? "success" : "danger"} alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                            ${baseResult.message}
                    </div>
                </c:if>
                <div class="box box-info">
                    <div class="box-header with-border">
                        <h3 class="box-title">${tbContentCategory.id == null ? "新增" : "编辑"}分类</h3>
                    </div>

                    <form:form cssClass="form-horizontal" id="inputForm" action="/content/category/save" method="post" modelAttribute="tbContentCategory">
                        <form:hidden path="id" />
                        <div class="box-body">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">父级类目</label>
                                <div class="col-sm-10">
                                    <form:hidden id="parentId" path="parent.id" />
                                    <input id="parentName" class="form-control" placeholder="请选择" readonly="true" data-toggle="modal" data-target="#modal-default" value="${tbContentCategory.parent.name}"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">分类名称</label>

                                <div class="col-sm-10">
                                    <form:input path="name" class="form-control required" placeholder="分类名称" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">分类排序</label>

                                <div class="col-sm-10">
                                    <form:input path="sortOrder" class="form-control digits required" placeholder="分类排序" />
                                </div>
                            </div>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer">
                            <button type="button" class="btn btn-default" onclick="history.go(-1);">取消并返回</button>
                            <button id="btnSubmit" type="submit" class="btn btn-info pull-right">提交</button>
                        </div>
                        <!-- /.box-footer -->
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../includes/copyRight.jsp" />
<jsp:include page="../includes/footer.jsp" />

<!-- 自定义标签 模态框 -->
<sys:modal title="请选择一个类别" message="<ul id='myTree' class='ztree'></ul>"/>

<script>
    $(function () {
        // ztree 树状数据结构
        App.initZtree("/content/category/tree/data",["id"],function (nodes) {
            var node = nodes[0];
            $("#parentId").val(node.id);
            $("#parentName").val(node.name);
            $("#modal-default").modal("hide");
        });
    });
</script>
</body>
</html>
