package com.gxf.his.service;

import com.gxf.his.po.vo.CashierVo;
import com.gxf.his.po.generate.Cashier;
import com.gxf.his.po.generate.User;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2020-01-19
 */
public interface CashierService {
    /**
     * 添加一位收银员
     *
     * @param cashier 收银员对象
     */
    int addCashier(Cashier cashier);

    /**
     * 获取所有收银员列表
     *
     * @return 收银员列表
     */
    List<CashierVo> getAllCashiers();

    /**
     * 根据属性进行模糊或者精确查询收银员列表
     *
     * @param cashierVo 查询信息
     * @return 收银员列表
     */
    List<CashierVo> selectCashierByAttribute(CashierVo cashierVo);

    /**
     * 更新某个收银员
     *
     * @param cashier 收银员对象
     * @return 本次操作影响的行数
     */
    int updateCashier(Cashier cashier);

    /**
     * 删除某个收银员，包括收银员对象与对应的收银员用户
     *
     * @param cashierId 收银员对象的ID
     * @param userId    收银员用户的ID
     * @return 本次操作影响的行数
     */
    int deleteCashierAndUser(Long cashierId, Long userId);

    /**
     * 批量删除收银员对象与对应的收银员用户
     *
     * @param cashiers 收银员对象列表
     * @param users    收银员用户列表
     * @return 本次操作影响的行数
     */
    int deleteCashierAndUserBatch(List<Cashier> cashiers, List<User> users);
}
