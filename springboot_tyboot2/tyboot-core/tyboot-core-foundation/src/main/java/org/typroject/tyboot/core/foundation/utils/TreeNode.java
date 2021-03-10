package org.typroject.tyboot.core.foundation.utils;

/**
 * Created by magintursh on 2017/12/3.
 */

import java.util.Collection;

/**
 * 树结构的节点，要使用TreeBuilder进行转换，先要实现此接口
 * @param <N>
 * @param <ID>
 */
public interface TreeNode<N,ID> extends Comparable<N> {
    ID getMyParentId();

    ID getMyId();

    Collection<N> getChildren();

    void setChildren(Collection<N> childs);
}
