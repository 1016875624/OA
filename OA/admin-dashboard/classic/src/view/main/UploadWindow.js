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
    height: 500,
    width: 500,
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
        id: 'upload-form',
        layout: {
        	align: 'middle',
        	pack: 'center',
        	type: 'vbox'
        },
        padding: '10px',
        items: [
        		/*{
        		id: 'File',
        		xtype: 'filefield',
		        width: 300,
		        labelWidth: 80,
		        name:'file',
		        emptyText: 'Select a photo!', 
		        //fieldLabel: '上传文件:',
		        labelSeparator: '',
		        listeners: {
                    'render': 'previewImage'
                	},
		        buttonConfig: {
		            xtype: 'filebutton',
		            glyph:'',
		            iconCls: 'x-fa fa-upload',
		            text: 'Browse'
		        	}
	            },*/
	           /* {
	            id: 'File',
	            name: 'File',
	            inputType: "file",
	            fieldLabel: '上传头像',
	            xtype: 'textfield',
	            anchor: '40%',
	            allowBlank: false,
	            listeners: {
	            	//监听事件
	                'render': 'previewImage'
	            	}
	            },
	            {
                xtype: 'box', 
                width: 300, //图片宽度
                height: 300, //图片高度
                fieldLabel: "预览图片",
                id: 'browseImage',
                autoEl: {
                    tag: 'img',    //指定为img标签
                    src: 'resources/images/user-profile/default.jpg',
                    id: 'imageBrowse'
                }*/
        		{
                xtype: 'filefield',
                id: 'upload',
                fieldLabel: "上传头像",
                emptyText : '选择图片存放路径',
                buttonText:'浏览...',
                width: '100%',
                name: 'photo',
                listeners : {
                    'render' : 'previewImage'
                }
        		},{
                xtype: 'image',
                id: 'imageId',
                alt:'preview image',
                src: 'resources/images/user-profile/default.jpg',
                width: 300, //图片宽度
                height: 300, //图片高度
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
