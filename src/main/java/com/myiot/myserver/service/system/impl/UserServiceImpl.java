package com.myiot.myserver.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.constant.Constant;
import com.myiot.myserver.data.po.system.Department;
import com.myiot.myserver.data.po.system.Menu;
import com.myiot.myserver.data.po.system.User;
import com.myiot.myserver.data.vo.system.MenuDetailInfo;
import com.myiot.myserver.data.vo.system.RegisterRequest;
import com.myiot.myserver.data.vo.system.UserInfo;
import com.myiot.myserver.data.vo.system.UserQuery;
import com.myiot.myserver.mapper.system.DepartmentMapper;
import com.myiot.myserver.mapper.system.MenuMapper;
import com.myiot.myserver.mapper.system.RoleMenuMapper;
import com.myiot.myserver.mapper.system.UserMapper;
import com.myiot.myserver.service.system.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author origin
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private AccountServiceImpl accountService;

    @Override
    public ActionResult addUser(UserInfo userInfo) {
        RegisterRequest registerReq = new RegisterRequest();

        if (StringUtils.isBlank(userInfo.getAccountName())) {
            registerReq.setAccountName(userInfo.getUserName());
        } else {
            registerReq.setAccountName(userInfo.getAccountName());
        }

        if (StringUtils.isBlank(userInfo.getPassword())) {
            registerReq.setPassword(Constant.DEFAULT_PASSWORD);
        } else {
            registerReq.setPassword(userInfo.getPassword());
        }

        ActionResult registerRes = accountService.register(registerReq);
        if (!Constant.RESULT_SUCC.equals(registerRes.getResult())) {
            return registerRes;
        }

        if (StringUtils.isNotBlank(userInfo.getDepId())
                && null == departmentMapper.selectByPrimaryKey(userInfo.getDepId())) {
            return new ActionResult(Constant.RESULT_FAIL, "部门已不存在");
        }

        String userId = registerRes.getMessage();
        userInfo.setId(userId);

        return modifyUser(userInfo);
    }

    @Override
    public ActionResult modifyUser(UserInfo userInfo) {
        if (null == userMapper.selectByPrimaryKey(userInfo.getId())) {
            return new ActionResult(Constant.RESULT_FAIL, "用户已不存在");
        }

        if (StringUtils.isNotBlank(userInfo.getDepId())
                && null == departmentMapper.selectByPrimaryKey(userInfo.getDepId())) {
            return new ActionResult(Constant.RESULT_FAIL, "部门已不存在");
        }

        User user = new User();
        BeanUtils.copyProperties(userInfo, user);
        user.setName(userInfo.getUserName());
        user.setDeptId(userInfo.getDepId());

        try {
            userMapper.updateByPrimaryKey(user);
            return new ActionResult(Constant.RESULT_SUCC, user.getId());
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
    }

    @Override
    public ActionResult queryUserByPage(UserQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());

        List<UserInfo> userList;
        ActionResult actionResult = null;

        try {
            userList = userMapper.queryByPage(query);
            actionResult = new ActionResult(Constant.RESULT_SUCC, "", new PageInfo<UserInfo>(userList));
        } catch (Exception e) {
            actionResult = new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
        return actionResult;
    }

    @Override
    public ActionResult queryUserList(UserQuery query) {
        ActionResult actionResult = null;

        try {
            List<UserInfo> userList = userMapper.queryByPage(query);
            actionResult = new ActionResult(Constant.RESULT_SUCC, "", userList);
        } catch (Exception e) {
            actionResult = new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
        return actionResult;
    }

    @Override
    public ActionResult queryUserMenus(String userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user) {
            return new ActionResult(Constant.RESULT_FAIL, "用户已不存在");
        }

        Department dept = departmentMapper.selectByPrimaryKey(user.getDeptId());
        if (null == dept) {
            return new ActionResult(Constant.RESULT_FAIL, "部门已不存在");
        }

        if (StringUtils.isBlank(dept.getRoleId())) {
            return new ActionResult(Constant.RESULT_SUCC, "", Collections.emptyList());
        }

        List<Menu> menuList = roleMenuMapper.queryRoleMenus(dept.getRoleId());
        Set<Long> menuIdSet = new HashSet<>(menuList.size());
        menuList.forEach(menu -> { menuIdSet.add(menu.getId()); });
        List<Menu> menuTmpList = new ArrayList<>(menuList.size());
        menuList.forEach(menu -> {
            Long parentId = menu.getParentId();
            if (0 != parentId && !menuIdSet.contains(parentId)) {
                menuIdSet.add(parentId);
                menuTmpList.add(menuMapper.selectByPrimaryKey(parentId));
            }
        });
        menuList.addAll(menuTmpList);

        return new ActionResult(Constant.RESULT_SUCC, "", menuList);
    }
}
