Ext.define('Admin.view.salarypay.SalarypayWindow', {
    extend:'Ext.window.Window',
    //alias:'widget.orderWindow',
	
	requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
		'Ext.form.field.ComboBox',
        'Ext.grid.column.Date'
    ],
	xtype:'salarypayWindow',
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
            {fieldLabel: '员工姓名', name: 'employeeName'},
            {fieldLabel: '发放日期', name: 'date',xtype:"datefield",format: 'Y/m/d'},
            {fieldLabel: '状态', name: 'status'},
            {fieldLabel: '实际工资', name: 'money',xtype:"numberfield"},
            {fieldLabel: '要求工作时间', name: 'worktime',xtype:"numberfield",allowDecimals:false},
            {fieldLabel: '实际工作时间', name: 'realWorktime',xtype:"numberfield",allowDecimals:false},
            {fieldLabel: '出勤率', name: 'attendRate',xtype:"numberfield"},
		],
		
		
	
		}],
	
	buttons:['->',{
		id:'salarypayWindowSave',
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
