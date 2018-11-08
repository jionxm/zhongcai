<html lang="en">

<head>
<#assign ctx = request.contextPath /> 
<#include "h5/base.ftl">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="theme-color" content="#000000">
<title>任务监控列表</title>
<style type="text/css">
.list{
	float:left;
	text-align:center;
	width:100%;
}
.job{
   font-size:30px;
   font-weight:bolder;
}
</style>
</head>
<body>
<p class="list" >
    <a class="job">统计列表</a>
<button class="btn-icundefine" style="width:100px;height:50px;float:right;" onclick="javascript:history.go(-2);">返回</button>
<button class="btn-icquery" style="width:100px;height:50px;float:right;" onclick="query()">查询</button>
</p>
	<table border="1" width="50%" align="center" >
		<tr id="domain">
			<th>主体名称</th>
			<#list question as q>
			<th>${q.question }</th>
			</#list>
		</tr>
		<#list result as r>
		<tr id="domain">
			
			<th>${r.name }</th>
			<#list question as q>
			<th>${r['${q.question}'] }</th>
			</#list>
		</tr>
		</#list>
	</table>

</body>
 <script>
</script>
</html>