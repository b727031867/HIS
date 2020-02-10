package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.CashierException;
import com.gxf.his.mapper.dao.ICashierMapper;
import com.gxf.his.po.vo.CashierVo;
import com.gxf.his.po.generate.Cashier;
import com.gxf.his.po.generate.User;
import com.gxf.his.service.CashierService;
import com.gxf.his.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2020-01-19
 */
@Service
@Slf4j
public class CashierServiceImpl implements CashierService {


    @Resource
    private ICashierMapper iCashierMapper;

    @Resource
    private UserService userService;

    @Override
    public int addCashier(Cashier cashier) {
        try {
            return iCashierMapper.insert(cashier);
        } catch (Exception e) {
            log.error("收银员保存失败", e);
            throw new CashierException(ServerResponseEnum.CASHIER_SAVE_FAIL);
        }
    }

    @Override
    public List<CashierVo> getAllCashiers() {
        List<CashierVo> cashiers;
        try {
            cashiers = iCashierMapper.selectAllCashierInfo();
        } catch (Exception e) {
            log.error("查询所有收银员失败", e);
            throw new CashierException(ServerResponseEnum.CASHIER_LIST_FAIL);
        }
        return cashiers;
    }

    @Override
    public List<CashierVo> selectCashierByAttribute(CashierVo cashierVo) {
        List<CashierVo> cashiers;
        try {
            if (cashierVo.getIsAccurate()) {
                cashiers = iCashierMapper.selectCashierByAccurateAttribute(cashierVo);
            } else {
                cashiers = iCashierMapper.selectCashierByAttribute(cashierVo);
            }
        } catch (Exception e) {
            log.error("按属性查询收银员失败", e);
            throw new CashierException(ServerResponseEnum.CASHIER_LIST_FAIL);
        }
        return cashiers;
    }

    @Override
    public int updateCashier(Cashier cashier) {
        try {
            return iCashierMapper.updateByPrimaryKey(cashier);
        } catch (Exception e) {
            log.error("收银员信息更新失败", e);
            throw new CashierException(ServerResponseEnum.CASHIER_UPDATE_FAIL);
        }
    }

    @Override
    public int deleteCashierAndUser(Long cashierId, Long userId) {
        int a = 0;
        int b = 2;
        a = iCashierMapper.deleteByPrimaryKey(cashierId) + a;
        a = userService.deleteUser(userId) + a;
        if (a != b) {
            log.warn("删除时，没有找到ID为" + cashierId + "的用户并且ID为" + userId + "的收银员！");
            throw new CashierException(ServerResponseEnum.CASHIER_DELETE_FAIL);
        }
        return 1;
    }

    @Override
    public int deleteCashierAndUserBatch(List<Cashier> cashiers, List<User> users) {
        try {
            Integer a;
            a = iCashierMapper.batchCashierDelete(cashiers);
            a = userService.deleteUserBatch(users) + a;
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CashierException(ServerResponseEnum.CASHIER_DELETE_FAIL);
        }
    }
}
