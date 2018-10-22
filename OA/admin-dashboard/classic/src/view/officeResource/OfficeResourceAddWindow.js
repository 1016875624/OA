Ext.define('Admin.view.officeResource.OfficeResourceAddWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.officeResourceAddWindow',
	autoShow: true,
    height: 350,
    minHeight: 350,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Add OfficeResource Window',
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
            fieldLabel: '数目',
            name:'leftCount',
            value:'500'
        },{
            xtype: 'textfield',
            fieldLabel: '资源名称',
            name:'resourceName'
        },{
			xtype: 'combobox',
			name: 'remark',
			fieldLabel: '用途',
			//vtype: 'email',
			store: Ext.create('Ext.data.Store', {
				fields: ['value', 'name'],
				data : [
					{"value":"0", "name":"抽奖用"},
					{"value":"1", "name":"抢夺用"}
				]
			}),
			queryMode: 'local',
			displayField: 'name',
			valueField: 'value',
			value: '0',
			allowBlank: false
		},{
			xtype: 'datefield',
			fieldLabel: '开始时间',
			format: 'Y/m/d H:i:s', 
			name: 'startTime'
		},{
			xtype: 'datefield',
			fieldLabel: '结束时间',
			format: 'Y/m/d H:i:s', 
			name: 'endTime'
		}]
    }],
	buttons: ['->',{
        xtype: 'button',
        text: 'Submit',
        handler: 'submitAddForm'
    },{
        xtype: 'button',
        text: 'Close',
        handler: function(btn) {
            btn.up('window').close();
        }
    },'->']
});