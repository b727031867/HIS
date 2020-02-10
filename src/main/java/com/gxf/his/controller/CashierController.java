package com.gxf.his.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.vo.CashierVo;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.po.generate.Cashier;
import com.gxf.his.po.generate.User;
import com.gxf.his.service.CashierService;
import com.gxf.his.service.UserService;
import com.gxf.his.uitls.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2020-01-19
 */
@RestController
@RequestMapping("/cashier")
@Slf4j
public class CashierController extends BaseController {
    @Resource
    private CashierService cashierService;
    @Resource
    private UserService userService;

    @GetMapping
    public <T> ServerResponseVO<T> getCashiers(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        PageHelper.startPage(page, size);
        List<CashierVo> cashierVos = cashierService.getAllCashiers();
        PageInfo<CashierVo> pageInfo = PageInfo.of(cashierVos);
        return MyUtil.cast(ServerResponseVO.success(pageInfo));
    }

    @GetMapping("/attribute")
    public <T> ServerResponseVO<T> getCashiersByAttribute(@RequestParam(value = "attribute", defaultValue = "patientName") String attribute
            , @RequestParam(value = "isAccurate") Boolean isAccurate, @RequestParam(value = "value") String value, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        if(searchParamCheck(attribute,isAccurate,value)){
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        CashierVo cashierVo = new CashierVo();
        cashierVo.setSearchAttribute(attribute.trim());
        cashierVo.setValue(value.trim());
        cashierVo.setIsAccurate(isAccurate);
        PageHelper.startPage(page, size);
        List<CashierVo> patients = cashierService.selectCashierByAttribute(cashierVo);
        PageInfo<CashierVo> pageInfo = PageInfo.of(patients);
        return MyUtil.cast(ServerResponseVO.success(pageInfo));
    }

    @PutMapping
    public <T> ServerResponseVO<T> saveCashierAndUser(@RequestBody CashierVo cashierVo) {
        log.info("当前更新的收银员信息为：" + cashierVo.toString());
        Cashier cashier = new Cashier();
        cashier.setCashierId(cashierVo.getCashierId());
        cashier.setDepartmentCode(cashierVo.getDepartment().getDepartmentCode());
        cashier.setEntryDate(cashierVo.getEntryDate());
        cashier.setName(cashierVo.getName());
        cashier.setPhone(cashierVo.getPhone());
        cashier.setUserId(cashierVo.getUser().getUserId());
        //更新收银员信息
        cashierService.updateCashier(cashier);
        //更新用户信息
        User user = cashierVo.getUser();
        userService.updateUser(user);
        return ServerResponseVO.success();
    }

    @PostMapping
    public <T> ServerResponseVO<T> saveCashier(@RequestBody CashierVo cashierVo) {
        if (StringUtils.isEmpty(cashierVo.getUser().getUserName().trim()) ||
                StringUtils.isEmpty(cashierVo.getUser().getUserPassword().trim()) ||
                StringUtils.isEmpty(cashierVo.getPhone().trim()) ||
                StringUtils.isEmpty(cashierVo.getName().trim())
        ) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        //如果此用户名对应多个用户，则会抛出异常
        userService.findByUserName(cashierVo.getUser().getUserName());
        User user = UserController.doHashedCredentials(cashierVo.getUser().getUserName(), cashierVo.getUser().getUserPassword());
        userService.addUser(user);
        Cashier cashier = new Cashier();
        cashier.setDepartmentCode(cashierVo.getDepartment().getDepartmentCode());
        cashier.setEntryDate(cashierVo.getEntryDate());
        cashier.setName(cashierVo.getName());
        cashier.setPhone(cashierVo.getPhone());
        cashier.setUserId(user.getUserId());
        cashierService.addCashier(cashier);
        return ServerResponseVO.success();
    }

    @DeleteMapping
    public <T> ServerResponseVO<T> deleteCashierAndUserByCashierId(@RequestParam(name = "cashierId") Long cashierId,
                                                            @RequestParam(name = "userId") Long userId) {
        try {
            cashierService.deleteCashierAndUser(cashierId, userId);
            return ServerResponseVO.success();
        } catch (Exception e) {
            return ServerResponseVO.error(ServerResponseEnum.CASHIER_DELETE_FAIL);
        }
    }

    @DeleteMapping("/batch")
    public <T> ServerResponseVO<T> deleteCashiersAndUsersByIds(@RequestBody List<CashierVo> cashierVos) {
        List<Cashier> cashiers = new ArrayList<>(16);
        List<User> users = new ArrayList<>(16);
        for (CashierVo cashierVo : cashierVos) {
            Cashier cashier = new Cashier();
            cashier.setCashierId(cashierVo.getCashierId());
            cashiers.add(cashier);
            users.add(cashierVo.getUser());
        }
        cashierService.deleteCashierAndUserBatch(cashiers, users);
        return ServerResponseVO.success();
    }
}
