Ext.define('Admin.view.employeeResource.EmployeeResourceCenterPanel', {
    extend: 'Ext.tab.Panel',
    xtype: 'employeeResourceCenterPanel',
    controller: 'employeeResourceViewController',
    viewModel: {type: 'employeeResourceViewModel'},
    layout: 'fit',
    items: [
		{
			title: 'EmployeeResource',
			glyph: 'xf183@FontAwesome',
			xtype:'employeeResourceGridPanel'
		}
	]
});
