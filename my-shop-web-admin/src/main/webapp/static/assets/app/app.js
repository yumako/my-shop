var App = function () {

    /**
     * 格式化时间戳
     */
    var handlerDateFormat = function (cellval) {
        var dateVal = cellval + "";
        if (cellval != null) {
            var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
            var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
            var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
            var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
            var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

            return  date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
        }
    };

    // iCheck
    var _masterCheckbox;
    var _checkbox;

    //用于存放用户 id 的数组
    var _idArray;

    // Dropzone 参数列表
    var defaultDropzoneOpts = {
        url: "", // 文件提交地址
        paramName: "dropzFile", // 传到后台的参数名称，默认为dropzFile
        method: "post",  // 也可用put
        maxFiles: 1,// 一次性上传的文件数量上限
        maxFilesize: 2, // 文件大小，单位：MB
        acceptedFiles: ".jpg,.gif,.png,.jpeg", // 上传的类型
        addRemoveLinks: true,
        parallelUploads: 1,// 一次上传的文件数量
        dictDefaultMessage: '拖动文件至此或者点击上传',
        dictMaxFilesExceeded: "您最多只能上传" + this.maxFiles + "个文件！",
        dictResponseError: '文件上传失败!',
        dictInvalidFileType: "文件类型只能是*.jpg,*.gif,*.png,*.jpeg。",
        dictFallbackMessage: "浏览器不受支持",
        dictFileTooBig: "文件过大上传文件最大支持.",
        dictRemoveLinks: "删除",
        dictCancelUpload: "取消"
    };

    /**
     * 私有方法
     */
    var handlerInitCheckbox = function () {
        // 激活 iCheck
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass   : 'iradio_minimal-blue'
        });

        //获取控制端的 Checkbox
        _masterCheckbox = $('input[type="checkbox"].minimal.ickeck_master');

        //获取全部的 Checkbox 集合
        _checkbox = $('input[type="checkbox"].minimal');
    };

    /**
     * Checkbox 全选功能
     */
    var handlerCheckboxAll = function () {
        _masterCheckbox.on("ifClicked",function (e) {
            // console.log(e.target.checked);
            //返回 true 表示未选中
            if (e.target.checked){
                _checkbox.iCheck("uncheck");
            }
            //false选中状态
            else {
                _checkbox.iCheck("check");
            }
        });
    };

    /**
     * 单个用户删除
     * @param url
     */
    var handlerDeleteSingle = function (url) {
        _idArray = new Array();
        var _id = url.split("=")[1];
        _idArray.push(_id);

        $("#modal-message").html("您确认要删除改数据项吗？");
        $("#modal-default").modal("show");

        $("#btnModalOk").bind("click",function () {
            del(url);
        });
    };

    /**
     * 批量删除功能
     * @param url
     */
    var handlerDeleteMulti = function (url) {
        _idArray = new Array();

        //将选中的id放入数组
        _checkbox.each(function () {
            var _id = $(this).attr("id");
            if (_id != null && _id != "undefine" && $(this).is(":checked")){
                _idArray.push(_id);
            }
        });

        if(_idArray.length === 0){
            $("#modal-message").html("您还未选择任何一项数据，请至少选择一项");
        }
        else {
            $("#modal-message").html("您确认要删除吗？");
        }
        $("#modal-default").modal("show");

        $("#btnModalOk").bind("click",function () {
            del(url);
        });
    };

    /**
     * AJAX 异步删除，删除用户数据
     */
    function del(url) {
        $("#modal-default").modal("hide");
        //如果没有选中任何一项数据，关闭模态框
        if (_idArray.length === 0){
            //TODO
        }
        //删除操作
        else {
            setTimeout(function () {
                $.ajax({
                    url: url,
                    type: "POST",
                    dataType: "JSON",
                    data: {"ids":_idArray.toString()},
                    success:function (data) {
                        // 请求成功后，无论是成功或是失败都需要弹出模态框进行提示，所以这里需要先解绑原来的 click 事件
                        $("#btnModalOk").unbind();
                        //删除成功
                        if(data.status == 200){
                            // 刷新页面
                            $("#btnModalOk").bind("click",function () {
                                $("#modal-default").modal("hide");
                                window.location.reload();
                            });
                        }
                        //删除失败
                        else {
                            // 确定按钮的事件改为隐藏模态框
                            $("#btnModalOk").bind("click",function () {
                                $("#modal-default").modal("hide");
                            });
                        }
                        // 因为无论如何都需要提示信息，所以这里的模态框是必须调用的
                        $("#modal-message").html(data.message);
                        $("#modal-default").modal("show");
                    }
                });
            },500);
        }
    }

    /**
     * 初始化DataTables -- 表格分页
     */
    var handlerInitDataTables = function (url,columns) {
        var _dataTable = $("#dataTable").DataTable({
            // 是否开启本地分页，
            "paging": true,
            // 是否开启本地排序
            "ordering": false,
            // 是否显示左下角信息
            "info": true,
            // 是否允许用户改变表格每页显示的记录数
            "lengthChange": false,
            // 是否显示处理状态(排序的时候，数据很多耗费时间长的话，也会显示这个)
            "processing": true,
            // 是否允许 DataTables 开启本地搜索
            "searching": false,
            // 是否开启服务器模式
            "serverSide": true,
            // 控制 DataTables 的延迟渲染，可以提高初始化的速度
            "deferRender": true,
            // 增加或修改通过 Ajax 提交到服务端的请求数据
            "ajax": {
                "url": url
            },
            "columns":columns,
            // 表格重绘的回调函数
            "drawCallback": function (settings) {
                handlerInitCheckbox();
                handlerCheckboxAll();
            },
            // 国际化
            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            }
        });
        return _dataTable;
    };

    /**
     * 初始化文件上传 Dropzone
     * @param opts
     */
    var handlerInitDropzone = function (opts) {
        //关闭 Dropzone 自动发现html节点功能
        Dropzone.autoDiscover = false;
        //继承
        $.extend(defaultDropzoneOpts,opts);
        new Dropzone(defaultDropzoneOpts.id,defaultDropzoneOpts);
    };

    /**
     * 查看用户详情
     * @param url
     */
    var handlerShowDeatil = function (url) {
        $.ajax({
            url:url,
            type:"get",
            dataType:"html",
            success:function (data) {
                $("#modal-detail-body").html(data);
                $("#modal-detail").modal("show");
            }
        });
    };

    /**
     * 初始化 Ztree 内容分类节点
     * @param url
     * @param autoParam
     * @param callback
     */
    var handlerInitZtree = function (url,autoParam,callback) {
        var setting = {
            view:{
                //关闭多选
                selectedMulti: false
            },
            async: {
                enable: true,
                url:url,
                autoParam:autoParam,
            }
        };
        $.fn.zTree.init($("#myTree"), setting);
        $("#btnModalOk").bind("click",function () {
            var zTree = $.fn.zTree.getZTreeObj("myTree");
            var nodes = zTree.getSelectedNodes();
            if (nodes.length == 0) {
                alert("请选择一个节点");
            }

            else {
                callback(nodes);
            }
        });
    }

    return {
        /**
         * 初始化 iCheck
         */
        init:function () {
            handlerInitCheckbox();
            handlerCheckboxAll();
        },

        /**
         * 初始化格式化时间戳
         * @param fmt
         * @returns {*}
         */
        getDateFormat:function (fmt) {
            return handlerDateFormat(fmt);
        },

        /**
         * 单个用户删除
         * @param url
         */
        deleteSingle:function(url){
            handlerDeleteSingle(url);
        },
        /**
         * 批量删除用户
         * @param url
         */
        deleteMulti:function (url) {
            handlerDeleteMulti(url);
        },
        /**
         * 初始化 DataTables
         * @param url
         * @param columns
         * @returns {jQuery}
         */
        initDataTables:function (url,columns) {
            return handlerInitDataTables(url,columns);
        },
        /**
         * 查看用户详情
         * @param url
         * @constructor
         */
        ShowDeatil:function (url) {
            handlerShowDeatil(url);
        },

        /**
         * 初始化 Ztree
         * @param url
         * @param autoParam
         * @param callback
         * @constructor
         */
        initZtree:function (url,autoParam,callback) {
            handlerInitZtree(url,autoParam,callback);
        },

        /**
         * 初始化文件上传 Dropzone
         * @param opts
         */
        initDropzone:function (opts) {
            handlerInitDropzone(opts);
        }
    }
}();

$(document).ready(function () {
    App.init();
});