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

    title: 'OA办公自动化系统',
    defaultFocus: 'authdialog', // Focus the Auth Form to force field focus as well

    items: [
        {
            xtype: 'authdialog',
            defaultButton : 'loginButton',
            autoComplete: true,
            bodyPadding: '20 20',
            bodyStyle:{
            	background:'#fff',
        		//border-radius:'9px',
        		opacity: '0.8'
            },
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
                },
                {
					items:[{
							xtype:'box',
							width: 225,
							height: 60,
							id : 'changeCode',
							autoEl : {
							tag : 'img',
							src : "/verify/code"	//这里需要根据此条数据传过来的文件名动态拿图片							 
							}
						},{
							xtype:'button',
							height: 60,
							width: 150, 
							text: '看不清，换一张',
							listeners: {
										click: 'onChangeCode'
										}
						}]
				},
				{
					xtype: 'textfield',
                    cls: 'auth-textbox',
                    height: 55,
                    hideLabel: true,
                    emptyText: 'Verification Code',
                    inputType: 'code',
                    name: 'code',
                    bind: '{code}',
                    allowBlank : false
                },
				
				
				{
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
                        	xtype: 'tbtext',
                        	text: 'Forgot Password ?',
                        	margin: '10 0 0 0',
                        	style: {
                        		'text-decoration' : 'underline',
                        		'cursor': 'pointer',
                        		'color' : 'blue'
                        		},
	                        listeners: {
	                            //click: 'resetPasswordBtn'
	                        	//click: 'resetPasswordWin'
                                el:{
                                    click:'resetPasswordBtn' //imgClick方法写在了controller中，在这里也可以直接替换成function（）{console.log('click')}
                                }
	                        }
                        }
                    ]
                }
                /*{
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
                }*/
            ]
        }
    ],

    initComponent: function() {
        this.addCls('user-login-register-container');
        this.callParent(arguments);
    }
});
