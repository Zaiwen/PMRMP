package org.sklse.processRegister.db.dao;

import databaseaccess.Access;
import org.sklse.processRegister.db.dto.PMContainPEDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PMContainPEDAO {

    private Access ac;
    private Connection mycon = null;
    private PreparedStatement ps = null;

    private static final String INSERT = "insert into pm_contain_pe(pmid , peid,petype) "
            + "values(?,?,?);";

    public PMContainPEDAO() {
        ac = new Access();
        try {
            ac.connDB("bpep");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mycon = ac.getCon();
    }

    public boolean create(PMContainPEDTO dto) throws SQLException {
        boolean ret = false;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(INSERT);
        ps.setLong(1, dto.getPMId());
        ps.setLong(2, dto.getPEId());
        ps.setString(3, dto.getPetype().toString());
        int rows = ps.executeUpdate();
        if (rows == 1)
            ret = true;
        ps.close();

        this.close();
        return ret;
    }

    public boolean delete(long pmid, long peid) throws SQLException {
        boolean ret = false;
        String sql = "delete * from pm_contain_pe where pmid = ? , peid = ? ";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);
        ps.setLong(1, pmid);
        ps.setLong(2, peid);
        int rows = ps.executeUpdate();
        if (rows == 1)
            ret = true;

        ps.close();
        this.close();
        return ret;
    }

    public List<PMContainPEDTO> queryByPMId(long pmid) throws SQLException {
        List<PMContainPEDTO> ret = new ArrayList<PMContainPEDTO>();
        String sql = "select * from pm_contain_pe where pmid = ?;";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);
        ps.setLong(1, pmid);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            PMContainPEDTO dto = new PMContainPEDTO();
            dto.setPMId(set.getLong("pmid"));
            dto.setPEId(set.getLong("peid"));
            dto.setPetype(set.getString("petype"));
            ret.add(dto);
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
            e.printStackTrace();
        }
    }
}