/*
 * This file is generated by jOOQ.
*/
package com.mycompany.myproject.jooq.tables;


import com.mycompany.myproject.jooq.Barry;
import com.mycompany.myproject.jooq.Indexes;
import com.mycompany.myproject.jooq.Keys;
import com.mycompany.myproject.jooq.tables.records.WelcomeRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Welcome extends TableImpl<WelcomeRecord> {

    private static final long serialVersionUID = 397797684;

    /**
     * The reference instance of <code>barry.welcome</code>
     */
    public static final Welcome WELCOME = new Welcome();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WelcomeRecord> getRecordType() {
        return WelcomeRecord.class;
    }

    /**
     * The column <code>barry.welcome.id</code>.
     */
    public final TableField<WelcomeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>barry.welcome.welcome_msg</code>.
     */
    public final TableField<WelcomeRecord, String> WELCOME_MSG = createField("welcome_msg", org.jooq.impl.SQLDataType.VARCHAR(128), this, "");

    /**
     * The column <code>barry.welcome.create_by</code>.
     */
    public final TableField<WelcomeRecord, String> CREATE_BY = createField("create_by", org.jooq.impl.SQLDataType.VARCHAR(45).nullable(false), this, "");

    /**
     * The column <code>barry.welcome.create_date</code>. 创建时间
     */
    public final TableField<WelcomeRecord, Timestamp> CREATE_DATE = createField("create_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>barry.welcome.update_by</code>.
     */
    public final TableField<WelcomeRecord, String> UPDATE_BY = createField("update_by", org.jooq.impl.SQLDataType.VARCHAR(45).nullable(false), this, "");

    /**
     * The column <code>barry.welcome.update_date</code>. 修改时间
     */
    public final TableField<WelcomeRecord, Timestamp> UPDATE_DATE = createField("update_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * Create a <code>barry.welcome</code> table reference
     */
    public Welcome() {
        this(DSL.name("welcome"), null);
    }

    /**
     * Create an aliased <code>barry.welcome</code> table reference
     */
    public Welcome(String alias) {
        this(DSL.name(alias), WELCOME);
    }

    /**
     * Create an aliased <code>barry.welcome</code> table reference
     */
    public Welcome(Name alias) {
        this(alias, WELCOME);
    }

    private Welcome(Name alias, Table<WelcomeRecord> aliased) {
        this(alias, aliased, null);
    }

    private Welcome(Name alias, Table<WelcomeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Barry.BARRY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.WELCOME_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<WelcomeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_WELCOME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<WelcomeRecord> getPrimaryKey() {
        return Keys.KEY_WELCOME_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<WelcomeRecord>> getKeys() {
        return Arrays.<UniqueKey<WelcomeRecord>>asList(Keys.KEY_WELCOME_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Welcome as(String alias) {
        return new Welcome(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Welcome as(Name alias) {
        return new Welcome(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Welcome rename(String name) {
        return new Welcome(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Welcome rename(Name name) {
        return new Welcome(name, null);
    }
}
