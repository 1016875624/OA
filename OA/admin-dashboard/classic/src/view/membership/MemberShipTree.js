Ext.define('Admin.view.membership.MemberShipTree', {
    extend: 'Ext.panel.Panel',
    xtype: 'membershipTree',
    //controller: 'membershipcontroller',
    reference:"membershipTree",
    requires: [
       // 'Ext.d3.chart.Org',
        //'Admin.store.MemberShipStore',
        //'Admin.store.MemberShipLocalStore',
        //'Ext.d3.hierarchy.tree.HorizontalTree'
        "Ext.d3.interaction.PanZoom",
    ],

    width: 1200,
    height: 700,

    layout: 'fit',

    items: {
        xtype: 'panel',
        layout: 'fit',

        title: 'Sencha Org Chart',

        items: {
            xtype: 'membershipChart',
            reference:"membershipChart",
            interactions: {
                type: 'panzoom',
                zoom: {
                    extent: [0.5, 2],
                    doubleTap: false
                }
            },

            tooltip: {
                renderer: 'onTooltip'
            },

            nodeSize: [200, 100],

            imagePath: 'http://localhost:8080/images/employee/1.png',

//             store: Ext.create("Ext.data.TreeStore",{
//                 storeId:"test",
//                 root:{},
// //root:{"departmentName":"测试部","departmentid":"test","picture":"4.png","expanded":true,"password":"user1","children":[{"departmentName":"管理员","expanded":true,"password":"admin","leaderName":"员工1号","children":[],"departmentid":"admin","name":"我是管理员","id":"admin","picture":"1.png","leaderid":"user1"},{"departmentName":"测试部","expanded":true,"password":"user2","leaderName":"员工1号","children":[{"departmentName":"测试部","expanded":true,"password":"user6","leaderName":"员工2号","children":[],"departmentid":"test","name":"员工6号","id":"user6","leaderid":"user2"}],"departmentid":"test","name":"员工2号","id":"user2","email":"499859073@qq.com","picture":"11.png","leaderid":"user1"},{"departmentName":"测试部","expanded":true,"password":"user3","leaderName":"员工1号","children":[],"departmentid":"test","name":"员工3号","id":"user3","picture":"9.png","leaderid":"user1"},{"departmentName":"测试部","expanded":true,"password":"user4","leaderName":"员工1号","children":[],"departmentid":"test","name":"员工4号","id":"user4","picture":"18.png","leaderid":"user1"},{"departmentName":"测试部","expanded":true,"password":"user5","leaderName":"员工1号","children":[],"departmentid":"test","name":"员工5号","id":"user5","leaderid":"user1"}],"name":"员工1号","id":"user1","position":"经理","email":"499859073@qq.com"},
// })

            store:{
                type: 'membershipstore',
            }
        }
    },
    // listeners:{
    //     beforerender:"beforerender",
    //     //beforeshow:"beforeshow",
    // }

});