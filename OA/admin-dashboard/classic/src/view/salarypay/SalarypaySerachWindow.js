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
            {fieldLabel: '离职原因', name: 'reason'},
            {fieldLabel: '员工id', name: 'employeeid'},
            {fieldLabel: '员工姓名', name: 'employeeName'},
            {
                xtype:"departmentcombobox",
                fieldLabel: '部门', name: 'departmentid'
            },
            {fieldLabel: 'status', name: 'status'},
            {fieldLabel: '申请日期范围', name: 'preApplyDate',xtype:"datefield",format:"Y/m/d H:i:s"},
            {fieldLabel: '申请日期范围', name: 'sufApplyDate',xtype:"datefield",format:"Y/m/d H:i:s"},
            {fieldLabel: '离职日期范围', name: 'preSalarypayDate',xtype:"datefield",format:"Y/m/d H:i:s"},
            {fieldLabel: '离职日期范围', name: 'sufSalarypayDate',xtype:"datefield",format:"Y/m/d H:i:s"},

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
