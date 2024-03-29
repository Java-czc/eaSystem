package com.upower.easystemservice.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.upower.easystemservice.mapper.AdminMapper;
import com.upower.easystemservice.pojo.Admin;
import com.upower.easystemservice.pojo.PageBean;
import com.upower.easystemservice.pojo.User;
import com.upower.easystemservice.service.AdminService;
import com.upower.easystemservice.util.StringRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * description:
 * author: 沫凌
 * create: 2019-06-11 9:38
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 添加用户
     * @param admin
     * @return
     */
    @Override
    public String insertAdamin(Admin admin) {

        List<User> users = adminMapper.selectUserAll();
        for (User user : users) {
            if (user.getName().equals(admin.getName())) {
                return "姓名不能重复";
            }
        }
        String[] acc = {"admin"};
        admin.setToken(StringRandom.getStringRandom(15));
        admin.setAccess(Arrays.toString(acc));
        admin.setAvator("https://file.iviewui.com/dist/a0e88e83800f138b94d2414621bd9704.png");
        admin.setRoles("0");
        int i = adminMapper.insertAdamin(admin);
        if (i > 0) {
            return "添加成功";
        } else {
            return "添加失败";
        }
    }

    /**
     * 模糊查询、分页
     * @param name 名称
     * @param page 页码
     * @param limit 页数
     * @return
     */
    @Override
    public PageBean searchAdmin(String name, Integer page, Integer limit) {

        Page<Admin> pages;
        PageHelper.startPage(page, limit);//开启分页

        //若不查询
        if (StringUtils.isEmpty(name)) {
            pages = (Page<Admin>) adminMapper.searchAdmin("", "0");
        } else {
            pages = (Page<Admin>) adminMapper.searchAdmin(name, "0");

        }
        return new PageBean(pages.getTotal(), pages.getResult());
    }

    /**
     * 删除用户
     * @param id 用户编号
     * @return
     */
    @Override
    public String deleteAdmin(int id) {
        int i = adminMapper.deleteAdmin(id);
        if (i > 0) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }

    /**
     *
     * @param admin
     * @return
     */
    @Override
    public String updateAdmin(Admin admin) {
        int i = adminMapper.updateAdmin(admin);
        if (i > 0) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }

}
