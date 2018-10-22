Ext.define('Admin.view.leaveapprove.LeaveApproveWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.leaveApproveWindow',
	autoShow: true,
    height: 400,
    minHeight: 350,
    minWidth: 300,
    width: 550,
    scrollable: true,
    title: 'leaveApproveWindow',
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
			name: 'employeeName',
			fieldLabel: '职员名称',
			//value:loginUser,
			allowBlank: false,
			readOnly: true
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
			readOnly: true,
			allowBlank: false
		},{
			xtype: 'datefield',
			fieldLabel: '请假开始时间',
			format: 'Y/m/d H:i:s', 
			name: 'startTime',
			readOnly: true
		},{
			xtype: 'datefield',
			fieldLabel: '请假结束时间',
			format: 'Y/m/d H:i:s', 
			name: 'endTime',
			readOnly: true
		},{
			xtype: 'datefield',
			fieldLabel: '创建时间',
			format: 'Y/m/d H:i:s', 
			name: 'applyTime',
			readOnly: true
		},{
			xtype     : 'textareafield',
			grow      : true,
			name      : 'reason',
			fieldLabel: '请假原因',
			anchor    : '100%',
			readOnly: true
		}]
    }],
   buttons: ['->',{
        xtype: 'button',
        text: 'Submit',
        handler: 'submitApproveForm'
    },{
        xtype: 'button',
        text: 'Close',
        handler: function(btn) {
            btn.up('window').close();
        }
    },'->']
  
});
