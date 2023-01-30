package com.cyn.permission_demo.config;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;

/**
 * @author Godc
 * @description: SecurityExpressionRoot用来处理@PreAuthorize()注解中的表达式。
 * @date 2023/1/29 13:08
 */
public class CustomSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    /**
     * 自定义EL表达式用于判断 当前用户是否具有权限。
     * <br>支持ant风格通配符
     *
     * @param permission:当前方法需要具备的权限
     * @return
     */
    public boolean hasPermission(String permission) {
        Collection<? extends GrantedAuthority> authorities = getGrantedAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (antPathMatcher.match(authority.getAuthority(), permission)) return true;
        }
        return false;
    }

    /**
     * 当前用户是否具有所需权限的任意一个
     *
     * @param permissions
     * @return
     */
    public boolean hasAnyPermission(String... permissions) {
        if (permissions == null || permissions.length == 0) {
            // 如果无所需权限意味着任意都能访问
            return true;
        }
        Collection<? extends GrantedAuthority> authorities = getGrantedAuthorities();
        for (GrantedAuthority authority : authorities) {
            for (String permission : permissions) {
                if (antPathMatcher.match(authority.getAuthority(), permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 当前用户是否具有所有权限
     *
     * @param permissions
     * @return
     */
    public boolean hasAllPermission(String... permissions) {
        Collection<? extends GrantedAuthority> authorities = getGrantedAuthorities();
        if (permissions == null || permissions.length == 0) {
            return true;
        }
        for (GrantedAuthority authority : authorities) {
            boolean flag = false;
            for (String permission : permissions) {
                if (antPathMatcher.match(authority.getAuthority(), permission)) {
                    flag = true;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities;
    }

    /**
     * Creates a new instance
     *
     * @param authentication the {@link Authentication} to use. Cannot be null.
     */
    public CustomSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
