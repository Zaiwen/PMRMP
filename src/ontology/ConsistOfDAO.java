package ontology;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import databaseaccess.Access;


public class ConsistOfDAO extends BaseDAO{
	public ConsistOfDAO(Connection conn) {
		super(conn);
	}

	public ConsistOfDAO() {
	}
//	public boolean create(ConsistOfDTO dto) {
//		boolean ret = false;
//		String sql = "insert into consist_of(ontology_id,ontology_component_id) " +
//				     "values(?,?);";
//		try {
//			sta = conn.prepareStatement(sql);
//			sta.setInt(1, dto.getOntologyId());
//			sta.setInt(2, dto.getOntologyComponentId());
//			int rows = sta.executeUpdate();
//			if (rows == 1)
//				ret = true;
//		}catch (MySQLIntegrityConstraintViolationException e){
//			System.out.println(">>> ConsistOfDAO WARNING : "+e.getMessage());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBConnector.closeStatement(sta);
//		}
//
//		return ret;
//	}
//
//	public boolean delete(ConsistOfDTO dto) {
//		boolean ret = false;
//		String sql = "delete from consist_of " +
//				"where ontology_id = ? and ontology_component_id = ?;";
//		try {
//			sta = conn.prepareStatement(sql);
//			sta.setInt(1, dto.getOntologyId());
//			sta.setInt(2, dto.getOntologyComponentId());
//			int rows = sta.executeUpdate();
//			if (rows == 1)
//				ret = true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBConnector.closeStatement(sta);
//		}
//		return ret;
//	}

//	public List<ConsistOfDTO> queryAll() {
//		List<ConsistOfDTO> ret = new ArrayList<ConsistOfDTO>();
//		String sql = "select * from consist_of;";
//		try {
//			sta = conn.prepareStatement(sql);
//			set = sta.executeQuery();
//			while (set.next()) {
//				ConsistOfDTO dto = new ConsistOfDTO();
//				dto.setOntologyId(set.getInt("ontology_id"));
//				dto.setOntologyComponentId(set.getInt("ontology_component_id"));
//				ret.add(dto);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBConnector.closeStatement(sta);
//		}
//		return ret;
//	}
	
	public List<ConsistOfDTO> queryAllByComponentId(int componentId) {
		List<ConsistOfDTO> ret = new ArrayList<ConsistOfDTO>();
		String sql = "select * from ontology_component where id = ? ;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, componentId);
			set = sta.executeQuery();
			while (set.next()) {
				ConsistOfDTO dto = new ConsistOfDTO();
				dto.setOntologyId(set.getInt("ontology_id"));
				dto.setOntologyComponentId(set.getInt("id"));
				ret.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}
	
	public List<ConsistOfDTO> queryAllByOntologyId(int ontologyId) {
		List<ConsistOfDTO> ret = new ArrayList<ConsistOfDTO>();
		String sql = "select * from ontology_component where ontology_id = ? ;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, ontologyId);
			set = sta.executeQuery();
			while (set.next()) {
				ConsistOfDTO dto = new ConsistOfDTO();
				dto.setOntologyId(set.getInt("ontology_id"));
				dto.setOntologyComponentId(set.getInt("id"));
				ret.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}
	
}