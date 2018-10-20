Ext.define('Admin.view.authentication.ResetPasswordWindow', {
	extend: 'Admin.view.authentication.LockingWindow',
	xtype: 'resetPasswordWindow',
    requires: [
        'Admin.view.authentication.Dialog',
        'Ext.form.Label',
        'Ext.form.field.Text',
        'Ext.button.Button'
    ],

    title: 'Password Reset',

    defaultFocus : 'authdialog',  // Focus the Auth Form to force field focus as well

    items: [
        {
            xtype: 'authdialog',
            width: 455,
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
                    alt:'步骤③',
                    src:'http://localhost:8080/images/resetpassword/3.png',
                },
                {
                	xtype: 'textfield',
                    fieldLabel: '新的密码',
                    allowBlank : false,
                    inputType: 'password',
                    name:'new_password'
                },
                {
                	xtype: 'textfield',
                    fieldLabel: '再次输入',
                    allowBlank : false,
                    inputType: 'password',
                    name:'again_password'
                },
                {
                    xtype: 'button',
                    reference: 'resetPassword',
                    scale: 'large',
                    ui: 'soft-blue',
                    formBind: true,
                    iconAlign: 'right',
                    iconCls: 'x-fa fa-angle-right',
                    text: 'Next',
                    listeners: {
                        click: 'onNextClick3'
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

