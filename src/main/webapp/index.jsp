<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>

<head>
    <title>Title</title>
    <script src="./jquery.min.js"></script>
    <script>

        $(function () {
            $("#button").click(function () {

                var x1xishu=$("#x1xishu").val();
                var x2xishu=$("#x2xishu").val();
                var x3xishu=$("#x3xishu").val();
                var x1fenmu=$("#x1fenmu").val();
                if(x1fenmu=="")x1fenmu=10;
                var x2fenmu=$("#x2fenmu").val();
                if(x2fenmu=="")x2fenmu=20;
                var x3fenmu=$("#x3fenmu").val();
                if(x3fenmu=="")x3fenmu=40;
                var genghuancishu = $("#genghuancishu").val();
                if (genghuancishu=="")genghuancishu=0;
                var yanzhongcishu = $("#yanzhongcishu").val();
                if (yanzhongcishu=="")yanzhongcishu=0;
                var zhongjicishu = $("#zhongjicishu").val();
                if (zhongjicishu=="")zhongjicishu=0;
                var yibancishu = $("#yibancishu").val();
                if (yibancishu=="")yibancishu=0;
                var xingcheng = $("#xingcheng").val();
                if (xingcheng=="")xingcheng=0;

                $.getJSON("${ pageContext.request.contextPath }/getScore", {
                    "x1xishu":x1xishu,
                    "x2xishu":x2xishu,
                    "x3xishu":x3xishu,
                    "x1fenmu":x1fenmu,
                    "x2fenmu":x2fenmu,
                    "x3fenmu":x3fenmu,
                    "genghuancishu": genghuancishu,
                    "yanzhongcishu": yanzhongcishu,
                    "zhongjicishu": zhongjicishu,
                    "yibancishu": yibancishu,
                    "xingcheng": xingcheng
                }, function (data) {
                    $("#s1").html("<font color='green'>更换驾驶员事件扣除:"+data.genghuanscore+"(分)</font>");
                    $("#s2").html("<font color='green'>严重报警扣除:"+data.yanzhongscore+"(分)</font>");
                    $("#s3").html("<font color='green'>中级报警扣除:"+data.zhongjiscore+"(分)</font>");
                    $("#s4").html("<font color='green'>一般报警扣除:"+data.yibanscore+"(分)</font>");
                    $("#s5").html("<font color='green'>总得分是:"+data.zongscore+"(分)</font>");


                });
            })
        })

    </script>
</head>

<body style="background: black">
<div>
    <div style="border:5px solid gray;background-color:white;position:absolute;left:100px;top:160px;width:80%;">
        <form id="tableform" method="get">
            <table border="0" width="100%" cellspacing="15">


                <tr>
                    <td>更换驾驶员事件次数</td>
                    <td><input type="text" id="genghuancishu" name="genghuancishu"  placeholder="请输入次数,默认是0">次</td>
                    <td></td>
                    <td></td>
                    <td><span id="s1"></span></td>
                </tr>
                <tr>
                    <td>严重报警次数</td>
                    <td><input type="text" id="yanzhongcishu" name="yanzhongcishu" placeholder="请输入次数,默认是0">次</td>
                    <td>X1分母变量<input type="text" id="x1fenmu" name="x1fenmu" placeholder="请输入分母,默认是10"></td>
                    <td>X1系数变量
                        <select id="x1xishu">
                            <option value ="0" >0</option>
                            <option value ="1" >1</option>
                            <option value ="2" >2</option>
                            <option value ="3">3</option>
                            <option value ="4">4</option>
                            <option value ="5">5</option>
                            <option value="6" selected>6</option>
                            <option value ="7">7</option>
                            <option value ="8">8</option>
                            <option value ="9">9</option>
                            <option value ="10">10</option>
                        </select>
                    </td>


                    <td><span id="s2"></span></td>
                </tr>
                <tr>
                    <td>中级报警次数</td>
                    <td><input type="text" id="zhongjicishu" name="zhongjicishu" placeholder="请输入次数,默认是0">次</td>
                    <td>X2分母变量<input type="text" id="x2fenmu" name="x2fenmu" placeholder="请输入分母,默认是20"></td>
                    <td>X2系数变量
                        <select id="x2xishu">
                            <option value ="0" >0</option>
                            <option value ="1" >1</option>
                            <option value ="2" >2</option>
                            <option value ="3" selected>3</option>
                            <option value ="4">4</option>
                            <option value ="5">5</option>
                            <option value="6" >6</option>
                            <option value ="7">7</option>
                            <option value ="8">8</option>
                            <option value ="9">9</option>
                            <option value ="10">10</option>
                        </select>
                    </td>

                    <td><span id="s3"></span></td>
                </tr>
                <tr>
                    <td>一般报警次数</td>
                    <td><input type="text" id="yibancishu" name="yibancishu" placeholder="请输入次数,默认是0">次</td>
                    <td>X3分母变量<input type="text" id="x3fenmu" name="x3fenmu" placeholder="请输入分母,默认是40"></td>
                    <td>X3系数变量
                        <select id="x3xishu">
                            <option value ="0">0</option>
                            <option value ="1" selected>1</option>
                            <option value ="2" >2</option>
                            <option value ="3">3</option>
                            <option value ="4">4</option>
                            <option value ="5">5</option>
                            <option value="6" >6</option>
                            <option value ="7">7</option>
                            <option value ="8">8</option>
                            <option value ="9">9</option>
                            <option value ="10">10</option>
                        </select>
                    </td>

                    <td><span id="s4"></span></td>
                </tr>
                <tr>
                    <td>总行程(KM)</td>
                    <td><input type="text" id="xingcheng" name="xingcheng" placeholder="行程小于10km视无行程">KM</td>
                </tr>
                <tr>
                    <td colspan="2"><input id="button" type="button" value="查询得分"></td>
                    <td></td>
                    <td></td>
                    <td><span id="s5"></span></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>


</html>
