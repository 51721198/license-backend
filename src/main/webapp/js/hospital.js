$(document).ready(function () {                                         //初始化jQuery
    $.ajax({
        type: "GET",
        url: "/license/hospitalController/showhospital",
        dataType: "json",
        success: function (data) {
            var pageTiao = 30;             //每页展示多少条数据
            //计算总页面数
            var totalPage = 1;
            if (data.resultobject.length % pageTiao != 0) {
                totalPage = data.resultobject.length / pageTiao + 1;
                //alert(totalPage);
            }
            if (data.resultobject.length % pageTiao == 0) {
                totalPage = data.resultobject.length / pageTiao;
                //alert(totalPage);
            }
            function initUI(pageNo, pageSize) {
                //alert("here2");

                var rows = $("#allhospital").find("tr").length;
                if (rows > 1) { // 判断table中是否有数据，若有则先进行清除
                    for (var j = rows - 1; j > 0; j--) {
                        //从表的最下端往最上端删除，防止删到表头！！！！！注意这种表删除的写法
                        $("#allhospital").find("tr").eq(j).remove();
                        console.info(j);
                    }
                }


                for (var i = (pageNo - 1) * pageSize; i < pageNo * pageSize; i++) {
                    var but = "button_" + i;
                    var but2 = "button2_" + i;
                    $("#allhospital").append("<tr><td>"
                        + data.resultobject[i].hospitalNumber + "</td><td>"
                        + data.resultobject[i].hospitalName + "</td><td>"
                        + data.resultobject[i].hospitalPhone + "</td><td>"
                        + data.resultobject[i].hospitalAddress + "</td><td>"
                        + "<button id=" + but + " type='button' class='del' value=" + data.resultobject[i].hospitalNumber + ">删除</button>"
                        + "&nbsp&nbsp&nbsp&nbsp<button id=" + but2 + " type='button' class='mod' value=" + data.resultobject[i].hospitalNumber + ">修改</button>"
                        + "</td></tr>");

                    $("#allhospital tr").hover(
                        function () {
                            if (this.id != "trid") {
                                $(this).css("background-color", "#EEEEEE");
                            }
                        },
                        function () {
                            $(this).css("background-color", "");
                        });

                    $("#" + but).on("click", function () {
                        //do something;
                        var test = $(this).val();
                        if (confirm("确定删除第: " + test + " 条医院信息？")) {
                            $.ajax({
                                type: "GET",
                                url: "/license/hospitalController/deletehospital?hospitalNumber=" + test,
                                dataType: "json",
                                success: function (data) {
                                    if (data.resultcode == 1) {
                                        alert(data.resultdesc);
                                        location.href = "/license/bounceController/toshowallhospital";
                                    } else {
                                        alert(data.resultmessage);
                                    }
                                },
                                error: function (jqXHR) {
                                    alert("发生错误：" + jqXHR.status);
                                }
                            });
                        }
                    });

                    $("#" + but2).on("click", function () {
                        //do something;
                        var test2 = $(this).val();
                        location.href = "/license/bounceController/toaddhospital?hospitalNumber=" + test2;

                    });
                }

                pagination({                                //定义四个参数
                    cur: pageNo,              //当前页
                    total: totalPage,                 //总的页面数
                    len: 4,                   //显示多少个可点的数字
                    targetId: 'pagination',   //分页条在页面中的DOM位置
                    callback: function () {
                        var me = this;
                        var oPages = $(".page-index");
                        for (var i = 0; i < oPages.length; i++) {
                            oPages[i].onclick = function () {
                                initUI(this.getAttribute('data-index'), pageTiao);
                            }
                        }
                        var goPage = $("#go-search")
                        goPage.onclick = function () {
                            var index = $("#yeshu").val();
                            if (!index || (+index > me.total) || (+index < 1)) {
                                return;
                            }
                            initUI(index, pageTiao);
                        }
                    }
                });
            }

            initUI(1, pageTiao);

        },
        error: function (jqXHR) {
            alert("发生错误：" + jqXHR.status);
        }
    });
});	