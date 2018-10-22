Ext.define('Admin.view.authentication.VerifyEmailWindow', {
	extend: 'Admin.view.authentication.LockingWindow',
	xtype: 'verifyEmailWindow',
    requires: [
        'Admin.view.authentication.Dialog',
        'Ext.form.Label',
        'Ext.form.field.Text',
        'Ext.button.Button'
    ],

    title: 'Email Validation',

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
                    alt:'步骤②',
                    src:'http://localhost:8080/images/resetpassword/2.png',
                },
				{	
                    xtype: 'textfield',
                    emptyText:'EmailBox not found',
                    id:'user_Email',
                    //width: 225,
					height: 55,
                    editable: false
            	},
            	{
					xtype:'button',
					height: 55,
					text: 'Get The Verification Code By Email',
					iconCls: 'x-fa fa-paper-plane',
					//iconAlign : 'right',
					listeners: {
								click: 'send_email_btn'
								}
                },
                {
                    xtype: 'textfield',
                    height: 55,
                    name: 'number',
                    emptyText: 'Email Verification Code'
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
                        click: 'onNextClick2'
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

