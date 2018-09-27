Ext.define('Admin.view.department.DepartmentSearchWindow', {
    extend:'Ext.window.Window',
    //alias:'widget.orderWindow',
	
	requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
		'Ext.form.field.ComboBox',
        'Ext.grid.column.Date'
    ],
	xtype:'departmentSearchWindow',
	autoShow:true,
	modal:true,
	layout:'fit',
	width:600,
	height:450,
	title:'高级查询',
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
		items:[
			{fieldLabel: 'id', name: 'id'},
			{fieldLabel: 'name', name: 'name'}
			/*{xtype:'datefield',format: 'Y/m/d H:i:s',fieldLabel: 'startDate', name: 'startDate'}
			,{xtype:'datefield',fieldLabel: 'endDate', name: 'endDate',format: 'Y/m/d H:i:s'},*/
		],
	
		}],
	
	buttons:['->',{
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
