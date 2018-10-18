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
	
	resetPasswordWin: function() {
		Ext.widget('passwordreset').show();
    },
    
    resetPasswordBtn: function() {
		Ext.widget('passwordResetWindow').show();
    },
    
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
    },
});