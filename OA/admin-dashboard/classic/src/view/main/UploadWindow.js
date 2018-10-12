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
        items: [{
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
	    }]
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