package ${basePackage}.${sign}.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import ${basePackage}.${sign}.model.bean.${modelNameUpperCamel};
import ${basePackage}.${sign}.service.${modelNameUpperCamel}Service;
import com.trigms.mango.common.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *${modelNameUpperCamel}控制类
 * Created by ${author} on ${date}.
 */
@Controller
@RequestMapping("/${sign}/${modelNameLowerCamel}/")
public class ${modelNameUpperCamel}Controller {

    @Autowired
    ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;


    @ResponseBody
    @RequestMapping("list.do")
    public ResultBean list(@RequestBody RequestBean requestBean) {
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.getAll();
        ResultBean resultBean = ResultBean.success(list);
        return resultBean;
    }
    @ResponseBody
    @RequestMapping("search.do")
    public ResultBean search(@RequestBody SearchRequestPageBean searchRequestBean) {
        if (searchRequestBean.getPage()==null||searchRequestBean.getPage().intValue()==0){
            List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.search(searchRequestBean);
            ResultBean resultBean = ResultBean.success(list);
            return resultBean;
        }
        PageInfo<${modelNameUpperCamel}> pageInfo = ${modelNameLowerCamel}Service.searchPage(searchRequestBean);
        return ResultPageBean.success(pageInfo);
    }
    @ResponseBody
    @RequestMapping("info.do")
    public ResultBean getInfo(@RequestBody IdRequestBean requestBean) {
        if (requestBean.getId()==0){
            return ResultBean.PARAMETER_ERROR_RESULTBEAN;
        }
        ${modelNameUpperCamel} ${modelNameLowerCamel}=${modelNameLowerCamel}Service.getInfo(requestBean.getId());
        if (${modelNameLowerCamel}==null){
            return ResultBean.fail(ResultBean.ERROR_CODE_RECORDNOEXISTS,"记录不存在");
        }

        ResultBean resultBean = ResultBean.success(${modelNameLowerCamel});
        return resultBean;
    }
    /**
    * 检查参数的合法性
    */
    private ResultBean checkParameter(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        return null;
    }
    @ResponseBody
    @RequestMapping("create.do")
    public ResultBean add(@RequestBody JSONObject jsonObject) {
        ${modelNameUpperCamel} ${modelNameLowerCamel}=jsonObject.toJavaObject(${modelNameUpperCamel}.class);

        ResultBean resultBean=checkParameter(${modelNameLowerCamel});
        if (resultBean!=null){
            return resultBean;
        }

        int result = ${modelNameLowerCamel}Service.insert(${modelNameLowerCamel});
        if (result>0){
            ResultBean resultBean = ResultBean.success();
            return resultBean;
        }
        return ResultBean.fail();
    }

    @RequestMapping("update.do")
    @ResponseBody
    public ResultBean update(@RequestBody JSONObject jsonObject) {
        ${modelNameUpperCamel} ${modelNameLowerCamel}=jsonObject.toJavaObject(${modelNameUpperCamel}.class);
        if (${modelNameLowerCamel}.getId()==0){
            return ResultBean.PARAMETER_ERROR_RESULTBEAN;
        }

        ResultBean resultBean=checkParameter(${modelNameLowerCamel});
        if (resultBean!=null){
            return resultBean;
        }

        ${modelNameUpperCamel} old${modelNameLowerCamel}=${modelNameLowerCamel}Service.getInfo(${modelNameLowerCamel}.getId());
        if (old${modelNameLowerCamel}==null){
            return ResultBean.fail(ResultBean.ERROR_CODE_RECORDNOEXISTS,"记录不存在");
        }

        int result = ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        if (result>0){
            ResultBean resultBean = ResultBean.success();
            return resultBean;
        }
        return ResultBean.fail();
    }

    @ResponseBody
    @RequestMapping(value = "delete.do")
    public ResultBean delete(@RequestBody IdRequestBean requestBean) {
        if (requestBean.getId()==0){
            return ResultBean.PARAMETER_ERROR_RESULTBEAN;
        }
        ${modelNameUpperCamel} ${modelNameLowerCamel}=${modelNameLowerCamel}Service.getInfo(requestBean.getId());
        if (${modelNameLowerCamel}==null){
            return ResultBean.fail(ResultBean.ERROR_CODE_RECORDNOEXISTS,"记录不存在");
        }
        int result = ${modelNameLowerCamel}Service.delete(requestBean.getId());
        if (result>0){
            ResultBean resultBean = ResultBean.success();
            return resultBean;
        }
        return ResultBean.fail();
    }
}
