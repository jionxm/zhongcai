function ddClick(me) {
    $(me).find("input").attr("checked", true);
    radionClick();
}
function radionClick(){
    if (isCheckAll()) {
        $($(".hrefBtn")[1]).hide();
        $($(".hrefBtn")[0]).show();
        window.location.href="#"+6;
    }else {
        $($(".hrefBtn")[0]).hide();
        $($(".hrefBtn")[1]).show();
        notHrefBtnClick();
    }
}
function isCheckAll() {
    return $("input:checked").length==6;
}
function notHrefBtnClick(){
    for (var i = 0; i < 6; i++) {
        if ($("input:radio[name='q"+i+"']:checked").val()==null) {
            window.location.href="#"+i;
            break;
        }
    }
}
function submit(){
    if (isCheckAll()) {
        $(".renameResumeWrap").css("height",$("body").css("height"));
        $(".renameResumeWrap").show();
    }
}
function okBtn(){
    
}
function Close() {
    $(".renameResumeWrap").hide();
}
$(document).ready(function () {
    $(".renameResumeWrap").hide();
    $(".notHrefBtn").bind("click",notHrefBtnClick);
    $(".hrefBtn").bind("click",submit);
    $(".hrefBtn").hide();
    $(".notHrefBtn").show();
});