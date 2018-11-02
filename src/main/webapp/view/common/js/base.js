/**
 * base on easyUI 
 */
//用于客户端function根据服务端解析时定义的functionId获取指定ui下对应的token

var getFnName = function(callee){
    var _callee = callee.toString().replace(/[\s\?]*/g,""),
    comb = _callee.length >= 50 ? 50 :_callee.length;
    _callee = _callee.substring(0,comb);
    var name = _callee.match(/^function([^\(]+?)\(/);
    if(name && name[1]){
      return name[1];
    }
    var caller = callee.caller,
    _caller = caller.toString().replace(/[\s\?]*/g,"");
    var last = _caller.indexOf(_callee),
    str = _caller.substring(last-30,last);
    name = str.match(/var([^\=]+?)\=/);
    if(name && name[1]){
      return name[1];
    }
    return "anonymous"
  };

  
function getStorage(key){
	if(parent.gloableStorage){
		return parent.gloableStorage[key];
	} else if(parent.parent.gloableStorage){
		return parent.parent.gloableStorage[key];
	}else if(parent.parent.parent.gloableStorage){
		return parent.parent.parent.gloableStorage[key];
	} else{
		return null;
	}
}
function delStorage(key){
	if(parent.gloableStorage){
		delete parent.gloableStorage[key];
	} else if(parent.parent.gloableStorage){
		delete parent.parent.gloableStorage[key];
	}else if(parent.parent.parent.gloableStorage){
		delete parent.parent.parent.gloableStorage[key];
	} 
}
function setStorage(key, val){
	if(parent.gloableStorage){
		parent.gloableStorage[key]=val;
	}else if(parent.parent.gloableStorage){
		parent.parent.gloableStorage[key]=val;
	}else if(parent.parent.parent.gloableStorage){
		parent.parent.parent.gloableStorage[key]=val;
	}
}


function getUiStorage(key){
	if(uiStorage){
		return uiStorage[key];
	} else if(parent.uiStorage){
		return parent.uiStorage[key];
	}else if(parent.uiStorage){
		return parent.uiStorage[key];
	} else{
		return null;
	}
}
function setUiStorage(key, val){
	if(uiStorage){
		uiStorage[key]=val;
	}else if(parent.uiStorage){
		parent.uiStorage[key]=val;
	}else if(parent.uiStorage){
		parent.parent.uiStorage[key]=val;
	}
}
/**
 * 分组存放数据
 * @param group
 * @param key
 * @returns
 */
function getGroupStorage(group,key){
	if(parent.gloableStorage[group]==undefined){
		return "";
	}else{
		return eval("parent.gloableStorage." + group + "." + key);	
	}
}
		//easyui common formater
		var Common = {

			    //EasyUI用DataGrid用日期格式化
			    TimeFormatter: function (value, rec, index) {
			        if (value == undefined) {
			            return "";
			        }
			        /*json格式时间转js时间格式*/
			        value = value.substr(1, value.length - 2);
			        var obj = eval('(' + "{Date: new " + value + "}" + ')');
			        var dateValue = obj["Date"];
			        if (dateValue.getFullYear() < 1900) {
			            return "";
			        }
			        var val = dateValue.format("yyyy-mm-dd HH:MM");
			        return val.substr(11, 5);
			    },
			    DateTimeFormatter: function (value, rec, index) {
			        if (value == undefined) {
			            return "";
			        }
			        /*json格式时间转js时间格式*/
			        value = value.substr(1, value.length - 2);
			        var obj = eval('(' + "{Date: new " + value + "}" + ')');
			        var dateValue = obj["Date"];
			        if (dateValue.getFullYear() < 1900) {
			            return "";
			        }

			        return dateValue.format("yyyy-mm-dd HH:MM");
			    },

			    //EasyUI用DataGrid用日期格式化
			    DateFormatter: function (value, rec, index) {
			        if (value == undefined || value.length == 0) {
			            return "";
			        }
			       
			        if (value.length > 10){
			        	return value.substr(0, 10);
			        }else{
			        	return value;
			        }
			        
			    },
			    TitleFormatter : function (value, rec, index) {
			        if (value.length > 10) value = value.substr(0, 8) + "...";
			        return value;
			    },
			    LongTitleFormatter: function (value, rec, index) {
			        if (value.length > 15) value = value.substr(0, 12) + "...";
			        return value;
			    }
			};

/**
 * 根据传入URL生成一个基于当前窗体的dialog
 */
function createModalDialog(id, _url, _title, _width, _height, _icon){
    $("body").append("<div id='"+id+"' class='easyui-dialog'></div>");
    if (_width == null)
        _width = 800;
    if (_height == null)
        _height = 400;

    $("#"+id).dialog({
        href: _url
    });
    $("#"+id).dialog('open');
}

/**
 * 信息已保存时右下角滑动自动消失的提示信息
 * 统一
 */
function showSaved(){
	$.messager.show({
		title:'提示信息',
		msg:'信息已保存.',
		timeout:3000,
		showType:'slide'
	});
}
function showSlide(title,message){
	$.messager.show({
		title:title,
		msg:message,
		timeout:3000,
		showType:'slide'
	});
}
function showSaveb(){
	$.messager.show({
		title:'提示信息',
		msg:'请先保存.',
		timeout:3000,
		showType:'slide'
	});
}
/**
 * datagrid没有返回结果时显示
 */
function showEmptyResultMsg(){
$.messager.show({
	title:'提示信息',
	msg:'没有查询结果',
	timeout:3000,
	showType:'slide'
});
}




/**
 * 显示异常
 * 内容为配置的异常拦截器对应的view
 * @param content
 */
function showError(content){
	$.messager.show({
		title:'提示信息',
		msg:content,
		width:350,
		height:260,
		showType:'slide'
	});
}

function showPageDialog(url, title, width, height,callback, shadow) {  //
    var content = '<iframe src="' + url + '" width="100%" height="100%" style="padding: 0px;overflow:hidden;" frameborder="0" scrolling="no"></iframe>';  
    var boarddiv = '<div id="tmpDlg" title="' + title + '"></div>'//style="overflow:hidden;"可以去掉滚动条  
    $(document.body).append(boarddiv);  
    var win = $('#tmpDlg').dialog({  
        content: content,  
        width: width,  
        resizable:true,
        height: height,  
        modal: arguments[5]?arguments[5]:true,   //默认为模式对话框
        title: title,  
//        buttons:[], //按钮在这里不设置否则会产生一个窄的灰色div
        onClose:callback
    });  
    win.dialog('open');  
    return win;
} 
//和show是一对
function closePageDialog(){
	 parent.$('#tmpDlg').dialog('close');
}


$.fn.serializeJson = function(noIncludeEmpty)    
{    
   var o = {};    
   // 添加filebox value
   $(".easyui-filebox").each(function() {
	   o[$(this).attr('textboxname')]=$(this).filebox("getText");
   })
   var a = this.serializeArray();    
   $.each(a, function() {    
       if (o[this.name]) {    
           if (!o[this.name].push) {    
               o[this.name] = [o[this.name]];    
           }    
           o[this.name].push(this.value || '');    
       } else {  
    	   if(noIncludeEmpty){
    		   if(this.value){
    			   o[this.name] = this.value || '';
    		   }
    	   } else {
    		   o[this.name] = this.value || '';    
    	   }
       }    
   });    
   return o;    
};  

function goback(){
	if(document.referrer=='${ctx}'){
		alert('top');
	}else{
		location.href=document.referrer;
	}
}
function setCookie(cname, cvalue, exdays) {  
    var d = new Date();  
    d.setTime(d.getTime() + (exdays*24*60*60*1000));  
    var expires = "expires="+d.toUTCString();  
    document.cookie = cname + "=" + cvalue + "; path=/;" + expires;  
}
//获取cookie  
function getCookie(cname) {  
    var name = cname + "=";  
    var ca = document.cookie.split(';'); 
    for(var i=0; i<ca.length; i++) {  
        var c = ca[i];  
        while (c.charAt(0)==' ') c = c.substring(1);  
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);  
    }  
    return "";  
}  
//清除cookie    
function clearCookie(name) {    
    setCookie(name, "", -1);    
}
//datagrid保持数据封箱拆箱操作
function packageDataGridOption(grid){
	var dataGridOption={};
	dataGridOption.pageSize=grid.datagrid('getPager').data("pagination").options.pageSize;
	dataGridOption.pageNumber=grid.datagrid('getPager').data("pagination").options.pageNumber;
	dataGridOption.selected=grid.datagrid('getRowIndex',grid.datagrid('getSelected'));
	return dataGridOption;
}
//out参数通常配置方式为panelName.controlName
//对组件赋值等操作时需要使用组件id
function getControlId(out){
	var arr = out.split(".");
	return "#"+arr[0] + "_" + arr[1];
}

function getControlName(panelControlName){
	var arr = panelControlName.split(".");
	if(arr!=null && arr.length>1){
		return arr[1];
	}
	return null;
}
//取指定panel的指定control值。
//TODO未来扩展组件支持。
function getPanelControlValue(panelControlName,single){
	if(panelControlName==undefined) return null;
	var arr = panelControlName.split(".");
	var panelId = "#"+arr[0];
	var controlName = arr[1];
	var className = $(panelId).attr("class");
	var currentId = undefined;
	if(!className){
		currentId = $(getControlId(panelControlName)).val();
	}else if(className.indexOf('datagrid')>=0){
		if($(panelId).datagrid("getColumnOption", 'ck')){
			var selectedRows = $(panelId).datagrid('getChecked');
		} else {
			var selectedRows = $(panelId).datagrid('getSelections');
		}
		var ids = [];
		for(var i=0; i<selectedRows.length; i++){
			ids.push(selectedRows[i][controlName]);
		}
		currentId = ids.join(',');
	}else if(className&&className.indexOf('tree')>=0){
		var selectTree = getStorage('selectTree');
		if(selectTree&&$(panelId).tree('getSelected')){
			currentId = selectTree[controlName];
		}
	}else if(className&&className.indexOf('barChart')>=0||className.indexOf('columnChart')>=0||className.indexOf('pieChart')>=0||className.indexOf('lineChart')>=0){
		var selectChartData = getStorage('selectChartDate');
		var type =selectChartData.componentSubType;
        var dataIndex = selectChartData.dataIndex;
        //panel取Echart
        var encodeCharts = eval(arr[0]);
        var seri = encodeCharts.getOption().series[selectChartData.seriesIndex].data;
        var controlIndex = $(getControlId(panelControlName)).index();
		if(type!="pie") {
            //controlName去PropertyIndex
            currentId = seri[seri.length - 1][0][controlIndex][dataIndex];
        }else{
            currentId = seri[seri.length-1].key[0][controlIndex][dataIndex];
		}

	}else{
        currentId = $(panelId+" [name='"+controlName+"']").val();
    }
	if(!currentId){
		console.warn("panelControlName.value" + panelControlName + " is undefined...可能是错误的配置值造成无法获取值");
	}
	return currentId;
}
function getCurrentTabIndex(){
	var index = $("#center_tab").tabs('getTab',$("#center_tab").tabs('getSelected'));
	return index;
}

//密码内容加密
function getMD5PassWord(obj,newValue,oldValue){
//	var pswd = $(".js-password").val();
	var pswd = newValue;
	alert("输入密码框原值："+pswd);
	if(pswd != ""){
		$('.js-MD5password').val(MD5(pswd));
	}
	alert("密码框加密值："+$('.js-MD5password').val());
}
//本地变量存储路径获得
function getPath(t){
	return $(".js-ui").attr('id')+"_"+t.attr("id")+"_"+$("#code").val();
}

function getUIMode(uiName){
	return getStorage(uiName + '_EditMode')
}
function setUIMode(uiName, mode){
	return setStorage(uiName + '_EditMode', mode)
}

function setEncodeParam(out,paramObj){
	setStorage('EncodeParam' + out,paramObj);
}

function getEncodeParam(out){
	return getStorage('EncodeParam' + out);
}

function delEncodeParam(out){
	delStorage('EncodeParam' + out);
}
//用逗号分隔符将数组中指定属性合并成一个值返回
function getPropertyValues(arr,propertyName){
	var len = arr.length;
	if(len){
		var arrResult =  new Array(len);
		for(i=0;i<len;i++){
			arrResult[i] = arr[i][propertyName];
		}
		return arrResult.join(",");
	}
	return "";
}
//grid向上移动一列
function moveUp (dg) {
	var selectrow=$('#'+dg).datagrid('getSelected');
	if(!selectrow){
		$.messager.alert('提示', '必须选中一列移动!', 'warning');  
	}
	var rowIndex=$('#'+dg).datagrid('getRowIndex', selectrow);  
	if(rowIndex==0){  
        $.messager.alert('提示', '顶行无法上移!', 'warning');  
    }else{
    	//判断当前行是否被选中
	    var allRows=$('#'+dg).datagrid('getChecked');   //获取所有被选中的行
	    var flg=false;
	    $.each(allRows,function(i,n){
    	    if(selectrow.field==n.field){
    	    	flg=true;
    	    	return false;
    	    }
	    })
        $('#'+dg).datagrid('deleteRow', rowIndex);//删除一行  
        rowIndex--;  
        $('#'+dg).datagrid('insertRow', {  
            index:rowIndex,  
            row:selectrow  
        });  
        $('#'+dg).datagrid('selectRow', rowIndex);
        if(flg)
        	$('#'+dg).datagrid("checkRow",rowIndex);
    } 
}
function moveDown (dg) {
	var rows=$('#'+dg).datagrid('getRows');  
	var rowlength=rows.length; 
	var selectrow=$('#'+dg).datagrid('getSelected');
	if(!selectrow){
		$.messager.alert('提示', '必须选中一列移动!', 'warning');  
	}
	var rowIndex=$('#'+dg).datagrid('getRowIndex', selectrow);  
	if(rowIndex==rowlength-1){  
        $.messager.alert('提示', '底行无法下移!', 'warning');  
    }else{
    	//判断当前行是否被选中
	    var allRows=$('#'+dg).datagrid('getChecked');   //获取所有被选中的行
	    var flg=false;
	    $.each(allRows,function(i,n){
    	    if(selectrow.field==n.field){
    	    	flg=true;
    	    	return false;
    	    }
	    })
        $('#'+dg).datagrid('deleteRow', rowIndex);//删除一行  
        rowIndex++;  
        $('#'+dg).datagrid('insertRow', {  
            index:rowIndex,  
            row:selectrow  
        });  
        $('#'+dg).datagrid('selectRow', rowIndex);  
        if(flg)
        	$('#'+dg).datagrid("checkRow",rowIndex);
    } 
}

function setNav(ToTitle, To){
	var path=$("#from").val()+$("#code").val();
	if(getStorage(path)){
		var pathValue = JSON.parse(JSON.stringify(getStorage(path)));
	}else{
		var pathValue=[];
	}
	pathValue.push(To);
	setStorage(path, pathValue);
	
	if(getStorage(path + "_name")){
		var pathNameValue = JSON.parse(JSON.stringify(getStorage(path + "_name")));
	}else{
		var pathNameValue=[];
	}
	pathNameValue.push(ToTitle)
	setStorage(path + "_name", pathNameValue);
}
function getTransitionUrl(token, type, puid,EditMode, from, code, ToTitle, To, url){
	setNav(ToTitle, To);
	return url+To+"?token="+token+"&type="+type+"&puid="+puid+"&EditMode="+EditMode+"&from="+from+"&code="+code;
}

function getRows(panelName){
	var rowCount = $("#"+panelName).datagrid('getRows').length;
	return rowCount;
	
}

function getRowsValueTrue(panelName,controlName){
	debugger
	var rows = $("#"+panelName).datagrid('getRows');
	var len = rows.length;
	var flag = true;
	var ids = [];
	for(i=0;i<len;i++){
	    value = rows[i][controlName];
		console.log(value);
		if(value=='0'){
			flag =  false;
			return flag;
		}
	}
	return flag;
	
}














/*
 *  jQuery table2excel - v1.1.1
 *  jQuery plugin to export an .xls file in browser from an HTML table
 *  https://github.com/rainabba/jquery-table2excel
 *
 *  Made by rainabba
 *  Under MIT License
 */
//table2excel.js
;(function ( $, window, document, undefined ) {
    var pluginName = "table2excel",

    defaults = {
        exclude: ".noExl",
        name: "Table2Excel",
        filename: "table2excel",
        fileext: ".xls",
        exclude_img: true,
        exclude_links: true,
        exclude_inputs: true
    };

    // The actual plugin constructor
    function Plugin ( element, options ) {
            this.element = element;
            // jQuery has an extend method which merges the contents of two or
            // more objects, storing the result in the first object. The first object
            // is generally empty as we don't want to alter the default options for
            // future instances of the plugin
            //
            this.settings = $.extend( {}, defaults, options );
            this._defaults = defaults;
            this._name = pluginName;
            this.init();
    }

    Plugin.prototype = {
        init: function () {
            var e = this;

            var utf8Heading = "<meta http-equiv=\"content-type\" content=\"application/vnd.ms-excel; charset=UTF-8\">";
            e.template = {
                head: "<html xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns=\"http://www.w3.org/TR/REC-html40\">" + utf8Heading + "<head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets>",
                sheet: {
                    head: "<x:ExcelWorksheet><x:Name>",
                    tail: "</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet>"
                },
                mid: "</x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body>",
                table: {
                    head: "<table>",
                    tail: "</table>"
                },
                foot: "</body></html>"
            };

            e.tableRows = [];

            // get contents of table except for exclude
            $(e.element).each( function(i,o) {
                var tempRows = "";
                $(o).find("tr").not(e.settings.exclude).each(function (i,p) {
                    
                    tempRows += "<tr>";
                    $(p).find("td,th").not(e.settings.exclude).each(function (i,q) { // p did not exist, I corrected
                        
                        var rc = {
                            rows: $(this).attr("rowspan"),
                            cols: $(this).attr("colspan"),
                            flag: $(q).find(e.settings.exclude)
                        };
                        
                        if( rc.flag.length > 0 ) {
                            tempRows += "<td> </td>"; // exclude it!!
                        } else {
                            if( rc.rows  & rc.cols ) {
                                tempRows += "<td>" + $(q).html() + "</td>";
                            } else {
                                tempRows += "<td";
                                if( rc.rows > 0) {
                                    tempRows += " rowspan=\'" + rc.rows + "\' ";
                                }
                                if( rc.cols > 0) {
                                    tempRows += " colspan=\'" + rc.cols + "\' ";
                                }
                                tempRows += "/>" + $(q).html() + "</td>";
                            }
                        }
                    });

                    tempRows += "</tr>";
                    console.log(tempRows);
                    
                });
                // exclude img tags
                if(e.settings.exclude_img) {
                    tempRows = exclude_img(tempRows);
                }

                // exclude link tags
                if(e.settings.exclude_links) {
                    tempRows = exclude_links(tempRows);
                }

                // exclude input tags
                if(e.settings.exclude_inputs) {
                    tempRows = exclude_inputs(tempRows);
                }
                e.tableRows.push(tempRows);
            });

            e.tableToExcel(e.tableRows, e.settings.name, e.settings.sheetName);
        },

        tableToExcel: function (table, name, sheetName) {
            var e = this, fullTemplate="", i, link, a;

            e.format = function (s, c) {
                return s.replace(/{(\w+)}/g, function (m, p) {
                    return c[p];
                });
            };

            sheetName = typeof sheetName === "undefined" ? "Sheet" : sheetName;

            e.ctx = {
                worksheet: name || "Worksheet",
                table: table,
                sheetName: sheetName
            };

            fullTemplate= e.template.head;

            if ( $.isArray(table) ) {
                for (i in table) {
                      //fullTemplate += e.template.sheet.head + "{worksheet" + i + "}" + e.template.sheet.tail;
                      fullTemplate += e.template.sheet.head + sheetName + i + e.template.sheet.tail;
                }
            }

            fullTemplate += e.template.mid;

            if ( $.isArray(table) ) {
                for (i in table) {
                    fullTemplate += e.template.table.head + "{table" + i + "}" + e.template.table.tail;
                }
            }

            fullTemplate += e.template.foot;

            for (i in table) {
                e.ctx["table" + i] = table[i];
            }
            delete e.ctx.table;

            var isIE = /*@cc_on!@*/false || !!document.documentMode; // this works with IE10 and IE11 both :)            
            //if (typeof msie !== "undefined" && msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // this works ONLY with IE 11!!!
            if (isIE) {
                if (typeof Blob !== "undefined") {
                    //use blobs if we can
                    fullTemplate = e.format(fullTemplate, e.ctx); // with this, works with IE
                    fullTemplate = [fullTemplate];
                    //convert to array
                    var blob1 = new Blob(fullTemplate, { type: "text/html" });
                    window.navigator.msSaveBlob(blob1, getFileName(e.settings) );
                } else {
                    //otherwise use the iframe and save
                    //requires a blank iframe on page called txtArea1
                    txtArea1.document.open("text/html", "replace");
                    txtArea1.document.write(e.format(fullTemplate, e.ctx));
                    txtArea1.document.close();
                    txtArea1.focus();
                    sa = txtArea1.document.execCommand("SaveAs", true, getFileName(e.settings) );
                }

            } else {
                var blob = new Blob([e.format(fullTemplate, e.ctx)], {type: "application/vnd.ms-excel"});
                window.URL = window.URL || window.webkitURL;
                link = window.URL.createObjectURL(blob);
                a = document.createElement("a");
                a.download = getFileName(e.settings);
                a.href = link;

                document.body.appendChild(a);

                a.click();

                document.body.removeChild(a);
            }

            return true;
        }
    };

    function getFileName(settings) {
        return ( settings.filename ? settings.filename : "table2excel" );
    }

    // Removes all img tags
    function exclude_img(string) {
        var _patt = /(\s+alt\s*=\s*"([^"]*)"|\s+alt\s*=\s*'([^']*)')/i;
        return string.replace(/<img[^>]*>/gi, function myFunction(x){
            var res = _patt.exec(x);
            if (res !== null && res.length >=2) {
                return res[2];
            } else {
                return "";
            }
        });
    }

    // Removes all link tags
    function exclude_links(string) {
        return string.replace(/<a[^>]*>|<\/a>/gi, "");
    }

    // Removes input params
    function exclude_inputs(string) {
        var _patt = /(\s+value\s*=\s*"([^"]*)"|\s+value\s*=\s*'([^']*)')/i;
        return string.replace(/<input[^>]*>|<\/input>/gi, function myFunction(x){
            var res = _patt.exec(x);
            if (res !== null && res.length >=2) {
                return res[2];
            } else {
                return "";
            }
        });
    }

    $.fn[ pluginName ] = function ( options ) {
        var e = this;
            e.each(function() {
                if ( !$.data( e, "plugin_" + pluginName ) ) {
                    $.data( e, "plugin_" + pluginName, new Plugin( this, options ) );
                }
            });

        // chain jQuery functions
        return e;
    };

})( jQuery, window, document );



function excelDownLoad(tableid) {  
	$("#"+tableid).prev().table2excel({
		exclude: ".noExl",
		name: "Excel Document Name",
		filename: "myFileName" + new Date().toISOString().replace(/[\-\:\.]/g, ""),
		fileext: ".xlsx",
		exclude_img: false,
		exclude_links: true,
		exclude_inputs: true
	});
  
}  



