Ext.define('Admin.view.salarypay.SalarypaySearchWindow', {
    extend:'Ext.window.Window',
    //alias:'widget.orderWindow',
	
	requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
		'Ext.form.field.ComboBox',
        'Ext.grid.column.Date'
    ],
	xtype:'salarypaySearchWindow',
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
            {fieldLabel: '员工id', name: 'employeeid'},
            {fieldLabel: '员工姓名', name: 'employeeName'},
            {
                xtype:"departmentcombobox",
                fieldLabel: '部门', name: 'departmentid'
            },
            {fieldLabel: 'status', name: 'status'},
            {fieldLabel: '发放日期范围', name: 'startDate',xtype:"datefield",format:"Y/m/d"},
			//时间格式化,format:"Y/m/d H:i:s"
            {fieldLabel: '发放日期范围', name: 'endDate',xtype:"datefield",format:"Y/m/d H:i:s"},
            {fieldLabel: '工资范围', name: 'preMoney',xtype:"numberfield"},
            {fieldLabel: '工资范围', name: 'sufMoney',xtype:"numberfield"},
            {fieldLabel: '实际工作时间范围(单位 h)', name: 'preRealWorktime',xtype:"numberfield", allowDecimals:false},
            {fieldLabel: '实际工作时间范围(单位 h)', name: 'sufRealWorktime',xtype:"numberfield", allowDecimals:false},
			//禁止小数allowDecimals:false
            {fieldLabel: '应当工作时间范围(单位 h)', name: 'preWorktime',xtype:"numberfield", allowDecimals:false},
            {fieldLabel: '应当工作时间范围(单位 h)', name: 'sufWorktime',xtype:"numberfield", allowDecimals:false},
            {fieldLabel: '出勤率范围', name: 'preAttendRate',xtype:"numberfield"},
            {fieldLabel: '出勤率范围', name: 'sufAttendRate',xtype:"numberfield"},
            //{fieldLabel: '离职日期范围', name: 'preSalarypayDate',xtype:"datefield",format:"Y/m/d H:i:s"},

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
