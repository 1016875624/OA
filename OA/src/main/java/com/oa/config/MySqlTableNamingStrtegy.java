package com.oa.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class MySqlTableNamingStrtegy extends PhysicalNamingStrategyStandardImpl {
	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1L;
	//这里设置了mysql区分大小写
	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
//		String tableName = name.getText().toUpperCase();
		String tableName = name.getText();
		//return super.toPhysicalTableName(name, context);
		return Identifier.toIdentifier(tableName);
	}

}
