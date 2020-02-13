package com.gxf.his.po.generate;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Cashier implements Serializable {
    private Long cashierId;

    private String name;

    private String phone;

    private Date entryDate;

    private String departmentCode;

    private Long userId;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cashierId=").append(cashierId);
        sb.append(", name=").append(name);
        sb.append(", phone=").append(phone);
        sb.append(", entryDate=").append(entryDate);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}