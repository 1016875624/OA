Ext.define('Admin.view.employeeResource.EmployeeResourceTradeCenterPanel', {
    extend: 'Ext.tab.Panel',
    xtype: 'employeeResourceTradeCenterPanel',
    controller: 'employeeResourceTradeViewController',
    viewModel: {type: 'employeeResourceTradeViewModel'},
    layout: 'fit',
    items: [
		{
			title: 'Trade',
			glyph: 'xf25c@FontAwesome',
			xtype:'employeeResourceTradeGridPanel'
		}
	]
});
