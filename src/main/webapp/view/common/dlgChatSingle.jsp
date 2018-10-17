<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML >
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="no-cache">
<link
	href="${ctx}/view/common/css/plugins/easyui-1.5.2/themes/default/easyui.css"
	rel="stylesheet" media="screen" />
<link href="${ctx}/view/common/css/style.css" rel="stylesheet"
	media="screen" />
<link href="${ctx}/view/common/css/plugins/easyui-1.5.2/themes/icon.css"
	rel="stylesheet" media="screen" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/view/common/css/chat.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/view/common/css/common.css" />
<script src="${ctx}/view/common/css/plugins/easyui-1.5.2/jquery.min.js"></script>
<script
	src="${ctx}/view/common/css/plugins/easyui-1.5.2/jquery.easyui.min.js"></script>
<script
	src="${ctx}/view/common/css/plugins/easyui-1.5.2/easyui.plugins.js"></script>
<script src="https://cdn.bootcss.com/vue/2.5.17-beta.0/vue.js"></script>
<!-- 引用EasyUI的国际化文件,让它显示中文 -->
<script
	src="${ctx}/view/common/css/plugins/easyui-1.5.2/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script src="${ctx}/view/common/js/base.js"></script>
<!-- js本地存储-->
<script src="${ctx }/view/common/js/store/myStorage.js" charset="utf-8"></script>
<script src="${ctx }/view/common/js/store/json2.js" charset="utf-8"></script>
<script src="${ctx }/view/common/js/store/localDB.js" charset="utf-8"></script>
<script src="${ctx}/view/common/js/ajaxUtil.js"></script>
<script src="${ctx}/view/common/js/strophe.min.js"></script>

</head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<body>
	<script type="text/javascript">
function closes() {
    $("#Loading").fadeOut("normal", function () {
        $(this).remove();
    });
}
var pc;
function loaded() {
    if (pc) clearTimeout(pc);
    pc = setTimeout(closes, 0);
}
$(document).ready(function () {
	loaded();
	doQuery()
 });
</script>
	<div id='Loading'
		style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: #FFFFFF; text-align: center; padding-top: 10%;">
		<h1
			style="display: inline-block; border: 1px solid #95b9e7; font-size: 16px; padding: 5px;">
			<image
				src='${ctx}/view/common/css/plugins/easyui-1.5.2/themes/default/images/loading.gif' />
			<font color="#15428B" size="2">正在处理，请稍等···</font>
		</h1>
	</div>
	<div id="container">
		<div class="lf">
			<div class="lf_header">
				<b class="rt"></b> <span class="rt">全选</span>
			</div>
			<ul id="lf_users">
			
			</ul>
			<div class="lf_footer">
				<span class="lf">共选0人</span> <a class="rt" href="javascript:void(0)">确定</a>
			</div>
		</div>
		<div class="rt" id="chat">
			<div class="top">
				<div class="item" v-for="item in chatItem">
					<h1 class="time">{{item.time}}</h1>
					<div class="content">
						<div class="owner">{{item.owner}}</div>
						<div class="sentTo">{{item.sendTo}}</div>
						<p>{{item.p}}</p>
					</div>
				</div>
			</div>
			<div class="bottom">
				<textarea id="messageText" placeholder="在这里输入...."></textarea>
				<div class="btn-group rt">
					<button v-on:click="send()">发送</button>
					<button onClick="javascript:closeDialog();">取消</button>
				</div>
			</div>
		</div>
	</div>
	
</body>
<script> 
function closeDialog(){
	parent.$('#tmpSearcher').dialog('close');
}

function doQuery(){
	
	var url = '${ctx}/chatquery';
	$.ajax({
		type: 'GET',
		url: url,
		beforeSend: function(xhr) {
	        xhr.setRequestHeader("token","${token}");},
		success: function (data){
			var list=data.data;
			//console.log(data.data);
			var ul=$("#lf_users");
			 ul.empty();
			  for(var i in list){
			    ul.append('<li>'+
		  			     '<span class="lf">'+list[i].name+'</span>'+
		  			      '<b class="rt"></b>'+
		  			      '<span class="rt" id="middle">'+list[i].userName+'</span></li>');
			  }  
	}
	});
}
 	
	
var vm = new Vue({
	el: '#chat',
	data: {
		chatItem: [{
			time: getNowFormatDate(),
			owner: " ",
			sendTo: " ",
			p: ""
		}],
	},
	
	methods: {
		send() {
			var content = $(".bottom>textarea").val();
			var sendTo1 = "";
			var sendTo="";
			var message=$("#messageText").val();
			var date=new Date();
			$("#container>.lf>ul>li>b.active").each(function(index, item) {
				if(index == $("#container>.lf>ul>li>b.active").length - 1){
					sendTo1 += $(this).prev().html();
					sendTo += $(this).next().html();
				}
				else{
					sendTo1 += $(this).prev().html() + "、";
					sendTo += $(this).next().html() + ",";
				}
					
			});
		
			//判断是否选中联系人
			if(sendTo.length==0){
				$.messager.alert('提示消息','请至少选择一个发送对象','info');
			}else{
				this.chatItem.push({
					time: getNowFormatDate(),
					owner: "发送给",
					sendTo: sendTo1,
					p: content
				})
				var users=sendTo.split(',');
				//单人聊天
				if(users.length==1){
					var url="${ctx}/chatWith";
					$.ajax({
					type: 'POST',
					url: url,
					data:{"message":message,
						"userName":sendTo},
					dataType: "json",
					beforeSend: function(xhr) {
				        xhr.setRequestHeader("token","${token}");},
					success: function (data){
						console.log(data.msg);
						if(!data.msg.length==0){
							$.messager.alert('提示消息',data.msg,'info');
						}
						
					}
				});
				}else if(users.length>1){
				//发消息给多人
				var urls="${ctx}/mutiChat";
					$.ajax({
						type: 'POST',
						url: urls,
						data:{"message":message,
							"userName":sendTo},
						dataType: "json",
						beforeSend: function(xhr) {
					        xhr.setRequestHeader("token","${token}");},
						success: function (data){
							console.log(data.msg);
							if(!data.msg.length==0){
								$.messager.alert('提示消息',data.msg,'info');
							}
							
						}
					});	
				}
				$("#messageText").val('');
			}
			 
		}
	}
})
</script>
<script src="${ctx}/view/common/js/chat.js" type="text/javascript"
	charset="utf-8"></script>
</html>