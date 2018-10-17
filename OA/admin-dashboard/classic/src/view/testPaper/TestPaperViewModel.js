Ext.define('Admin.view.testPaper.TestPaperViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.testPaperViewModel',

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
    	testPaperLists: {type: 'testPaperGridStroe'},
		paperQuestionLists:{type:'paperQuestionGridStore'}
    }
});
