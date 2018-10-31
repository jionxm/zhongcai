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
function ddClick(me) {
	console.log(7);
    $(me).find("input[type='radio']").attr("checked", true);
    totle1 = $("input[type='radio']:checked").length;
    console.log(8);
    notHrefBtnClick();
}
//多选点击事件
function dc(me) {
    var names = document.getElementsByName(qNumber);
    //标记判断是否选中一个       
    for (var i = 0; i < names.length; i++) {
        if (names[i].checked) {
            
            flag = true;
           
            break;
        } else if (!names[i].checked) {
            
            
            flag = false;
        }
    }
    notHrefBtnClick();

}
//试题完成度校验事件
function notHrefBtnClick() {
    if ((totle1 == sTotal) && flag){
        console.log(1);
        $($(".hrefBtn")[1]).hide();
        $($(".hrefBtn")[0]).show();
        return true;
    } else {
        console.log(2);
        $($(".hrefBtn")[0]).hide();
        $($(".hrefBtn")[1]).show();
    }

}
function goto() {
    console.log("first");
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
    }
}

function okBtn() {

}

function Close() {
    $(".renameResumeWrap").hide();
}
$(document).ready(function () {
    $(".renameResumeWrap").hide();
    $(".notHrefBtn").bind("click", notHrefBtnClick);
    $(".hrefBtn").hide();
    $(".notHrefBtn").show();
})