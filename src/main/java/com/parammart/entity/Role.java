package com.parammart.entity;


import java.util.Set;

public enum Role {

    ADMIN(Set.of(

            Permission.USER_CREATE,
            Permission.USER_READ,
            Permission.USER_UPDATE,
            Permission.USER_DELETE,

            Permission.PRODUCT_CREATE,
            Permission.PRODUCT_READ,
            Permission.PRODUCT_UPDATE,
            Permission.PRODUCT_DELETE,

            Permission.CATEGORY_CREATE,
            Permission.CATEGORY_READ,
            Permission.CATEGORY_UPDATE,
            Permission.CATEGORY_DELETE,

            Permission.BRAND_CREATE,
            Permission.BRAND_READ,
            Permission.BRAND_UPDATE,
            Permission.BRAND_DELETE,

            Permission.ORDER_CREATE,
            Permission.ORDER_READ,
            Permission.ORDER_UPDATE,
            Permission.ORDER_DELETE,

            Permission.REPORT_READ
    )),

    MANAGER(Set.of(

            Permission.PRODUCT_CREATE,
            Permission.PRODUCT_READ,
            Permission.PRODUCT_UPDATE,

            Permission.CATEGORY_CREATE,
            Permission.CATEGORY_READ,
            Permission.CATEGORY_UPDATE,

            Permission.BRAND_CREATE,
            Permission.BRAND_READ,
            Permission.BRAND_UPDATE,

            Permission.ORDER_READ,
            Permission.ORDER_UPDATE,

            Permission.REPORT_READ
    )),

    EMPLOYEE(Set.of(

            Permission.PRODUCT_READ,

            Permission.CATEGORY_READ,

            Permission.BRAND_READ,

            Permission.ORDER_CREATE,
            Permission.ORDER_READ,
            Permission.ORDER_UPDATE
    )),

    CUSTOMER(Set.of(

            Permission.PRODUCT_READ,

            Permission.CATEGORY_READ,

            Permission.BRAND_READ,

            Permission.ORDER_CREATE,
            Permission.ORDER_READ
    ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}