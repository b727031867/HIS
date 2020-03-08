package com.gxf.his.service;

import com.gxf.his.controller.UserController;
import com.gxf.his.po.generate.Cashier;
import com.gxf.his.po.generate.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author 龚秀峰
 * @date 2020-01-19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CashierServiceTest {
    @Autowired
    CashierService cashierService;
    @Autowired
    UserService userService;

    @Test
    public void testAddCashier() {
        for (int i = 0; i < 300; i++) {
            Cashier cashier = new Cashier();
            String userName = "testCashier" + i;
            String password = "123456";
            User user = UserController.doHashedCredentials(userName, password);
            userService.addUser(user);
            cashier.setPhone(PatientServiceTest.getPhoneNumber());
            cashier.setName(PatientServiceTest.getName());
            cashier.setEntryDate(new Date());
            cashier.setDepartmentCode("0015");
            cashier.setUserId(user.getUserId());
            cashierService.addCashier(cashier);
        }

    }

}
