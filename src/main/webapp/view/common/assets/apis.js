//后台的接口地址
var API_PROXY = '';
//后台的接口列表
var APIS = {	    
		
		/**
	     * 匿名登录
	     */
		
	    loginAn: {
	        url: '/loginAn'
	    },
	    login: {
	        url: '/login'
	    },
		
		//参考例子
	    frmResultTestDetail: {	//uiname        
	    	save: {	    //自己命名    
	            url: '/api/save',	//执行的逻辑和xml小写
	            needLogin: true,	//不用改
	            tokenKey: 'OnClick_pnlDetailTool_ctlSave_frmResultTestDetail_jssave' //xml的点击事件_panel名称_按钮名称_UIname_逻辑名称
	    	},
	    	saveCustom: {	    //自己命名    
	            url: '/api/savecustom',	//执行的逻辑和xml小写
	            needLogin: true,	//不用改
	            tokenKey: 'OnClick_pnlDetailTool_ctlSave_frmResultTestDetail_jssave2' //xml的点击事件_panel名称_按钮名称_UIname_逻辑名称
	    	},
	    	queryById: {	    //自己命名    
	            url: '/api/querybyid',	//执行的逻辑和xml小写
	            needLogin: true,	//不用改
	            tokenKey: 'OnClick_pnlDetailTool_ctlSave_frmResultTestDetail_queryInfo' //xml的点击事件_panel名称_按钮名称_UIname_逻辑名称
	    	},
	    }
	    
		
		//ftl ajax代码参考
/*		<script type="text/javascript">
		function checkmm(){
			//需要的值
			console.log($("#psd1").val());
			console.log($("#psd2").val());
			var empId = ${empId};
			var id = ${id};
			var name = "${name}";
			var empName = "${empName}";
			var ctlNewPassword = $("#psd1").val();
			var ctlReNewPassword = $("#psd2").val();
			var authType = "${authType}";
			var status = "${status}";
			//ajax代码			
			ajaxPost(APIS.frmCustomerList.queryCustomer,{
				//传的值
				Mode:"Edit",
				empId:empId,
				id:id,
				name:name,
				empName:empName,
				ctlNewPassword:ctlNewPassword,
				ctlReNewPassword:ctlReNewPassword,
				authType:authType,
				status:status
	  		},function(data){
	  			//获取的值
	  			console.log(data);
	  			if(data.code == 0){
	  	        	console.log(data.code);
	  	            $(".submit-img").removeClass("none");
	  	            setTimeout(close, 3000);
	  	          	window.location.replace("${ctx}/login/h5");
	  	      	}
	  		});
		}
		*/
		
	    
};