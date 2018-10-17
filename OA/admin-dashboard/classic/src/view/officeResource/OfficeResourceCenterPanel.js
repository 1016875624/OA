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
		}
	]	
});
