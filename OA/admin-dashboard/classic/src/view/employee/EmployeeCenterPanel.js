Ext.define('Admin.view.employee.EmployeeCenterPanel', {
    extend: 'Ext.container.Container',
    xtype: 'employeeCenterPanel',
    //requires: [],
    //controller: 'employee',				//viewController:代码与视图分离。声明视图绑定的事件，可以多个视图共享。
    //viewModel: {type: 'employeelist'},	//viewModel：配置Stote数据源。多个视图共享Store。
    
    controller: 'employeeViewController',
    viewModel: {type: 'employeeViewModel'},
    	
    layout: 'fit',
    items: [{xtype:'employeeGridPanel'}]
});