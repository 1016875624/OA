Ext.define('Admin.store.MemberShipStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'store.membershipstore',
    storeId:"membershipstore",
    root:{},
    listeners:{
        beforeload:function () {
            console.log(111)
        }
    },
});