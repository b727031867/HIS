package com.gxf.his.mapper;

import com.gxf.his.po.Cashier;
import com.gxf.his.po.Doctor;
import com.gxf.his.po.Patient;
import com.gxf.his.po.User;
import com.gxf.his.vo.DoctorUserVo;

import java.util.List;
import java.util.Map;

/**
 * @author 龚秀峰
 * @date 2019-12-29
 */

public class Batch {

    /**
     * 批量删除医生
     * @param map 传入的对象
     * @return 批量删除的sql语句
     */
    public String batchDoctorDelete(Map map) {
        List<Doctor> doctors = (List<Doctor>) map.get("list");
        StringBuffer sb = new StringBuffer();
        sb.append("delete from entity_doctor where doctor_id in (");
        for (int i = 0; i < doctors.size(); i++) {
            sb.append("'").append(doctors.get(i).getDoctorId()).append("'");
            if (i < doctors.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 批量删除患者
     * @param map 传入的对象
     * @return 批量删除的sql语句
     */
    public String batchPatientDelete(Map map) {
        List<Patient> patients = (List<Patient>) map.get("list");
        StringBuffer sb = new StringBuffer();
        sb.append("delete from entity_patient where patient_id in (");
        for (int i = 0; i < patients.size(); i++) {
            sb.append("'").append(patients.get(i).getPatientId()).append("'");
            if (i < patients.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 批量删除收银员
     * @param map 传入的对象
     * @return 批量删除的sql语句
     */
    public String batchCashierDelete(Map map) {
        List<Cashier> cashiers = (List<Cashier>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("delete from entity_cashier where cashier_id in (");
        for (int i = 0; i < cashiers.size(); i++) {
            sb.append("'").append(cashiers.get(i).getCashierId()).append("'");
            if (i < cashiers.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }



    /**
     * 批量删除用户
     * @param map 传入的对象
     * @return 批量删除的sql语句
     */
    public String batchUserDelete(Map map) {
        List<User> users = (List<User>) map.get("list");
        StringBuffer sb = new StringBuffer();
        sb.append("delete from entity_user where user_id in (");
        for (int i = 0; i < users.size(); i++) {
            sb.append("'").append(users.get(i).getUserId()).append("'");
            if (i < users.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

}
