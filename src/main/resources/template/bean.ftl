package ${basePackage}.${sign}.model.bean;

import com.trigms.mango.common.bean.DaoBaseBean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *${tableComment}
 * Created by ${author} on ${date}.
 */
@Table(name = "${tableName}")
public class ${modelNameUpperCamel} extends DaoBaseBean {
    /**
    * 自增ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
<#list columnList as column>
    /**
    *${column.columnComment}
    */
    <#if column.simleJavaType>@Column</#if>
    private ${column.javaType} ${column.columnNameFormated};
</#list>

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
<#list columnList as column>
    public ${column.javaType} <#if column.javaType=="boolean">is<#else>get</#if>${column.columnNameFormated?cap_first}() {
    return ${column.columnNameFormated};
    }
    public void set${column.columnNameFormated?cap_first}(${column.javaType} ${column.columnNameFormated}) {
        this.${column.columnNameFormated} = ${column.columnNameFormated};
    }
</#list>
}
