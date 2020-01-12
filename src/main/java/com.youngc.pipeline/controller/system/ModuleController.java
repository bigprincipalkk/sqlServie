package com.youngc.pipeline.controller.system;

import com.youngc.pipeline.bean.param.ModuleBean;
import com.youngc.pipeline.bean.context.UserBean;
import com.youngc.pipeline.model.ModuleModel;
import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.system.ModuleService;
import com.youngc.pipeline.utils.RequestContextHolderUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

/**
 * @author liweiqiang
 */
@RestController
@RequestMapping("/module")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    @ApiOperation("获取模块树")
    @GetMapping("/tree")
    public Result getTree(@RequestParam String keyword,@RequestParam String roleIds,@RequestParam String droleIds) {

        return ResultGenerator.generate(ResultCode.SUCCESS,moduleService.getTree(keyword,roleIds,droleIds));
    }

    @ApiOperation("获取菜单树")
    @GetMapping("/moduleTree")
    public Result getModuleTree(@RequestParam String keyword,@RequestParam String roleIds,@RequestParam String droleIds) {

        return ResultGenerator.generate(ResultCode.SUCCESS,moduleService.getModuleTree(keyword,roleIds,droleIds));
    }

    @ApiOperation("获取模块信息")
    @GetMapping("/{moduleId}")
    public Result getGroupInfo(@PathVariable Long moduleId) {

        return ResultGenerator.generate(ResultCode.SUCCESS,moduleService.getModuleDetails(moduleId));
    }

    @ApiOperation("获取模块标识")
    @GetMapping("/getControlId")
    public Result getControlId(@RequestParam String controlId,Long moduleId) {
        return ResultGenerator.generate(ResultCode.SUCCESS,moduleService.getControlId(controlId,moduleId));
    }

    @ApiOperation("修改模块信息")
    @PutMapping
    public Result putInfo(@RequestBody ModuleBean moduleBean) {
        UserBean user = (UserBean) RequestContextHolderUtil.getRequest().getAttribute("user");

        ModuleModel moduleModel = new ModuleModel();
        moduleModel.setModuleName(moduleBean.getModuleName());
        moduleModel.setIcon(moduleBean.getIcon());
        moduleModel.setControlId(moduleBean.getControlId());
        moduleModel.setType(moduleBean.getType());
        moduleModel.setStatus(moduleBean.getStatus());
        moduleModel.setPid(moduleBean.getPid());
        moduleModel.setPriority(moduleBean.getPriority());
        moduleModel.setModuleDesc(moduleBean.getModuleDesc());
        moduleModel.setModuleId(moduleBean.getModuleId());

        moduleModel.setLastPerson(user.getUserId());

        return ResultGenerator.generate(ResultCode.SUCCESS,moduleService.updateModuleDetails(moduleModel));
    }

    @ApiOperation("添加模块信息")
    @PostMapping
    public Result postInfo(@RequestBody ModuleBean moduleBean) {
        UserBean user = (UserBean) RequestContextHolderUtil.getRequest().getAttribute("user");

        ModuleModel moduleModel = new ModuleModel();

        moduleModel.setModuleName(moduleBean.getModuleName());
        moduleModel.setIcon(moduleBean.getIcon());
        moduleModel.setControlId(moduleBean.getAddControlId());
        moduleModel.setType(moduleBean.getType());
        moduleModel.setStatus(moduleBean.getStatus());
        moduleModel.setPid(moduleBean.getModuleId());
        moduleModel.setPriority(moduleBean.getPriority());
        moduleModel.setModuleDesc(moduleBean.getModuleDesc());

        moduleModel.setAddPerson(user.getUserId());
        moduleModel.setLastPerson(user.getUserId());

        return ResultGenerator.generate(ResultCode.SUCCESS,moduleService.addModule(moduleModel));
    }

    @ApiOperation("删除模块信息")
    @DeleteMapping
    public Result deleteModule(@RequestParam Long moduleId) {
        moduleService.deleteModule(moduleId);
        return ResultGenerator.generate(ResultCode.SUCCESS);
    }
}
