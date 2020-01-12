package com.youngc.pipeline.service.system.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youngc.pipeline.mapper.system.UserManagerMapper;
import com.youngc.pipeline.model.UnitModel;
import com.youngc.pipeline.model.UserManagerModel;
import com.youngc.pipeline.service.system.UserManagerService;
import com.youngc.pipeline.utils.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class UserManagerServiceImpl implements UserManagerService {

    @Autowired
    private UserManagerMapper userManagerMapper;

    /**
     * 批量获取用户信息
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page getList(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return (Page) userManagerMapper.getList(keyword);
    }

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    public UserManagerModel getUserDetails(Long userId) {

        return userManagerMapper.getUserInfo(userId);
    }

    /**
     * 修改用户
     *
     * @param userManagerModel
     * @return
     */
    public UserManagerModel updateUserDetails(UserManagerModel userManagerModel, Long userId, String roleIds, String droleIds, Long personId) {

        userManagerMapper.updateUserInfo(userManagerModel);
        putUserRole(roleIds, userId, personId);
        putUserDataRole(droleIds, userId, personId);
        return userManagerModel;
    }

    /**
     * 更新密码
     *
     * @param userId
     * @param password
     * @param lastPerson
     */
    public void updatePassword(Long userId, String password, Long lastPerson) {
        String hashPwd = BCryptUtil.hashpw(password, BCryptUtil.gensalt(12));
        userManagerMapper.updatePassword(userId, hashPwd, lastPerson);
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    public boolean deleteUser(Long userId) {

        userManagerMapper.deleteUserRole(userId);
        userManagerMapper.deleteUserDataRole(userId);
        userManagerMapper.deleteUser(userId);


        return true;
    }

    /**
     * 批量删除用户
     *
     * @param userIds
     * @return
     */
    public boolean deleteUserList(String userIds) {

        userManagerMapper.deleteUsersRole(userIds);
        userManagerMapper.deleteUsersDataRole(userIds);
        userManagerMapper.deleteUserList(userIds);

        return true;
    }

    /**
     * 添加用户
     *
     * @param userManagerModel
     * @return
     */
    public UserManagerModel addUser(UserManagerModel userManagerModel, String roleIds, String droleIds, Long personId) {

        userManagerModel.setPassword(BCryptUtil.hashpw(userManagerModel
                .getPassword(), BCryptUtil.gensalt(12)));

        userManagerMapper.insertNewUser(userManagerModel);
        Long userId = userManagerModel.getUserId();
        if (!roleIds.equals("")) {
            putUserRole(roleIds, userId, personId);
        }
        if (!droleIds.equals("")) {
            putUserDataRole(droleIds, userId, personId);
        }
        return userManagerModel;
    }

    /**
     * 获取单位列表
     */
    public List getUnitList() {
        return userManagerMapper.getUnitList();
    }

    /**
     * 获取权限列表
     *
     * @return
     */
    public List getRoleList() {
        return userManagerMapper.getRoleList();
    }

    /**
     * 获取数据角色列表
     *
     * @return
     */
    public List getDataRoleList() {
        return userManagerMapper.getDataRoleList();
    }

    /**
     * 用户权限表中存数据
     *
     * @param roleIds
     * @param userId
     * @param personId
     * @return
     */
    public boolean putUserRole(String roleIds, Long userId, Long personId) {
        userManagerMapper.deleteUserRole(userId);
        if (roleIds.length() > 0) {
            String[] IDS = roleIds.split(",");
            List<String> roleId = Arrays.asList(IDS);
            userManagerMapper.insertUserRole(roleId, userId, personId);
        }
        return true;
    }

    /**
     * 用户数据角色表中存数据
     *
     * @param droleIds
     * @param userId
     * @param personId
     * @return
     */
    public boolean putUserDataRole(String droleIds, Long userId, Long personId) {
        userManagerMapper.deleteUserDataRole(userId);
        if (droleIds.length() > 0) {
            String[] IDS = droleIds.split(",");
            List<String> droleId = Arrays.asList(IDS);
            userManagerMapper.insertUserDataRole(droleId, userId, personId);
        }

        return true;
    }

    /**
     * 根据用户名查询是否唯一
     * @param userName
     * @return
     */
    public List<UserManagerModel> getInfoByUserName(String userName) {
        return userManagerMapper.getInfoByUserName(userName);
    }

}
