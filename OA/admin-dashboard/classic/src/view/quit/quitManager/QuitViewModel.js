Ext.define('Admin.view.quit.quitManager.QuitViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.quitViewModel',

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
		quitLists: {type: 'quitGridStroe'},
		departmentList:{type:'departmentLoadStroe'},
    }
});
