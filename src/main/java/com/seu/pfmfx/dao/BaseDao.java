package com.seu.pfmfx.dao;

import com.seu.pfmfx.util.ConnectionProvider;

import java.sql.Connection;

public abstract class BaseDao {
    protected Connection getConnection() {
        return ConnectionProvider.getSingleton().getConnection();
    }
}
