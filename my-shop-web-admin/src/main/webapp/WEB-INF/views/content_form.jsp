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
                        <h3 class="box-title">${tbContent.id == null ? "新增" : "编辑"}内容</h3>
                    </div>

                    <form:form cssClass="form-horizontal" id="inputForm" action="/content/save" method="post" modelAttribute="tbContent">
                        <form:hidden path="id" />
                        <div class="box-body">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">文章所属类别</label>
                                <div class="col-sm-10">
                                    <form:hidden id="categoryId" path="tbContentCategory.id" />
                                    <input id="categoryName" class="form-control" placeholder="请选择" readonly="true" data-toggle="modal" data-target="#modal-default" value="${tbContent.tbContentCategory.name}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="title" class="col-sm-2 control-label">标题</label>
                                <div class="col-sm-10">
                                    <form:input path="title" class="form-control required" placeholder="标题" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="subTitle" class="col-sm-2 control-label">子标题</label>
                                <div class="col-sm-10">
                                    <form:input path="subTitle" class="form-control required" placeholder="子标题" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="titleDesc" class="col-sm-2 control-label">标题描述</label>
                                <div class="col-sm-10">
                                    <form:input path="titleDesc" class="form-control required" placeholder="标题描述" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="url" class="col-sm-2 control-label">链接</label>
                                <div class="col-sm-10">
                                    <form:input path="url" class="form-control" placeholder="链接" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="url" class="col-sm-2 control-label">图片1</label>
                                <div class="col-sm-10">
                                    <form:input path="pic" class="form-control" placeholder="图片1" />
                                    <div id="dropz" class="dropzone"></div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="url" class="col-sm-2 control-label">图片2</label>
                                <div class="col-sm-10">
                                    <form:input path="pic2" class="form-control" placeholder="图片2" />
                                    <div id="dropz2" class="dropzone"></div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="content" class="col-sm-2 control-label">内容详情</label>
                                <div class="col-sm-10">
                                    <form:hidden path="content"/>
                                    <div id="editor">${tbContent.content}</div>
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
            $("#categoryId").val(node.id);
            $("#categoryName").val(node.name);
            $("#modal-default").modal("hide");
        });
        initwangEditor();
    });

    // wangEdit 编辑器
    function initwangEditor (){
        var E = window.wangEditor;
        var editor = new E('#editor');

        editor.customConfig.uploadImgServer = '/upload';
        editor.customConfig.uploadFileName = 'editorFile';

        editor.create();
        $("#btnSubmit").bind("click",function () {
            var contentHtml = editor.txt.html();
            $("#content").val(contentHtml);
        });
    }

    // Dropzone 图片上传功能
    App.initDropzone({
        id:"#dropz",
        url:"/upload",
        init: function () {
            this.on("success", function (file, data) {
                // 上传成功触发的事件
                $("#pic").val(data.fileName);
            });
        }
    });
    App.initDropzone({
        id:"#dropz2",
        url:"/upload",
        init: function () {
            this.on("success", function (file, data) {
                // 上传成功触发的事件
                $("#pic2").val(data.fileName);
            });
        }
    });
</script>
</body>
</html>
