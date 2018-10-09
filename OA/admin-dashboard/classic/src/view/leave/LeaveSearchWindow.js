Ext.define('Admin.view.leave.LeaveSearchWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.leaveSearchWindow',
    height: 300,
    minHeight: 300,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Search More Window',
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
			xtype: 'combobox',
			store:Ext.create("Ext.data.Store", {
				fields: ["name", "value"],
				data: [{ name: '所有', value: '' }
						,{ name: '已删除', value: '-1' }
						,{ name: '待申请', value: '0' }
						,{ name: '待审批', value: '1' }
						,{ name: '审批通过', value: '2' }
						,{ name: '已销假', value: '3' }
				]
			}),
			displayField: 'name',
			valueField:'value',
			value:'',
			editable: false,
			queryMode: 'local',
			triggerAction: 'all',
			width: 135,
			fieldLabel: 'status',
			name: 'status'
		},{
			xtype: 'datefield',
			format: 'Y/m/d H:i:s',
			fieldLabel: 'From',
			name: 'startTime'
		}, {
			xtype: 'datefield',
			format: 'Y/m/d H:i:s',
			fieldLabel: 'To',
			name: 'endTime'
		}]
    }],
	buttons: ['->',{
        xtype: 'button',
        text: 'Submit',
        handler: 'submitSearchForm'
    },{
        xtype: 'button',
        text: 'Close',
        handler: function(btn) {
            btn.up('window').close();
        }
    },'->']
});