//全选
$(".lf_header").click(function(e) {
	if($(".lf_header>b").hasClass("active")) {
		//		移除掉全选
		$(".lf_header>b").removeClass("active");
		$("#container>.lf>ul>li b").removeClass("active");
		$(".lf_footer>.rt").css("color", "black");
		getChoosedNum();
	} else {
		//		全选
		$(".lf_header>b").addClass("active");
		$("#container>.lf>ul>li b").addClass("active");
		getChoosedNum();
		$(".lf_footer>.rt").css("color", "#03a9f4");
	}

});

//单选
$("#lf_users").on('click','li',function(){
	$(this).children("b").toggleClass("active");
	console.log($(this).children("b"));
	getChoosedNum();
	if(!$("#container>.lf>ul>li>b").hasClass("active")) $(".lf_footer>.rt").css("color", "black");

	//	判断是否取消掉全选
	if($(this).children("b").hasClass("active")) {
		$(".lf_footer>.rt").css("color", "#03a9f4");
	} else {
		$(".lf_header>b").removeClass("active");
	}
})

function getChoosedNum() {
	var num = $("#container>.lf>ul>li>b.active").length;
	$(".lf_footer>.lf").html("共选" + num + "人");
}


