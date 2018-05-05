package org.sklse.processRegister.db.dao;

import databaseaccess.Access;
import org.sklse.ontologyRegister.db.BaseDAO;
import org.sklse.ontologyRegister.db.DBConnector;
import org.sklse.processRegister.db.dto.ProcessDTO;
import org.sklse.processRegister.db.dto.ProcessResourceDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProcessResourceDAO extends BaseDAO {

    public ProcessResourceDAO(Connection conn) {
        super(conn);
    }

    private Access ac;
    private Connection mycon = null;
    private PreparedStatement ps = null;

    public ProcessResourceDAO() throws Exception {
        ac = new Access();
        ac.connDB("bpep");
    }

    public boolean create(ProcessResourceDTO dto) throws SQLException {
        boolean ret = false;
        String sql = "insert into process_resource(pid,rid,relation) " +
                "values(?,?,?);";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);
        ps.setLong(1, dto.getPid());
        ps.setLong(2, dto.getRid());
        ps.setString(3, dto.getRelation().toString());
        int rows = ps.executeUpdate();
        if (rows == 1)
            ret = true;

        DBConnector.closeStatement(ps);
        ac.closeDB();

        return ret;
    }

    public boolean delete(ProcessResourceDTO dto) throws SQLException {
        boolean ret = false;
        String sql = "delete from process_resource where "
                + "pid = ? AND rid = ? AND relation = ?;";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);
        ps.setLong(1, dto.getPid());
        ps.setLong(2, dto.getRid());
        ps.setString(3, dto.getRelation().toString());
        int rows = ps.executeUpdate();
        if (rows == 1)
            ret = true;
        ac.closeDB();
        return ret;
    }

    public List<ProcessResourceDTO> queryByPid(long pid) throws SQLException {
        List<ProcessResourceDTO> ret = new ArrayList<ProcessResourceDTO>();
        String sql = "select * from process_resource where pid = ?;";
        ps = mycon.prepareStatement(sql);
        ps.setLong(1, pid);
        set = ps.executeQuery();
        while (set.next()) {
            ProcessResourceDTO dto = new ProcessResourceDTO();
            dto.setPid(set.getLong("pid"));
            dto.setRid(set.getLong("rid"));
            dto.setRelation(set.getString("relation"));
            ret.add(dto);
        }
        DBConnector.closeStatement(ps);
        ac.closeDB();
        return ret;
    }

    public List<ProcessResourceDTO> queryByRid(long rid) throws SQLException {
        List<ProcessResourceDTO> ret = new ArrayList<ProcessResourceDTO>();
        String sql = "select * from process_resource where rid = ?;";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);
        ps.setLong(1, rid);
        set = ps.executeQuery();
        while (set.next()) {
            ProcessResourceDTO dto = new ProcessResourceDTO();
            dto.setPid(set.getLong("pid"));
            dto.setRid(set.getLong("rid"));
            dto.setRelation(set.getString("relation"));
            ret.add(dto);
        }


        DBConnector.closeStatement(ps);
        ac.closeDB();
        return ret;
    }

    public boolean update(ProcessResourceDTO dto, ProcessDTO processDTO) throws SQLException {
        boolean ret = false;
        String sql = "update  process_resource set pid = ? where pid = ? , rid = ? , relation = ?;";
        mycon = ac.getCon();

        ps = mycon.prepareStatement(sql);
        ps.setLong(1, dto.getPid());
        ps.setLong(2, processDTO.getId());
        ps.setLong(3, dto.getRid());
        ps.setString(4, dto.getRelation().toString());
        int rows = ps.executeUpdate();
        if (rows == 1)
            ret = true;
        DBConnector.closeStatement(ps);
        ac.closeDB();
        return ret;
    }
}