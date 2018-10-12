
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
    autoShow: true,
    defaultFocus: 'textfield',
    modal:true,
    layout: 'fit',
    items: [{
        xtype: 'form',
        layout: 'form',
        padding: '10px',
        ariaLabel: 'Enter your WorkTime',
        items: [
        {
            xtype: 'textfield',
            fieldLabel: '员工编号',
            name:'employeeid',
            value:'2'
        },{
            xtype: 'combobox',
            fieldLabel: '当天上班时间(单位：H)',
            name:'hour',
            store:Ext.create('Ext.data.Store',{
            	fields:["name"],
            	data:[{"name":"0"},{"name":"1"},{"name":"2"},{"name":"3"},{"name":"4"},{"name":"5"},{"name":"6"},{"name":"7"},
            		{"name":"8"}
            		]
            }),
            editable: false,
            queryMode:'local',
            displayField:'name',
            valueField:'name',
            value:'2'
        }, {
            xtype: 'datefield',
            editable: false,
            fieldLabel: '日期起始',
            format: 'Y/m/d',
            reference:'startofdate',
            emptyText: '起始时间',
            name: 'StartDate',
            value:"2018/10/01"
        },{
        	xtype: 'datefield',
			editable:false,
			format: 'Y/m/d',
			reference:'endofdate',
			fieldLabel: '日期末端',
			emptyText: '末时间',
			name: 'EndDate',
			value:"2018/10/31"
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
