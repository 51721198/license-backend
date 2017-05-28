$(document).ready(function () {                                    //初始化jQuery
    $.ajax({
        type: "GET",
        url: "/license/licenseController/showallcodes",
        dataType: "json",            //jQuery将会把从服务器返回的数据格式化成此处的数据类型，若不指定jQuery将会从response的header中的Content-Type读取数据类型
        //如果数据类是“javascript”，则jquery会将返回数据转换为js代码并且执行他们,下面的data就是执行结果
        timeout: 5000,
        success: function (data) {   //这里的参数data是已经被jquery按照指定类型转换后的数据类型了
            //每页显示多少条记录
            var pageTiao = 10;
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
                var rows = $(".allcodes").find("tr").length;
                if (rows > 1) { // 判断table中是否有数据，若有则先进行清除
                    for (var j = rows - 1; j > 0; j--) {
                        //从表的最下端往最上端删除，防止删到表头！！！！！注意这种表删除的写法
                        $(".allcodes").find("tr").eq(j).remove();
                        console.info(j);
                    }
                }

                // alert("测试部署!!!!");
                var myDate = new Date();
                var dueTime = " 23:59:59"

                for (var i = (pageNo - 1) * pageSize; i < pageNo * pageSize; i++) {
                    var row = "rownum_" + i;
                    var but = "button_" + i;
                    var but2 = "button2_" + i;
                    var state = "";
                    if (data.resultobject[i].licenseState === 1) {
                        state = "yes";
                        $("#" + but2).attr('disabled', true);
                    }
                    else {
                        state = "no";
                    }

                    $(".allcodes").append("<tr id=" + row + "><td>"
                        + data.resultobject[i].serialNumberId + "</td><td>"
                        + data.resultobject[i].sourceNumber + "</td><td>"
                        + data.resultobject[i].createDate + "</td><td>"
                        + data.resultobject[i].expiredDate + "</td><td style='word-break:break-all'>"
                        + data.resultobject[i].encryptedNumber + "</td><td>"
                        + data.resultobject[i].hospital.hospitalName + "</td><td>"
                        + state + "</td><td>"
                        + "<button id=" + but + " class='del' type='button' value=" + data.resultobject[i].serialNumberId + ">删除</button>"
                        + "&nbsp&nbsp&nbsp<button id=" + but2 + " class='mod' type='button' value=" + data.resultobject[i].serialNumberId + ">使用</button>" + "</td></tr>"
                    );


                    if (data.resultobject[i].licenseState === 1) {
                        $("#" + but2).attr('disabled', true);
                        $("#" + but2).html("已发布");
                    }


                    var duedate = data.resultobject[i].expiredDate + dueTime;
                    var dueDate = new Date(duedate.replace('-', '/'));
                    if (myDate >= dueDate) {
                        //alert("#rownum_"+i);
                        $("#" + row).css("background-color", "#CCCCCC");
                        //$("#"+row).css("color","red");
                    }

                    $("#" + but).on("click", function () {
                        var test = $(this).val();
                        if (confirm("确定删除第: " + test + " 条记录？")) {
                            $.ajax({
                                type: "GET",
                                url: "/license/licenseController/deletecode?serialNumberId=" + test,
                                dataType: "json",
                                success: function (text) {
                                    if (text.resultcode == 1) {
                                        alert(text.resultdesc);
                                        location.href = "/license/bounceController/toshowallcodes";
                                    } else {
                                        alert(text.resultmessage);
                                    }
                                },
                                error: function (jqXHR) {
                                    alert("发生错误：" + jqXHR.status);
                                },
                            });
                        }
                    });

                    $("#" + but2).on("click", function () {
                        var test = $(this).val();
                        if (confirm("确认使用第: " + test + " 条序列号？")) {
                            $(this).attr('disabled', true);
                            $(this).html("已发布");
                            location.href = "/license/licenseController/uselicense?serialNumberId=" + test;
                        }
                    });

                    console.info($(".allcodes").find("tr").length);
                }
                //拼接完成后读取整个表的行数
                console.info("第二次" + $(".allcodes").find("tr").length);


                pagination({                           //定义四个参数
                    cur: pageNo,              //当前页
                    total: totalPage,         //总的页面数
                    len: 4,                   //显示多少个可点的数字
                    targetId: 'pagination',   //分页条在页面中的位置
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
        },
    });
});