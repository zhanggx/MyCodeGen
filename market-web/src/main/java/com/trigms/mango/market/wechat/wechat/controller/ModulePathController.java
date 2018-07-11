package com.trigms.mango.market.wechat.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.trigms.mango.market.wechat.wechat.model.bean.ModulePath;
import com.trigms.mango.market.wechat.wechat.service.ModulePathService;
import com.trigms.mango.common.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *ModulePath控制类
 * Created by zhanggx on 2018/07/03.
 */
@Controller
@RequestMapping("/wechat/modulePath/")
public class ModulePathController {

    @Autowired
    ModulePathService modulePathService;


    @ResponseBody
    @RequestMapping("list.do")
    public ResultBean list(@RequestBody RequestBean requestBean) {
        List<ModulePath> list = modulePathService.getAll();
        ResultBean resultBean = ResultBean.success(list);
        return resultBean;
    }
    @ResponseBody
    @RequestMapping("search.do")
    public ResultBean search(@RequestBody SearchRequestPageBean searchRequestBean) {
        if (searchRequestBean.getPage()==null||searchRequestBean.getPage().intValue()==0){
            List<ModulePath> list = modulePathService.search(searchRequestBean);
            ResultBean resultBean = ResultBean.success(list);
            return resultBean;
        }
        PageInfo<ModulePath> pageInfo = modulePathService.searchPage(searchRequestBean);
        return ResultPageBean.success(pageInfo);
    }
    @ResponseBody
    @RequestMapping("info.do")
    public ResultBean getInfo(@RequestBody IdRequestBean requestBean) {
        if (requestBean.getId()==0){
            return ResultBean.PARAMETER_ERROR_RESULTBEAN;
        }
        ModulePath modulePath=modulePathService.getInfo(requestBean.getId());
        if (modulePath==null){
            return ResultBean.fail(ResultBean.ERROR_CODE_RECORDNOEXISTS,"记录不存在");
        }

        ResultBean resultBean = ResultBean.success(modulePath);
        return resultBean;
    }
    /**
    * 检查参数的合法性
    */
    private ResultBean checkParameter(ModulePath modulePath) {
        return null;
    }
    @ResponseBody
    @RequestMapping("create.do")
    public ResultBean add(@RequestBody JSONObject jsonObject) {
        ModulePath modulePath=jsonObject.toJavaObject(ModulePath.class);

        ResultBean resultBean=checkParameter(modulePath);
        if (resultBean!=null){
            return resultBean;
        }

        int result = modulePathService.insert(modulePath);
        if (result>0){
            ResultBean resultBean = ResultBean.success();
            return resultBean;
        }
        return ResultBean.fail();
    }

    @RequestMapping("update.do")
    @ResponseBody
    public ResultBean update(@RequestBody JSONObject jsonObject) {
        ModulePath modulePath=jsonObject.toJavaObject(ModulePath.class);
        if (modulePath.getId()==0){
            return ResultBean.PARAMETER_ERROR_RESULTBEAN;
        }

        ResultBean resultBean=checkParameter(modulePath);
        if (resultBean!=null){
            return resultBean;
        }

        ModulePath oldmodulePath=modulePathService.getInfo(modulePath.getId());
        if (oldmodulePath==null){
            return ResultBean.fail(ResultBean.ERROR_CODE_RECORDNOEXISTS,"记录不存在");
        }

        int result = modulePathService.update(modulePath);
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
        ModulePath modulePath=modulePathService.getInfo(requestBean.getId());
        if (modulePath==null){
            return ResultBean.fail(ResultBean.ERROR_CODE_RECORDNOEXISTS,"记录不存在");
        }
        int result = modulePathService.delete(requestBean.getId());
        if (result>0){
            ResultBean resultBean = ResultBean.success();
            return resultBean;
        }
        return ResultBean.fail();
    }
}
