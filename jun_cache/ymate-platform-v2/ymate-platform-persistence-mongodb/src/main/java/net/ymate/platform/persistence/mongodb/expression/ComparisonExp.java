/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.persistence.mongodb.expression;

import net.ymate.platform.persistence.Params;
import net.ymate.platform.persistence.mongodb.AbstractOperator;
import net.ymate.platform.persistence.mongodb.IMongo;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/27 下午6:17
 * @version 1.0
 */
public class ComparisonExp extends AbstractOperator {

    public static ComparisonExp cmp(Object exp1, Object exp2) {
        ComparisonExp _exp = new ComparisonExp();
        _exp.__doAddOperator(IMongo.OPT.CMP, new Object[]{exp1, exp2});
        return _exp;
    }

    public static ComparisonExp eq(Object param) {
        ComparisonExp _exp = new ComparisonExp();
        _exp.__doAddOperator(IMongo.OPT.EQ, param);
        return _exp;
    }

    public static ComparisonExp eq(Params params) {
        ComparisonExp _exp = new ComparisonExp();
        _exp.__doAddOperator(IMongo.OPT.EQ, params);
        return _exp;
    }

    public static ComparisonExp ne(Object param) {
        ComparisonExp _c = new ComparisonExp();
        _c.__doAddOperator(IMongo.OPT.NE, param);
        return _c;
    }

    public static ComparisonExp ne(Params params) {
        ComparisonExp _exp = new ComparisonExp();
        _exp.__doAddOperator(IMongo.OPT.NE, params);
        return _exp;
    }

    public static ComparisonExp gt(Object param) {
        ComparisonExp _exp = new ComparisonExp();
        _exp.__doAddOperator(IMongo.OPT.GT, param);
        return _exp;
    }

    public static ComparisonExp gt(Params params) {
        ComparisonExp _exp = new ComparisonExp();
        _exp.__doAddOperator(IMongo.OPT.GT, params);
        return _exp;
    }

    public static ComparisonExp gte(Object param) {
        ComparisonExp _exp = new ComparisonExp();
        _exp.__doAddOperator(IMongo.OPT.GTE, param);
        return _exp;
    }

    public static ComparisonExp gte(Params params) {
        ComparisonExp _exp = new ComparisonExp();
        _exp.__doAddOperator(IMongo.OPT.GTE, params);
        return _exp;
    }

    public static ComparisonExp lt(Object param) {
        ComparisonExp _exp = new ComparisonExp();
        _exp.__doAddOperator(IMongo.OPT.LT, param);
        return _exp;
    }

    public static ComparisonExp lt(Params params) {
        ComparisonExp _exp = new ComparisonExp();
        _exp.__doAddOperator(IMongo.OPT.LT, params);
        return _exp;
    }

    public static ComparisonExp lte(Object param) {
        ComparisonExp _exp = new ComparisonExp();
        _exp.__doAddOperator(IMongo.OPT.LTE, param);
        return _exp;
    }

    public static ComparisonExp lte(Params params) {
        ComparisonExp _exp = new ComparisonExp();
        _exp.__doAddOperator(IMongo.OPT.LTE, params);
        return _exp;
    }

    public static ComparisonExp in(Params values) {
        ComparisonExp _exp = new ComparisonExp();
        _exp.__doAddOperator(IMongo.OPT.IN, values.toArray());
        return _exp;
    }

    public static ComparisonExp nin(Params values) {
        ComparisonExp _exp = new ComparisonExp();
        _exp.__doAddOperator(IMongo.OPT.NIN, values.toArray());
        return _exp;
    }
}
