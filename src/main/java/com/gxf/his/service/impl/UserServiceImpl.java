package com.gxf.his.service.impl;

import com.gxf.his.controller.UserController;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.UserException;
import com.gxf.his.mapper.dao.ICashierMapper;
import com.gxf.his.mapper.dao.IDoctorMapper;
import com.gxf.his.mapper.dao.IPatientMapper;
import com.gxf.his.mapper.dao.IUserMapper;
import com.gxf.his.po.generate.Cashier;
import com.gxf.his.po.generate.Doctor;
import com.gxf.his.po.generate.Patient;
import com.gxf.his.po.generate.User;
import com.gxf.his.po.vo.CashierVo;
import com.gxf.his.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private IUserMapper iUserMapper;
    @Resource
    private IPatientMapper iPatientMapper;
    @Resource
    private ICashierMapper iCashierMapper;
    @Resource
    private IDoctorMapper iDoctorMapper;

    @Override
    public User findByUserName(String userName) throws UserException {
        User user;
        try {
            user = iUserMapper.selectByUserName(userName);
        } catch (MyBatisSystemException e) {
            log.error("当前用户名查出多位用户：" + e.getMessage());
            throw new UserException(ServerResponseEnum.USER_REPEAT_ERROR);
        } catch (Exception e) {
            log.error("根据用户名查询用户失败！", e);
            throw new UserException(ServerResponseEnum.USER_SELECT_FAIL);
        }
        return user;

    }

    @Override
    public Long getLoginEntityId(Long uid) {
        Patient patient = iPatientMapper.selectByUid(uid);
        if (patient != null) {
            return patient.getPatientId();
        }
        CashierVo cashierVo = iCashierMapper.selectByUid(uid);
        if (cashierVo != null) {
            return cashierVo.getCashierId();
        }
        Doctor doctor = iDoctorMapper.selectByUid(uid);
        if (doctor != null) {
            return doctor.getDoctorId();
        }
        return null;
    }

    @Override
    public User getUserByPrimaryKey(Long uid) {
        User user;
        try {
            user = iUserMapper.selectByPrimaryKey(uid);
        } catch (Exception e) {
            log.error("根据用户ID查询用户失败！", e);
            throw new UserException(ServerResponseEnum.USER_SELECT_FAIL);
        }
        return user;
    }

    @Override
    public int deleteUser(Long id) {
        try {
            return iUserMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            log.error("根据ID删除用户失败！", e);
            throw new UserException(ServerResponseEnum.USER_DELETE_FAIL);
        }
    }

    @Override
    public Integer deleteUserBatch(List<User> users) {
        try {
            return iUserMapper.batchUserDelete(users);
        } catch (Exception e) {
            throw new UserException(ServerResponseEnum.USER_DELETE_FAIL);
        }
    }

    @Override
    public int addUser(User user) throws UserException {
        try {
            return iUserMapper.insertAndInjectThePrimaryKey(user);
        } catch (Exception e) {
            log.error("User插入失败!", e);
            throw new UserException(ServerResponseEnum.USER_SAVE_FAIL);
        }
    }

    @Override
    public Long updateUserPassword(User user) {
        try {
            iUserMapper.updateByPrimaryKey(user);
        } catch (Exception e) {
            log.error("User更新失败!", e);
            throw new UserException(ServerResponseEnum.USER_UPDATE_FAIL);
        }
        return user.getUserId();
    }

    @Override
    public Long updateUser(User user) {
        //重新加密密码,生成新密码盐和密钥
        String password = user.getUserPassword();
        //密码不为空才进行加密更新用户信息
        if (!"".equals(password.trim())) {
            User tempUser = UserController.doHashedCredentials(user.getUserName(), password);
            user.setUserPassword(tempUser.getUserPassword());
            user.setUserSalt(tempUser.getUserSalt());
            log.info("当前更新的用户信息为：" + user.toString());
            try {
                iUserMapper.updateByPrimaryKey(user);
            } catch (Exception e) {
                log.error("User更新失败!", e);
                throw new UserException(ServerResponseEnum.USER_UPDATE_FAIL);
            }
        } else {
            log.info("当前用户密码为空！");
        }
        return user.getUserId();
    }

}
