package ontology;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import databaseaccess.Access;

public class OntologyDAO extends BaseDAO {

	public OntologyDAO(Connection conn) {
		super(conn);
	}

	public OntologyDAO() {
	}

	public boolean create(OntologyDTO dto) {
		boolean ret = false;
		String sql = "insert into ontology(administered_item_administration_record,uri,ontology_name,model_type,ontology_type,"
				+ "header,description,fileLocation,\"domain\",\"user\") "
				+ "values(?,?,?,?,?,?,?,?,?,?); ";

		try {
			sta = conn.prepareStatement(sql);
			sta.setString(1, dto.getAdministeredItemAdministrationRecord());
			sta.setString(2, dto.getUri());
			sta.setString(3, dto.getOntologyName());
			sta.setString(4, dto.getModelType());
			sta.setInt(5, dto.getOntologyType());
			sta.setString(6, dto.getHeader());
			sta.setString(7, dto.getDescription());
			sta.setString(8, dto.getFileLocation());
			sta.setString(9, dto.getDomain());
			sta.setString(10, dto.getUser());
			int rows = sta.executeUpdate();
			if (rows == 1)
				ret = true;
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println(">>> OntologyDAO WARNING : " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}

		return ret;
	}

	public boolean update(OntologyDTO dto) {
		boolean ret = false;
		String sql = "update ontology set administered_item_administration_record = ?,"
				+ "uri = ?,ontology_name = ?,model_type = ?,"
				+ "ontology_type = ?, header = ?,"
				+ "description = ?, fileLocation = ?, \"domain\"=? where id = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setString(1, dto.getAdministeredItemAdministrationRecord());
			sta.setString(2, dto.getUri());
			sta.setString(3, dto.getOntologyName());
			sta.setString(4, dto.getModelType());
			sta.setInt(5, dto.getOntologyType());
			sta.setString(6, dto.getHeader());
			sta.setString(7, dto.getDescription());
			sta.setString(8, dto.getFileLocation());
			sta.setString(9, dto.getDomain());
			sta.setInt(10, dto.getId());
			int rows = sta.executeUpdate();
			if (rows == 1)
				ret = true;
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println(">>> OntologyDAO WARNING : " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	public boolean delete(int id) {
		boolean ret = false;
		String sql = "delete from ontology where id = ? ;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, id);
			int rows = sta.executeUpdate();
			if (rows == 1)
				ret = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	public List<OntologyDTO> queryAll() {
		List<OntologyDTO> ret = new ArrayList<OntologyDTO>();
		String sql = "select * from ontology;";
		try {
			sta = conn.prepareStatement(sql);
			set = sta.executeQuery();
			while (set.next()) {
				OntologyDTO dto = new OntologyDTO();
				dto.setId(set.getInt("id"));
				dto.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				dto.setUri(set.getString("uri"));
				dto.setOntologyName(set.getString("ontology_name"));
				dto.setModelType(set.getString("model_type"));
				dto.setOntologyType(set.getInt("ontology_type"));
				dto.setHeader(set.getString("header"));
				dto.setDescription(set.getString("description"));
				dto.setFileLocation(set.getString("fileLocation"));
				dto.setDomain(set.getString("domain"));
				dto.setUser(set.getString("user"));
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

	public OntologyDTO queryById(int id) {
		OntologyDTO ret = null;
		String sql = "select * from ontology where id = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, id);
			set = sta.executeQuery();
			if (set.next()) {
				ret = new OntologyDTO();
				ret.setId(set.getInt("id"));
				ret.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				ret.setUri(set.getString("uri"));
				ret.setOntologyName(set.getString("ontology_name"));
				ret.setModelType(set.getString("model_type"));
				ret.setOntologyType(set.getInt("ontology_type"));
				ret.setHeader(set.getString("header"));
				ret.setDescription(set.getString("description"));
				ret.setFileLocation(set.getString("fileLocation"));
				ret.setDomain(set.getString("domain"));
				ret.setUser(set.getString("user"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	public List<OntologyDTO> queryByTypeAndUser(int type, String user) {// ontology_type
		List<OntologyDTO> ret = new ArrayList<OntologyDTO>();
		String sql = "select * from ontology where ontology_type = ? and \"user\"=?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, type);
			sta.setString(2, user);
			set = sta.executeQuery();
			while (set.next()) {
				OntologyDTO dto = new OntologyDTO();
				dto.setId(set.getInt("id"));
				dto.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				dto.setUri(set.getString("uri"));
				dto.setOntologyName(set.getString("ontology_name"));
				dto.setModelType(set.getString("model_type"));
				dto.setOntologyType(set.getInt("ontology_type"));
				dto.setHeader(set.getString("header"));
				dto.setDescription(set.getString("description"));
				dto.setFileLocation(set.getString("fileLocation"));
				dto.setDomain(set.getString("domain"));
				dto.setUser(set.getString("user"));
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

	public OntologyDTO queryByDomainRO(String domain) {
		OntologyDTO ret = null;
		String sql = "select * from ontology where ontology_type = 0 AND domain = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setString(1, domain);
			set = sta.executeQuery();
			if (set.next()) {
				ret = new OntologyDTO();
				ret.setId(set.getInt("id"));
				ret.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				ret.setUri(set.getString("uri"));
				ret.setOntologyName(set.getString("ontology_name"));
				ret.setModelType(set.getString("model_type"));
				ret.setOntologyType(set.getInt("ontology_type"));
				ret.setHeader(set.getString("header"));
				ret.setDescription(set.getString("description"));
				ret.setFileLocation(set.getString("fileLocation"));
				ret.setDomain(set.getString("domain"));
				ret.setUser(set.getString("user"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}
	
	public List<OntologyDTO> queryByDomain(String domain) {
		List<OntologyDTO> ontos = new ArrayList<OntologyDTO>();
		
		String sql = "select * from ontology where domain = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setString(1, domain);
			set = sta.executeQuery();
			while (set.next()) {
				OntologyDTO ret = new OntologyDTO();
				ret = new OntologyDTO();
				ret.setId(set.getInt("id"));
				ret.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				ret.setUri(set.getString("uri"));
				ret.setOntologyName(set.getString("ontology_name"));
				ret.setModelType(set.getString("model_type"));
				ret.setOntologyType(set.getInt("ontology_type"));
				ret.setHeader(set.getString("header"));
				ret.setDescription(set.getString("description"));
				ret.setFileLocation(set.getString("fileLocation"));
				ret.setDomain(set.getString("domain"));
				ret.setUser(set.getString("user"));
				ontos.add(ret);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ontos;
	}
	

	public List<OntologyDTO> queryByType(int type) {// ontology_type
		List<OntologyDTO> ret = new ArrayList<OntologyDTO>();
		String sql = "select * from ontology where ontology_type = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, type);
			set = sta.executeQuery();
			while (set.next()) {
				OntologyDTO dto = new OntologyDTO();
				dto.setId(set.getInt("id"));
				dto.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				dto.setUri(set.getString("uri"));
				dto.setOntologyName(set.getString("ontology_name"));
				dto.setModelType(set.getString("model_type"));
				dto.setOntologyType(set.getInt("ontology_type"));
				dto.setHeader(set.getString("header"));
				dto.setDescription(set.getString("description"));
				dto.setFileLocation(set.getString("fileLocation"));
				dto.setDomain(set.getString("domain"));
				dto.setUser(set.getString("user"));
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

	public OntologyDTO queryByUri(String uri) {
		OntologyDTO ret = null;
		String sql = "select * from ontology where uri = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setString(1, uri);
			set = sta.executeQuery();
			if (set.next()) {
				ret = new OntologyDTO();
				ret.setId(set.getInt("id"));
				ret.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				ret.setUri(set.getString("uri"));
				ret.setOntologyName(set.getString("ontology_name"));
				ret.setModelType(set.getString("model_type"));
				ret.setOntologyType(set.getInt("ontology_type"));
				ret.setHeader(set.getString("header"));
				ret.setDescription(set.getString("description"));
				ret.setFileLocation(set.getString("fileLocation"));
				ret.setDomain(set.getString("domain"));
				ret.setUser(set.getString("user"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	public OntologyDTO queryByFileName(String file) {
		OntologyDTO ret = null;
		String sql = "select * from ontology where fileLocation = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setString(1, file);
			set = sta.executeQuery();
			if (set.next()) {
				ret = new OntologyDTO();
				ret.setId(set.getInt("id"));
				ret.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				ret.setUri(set.getString("uri"));
				ret.setOntologyName(set.getString("ontology_name"));
				ret.setModelType(set.getString("model_type"));
				ret.setOntologyType(set.getInt("ontology_type"));
				ret.setHeader(set.getString("header"));
				ret.setDescription(set.getString("description"));
				ret.setFileLocation(set.getString("fileLocation"));
				ret.setDomain(set.getString("domain"));
				ret.setUser(set.getString("user"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	// LOs based on RO
	public List<OntologyDTO> queryBaseRO(int id) {// ontology_type
		List<OntologyDTO> result = new ArrayList<OntologyDTO>();

		//Connection conn = DBConnector.getConnection();
		Connection conn=Access.getConnection();
		OntologyDAO dao = new OntologyDAO(conn);
		OntologyDTO odto = dao.queryById(id);
		String uri = odto.getUri();

		List<OntologyDTO> rets = queryAll();
		OntologyAtomicConstructDAO oacdao = new OntologyAtomicConstructDAO(conn);

		for (OntologyDTO dto : rets) {
			try {
				List<String> oacdtos = oacdao.queryByOntologyID(dto.getId());
				for (String oadto : oacdtos) {
					if (oadto.equals(uri) && !(uri.equals(dto.getUri()))) {
						result.add(dto);
						// break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {				
			}
		}
		 //DBConnector.closeStatement(sta);
		Access.closeStatement(sta);
		 oacdao.closeDBConnection();
		 dao.closeDBConnection();

		// result.remove(odto);
		 //System.out.println("<<<<<<<<<<<>>>>>>>>>"+result.size());

		return result;

		// OntologyManagement management = new OntologyManagement();
		// OntologyForMoreInfoDTO odto_more = management.getOntologyInfo(id);
		// management.closeDBConnection();
		//
		// Connection conn = DBConnector.getConnection();
		// OntologyDAO dao = new OntologyDAO(conn);
		// List<OntologyDTO> all = dao.queryAll();
		// OntologyComponentDAO ao = new OntologyComponentDAO(conn);
		// OntologyAtomicConstructDAO dd = new OntologyAtomicConstructDAO(conn);
		//
		// for (OntologyDTO dto : all) {
		// if (dto.getJoinid() != id) {
		// List<OntologyComponentDTO> coms = ao.queryByOntologyId(dto
		// .getJoinid());
		// // System.out.println("--" + dto.getJoinid());
		// for (int i = 0; i < coms.size(); i++) {
		// OntologyComponentDTO com = coms.get(i);
		// if (odto_more.getUri().equals(com.getNamespace())) {
		// ret.add(dto);
		// } else {
		// List<OntologyAtomicConstructDTO> atoms = dd
		// .queryByComponentId(com.getJoinid());
		// for (int j = 0; j < atoms.size(); j++) {
		// OntologyAtomicConstructDTO atom = atoms.get(j);
		// System.out.println("<<<<<<<"+atom.getNamespace());
		// if (odto_more.getUri().equals(atom.getNamespace())) {
		// ret.add(dto);
		// }
		// }
		// }
		// }
		// }
		// }
		// dao.closeDBConnection();
		// ao.closeDBConnection();
		// dd.closeDBConnection();
		// return removeDuplicate(ret);
	}

//	public List<OntologyDTO> removeDuplicate(List<OntologyDTO> dtos) {
//		List<OntologyDTO> res = new ArrayList<OntologyDTO>();
//		List<OntologyDTO> temp = new ArrayList<OntologyDTO>();
//		int length = dtos.size();
//		if (length != 0) {
//			res.add(dtos.get(0));
//		}
//
//		for (int i = 0; i < length; i++) {
//			OntologyDTO dto = dtos.get(i);
//			int id = dto.getJoinid();
//			// System.out.println("==" + res.size());
//			for (int j = 0; j < res.size(); j++) {
//				int id_2 = res.get(j).getJoinid();
//				if (id_2 == id) {
//					temp.add(dto);
//				}
//			}
//			if (temp.size() == 0) {
//				res.add(dto);
//			}
//		}
//		return res;
//
//	}

	public OntologyDTO queryByUriAndUser(String uri, String user) {
		OntologyDTO ret = null;
		String sql = "select * from ontology where uri = ? and user = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setString(1, uri);
			sta.setString(2, user);
			set = sta.executeQuery();
			if (set.next()) {
				ret = new OntologyDTO();
				ret.setId(set.getInt("id"));
				ret.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				ret.setUri(set.getString("uri"));
				ret.setOntologyName(set.getString("ontology_name"));
				ret.setModelType(set.getString("model_type"));
				ret.setOntologyType(set.getInt("ontology_type"));
				ret.setHeader(set.getString("header"));
				ret.setDescription(set.getString("description"));
				ret.setFileLocation(set.getString("fileLocation"));
				ret.setDomain(set.getString("domain"));
				ret.setUser(set.getString("user"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}
	
	
	public List<OntologyDTO> queryByTypeAndDomain(int type, String domain) {
		 List<OntologyDTO> rets =new  ArrayList<OntologyDTO>();
		OntologyDTO ret = null;
		String sql = "select * from ontology where ontology_type = ? and domain = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, type);
			sta.setString(2, domain);
			//System.out.println(sta);
			set = sta.executeQuery();
			while (set.next()) {
				ret = new OntologyDTO();
				ret.setId(set.getInt("id"));
				ret.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				ret.setUri(set.getString("uri"));
				ret.setOntologyName(set.getString("ontology_name"));
				ret.setModelType(set.getString("model_type"));
				ret.setOntologyType(set.getInt("ontology_type"));
				ret.setHeader(set.getString("header"));
				ret.setDescription(set.getString("description"));
				ret.setFileLocation(set.getString("fileLocation"));
				ret.setDomain(set.getString("domain"));
				ret.setUser(set.getString("user"));
				rets.add(ret);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		//System.out.println("SIZE: "+rets.size());		
		return rets;
	}
	
	/**
	 * @author ssj
	 * @param name 用户输入的本体名称
	 * @return 返回包含name的所有的本体
	 */
	public List<OntologyDTO> queryByName(String name) {
		 List<OntologyDTO> rets =new  ArrayList<OntologyDTO>();
			OntologyDTO ret = null;
			String sql = "select * from ontology where upper(ontology_name) like '%" + name.toUpperCase() + "%'";
			
			try {
				sta = conn.prepareStatement(sql);
				//System.out.println(sta);
				set = sta.executeQuery();
				while (set.next()) {
					ret = new OntologyDTO();
					ret.setId(set.getInt("id"));
					ret.setAdministeredItemAdministrationRecord(set
							.getString("administered_item_administration_record"));
					ret.setUri(set.getString("uri"));
					ret.setOntologyName(set.getString("ontology_name"));
					ret.setModelType(set.getString("model_type"));
					ret.setOntologyType(set.getInt("ontology_type"));
					ret.setHeader(set.getString("header"));
					ret.setDescription(set.getString("description"));
					ret.setFileLocation(set.getString("fileLocation"));
					ret.setDomain(set.getString("domain"));
					ret.setUser(set.getString("user"));
					rets.add(ret);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				//DBConnector.closeStatement(sta);
				Access.closeStatement(sta);
			}
			return rets;
	}
}
