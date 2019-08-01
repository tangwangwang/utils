package com.t2w.utils.database.domain;

import lombok.Getter;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-30 09:07
 * @name com.t2w.utils.database.domain.DatabaseType.java
 * @see 数据库类型
 */
@Getter
public enum DatabaseType {

    MYSQL("Mysql"),
    ORACLE("Oracle"),
    SQL_SERVER("SQL Server"),
    SQL_SERVER_2000("SQL Server 2000"),
    POSTGRE_SQL("PostgreSQL"),
    DB2("DB2"),
    ELASTICSEARCH("Elasticsearch"),
    MONGO_DB("MongoDB"),
    REDIS("Redis"),
    ;
    private String name;

    DatabaseType(String name) {
        this.name = name;
    }
}
