package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.CashierException;
import com.gxf.his.mapper.CashierMapper;
import com.gxf.his.po.Cashier;
import com.gxf.his.po.User;
import com.gxf.his.service.CashierService;
import com.gxf.his.service.UserService;
import com.gxf.his.vo.CashierUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2020-01-19
 */
@Service
public class CashierServiceImpl implements CashierService {

    private Logger logger = LoggerFactory.getLogger(CashierServiceImpl.class);

    @Resource
    private CashierMapper cashierMapper;

    @Resource
    private UserService userService;

    @Override
    public void addCashier(Cashier cashier) {
        try {
            cashierMapper.insert(cashier);
        }catch (Exception e){
            logger.error("收银员保存失败",e);
            throw new CashierException(ServerResponseEnum.CASHIER_SAVE_FAIL);
        }
    }

    @Override
    public List<CashierUserVo> getAllCashiers() {
        List<CashierUserVo> cashiers;
        try {
            cashiers = cashierMapper.selectAll();
        } catch (Exception e) {
            logger.error("查询所有收银员失败", e);
            throw new CashierException(ServerResponseEnum.CASHIER_LIST_FAIL);
        }
        return cashiers;
    }

    @Override
    public List<CashierUserVo> selectCashierByAttribute(CashierUserVo cashierUserVo) {
        List<CashierUserVo> cashiers;
        try {
            if(cashierUserVo.getIsAccurate()){
                cashiers = cashierMapper.selectCashierByAccurateAttribute(cashierUserVo);
            }else{
                cashiers = cashierMapper.selectCashierByAttribute(cashierUserVo);
            }
        } catch (Exception e) {
            logger.error("按属性查询收银员失败", e);
            throw new CashierException(ServerResponseEnum.CASHIER_LIST_FAIL);
        }
        return cashiers;
    }

    @Override
    public int updateCashier(Cashier cashier) {
        try {
            return cashierMapper.updateByPrimaryKey(cashier);
        } catch (Exception e) {
            logger.error("收银员信息更新失败", e);
            throw new CashierException(ServerResponseEnum.CASHIER_UPDATE_FAIL);
        }
    }

    @Override
    public int deleteCashierAndUser(Long cashierId, Long userId) {
        int a = 0;
        int b = 2;
        a = cashierMapper.deleteByPrimaryKey(cashierId) + a;
        a = userService.deleteUser(userId) + a;
        if(a != b){
            logger.warn("删除时，没有找到ID为"+cashierId+"的用户并且ID为"+userId+"的收银员！");
            throw new CashierException(ServerResponseEnum.CASHIER_DELETE_FAIL);
        }
        return 1;
    }

    @Override
    public int deleteCashierAndUserBatch(List<Cashier> cashiers, List<User> users) {
        try {
            Integer a;
            a = cashierMapper.batchCashierDelete(cashiers);
            a = userService.deleteUserBatch(users)+a;
            return a;
        }catch (Exception e){
            e.printStackTrace();
            throw new CashierException(ServerResponseEnum.CASHIER_DELETE_FAIL);
        }
    }
}
