Ext.define('Aria.view.salary.SalaryEditWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.salaryEditWindow',
    //height: 500,
    minHeight: 100,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Edit Salary Window',
    closable: true,
    constrain: true,
    defaultFocus: 'textfield',
    modal: true,
    layout: 'fit',
    autoShow:true,
    items: [{
        xtype: 'form',
        layout: 'form',
        padding: '10px',
        ariaLabel: 'Enter your name',
        items: [
            {
                xtype: 'textfield',
                fieldLabel: 'id',
                name: 'id',
                hidden: true,
                readOnly: true
            }, {
                xtype: 'textfield',
                fieldLabel: '员工工号',
                name: 'employeeid',
                disabled: true,
                readOnly: true,
            },{
                xtype: 'textfield',
                fieldLabel: '员工姓名',
                name: 'employeeName',
                disabled: true,
                readOnly: true,
            },
            {
                xtype: 'numberfield',
                fieldLabel: '工资',
                name: 'sal',
            },
            {
                xtype: 'numberfield',
                fieldLabel: '奖金',
                name: 'bonus',
            },
            {
                xtype: 'numberfield',
                fieldLabel: '工龄',
                name: 'workMonth',
                disable: true,
            },
            {
                xtype: 'numberfield',
                fieldLabel: '工龄工资',
                name: 'worktimeMoney',
            },
            {
                xtype: 'numberfield',
                fieldLabel: '补贴',
                name: 'subsidy',
            },

        ]
    }],
    buttons: ['->', {
        xtype: 'button',
        text: 'Submit',
        handler: 'submitEditForm'
    }, {
        xtype: 'button',
        text: 'Close',
        handler: function (btn) {
            btn.up('window').close();
        }
    }, '->']

});
