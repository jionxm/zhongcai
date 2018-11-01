<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="theme-color" content="#000000">
    <title>身份确认页面</title>
    <link href="${ctx}/view/common/assets/h5/css/normalize.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/view/common/assets/h5/css/identity.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${ctx}/view/common/assets/h5/js/base/jquery-1.11.0.min.js" ></script>
</head>

<body>

	<#if size!=0>
    <header>
        <p>${data.projectName}</p>
    </header>

    <div class="container">
    	<p>指导语</p>
        <div class="detail">
            <p>
            ${data.message}
            </p>
        </div>
        <p class="notice">温馨提示：你的身份是
            <span>${data.groupName}</span>
        </p>
        <a href="${ctx}/selection?qr_code=${data.qr_code}">
            <button>开始</button>
        </a>
    </div>
	</#if>
	<#if size==0>
	<div  class="container " style="margin-top:45%">
		 
		 <p class="notice">温馨提示：
            <span>
            	对不起，该二维码已经失效或者测评结束，请联系相关人员！
            </span>
        </p>
	</#if>
    </div>
</body>

</html>
