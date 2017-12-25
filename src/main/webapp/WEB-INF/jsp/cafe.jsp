<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>네이버카페</title>
    <link rel="stylesheet" type="text/css" href="/static/css/index.css" />
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <script src="/static/se2/js/HuskyEZCreator.js" charset="utf-8"></script>
</head>
<body>
<div id="index">네이버카페</div>
<br/><br/>
<div>
    <button style="width:100%;background:black;color:white;" id="save">글쓰기(마지막 글쓰기 내용이 쿠키에 저장되어 있음)</button>
</div>
<div  id="failure" style="display:none;background:white;color:red;">
    failure : <span id="failureText"></span>
</div>
<br/><br/>
<div style="board:solid 1px black">
    <div>카페목록(clubid/menuid/카페이름,clubid/menuid/카페이름)<a href="javascript:explain()">설명링크</a></div>
    <textarea style="width:100%;height:100px" id="naverCafeList"></textarea>
</div>
<br/><br/>
<div style="board:solid 1px black">
    <div>제목</div>
    <input style="width:100%;height:50px" id="subject" type="text" />
</div>
<br/><br/>
<div style="board:solid 1px black">
    <div>내용</div>
    <div>
        <textarea style="width:100%;" name="ir1" id="ir1" rows="10" cols="100"></textarea>
    </div>
</div>

<script src="/static/js/index.js"></script>
<script type="text/javascript">
    var oEditors = [];
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors,
        elPlaceHolder: "ir1",
        sSkinURI: "/static/se2/SmartEditor2Skin.html",
        fOnAppLoad : function(){
            oEditors.getById["ir1"].exec("PASTE_HTML", [localStorage.content]);
        },
        fCreator: "createSEditor2"
    });
    $("#naverCafeList").val(localStorage.naverCafeList);
    $("#subject").val(localStorage.subject);


    $("#save").click(function() {
        var naverCafeList = [];
        $.each($("#naverCafeList").val().split(","), function( index, value ) {
            var naverCafeArray = value.split("/");
            if(naverCafeArray.length >= 2 && $.isNumeric(naverCafeArray[0]) && $.isNumeric(naverCafeArray[1])){
                naverCafeList.push({clubid:naverCafeArray[0], menuid:naverCafeArray[1]});
            }
        });
        var subject = $("#subject").val();
        var content = oEditors.getById["ir1"].getContents();

        if (localStorage) {
            localStorage.naverCafeList = $("#naverCafeList").val();
            localStorage.subject = subject;
            localStorage.content = content;
        }

        $.ajax({
            url : "/save",
            type : "post",
            async : false,
            data : JSON.stringify({subject:subject, content:content, naverCafeList:naverCafeList}),
            contentType: 'application/json',
            dataType:"json"
        }).done(function(response){
            console.log(response);
            if(response.failure.length>0) {
                $("#failureText").text(response.failure.join(" , "));
                $("#failure").css("display","block");
            } else {
                $("#failureText").text("");
                $("#failure").css("display","none");
            }
        });
    });

    function explain() {
        window.open("/explain");
    }
//    27823088/1,123/4
</script>
</body>
</html>
