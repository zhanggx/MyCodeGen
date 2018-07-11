package me.liccon.simple.mapper;

import me.liccon.simple.model.DataTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataTableMapper {
    List<DataTable> selectAll(@Param("dbName") String dbName, @Param("groupPrefix") String groupPrefix);

    List<DataTable> selectByNames(@Param("dbName") String dbName, @Param("tableNames") String[] tableNames);
}
