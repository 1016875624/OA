Ext.define('Admin.view.officeResource.OfficeResourceSearchWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.officeResourceSearchWindow',
	autoShow: true,
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
						,{ name: '待发起', value: '0' }
						,{ name: '可抢夺', value: '1' }
						,{ name: '可抽奖', value: '3' }
				]
			}),
			displayField: 'name',
			valueField:'value',
			value:'',
			editable: false,
			queryMode: 'local',
			triggerAction: 'all',
			width: 135,
			fieldLabel: '状态',
			name: 'status'
		},{
			xtype: 'combobox',
			store:Ext.create("Ext.data.Store", {
				fields: ["name", "value"],
				data: [{ name: '所有', value: '' }
						,{ name: '抽奖用', value: '0' }
						,{ name: '抢夺用', value: '1' }
				]
			}),
			displayField: 'name',
			valueField:'value',
			value:'',
			editable: false,
			queryMode: 'local',
			triggerAction: 'all',
			width: 135,
			fieldLabel: '用途',
			name: 'remark'
		},{
			xtype: 'datefield',
			format: 'Y/m/d H:i:s',
			fieldLabel: 'From',
			name: '开始时间'
		}, {
			xtype: 'datefield',
			format: 'Y/m/d H:i:s',
			fieldLabel: 'To',
			name: '结束时间'
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