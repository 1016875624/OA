Ext.define('Admin.view.workTime.WorkTimeCenterPanel', {
    extend: 'Ext.container.Container',
    xtype: 'workTimeCenterPanel',
    id:"workTimeContainerId",
    controller: 'workTimeViewController',
    viewModel: {type: 'workTimeViewModel'},
    	
    layout: 'fit',
    items: [{xtype:'workTimeGridPanel'}]
});