package ${basePackage}.${sign}.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ${basePackage}.${sign}.model.dao.${modelNameUpperCamel}Mapper;
import ${basePackage}.${sign}.model.bean.${modelNameUpperCamel};
import ${basePackage}.${sign}.service.${modelNameUpperCamel}Service;
import com.trigms.mango.common.bean.SearchRequestPageBean;
import com.trigms.mango.common.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *
 * Created by ${author} on ${date}.
 */
@Service
public class ${modelNameUpperCamel}ServiceImpl implements ${modelNameUpperCamel}Service {

    @Autowired
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;
    @Override
    public List<${modelNameUpperCamel}> getAll() {
        return ${modelNameLowerCamel}Mapper.selectAll();
    }
    /**
    * 搜索记录
    * @return List
    */
    public List<${modelNameUpperCamel}> search(SearchRequestPageBean searchRequestBean) {
        return ${modelNameLowerCamel}Mapper.search(searchRequestBean);
    }
    /**
    * 搜索记录(分页)
    * @return PageInfo
    */
    public PageInfo<${modelNameUpperCamel}> searchPage(SearchRequestPageBean searchRequestBean){
        PageHelper.startPage(searchRequestBean.getPage(), searchRequestBean.getPageSize());
        return new PageInfo<>(${modelNameLowerCamel}Mapper.search(searchRequestBean));
    }

    @Override
    public ${modelNameUpperCamel} getInfo(int id) {
    return ${modelNameLowerCamel}Mapper.selectByPrimaryKey(id);
    }

    @Override
    public int insert(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        return ${modelNameLowerCamel}Mapper.insert${modelNameUpperCamel}(${modelNameLowerCamel});
    }
    @Override
    public int update(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        return ${modelNameLowerCamel}Mapper.update${modelNameUpperCamel}(${modelNameLowerCamel});
    }

    @Override
    public int delete(int id) {
        return ${modelNameLowerCamel}Mapper.deleteByPrimaryKey(id);
    }
}
