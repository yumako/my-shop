<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 弹出提示模态框 -->
<div class="modal fade" id="modal-detail">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">查看详情</h4>
            </div>
            <div class="modal-body">
                <p id="modal-detail-body"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<script src="/static/assets/jquery-3.3.1.min.js"></script>

<script src="/static/assets/bower_components/jquery/dist/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="/static/assets/bower_components/jquery-ui/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
    $.widget.bridge('uibutton', $.ui.button);
</script>
<!-- Bootstrap 3.3.7 -->
<script src="/static/assets/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- Morris.js charts -->
<script src="/static/assets/bower_components/raphael/raphael.min.js"></script>
<script src="/static/assets/bower_components/morris.js/morris.min.js"></script>
<!-- Sparkline -->
<script src="/static/assets/bower_components/jquery-sparkline/dist/jquery.sparkline.min.js"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="/static/assets/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
<!-- jQuery Validation Plugin v1.14.0 表单验证js -->
<script src="/static/assets/plugins/jquery-validation/js/jquery.validate.js"></script>
<script src="/static/assets/plugins/jquery-validation/js/additional-methods.js"></script>
<script src="/static/assets/plugins/jquery-validation/js/localization/messages_zh.js"></script>
<!-- DataTables -->
<script src="/static/assets/bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="/static/assets/bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<!-- FastClick -->
<script src="/static/assets/bower_components/fastclick/lib/fastclick.js"></script>
<!-- Slimscroll -->
<script src="/static/assets/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="/static/assets/bower_components/fastclick/lib/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="/static/assets/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="/static/assets/js/demo.js"></script>
<!-- iCheck 1.0.1 -->
<script src="/static/assets/plugins/iCheck/icheck.min.js"></script>
<!-- TreeTables -->
<script src="/static/assets/plugins/treeTable/jquery.treeTable.js"></script>
<!-- JQuery ZTree -->
<script src="/static/assets/plugins/jquery-ztree/js/jquery.ztree.core-3.5.min.js"></script>
<!-- Dropzone 文件上传 -->
<script src="/static/assets/plugins/dropzone/min/dropzone.min.js"></script>
<!-- wangEdit 富文本编辑器-->
<script src="/static/assets/plugins/wangEdit/wangEditor.min.js"></script>

<!-- App自定义js -->
<script src="/static/assets/app/validate.js"></script>
<script src="/static/assets/app/app.js"></script>
