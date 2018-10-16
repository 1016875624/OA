Ext.define('Admin.view.department.DepartmentChangeWindow', {
    extend:'Ext.window.Window',
    //alias:'widget.orderWindow',
	
	requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
		'Ext.form.field.ComboBox',
		'Ext.layout.container.Column',
        'Ext.grid.column.Date',
        'Ext.layout.container.Anchor',
        'Ext.layout.container.Border',
        'Ext.layout.container.HBox',
        'Ext.layout.container.Center'
    ],
    viewModel: {type: 'departmentViewModel'},
	xtype:'departmentChangeWindow',
	autoShow:true,
	title:'部门人员管理',
	width: 850,
    height: 550,
	items:  [{	
	        layout:'column',
			items: [{
					//anchor:'40%',
					columnWidth: .43,
					margin: '10 0 0 0',
			    	items:[{
							xtype:"departmentcombobox",//调用部门列表下拉框
					    	reference:'dpartmentBox',
					    	name:'departmentid',
					    	listeners: {
										change:'departmentSelectChange'//监听部门选择事件
									   },
					    	
			    			},{
		    				xtype: 'gridpanel',//显示面板
		    				reference:'loadingPanel',//
		    	            bind: '{employeesList}',//绑定员工信息
		    	            scrollable: true,//可下拉
		    	            selModel: {type: 'checkboxmodel'},//多选框
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
					},
					{
					columnWidth: .14,
					margin: '200 10 0 10',
					region:'center',
					layout: {
					type: 'vbox',
					align: 'middle ',
					pack: 'center'
					},
					items:	[
							{
					    	width:94,
				        	buttons:[{
				        			iconCls: 'fa fa-arrow-right',
				        			tooltip:'移动部门人员',
				        			handler:'rightPush'
					                }]
						    },
							{	
							width:94,
				        	buttons:[{
				        			iconCls: 'fa fa-arrow-left',
				        			tooltip:'移动部门人员',
				        			handler:'leftPull'
					                }]
						    }
							]
					},
				    {
				    //anchor:'40% 80%',
					columnWidth: .43,
					margin: '10 0 0 0',
			    	items:[{
							xtype:"departmentcombobox",//调用部门列表下拉框
					    	reference:'oDpartmentBox',
					    	name:'departmentid',
					    	listeners: {
										change:'oDepartmentSelectChange'//监听部门选择事件
									   }
			    			},{
		    				xtype: 'gridpanel',//显示面板
		    				reference:'oLoadingPanel',
		    	            bind: '{oemployeesList}',//绑定员工信息
		    	            scrollable: true,//可下拉
		    	            selModel: {type: 'checkboxmodel'},//多选框
		    	            columns: [
		    	            	{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'id',text: '员工编号',flex: 1},
		    	            	{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'name',text: '员工姓名',flex: 1}
		    	            ]
		    	            },{
		    	            dockedItems: [{
		    	                xtype: 'pagingtoolbar',
		    	                dock: 'bottom',
		    	                displayInfo: true,
		    	                bind: '{oemployeesList}'//分页工具绑定员工信息
		    	            }]
		    	        }]
				}]
		}],
	
	buttons:['->',{
		id:'departmentWindowSave',
		text:'Save',
		handler:function(){
			this.up('window').close();
		}},{
			text:'Cancel',
			handler:function(){
				this.up('window').close();
			}
		},'->'
	]
});
