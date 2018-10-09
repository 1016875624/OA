Ext.define('Admin.view.quit.quitManager.QuitWindow', {
    extend:'Ext.window.Window',
    //alias:'widget.orderWindow',
	
	requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
		'Ext.form.field.ComboBox',
        'Ext.grid.column.Date'
    ],
	xtype:'quitWindow',
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
		items:[
            {fieldLabel: 'id', name: 'id',hidden:true},
            {fieldLabel: '员工id', name: 'employeeid'},
            {fieldLabel: '申请日期', name: 'applyDate',xtype:"datefield",format: 'Y/m/d H:i:s'},
            {fieldLabel: '离职原因', name: 'reason'},
            {fieldLabel: '离职日期', name: 'quitDate',xtype:"datefield",format: 'Y/m/d H:i:s'},
            {fieldLabel: '状态', name: 'status',xtype:'numberfield',allowDecimals:false},
		],
		
		
	
		}],
	
	buttons:['->',{
		id:'quitWindowSave',
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
