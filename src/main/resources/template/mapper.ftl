package ${basePackage}.${sign}.model.dao;

import ${basePackage}.${sign}.model.bean.${modelNameUpperCamel};
import com.trigms.mango.common.bean.SearchRequestPageBean;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *
 * Created by ${author} on ${date}.
 */
@Repository
public interface ${modelNameUpperCamel}Mapper extends Mapper<${modelNameUpperCamel}> {

    List<${modelNameUpperCamel}> getList();

    List<${modelNameUpperCamel}> search(SearchRequestPageBean searchRequestBean);

    int insert${modelNameUpperCamel}(${modelNameUpperCamel} ${modelNameLowerCamel});

    int update${modelNameUpperCamel}(${modelNameUpperCamel} ${modelNameLowerCamel});
}
