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
    <style>
        .btn-tools{margin-left: 4px;}
    </style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<jsp:include page="../includes/nav.jsp" />
<jsp:include page="../includes/menu.jsp" />
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            内容分类
            <small></small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="/main"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li class="active">内容分类</li>
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
                <!-- /.box-header -->

                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title">内容分类</h3>
                    </div>

                    <div class="box-body">
                        <a href="/content/category/form" class="btn btn-default btn-sm" type="button"><i class="fa fa-plus"><span class="btn-tools">新增</span></i></a>&nbsp;&nbsp;&nbsp;
                        <a href="#" class="btn btn-default btn-sm" type="button"><i class="fa fa-arrow-down"><span class="btn-tools">导入</span></i></a>&nbsp;&nbsp;&nbsp;
                        <a href="#" class="btn btn-default btn-sm" type="button"><i class="fa fa-arrow-up"><span class="btn-tools">导出</span></i></a>&nbsp;&nbsp;&nbsp;
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body table-responsive">
                        <table id="treeTable" class="table table-hover">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>名称</th>
                                <th>排序</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${tbContentCategories}" var="tbContentCategory">
                                    <tr id="${tbContentCategory.id}" pId="${tbContentCategory.parent.id}">
                                        <td>${tbContentCategory.id}</td>
                                        <td>${tbContentCategory.name}</td>
                                        <td>${tbContentCategory.sortOrder}</td>
                                        <td>
                                            <a href="/content/category/form?id=${tbContentCategory.id}" class="btn btn-info btn-xs" type="button"><i class="fa fa-edit"><span class="btn-tools">编辑</span></i></a>&nbsp;&nbsp;&nbsp;
                                            <button class="btn btn-danger btn-xs" type="button" onclick="testDel('${tbContentCategory.id}', '警告：该删除操作会将包括选中类目在内的全部子类目及属于类目的内容一并删除，请谨慎操作！您还确定继续吗？')"><i class="fa fa-trash-o"><span class="btn-tools">删除</span></i></button>&nbsp;&nbsp;&nbsp;
                                            <a href="/content/category/form?parent.id=${tbContentCategory.id}&parent.name=${tbContentCategory.name}" class="btn btn-default btn-xs" type="button"><i class="fa fa-plus"><span class="btn-tools">新增下级菜单</span></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>
        </div>
    </section>
</div>


<jsp:include page="../includes/copyRight.jsp" />
<jsp:include page="../includes/footer.jsp" />

<!-- 自定义标签 模态框 -->
<sys:modal />

<script>
    $(function () {
        $('#treeTable').treeTable({
            expandLevel: 2,
            column: 1
        });
    });
    
    function testDel(id,msg) {
        console.log(id);
        if(!msg) msg=null;
        // 将 ID 放入数组中，以便和批量删除通用
        _idArray = new Array();
        _idArray.push(id);

        $("#modal-message").html(msg == null ? "您确定删除数据项吗？" : msg);
        $("#modal-default").modal("show");

        // 绑定删除事件
        $("#btnModalOk").bind("click", function () {
            $("#modal-default").modal("hide");

            if (_idArray.length > 0) {
                // AJAX 异步删除操作
                setTimeout(function () {
                    $.ajax({
                        url: "/content/category/delete",
                        type: "POST",
                        data: {"ids" : _idArray.toString()},
                        dataType: "JSON",
                        success: function (data) {
                            console.log(data);
                            // 请求成功后，无论是成功或是失败都需要弹出模态框进行提示，所以这里需要先解绑原来的 click 事件
                            $("#btnModalOk").unbind("click");
                            // 请求成功
                            if (data.status === 200) {
                                // 刷新页面
                                $("#btnModalOk").bind("click", function () {
                                    window.location.reload();
                                });
                            }

                            // 请求失败
                            else {
                                // 确定按钮的事件改为隐藏模态框
                                $("#btnModalOk").bind("click", function () {
                                    $("#modal-default").modal("hide");
                                });
                            }

                            // 因为无论如何都需要提示信息，所以这里的模态框是必须调用的
                            $("#modal-message").html(data.message);
                            $("#modal-default").modal("show");
                        }
                    });
                }, 500)
            }
        });
    }
</script>
</body>
</html>
