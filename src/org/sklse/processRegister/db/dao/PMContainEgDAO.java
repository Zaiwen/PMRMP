package org.sklse.processRegister.db.dao;

import change4epml.Edge;
import databaseaccess.Access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class PMContainEgDAO {

    private Access ac;
    private Connection mycon = null;
    private PreparedStatement ps = null;

    private static final String INSERT = "insert into pm_edge(pmid,srcid,tgtid,originalId) values(?,?,?,?)";
    private static final String QUERY_BY_PMID = "select * from pm_edge where pmid = ?";

    public PMContainEgDAO() {
        ac = new Access();
        try {
            ac.connDB("bpep");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mycon = ac.getCon();
    }

    public void insert(Edge e) throws SQLException {
        mycon = ac.getCon();
        ps = mycon.prepareStatement(INSERT);
        ps.setLong(1, e.getId());
        ps.setLong(2, e.getSrcid());
        ps.setLong(3, e.getTgtid());
        ps.setLong(4, e.getOriginalId());
        ps.execute();
        ps.close();
        this.close();
    }

    public Set<Edge> QueryByPmid(long pmid) throws SQLException {
        Set<Edge> edgeset = new HashSet<Edge>();
        mycon = ac.getCon();
        ps = mycon.prepareStatement(QUERY_BY_PMID);
        ps.setLong(1, pmid);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            Edge e = new Edge();
            e.setSrcid(set.getLong("srcid"));
            e.setTgtid(set.getLong("tgtid"));
            e.setOriginalId(set.getLong("originalId"));
            edgeset.add(e);
        }
        ps.close();
        this.close();
        return edgeset;

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
