package org.sklse.processRegister.db.dao;

import databaseaccess.Access;
import org.sklse.processRegister.db.dto.ProcessDTO;
import org.sklse.processRegister.db.dto.SplitDependencyOptionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SplitDependencyOptionDAO {

    private Access ac;
    private Connection mycon = null;
    private PreparedStatement ps = null;

    private static final String INSERT = "insert  split_dependency_option() "
            + "values(?,?,?,?,?)";
    private static final String UPDATE = "update  split_dependency_option set following = ?,split_id = ?,condition = ? , param = ? where  id = ? ;";
    private static final String DELETE = "delete from split_dependency_option where id = ? ;";
    private static final String QUERY_ALL = "select * from split_dependency_option;";
    private static final String QUERY_BY_ID = "select * from split_dependency_option where id = ?;";

    public SplitDependencyOptionDAO() throws Exception {
        ac = new Access();
        ac.connDB("bpep");

    }

    public boolean create(SplitDependencyOptionDTO dto) throws SQLException {
        boolean ret = false;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(INSERT);
        ps.setLong(1, dto.getId());
        ps.setLong(3, dto.getFollowing());
        ps.setString(2, dto.getCondition());
        ps.setLong(4, dto.getSplitid());
        ps.setString(5, dto.getParam());
        ps.execute();

        ps.close();
        this.close();


        return ret;
    }

    public boolean update(SplitDependencyOptionDTO dto, ProcessDTO processDTO) throws SQLException {
        boolean ret = false;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(UPDATE);
        ps.setLong(1, dto.getFollowing());
        ps.setLong(2, dto.getId());
        ps.setString(3, dto.getCondition());
        ps.setLong(4, dto.getId());

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

        return ret;
    }

    public List<SplitDependencyOptionDTO> queryAll() throws SQLException {
        List<SplitDependencyOptionDTO> ret = new ArrayList<SplitDependencyOptionDTO>();
        mycon = ac.getCon();
        ps = mycon.prepareStatement(QUERY_ALL);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            SplitDependencyOptionDTO dto = new SplitDependencyOptionDTO();
            dto.setSplitid(set.getLong("split_id"));
            dto.setFollowing(set.getLong("following"));
            dto.setCondition(set.getString("condition"));
            dto.setId(set.getLong("id"));
            ret.add(dto);
        }
        set.close();
        ps.close();

        this.close();

        return ret;
    }

    public SplitDependencyOptionDTO queryById(long id) throws SQLException {
        mycon = ac.getCon();
        SplitDependencyOptionDTO dto = null;
        ps = mycon.prepareStatement(QUERY_BY_ID);
        ps.setLong(1, id);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            dto = new SplitDependencyOptionDTO();
            dto.setSplitid(set.getLong("split_id"));
            dto.setFollowing(set.getLong("following"));
            dto.setCondition(set.getString("condition"));
            dto.setId(set.getLong("id"));

        }
        set.close();
        ps.close();
        this.close();
        return dto;


    }

    public void close() {
        try {
            ac.closeDB();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}