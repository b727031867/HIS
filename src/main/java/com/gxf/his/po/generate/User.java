package com.gxf.his.po.generate;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class User implements Serializable {
    private Long userId;

    private String userName;

    private String userSalt;

    private String userPassword;

    private String userType;

    private Byte userStatus;

    private Date userCreateDate;

    private Long appId;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", userSalt=").append(userSalt);
        sb.append(", userPassword=").append(userPassword);
        sb.append(", userType=").append(userType);
        sb.append(", userStatus=").append(userStatus);
        sb.append(", userCreateDate=").append(userCreateDate);
        sb.append(", appId=").append(appId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}