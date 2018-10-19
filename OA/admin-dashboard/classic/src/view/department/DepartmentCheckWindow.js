Ext.define('Admin.view.employee.DepartmentCheckWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.departmentCheckWindow',
	
	requires: [
        'Ext.toolbar.Paging',
        'Ext.layout.container.VBox'
    ],
	title: '部门人员',
    height: 400,
    width: 300,
    items:	[{
    		region:'center',
    		layout: {type: 'vbox',align: 'middle ',pack: 'center'},
			items:	[{
					xtype: 'gridpanel',//显示面板
					reference:'checkPanel',//
		            bind: '{employeesList}',//绑定员工信息
		            scrollable: true,//可下拉
		            //selModel: {type: 'checkboxmodel'},//多选框
		            columns: [
		            	{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'id',text: '员工编号',flex: 1},
		            	{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'name',text: '员工姓名',flex: 1}
		            ]
		            },{
		            dockedItems: [{
		                xtype: 'pagingtoolbar',
		                dock: 'bottom',
		                displayInfo: true,
		                bind: '{employeesList}'//分页工具绑定员工信息
		            }]
		            }]
    		}]
});