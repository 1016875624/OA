Ext.define('Admin.view.department.DepartmentChangeWindow', {
    extend:'Ext.window.Window',
    //alias:'widget.orderWindow',
	
	requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
		'Ext.form.field.ComboBox',
		'Ext.layout.container.Column',
        'Ext.grid.column.Date'
    ],
	xtype:'departmentChangeWindow',
	autoShow:true,
//	modal:true,
	title:'部门人员管理',
	width: 600,
    height: 500,
    layout: 'column',
    //bodyPadding: 5,
//    defaults: {
//        bodyPadding: 15
//    },
    //width: 600,
    //layout: 'column',
    viewModel: true,

    bodyStyle: "background: transparent",

    defaults: {
        bodyPadding: 10,
        height: 300,
        scrollable: true
    },
    
	items:  [
				{	
				/*width: 200,
		        title: '员工部门',
				xtype:"departmentcombobox",
		    	reference:'departmentBox',
		    	name:'departmentid',
				html: '<p>这里显示部门人员.</p>'
		        margin: "10 0 0 5",*/
				//columnWidth: 0.5,
				xtype: 'layout-center',
				layout: 'center',
		        frame: true,
		        title: '当前部门人员',
		        icon: null,
		        height: 400,
		        //glyph: 117,
				items: [
					{
			        width: 200,
			        title: '这里显示部门人员',
					xtype:"departmentcombobox",
			    	reference:'departmentBox',
			    	name:'departmentid'
		        	}, 
		        	{
		            xtype: 'displayfield',
		            html: '<p>这里显示部门人员.</p>'
		        	}] 
		    },
		    {	
		    	width: 80
		        //title: 'Width = 0.7',
		        //columnWidth: 0.2,
		        //html: '<p>This is some longer content.</p><p>This is some longer content.</p><p>This is some longer content.</p><p>This is some longer content.</p><p>This is some longer content.</p><p>This is some longer content.</p>'
		    },
		    {
		        //title: 'Width = 150px',
		    	frame: true,
		        title: '其他部门人员',
		        icon: null,
		        height: 400,
		        //glyph: 117,
				items: [
					{
			        width: 200,
			        title: '这里显示部门人员',
					xtype:"departmentcombobox",
			    	reference:'departmentBox',
			    	name:'departmentid'
		        	}, 
		        	{
		            xtype: 'displayfield',
		            html: '<p>这里显示部门人员.</p>'
		        	}] 
		    }
		    ],
	
	buttons:['->',{
		id:'departmentWindowSave',
		text:'save',
		handler:function(){
			this.up('window').close();
		}},{
			text:'Cancel',
			handler:function(){
				this.up('window').close();
			}
		},'->'
	]
});
