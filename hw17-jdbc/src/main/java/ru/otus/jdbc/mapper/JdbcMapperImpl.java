package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);
    private final Class<T> objectDataClass;
    private final EntitySQLMetaData sqlMeta;
    private final EntityClassMetaData<T> entityMeta;
    private final DbExecutor<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;


    public JdbcMapperImpl(Class<T> objectDataClass, SessionManagerJdbc sessionManager) {
        this.objectDataClass = objectDataClass;
        sqlMeta = new EntitySQLMetaDataImpl(objectDataClass);
        entityMeta = new EntityClassMetaDataImpl<>(objectDataClass);
        this.sessionManager = sessionManager;
        dbExecutor = new DbExecutorImpl<>();
    }

    @Override
    public void insert(T objectData) {
        String sql = sqlMeta.getInsertSql();
        logger.info("insert sql:{}", sql);
        try {
            long id = dbExecutor.executeInsert(getConnection(), sql, getInsertParams(objectData));
            entityMeta.getIdField().set(objectData, id);
        } catch (SQLException e) {
            throw new JdbcMapperException(e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(),e);
            throw new JdbcMapperException(e);
        }
    }

    @Override
    public void update(T objectData) {
        String sql = sqlMeta.getUpdateSql();
        logger.info("update sql:{}", sql);
        try {
            dbExecutor.executeInsert(getConnection(), sql, getUpdateParams(objectData));
        } catch (SQLException e) {
            throw new JdbcMapperException(e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(),e);
            throw new JdbcMapperException(e);
        }
    }


    @Override
    public void insertOrUpdate(T objectData) {
        try {
            T curEntity = findById(getEntityId(objectData), objectDataClass);
            if (curEntity == null) {
                insert(objectData);
            } else {
                update(objectData);
            }
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(),e);
            throw new JdbcMapperException(e);
        }
    }

    @Override
    public T findById(long id, Class<T> clazz) {
        String sql = sqlMeta.getSelectByIdSql();
        logger.info("findById sql:{}", sql);

        try {
            Optional<T> foundEntity = dbExecutor.executeSelect(getConnection(), sql, id,
                    rs -> {
                        try {
                            if (rs.next()) {
                                T object = entityMeta.getConstructor().newInstance();
                                for (Field field : entityMeta.getAllFields()) {
                                    field.set(object, rs.getObject(field.getName()));
                                }
                                return object;
                            }
                        } catch (SQLException | InstantiationException | IllegalAccessException |
                                InvocationTargetException e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    });
            return foundEntity.orElse(null);
        } catch (SQLException e) {
            throw new JdbcMapperException(e);
        }

    }


    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    private List<Object> getInsertParams(T objectData) throws IllegalAccessException {
        List<Object> res = new ArrayList<>();
        /* Поскольку EntityClassMetaDataImpl.getAllFields после первого вызова сохраняет результаты во внутреннем
        * списке, то используя сейчас getFieldsWithoutId мы получим поля в том же порядке, в котором они были, когда
        * формировался SQL запрос */
        for (Field field : entityMeta.getFieldsWithoutId()) {
            res.add(field.get(objectData));
        }
        return res;
    }

    private List<Object> getUpdateParams(T objectData) throws IllegalAccessException {
        List<Object> res = new ArrayList<>();
        /* Поскольку EntityClassMetaDataImpl.getAllFields после первого вызова сохраняет результаты во внутреннем
         * списке, то используя сейчас getFieldsWithoutId мы получим поля в том же порядке, в котором они были, когда
         * формировался SQL запрос */
        for (Field field : entityMeta.getFieldsWithoutId()) {
            res.add(field.get(objectData));
        }

        res.add(getEntityId(objectData)); // Значение идентификатора строки
        return res;
    }

    private long getEntityId(T objectData) throws IllegalAccessException {
        return (long) entityMeta.getIdField().get(objectData);
    }

}
