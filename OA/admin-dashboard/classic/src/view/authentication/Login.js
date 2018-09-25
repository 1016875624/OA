Ext.define('Admin.view.authentication.Login', {
    extend: 'Admin.view.authentication.LockingWindow',
    xtype: 'login',

    requires: [
        'Admin.view.authentication.Dialog',
        'Ext.container.Container',
        'Ext.form.field.Text',
        'Ext.form.field.Checkbox',
        'Ext.button.Button'
    ],

    title: 'Let\'s Log In',
    defaultFocus: 'authdialog', // Focus the Auth Form to force field focus as well

    items: [
        {
            xtype: 'authdialog',
            defaultButton : 'loginButton',
            autoComplete: true,
            bodyPadding: '20 20',
            cls: 'auth-dialog-login',
            header: false,
            width: 415,
            layout: {
                type: 'vbox',
                align: 'stretch'
            },

            defaults : {
                margin : '5 0'
            },

            items: [
                {
                    xtype: 'label',
                    text: 'Sign into your account'
                },
                {
                    xtype: 'textfield',
                    cls: 'auth-textbox',
                    name: 'userid',
                    bind: '{userid}',
                    height: 55,
                    hideLabel: true,
                    allowBlank : false,
                    emptyText: 'user id',
                    triggers: {
                        glyphed: {
                            cls: 'trigger-glyph-noop auth-email-trigger'
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    cls: 'auth-textbox',
                    height: 55,
                    hideLabel: true,
                    emptyText: 'Password',
                    inputType: 'password',
                    name: 'password',
                    bind: '{password}',
                    allowBlank : false,
                    triggers: {
                        glyphed: {
                            cls: 'trigger-glyph-noop auth-password-trigger'
                        }
                    }
                },{
                    // items:[
							// xtype: 'textfield',
							// cls: 'auth-textbox',
							// name: 'code',
							// bind: '{code}',
							// height: 55,
							// hideLabel: true,
							// allowBlank : false,
							// emptyText: 'user id',
						// },'-',{
							// xtype: 'textfield',height: 55,text: '验证码',
								// renderer: function(value) {
									// return ;
								// },
							// handler: 'onGetCode'
							// }
						// },{
							// xtype: 'textfield',
							//text: '看不清，换一张',
							// tooltip: 'Add a new row',
							// iconCls: 'fa fa-plus',
							// handler: 'openAddWindow'	
						// }
					// ]
					xtype: 'textfield',
                    cls: 'auth-textbox',
                    height: 55,
                    hideLabel: true,
                    emptyText: 'Verification Code',
                    inputType: 'code',
                    name: 'code',
                    bind: '{code}',
                    allowBlank : false,
                    triggers: {
                        glyphed: {
                            cls: 'trigger-glyph-noop auth-password-trigger'
                        }
                    }
                },
				// {
                    // xtype: 'button',
                    // scale: 'large',
                    // ui: 'gray',
                    // iconAlign: 'right',
                    // iconCls: 'x-fa fa-code',
                    // text: '点击获取验证码',
                    // listeners: {
                        // click: 'onGetCode'
                    // },
					// hidden:true
                // },
				{
					//xtype:'panel', 
					height: 55,
					width: 250,
					html:'<img src="/verify/code.action"/>' ,
					//reference:'codeFieldValue'
					//text:'<img src="/verify/code.action"/>',
					//hidden:true
				},{
					xtype:'button',
					height: 55,
					width: 250, 
					text: '看不清，换一张',
                    onclick: 'onChangeCode'
					//reference:'codeFieldValue'
					//text:'<img src="/verify/code.action"/>',
					//hidden:true
				},{
                    xtype: 'button',
                    reference: 'loginButton',
                    scale: 'large',
                    ui: 'soft-green',
                    iconAlign: 'right',
                    iconCls: 'x-fa fa-angle-right',
                    text: 'Login',
                    formBind: true,
                    listeners: {
                        click: 'onLoginButton'
                    }
                },{
                    xtype: 'container',
                    layout: 'hbox',
                    items: [
                        {
                            xtype: 'checkboxfield',
                            flex : 1,
                            cls: 'form-panel-font-color rememberMeCheckbox',
                            height: 30,
                            bind: '{persist}',
                            boxLabel: 'Remember me'
                        },
                        {
                            xtype: 'box',
                            html: '<a href="#passwordreset" class="link-forgot-password"> Forgot Password ?</a>'
                        }
                    ]
                },
                {
                    xtype: 'box',
                    html: '<div class="outer-div"><div class="seperator">OR</div></div>',
                    margin: '10 0'
                },
                {
                    xtype: 'button',
                    scale: 'large',
                    ui: 'facebook',
                    iconAlign: 'right',
                    iconCls: 'x-fa fa-facebook',
                    text: 'Login with Facebook',
                    listeners: {
                        click: 'onFaceBookLogin'
                    }
                },
                {
                    xtype: 'box',
                    html: '<div class="outer-div"><div class="seperator">OR</div></div>',
                    margin: '10 0'
                },
                {
                    xtype: 'button',
                    scale: 'large',
                    ui: 'gray',
                    iconAlign: 'right',
                    iconCls: 'x-fa fa-user-plus',
                    text: 'Create Account',
                    listeners: {
                        click: 'onNewAccount'
                    }
                }
            ]
        }
    ],

    initComponent: function() {
        this.addCls('user-login-register-container');
        this.callParent(arguments);
    }
});
