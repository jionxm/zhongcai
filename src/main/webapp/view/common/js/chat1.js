
// XMPP连接  
var connection = null;  
  
// 当前状态是否连接  
var connected = false;  
  

// 连接状态改变的事件  
function onConnect(status) {  
    console.log(status)  
    if (status == Strophe.Status.CONNFAIL) {  
    	console.log("连接失败！");  
    } else if (status == Strophe.Status.AUTHFAIL) {  
    	console.log("登录失败！");  
    } else if (status == Strophe.Status.DISCONNECTED) {  
    	console.log("连接断开！");  
        connected = false;  
    } else if (status == Strophe.Status.CONNECTED) {  
    	 console.log("连接成功，可以开始聊天了！"); 
    	
        connected = true;  
        // 当接收到<message>节，调用onMessage回调函数  
        connection.addHandler(onMessage, null, 'message', null, null, null);
        // 首先要发送一个<presence>给服务器（initial presence）  
        connection.send($pres().tree()); 
    }  
    return true;
}  
  
// 接收到<message>  
function onMessage(msg) {
	// 解析出<message>的from、type属性，以及body子元素  
    var from = msg.getAttribute('from');  
    var type = msg.getAttribute('type');  
    var elems = msg.getElementsByTagName('body');  
    console.log("监听中");
    if (type == "chat" && elems.length > 0) {  
        var body = elems[0];
        var fromName=from.split("@")[0];
        console.log("fromName"+fromName);
        console.log(Strophe.getText(body));
        //将收到的信息以弹框的方式显示出来
        $.messager.show({
        	title:fromName+'发来的消息',
        	width:"450",
        	height:"300",
        	msg:Strophe.getText(body),
        	//timeout:4000,
        	showType:'slide',
        });
    }  
    return true;
}  
  	//登录
	function loginOpenfire(userName,passWord,server,doMain){
	 if(!connected) {  
		 console.log(userName+"  "+passWord);
         connection = new Strophe.Connection('http://'+server+':7070/http-bind');  
         connection.connect(userName+doMain,passWord, onConnect);
         //connection.addHandler(onReconnect,null,'status',null,null,null);
         
         
         /*var rid=connection.rid;
         var sid=connection.sid;
         var jid=connection.jid;
         console.log("rid:"+rid+",sid:"+sid+",jid:"+jid);*/
     }  
  
}
	//接收离线消息
	/*function showOffMessage(){
		var url="${ctx}/getOffMessages";
		$.ajax({
			type: 'get',
			url: url,
			dataType: "json",
			beforeSend: function(xhr) {
		        xhr.setRequestHeader("token","${token}");},
			success: function (data){
				console.log(data.data);
				if(!data.msg.length==0){
					$.messager.alert('提示消息',data.msg,'warning');
				}
				

			}
		});
		
	}*/
