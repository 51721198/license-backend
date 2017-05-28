$("document").ready(function () {
    $("#go").click(function () {
        if ($("#name").val() == '' || $("#name").val() < 1) {
            alert("医院名称必填！！");
            return false;
        }
    })
    //html页面之间跳转传值！！！！！
    var parm1 = getParam('hospitalNumber');
    //alert(parm1);
    function getParam(paramName) {
        paramValue = "";
        isFound = false;
        if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {
            arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&");
            i = 0;
            while (i < arrSource.length && !isFound) {
                if (arrSource[i].indexOf("=") > 0) {
                    if (arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase()) {
                        paramValue = arrSource[i].split("=")[1];
                        isFound = true;
                    }
                }
                i++;
            }
        }
        return paramValue;
    }

    $.ajax({
        type: "GET",
        url: "/license/hospitalController/showone?hospitalNumber=" + parm1,
        dataType: "json",
        success: function (data) {
            $("#name").val(data.resultobject.hospitalName);
            $("#phone").val(data.resultobject.hospitalPhone);
            $("#address").val(data.resultobject.hospitalAddress);
            $("#number").val(data.resultobject.hospitalNumber);
        },
        error: function (jqXHR) {
            alert("发生错误：" + jqXHR.status);
        },
    })

});