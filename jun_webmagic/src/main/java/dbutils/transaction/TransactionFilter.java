package dbutils.transaction;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbutils.DBUtils;

/**
* @ClassName: TransactionFilter
* @Description:ThreadLocal + Filter 统一处理数据库事务
* @date: 2014-10-6 下午11:36:58
*
*/ 
public class TransactionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        Connection connection = null;
        try {
            //1、获取数据库连接对象Connection
            connection = DBUtils.getConnection();
            //2、开启事务
            connection.setAutoCommit(false);
            //3、利用ThreadLocal把获取数据库连接对象Connection和当前线程绑定
            ConnectionContext.getInstance().bind(connection);
            //4、把请求转发给目标Servlet
            chain.doFilter(request, response);
            //5、提交事务
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //6、回滚事务
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            //req.setAttribute("errMsg", e.getMessage());
            //req.getRequestDispatcher("/error.jsp").forward(req, res);
            //出现异常之后跳转到错误页面
            res.sendRedirect(req.getContextPath()+"/error.jsp");
        }finally{
            //7、解除绑定
            ConnectionContext.getInstance().remove();
            //8、关闭数据库连接
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {

    }
}