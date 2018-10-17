Ext.define('Admin.view.salarypay.SalarypayViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.salarypayViewModel',

    requires: [
        'Ext.data.Store',
        'Ext.data.proxy.Memory',
        'Ext.data.field.Integer',
        'Ext.data.field.String',
        'Ext.data.field.Date',
        'Ext.data.field.Boolean',
        'Ext.data.reader.Json'
    ],
    data:{
        startDate:"2018/09/01",
        endDate:"2018/09/30"
    },


    stores: {
		salarypayLists: {type: 'salarypayGridStroe'},
		departmentList:{type:'departmentLoadStroe'},
        workOverTimeList:{type:"workOverTimeStore"},
    }
});
