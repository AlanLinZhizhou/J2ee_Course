package com.qingguatang.petchase_12_3.tables;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;


@Repository
public class SimpleService<T, ID extends Serializable> implements BaseAppDao<T, ID> {

    private static Logger log = (Logger) Logger.getLogger(String.valueOf(SimpleService.class));


    @PersistenceContext
    private EntityManager entityManager;


    /**
     * 插入数据
     */
    @Transactional
    @Override
    public boolean insert(T entity) {
        boolean flag = false;
        try {
            entityManager.persist(entity);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     *      * 根据id查找数据
     *      
     */
    @Transactional
    @Override
    public Object findById(Object obj, Long id) {
        return entityManager.find(obj.getClass(), id);
    }

    /**
     *      * 根据表名，字段，参数查询，拼接sql语句
     *      
     */
    @Transactional
    @Override
    public List<T> findByHql(String tableName, String filed, Object o) {
        String sql = "from " + tableName + " where " + filed + " =?";
//        log.warn(sql);
        Query query = entityManager.createQuery(sql);
        query.setParameter(1, o);
        List<T> result = query.getResultList();
        entityManager.close();
        return result;
    }


    @Transactional
    @Override
    public Object findObjiectByHql(String tablename, String filed, Object o) {
        Query query = entityManager.createQuery("from " + tablename + " where " + filed + " =?");
        query.setParameter(1, o);
        entityManager.close();
        return query.getSingleResult();
    }


    /**
     *      * 多个字段的查询
     *      * @param tablename 表名
     *      * @param map 将你的字段传入map中
     *      * key:字段 ,value:值
     *      * @return
     *      
     */
    @Transactional
    @Override
    public List<T> findByMoreFiled(String tablename, LinkedHashMap<String, Object> map) {
        List<Object> filedlist = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("from " + tablename + " u where ");
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            sql.append(" u." + iter.next() + "=? and ");
            filedlist.add(map.get(iter.next()));
        }
        Query query = entityManager.createQuery(sql.toString().substring(0, sql.toString().length() - 4));

        for (int i = 0; i < filedlist.size(); i++) {
            query.setParameter(i + 1, filedlist.get(i));
        }
        List<T> result = query.getResultList();
        entityManager.close();
        return result;
    }


    /**
     *      * 多字段查询分页
     *      * @param tablename 表名
     *      * @param map 以map存储key,value
     *      * @param start 第几页
     *      * @param pageNumer 一个页面的条数
     *      * @return
     *      
     */
    @Transactional
    @Override
    public List<T> findByMoreFiledPage(String tableName, LinkedHashMap<String, Object> map, int star, int pageNumer) {
        List<Object> filedlist = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("from " + tableName + " u where ");
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            sql.append(" u." + iter.next() + "=? and ");
            filedlist.add(map.get(iter.next()));
        }
        Query query = entityManager.createQuery(sql.toString().substring(0, sql.toString().length() - 4));

        for (int i = 0; i < filedlist.size(); i++) {
            query.setParameter(i + 1, filedlist.get(i));
        }
        query.setFirstResult((star - 1) * pageNumer);
        query.setMaxResults(pageNumer);
        List<T> result = query.getResultList();
        entityManager.close();
        return result;
    }


    /**
     *      * 一个字段的分页
     *      * @param  tablename 表名
     *      * @param filed 字段名
     *      * @param o 字段参数
     *      * @param start 第几页
     *      * @param pageNumer 一个页面多少条数据
     *      * @return
     *      
     */
    @Transactional
    @Override
    public List<T> findPages(String tableName, String filed, Object o, int start, int pageNumer) {
        try {
            Query query = entityManager.createQuery(" from " + tableName + " t where t." + filed + "=?");
            query.setParameter(1, o);
            query.setFirstResult((start - 1) * pageNumer);
            query.setMaxResults(pageNumer);
            List<T> result = query.getResultList();
            entityManager.close();
            return result;

        } catch (Exception e) {
            log.warning(e.getMessage());
            return null;
        }
    }


    /**
     *      * 根据表的id删除数据
     *      * @param  entity
     *      
     */
    @Transactional
    @Override
    public boolean delete(T entity) {
        boolean flag = false;
        try {
            entityManager.remove(entityManager.merge(entity));
            flag = true;
            return flag;
        } catch (Exception e) {
            log.warning(e.getMessage());
            return flag;
        }
    }


    /**
     *      * 更新对象
     *      * @param e
     *      * @return
     *      
     */
    @Transactional
    @Override
    public boolean update(T entity) {
        boolean flag = false;
        try {
            entityManager.merge(entity);
            flag = true;
            return flag;
        } catch (Exception e) {
            log.warning(e.getMessage());
            return flag;
        }
    }

    /**
     *      * 根据传入的map遍历key,value拼接字符串，以id为条件更新
     *      * @param tablename 表名
     *      * @param map 传入参数放入map中
     *      * @return
     *      
     */
    @Transactional
    @Override
    public Integer updateMoreFiled(String tableName, LinkedHashMap<String, Object> map, Long id) {

        StringBuffer sql = new StringBuffer();
        sql.append("update " + tableName + " t set ");
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            if (map.get(iter.next()).getClass().getTypeName() == "java.lang.String") {
                sql.append("t." + iter.next() + "='" + map.get(iter.next()) + "', ");
            } else {
                sql.append("t." + iter.next() + "=" + map.get(iter.next()) + ", ");
            }
        }
        String exesql = sql.toString().substring(0, sql.toString().length() - 2);
        exesql += "t.id=" + id;
        try {
            Query query = entityManager.createQuery(exesql);
            query.setParameter(1, id);
            int result = query.executeUpdate();
            return result;
        } catch (Exception e) {
            log.warning(e.getMessage());
            return 0;
        }
    }


    /**
     *      * 根据条件查询总条数返回object类型
     *      * @param tablename  表名
     *      * @param map 传入参数放入map中
     *      * @return
     *      
     */
    @Transactional
    @Override
    public Object findCount(String tableName, LinkedHashMap<String, Object> map) {
        String sql = "select count(u) from " + tableName + " u WHERE ";
        Set<String> set = null;
        set = map.keySet();
        List<String> list = new ArrayList<>(set);
        List<Object> filedlist = new ArrayList<>();
        for (String filed : list) {
            sql += "u." + filed + "=? and ";
            filedlist.add(filed);
        }
        sql = sql.substring(0, sql.length() - 4);
        System.out.println(sql + "--------sql语句-------------");
        Query query = entityManager.createQuery(sql);
        for (int i = 0; i < filedlist.size(); i++) {
            query.setParameter(i + 1, map.get(filedlist.get(i)));
        }
        return query.getSingleResult();
    }


}

