package com.youngc.pipeline.controller.system;

import com.youngc.pipeline.bean.param.SysRoleBean;
import com.youngc.pipeline.model.SysRoleModel;
import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.system.SysRoleService;
import com.youngc.pipeline.utils.RequestContextHolderUtil;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 通过数据角色的名字模糊查询数据角色表中的数据
     * @param keyWord
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/getList")
    public Result getRoleList(@RequestParam String keyWord, @RequestParam int pageNum, @RequestParam int pageSize){
        return ResultGenerator.generate(sysRoleService.getRoleList(keyWord, pageNum, pageSize));
    }

    /**
     * 通过id查询数据角色表中的id
     * @param id
     * @return
     */
    @GetMapping(value = "/getInfo/{id}")
    public Result getRoleInfo(@PathVariable Long id) {
        return ResultGenerator.generate(ResultCode.SUCCESS, sysRoleService.getRoleInfo(id));
    }

    /**
     * 添加数据角色
     * @param
     * @return
     */
    @PostMapping(value = "/post")
    public Result postRoleInfo(@RequestBody SysRoleBean sysRoleBean) {
        com.youngc.pipeline.bean.context.UserBean user
                = (com.youngc.pipeline.bean.context.UserBean) RequestContextHolderUtil.getRequest().getAttribute("user");
        SysRoleModel sysRoleModel = new SysRoleModel();

        sysRoleModel.setRoleName(sysRoleBean.getRoleName());
        sysRoleModel.setStatus(sysRoleBean.getStatus());
        sysRoleModel.setRoleDesc(sysRoleBean.getRoleDesc());

        sysRoleModel.setAddPerson(user.getUserId());
        sysRoleModel.setLastPerson(user.getUserId());

        return ResultGenerator.generate(ResultCode.SUCCESS,
                sysRoleService.addRole(sysRoleModel));
    }

    /**
     * 修改数据角色
     * @param
     * @return
     */
    @PutMapping(value = "/put")
    public Result putRoleInfo(@RequestBody SysRoleBean sysRoleBean) {
        com.youngc.pipeline.bean.context.UserBean user
                = (com.youngc.pipeline.bean.context.UserBean) RequestContextHolderUtil.getRequest().getAttribute("user");
        SysRoleModel sysRoleModel = new SysRoleModel();

        sysRoleModel.setRoleId(sysRoleBean.getRoleId());
        sysRoleModel.setRoleName(sysRoleBean.getRoleName());
        sysRoleModel.setStatus(sysRoleBean.getStatus());
        sysRoleModel.setRoleDesc(sysRoleBean.getRoleDesc());

        sysRoleModel.setLastPerson(user.getUserId());

        return ResultGenerator.generate(ResultCode.SUCCESS, sysRoleService.updateRoleInfo(sysRoleModel));
    }

    /**
     * 删除数据角色
     * @param idList
     * @return
     */
    @DeleteMapping(value = "/del")
    public Result deleteRoleList(@RequestParam("idList") String idList) {
        sysRoleService.deleteRoleList(idList);
        return ResultGenerator.generate(ResultCode.SUCCESS);
    }

    /**
     *根据roleID获取权限树
     * @param roleId
     * @return
     */
    @GetMapping(value = "/getTree/{roleId}")
    public Result getRoleTree(@PathVariable Long roleId){
        return ResultGenerator.generate(ResultCode.SUCCESS,sysRoleService.getRoleTree(roleId));
    }

    /**
     * 修改权限树
     * @param roleId
     * @param moduleIds
     * @return
     */
    @PutMapping(value = "/putRoleModule/{roleId}/{moduleIds}")
    public Result setRoleModule(@PathVariable Long roleId,@PathVariable String moduleIds){
        com.youngc.pipeline.bean.context.UserBean user
                = (com.youngc.pipeline.bean.context.UserBean) RequestContextHolderUtil.getRequest().getAttribute("user");
        sysRoleService.updateRoleModule(roleId,moduleIds,user.getUserId());
        return ResultGenerator.generate(ResultCode.SUCCESS);
    }

}
