package me.liccon.simple.mapper;

import me.liccon.simple.model.DataColumn;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DataColumnMapper {
    List<DataColumn> selectAll(@Param("dbName") String dbName, @Param("tableName") String tableName);
}
