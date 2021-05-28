package org.typroject.tyboot.core.rdbms.annotation;

/**
 *
 *条件操作符，目前只支持最常用的。
 * Created by magintursh on 2017-06-29.
 */
public enum Operator
{
    eq,//eq	等于=
    ne,//不等于，例: ne("name", "老王")--->name <> '老王'
    like,//模糊查询 like
    notLike,//notLike("name", "王")--->name not like '%王%'
    likeLeft,//例: likeLeft("name", "王")--->name like '%王'
    likeRight,//例: likeRight("name", "王")--->name like '王%'
    in,   //IN操作符的 对应的参数类型必须是Collection 的子类 参数名与 类属性一致
    notIn,//例: notIn("age",{1,2,3})--->age not in (1,2,3)
    between,  //between 条件语句, 对应的参数类型必须是数组类型 参数名与 类属性一致
    notBetween,//

    isNull,//例: isNull("name")--->name is null
    isNotNull,//例: isNotNull("name")--->name is not null
    gt,//  gt	大于>
    ge,//  ge	大于等于>=
    lt,//  lt	小于<
    le//   le	小于等于<=

}
