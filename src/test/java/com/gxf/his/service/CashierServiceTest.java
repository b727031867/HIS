package com.gxf.his.service;

import com.gxf.his.controller.UserController;
import com.gxf.his.po.Cashier;
import com.gxf.his.po.Patient;
import com.gxf.his.po.User;
import com.gxf.his.uitls.DataGeneratorUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

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
    public void testAddCashier(){
        for(int i=0;i<300;i++){
            Cashier cashier =new Cashier();
            String userName = "testCashier"+i;
            String password = "test";
            User user = UserController.doHashedCredentials(userName,password);
            Long userId = userService.addUser(user);
            cashier.setPhone(PatientServiceTest.getPhoneNumber());
            cashier.setName(PatientServiceTest.getName());
            cashier.setEntryDate(new Date());
            cashier.setDepartmentCode("0015");
            cashier.setUserId(userId);
            cashierService.addCashier(cashier);
        }

    }

}
