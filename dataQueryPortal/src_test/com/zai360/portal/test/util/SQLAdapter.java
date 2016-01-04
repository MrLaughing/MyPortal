package com.zai360.portal.test.util;
/**
 * 该类型为mapperXML中传入参数
 * 目的：在action中拼接SQL语句
 * @author report
 *
 */
public class SQLAdapter {    
    /**
     * 要拼接的SQL语句
     */
    String sql;      
          
    public SQLAdapter(String sql) {      
        this.sql = sql;      
    }      
      
    public String getSql() {      
        return sql;      
    }      
      
    public void setSql(String sql) {      
        this.sql = sql;      
    }      
    
}  