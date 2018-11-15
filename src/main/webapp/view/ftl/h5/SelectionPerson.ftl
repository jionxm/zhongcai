<html lang="en">

<head>
	<#assign ctx=request.contextPath>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="theme-color" content="#000000">
    <title>测评页面</title>
    <link href="${ctx}/view/common/assets/h5/css/normalize.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/view/common/assets/h5/css/Selection.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/view/common/assets/h5/css/alert.css" rel="stylesheet" type="text/css">
    <#include "h5/base.ftl">
</head>

<body style="background-color: #fff;">
    <header class="title">
        <a class="r_return" onclick="popPage()"></a>
        <#list test as t>
        ${t.title }
        </#list>
    </header>
    
    <div class="container">
		<#list test as t>
        <input type="hidden" id="totalQuestion" value="${t.total}">
        </#list>
        <div class="answer">
            <#assign y=0 />
            <#assign x=0 />
            <#list qList as q>
            <#if q.chooseType == "single"> 
            <#assign x=x+1 />
				<#list total as t>
				<p>${x}/${t.total }</p>
				</#list> 
            <p class="top"><b>#${q.dimension}</b><font id="${q.questionId}" name="${q.questionNumber}">${q.questionName}</font></p>
            <dl>
            	<#assign y=0 />
            	<#list empList as e>
            	<#if e.questionId == q.questionId> 
            	<#assign y=y+1 />
                <dt id="${e.empId}" class="${q.must}" >${e.empName}</dt>
                <dd  class="box" id="${x}${y}">
                    <#list cList as c>
                	<#if q.questionId == c.questionId && c.chooseType == "single"> 
                    <input type="radio" name="q${x}${y}" id="radio0${c_index}${y}" value="${c.valueName}" onclick="ddClick(this,'${x}','${y}')">
                    <label for="radio0${c_index }${y}">&nbsp;${c.valueName}</label>
                    </#if> 
                	</#list>
                </dd>
                </#if> 
                </#list>  
            </dl>
            </#if> 
            </#list>
        </div>
        <#assign sTotal=y />
        <p id="i" hidden>${sTotal }</p>
		
            <div class="answer answers">
                    <#assign r=0 />
                    <#list qList as q>
                    <#if q.chooseType == "multi">
                    <#assign x=x+1 />
		            <#list total as t>
		            <p>${x}/${t.total }</p>
		            </#list>
		            <p id="i${r }" hidden>d${x}</p>
		            <#assign r=r+1 />
                    <p class="top"><b>#${q.dimension}</b><font id="${q.questionId}" name="${q.questionNumber}">${q.questionName}</font></p>
                    <dl onclick="dc(this)"> 
                    	<#assign y=0 />
            			<#list empList as e>
            			<#if e.questionId == q.questionId>
            			<#assign y=y+1 />
                		<dt id="${e.empId}" class="${q.must}" >${e.empName}</dt>
                		<dd  class="box">
                		<#list cList as c>
						<#if q.questionId == c.questionId && c.chooseType == "multi">
							<input  type="checkbox" name="d${x}${y}" id="checkbox0${c_index }${y}" value="${c.valueName}" onchange="changeCheckbox(this)">
							<label for="checkbox0${c_index }${y}">&nbsp;${c.valueName}</label>	
						</#if>
						</#list>
						</dd>
						</#if>
						</#list>                         
                    </dl>
					</#if>
					</#list>
            </div>
			
            <div class="answer answers">
                    <#list qList as q>
                    <#if q.chooseType == "completion">
                    <#assign x=x+1 />
		            <#list total as t>
		            <p>${x}/${t.total }</p>
		            </#list>
                    <p class="top"><b>#${q.dimension}</b><font id="${q.questionId}" name="${q.questionNumber}">${q.questionName}</font></p>
					<dl>
						<#assign y=0 />
						<#list empList as e>
						<#if e.questionId == q.questionId>
						<#assign y=y+1 />
						<dt id="${e.empId}" class="${q.must}">${e.empName}</dt>
						<textarea class="usertext" placeholder="请填写您的意见" ></textarea>
						</#if>
						</#list>  
					</dl>
                    </#if>
                    </#list>
            </div>

        <div class="renameResumeWrap">
            <div class="renameResume">
                <h4>确认交卷</h4>
                <p>您已完成所有题目，是否交卷？</p>
                <ul>
                    <li class="renameResumeClose" onclick="Close()">取消</li>
                    <li onclick="okBtn()">确认交卷</li>
                </ul>
            </div>
        </div>
    </div>
    <footer>
        <span class="hrefBtn"  onclick="submit()">提交试卷</span>
        <span class="hrefBtn notHrefBtn"  onclick="gotoask()"><p>您有未完成的题目</p></span>
    </footer>

    <script type="text/javascript">
     function popPage() {
            window.history.go(-1);
        }
     var sTotal = $(".ismust").length; 
     var r = $("#sum").html();console.log(r);
     var radioNum=${sTotal };
     console.log(radioNum);
     <#list test as t>
     var QRId = ${t.QRId}
     var projectId = ${t.projectId};
     var projectGroupId = ${t.projectGroupId}
     var ztqzId = ${t.ztqzId}
     var testId = ${t.testId}
     var type = "${t.type}"
     var M={};
     </#list>
     var empArr = [];
     
     
     
     /* for(var j=0;j<r;j++){
     	var qNumber = $("#i"+j).html();
     } */
    </script>
    <script type="text/javascript" src="${ctx}/view/common/assets/h5/js/page/SelectionPerson.js"></script>
    <script type="text/javascript" src="${ctx}/view/common/assets/h5/js/page/alert.min.js"></script>
</body>

</html>
