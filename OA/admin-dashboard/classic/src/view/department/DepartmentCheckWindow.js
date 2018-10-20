Ext.define('Admin.view.employee.DepartmentCheckWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.departmentCheckWindow',
	
	requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
		'Ext.form.field.ComboBox',
		'Ext.layout.container.Column',
        'Ext.grid.column.Date',
        'Ext.layout.container.Border',
        'Ext.layout.container.HBox'
    ],
    viewModel: {type: 'departmentViewModel'},
	xtype:'departmentChangeWindow',
	autoShow:true,
	title:'部门人员管理',
	width: 850,
    height: 550,
	items:  [{	
	        layout:'column',
			columnWidth: 1,
			margin: '10 0 0 0',
	    	items:[{
						xtype:"departmentcombobox",//调用部门列表下拉框
				    	reference:'dpartmentBox',
				    	name:'departmentid',
				    	listeners: {
									change:'departmentSelectChange'//监听部门选择事件
								   }
	    			},
	    			{
	    				xtype: 'gridpanel',//显示面板
	    				reference:'loadingPanel',//
	    	            bind: '{employeesList}',//绑定员工信息
	    	            scrollable: true,//可下拉
	    	            selModel: {type: 'checkboxmodel'},//多选框
	    	            columns: [
	    	            	{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'id',text: '员工编号',flex: 1},
	    	            	{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'name',text: '员工姓名',flex: 1}
	    	            ]
    	            },
    	            {
	    	            dockedItems: [{
	    	                xtype: 'pagingtoolbar',
	    	                dock: 'bottom',
	    	                displayInfo: true,
	    	                bind: '{employeesList}'//分页工具绑定员工信息
	    	            }]
    	            }]
			}],
});