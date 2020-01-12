package com.youngc.pipeline.service.system;

import com.github.pagehelper.Page;
import com.youngc.pipeline.model.UnitModel;
import com.youngc.pipeline.model.UserManagerModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserManagerService {
    UserManagerModel getUserDetails(Long userId);

    UserManagerModel updateUserDetails(UserManagerModel userManagerModel,Long userId,String roleIds,String droleIds,Long personId);

    boolean deleteUser(Long userId);

    boolean deleteUserList(String userIds);

    UserManagerModel addUser(UserManagerModel userManagerModel,String roleIds,String droleIds,Long personId);

    Page getList(String keyword, int pageNum, int pageSize);

    void updatePassword(Long userId, String password, Long lastPerson);

    List getUnitList();

    List getRoleList();

    List getDataRoleList();

    boolean putUserRole(String roleIds, Long userId, Long personId);

    boolean putUserDataRole(String droleIds, Long userId, Long personId);

    List<UserManagerModel> getInfoByUserName(String userName);
}
