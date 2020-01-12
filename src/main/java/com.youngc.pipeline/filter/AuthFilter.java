package com.youngc.pipeline.filter;

import com.youngc.pipeline.bean.context.UserBean;
import com.youngc.pipeline.model.UserManagerModel;
import com.youngc.pipeline.service.login.AuthService;
import com.youngc.pipeline.service.system.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author
 *
 */
@Component
@WebFilter(urlPatterns = { "/**" }, filterName = "authFilter")
public class AuthFilter implements Filter {

    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserManagerService userManagerService;

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<String>(
            Arrays.asList("/auth/login", "/auth/logout", "/register","/auth/demo","/update/updateteacherpassword","/update/chackemail","/update/sendemail","/Teacher/DownLoadExcel")));

    private static final Set<String> TOKEN_IN_URL_PATHS = Collections.unmodifiableSet(new HashSet<String>(
            Arrays.asList("/export","/TeacherSecretary/excelAddStudent")));


    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //设置允许跨域的配置
        // 这里填写你允许进行跨域的主机
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许的访问方法
        response.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, OPTIONS, DELETE, PATCH");
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers","content-type, X-Auth-Token");

        //test
        if (env.equals("dev")) {
            UserBean userBean = new UserBean();

            userBean.setUserId(1);
            userBean.setUserName("test");

            request.setAttribute("user", userBean);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        //

        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");

        if (request.getMethod().toUpperCase().equals("OPTIONS")
                || ALLOWED_PATHS.contains(path)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token;
        if (TOKEN_IN_URL_PATHS.contains(path)) {
            token = request.getParameter("token");
        }
        else {
            token = request.getHeader("X-Auth-Token");
        }
        System.out.println(path);
        System.out.println(token);
        if (token == null || token.equals("") || !authService.isTokenExist(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Long userId = authService.getUserIdByToken(token);
        UserManagerModel userManagerModel = userManagerService.getUserDetails(userId);

        UserBean userBean = new UserBean();

        //userBean.setUserId(userManagerModel.getUserId());
        //userBean.setUserName(userManagerModel.getUserName());
        //userBean.setToken(token);
        // 数据库中没有相关字段
        //userBean.setUserRole("user");


        request.setAttribute("user", userBean);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }

}
