Ext.define('Admin.view.quit.quitApproval.QuitApproval', {
    extend: 'Ext.container.Container',
    xtype: 'quitApproval',
    //requires: [],
    //controller: 'order',				//viewController:代码与视图分离。声明视图绑定的事件，可以多个视图共享。
    //viewModel: {type: 'orderlist'},	//viewModel：配置Stote数据源。多个视图共享Store。
    
    controller: 'quitViewController',
    viewModel: {type: 'quitApprovalViewModel'},
    	
    layout: 'fit',
    items: [{xtype:'quitApprovalPanel'}]
    //html:'订单管理模块'
})