//input输入值
var arr = [];

$(".sorting").keyup(function () {
    $(this).val($(this).val().replace(/[^1-9]/, ''));

    var arrs = $(this).parent().siblings().length;
    var index = $(this).parent().index();
    var title = $(this).val();

    if(title){
        if(arr.indexOf(title) == -1 && title <= arrs && title > 0){
            arr[index-1] = title;
            console.log(arr);
        }else {
            alert("请输入正确的排序号");
        }
    }else {
        arr[index-1] = '';
    }

}).bind("paste", function () {
    $(this).val($(this).val().replace(/[^1-9]/, ''));
})