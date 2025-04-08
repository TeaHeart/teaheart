package org.example.model;

import lombok.Getter;

@Getter
public enum Privilege {
    USER_LOGIN(1 << 0), USER_CHANGE_PASSWORD(1 << 1),

    USER_INSERT(1 << 4), USER_DELETE(1 << 5), USER_UPDATE(1 << 6), USER_SELECT(1 << 7),

    GOODS_INSERT(1 << 8), GOODS_DELETE(1 << 9), GOODS_UPDATE(1 << 10), GOODS_SELECT(1 << 11),

    STOCK_INSERT(1 << 12), STOCK_DELETE(1 << 13), STOCK_UPDATE(1 << 14), STOCK_SELECT(1 << 15);

    private final int privilege;

    Privilege(int privilege) {
        this.privilege = privilege;
    }

    public boolean hasPrivilege(int mask) {
        return (mask & privilege) != 0;
    }

    public int setPrivilege(int mask) {
        return mask | privilege;
    }

    public int resetPrivilege(int mask) {
        return mask & ~privilege;
    }
}
