Ext.define('Admin.view.question.QuestionsViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.questionViewModel',

    requires: [
        'Ext.data.Store',
        'Ext.data.proxy.Memory',
        'Ext.data.field.Integer',
        'Ext.data.field.String',
        'Ext.data.field.Date',
        'Ext.data.field.Boolean',
        'Ext.data.reader.Json',
        'Ext.field.InputMask'
    ],

    stores: {
    	questionLists: {type: 'questionGridStroe'}
    }
});
