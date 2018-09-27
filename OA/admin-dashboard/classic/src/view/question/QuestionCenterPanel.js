Ext.define('Admin.view.question.QuestionCenterPanel', {
    extend: 'Ext.container.Container',
    xtype: 'questionCenterPanel',
    controller: 'questionViewController',
    viewModel: {type: 'questionViewModel'},
    	
    layout: 'fit',
    items: [{xtype:'questionGridPanel'}]
});