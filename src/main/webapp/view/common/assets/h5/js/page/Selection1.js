function ddClick(me) {
    var totle = 0;
    $(me).attr("checked", true);
    var i = $(me).parent().attr("id");
    window.location.href="#"+i;
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

$('textarea').change(function(e){
	
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
	if(r==0){
		flag=true;
	}
    if ((totle1 == sTotal) && flag){
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
function goto() {
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
        	arr1.push($(item).val());
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
						"QRId":300,
						"editing":true,
						"empId" : 1,
						"empName":empName,
						"projectGroupId": 111,
						"projectId":123,
						"questionId":333,
						"questionName":questionName,
						"questionNumber":33,
						"result":mValue,
						"testId": 34,
						"testType": type,
						"ztqzId": 54
					};	

				
			}else{
				empSValue = arr1[i].split("&");
				obj={
					"QRId":300,
					"editing":true,
					"empId" : 1,
					"empName":empSValue[3],
					"projectGroupId": 111,
					"projectId":123,
					"questionId":333,
					"questionName":empSValue[4],
					"questionNumber":33,
					"testType": type,
					
					
					"result":empSValue[1],
					"ztqzId": 54,
					 
					"testId": 34
					
					
				};
			}
			console.log("obj" + obj);
			
			retarr1.push(obj);
		}
 
		console.log(retarr1);
		
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
	        					 if(data.code==904){
//	        						 alert("该二维码已经被使用并提交答案");
	        						 /*window.location.reload(-1);*/
	        					 }else{
	        						 ajaxPost(APIS.frmResultTestPerson.saveCustom, 
	        								 {
	        							 Mode:"Add",
	        							 state:0,
	        							 ctlId:QRId
	        								 }, function(result) {
//	        									 alert("提交成功");
//	        									 window.location.reload(-1);
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