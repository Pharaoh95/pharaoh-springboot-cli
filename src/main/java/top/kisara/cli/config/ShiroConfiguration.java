package top.kisara.cli.config;

import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;

import java.util.HashMap;

@Configuration
@Log4j2
public class ShiroConfiguration {
    /**
     * 自定义 - 数据域
     */
    private class MyShiroRealm extends AuthorizingRealm {
        /**
         * @param principalCollection
         * @return
         * @implNote 功能授权
         */
        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
            String name = (String) principalCollection.getPrimaryPrincipal();
            log.info("principalCollection.getPrimaryPrincipal():" + name);
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            //从数据库查询功能权限，这里测试时期直接写死
            authorizationInfo.addRole("admin");
            authorizationInfo.addStringPermission("user-get");
            log.error("authorizationInfo:" + authorizationInfo);
            return authorizationInfo;
        }

        /**
         * @param authenticationToken
         * @return
         * @throws AuthenticationException
         * @implNote 身份认证
         */
        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
            String name = (String) authenticationToken.getPrincipal();
            log.info("authenticationToken.getPrincipal()：" + name);
            if (name == null || !name.equals("pharaoh")) {
                return null;
            } else {
                SimpleAuthenticationInfo simpleAuthenticationInfo =
                        new SimpleAuthenticationInfo(name, "19e27b1bc502d62b3b572682d5da824d", this.getName());
                return simpleAuthenticationInfo;
            }
        }
    }

    /**
     * @param securityManager
     * @return 拦截工厂配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        HashMap<String, String> filterMap = new HashMap<>();
        filterMap.put("/api/**", "authc");
        filterMap.put("/logout", "logout");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }

    /**
     * @return 安全管理器
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(credentialsMatcher());
        return myShiroRealm;
    }

    /**
     * @return 密码匹配器
     */
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        return hashedCredentialsMatcher;
    }

    /**
     * @param securityManager
     * @return 授权注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}