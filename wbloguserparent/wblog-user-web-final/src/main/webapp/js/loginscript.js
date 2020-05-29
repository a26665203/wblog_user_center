document.querySelector('.img__btn').addEventListener('click', function() {
    document.querySelector('.content').classList.toggle('s--signup')
})
function loginsubmit () {
    $.ajax({
        type : 'POST',
        datatype : 'text',
        url : './userLogin',
        data : $("#userLogin").serialize(),
        contentType : "application/x-www-form-urlencoded; charset=utf-8",
        success : function (data) {
            //将对象转换成json
            if(data.code == 200){
                alert("登陆成功");
                window.location.href="./toIndex?nickName="+data.result+"&account="+$("#account").val();
            }else{
                alert("账号不存在或密码不正确，请重新输入")
            }
        }
    })
};

function registe() {
        $.ajax({
            type : 'POST',
            dataType : 'text',
            url : './register',
            data : $("#register").serialize(),
            contentType : "application/x-www-form-urlencoded; charset=utf-8",
            success : function (data) {
                //将对象转换成json
                var jsonobj=eval("("+data+")")
                if(jsonobj.code == 400){
                   alert(jsonobj.desc);
                }else if(jsonobj.code == 200){
                    alert("注册成功");
                }
                document.getElementById("userAccount").value = "";
                document.getElementById("nickName").value = "";
                document.getElementById("email").value = "";
                document.getElementById("bizCode").value = "";
                document.getElementById("userPassword").value = "";
            }
        })

};
function sendBizCode() {
    var eamil = $("#email").val();
    $.get("./bizcode",{emailAccount : eamil},function (result) {
        alert("发送成功，请到邮箱查收")
    })
}

function getRootPath() {//获得根目录
    var strFullPath = window.document.location.href;
    var strPath = window.document.location.pathname;
    var pos = strFullPath.indexOf(strPath);
    var prePath = strFullPath.substring(0, pos);
    var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    return (prePath + postPath);
}
