package com.alonebums.project.db;

/**
 * Created by admin on 2016/11/8.
 */

public class DBTable {

    public static final String TABLE_NAME = "db_table";

    public static final String ID = "_id";
    public static final String NAME = "name";

    public static final String CREATE_TABLE_SQL = new StringBuffer()
            .append("create table if not exists ")
            .append(TABLE_NAME)
            .append("(")
            .append(ID).append(" integer primary key autoincrement,")
            .append(NAME).append(" text")
            .append(")").toString();

    //创建索引
    public static final String CREATE_INDEX_NAME_SQL = new StringBuffer()
            .append("create index ")
            .append(TABLE_NAME).append("_").append(NAME).append("_index")
            .append(" on ").append(TABLE_NAME)
            .append("(")
            .append(NAME)
            .append(")").toString();

    //删除表
    public static final String DROP_TABLE_SQL = "drop table " + TABLE_NAME;
}
