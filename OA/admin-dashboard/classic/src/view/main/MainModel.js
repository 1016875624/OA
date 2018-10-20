Ext.define('Admin.view.main.MainModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.main',

    data: {
        currentView: null
    },
    stores:{
        membershipstore: {type: 'membershipstore'},
    }
});
