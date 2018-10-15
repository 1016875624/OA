Ext.define('Admin.view.main.UploadWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.uploadWindow',
    autoShow: true,
    requires: [
        'Ext.button.Button',
        'Ext.form.field.Text',
        'Ext.form.field.File',
        'Ext.form.field.HtmlEditor'
    ],
    height: 400,
    width: 300,
    scrollable: true,
    title: 'Upload Window',
    closable: true,
    constrain: true,
    defaultFocus: 'textfield',
    modal:true,
    layout: {
    	align: 'middle',
    	pack: 'center',
    	type: 'vbox'
    },
    items: [{
        xtype: 'form',
        layout: {
        	align: 'middle',
        	pack: 'center',
        	type: 'vbox'
        },
        padding: '10px',
        items: [
        		{
                id: 'File',
                name: 'File',
                inputType: "file",
                xtype: 'textfield',
                anchor: '40%',
                allowBlank: false,
                listeners: {
                    'render': 'previewImage'
                	}
	            },
	            {
                xtype: 'box', 
                width: 200, //图片宽度
                height: 200, //图片高度
                fieldLabel: "预览图片",
                id: 'browseImage',
                autoEl: {
                    tag: 'img',    //指定为img标签
                    src: 'resources/images/user-profile/default.jpg',
                    id: 'imageBrowse'
                }
            }
       ]
    }],
	buttons: ['->',{
        xtype: 'button',
        text: 'Upload',
        handler: 'onClickUploadFormSumbitButton'
    },{
        xtype: 'button',
        text: 'Close',
        handler: function(btn) {
            btn.up('window').close();
        }
    },'->']
});
