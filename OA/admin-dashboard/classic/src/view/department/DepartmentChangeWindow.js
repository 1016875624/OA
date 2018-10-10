Ext.define('Admin.view.department.DepartmentChangeWindow', {
    extend:'Ext.window.Window',
    //alias:'widget.orderWindow',
	
	requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
		'Ext.form.field.ComboBox',
		'Ext.layout.container.Column',
        'Ext.grid.column.Date',
        'Ext.layout.container.Center'
    ],
	xtype:'departmentChangeWindow',
	autoShow:true,
	title:'部门人员管理',
	width: 600,
    height: 500,
    buttonAlign:'center',
    //labelAlign:'right',
    frame:true,

	items:  [
				{	
		        layout:'column',
				items: [
						{
						//layout:'form',
						//columnWidth:.40,
						columnWidth: .4,
						margin: '10 0 0 10',
				    	items:[
				    			{
								xtype:"departmentcombobox",
						    	reference:'departmentBox',
						    	name:'departmentid'
				    			},
				    			{
			    				xtype: 'gridpanel',
			    	            bind: '{employeeLists}',
			    	            scrollable: false,
			    	            selModel: {type: 'checkboxmodel'},
			    	            handler: 'quickSearch1',
			    	            columns: [
			    	            	{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'employeesids',text: '员工编号',flex: 1},
			    	            	{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'employeesName',text: '员工姓名',flex: 1}
			    	            ]
			    	            }]
						},
						
			        	{	
			        	layout: 'center',
			        	//columnWidth:.10,
			        	columnWidth: .1,
			        	buttons:[{
			        			iconCls: 'fa fa-arrow-left',
				                handler:function(){}
				                }]
					    },
					    
					    {	
			        	layout: 'center',
			        	//columnWidth:.10,
			        	columnWidth: .1,
			        	buttons:[{
			        			iconCls: 'fa fa-arrow-right',
				                handler:function(){}
				                }]
					    },
					    
					    {
				    	//layout:'form',
						//columnWidth:.40,
					    columnWidth: .4,
						margin: '10 10 0 0',
				    	items:[
				    			{
								xtype:"departmentcombobox",
						    	reference:'departmentBox',
						    	name:'departmentid'
				    			},
				    			{
			    				xtype: 'gridpanel',
			    	            bind: '{employeeLists}',
			    	            scrollable: false,
			    	            selModel: {type: 'checkboxmodel'},
			    	            columns: [
			    	            	{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'employeesids',text: '员工编号',flex: 1},
			    	            	{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'employeesName',text: '员工姓名',flex: 1}
			    	            ]
			    	            }]
				        }
					    ]
		}
	    ],
	
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
