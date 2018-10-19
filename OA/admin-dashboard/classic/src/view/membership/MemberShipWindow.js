Ext.define('Aria.view.membership.MemberShipWindow', {
    extend: 'Ext.window.Window',
    xtype: 'membershipWindow',
    //height: 200,
    minHeight: 100,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: '人员关系',
    closable: true,
    constrain: true,
    modal: true,
    autoShow:true,
    layout: 'fit',
    items: [],
    buttons: ['->', {
        xtype: 'button',
        text: '确定',
        handler: function (btn) {
            btn.up('window').close();
        },
    }, {
        xtype: 'button',
        text: 'Close',
        handler: function (btn) {
            btn.up('window').close();
        }
    }, '->']
});
