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
    height: 180,
    minHeight: 100,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Upload Window',
    closable: true,
    constrain: true,
    defaultFocus: 'textfield',
    modal:true,
    layout: 'fit',
    items: [{
        xtype: 'form',
        layout: 'form',
        padding: '10px',
        items: [
        		{
                id: 'File',
                name: 'File',
                inputType: "file",
                fieldLabel: '上传图片',
                xtype: 'textfield',
                anchor: '40%',
                allowBlank: false,
                listeners: {
                    'render': 'previewImage'
                	}
	            },
	            {
                xtype: 'box', //或者xtype: 'component',
                //width: '100%', //图片宽度
                //height: 200, //图片高度
                fieldLabel: "预览图片",
                id: 'browseImage',
                autoEl: {
                    tag: 'img',    //指定为img标签
                    src: 'resources/images/user-profile/1.jpg',
                    id: 'imageBrowse'
                }
            }
        	/*{
        	xtype: 'filefield',
	        width: 400,
	        labelWidth: 80,
	        name:'file',
	        emptyText: 'Select an png/jpg file!', 
	        fieldLabel: '上传头像:',
	        labelSeparator: '',
	        buttonConfig: {
				            xtype: 'filebutton',
				            glyph:'',
				            iconCls: 'x-fa fa-upload',
				            text: 'Browse'
        	}
        	}*/
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
