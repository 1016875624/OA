﻿Ext.define('Admin.view.officeResource.OfficeResourceViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.officeResourceViewModel',
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
        leaveLists: {type: 'officeResourceStore'}
    }
});
