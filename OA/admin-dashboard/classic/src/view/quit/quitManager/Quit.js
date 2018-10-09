Ext.define('Admin.view.quit.quitManager.Quit', {
    extend: 'Ext.container.Container',
    xtype: 'quit',
    //requires: [],
    //controller: 'order',				//viewController:代码与视图分离。声明视图绑定的事件，可以多个视图共享。
    //viewModel: {type: 'orderlist'},	//viewModel：配置Stote数据源。多个视图共享Store。
    
    controller: 'quitViewController',
    viewModel: {type: 'quitViewModel'},
    	
    layout: 'fit',
    items: [{xtype:'quitPanel'}]
    //html:'订单管理模块'
})