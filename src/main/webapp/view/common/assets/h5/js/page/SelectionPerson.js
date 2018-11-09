function ddClick(me,i,j) {
    var totle = 0;
    $(me).attr("checked", true);
    var jum = i+j;
    window.location.href="#"+jum;
}

//单选题目完成量
var totle1 = 0;
var sTotal = 30; 
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
var totaltext=0;
//var resultarr=[];

$('.box').click(function(e){
	
	var retarr=[];
	var arr=[];
	
	if($(e.target).is('input')){
		$("input[type='radio']:checked").each(function(index,item){
			    var empId = $(item).parent().prev().attr('id');
			    var empName = $(item).parent().prev().html();
			    var questionId = $(item).parent().parent().prev().children("font").attr("id"); 		    
				var questionName = $(item).parent().parent().prev().children("font").html();
			    var questionNumber = $(item).parent().parent().prev().children("font").attr("name");
			    arr.push(questionId +"&"+item.value +"&"+ empId +"&"+ empName + "&"  + questionName+ "&" + questionNumber);

		});
		totle1=arr.length;
		console.log("arr:"+arr);
//		if($("input[type='checkbox']:checked").length>0){
//			console.log($("input[type='checkbox']:checked").length);
//			var checkboxarr=[];
//			$("input[type='checkbox']:checked").each(function(index,item){
//				checkboxarr.push(item.value);			
//			});
//			arr.push(checkboxarr);
//		}
		if($("input[type='checkbox']:checked").length>0){
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
		}
		
		 
		

		
		notHrefBtnClick();
		 retarr1=retarr;
		 arr1=arr;
		 
		 
}
});

var textNum = "";
$('textarea').change(function(e){
	var id = $(this).prev().attr("id");
	if(textNum.search(id)==-1){
		textNum = textNum+","+id;
		totaltext = totaltext + 1;
	}
	console.log("textNum="+textNum);
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
	console.log("totle1:"+totle1);
	console.log("sTotal:"+sTotal);
	console.log(flag);
	/*if(r==0){
		flag=true;
	}*/
	
    if ((totle1+totaltext) == sTotal){
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

function submit() {
    if (notHrefBtnClick()) {
        $(".renameResumeWrap").css("height", $("body").css("height"));
        $(".renameResumeWrap").show();
        
        $('textarea').each(function(index,item){        	
        	var empId = $(item).prev().attr('id');
        	var empName = $(item).prev().html();
        	var questionId = $(item).parent().prev().children("font").attr("id");
        	var questionName = $(item).parent().prev().children("font").html();
        	var questionNumber = $(item).parent().prev().children("font").attr("name");
        	         
        	arr1.push(questionId + "&" + $(item).val() + "&" + empId + "&" + empName + "&" +questionName+ "&" + questionNumber);
        	//arr1.push($(item).val());
        });
        for(var i=0;i<arr1.length;i++){
			var empSValue=[];
			var value=arr1[i];
			var mValue = [];
			var eEmp;
			var empName;
			var questionId;
			var questionName;
			var questionNumber;
			var obj ={};
			if(Array.isArray(value)){
				for(var j=0;j<value.length;j++){
					var reValue= value[j].split("&");
					if(j == 0){
						mValue = reValue[1] + "&";
					}else if( j == value.length - 1){
						mValue =mValue + reValue[1];
						eEmp = reValue[2];
						questionId = reValue[0];
						empName = reValue[3];
						questionName =reValue[4] ;
						questionNumber = reValue[5];
						
					}
					else{
						mValue =mValue + reValue[1] +"&";
					}
					
					
				}
					
				obj={
						"QRId":QRId,
						"editing":true,
						"empId" : eEmp,
						"empName":empName,
						"projectGroupId": projectGroupId,
						"projectId":projectId,
						"questionId":parseInt(questionId),
						"questionName":questionName,
						"questionNumber":questionNumber,
						"result":mValue,
						"testId": testId,
						"testType": type,
						"ztqzId": ztqzId
					};	

				
			}else{
				empSValue = arr1[i].split("&");
				obj={
					"QRId":QRId,
					"editing":true,
					"empId" : empSValue[2],
					"empName":empSValue[3],
					"projectGroupId": projectGroupId,
					"projectId":projectId,
					"questionId":parseInt(empSValue[0]),
					"questionName":empSValue[4],
					"questionNumber":empSValue[5],
					"testType": type,
					
					
					"result":empSValue[1],
					"ztqzId": ztqzId,
					 
					"testId": testId
					
					
				};
			}
			//console.log("obj" + obj);
			
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
    retarr1=[];
}
$(document).ready(function () {
    $(".renameResumeWrap").hide();
    $(".notHrefBtn").bind("click", notHrefBtnClick);
    $(".hrefBtn").hide();
    $(".notHrefBtn").show();
})