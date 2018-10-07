Ext.define('Admin.view.workTimeApproval.WorkTimeApprovalCenterPanel', {
    extend: 'Ext.container.Container',
    xtype: 'workTimeApprovalCenterPanel',
    controller: 'workTimeApprovalViewController',
    viewModel: {type: 'workTimeApprovalViewModel'},
    	
    layout: 'fit',
    items: [{xtype:'workTimeApprovalGridPanel'}]
});