Ext.define('Admin.view.workTime.WorkTimeViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.workTimeViewModel',
    
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
    	workTimeLists: {type: 'workTimeGridStroe'},
		departmentList:{type:'departmentLoadStroe'},
		workTimes:{type:'holidayGridStroe'}
    }
});
