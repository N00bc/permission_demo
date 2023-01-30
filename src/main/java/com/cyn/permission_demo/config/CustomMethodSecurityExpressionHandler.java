package com.cyn.permission_demo.config;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;

/**
 * @author Godc
 * @description:提供SecurityExpressionRoot对象
 * @date 2023/1/29 13:08
 */
public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {
    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        CustomSecurityExpressionRoot root = new CustomSecurityExpressionRoot(authentication);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(getTrustResolver());
        root.setRoleHierarchy(getRoleHierarchy());
        return root;
    }
}
