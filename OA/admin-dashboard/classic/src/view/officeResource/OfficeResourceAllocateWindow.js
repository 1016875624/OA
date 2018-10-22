Ext.define('Admin.view.officeResource.OfficeResourceAllocateWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.officeResourceAllocateWindow',
	autoShow: true,
    height: 350,
    minHeight: 350,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Allocate OfficeResource Window',
    closable: true,
    constrain: true,
    defaultFocus: 'textfield',
    modal:true,
    layout: 'fit',
	items: [{
        xtype: 'form',
        layout: 'form',
        padding: '10px',
        ariaLabel: 'Enter your name',
        items: [{
            xtype: 'textfield',
            fieldLabel: '分配数目',
            name:'allocatedNum',
            value:'50'
        },{
            xtype: 'textfield',
            fieldLabel: '资源名称',
            name:'resourceName'
        }]
    }],
   buttons: ['->',{
        xtype: 'button',
        text: 'Submit',
        handler: 'submitAllocateForm'
    },{
        xtype: 'button',
        text: 'Close',
        handler: function(btn) {
            btn.up('window').close();
        }
    },'->']
  
});
