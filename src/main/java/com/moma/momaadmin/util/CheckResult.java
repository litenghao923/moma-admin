package com.moma.momaadmin.util;

import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * jwt验证
 */
@Data
public class CheckResult {

    private int errCode;

    private boolean success;

    private Claims claims;

}
