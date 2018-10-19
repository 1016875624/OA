

Ext.define('Admin.view.membership.MemberShipController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.membershipcontroller',
    onTooltip: function (component, tooltip, node, element, event) {
        var record = node.data,
            name = record.get('name'),
            //title = record.get('title'),
            title = record.get('position'),
            html = '<span style="font-weight: bold">' + name + '</span><br>' + title;
        tooltip.setHtml(html);
    },
    init:function () {
        
        var store =Ext.data.StoreManager.lookup('membershipstore');
        //var local =Ext.data.StoreManager.lookup('membershiplocalstore');
        var tree=this.lookupReference("membershipChart");
        store.load();
        console.log(store);
        //var store =tree.store;
        /*store.setListeners({
            load:function () {
                //console.log(store.getAt(0).get("picture"));
                //tree.imagePath="http://localhost:8080/images/employee/"+store.getAt(0).get("picture");
                datas=new Array();
                var count=0;
                store.each(function (r) {
                    if (count++==0){
                        temp=r.copy();
                        delete temp.leaderid;
                        //datas.push(temp);
                    }
                    else
                    {
                        datas.push(r.copy());
                    }
                });

                store.setData(datas);
                //local.setData(datas);
                console.log(datas);
                console.log(store.getData());
            }
        });*/
    }

    
});