﻿Ext.define('Admin.view.leave.LeaveEditWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.leaveEditWindow',
	autoShow: true,
    height: 350,
    minHeight: 350,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Edit Leave Window',
    closable: true,
    constrain: true,
    defaultFocus: 'textfield',
    modal:true,
    layout: 'fit',
	items: [{
        xtype: 'form',
        layout: 'form',
        padding: '10px',
        ariaLabel: 'Enter your name',
        items: [{
            xtype: 'textfield',
            fieldLabel: 'id',
            name:'id',
            hidden: true,
            readOnly: true
        },{
            xtype: 'textfield',
            fieldLabel: 'status',
            name:'status',
            value:'0',
            hidden: true,
            readOnly: true
        },{
			xtype: 'textfield',
			name: 'employeeName',
			fieldLabel: '请假人',
			//value:loginUser,
			allowBlank: false
		},{
			xtype: 'combobox',
			name: 'leaveType',
			fieldLabel: '请假类型',
			//vtype: 'email',
			store: Ext.create('Ext.data.Store', {
				fields: ['value', 'name'],
				data : [
					{"value":"A", "name":"带薪假期"},
					{"value":"B", "name":"无薪假期"},
					{"value":"C", "name":"病假"}
				]
			}),
			queryMode: 'local',
			displayField: 'name',
			valueField: 'value',
			allowBlank: false
		},{
			xtype: 'datefield',
			fieldLabel: '请假开始时间',
			format: 'Y/m/d H:i:s', 
			name: 'startTime'
		},{
			xtype: 'datefield',
			fieldLabel: '请假结束时间',
			format: 'Y/m/d H:i:s', 
			name: 'endTime'
		},{
			xtype     : 'textareafield',
			grow      : true,
			name      : 'reason',
			fieldLabel: '请假原因',
			anchor    : '100%'
		}]
    }],
   buttons: ['->',{
        xtype: 'button',
        text: 'Submit',
        handler: 'submitEditForm'
    },{
        xtype: 'button',
        text: 'Close',
        handler: function(btn) {
            btn.up('window').close();
        }
    },'->']
  
});
