Ext.define('Admin.view.employeeResource.EmployeeResourceTradeWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.employeeResourceTradeWindow',
	autoShow: true,
    height: 350,
    minHeight: 350,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Trade EmployeeResource Window',
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
            fieldLabel: '要交易的资源',
            name:'resourceName',
            readOnly: true
		},{
            xtype: 'textfield',
            fieldLabel: '要交易的数量',
            name:'buyNum'
        },{
            xtype: 'textfield',
            fieldLabel: '用什么资源来换',
            name:'resourceNameForTrade'
        },{
            xtype: 'textfield',
            fieldLabel: '愿意用多少资源来换',
            name:'sellNum'
        }]
    }],
   buttons: ['->',{
        xtype: 'button',
        text: 'Submit',
        handler: 'submitTradeForm'
    },{
        xtype: 'button',
        text: 'Close',
        handler: function(btn) {
            btn.up('window').close();
        }
    },'->']
  
});
