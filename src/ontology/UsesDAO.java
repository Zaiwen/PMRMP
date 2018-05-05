package ontology;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import databaseaccess.Access;



public class UsesDAO extends BaseDAO{
	public UsesDAO(Connection conn){
		super(conn);
	}
	
	public UsesDAO(){
	}
	
//	public boolean create(UsesDTO dto) {
//		boolean ret = false;
//		String sql = "insert into uses(ontology_component_id,ontology_atomic_construct_id) " +
//				     "values(?,?);";
//		try {
//			sta = conn.prepareStatement(sql);
//			sta.setInt(1, dto.getOntologyComponentId());
//			sta.setInt(2, dto.getOntologyAtomicConstructId());
//			int rows = sta.executeUpdate();
//			if (rows == 1)
//				ret = true;
//		} catch (MySQLIntegrityConstraintViolationException e){
//			System.out.println(">>> UsesDAO WARNING : "+e.getMessage());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBConnector.closeStatement(sta);
//		}
//
//		return ret;
//	}
//
//	public boolean delete(UsesDTO dto) {
//		boolean ret = false;
//		String sql = "delete from uses " +
//				"where ontology_component_id = ? and ontology_atomic_construct_id = ?;";
//		try {
//			sta = conn.prepareStatement(sql);
//			sta.setInt(1, dto.getOntologyComponentId());
//			sta.setInt(2, dto.getOntologyAtomicConstructId());
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

//	public List<UsesDTO> queryAll() {
//		List<UsesDTO> ret = new ArrayList<UsesDTO>();
//		String sql = "select * from uses;";
//		try {
//			sta = conn.prepareStatement(sql);
//			set = sta.executeQuery();
//			while (set.next()) {
//				UsesDTO dto = new UsesDTO();
//				dto.setOntologyComponentId(set.getInt("ontology_component_id"));
//				dto.setOntologyAtomicConstructId(set.getInt("ontology_atomic_construct_id"));
//				ret.add(dto);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBConnector.closeStatement(sta);
//		}
//		return ret;
//	}

	public List<UsesDTO> queryAllByComponentId(int componentId) {
		List<UsesDTO> ret = new ArrayList<UsesDTO>();
		String sql = "select * from ontology_atomic_construct where component_id = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, componentId);
			set = sta.executeQuery();
			while (set.next()) {
				UsesDTO dto = new UsesDTO();
				dto.setOntologyComponentId(set.getInt("component_id"));
				dto.setOntologyAtomicConstructId(set.getInt("id"));
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
	
	public List<UsesDTO> queryAllByAtomicConstructId(int atomicConstructId) {
		List<UsesDTO> ret = new ArrayList<UsesDTO>();
		String sql = "select * from ontology_atomic_construct where id = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, atomicConstructId);
			set = sta.executeQuery();
			while (set.next()) {
				UsesDTO dto = new UsesDTO();
				dto.setOntologyComponentId(set.getInt("component_id"));
				dto.setOntologyAtomicConstructId(set.getInt("id"));
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

