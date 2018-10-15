Ext.define('Aria.view.salary.SalaryAddWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.salaryAddWindow',
    autoShow:true,
    height: 430,
    minHeight: 100,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Add Salary Window',
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
        }, {
            xtype: 'textfield',
            fieldLabel: '员工工号',
            name:'employeeid'
        }, {
            xtype: 'textfield',
            fieldLabel: '员工姓名',
            name:'employeeName'
	    }, {
            xtype: 'textfield',
            fieldLabel: '基本工资',
            name:'sal'
        },  {
            xtype: 'textfield',
            fieldLabel: '奖金',
            name:'bonus'
        },  {
            xtype: 'textfield',
            fieldLabel: '工龄',
            name:'workMonth'
        }, {
            xtype: 'textfield',
            fieldLabel: '工龄工资',
            name:'worktimeMoney'
        }, {
            xtype: 'textfield',
            fieldLabel: '补贴',
            name:'subsidy'
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
