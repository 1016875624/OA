Ext.define('Admin.view.officeResource.OfficeResourceCenterPanel', {
    extend: 'Ext.tab.Panel',
    xtype: 'officeResourceCenterPanel',
    controller:'officeResourceViewController',	
    viewModel: {type: 'officeResourceViewModel'},
    layout: 'fit',
    items: [
		{	
			title: 'OfficeResource',
			glyph: 'xf233@FontAwesome',
			xtype:'officeResourceGridPanel'
		},
		{
			title: 'EmployeeResource',
			glyph: 'xf183@FontAwesome',
			html: '111'
		},
		{
			title: 'Trade',
			glyph: 'xf25c@FontAwesome',
			html: '222'
		}
	]	
});
