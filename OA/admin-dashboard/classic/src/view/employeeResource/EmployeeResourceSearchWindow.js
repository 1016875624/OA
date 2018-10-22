Ext.define('Admin.view.employeeResource.EmployeeResourceSearchWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.employeeResourceSearchWindow',
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
						,{ name: '交易中', value: '0' }
						,{ name: '已拥有', value: '1' }
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
			xtype: 'datefield',
			format: 'Y/m/d H:i:s',
			fieldLabel: 'From',
			name: '最近改变时间'
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