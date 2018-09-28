Ext.define('Admin.view.employee.EmployeeSearchWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.employeeSearchWindow',
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
				xtype: 'textfield',
				fieldLabel: '员工编号',
				name:'id'
			}, {
				xtype: 'combobox',
		           fieldLabel: '所属部门',
		           name:'departmentid',
		           store:Ext.create("Ext.data.Store", {
					    fields: ["id", "name"],
					   	proxy: {
					        type: 'ajax',
					        url:'http://localhost:8080/department/simpleget',
						    reader:{
						    	type:'json',
						    },
					    }
					   	,
					   	autoLoad: 'true',
						autoSync:'true',
					}),
					displayField:'name',
					valueField:'id',
					editable:false,
					queryMode: 'local',
					triggerAction: 'all',
					//emptyText: 'Select a state...',
					listeners:{
					
					}
			}, {
				xtype: 'datefield',
				fieldLabel: '入职时间',
				format: 'Y/m/d',
				name: 'entryTime'
				
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