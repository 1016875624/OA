Ext.define('Admin.store.holiday.HolidayGridStroe', {
	extend: 'Ext.data.Store',
	storeId:'holidayGridStroe',
	alias: 'store.holidayGridStroe',
	fields: [
	    {type: 'string',name: 'employeeid'},
		{type: 'string',name: 'employeeName'},
		{type: 'string',name: 'departmentName'},
		{type: 'int',name: 'hour'},
	    {type: 'date', name: 'date', dateFormat:'Y/m/d'},
	    //{type:'date'name:'StartDate'},
	    //{type:'date',name:'EndDate'},
	    {type:'int',name:'status'},
	    {type:'int',name:'ifholiday'}
	],
	/*data:{
		'lists':[]
	},*/
	proxy: {
		type: 'ajax',
		method : 'post', 
		url : 'http://localhost:8080/workTime/savemore', 
		reader:{
			type:'json',
			//rootProperty:'lists',//对应当前date返回的结果集名称
			//totalProperty: 'totalElements'//分页需要知道总记录数
		},
		writer: {
			type: 'json'
		},
		simpleSortMode: true	//简单排序模式
	},
	asynchronousLoad:false,
	autoLoad:false,
	autoSync: false,
	remoteSort: true,//全局(远程)排序
	pageSize: 15,
	sorters: {
		direction: 'DESC',property: 'id'
	},
	listeners:{
		update:function(store, record, operation, modifiedFieldNames, details, eOpts){
//			if(record.getAt("hour")>10||record.getAt("hour<0")){
//				Ext.Msg.alert("提示！","工作时间修改范围为1-10小时");
//			}
			console.log(record);
			console.log(modifiedFieldNames);
			console.log(details);
			if(record.getData().hour>10||record.getData().hour<0){
				
				//record.getData().hour=details.item.modified.hour;
				//delete record.getData().hour;
				//console.log(record.getData().hour);
				//record.getData().hour=details.item.modified.hour;
				//console.log(details.item.modified.hour);
				var array=new Array();			//声明一个数组
				store.each(function(r){   		//遍历store
					if(r.getData().id==record.getData().id){		//判断如果是那条被改变的数据
						r.getData().hour=details.item.modified.hour;
						console.log(r.getData().hour);
						console.log(r.getData());
						
						var temp=r.copy().getData();
						delete temp.hour;
						temp.hour=details.item.modified.hour;
						console.log(temp);
						console.log(temp.hour);
						array.push(temp);
					}
					else{				//如果不是那条被改变的数据
						array.push(r.copy().getData());
					}
				});
				
				store.setData(array);
				
				console.log(record.getData().hour);
				Ext.Msg.alert("提示！","工作时间修改范围为1-10小时");
				return false;
			}
			var array=new Array();
			store.each(function(r){
				array.push(r.copy().getData());
			});
			
			store.setData(array);
			//Ext.Msg.alert("提示！","工作时间修改范围为1-10   aaa小时");
			//return false;
		},
		/*datachanged:function(){
			Ext.Msg.alert("提示！","请按回车保存修改");
		}*/
	}
	
});
