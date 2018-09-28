Ext.define('Admin.view.employee.EmployeeAddWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.employeeAddWindow',
    height: 500,
    minHeight: 100,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Add employee Window',
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
            fieldLabel: '员工编号',
            name:'id'
        }, {
            xtype: 'textfield',
            fieldLabel: '员工姓名',
            name:'name'
        }, {
			fieldLabel: '员工部门',
			xtype:"departmentcombobox",
	    	reference:'departmentBox',
	    	name:'departmentid',
	    	//hidden:true
        }, {
            xtype: 'textfield',
            fieldLabel: '员工邮箱',
            name:'email'
        }, {
            xtype: 'textfield',
            fieldLabel: '职位',
            name:'position'
        }, {
            xtype: 'combobox',
	        fieldLabel: '在职状态',
			name:'status',
            store: Ext.create('Ext.data.Store', {
                fields: ['value', 'name'],
                data: [
				{"value":"0","name":"正常"},
                {"value":"1","name":"离职"},
				{"value":"-1","name":"封禁"}
                ]
                }),
                displayField: 'name',
                valueField: 'value',
        }, {
            xtype: 'textfield',
            fieldLabel: '上级领导',
            name:'leaderName'
        }, {
            xtype: 'datefield',
            fieldLabel: '入职时间',
            name:'entryTime',
            format: 'Y/m/d'
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
