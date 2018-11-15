function ddClick(me,i,j) {
    var totle = 0;
    $(me).attr("checked", true);
    var jum = i+j;
    window.location.href="#"+jum;
}

//单选题目完成量
var totle1 = 0;

//多选勾选量
var flag = false;
//单选点击事件
//function ddClick(item) {
//	
//    /*$(me).find("input[type='radio']").attr("checked", true);*/
//    totle1 = $("input[type='radio']:checked").length;
//    console.log(totle1);
////    console.log(8);
//    notHrefBtnClick();
//}

var retarr1=[];
var arr1=[];
var titlearr1 = [];
var checkboxarr1 = [];
var gaparr1 = [];
var totaltext=0;
var radioNum=0;
var checkNum=0;


//var resultarr=[];

$('.box').click(function(e){
	var titlearr=[];
	var retarr=[];
	var arr=[];
	radioNum = 0;
	if($(e.target).is('input')){
		$("input[type='radio']:checked").each(function(index,item){
			var empId = $(item).parent().prev().attr('id');
			var questionId = $(item).parent().parent().prev().prev()
					.children("font").attr("id");
			arr.push(item.value);
			titlearr.push(questionId + "&" + empId);
			    var must = $(item).parent().prev().attr("class");
			    if(must=="ismust"){
			    	radioNum+=1;
			    }

		});
		console.log("arr:"+arr);
//		if($("input[type='checkbox']:checked").length>0){
//			console.log($("input[type='checkbox']:checked").length);
//			var checkboxarr=[];
//			$("input[type='checkbox']:checked").each(function(index,item){
//				checkboxarr.push(item.value);			
//			});
//			arr.push(checkboxarr);
//		}
		/*if($("input[type='checkbox']:checked").length>0){
			$('.answers dl .box').each(function(index,item){
				var checkboxarr=[];
				var result=[];
				$(item).find("input[type='checkbox']:checked").each(function(index,item){
					var empId = $(item).parent().prev().attr('id');
					var empName = $(item).parent().prev().html();
					var questionId = $(item).parent().parent().prev().children("font").attr("id");
					var questionName = $(item).parent().parent().prev().children("font").html();
				    var questionNumber = $(item).parent().parent().prev().children("font").attr("name");
					//result.push(item.value);
					checkboxarr.push(questionId + "&" + item.value + "&" + empId + "&" + empName + "&" +questionName+ "&" + questionNumber);	
				});
				
				
				console.log("checkboxarr" + checkboxarr);
				arr.push(checkboxarr);
			})
//			flag=true;
		}*/
		
		 
		

		
		notHrefBtnClick();
		 retarr1=retarr;
		 arr1=arr;
		 titlearr1 = titlearr;
		 
}
});
var checkBoxIds = "";
var textareaIds = "";
function changeCheckbox(obj){
	/*var must = $(item).prev().attr("class");
	if(must=="must"){
		checkNum+=1;
	}*/
	var name = $(obj).attr("name");
	var clazz = $(obj).parent().prev().attr("class");
	if(checkBoxIds.search(name)==-1&&clazz=="ismust"){
		
		checkBoxIds = checkBoxIds+","+name;
		checkNum = checkNum + 1;
	}else if(checkBoxIds.search(name)!=-1){
		var f=false;
		console.log("选择的长度"+$(":checkbox[name='"+name+"']:checked").length);
		if($(":checkbox[name='"+name+"']:checked").length>0){
			f=true;
		}
		if(!f){
			checkBoxIds = checkBoxIds.replace(","+name, "");
			checkNum = checkNum - 1;
		}
		
	}
	console.log("checkBoxIds="+checkBoxIds);
	notHrefBtnClick();
}


$('textarea').change(function(e){
	var id = $(this).prev().attr("id");
	var clazz = $(this).prev().attr("class");
	if(textareaIds.search(id)==-1&&clazz=="ismust"&&$(this).val()!=""){
		textareaIds = textareaIds+","+id;
		totaltext = totaltext + 1;
	}else if(textareaIds.search(id)!=-1&&$(this).val()==""){
		textareaIds = textareaIds.replace(","+id, "");
		totaltext = totaltext - 1;
	}
	console.log("textareaIds="+textareaIds);
	notHrefBtnClick();
})
//多选点击事件
function dc(me) {
	
    var names = $(me).find("input[type='checkbox']:checked");
    //标记判断是否选中一个       
    if(names.length>0){
    	for (var i = 0; i < names.length; i++) {
            if (names[i].checked) {
                
                flag = true;
         
                break;
            } else if (!names[i].checked) {
                
                
                flag = false;
            }
        }
    }else{
    	flag=true;
    }
    
    notHrefBtnClick();

}

//试题完成度校验事件
function notHrefBtnClick() {
	totle1 = totaltext + radioNum + checkNum;     
	console.log("radioNum:"+radioNum);
	console.log("checkNum:"+checkNum);
	console.log("totaltext:"+totaltext);
	console.log("totle1:"+totle1);
	console.log("sTotal:"+sTotal);
	console.log(flag);
	/*if(r==0){
		flag=true;
	}*/
    if ((totle1) == sTotal){
//        console.log(1);
        $($(".hrefBtn")[1]).hide();
        $($(".hrefBtn")[0]).show();
        return true;
    } else {
//        console.log(2);
        $($(".hrefBtn")[0]).hide();
        $($(".hrefBtn")[1]).show();
    }

}
function gotoask() {
//    console.log("first");
    for (var i = 1; i < 3; i++) {
        if ($("input:radio[name='q" + i + "']:checked").val() == null) {
            console.log("second");
            window.location.href = "#" + i;
            break;
        }
    }
}

