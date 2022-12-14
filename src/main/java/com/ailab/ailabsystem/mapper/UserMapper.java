package com.ailab.ailabsystem.mapper;

import com.ailab.ailabsystem.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据学号和密码查询用户
     * @param studentNumber
     * @param password
     * @return
     */
    User selectByStuNumAndPwd(String studentNumber, String password);

    /**
     * 获取所有未毕业的学员
     * @return
     */
    List<User> selectAllNotGraduatedUser();
}
