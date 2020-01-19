package com.gxf.his.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxf.his.config.RedisClient;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.Cashier;
import com.gxf.his.po.User;
import com.gxf.his.service.CashierService;
import com.gxf.his.service.UserService;
import com.gxf.his.vo.CashierUserVo;
import com.gxf.his.vo.ServerResponseVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2020-01-19
 */
@RestController
@RequestMapping("/cashier")
public class CashierController {
    private Logger logger = LoggerFactory.getLogger(CashierController.class);

    @Autowired
    public CashierController(CashierService cService, UserService uService, RedisClient redis) {
        this.redis = redis;
        cashierService = cService;
        userService = uService;
    }

    private RedisClient redis;

    private CashierService cashierService;

    private UserService userService;


    @GetMapping
    public ServerResponseVO getCashiers(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        PageHelper.startPage(page, size);
        List<CashierUserVo> cashierUserVos = cashierService.getAllCashiers();
        PageInfo<CashierUserVo> pageInfo = PageInfo.of(cashierUserVos);
        return ServerResponseVO.success(pageInfo);
    }

    @GetMapping("/attribute")
    public ServerResponseVO getCashiersByAttribute(@RequestParam(value = "attribute",defaultValue = "patientName") String attribute
            , @RequestParam(value = "isAccurate") Boolean isAccurate , @RequestParam(value = "value") String value , @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        if(value == null || value.trim().length() == 0 || isAccurate == null){
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        CashierUserVo cashierUserVo =new CashierUserVo();
        cashierUserVo.setSearchAttribute(attribute.trim());
        cashierUserVo.setValue(value.trim());
        cashierUserVo.setIsAccurate(isAccurate);
        PageHelper.startPage(page, size);
        List<CashierUserVo> patients = cashierService.selectCashierByAttribute(cashierUserVo);
        PageInfo<CashierUserVo> pageInfo = PageInfo.of(patients);
        return ServerResponseVO.success(pageInfo);
    }

    @PutMapping
    public ServerResponseVO saveCashierAndUser(@RequestBody CashierUserVo cashierUserVo) {
        logger.info("当前更新的收银员信息为：" + cashierUserVo.toString());
        Cashier cashier = new Cashier();
        cashier.setCashierId(cashierUserVo.getCashierId());
        cashier.setDepartmentCode(cashierUserVo.getDepartment().getDepartmentCode());
        cashier.setEntryDate(cashierUserVo.getEntryDate());
        cashier.setName(cashierUserVo.getName());
        cashier.setPhone(cashierUserVo.getPhone());
        cashier.setUserId(cashierUserVo.getUser().getUserId());
        //更新收银员信息
        cashierService.updateCashier(cashier);
        //更新用户信息
        User user = cashierUserVo.getUser();
        userService.updateUser(user);
        return ServerResponseVO.success();
    }

    @PostMapping
    public ServerResponseVO saveCashier(@RequestBody CashierUserVo cashierUserVo) {
        if (StringUtils.isEmpty(cashierUserVo.getUser().getUserName().trim()) ||
                StringUtils.isEmpty(cashierUserVo.getUser().getUserPassword().trim()) ||
                StringUtils.isEmpty(cashierUserVo.getPhone().trim()) ||
                StringUtils.isEmpty(cashierUserVo.getName().trim())
        ) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        //如果此用户名对应多个用户，则会抛出异常
        userService.findByUserName(cashierUserVo.getUser().getUserName());
        User user = UserController.doHashedCredentials(cashierUserVo.getUser().getUserName(), cashierUserVo.getUser().getUserPassword());
        Long userId = userService.addUser(user);
        Cashier cashier = new Cashier();
        cashier.setDepartmentCode(cashierUserVo.getDepartment().getDepartmentCode());
        cashier.setEntryDate(cashierUserVo.getEntryDate());
        cashier.setName(cashierUserVo.getName());
        cashier.setPhone(cashierUserVo.getPhone());
        cashier.setUserId(userId);
        cashierService.addCashier(cashier);
        return ServerResponseVO.success();
    }

    @DeleteMapping
    public ServerResponseVO deleteCashierAndUserByCashierId(@RequestParam(name = "cashierId") Long cashierId,
                                                          @RequestParam(name = "userId") Long userId) {
        try {
            cashierService.deleteCashierAndUser(cashierId, userId);
            return ServerResponseVO.success();
        } catch (Exception e) {
            return ServerResponseVO.error(ServerResponseEnum.CASHIER_DELETE_FAIL);
        }
    }

    @DeleteMapping("/batch")
    public ServerResponseVO deleteCashiersAndUsersByIds(@RequestBody List<CashierUserVo> cashierUserVos){
        List<Cashier> cashiers = new ArrayList<>(16);
        List<User> users = new ArrayList<>(16);
        for(CashierUserVo cashierUserVo : cashierUserVos){
            Cashier cashier = new Cashier();
            cashier.setCashierId(cashierUserVo.getCashierId());
            cashiers.add(cashier);
            users.add(cashierUserVo.getUser());
        }
        cashierService.deleteCashierAndUserBatch(cashiers,users);
        return ServerResponseVO.success();
    }
}
