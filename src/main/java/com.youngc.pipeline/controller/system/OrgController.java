package com.youngc.pipeline.controller.system;

import com.youngc.pipeline.bean.param.OrgBean;
import com.youngc.pipeline.model.OrgModel;
import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.system.OrgService;
import com.youngc.pipeline.utils.RequestContextHolderUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liweiqiang
 */
@RestController
@RequestMapping("/organization")
public class OrgController {
    @Autowired
    private OrgService orgService;

    @ApiOperation("获取组织树")
    @GetMapping("/tree")
    public Result getTree(@RequestParam String keyword) {

        return ResultGenerator.generate(ResultCode.SUCCESS, orgService.getTree(keyword));
    }

    @ApiOperation("获取组织信息")
    @GetMapping("/{orgId}")
    public Result getOrgInfo(@PathVariable Long orgId) {

        return ResultGenerator.generate(ResultCode.SUCCESS, orgService.getOrg(orgId));
    }

    @ApiOperation("获取组织编号")
    @GetMapping("/getOrgCode")
    public Result getOrgCode(@RequestParam String orgCode) {
        return ResultGenerator.generate(ResultCode.SUCCESS, orgService.getOrgCode(orgCode));
    }

    @ApiOperation("修改组织信息")
    @PutMapping
    public Result putOrgInfo(@RequestBody OrgBean orgBean) {
        com.youngc.pipeline.bean.context.UserBean user
                = (com.youngc.pipeline.bean.context.UserBean) RequestContextHolderUtil.getRequest().getAttribute("user");

        OrgModel orgModel = new OrgModel();

        orgModel.setOrgName(orgBean.getOrgName());
        orgModel.setOrgCode(orgBean.getOrgCode());
        orgModel.setOrgId(orgBean.getOrgId());
        orgModel.setPid(orgBean.getPid());
        orgModel.setOrgDesc(orgBean.getOrgDesc());

        orgModel.setLastPerson(user.getUserId());

        return ResultGenerator.generate(ResultCode.SUCCESS, orgService.updateOrg(orgModel));
    }

    @ApiOperation("添加组织信息")
    @PostMapping
    public Result postOrgInfo(@RequestBody OrgBean orgBean) {
        com.youngc.pipeline.bean.context.UserBean user
                = (com.youngc.pipeline.bean.context.UserBean) RequestContextHolderUtil.getRequest().getAttribute("user");

        OrgModel orgModel = new OrgModel();

        orgModel.setOrgName(orgBean.getOrgName());
        orgModel.setOrgCode(orgBean.getAddOrgCode());
        orgModel.setPid(orgBean.getOrgId());
        orgModel.setOrgDesc(orgBean.getOrgDesc());

        orgModel.setAddPerson(user.getUserId());
        orgModel.setLastPerson(user.getUserId());

        return ResultGenerator.generate(ResultCode.SUCCESS, orgService.addOrg(orgModel));
    }

    @ApiOperation("删除组织信息")
    @DeleteMapping
    public Result deleteOrg(@RequestParam Long orgId) {
        orgService.deleteOrg(orgId);
        return ResultGenerator.generate(ResultCode.SUCCESS);
    }
}
