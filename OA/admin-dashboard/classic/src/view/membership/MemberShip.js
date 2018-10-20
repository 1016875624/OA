Ext.define('Admin.view.membership.MemberShip', {
    extend: 'Ext.container.Container',
    xtype: 'membership',
    //requires: [],
    //controller: 'salary',				//viewController:代码与视图分离。声明视图绑定的事件，可以多个视图共享。
    //viewModel: {type: 'salarylist'},	//viewModel：配置Stote数据源。多个视图共享Store。
    requires: [
        'Admin.store.MemberShipStore',
        'Admin.view.main.Main',
        "Ext.d3.interaction.PanZoom",
    ],
    controller: 'membershipcontroller',

    layout: 'fit',
    items: [{xtype:'membershipTree'}]
    //html:'订单管理模块'
});
