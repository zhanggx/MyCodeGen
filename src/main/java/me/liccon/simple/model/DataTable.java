package me.liccon.simple.model;

import me.liccon.simple.util.StringUtils;

public class DataTable {
	private String tableName;
	private String tableComment;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
}
