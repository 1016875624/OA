Ext.define('Admin.view.salary.Salary', {
    extend: 'Ext.container.Container',
    xtype: 'salary',
    //requires: [],
    //controller: 'salary',				//viewController:代码与视图分离。声明视图绑定的事件，可以多个视图共享。
    //viewModel: {type: 'salarylist'},	//viewModel：配置Stote数据源。多个视图共享Store。
    
    controller: 'salaryViewController',
    viewModel: {type: 'salaryViewModel'},
    	
    layout: 'fit',
    items: [{xtype:'salaryPanel'}]
    //html:'订单管理模块'
});
