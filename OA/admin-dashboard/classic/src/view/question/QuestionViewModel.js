Ext.define('Admin.view.question.QuestionViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.questionViewModel',

    requires: [
        'Ext.data.Store',
        'Ext.data.proxy.Memory',
        'Ext.data.field.Integer',
        'Ext.data.field.String',
        'Ext.data.field.Date',
        'Ext.data.field.Boolean',
        'Ext.data.reader.Json'
    ],

    stores: {
    	questionLists: {type: 'questionGridStroe'}
    }
});
