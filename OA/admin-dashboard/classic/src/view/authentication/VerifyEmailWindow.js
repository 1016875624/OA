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
                    src:'resources/images/2.png',
                },
                {
					items:[
						{	
		                    xtype: 'textfield',
		                    text:'user@qq.com',
		                    id:'user_Email',
		                    width: 225,
							height: 60,
		                    editable: false
	                	},
	                	{
							xtype:'button',
							height: 60,
							width: 150, 
							text: 'Send',
							listeners: {
										click: 'onChangeCode'
										}
		                 }
		            ]
				},
				{
                    xtype: 'textfield',
                    height: 55,
                    //name: 'code',
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

