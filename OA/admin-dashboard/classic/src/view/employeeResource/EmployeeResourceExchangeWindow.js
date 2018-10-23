Ext.define('Admin.view.employeeResource.EmployeeResourceExchangeWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.employeeResourceExchangeWindow',
	autoShow: true,
    height: 350,
    minHeight: 350,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Exchange EmployeeResource Window',
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
            fieldLabel: 'id',
            name:'id',
            hidden: true,
            readOnly: true
        },{
            xtype: 'textfield',
            fieldLabel: '资源名称',
            name:'resourceName',
			readOnly: true
        },{
            xtype: 'textfield',
            fieldLabel: '原有数目',
            name:'count',
			readOnly: true
        },{
            xtype: 'textfield',
            fieldLabel: '兑换数目',
            name:'exchangeCount'
        }]
    }],
   buttons: ['->',{
        xtype: 'button',
        text: 'Submit',
        handler: 'submitExchangeForm'
    },{
        xtype: 'button',
        text: 'Close',
        handler: function(btn) {
            btn.up('window').close();
        }
    },'->']
  
});
