// function ddClick(me) {
//     var totle = 0;
//     $(me).find("input").attr("checked", true);
//     radionClick();
//     notHrefBtnClick();
//     isCheckAll();
//     console.log(totle+'first');
// }
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

$('.box').click(function(e){
	var retarr=[];
	var arr=[];
	var titlearr=[];
	if($(e.target).is('input')){
		$("input[type='radio']:checked").each(function(index,item){
				arr.push(item.value);				
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
        for(var i=0;i<titlearr1.length;i++){
			var title1=titlearr1[i];
			var value=arr1[i];
			if(Array.isArray(value)){
				value=value.join('&');
			}
			var obj={
				"questionNumber":title1,
				"result":value,
				"QRId":QRId,
				"testType": testType,
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
	ajaxPost(APIS.frmResultTestDetail.save, 
	         {
				Mode:"Add",
				pTable:retarr1
	         }, function(data) {
	        	 console.log(data);
	          }
	        );
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