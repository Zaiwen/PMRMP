package org.sklse.processRegister.db.dao;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import org.sklse.ontologyRegister.db.BaseDAO;
import org.sklse.ontologyRegister.db.DBConnector;
import org.sklse.processRegister.db.dto.LoopDependencyDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoopDependencyDAO extends BaseDAO {

    public LoopDependencyDAO(Connection conn) {
        super(conn);
    }

    public LoopDependencyDAO() { }

    public long create(LoopDependencyDTO dto) {
        long ret = -1;
        String sql = "insert into loop_dependency(id,preceding,entry_point,following,condition,type) "
                + "values(?,?,?,?,?,?) returning id;";

        try {
            ps = mycon.prepareStatement(sql);
            ps.setLong(1, dto.getId());
            if(dto.getPreceding() > 0) {
                ps.setLong(2, dto.getPreceding());
            } else ps.setNull(2, java.sql.Types.BIGINT);
            if(dto.getEntryPoint() > 0) {
                ps.setLong(3, dto.getEntryPoint());
            } else ps.setNull(3, java.sql.Types.BIGINT);
            if(dto.getFollowing() > 0) {
                ps.setLong(4, dto.getFollowing());
            } else ps.setNull(4, java.sql.Types.BIGINT);
            if(dto.getCondition()!=null && !dto.getCondition().isEmpty()) {
                ps.setString(5, dto.getCondition());
            } else ps.setString(5, "");
            ps.setString(6, dto.getType().toString());
            boolean exec = ps.execute();
            if (exec) {
                ResultSet rs = ps.getResultSet();
                if(rs.next()) {
                    ret = rs.getLong(1);
                }
            }
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(">>> LoopDependencyDAO WARNING : "
                    + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnector.closeStatement(ps);
        }

        return ret;
    }
    
    public boolean update(LoopDependencyDTO dto) {
        boolean ret = false;
        String sql = "update loop_dependency set " +
                     "preceding = ?, " + 
                     "entry_point = ?, " +
                     "following = ?, " + 
                     "condition = ?, " +
                     "type = ? " +
                     "where id = ?;";
        try {
            ps = mycon.prepareStatement(sql);
            if(dto.getPreceding()>0) {
                ps.setLong(1, dto.getPreceding());
            } else ps.setNull(1, java.sql.Types.BIGINT);
            if(dto.getEntryPoint()>0) {
                ps.setLong(2, dto.getEntryPoint());
            } else ps.setNull(2, java.sql.Types.BIGINT);
            if(dto.getFollowing()>0) {
                ps.setLong(3, dto.getFollowing());
            } else ps.setNull(3, java.sql.Types.BIGINT);
            if(dto.getCondition()!=null && !dto.getCondition().isEmpty()) {
                ps.setString(4, dto.getCondition());
            } else ps.setString(4, "");
            ps.setString(5, dto.getType().toString());
            ps.setLong(6, dto.getId());
            int rows = ps.executeUpdate();
            if (rows == 1)
                ret = true;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(">>> LoopDependencyDAO WARNING : "
                    + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnector.closeStatement(ps);
        }
        return ret;
    }
    
    public boolean delete(long id) {
        boolean ret = false;
        String sql = "delete from loop_dependency where id = ? ;";
        try {
            ps = mycon.prepareStatement(sql);
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 1)
                ret = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnector.closeStatement(ps);
        }
        return ret;
    }

    public List<LoopDependencyDTO> queryAll() {
        List<LoopDependencyDTO> ret = new ArrayList<LoopDependencyDTO>();
        String sql = "select * from loop_dependency;";
        try {
            ps = mycon.prepareStatement(sql);
            set = ps.executeQuery();
            while (set.next()) {
                LoopDependencyDTO dto = new LoopDependencyDTO();
                dto.setId(set.getLong("id"));
                dto.setPreceding(set.getLong("preceding"));
                dto.setEntryPoint(set.getLong("entry_point"));
                dto.setFollowing(set.getLong("following"));
                dto.setCondition(set.getString("condition"));
                dto.setType(set.getString("type"));
                ret.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnector.closeStatement(ps);
        }
        return ret;
    }

    public LoopDependencyDTO queryById(long id) {
        LoopDependencyDTO ret = null;
        String sql = "select * from loop_dependency where id = ?;";
        try {
            ps = mycon.prepareStatement(sql);
            ps.setLong(1, id);
            set = ps.executeQuery();
            if (set.next()) {
                ret = new LoopDependencyDTO();
                ret.setId(set.getLong("id"));
                ret.setPreceding(set.getLong("preceding"));
                ret.setEntryPoint(set.getLong("entry_point"));
                ret.setFollowing(set.getLong("following"));
                ret.setCondition(set.getString("condition"));
                ret.setType(set.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnector.closeStatement(ps);
        }
        return ret;
    }
}