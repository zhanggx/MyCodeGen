package me.liccon.simple.model;

import me.liccon.simple.util.StringUtils;

public class DataColumn {
	private String columnName;
	private int ordinalPosition;
	private String isNullable;
	private String dataType;
	private String columnType;
	private String columnKey;
	private String extra;
	private String columnComment;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnNameFormated() {
		if (columnName.contains("_")){
			String[] strs=columnName.split("_");
			StringBuilder sb=new StringBuilder();
			sb.append(strs[0]);
			for(int i=1;i<strs.length;i++){
				sb.append(StringUtils.toUpperCaseFirstOne(strs[i]));
			}
			return sb.toString();
		}
		return columnName;
	}

	public String getJavaType() {
		if (dataType.equals("int")||dataType.equals("tinyint")){
			if ("YES".equals(isNullable)) {
				return "Integer";
			}
			return "int";
		}
		if (dataType.equals("timestamp")){
			return "java.sql.Timestamp";
		}
		if (dataType.equals("date")||dataType.equals("datetime")){
			return "java.sql.Date";
		}
		if (dataType.equals("bit")){
			if ("YES".equals(isNullable)) {
				return "Boolean";
			}
			return "boolean";
		}
		if (dataType.equals("double")||dataType.equals("decimal")){
			if ("YES".equals(isNullable)) {
				return "Double";
			}
			return "double";
		}
		if (dataType.equals("bigint")){
			if ("YES".equals(isNullable)) {
				return "Long";
			}
			return "long";
		}

		return "String";
	}
	public boolean isSimpleJavaType() {
		if (dataType.equals("int")||dataType.equals("tinyint")){
			if ("YES".equals(isNullable)) {
				return false;
			}
			return true;
		}
		if (dataType.equals("bit")){
			if ("YES".equals(isNullable)) {
				return false;
			}
			return true;
		}
		if (dataType.equals("double")||dataType.equals("decimal")){
			if ("YES".equals(isNullable)) {
				return false;
			}
			return true;
		}
		if (dataType.equals("bigint")){
			if ("YES".equals(isNullable)) {
				return false;
			}
			return true;
		}
		return false;
	}
	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String nullable) {
		isNullable = nullable;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public int getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(int ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
}
