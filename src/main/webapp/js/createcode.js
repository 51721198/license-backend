$(document).ready(function () {
    $("#createcode").click(function () {                                //生成原始序列号
        if ($("#hosnumber").val() == '') {
            $("#warn-number").html("*请选择一家医院！");
        }

        if ($("#duedate").val() == '') {
            $("#warn-date").html("*请填写到期日期！");
        }
        //alert("here");
        if ($("#hosnumber").val() !== '' && $("#duedate").val() !== '') {
            $.ajax({
                type: "GET",
                url: "/license/licenseController/createcode?hosnumber=" + $("#hosnumber").val() + "&duedate=" + $("#duedate").val(),
                dataType: "json",
                success: function (data) {
                    if (data.resultcode == 1) {
                        $("#code1").val(data.resultmessage);
                    } else {
                        alert(data.resultdesc);
                    }
                },
                error: function (jqXHR) {
                    alert("发生错误：" + jqXHR.status);
                }
            });
        }
    });

    $("#encryptcode").click(function () {                              //加密序列号
        if ($("#code1").val() == '') {
            $("#warn-encrypt").html("*请先生成序列号！");
            return false;
        }

        $.ajax({
            type: "POST",
            url: "/license/licenseController/encryptcode",
            dataType: "json",
            data: {
                sourcecode: $("#code1").val(),
            },
            success: function (data) {
                if (data.resultcode == 1) {
                    $("#code2").val(data.resultmessage);
                    $("#code3").val(data.resultobject);
                } else {
                    alert(data.resultdesc);
                }
            },
            error: function (jqXHR) {
                alert("发生错误：" + jqXHR.status);
            }
        });
    });

    //$("#hosnumber").click(function(){                                //医院下拉菜单
    $.ajax({
        type: "GET",
        url: "/license/hospitalController/showhospital",
        dataType: "json",
        success: function (text) {
            for (var i = 0; i < text.resultobject.length; i++) {
                $("#select_id").append("<option value='" + text.resultobject[i].hospitalNumber + "'>" + text.resultobject[i].hospitalName + "</option>");
                //$("option").val(data[i].hospitalName);
            }
        },

        error: function (jqXHR) {
            alert("发生错误：" + jqXHR.status);
        }
    });
    $("#duedate").click(function () {
        // body...  
        $("#code1").val('');
        $("#code2").val('');
    });

    $("#select_id").click(function () {
        // body...  
        $("#code1").val('');
        $("#code2").val('');
    });

    $("#select_id").change(function () {
        $("#hosnumber").val($("#select_id").val());
    });

    $("#go").click(function () {
        if ($("#code2").val() == '') {
            $("#warn-go").html("*请先获取加密序列号！");
            return false;
        }
    });

});	        //总函数关闭括号
