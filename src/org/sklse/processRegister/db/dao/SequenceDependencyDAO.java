package org.sklse.processRegister.db.dao;

import databaseaccess.Access;
import org.sklse.processRegister.db.dto.SequenceDependencyDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SequenceDependencyDAO {

    private Access ac;
    private Connection mycon = null;
    private PreparedStatement ps = null;
    private static final String INSERT = "insert into sequence_dependency(id,preceding,following,param) "
            + "values(?,?,?,?) ";
    private static final String UPDATE = "update sequence_dependency set " +
            "preceding = ?, " +
            "following = ?,param = ?, " +
            "where id = ?;";
    private static final String QUERY_ALL = "select * from sequence_dependency;";
    private static final String QUERY_BY_ID = "select * from sequence_dependency where id = ?;";

    public SequenceDependencyDAO() throws Exception {
        ac = new Access();
        ac.connDB("bpep");
    }

    public long create(SequenceDependencyDTO dto) throws SQLException {
        long ret = -1;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(INSERT);
        ps.setLong(1, dto.getId());
        ps.setLong(2, dto.getPreceding());
        ps.setLong(3, dto.getFollowing());
        ps.setString(4, dto.getParam());
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


    public boolean update(SequenceDependencyDTO dto) throws SQLException {
        boolean ret = false;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(UPDATE);
        ps.setLong(1, dto.getPreceding());
        ps.setLong(2, dto.getFollowing());
        ps.setLong(3, dto.getId());
        ps.setString(4, dto.getParam());
        int rows = ps.executeUpdate();
        if (rows == 1)
            ret = true;
        ps.close();

        this.close();

        return ret;
    }

    public List<SequenceDependencyDTO> queryAll() throws SQLException {
        List<SequenceDependencyDTO> ret = new ArrayList<SequenceDependencyDTO>();
        mycon = ac.getCon();
        ps = mycon.prepareStatement(QUERY_ALL);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            SequenceDependencyDTO dto = new SequenceDependencyDTO();
            dto.setId(set.getLong("id"));
            dto.setPreceding(set.getLong("preceding"));
            dto.setFollowing(set.getLong("following"));
            dto.setParam(set.getString("param"));
            ret.add(dto);
        }
        set.close();
        ps.close();

        this.close();

        return ret;
    }

    public SequenceDependencyDTO queryById(long id) throws SQLException {
        SequenceDependencyDTO ret = new SequenceDependencyDTO();
        mycon = ac.getCon();
        ps = mycon.prepareStatement(QUERY_BY_ID);
        ps.setLong(1, id);
        ResultSet set = ps.executeQuery();
        if (set.next()) {
            ret.setId(set.getLong("id"));
            ret.setPreceding(set.getLong("preceding"));
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
            e.printStackTrace();
        }
    }
}