Ext.define('Admin.view.authentication.ChangeCompleteWindow', {
	extend: 'Admin.view.authentication.LockingWindow',
	xtype: 'changeCompleteWindow',
    requires: [
        'Admin.view.authentication.Dialog',
        'Ext.form.Label',
        'Ext.form.field.Text',
        'Ext.button.Button'
    ],

    title: 'Complete Change',

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
                    alt:'步骤④',
                    src:'http://localhost:8080/images/resetpassword/4.png',
                },
                {
                	xtype: 'label',
                    cls: 'lock-screen-top-label',
                    text: '恭喜你！修改密码成功！请点击返回登录！'
                },
                {
                    xtype: 'button',
                    //reference: 'resetPassword',
                    scale: 'large',
                    ui: 'soft-blue',
                    formBind: true,
                    iconAlign: 'right',
                    iconCls: 'x-fa fa-angle-right',
                    text: 'Back To Login',
                    listeners: {
                        click: 'onNextClick4'
                    }
                }
                /*{
                    xtype: 'component',
                    html: '<div style="text-align:right">' +
                        '<a href="" class="link-forgot-password">'+
                            'Back to Log In</a>' +
                        '</div>'
                }*/
            ]
        }
    ]
});

