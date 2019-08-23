<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<!DOCTYPE html>
<html>
<head>
    <title>我的商城 | 内容列表</title>
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
            内容管理
            <small></small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="/main"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li class="active">内容列表</li>
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
                <div class="box box-info box-info-search" style="display: none;">
                    <div class="box-header">
                        <h3 class="box-title">高级搜索</h3>
                    </div>
                    <!-- search start -->
                    <div class="form-horizontal">
                        <div class="box-body">
                            <div class="row">
                                <div class="col-xs-12 col-sm-3">
                                    <div class="form-group">
                                        <label for="title" class="col-sm-4 control-label">标题</label>
                                        <div class="col-sm-8">
                                            <input id="title" class="form-control" placeholder="请输入文章标题" />
                                        </div>
                                    </div>
                                </div>

                                <div class="col-xs-12 col-sm-3">
                                    <div class="form-group">
                                        <label for="subTitle" class="col-sm-4 control-label">子标题</label>
                                        <div class="col-sm-8">
                                            <input id="subTitle" class="form-control" placeholder="请输入文章子标题" />
                                        </div>
                                    </div>
                                </div>

                                <div class="col-xs-12 col-sm-3">
                                    <div class="form-group">
                                        <label for="titleDesc" class="col-sm-4 control-label">标题描述</label>
                                        <div class="col-sm-8">
                                            <input id="titleDesc" class="form-control" placeholder="请输入文章标题描述" />
                                        </div>
                                    </div>
                                </div>

                                <div class="col-xs-12 col-sm-3" style="text-align: center;">
                                    <div class="form-group">
                                        <button type="button" class="btn btn-info" onclick="search();">搜索</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- search end -->
                </div>

                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title">文章列表</h3>
                    </div>

                    <div class="box-body">
                        <a href="/content/form" class="btn btn-default btn-sm" type="button"><i class="fa fa-plus"><span class="btn-tools">新增</span></i></a>&nbsp;&nbsp;&nbsp;
                        <button class="btn btn-default btn-sm" type="button"><i class="fa fa-trash-o" onclick="App.deleteMulti('/content/delete')"><span class="btn-tools">删除</span></i></button>&nbsp;&nbsp;&nbsp;
                        <a href="#" class="btn btn-default btn-sm" type="button"><i class="fa fa-arrow-down"><span class="btn-tools">导入</span></i></a>&nbsp;&nbsp;&nbsp;
                        <a href="#" class="btn btn-default btn-sm" type="button"><i class="fa fa-arrow-up"><span class="btn-tools">导出</span></i></a>&nbsp;&nbsp;&nbsp;
                        <button class="btn btn-primary btn-sm" type="button" onclick="$('.box-info-search').css('display')=='none' ? $('.box-info-search').show('fast') : $('.box-info-search').hide('fast')"><i class="fa fa-search"><span class="btn-tools">搜索</span></i></button>&nbsp;&nbsp;&nbsp;
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body table-responsive">
                        <table id="dataTable" class="table table-hover">
                            <thead>
                            <tr>
                                <th><input type="checkbox" class="minimal ickeck_master" /></th>
                                <th>ID</th>
                                <th>所属分类</th>
                                <th>标题</th>
                                <th>子标题</th>
                                <th>标题描述</th>
                                <th>链接</th>
                                <th>图片1</th>
                                <th>图片2</th>
                                <th>更新时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
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
    var _dataTable;
    $(function () {
        var _columns =[
            {"data":function (row,type,val,meta) {
                    return '<input id="' + row.id + '" type="checkbox" class="minimal" />';
                }},
            {"data":"id"},
            {"data":"tbContentCategory.name"},
            {"data":"title"},
            {"data":"subTitle"},
            {"data":"titleDesc"},
            {"data":function (row,type,val,meta) {
                    return '<a href="'+row.url+'" target="_blank">查看</a>';
                }},
            {"data":function (row,type,val,meta) {
                   return '<a href="'+row.pic+'" target="_blank">查看</a>';
                }},
            {"data":function (row,type,val,meta) {
                    return '<a href="'+row.pic2+'" target="_blank">查看</a>';
                }},
            {"data":"updated",
                "render":function (date) {
                    //时间格式化
                    return App.getDateFormat(date);
                }
            },
            {"data":function (row,type,val,meta) {
                    var detailUrl = "/content/detail?id=" + row.id;
                    var deleteUrl = "/content/delete?id=" + row.id;
                    return '<button type="button" class="btn btn-sm btn-default" onclick="App.ShowDeatil(\''+detailUrl+'\');"><i class="fa fa-search"><span class="btn-tools">查看</span></i></button>&nbsp;&nbsp;&nbsp;' +
                        '<a href="/content/form?id='+ row.id +'" type="button" class="btn btn-sm btn-primary"><i class="fa fa-edit"><span class="btn-tools">编辑</span></i></a>&nbsp;&nbsp;&nbsp;' +
                        '<button type="button" class="btn btn-sm btn-danger" onclick="App.deleteSingle(\''+deleteUrl+'\')"><i class="fa fa-trash-o"><span class="btn-tools">删除</span></i></button>';
                }}
        ];
        _dataTable = App.initDataTables("/content/page",_columns);
    });

    /**
     * 条件搜索
     */
    function search() {
        var title= $("#title").val();
        var subTitle = $("#subTitle").val();
        var titleDesc = $("#titleDesc").val();
        var param = {
            "title":title,
            "subTitle":subTitle,
            "titleDesc":titleDesc
        };
        _dataTable.settings()[0].ajax.data = param;
        _dataTable.ajax.reload();
    }

</script>
</body>
</html>
