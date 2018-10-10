Ext.define('Admin.view.leaveapprove.LeaveApproveCenterPanel', {
    extend: 'Ext.panel.Panel',
    xtype: 'leaveApproveCenterPanel',
	layout:'fit',
	controller: 'leaveApproveViewController',
    viewModel : { type: 'leaveApproveViewModel'},
	items: [{xtype:'leaveApproveGrid'}]	//需要修改
});