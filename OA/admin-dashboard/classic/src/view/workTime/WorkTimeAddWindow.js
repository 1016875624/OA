Ext.define('Admin.view.workTime.WorkTimeAddWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.workTimeAddWindow',
    height: 500,
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
        ariaLabel: 'Enter your name',
        items: [{
            xtype: 'textfield',
            fieldLabel: '当天上班时间',
            name:'hour'
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
