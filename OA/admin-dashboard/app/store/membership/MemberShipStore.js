Ext.define('Admin.store.MemberShipStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'store.membershipstore',
    storeId:"membershipstore",
    root:{},
    //autoLoad:true,
    // listeners:{
    //     beforeload:function () {
    //         console.log(111)
    //     },
    //     add:function () {
    //       console.log(123)
    //     },
    //     load:function () {
    //         console.log("111")
    //     }
    // },
});