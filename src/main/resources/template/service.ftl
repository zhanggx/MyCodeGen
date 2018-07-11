package ${basePackage}.${sign}.service;

import com.github.pagehelper.PageInfo;
import ${basePackage}.${sign}.model.bean.${modelNameUpperCamel};
import com.trigms.mango.common.bean.SearchRequestPageBean;
import com.trigms.mango.common.service.BaseService;

import java.util.List;

/**
 *
 * Created by ${author} on ${date}.
 */
public interface ${modelNameUpperCamel}Service extends BaseService<${modelNameUpperCamel}> {
  /**
  * 搜索记录
  * @return List
  */
  public List<${modelNameUpperCamel}> search(SearchRequestPageBean searchRequestBean);
  /**
  * 搜索记录(分页)
  * @return PageInfo
  */
  public PageInfo<${modelNameUpperCamel}> searchPage(SearchRequestPageBean searchRequestBean);
}
