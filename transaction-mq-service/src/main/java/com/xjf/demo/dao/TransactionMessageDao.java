package com.xjf.demo.dao;

import com.xjf.demo.entity.TransactionMessage;
import com.xjf.demo.enums.TransactionMessageStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

/**
 * @author xjf
 * @date 2020/2/14 16:56
 */
@Component
public class TransactionMessageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 插入数据
     *
     * @param message
     * @return 插入条数
     */
    public int insert(TransactionMessage message){
        String sql = "insert into transaction_message(id, message, queue, send_system, send_count, create_date" +
                ", send_date, status, die_count, customer_date, customer_system, die_date) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

        try {
            return jdbcTemplate.update(sql, message.getId(), message.getMessage(), message.getQueue(), message.getSendSystem(), message.getSendCount(),
                    message.getCreateDate(), message.getSendDate(), message.getStatus(), message.getDieCount(),
                    message.getCustomerDate(), message.getCustomerSystem(), message.getDieDate());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    public int[] batchInsert(List<TransactionMessage> list){
        String sql = "insert into transaction_message(id, message, queue, send_system, send_count, create_date" +
                ", send_date, status, die_count, customer_date, customer_system, die_date) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                TransactionMessage message = list.get(i);
                preparedStatement.setLong(1, message.getId());
                preparedStatement.setString(2, message.getMessage());
                preparedStatement.setString(3, message.getQueue());
                preparedStatement.setString(4, message.getSendSystem());
                preparedStatement.setInt(5, message.getSendCount());
                preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                preparedStatement.setInt(8, message.getStatus());
                preparedStatement.setInt(9, message.getDieCount());
                preparedStatement.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
                preparedStatement.setString(11, message.getCustomerSystem());
                preparedStatement.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
    }

    /**
     * 更新
     * @param message
     * @return
     */
    public int update(TransactionMessage message){
        // 这种方式注入值时，如果实体的值为 null ，则会报空指针异常
        String sql = "update transaction_message set message = ?, queue = ?, send_system = ?, send_count = ?, create_date = ?" +
                ", send_date = ?, status = ?, die_count = ?, customer_date = ?, customer_system = ?, die_date = ? " +
                "where id = ? ";
        return jdbcTemplate.update(sql,  message.getMessage(), message.getQueue(), message.getSendSystem(), message.getSendCount(),
                message.getCreateDate(), message.getSendDate(), message.getStatus(), message.getDieCount(),
                message.getCustomerDate(), message.getCustomerSystem(), message.getDieDate(), message.getId());
    }

    /**
     * 根据 ID 查询
     * @param id
     * @return
     */
    public TransactionMessage findById(Long id){
        String sql = "select * from transaction_message where id = ?";

        return jdbcTemplate.queryForObject(sql, new Long[]{id}, new RowMapper<TransactionMessage>() {
            @Override
            public TransactionMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
                TransactionMessage message = new TransactionMessage();
                message.setId(rs.getLong("id"));
                message.setMessage(rs.getString("message"));
                message.setQueue(rs.getString("queue"));
                message.setSendSystem(rs.getString("send_system"));
                message.setSendCount(rs.getInt("send_count"));
                message.setCreateDate(rs.getTimestamp("create_date"));
                message.setSendDate(rs.getTimestamp("send_date"));
                message.setStatus(rs.getInt("status"));
                message.setDieCount(rs.getInt("die_count"));
                message.setCustomerDate(rs.getTimestamp("customer_date"));
                message.setCustomerSystem(rs.getString("customer_system"));
                message.setDieDate(rs.getTimestamp("die_date"));
                return message;
            }
        });
    }

    /**
     * 查询最早没有被消费的消息，状态为等待，根据 ID 升序排序
     * @param limit
     * @return
     */
    public List<TransactionMessage> findByWaitingMessage(int limit) {

        String sql = "select * from transaction_message where status=0 order by id asc limit ? ";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionMessage.class) , limit);

    }

    /**
     * 特殊更新
     * @return
     */
    public boolean updateStatusAndSendCount() {

        String sql = "update transaction_message set status=0, send_count=0 where status=2";

        return jdbcTemplate.update(sql) > 0;
    }

    public List<TransactionMessage> findMessageByPage(int pageNum, int pageSize, TransactionMessageStatusEnum status) {

        String sql = "select * from transaction_message where status = ? limit ?, ?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionMessage.class), status.getStatus(), pageNum - 1, pageSize);
    }
}
