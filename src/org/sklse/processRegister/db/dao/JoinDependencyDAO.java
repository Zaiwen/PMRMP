package org.sklse.processRegister.db.dao;

import databaseaccess.Access;
import org.sklse.processRegister.db.dto.JoinDependencyDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JoinDependencyDAO {

    private Access ac;
    private Connection mycon = null;
    private PreparedStatement ps = null;
    private static final String INSERT = "insert into join_dependency(id,type,sync,following,param) "
            + "values(?,?,?,?,?) ";
    private static final String UPDATE = "update join_dependency set " +
            "type = ?, " +
            "sync = ?, " +
            "following = ? ,param = ?" +
            "where id = ?;";
    private static final String DELETE = "delete from join_dependency where id = ? ;";
    private static final String QUERY_ALL = "select * from join_dependency;";
    private static final String SELECT_BY_ID = "select * from join_dependency where id = ?;";

    public JoinDependencyDAO() throws Exception {
        ac = new Access();
        ac.connDB("bpep");
        mycon = ac.getCon();
    }

    public long create(JoinDependencyDTO dto) throws SQLException {
        long ret = -1;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(INSERT);
        ps.setLong(1, dto.getId());
        ps.setString(2, dto.getType().toString());
        ps.setBoolean(3, dto.getSync());
        ps.setLong(4, dto.getFollowing());
        ps.setString(5, dto.getParam());
        boolean exec = ps.execute();
        if (exec) {
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                ret = rs.getLong(1);
            }
        }
        ps.close();


        this.close();


        return ret;
    }

    public boolean update(JoinDependencyDTO dto) throws SQLException {
        boolean ret = false;

        mycon = ac.getCon();
        ps = mycon.prepareStatement(UPDATE);
        ps.setString(1, dto.getType().toString());
        ps.setBoolean(2, dto.getSync());
        ps.setLong(3, dto.getFollowing());
        ps.setLong(4, dto.getId());
        ps.setString(5, dto.getParam());
        int rows = ps.executeUpdate();
        if (rows == 1)
            ret = true;
        ps.close();
        this.close();

        return ret;
    }

    public boolean delete(long id) throws SQLException {
        boolean ret = false;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(DELETE);
        ps.setLong(1, id);
        int rows = ps.executeUpdate();
        if (rows == 1)
            ret = true;
        ps.close();

        this.close();

        return ret;
    }

    public List<JoinDependencyDTO> queryAll() throws SQLException {
        List<JoinDependencyDTO> ret = new ArrayList<JoinDependencyDTO>();

        mycon = ac.getCon();
        ps = mycon.prepareStatement(QUERY_ALL);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            JoinDependencyDTO dto = new JoinDependencyDTO();
            dto.setId(set.getLong("id"));
            dto.setType(set.getString("type"));
            dto.setSync(set.getBoolean("sync"));//与数据表类型int冲突
            dto.setFollowing(set.getLong("following"));
            dto.setParam(set.getString("param"));
            ret.add(dto);
        }
        set.close();
        ps.close();

        this.close();

        return ret;
    }

    public JoinDependencyDTO queryById(long id) throws SQLException {
        JoinDependencyDTO ret = null;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(SELECT_BY_ID);
        ps.setLong(1, id);
        ResultSet set = ps.executeQuery();
        if (set.next()) {
            ret = new JoinDependencyDTO();
            ret.setId(set.getLong("id"));
            ret.setType(set.getString("type"));
            ret.setSync(set.getBoolean("sync"));
            ret.setFollowing(set.getLong("following"));
            ret.setParam(set.getString("param"));
        }
        set.close();
        ps.close();

        this.close();

        return ret;
    }

    public void close() {
        try {
            ac.closeDB();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Close DB Failed in JoinDependencyDAO");
            e.printStackTrace();
        }
        ;
    }

}