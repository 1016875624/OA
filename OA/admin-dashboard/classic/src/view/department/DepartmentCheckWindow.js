Ext.define('Admin.view.employee.DepartmentCheckWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.departmentCheckWindow',
	
	title: '部门人员',
	xtype: 'grouped-list',
    height: 400,
    width: 300,
    indexBar: true,
    grouped: true,
    pinHeaders: false,
    //store: 'List'
});