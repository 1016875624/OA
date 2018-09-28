
Ext.define('Admin.view.workTime.WorkTimeAddWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.workTimeAddWindow',
    height: 450,
    minHeight: 100,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Add workTime Window',
    closable: true,
    constrain: true,
    
    defaultFocus: 'textfield',
    modal:true,
    layout: 'fit',
    items: [{
        xtype: 'form',
        layout: 'form',
        padding: '10px',
        ariaLabel: 'Enter your WorkTime',
        items: [{
            xtype: 'textfield',
            fieldLabel: 'id',
            name:'id',
            hidden: true,
            readOnly: true
        },{
            xtype: 'textfield',
            fieldLabel: '员工编号',
            name:'employeeid'
        },{
            xtype: 'combobox',
            fieldLabel: '当天上班时间(单位：H)',
            name:'hour',
            store:Ext.create('Ext.data.Store',{
            	fields:["name"],
            	data:[{"name":"0"},{"name":"1"},{"name":"2"},{"name":"3"},{"name":"4"},{"name":"5"},{"name":"6"},{"name":"7"},
            		{"name":"8"},{"name":"9"},{"name":"10"},{"name":"11"},{"name":"12"},{"name":"13"},{"name":"14"},{"name":"15"},
            		{"name":"16"},{"name":"17"},{"name":"18"},{"name":"19"},{"name":"20"},{"name":"21"},{"name":"22"},{"name":"23"},
            		{"name":"24"}
            		]
            }),
            queryMode:'local',
            displayField:'name',
            valueField:'name'
            
        }, {
            xtype: 'datefield',
            fieldLabel: '日期',
            name:'date',
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
