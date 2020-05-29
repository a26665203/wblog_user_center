function show(file) {
    var reader = new FileReader();	// 实例化一个FileReader对象，用于读取文件
    var img = document.getElementById('previewImg'); 	// 获取要显示图片的标签

    //读取File对象的数据
    reader.onload = function (evt) {
        img.width = "80";
        img.height = "80";
        img.src = evt.target.result;
    }
    reader.readAsDataURL(file.files[0]);
}

function sendBlog() {
    var text =  $("#content").html();
    var formData = new FormData();
    formData.append("wblogImg",$('#wblogImg')[0].files[0]);
    formData.append("wblogContent",text);
    formData.append("nickName",$("#hideUser").val());
    $.ajax({
        type : 'POST',
        datatype : 'json',
        url : './sendWblog?nickName='+$("#hideUser").val()+'&account='+$("#hideAccount").val(),
        data : formData,
        cache: false,
        processData: false,
        contentType: false,
        success : function (data) {
            alert("发布成功");
            window.location.href="./updateWblog?nickName="+$("#hideUser").val()+"&account="+$("#hideAccount").val();
        }
    })
}