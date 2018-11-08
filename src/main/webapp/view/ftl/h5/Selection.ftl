<html lang="en">

<head>
	<#assign ctx=request.contextPath>
    <#include "h5/base.ftl">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="theme-color" content="#000000">
    <title>测评页面</title>
    <link href="${ctx}/view/common/assets/h5/css/normalize.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/view/common/assets/h5/css/Selection.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/view/common/assets/h5/css/alert.css" rel="stylesheet" type="text/css">
</head>

<body style="background-color: #fff;">
    <header class="title">
        <a class="r_return" onclick="popPage()"></a>
        <#list test as t>
        ${t.title }
        </#list>
    </header>
    
    <div class="container">
        <div class="answer">
        	<#assign x=0 />
            <#list qList as q>
            <#if q.chooseType == "single">
            <#assign x=x+1 />
            <#list total as t>
            <p>${x}/${t.total }</p>
            </#list>
            <p class="top"><b>#${q.dimension}</b><font name="${q.questionId}">${q.questionName}</font></p>
            <dl>
                <!-- <dt id="0">张大大</dt> -->
                
                <dd  class="box" id="${x}">
                    <#list cList as c>
                	<#if q.questionId == c.questionId && c.chooseType == "single">
                    <input  type="radio" name="q${x}" id="radio0${c_index }" value="${c.score}" onclick="ddClick(this)">
                    <label for="radio0${c_index }">&nbsp;${c.valueName}</label>
                    </#if>
                	</#list>

                    <!-- <input  type="radio" name="q0" id="radio01">
                    <label for="radio01">&nbsp;良</label>

                    <input  type="radio" name="q0" id="radio02">
                    <label for="radio02">&nbsp;中</label>
                    
                    <input  type="radio" name="q0" id="radio03">
                    <label for="radio03">&nbsp;差</label> -->
                </dd>
                
                <!-- <hr> -->
                <!-- <dt id="1">杨大伟</dt>
                <dd class="box"onclick="ddClick(this)">
                    <input   type="radio" name="q1"id="radio10">
                    <label for="radio10" >&nbsp;优</label>
                
                    <input  type="radio" name="q1" id="radio11">
                    <label for="radio11">&nbsp;良</label>
               
                    <input  type="radio" name="q1" id="radio12">
                    <label for="radio12">&nbsp;中</label>
               
                    <input  type="radio" name="q1" id="radio13">
                    <label for="radio13">&nbsp;差</label>
                </dd> -->
            </dl>
            </#if>
            </#list>
        </div>
        <#assign sTotal=x />
        <p id="i" hidden>${sTotal }</p>
        <!-- <div class="answer" id="2 3">
                <p>2/12</p>
                <p class="top"><b>#忠诚企业</b>重操守，有大局意识，服从安排，恪尽职守，勤勉敬业，维护企业整体利益。</p>
                <dl>
                    <dt id="2">张大大</dt>
                    <dd onclick="ddClick(this)" class="box">
                        
                        <input  type="radio" name="q2"id="radio20">
                        <label for="radio20">&nbsp;优</label>
                      
                        <input  type="radio" name="q2"id="radio21">
                        <label for="radio21">&nbsp;良</label>
    
                        <input  type="radio" name="q2"id="radio22">
                        <label for="radio22">&nbsp;中</label>
                        
                        <input  type="radio" name="q2"id="radio23">
                        <label for="radio23">&nbsp;差</label>
                    </dd>
                    <hr>
                    <dt id="3">杨大伟</dt>
                    <dd onclick="ddClick(this)" class="box">
                        <input  type="radio" name="q3"id="radio30">
                        <label for="radio30">&nbsp;优</label>
                    
                        <input  type="radio" name="q3"id="radio31">
                        <label for="radio31">&nbsp;良</label>
                   
                        <input  type="radio" name="q3"id="radio32">
                        <label for="radio32">&nbsp;中</label>
                   
                        <input  type="radio" name="q3"id="radio33">
                        <label for="radio33">&nbsp;差</label>
                    </dd>
                </dl>
            </div> -->
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
                    <p class="top"><b>#${q.dimension}</b><font name="${q.questionId}" >${q.questionName}（可多选）</font></p>
                    <dl onclick="dc(this)">
                        <dd class="box">
		                    <#list cList as c>
		                	<#if q.questionId == c.questionId && c.chooseType == "multi">
                            <input  type="checkbox" name="d${x}" id="checkbox${c_index }" value="${c.score}">
                            <label for="checkbox${c_index }">&nbsp;${c.valueName}</label>
		                    </#if>
		                	</#list>
                        </dd>
                        <!-- <dd onclick="dClick(this)" class="box">
                                <input  type="checkbox" name="d1"id="checkbox1">
                                <label for="checkbox1">&nbsp;执行制度规定度规定度规定的资格、条件和程序不严格</label>
                        </dd>
                        <dd onclick="dClick(this)" class="box">
                                <input  type="checkbox" name="d2"id="checkbox2">
                                <label for="checkbox2">&nbsp;任人唯亲</label>
                        </dd>
                        <dd onclick="dClick(this)" class="box">
                                <input  type="checkbox" name="d3"id="checkbox3">
                                <label for="checkbox3">&nbsp;买官卖官</label>
                        </dd>
                        <dd onclick="dClick(this)" class="box">
                                <input  type="checkbox" name="d4"id="checkbox4">
                                <label for="checkbox4">&nbsp;拉票贿选</label>
                        </dd>
                        <dd onclick="dClick(this)" class="box">
                                <input  type="checkbox" name="d5"id="checkbox5">
                                <label for="checkbox5">&nbsp;跑官要官</label>
                        </dd>
                        <dd onclick="dClick(this)" class="box">
                                <input  type="checkbox" name="d6"id="checkbox6">
                                <label for="checkbox6">&nbsp;领导</label>
                        </dd> -->
                        <!-- <dd onclick="dClick(this)" class="box">
                                <input  type="checkbox" name="d7"id="checkbox7">
                                <label for="checkbox7">
                                    &nbsp;其他
                                    <input type="text" name="usered" class="usered"placeholder="如选此项，请填写具体内容">
                                </label>
                        </dd> -->
                    </dl>
                    </#if>
                    </#list>
                    <p id="sum" hidden>${r}</p>
            </div>
            <div class="answer answers">
                    <#list qList as q>
                    <#if q.chooseType == "completion">
                    <#assign x=x+1 />
		            <#list total as t>
		            <p>${x}/${t.total }</p>
		            </#list>
                    <p class="top"><b>#${q.dimension}</b><font name="${q.questionId}">${q.questionName}</font></p>
                    <#if q.textLength gt 10>
                    <textarea class="usertext textarea" placeholder="请填写您的意见" ></textarea>
                    <#else>
                    <input type="text" class="blanks textarea">
                    </#if>
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
        <span class="hrefBtn notHrefBtn"  onclick="goto()"><p>您有未完成的题目</p></span>
    </footer>

    <script type="text/javascript">
     function popPage() {
            window.history.go(-1);
        }
     var sTotal = $("#i").html(); 
     var r = $("#sum").html();console.log(r);
     var radioNum=${sTotal };
     console.log(radioNum);
     <#list test as t>
     var QRId = ${t.QRId}
     var projectGroupId = ${t.projectGroupId}
     var ztqzId = ${t.ztqzId}
     var testId = ${t.testId}
     var type = "${t.type}"
     var M={};
     </#list>
     
     
     /* for(var j=0;j<r;j++){
     	var qNumber = $("#i"+j).html();
     } */
    </script>
    <script type="text/javascript" src="${ctx}/view/common/assets/h5/js/page/Selection.js"></script>
    <script type="text/javascript" src="${ctx}/view/common/assets/h5/js/page/alert.min.js"></script>
</body>

</html>
