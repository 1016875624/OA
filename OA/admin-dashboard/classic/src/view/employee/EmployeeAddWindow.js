Ext.define('Admin.view.employee.EmployeeAddWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.employeeAddWindow',
    autoShow: true,
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
            allowBlank : false,
            name:'id',
            html: '<font color="red">*</font>'
        }, {
            xtype: 'textfield',
            fieldLabel: '员工姓名',
            allowBlank : false,
            name:'name'
        }, {
			fieldLabel: '员工部门',
			allowBlank : false,
			xtype:"departmentcombobox",
	    	reference:'departmentBox',
	    	name:'departmentid'
	    	//hidden:true
        }, {
            xtype: 'textfield',
            fieldLabel: '员工邮箱',
            name:'email'
        }, {
            xtype: 'textfield',
            fieldLabel: '职位',
            name:'position'
        }, 
       /* {
            xtype: 'combobox',
	        fieldLabel: '在职状态',
	        allowBlank : false,
			name:'status',
			editable:false,
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
        }, */
        {
            xtype: 'textfield',
            fieldLabel: '上级领导',
            name:'leaderid'
        }, {
            xtype: 'datefield',
            fieldLabel: '入职时间',
            allowBlank : false,
            editable:false,
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
