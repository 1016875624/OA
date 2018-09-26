Ext.define('Admin.view.department.DepartmentWindow', {
    extend:'Ext.window.Window',
    //alias:'widget.orderWindow',
	
	requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
		'Ext.form.field.ComboBox',
        'Ext.grid.column.Date'
    ],
	xtype:'departmentWindow',
	autoShow:true,
	modal:true,
	layout:'fit',
	width:600,
	height:450,
	title:'修改部门信息',
	items:[{
		xtype:'form',
		layout:{type:'vbox',align:'stretch'},
		bodyPadding:20,
		scrollable:true,
		default:{
			labelWidth:100,
			labelSeparator:''
		},
		defaultType:'textfield',
		items:[{fieldLabel: 'id', name: 'id',hidden:true},
			{fieldLabel: 'orderNo', name: 'orderNo'},
			{xtype:'datefield',format: 'Y-m-d H:i:s',fieldLabel: 'orderDate', name: 'orderDate'}
		],
		
		
	
		}],
	
	buttons:['->',{
		id:'departmentWindowSave',
		text:'save',
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
