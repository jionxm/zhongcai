/*!
 * save MarkDown.md as PDF plugin for Editor.md
 *
 * @file        saveAsPdf-dialog.js
 * @author      zzm
 * @version     1.3.4
 * @updateTime  2018-6-22
 * {@link       https://github.com/pandao/editor.md}
 * @license     MIT
 */

(function() {

    var factory = function (exports) {

		var pluginName   = "saveAsPdf-dialog";

		exports.fn.saveAsPdfDialog = function() {
            var _this       = this;
            var cm          = this.cm;
            var lang        = this.lang;
            var editor      = this.editor;
            var settings    = this.settings;
            var selection   = cm.getSelection();
            var imageLang   = lang.dialog.PDF;
            var classPrefix = this.classPrefix;
			var dialogName  = classPrefix + pluginName, dialog;

			cm.focus();
			
			var guid   = (new Date).getTime();
			var actionDoc = settings.saveAsPDFURL+ (settings.saveAsPDFURL.indexOf("?") >= 0 ? "&" : "?");
			var modeHandle = settings.modeHandleURL; 
			
			if (editor.find("." + dialogName).length > 0)
            {
                dialog = editor.find("." + dialogName);
                this.dialogShowMask(dialog);
                this.dialogLockScreen();
                dialog.show();
            }
            else
            {
            	if(modeHandle === 'Add'){
            		
            	}else if(modeHandle === 'Edit'){
            		//文件名
   				 	var fileTitle = settings.mdUploadURL.substring(settings.mdUploadURL.indexOf("&fileTitle=",0)+11,settings.mdUploadURL.length);
   				 	if(fileTitle === ''){
   				 		//新增窗口传值
   				 		selection = "";
   				 	}else{
   				 		selection = fileTitle;
   				 	}
            	}
            	 
            	 var dialogHTML = "<div class=\"" + classPrefix + "form\">" + 
                                        "<label>" + imageLang.alt + "</label>" + 
                                        "<input type=\"text\" value=\"" + selection + "\" data-alt />" +
                                        "<br/>" +
                                        "</div>";
            	 
                dialog = this.createDialog({
                    title      : imageLang.title,
                    width      : 380,
                    height     : 190,
                    content    : dialogHTML,
                    mask       : settings.dialogShowMask,
                    drag       : settings.dialogDraggable,
                    lockScreen : settings.dialogLockScreen,
                    maskStyle  : {
                        opacity         : settings.dialogMaskOpacity,
                        backgroundColor : settings.dialogMaskBgColor
                    },
                    buttons    : {
                        enter  : [lang.buttons.enter, function() {
                        	 var alt  = this.find("[data-alt]").val();
                        	 parent.flg=true;
                             if (alt === "")
                             {
                                 alert(imageLang.mdNameEmpty);
                                 return false;
                             }
 							var altAttr = (alt !== "") ? " \"" + alt + "\"" : "";
 							//_this.gotoLine(1); //指定行插入 （如果可以作为参数传递，可以不向md内追加文档名称的。）

                             //文档名称存放格式
 							//cm.replaceSelection("[文档名称:" + alt + "](" + altAttr + ")"+"\r\n");
 							//给父窗口隐藏控件text赋值
 							
                            this.hide().lockScreen(false).hideMask();
                            return false;
                        }],

                        cancel : [lang.buttons.cancel, function() {                                   
                            this.hide().lockScreen(false).hideMask();

                            return false;
                        }]
                    }
                });
                
                var fileInput  = dialog.find("[class=\"editormd-btn editormd-enter-btn\"]");
                
                function downloadFile(fileId){
                	location.href = parent.ctx+"/localDownload?fileId="+fileId+"&methodType=export";
                }
                var fileId="";
				 if(modeHandle === 'Edit'){
					 fileId= settings.saveAsPDFURL.substring(settings.saveAsPDFURL.indexOf("=",0)+1,settings.saveAsPDFURL.length);
				 }else{
				 }
                fileInput.bind("click", function() {
					var xhr = new XMLHttpRequest;
					 xhr.open("POST",actionDoc
			    			 +"&file_id=" + fileId
			    			 +"&file_name="+dialog.find("[data-alt]").val(),true); 
			    	 xhr.setRequestHeader("Content-Type", "multipart/form-data;boundary=----"+guid);
			    	 var data ="";
					xhr.onreadystatechange=function(){
			    		 if(xhr.readyState ==4 && xhr.status ==200){
			    			 if(xhr.responseText ==-1){
			    				 showSaveb();
			    			 }else{
			    				 var download = "<div align='center' ><h3>导出成功！<h3><br/>";
			    				 fileId=xhr.responseText;
			    				 $.messager.alert("另存为pdf",download,downloadFile(fileId));
			    			 }
			    		 }
			    	};
					xhr.send(data);
				});
            };
		}
    }
	// CommonJS/Node.js
	if (typeof require === "function" && typeof exports === "object" && typeof module === "object")
    {
        module.exports = factory;
    }
	else if (typeof define === "function")  // AMD/CMD/Sea.js
    {
		if (define.amd) { // for Require.js

			define(["editormd"], function(editormd) {
                factory(editormd);
            });

		} else { // for Sea.js
			define(function(require) {
                var editormd = require("./../../editormd");
                factory(editormd);
            });
		}
	}
	else
	{
        factory(window.editormd);
	}

})();

