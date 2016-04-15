package com.example.vdllo.mirror.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.vdllo.mirror.base.BaseApplication;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by dllo on 16/4/14.
 */
public class DaoSingleton {
    private static final String DATABASE_NAME = "mirrorDao.db";
    private volatile static DaoSingleton instance;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Context context;
    private DaoMaster.DevOpenHelper helper;
    private MirrorEntityDao mirrorEntityDao;

    /**
     * 私有化构造方法
     */
    private DaoSingleton() {
        context = BaseApplication.getContext();
    }

    /**
     * 对外暴露一个方法，获得实例 instance
     *
     * @return instance
     */
    public static DaoSingleton getInstance() {
        if (instance == null) {
            synchronized (DaoSingleton.class) {
                if (instance == null) {
                    instance = new DaoSingleton();
                }
            }
        }
        return instance;
    }

    /**
     * 获得DevOpenHelper
     *
     * @return helper
     */
    public DaoMaster.DevOpenHelper getHelper() {
        if (helper == null) {
            helper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);

        }
        return helper;
    }

    /**
     * 获得可操作数据库对象
     *
     * @return db
     */
    private SQLiteDatabase getDb() {
        if (db == null) {
            db = getHelper().getWritableDatabase();
        }
        return db;
    }

    private DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            daoMaster = new DaoMaster(getDb());
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            daoSession = getDaoMaster().newSession();
        }
        return daoSession;
    }

    public MirrorEntityDao getMirrorDao() {
        if (mirrorEntityDao == null) {
            mirrorEntityDao = getDaoSession().getMirrorEntityDao();
        }
        return mirrorEntityDao;
    }

    /**
     * insert 插入 通过id判断一下是否重复
     */
    public void insertSingleData(MirrorEntity entity) {
        QueryBuilder<MirrorEntity> query = mirrorEntityDao.queryBuilder().where(MirrorEntityDao.Properties.Id.eq(entity.getId()));
        if (query == null) {
            getMirrorDao().insert(entity);
        }
    }

    /**
     * 插入集合
     *
     * @param entities
     */
    public void insertList(List<MirrorEntity> entities) {
        getMirrorDao().insertOrReplaceInTx(entities);
    }

    /**
     * 删除 根据Id删除
     */
    public void deleteByKey(MirrorEntity entity) {
        getMirrorDao().deleteByKey(entity.getId());
    }

    
    public void delete(MirrorEntity entity) {
        getMirrorDao().delete(entity);
    }

    /**
     * 全部删除
     */
    public void deleteAll() {
        getMirrorDao().deleteAll();
    }

    /**
     * 查询 根据id
     */
    public List<MirrorEntity> queryList(long id) {
        QueryBuilder<MirrorEntity> qb = getMirrorDao().queryBuilder();
        qb.where(MirrorEntityDao.Properties.Id.eq(id));
        return qb.list();
    }

}
