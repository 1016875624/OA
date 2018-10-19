Ext.define('Admin.view.authentication.PasswordResetWindow', {
	extend: 'Ext.window.Window',
    // xtype: 'passwordResetWindow',
    alias: 'widget.passwordResetWindow',
    
    requires: [
    	'Ext.layout.container.Card',
    	'Ext.tab.Panel'
    ],
    title: '修改密码',
    autoShow: true,
    width: 800,
    height: 600,
    defaults: {
        bodyPadding: 10,
        scrollable: true
    },
    tabBar: {
        layout: {
            pack: 'center'
        }
    },
    xtype: 'form',
    layout: 'form',
    items: [
		    	{
			    	title: '用户账号',
			    	xtype: 'textfield',
			    	reference: 'validating_id',
			    	fieldLabel: '请输入您的ID'
		    	},
		    	{
			    	title: '邮箱验证',
			    	reference: 'validating_email',
			    	items: [
			    		{
			    			xtype: 'textfield',
					    	fieldLabel: '请输入您的邮箱'
			    		},
			    		{
			    			xtype: 'button',
			    	        text: 'Send Email',
			    	        handler: ''
			    		},
			    		{
			    			xtype: 'textfield',
			    			fieldLabel: '输入邮箱中的验证码'
			    		}
			    	]
		    	},
		    	{
			    	title: '修改密码',
			        // disabled: true
			    	reference: 'new_password',
			    	items: [
			    		{
			    			xtype: 'textfield',
			    			inputType: 'password',
					    	fieldLabel: '输入新的密码'
			    		},
			    		{
			    			xtype: 'textfield',
			    			inputType: 'password',
					    	fieldLabel: '再次输入密码'
			    		}
			    	]
		    	}
		    	],
    buttons: [{
        xtype: 'button',
        text: '下一步',
        handler: ''
    }]
});
    
    