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
            url: 'login',
            method: 'post',
            params: {
                userId: btn.up("form").getForm().findField("userid").getValue(),
                password: btn.up("form").getForm().findField("password").getValue(),
				code: btn.up("form").getForm().findField("code").getValue()
            },
            success: function(response, options) {
            	var json = Ext.util.JSON.decode(response.responseText);
	            if(json.success){
	            	me.redirectTo('dashboard', true);
	            	Ext.getCmp('loginUserId').setText(json.map.userId);
	            	//Ext.getCmp('loginUserImage').getEl().dom.src = json.map.loginUserImage;
		        }else{
		        	Ext.Msg.alert('登录失败', json.msg);
		        }
            }
        });
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

    onResetClick:  function() {
        this.redirectTo('dashboard', true);
    },
	
	onChangeCode:  function() {
			/*Ext.Ajax.request({
				url: '/verify/code',
				method: 'get',  
				success: function(response) {
            	var o = Ext.getCmp("changeCode").getEl().dom.src="/verify/code?date="+new Date();
				}				 
			});*/
			var o = Ext.getCmp("changeCode").getEl().dom.src="/verify/code?date="+new Date();
			//Ext.Msg.alert("Title","Click onChangeCode Button");
    },
});