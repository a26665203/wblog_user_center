var ipaddress="127.0.0.1";
//新建socket对象
window.socket = new WebSocket("ws://"+ipaddress+":8081/ws");
//监听netty服务器消息并打印到页面上
socket.onmessage = function(event) {
    var datas=event.data.split(",");
    console.log("服务器消息===="+datas);
    document.getElementById("chat-input").value=datas;
}
//将发送人接收人的id和要发生的消息发送出去
function send(){
    var myid = document.getElementById("myid").value;
    var friendid = document.getElementById("friendid").value;
    var chat = document.getElementById("chat-input").value;
    alert(chat);
    console.log(chat)
    var data="chat@#"+myid+"@#"+friendid+"@#"+chat
    socket.send(data)
}
//登录事件
function login(){
    var myid = document.getElementById("myid").value;
    alert(myid);
    var data="connect@#"+myid;socket.send(data);
}