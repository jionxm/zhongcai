function ddClick(me) {
    var totle = 0;
    $(me).attr("checked", true);
    var i = $(me).parent().attr("id");
    window.location.href="#"+i;
}
$(function(){
    $('.blanks').bind('input propertychange',function(){
        var obj = $(this);
        var text_length = obj.val().length;  //获取当前长度
        if(text_length > 0){
             var width = parseInt(text_length)*14; //该12是改变前的宽度除以当前字符串的长度，算出每个字符的长度
        obj.css('width',width+'px');
        }else{
            obj.css('width',160+'px');
        }
       
    });
})
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
var titlearr1=[];
var radioNum=0;
var checkNum=0;
var totaltext=0;
$('.box').click(function(e){
	var retarr=[];
	var arr=[];
	var titlearr=[];
	radioNum = 0;
	if($(e.target).is('input')){
		$("input[type='radio']:checked").each(function(index,item){
				arr.push(item.value);
				console.log("测试"+item.value);
				var must = $(item).parent().parent().prev().prev().children("font").attr("class");
				//console.log("must" + must);
				if(must=="ismust"){
			    	radioNum+=1;
			    }
		});
		totle1=arr.length;
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
				$(item).find("input[type='checkbox']:checked").each(function(index,item){
					checkboxarr.push(item.value);			
				});
				arr.push(checkboxarr);
			})
//			flag=true;
		}
		
		 
		$('.top').find('font').each(function(index,item){
			var title=$(item).attr('name');
			titlearr.push(title);			
		});
//		titlearr.push($('textarea').val());
		
		notHrefBtnClick();
		 retarr1=retarr;
		 arr1=arr;
		 titlearr1=titlearr;
}
});

var checkBoxIds = "";
var textareaIds = "";
function changeCheckbox(obj){
	var name = $(obj).attr("name");
	var clazz = $(obj).parent().parent().prev().prev().children("font").attr("class");
	
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
	var id = $(this).prev().prev().children("font").attr("name");
	var clazz = $(this).prev().prev().children("font").attr("class");
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
//    var names = $(me).chilrdren("input[type='checkbox']:checked");
    //标记判断是否选中一个       
    console.log(names);
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

	if ((totle1) == sTotal){
//      console.log(1);
      $($(".hrefBtn")[1]).hide();
      $($(".hrefBtn")[0]).show();
      return true;
  } else {
//      console.log(2);
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

function bounced(e) {
	console.log(e);
	  $(".bounced").css("height", $("body").css("height"));
	  $(e).parent().next(".bounced").show();
	  $(e).parent().next().next(".bounced-con").show();
	  
//	  $(".renameResume").removeClass("no");
//	  $(".renameResume").addClass("show");
	 }

/*$(document).mouseup(function(e) { 
    var  pop = $('.bounced-con');  
    if(!pop.is(e.target) && pop.has(e.target).length === 0) { 
        $('.bounced').hide(); 
    }  
});*/

function submit() {
    if (notHrefBtnClick()) {
        $(".renameResumeWrap").css("height", $("body").css("height"));
        $(".renameResumeWrap").show();
        
        $('.textarea').each(function(index,item){
        	arr1.push($(item).val());
        });
        for(var i=0;i<titlearr1.length;i++){
			var title1=titlearr1[i];
			var value=arr1[i];
			if(Array.isArray(value)){
				value=value.join('&');
			}
			var obj={
				"questionId":title1,
				"result":value,
				"QRId":QRId,
				"testType": type,
				"ztqzId": ztqzId,
				"projectGroupId": projectGroupId, 
				"testId": testId
				
			};
			
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
	        		 ajaxPost(APIS.frmResultTestDetail.save, 
	        				 {
	        			 Mode:"Add",
	        			 pTable:retarr1
	        				 }, function(data) {
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
	        						 ajaxPost(APIS.frmResultTestDetail.saveCustom, 
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
	        										            window.location.href=API_PROXY + '/view/common/end.jsp';
	        										        }
	        										    }
	        										})
	        								 }
	        						 );
	        					 }
	        				 }
	        		 );
	        			
}

function Close() {
    $(".renameResumeWrap").hide();
    $(".bounced").hide();
    $(".bounced-con").hide();
    retarr1=[];
//	  $(".renameResume").removeClass("show");
//	  $(".renameResume").addClass("no");
}
$(document).ready(function () {
    $(".renameResumeWrap").hide();$(".bounced").hide();
    $(".notHrefBtn").bind("click", notHrefBtnClick);
    $(".hrefBtn").hide();
    $(".bounced-con").hide();
    $(".notHrefBtn").show();
    $(".ismust").parent().prev().prepend('<i class="rip">*</i>');
})