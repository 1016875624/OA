Ext.define('Admin.store.MemberShipStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'store.membershipstore',
    storeId:"membershipstore",
    config: {
        rootVisible: true
    },
    defaultRootProperty:"leaderid",
    defaultRootId:"user1",
    parentIdProperty:"leaderid",

    autoLoad: 'true',
    autoSync:false,
    //remoteSort: false,
    pageSize: 100,
    // sorters: {
    //     direction: 'DESC',
    //     property: 'overHours'
    // },

    fields: [
        {type: 'string',name: 'id'},
        {type: 'string',name: 'name'},
        {type: 'string',name: 'departmentName'},
        {type: 'string',name: 'email'},
        {type: 'string',name: 'position'},
        {type: 'int',name: 'state'},
        {type: 'string',name: 'picture'},
        {type: 'string',name: 'leaderName'},
        {type: 'string',name: 'leaderid'},
        {type: 'string',name: 'departmentid'},
        {type: 'date', name: 'entryTime', dateFormat:'Y/m/d H:i:s'}
    ],
    proxy: {
        type: 'rest', //类型为依赖
        url: 'http://localhost:8080/employee',
        reader:{
            type:'json',
            //rootProperty:'root',
            //totalProperty:'totalElements'
        },
    },
    /*listeners:{
        add : function( store, records, index, eOpts ){
            Ext.Ajax.request({
                url: 'http://localhost:8080/employee/user1'
            }).then(function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    console.dir(obj);
                    this.setRoot(obj);
                },
                function(response, opts) {
                    console.log('server-side failure with status code ' + response.status);
                })
        },
        load:function () {
            Ext.Ajax.request({
                url: 'http://localhost:8080/employee/user1'
            }).then(function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    console.dir(obj);
                    this.setRoot(obj);
                },
                function(response, opts) {
                    console.log('server-side failure with status code ' + response.status);
                })
        }
    },*/
    /*root: {
        name: 'Cliff Capote',
        title: 'CEO',
        url: '1.jpg',
        expanded: true,
        children: [
            {
                name: 'Rina Hohn',
                title: 'Vice President, Engineering',
                url: '4.jpg',
                expanded: true,
                children: [{
                    name: 'Edgardo Elvin',
                    title: 'Director of Engineering',
                    url: '2.jpg',
                    expanded: true,
                    children: [
                        {
                            name: 'Martin Patlan',
                            title: 'Sr. Software Architect',
                            url: '13.jpg'
                        },
                        {
                            name: 'Paola Motes',
                            title: 'Sr. Software Engineer',
                            url: '8.jpg'
                        },
                        {
                            name: 'Angelo Aden',
                            title: 'Sr. Software Engineer',
                            url: '12.jpg'
                        },
                        {
                            name: 'Christina Timmins',
                            title: 'Sr. Software Engineer',
                            url: '14.jpg'
                        },
                        {
                            name: 'Derrick Curtsinger',
                            title: 'Software Engineer',
                            url: '15.jpg'
                        }
                    ]
                },  {
                    name: 'Freda Mcmurray',
                    title: 'Sr. Engineering Manager',
                    url: '9.jpg'
                }]
            },
            {
                name: 'Ramiro Frew',
                title: 'Vice President, Marketing',
                url: '5.jpg',
                expanded: true,
                children: [
                    {
                        name: 'Conrad Yohe',
                        title: 'Sr. Director, Product Management',
                        url: '3.jpg'
                    }
                ]
            },
            {
                name: 'Marita Meserve',
                title: 'Senior Vice President and Chief Financial Officer',
                url: '7.jpg'
            },
            {
                name: 'Tory Orban',
                title: 'Vice President, Global Alliances & Professional Services',
                url: '10.jpg',
                expanded: true,
                children: [
                    {
                        name: 'Jarred Lasky',
                        title: 'Principal Architect',
                        url: '11.jpg'
                    }
                ]
            }
        ]
    }*/
});