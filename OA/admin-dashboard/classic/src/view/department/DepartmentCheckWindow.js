Ext.define('Admin.view.employee.DepartmentCheckWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.departmentCheckWindow',
	
	requires: [
		'Ext.toolbar.Paging'
    ],
    modal:true,
    autoShow:true,
    width: 650,
    height: 700,
    title: '查看部门人员',
    scrollable: true,
    layout:'fit',
    items:[{
			xtype: 'gridpanel',//显示面板
			border:false,
            bind: '{employeesList}',//绑定员工信息
            //selModel: {type: 'checkboxmodel'},//多选框
            columns: [
            	{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'id',text: '员工编号',flex: 1},
            	{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'name',text: '员工姓名',flex: 1}
            ],
            dockedItems: [{
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                displayInfo: true,
                bind: '{employeesList}'//分页工具绑定员工信息
            }]
    }]
});