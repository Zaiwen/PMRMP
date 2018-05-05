package org.sklse.processRegister.db.dao;

import databaseaccess.Access;
import ontology.IdWorker;
import org.sklse.processRegister.db.dto.ConditionDTO;
import org.utils.JsonUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConditionDAO {


    //表明condition与数据库内部冲突，改名之
    private Access ac;
    private Connection mycon = null;
    private PreparedStatement ps = null;
    private static final String INSERT = "insert into _condition(id,singletype,positiontype,predicate,name,composingexpressionids,param) values(?,?,?,?,?,?,?);";
    private static final String UPDATE = "update _condition set " +
            "singletype = ?, " +
            "positiontype = ?, " +
            "predicate = ? ,name = ?,composingexpressionids = ?, param = ?" +
            "where id = ?;";

    private static final String DELETE = "delete from _condition where id = ? ;";
    private static final String QUERY_ALL = "select * from _condition;";
    private static final String QUERY_BY_ID = "select * from _condition where id = ?;";

    public ConditionDAO() throws Exception {
        ac = new Access();
        ac.connDB("bpep");
        mycon = ac.getCon();
    }


    private static ConditionDAO instance;

    public static ConditionDAO getDAO() {
        if (instance == null) {
            synchronized (ConditionDAO.class) {
                if (instance == null) {
                    try {
                        instance = new ConditionDAO();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public long saveOrUpdate(ConditionDTO dto) throws SQLException {
        assert dto != null;
        if (dto.getId() > 1L) {
            return update(dto);
        } else {
            long nextId = IdWorker.getNextId();
            if (nextId > 1L) {
                dto.setId(nextId);
                return create(dto);
            }
            return nextId;
        }
    }

    /**
     * 保存全部condition信息
     *
     * @param conditionDTO
     * @return
     * @throws SQLException
     */
    public long saveAllCondition(ConditionDTO conditionDTO) throws SQLException {
        assert conditionDTO != null;
        ConditionDTO.SingleTypeEnum single = ConditionDTO.SingleTypeEnum.fromint(conditionDTO.getSingleType());
        assert single != null;
        switch (single) {
            case single:
                return create(conditionDTO);
            case connect:
                List<Long> conditionid = new ArrayList<>();
                for (ConditionDTO dto : conditionDTO.getConditionDTOList()) {
                    conditionid.add(saveAllCondition(dto));
                }
                conditionDTO.setConditionIds(JsonUtil.toJson(conditionid));
                conditionDTO.setConditionIdList(conditionid);
                return create(conditionDTO);
        }
        return -1;
    }

    public long create(ConditionDTO dto) throws SQLException {
        assert dto != null;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(INSERT);
        ps.setLong(1, dto.getId());
        ps.setInt(2, dto.getSingleType());
        ps.setInt(3, dto.getPositionType());
        ps.setInt(4, dto.getPredicate());
        ps.setString(5, dto.getName());
        ps.setString(6, dto.getConditionIds());
        ps.setString(7, dto.getParam());
        ps.execute();
        ps.close();
        this.close();
        return dto.getId();
    }

    public long update(ConditionDTO dto) throws SQLException {
        assert dto != null;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(UPDATE);
        ps.setLong(1, dto.getId());
        ps.setInt(2, dto.getSingleType());
        ps.setInt(3, dto.getPositionType());
        ps.setInt(4, dto.getPredicate());
        ps.setString(5, dto.getName());
        ps.setString(6, dto.getConditionIds());
        ps.setString(7, dto.getParam());
        ps.executeUpdate();
        ps.close();

        this.close();

        return dto.getId();
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


    public ConditionDTO queryById(long id) throws SQLException {
        ConditionDTO ret = null;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(QUERY_BY_ID);
        ps.setLong(1, id);
        ResultSet set = ps.executeQuery();
        if (set.next()) {
            ret = new ConditionDTO();
            ret.setId(set.getLong("id"));
            ret.setSingleType(set.getInt("singletype"));
            ret.setPositionType(set.getInt("positiontype"));
            ret.setPredicate(set.getInt("predicate"));
            ret.setName(set.getString("name"));
            ret.setConditionIds(set.getString("composingexpressionids"));
            ret.setParam(set.getString("param"));
        }
        set.close();
        ps.close();
        this.close();
        return ret;
    }


    /**
     *
     *
     * @param id
     * @return
     * @throws Exception
     */
    public ConditionDTO getRecursiveConditionById(long id) throws Exception {
        ConditionDTO conditionDTO = queryById(id);
        if (conditionDTO == null) {
            return null;
        }
        ConditionDTO.SingleTypeEnum singletype = ConditionDTO.SingleTypeEnum.fromint(conditionDTO.getSingleType());
        assert singletype != null;
        switch (singletype) {
            case connect:
                List<Long> conditionIdList = conditionDTO.getConditionIdList();
                assert conditionIdList != null && conditionIdList.size() > 1;
                for (Long conId : conditionIdList) {
                    conditionDTO.getConditionDTOList().add(getRecursiveConditionById(conId));
                }
        }
        return conditionDTO;
    }


    public void close() {
        try {
            ac.closeDB();
        } catch (SQLException e) {
            System.out.println("Close DB failed in ConditionDao");
            e.printStackTrace();
        }
    }
}