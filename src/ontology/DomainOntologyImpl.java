package ontology;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import databaseaccess.Access;

public class DomainOntologyImpl implements IDomainOntology {

	public String getDomainOntologyFilePath(int domainOntologyid) {
		OntologyDAO dao = new OntologyDAO();
		OntologyDTO dto = dao.queryById(domainOntologyid);
		String path = dto.getFileLocation();
		dao.closeDBConnection();
		String lastPath = "";
		if (dto.getOntologyType() == 0) {
			lastPath = "RO\\" + path;
		} else if (dto.getOntologyType() == 1) {
			lastPath = "LO\\" + path;
		} else if (dto.getOntologyType() == 1) {
			lastPath = "MODEL\\" + path;
		}
		return lastPath;
	}

	public List<OntologyDTO> getLOsBaseRO(int id) {
		//Connection conn = DBConnector.getConnection();
		Connection conn=Access.getConnection();
		OntologyDAO odao = new OntologyDAO(conn);
		List<OntologyDTO> dtosBme = odao.queryBaseRO(id);
		return dtosBme;
	}

	public OntologyDTO getROByDomain(String domain) {
		//Connection conn = DBConnector.getConnection();
		Connection conn=Access.getConnection();
		OntologyDAO odao = new OntologyDAO(conn);
		OntologyDTO dto = odao.queryByDomainRO(domain);
		odao.closeDBConnection();
		return dto;
	}

	/*
	 * 修改一下，大概过程我已经写好了，不要用杨写的dao进行操作，直接写sql语句, 返回结果用我已经包装好的SameAsResult
	 * 
	 * @see org.sklse.interfaces.IDomainOntology#searchSameAS(int,
	 *      java.lang.String)
	 */
	public List<SameAsResult> getSameAS(int domainOntologyID,
			String nonLogicalSymbol) {
		List<SameAsResult> sames = new ArrayList<SameAsResult>();
//		List<SameAsResult> result = new ArrayList<SameAsResult>();
		//Connection conn = DBConnector.getConnection();
		Connection conn=Access.getConnection();
		SameAsOfAtomicConstructDAO soa = new SameAsOfAtomicConstructDAO(conn);
		OntologyDAO dao = new OntologyDAO();
		OntologyDTO dto = dao.queryById(domainOntologyID);

		dao.closeDBConnection();
		int type = dto.getOntologyType();
		if (type == 0) {
			sames.add(soa.querySameASByRO(domainOntologyID, nonLogicalSymbol));
		} else {
			sames = soa.querySameASByLO(domainOntologyID, nonLogicalSymbol);
		}
		// close connection
		//DBConnector.closeConnection(conn);
		Access.closeConnection(conn);
		//System.out.println("?????"+sames.size());
//		for (SameAsResult res : sames) {
//			List<LO> los = res.getList_LO();
//			LO lo1 = los.get(0);
//			System.out.println(res.getNonLogical_RO()+lo1.getNonLogical_LO());
//			if ((res.getNamespace_RO().equals(lo1.getNamespace_LO()) && (res
//					.getNonLogical_RO().equals(lo1.getNonLogical_LO())))) {
//				result.add(res);
//			}
//		}
		return sames;
	}
}