function bounced(e) {
	console.log(e);
	  $(".bounced").css("height", $("body").css("height"));
	  $(e).parent().next(".bounced").show();
}

function submit() {
    if (notHrefBtnClick()) {
        $(".renameResumeWrap").css("height", $("body").css("height"));
        $(".renameResumeWrap").show();
        
        $('.answers dl .box').each(function(index,item){
			var checkboxarr = [];
			$(item).find("input[type='checkbox']:checked").each(
				function(index, item) {
					checkboxarr.push(item.value);
				});
		checkboxarr1.push(checkboxarr);
		});
		console.log("checkboxarr1" + checkboxarr1);
		
		$('.answers dl .box').each(function(index,item){
			var empId = $(item).prev().attr('id');
			var questionId = $(item).parent()
			.prev().prev().children("font").attr("id");
			titlearr1.push(questionId + "&" + empId);
		});
		console.log("titlearr:" + titlearr1);
		
		$('textarea').each(
				function(index, item) {
					var empId = $(item).prev().attr('id');
					var questionId = $(item).parent().prev().prev().children("font")
							.attr("id");
					console.log("empId" + empId + "questionId" + questionId);
					gaparr1.push($(item).val());
					titlearr1.push(questionId + "&" + empId);

				});
		console.log("titlearr:" + titlearr1);
        
        
		var obj = {};
		var i;
		for (i = 0; i < arr1.length; i++) {
			var value = arr1[i];
			var title1 = titlearr1[i].split("&");
			obj = {
				"questionId" : title1[0],
				"empId" : title1[1],
				"result" : value,
				"QRId" : QRId,
				"testType" : type,
				"ztqzId" : ztqzId,
				"projectGroupId" : projectGroupId,
				"testId" : testId
			};
			retarr1.push(obj);
		}
		var j;
		for (j = 0; j < checkboxarr1.length; j++) {
			var value = checkboxarr1[j];
			var title1 = titlearr1[j+i].split("&");
			
			value = value.join("&");
			obj = {
				"questionId" : title1[0],
				"empId" : title1[1],
				"result" : value,
				"QRId" : QRId,
				"testType" : type,
				"ztqzId" : ztqzId,
				"projectGroupId" : projectGroupId,
				"testId" : testId
			};
			retarr1.push(obj);
		}
		
		for (var k = 0; k < gaparr1.length; k++) {
			var value = gaparr1[k];
			var title1 = titlearr1[k+i+j];console.log(title1);
			obj = {
				"questionId" : title1[0],
				"empId" : title1[1],
				"result" : value,
				"QRId" : QRId,
				"testType" : type,
				"ztqzId" : ztqzId,
				"projectGroupId" : projectGroupId,
				"testId" : testId
			};
			retarr1.push(obj);
		}
 
		//console.log(retarr1);
	}
}


function okBtn() {
	/*debugger;
	$.ajax({
		url:"selection/question",
		type:"post",
		dataType:"json",
		data:JSON.stringify(retarr1),
		success:function(result){
			console.log(result);
		}
		
	});*/
	/*ajaxPost(APIS.frmResultTestDetail.queryById, 
	         {
				eq_id:QRId
	         }, function(queryResult) {
	        	 debugger;
	        	 console.log("111"+queryResult);
	        	 if(queryResult.data.ctlState==1){*/
	        		 ajaxPost(APIS.frmResultTestPerson.save, 
	        				 {
	        			 Mode:"Add",
	        			 pTable:retarr1
	        				 }, function(data) {
	        					 console.log(data);
	        					 if(data.code==904){
	        						 Close();
	        						 if(M.dialog2){
										    return M.dialog2.show();
										}
										M.dialog2 = jqueryAlert({
										    'content' : '该二维码已经被使用并已提交答案',
										    'modal'   : true,
										    'buttons' :{
										        '确定' : function(){
										            M.dialog2.close();
										            window.location.reload(-1);
										        }
										    }
										})
	        					 }else{
	        						 ajaxPost(APIS.frmResultTestPerson.saveCustom, 
	        								 {
	        							 Mode:"Add",
	        							 state:0,
	        							 ctlId:QRId
	        								 }, function(result) {
	        									 Close();
	        									 if(M.dialog2){
	        										    return M.dialog2.show();
	        										}
	        										M.dialog2 = jqueryAlert({
	        										    'content' : '提交成功',
	        										    'modal'   : true,
	        										    'buttons' :{
	        										        '确定' : function(){
	        										            M.dialog2.close();
	        										            window.location.reload(-1);
	        										        }
	        										    }
	        										})
	        								 }
	        						 );
	        						 
	        					 }
	        				 }
	        		 );
	        			
	        		/*}else{
	        			alert("该二维码已被使用并提交了答案");
	        			window.location.reload(-1);
	        		}
	          }
	        );*/
	
}

function Close() {
    $(".renameResumeWrap").hide();
    $(".bounced").hide();
    retarr1=[];
}
$(document).ready(function () {
    $(".renameResumeWrap").hide();
    $(".bounced").hide();
    $(".notHrefBtn").bind("click", notHrefBtnClick);
    $(".hrefBtn").hide();
    $(".notHrefBtn").show();
    $(".ismust").parent().prev().prev().prepend('<span class="rip">*</span>');
})