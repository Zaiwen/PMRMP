package org.sklse.processRegister.db.dao;

import databaseaccess.Access;
import org.sklse.ontologyRegister.db.BaseDAO;
import org.sklse.ontologyRegister.db.DBConnector;
import org.sklse.processRegister.db.dto.ProcessDTO;
import org.sklse.processRegister.db.dto.ProcessEventDTO;
import org.sklse.processRegister.db.dto.ProcessEventDTO.PERelation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProcessEventDAO extends BaseDAO {

    private Access ac;
    private Connection mycon = null;
    private PreparedStatement ps = null;

    public ProcessEventDAO() throws Exception {
        ac = new Access();
        ac.connDB("bpep");
        mycon = ac.getCon();
    }

    public boolean create(ProcessEventDTO dto) throws SQLException {
        boolean ret = false;
        String sql = "insert into process_event(relation,pid,eid) " +
                "values(?,?,?);";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);
        ps.setString(1, dto.getRelation().toString());
        ps.setLong(2, dto.getPid());
        ps.setLong(3, dto.getEid());
        int rows = ps.executeUpdate();
        if (rows == 1)
            ret = true;

        DBConnector.closeStatement(ps);

        return ret;
    }

    /**
     * 更新ProcessEventDTO
     *
     * @param dto
     * @param processDTO
     * @return
     */
    public boolean update(ProcessEventDTO dto, ProcessDTO processDTO) throws SQLException {
        boolean ret = false;
        String sql = "update  process_event set pid = ?  where pid = ? and eid = ? and relation = ?";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);
        ps.setLong(1, dto.getPid());
        ps.setLong(2, processDTO.getId());
        ps.setLong(2, dto.getEid());
        ps.setString(2, dto.getRelation().toString());
        int rows = ps.executeUpdate();
        if (rows == 1)
            ret = true;

        DBConnector.closeStatement(ps);


        return ret;
    }

    public List<ProcessEventDTO> queryAll() throws SQLException {
        List<ProcessEventDTO> ret = new ArrayList<ProcessEventDTO>();
        String sql = "select * from process_event;";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);
        set = ps.executeQuery();
        while (set.next()) {
            ProcessEventDTO dto = new ProcessEventDTO();
            dto.setRelation(set.getString("relation"));
            dto.setPid(set.getLong("pid"));
            dto.setEid(set.getLong("eid"));
            ret.add(dto);
        }

        DBConnector.closeStatement(ps);

        return ret;
    }

    public List<ProcessEventDTO> queryByPid(long pid) throws SQLException {
        List<ProcessEventDTO> ret = new ArrayList<ProcessEventDTO>();
        String sql = "select * from process_event where pid = ?;";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);
        ps.setLong(1, pid);
        set = ps.executeQuery();
        if (set.next()) {
            ProcessEventDTO dto = new ProcessEventDTO();
            dto.setRelation(set.getString("relation"));
            dto.setPid(set.getLong("pid"));
            dto.setEid(set.getLong("eid"));
            ret.add(dto);
        }

        DBConnector.closeStatement(ps);

        return ret;
    }

    public List<ProcessEventDTO> queryByEid(long eid) throws SQLException {
        List<ProcessEventDTO> ret = new ArrayList<ProcessEventDTO>();
        String sql = "select * from process_event where eid = ?;";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);
        ps.setLong(1, eid);
        set = ps.executeQuery();
        if (set.next()) {
            ProcessEventDTO dto = new ProcessEventDTO();
            dto.setRelation(set.getString("relation"));
            dto.setPid(set.getLong("pid"));
            dto.setEid(set.getLong("eid"));
            ret.add(dto);
        }

        DBConnector.closeStatement(ps);

        return ret;
    }

    public List<ProcessEventDTO> queryByPidAndRelation(
            long pid, PERelation relation) throws SQLException {
        List<ProcessEventDTO> ret = new ArrayList<ProcessEventDTO>();
        String sql = "select * from process_event where pid = ? AND relation = ?;";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);
        ps.setLong(1, pid);
        ps.setString(2, relation.toString());
        set = ps.executeQuery();
        while (set.next()) {
            ProcessEventDTO dto = new ProcessEventDTO();
            dto.setRelation(set.getString("relation"));
            dto.setPid(set.getLong("pid"));
            dto.setEid(set.getLong("eid"));
            ret.add(dto);
        }

        DBConnector.closeStatement(ps);

        return ret;
    }
}