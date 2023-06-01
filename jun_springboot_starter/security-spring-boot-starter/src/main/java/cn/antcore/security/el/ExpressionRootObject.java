package cn.antcore.security.el;

/**
 * 表达式根节点
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/30</p>
 **/
public class ExpressionRootObject {
    
    private final Object object;
    private final Object[] args;

    public ExpressionRootObject(Object object, Object[] args) {
        this.object = object;
        this.args = args;
    }

    public Object getObject() {
        return object;
    }

    public Object[] getArgs() {
        return args;
    }
}
