Ext.define('Admin.view.authentication.VerifyIdWindow', {
	extend: 'Admin.view.authentication.LockingWindow',
	xtype: 'verifyIdWindow',
    requires: [
        'Admin.view.authentication.Dialog',
        'Ext.form.Label',
        'Ext.form.field.Text',
        'Ext.button.Button'
    ],

    title: 'ID Validation',

    defaultFocus : 'authdialog',  // Focus the Auth Form to force field focus as well

    items: [
        {
            xtype: 'authdialog',
            width: 415,
            defaultButton: 'resetPassword',
            autoComplete: true,
            bodyPadding: '20 20',
            layout: {
                type: 'vbox',
                align: 'stretch'
            },

            defaults : {
                margin: '10 0'
            },

            cls: 'auth-dialog-login',
            items: [
                {
                	xtype:'image',
                    width:400,
                    height:35,
                    alt:'步骤①',
                    src:'http://localhost:8080/images/resetpassword/1.png',
                },
                {
                    xtype: 'textfield',
                    cls: 'auth-textbox',
                    //id:"user_password_id",
                    reference:'user_password_id',
                    height: 55,
                    name: 'user_Id',
                    hideLabel: true,
                    allowBlank: false,
                    emptyText: 'User Id',
                    triggers: {
                        glyphed: {
                            cls: 'trigger-glyph-noop auth-email-trigger'
                        }
                    }
                },
                {
					items:[{
							xtype:'box',
							width: 225,
							height: 60,
							id : 'change_Code',
							autoEl : {
							tag : 'img',
							src : "/verify/code"	//这里需要根据此条数据传过来的文件名动态拿图片							 
							}
						},{
							xtype:'button',
							height: 60,
							width: 120, 
							text: '看不清，换一张',
							listeners: {
										click: 'onChangeCode'
										}
						}]
				},
				{
                    xtype: 'textfield',
                    //cls: 'auth-textbox',
                    height: 55,
                    name: 'code',
                    //allowBlank: false,
                    emptyText: 'Verification Code'
                },
                {
                    xtype: 'button',
                    reference: 'nextToEmail',
                    scale: 'large',
                    ui: 'soft-blue',
                    formBind: true,
                    iconAlign: 'right',
                    iconCls: 'x-fa fa-angle-right',
                    text: 'Next',
                    listeners: {
                        click: 'onNextClick1'
                    }
                },
                {
                    xtype: 'component',
                    html: '<div style="text-align:right">' +
                        '<a href="" class="link-forgot-password">'+
                            'Back to Log In</a>' +
                        '</div>'
                }
            ]
        }
    ]
});

