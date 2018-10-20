

Ext.define('Admin.view.membership.MemberShipController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.membershipcontroller',
    onTooltip: function (component, tooltip, node, element, event) {
        var record = node.data,
            name = record.get('name'),
            //title = record.get('title'),
            title = record.get('position'),
            id=record.get("id");
            departmentName=record.get("departmentName");
            //html = '<span style="font-weight: bold">' + name + '</span><br>' + title;
            html = '<span style="font-weight: bold">'+id+'<br>'+departmentName+'<br>' + name + '</span><br>' + title+'<br>';
        tooltip.setHtml(html);
    },
    init:function () {
        var membershipTree=this.lookupReference("membershipChart");
        var store=Ext.data.StoreManager.lookup('membershipstore');

        console.log(store.getRoot());
        Ext.Ajax.request({
            url: 'http://localhost:8080/employee/user9',
            async:false,
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                store.setRoot(obj.root);
                //window.datas=obj.root;
            }
        });

        membershipTree.setStore(store);
        membershipTree.up("container").updateLayout();
        // var membershipTree=this.lookupReference("membershipChart");
        // var store=Ext.data.StoreManager.lookup('membershipstore');
        // store.setRoot(window.datas);
        // membershipTree.setStore(store);
        // membershipTree.up("container").updateLayout();
        // console.log(store.getRoot());
    },
    beforeRender:function () {
        // var tree=this.lookupReference("membershipChart");
        // var store=tree.getStore();
        // Ext.Ajax.request({
        //     url: 'http://localhost:8080/employee/user9'
        // }).then(function(response, opts) {
        //         var obj = Ext.decode(response.responseText);
        //         console.dir(obj);
        //         store.setRoot(obj.root);
        //     },
        //     function(response, opts) {
        //         console.log('server-side failure with status code ' + response.status);
        //     });
    },
    beforerender:function () {
        // var tree=this.lookupReference("membershipChart");
        // var store=tree.getStore();
        // Ext.Ajax.request({
        //     url: 'http://localhost:8080/employee/user9'
        // }).then(function(response, opts) {
        //         var obj = Ext.decode(response.responseText);
        //         console.dir(obj);
        //         store.setRoot(obj.root);
        //     },
        //     function(response, opts) {
        //         console.log('server-side failure with status code ' + response.status);
        //     });
    }


    ,add111:function () {
        var panel=Ext.create("Ext.window.Window",{
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

                //store:store,
            }
        });
        membershipTree.add(panel);
        panel.show();
    }

});