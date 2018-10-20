Ext.define('Admin.view.authentication.AuthenticationController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.authentication',

    //TODO: implement central Facebook OATH handling here

	onFaceBookLogin : function() {
        this.redirectTo('dashboard', true);
    },

	onLoginButton: function(btn){
    	var me = this;
        Ext.Ajax.request({
            url: '/login',
            method: 'post',
            params: {
                userId: btn.up("form").getForm().findField("userid").getValue(),
                password: btn.up("form").getForm().findField("password").getValue(),
				code: btn.up("form").getForm().findField("code").getValue()
            },
            //var name=Ext.getCmp('loginUserName');
            success: function(response, options) {
            	var json = Ext.util.JSON.decode(response.responseText);
	            if(json.success){
	            	me.redirectTo('dashboard', true);
	            	//var id = Ext.getCmp('loginUserId').setText(json.map.userId);
	            	//var name = Ext.getCmp('loginUserName').setText(json.map.userId);
	            	var id = Ext.getCmp('loginUserName').setText("用户名： "+json.map.userId);
	            	console.log(id);
	            	var position = Ext.getCmp('userPositon').setText(json.map.position);
	            	console.log(position);
	            	var headIcon = Ext.getCmp('head_Icon');
			        console.log(json.msg);
			        var path = '/images/employee/' + json.msg;
			        headIcon.setSrc(path);
			        console.log(path);
		        }else{
		        	Ext.Msg.alert('登录失败', json.msg);
		        }
            },
            failure:  function(response, options){
            	Ext.Msg.alert('登录失败', '请重新登录');
            }
            
        });
	},
	
    // 打开修改密码窗口
    resetPasswordBtn: function(btn) {
    	var win = Ext.widget('verifyIdWindow');
    	win.show();
    },
    
    // ID验证窗口
    onNextClick1: function(btn) {
    	window.userid001=btn.up("form").getForm().findField("user_Id").getValue();
    	Ext.Ajax.request({
            url: '/passwordReset/check',
            method: 'post',
            params: {
            	userId: btn.up("form").getForm().findField("user_Id").getValue(),
				code: btn.up("form").getForm().findField("code").getValue()
            },
            success: function(response, options) {
            	var json = Ext.util.JSON.decode(response.responseText);
	            if(json.success){
	            	btn.up('window').close();
	            	Ext.widget('verifyEmailWindow').show();
	            	var id = Ext.getCmp('user_Id');
	            	var changeCode = Ext.getCmp('change_Code');
	            	var email = Ext.getCmp('user_Email').setValue(json.map.email);
	            	console.log(email);
		        }else{
		        	Ext.Msg.alert('验证失败', json.msg);
		        }
            },
            failure:  function(response, options){
            	Ext.Msg.alert('验证失败', '请重新输入ID');
            }
    	})
    },
	// 发送邮件按钮
	send_email_btn: function(btn) {
		var userEmail = Ext.getCmp('user_Email').getValue();
		Ext.Ajax.request({
	        url: 'http://localhost:8080/passwordReset/sendEmail',
	        method: 'post',
	        params: {
				userEmail:userEmail
				},
	        success: function(response, options) {
	        	var json = Ext.util.JSON.decode(response.responseText);
	            if(json.success){
	            	Ext.Msg.alert('发送成功', json.msg);
		        }else{
		        	Ext.Msg.alert('发送失败', json.msg);
		        }
	        },
	    	failure: function(form,action){ 
				Ext.Msg.alert('失败', '发送失败');
			}
	    })
	},
    
    // Email验证窗口
    onNextClick2: function(btn) {
    	Ext.Ajax.request({
            url: '/passwordReset/verifyEmail',
            method: 'post',
            params: {
            	number: btn.up("form").getForm().findField("number").getValue()
            },
            success: function(response, options) {
            	var json = Ext.util.JSON.decode(response.responseText);
	            if(json.success){
	            	btn.up('window').close();
	            	Ext.widget('resetPasswordWindow').show();
	            	console.log(number);
		        }else{
		        	Ext.Msg.alert('验证失败', json.msg);
		        }
            },
            failure:  function(response, options){
            	Ext.Msg.alert('验证失败', '验证失败');
            }
    	})
    },
    
    // 密码重置窗口
    onNextClick3: function(btn) {
    	var userId = window.userid001;
    	Ext.Ajax.request({
            url: '/passwordReset/reset',
            method: 'post',
            params: {
            	//userId: btn.up("window").down("form").getForm().findField("user_Id").getValue(),
            	userId:userId,
            	newpassword: btn.up("form").getForm().findField("new_password").getValue(),
            	againpassword: btn.up("form").getForm().findField("again_password").getValue()
            },
            success: function(response, options) {
            	var json = Ext.util.JSON.decode(response.responseText);
	            if(json.success){
	            	btn.up('window').close();
	            	Ext.widget('changeCompleteWindow').show();
	            	console.log(newpassword);
		        }else{
		        	Ext.Msg.alert('验证失败', json.msg);
		        }
            },
            failure:  function(response, options){
            	Ext.Msg.alert('验证失败', '验证失败了');
            }
    	})
    	
    },
    
    // 完成设置，返回登录界面
    onNextClick4: function(btn) {
    	btn.up("window").close();
    },
    
    /*// 点击确认按钮
    resetPasswordSubmit: function() {
    	var win=btn.up("window");
    	Ext.Msg.alert('message', '修改成功，点击返回登录',function(){
	    	  win.close();
	    });
    },*/
    
    onResetClick:  function() {
        this.redirectTo('dashboard', true);
    },
	
    onLoginAsButton: function() {
        this.redirectTo('login', true);
    },

    onNewAccount:  function() {
        this.redirectTo('register', true);
    },

    onSignupClick:  function() {
        this.redirectTo('dashboard', true);
    },

    
	
	onChangeCode:  function() {
		var o = Ext.getCmp("changeCode").getEl().dom.src="/verify/code?date="+new Date();
		var o2 = Ext.getCmp("change_Code").getEl().dom.src="/verify/code?date="+new Date();
    },
});