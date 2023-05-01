package RandomLunch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LunchBoxDAO {

	//메뉴 및 식당 추가
	public int rlboxInsert(RLunchBox rlbox) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "  insert into lunch(lunch_num,lunch_sicdang,lunch_kind,lunch_menu,lunch_ref, "
				+ " lunch_seq,lunch_lev) values (lunch_sequ.nextval,null,?,?,lunch_sequ.nextval,?,?)";
		int result = 0;

		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "lunch", "1234");


			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rlbox.getLunch_kind());
			pstmt.setString(2, rlbox.getLunch_menu());

			pstmt.setInt(3, 0);
			pstmt.setInt(4, 0);

			result = pstmt.executeUpdate();
			System.out.println(result + "행이 추가되었습니다.");

		} catch (ClassNotFoundException cnfe) {
			System.out.println("해당클래스 찾을 수 없음.");
			System.out.println(cnfe.getMessage());
			cnfe.printStackTrace();
		} catch (SQLException se) {

			result = 19909;

		} finally {

			try {
				if (pstmt != null)
					pstmt.close();// 꼭 닫아줘야함 ㅇㅇ

			} catch (SQLException e) {

			}
			try {
				if (conn != null)
					conn.close();// 꼭 닫아줘야함 ㅇㅇ

			} catch (Exception e) {

			}

		}
		return result;
	}

	//메뉴 및 식당 전체보기
	public ArrayList<RLunchBox> selectAll() {

		ArrayList<RLunchBox> l = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "select rownum, lunch_num, lunch_menu, lunch_kind, lunch_sicdang, lunch_ref, \r\n"
				+ "			lunch_seq,lunch_lev\r\n" + "		from (select * from lunch \r\n"
				+ "					order by lunch_ref desc, lunch_seq asc)";

//			select lunch_num, lunch_sicdang, lunch_kind, lunch_menu, lunch_ref, 
//			lunch_seq,lunch_lev from (select rownum rnum, l.* from 
//					(select * from lunch order by lunch_ref desc, lunch asc)l
//					where rownum <=?)
//							where rnum >= ? and rnum <=?;
		ResultSet result = null;
		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "lunch", "1234");

			System.out.println("데이터베이스에 접속했습니다.");
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeQuery();
			int count = 0;
			while (result.next()) {
				if (count++ == 0) {
					l = new ArrayList<RLunchBox>();
				}
				RLunchBox rl = new RLunchBox();
				rl.setLunch_num(result.getInt("lunch_num"));
				rl.setLunch_kind(result.getString("lunch_kind"));

				if (rl.getLunch_kind() == null) {

					rl.setLunch_kind("  ");

				}
				rl.setLunch_menu(result.getString("lunch_menu"));

				if (rl.getLunch_menu() == null) {

					rl.setLunch_menu("  ");
				}
				rl.setLunch_sicdang(result.getString("lunch_sicdang"));
				if (rl.getLunch_sicdang() == null) {

					rl.setLunch_sicdang("  ");
				}
				rl.setLunch_lev(result.getInt("lunch_lev"));
				rl.setLunch_ref(result.getInt("lunch_ref"));
				rl.setLunch_seq(result.getInt("lunch_seq"));
				l.add(rl);
			}

		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
			cnfe.getStackTrace();
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			se.getStackTrace();
		} finally {
			try {
				if (pstmt != null && result != null) {
					pstmt.close();
					result.close();
				}
			} catch (SQLException e) {
				e.getStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {

			}
		}
		return l;
	}

	//메뉴 및 식당선택
	public RLunchBox select(int no) {

		RLunchBox rl = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;

		String sql = "select * from lunch where lunch_num =? ";

		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "lunch", "1234");

			// System.out.println("데이터 베이스에 접속했습니다.");

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, no);
			result = pstmt.executeQuery();
			if (result.next()) {
				rl = new RLunchBox();
				rl.setLunch_menu(result.getString("lunch_menu"));
				rl.setLunch_kind(result.getString("lunch_kind"));
				rl.setLunch_num(result.getInt("lunch_num"));
				rl.setLunch_sicdang(result.getString("lunch_sicdang"));
				if (rl.getLunch_sicdang() == null) {

					rl.setLunch_sicdang("  ");
				}

				rl.setLunch_lev(result.getInt("lunch_lev"));
				rl.setLunch_ref(result.getInt("lunch_ref"));
				rl.setLunch_seq(result.getInt("lunch_seq"));
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("해당클래스 찾을 수 없음.");
			System.out.println(cnfe.getMessage());
		} catch (SQLException se) {
			System.out.println("db주소랑 아이디 비번확인 ㅇㅇ");
			System.out.println(se.getMessage());
			se.printStackTrace();

		} finally {

			try {
				if (pstmt != null) {
					pstmt.close();// 꼭 닫아줘야함 ㅇㅇ
				}
			} catch (SQLException e) {

			}
			try {
				if (conn != null)
					conn.close();// 꼭 닫아줘야함 ㅇㅇ

			} catch (Exception e) {

			}

		}

		return rl;

	}

	//메뉴 및 식당 업데이트
	public int update(RLunchBox rl) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update lunch set lunch_menu =?, lunch_kind =?" + " where lunch_num =?";

		int result = -1;
		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "lunch", "1234");
			System.out.println("데이터 베이스에 접속했습니다.");
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, rl.getLunch_menu());
			pstmt.setString(2, rl.getLunch_kind());
			pstmt.setInt(3, rl.getLunch_num());
			// System.out.println(sql);

			result = pstmt.executeUpdate();

			System.out.println(result);
		} catch (ClassNotFoundException cnfe) {
			cnfe.getStackTrace();
		} catch (SQLException se) {
			se.getStackTrace();
		} finally {

			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.getStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.getStackTrace();
			} catch (Exception e) {
			}
		}
		return result;
	}

	//식당추천(답글)
	public int sicdangrecommend(RLunchBox rl) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		int lunch_ref = rl.getLunch_ref();
		int lunch_lev = rl.getLunch_lev();
		int lunch_seq = rl.getLunch_seq();
		String sql = "update lunch set lunch_seq = lunch_seq+ 1 where  lunch_ref= ? and lunch_seq >?";
		String sql2 = " insert into lunch(lunch_num,lunch_sicdang, " + "lunch_ref, " + "lunch_seq, "
				+ "lunch_lev) values (lunch_sequ.nextval,?,?,?,?)";
		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "lunch", "1234");
			System.out.println("데이터베이스에 접속했습니다.");

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, lunch_ref);
			pstmt.setInt(2, lunch_seq);
			int result1 = pstmt.executeUpdate();
			pstmt.close();

			lunch_seq = lunch_seq + 1;
			lunch_lev = lunch_lev + 1;

			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, rl.getLunch_sicdang());
			pstmt.setInt(2, rl.getLunch_ref());
			pstmt.setInt(3, lunch_seq);
			pstmt.setInt(4, lunch_lev);
			int result2 = pstmt.executeUpdate();
			pstmt.close();
			conn.commit();
			// System.out.println(sql);
			// System.out.println(sql2);
			result = result1 + result2;

		} catch (ClassNotFoundException cnfe) {

			System.out.println(cnfe.getMessage());
		} catch (SQLException se) {
			try {
				System.out.println(se.getMessage());
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();

			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();

			}

		}
		return result;
	}

	// 런치박스 메뉴 및 식당 지우기
	public int lunchDelete(RLunchBox rl) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = " delete from lunch where lunch_ref = ? " + "					and lunch_lev >=? "
				+ "					and lunch_seq>=? "
				+ "					and lunch_seq <=(nvl((select min(lunch_seq)-1 "
				+ "											from lunch"
				+ "											where lunch_ref = ? "
				+ "											and lunch_lev = ? "
				+ "											and lunch_seq>?) ,"
				+ "											(select max (lunch_seq) "
				+ "											from lunch "
				+ "											where lunch_ref = ?))) ";
		ResultSet rs = null;
		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "lunch", "1234");
			// System.out.println("데이터 베이스에 접속했습니다.");
			// System.out.println(sql);
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, rl.getLunch_ref());
			pstmt.setInt(2, rl.getLunch_lev());
			pstmt.setInt(3, rl.getLunch_seq());
			pstmt.setInt(4, rl.getLunch_ref());
			pstmt.setInt(5, rl.getLunch_lev());
			pstmt.setInt(6, rl.getLunch_seq());
			pstmt.setInt(7, rl.getLunch_ref());

			count = pstmt.executeUpdate();
			System.out.println(count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.getStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException se) {
				se.getStackTrace();
			}
			try {
				if (pstmt != null)
					pstmt.close();// 꼭 닫아줘야함 ㅇㅇ

			} catch (SQLException e) {

			}
			try {
				if (conn != null)
					conn.close();// 꼭 닫아줘야함 ㅇㅇ

			} catch (Exception e) {

			}
		}
		return count;
	}

	//메뉴 및 식당보기
	public ArrayList<RLunchBox> shwomenu() {
		ArrayList<RLunchBox> l = null;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "lunch", "1234");

			// System.out.println("데이터베이스에 접속했습니다.");
			st = conn.createStatement();

			String sql = "select  lunch_kind,lunch_menu from lunch " + "	where lunch_menu is not null ";

			rs = st.executeQuery(sql);

			int count = 0;
			while (rs.next()) {
				if (count++ == 0) {
					l = new ArrayList<RLunchBox>();
				}
				RLunchBox rl = new RLunchBox();

				rl.setLunch_kind(rs.getString(1));
				rl.setLunch_menu(rs.getString(2));

				l.add(rl);
			}

		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
			cnfe.getStackTrace();
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			se.getStackTrace();
		} finally {
			try {
				if (st != null && rs != null) {
					st.close();
					rs.close();
				}
			} catch (SQLException e) {
				e.getStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.getStackTrace();
				System.out.println(e.getMessage());
			}
		}
		return l;
	}

	// 랜덤 번호의 메뉴 답글까지 보내기
	public ArrayList<RLunchBox> random(int num) {
		RLunchBox rl = null;
		ArrayList<RLunchBox> l = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;

		String sql = "select lunch_num,lunch_kind, lunch_menu, lunch_sicdang from lunch where lunch_ref =?"
				+ "order by lunch_num";

		try {

			String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "lunch", "1234");
			// System.out.println("데이터 베이스에 접속했습니다.");

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, num);
			result = pstmt.executeQuery();
			int count = 0;
			while (result.next()) {
				if (count++ == 0) {
					l = new ArrayList<RLunchBox>();
				}
				rl = new RLunchBox();
				rl.setLunch_menu(result.getString("lunch_menu"));
				if (rl.getLunch_menu() == null) {
					rl.setLunch_menu("  ");
				}
				rl.setLunch_kind(result.getString("lunch_kind"));

				if (rl.getLunch_kind() == null) {

					rl.setLunch_kind("  ");

				}
				rl.setLunch_num(result.getInt("lunch_num"));

				rl.setLunch_sicdang(result.getString("lunch_sicdang"));
				if (rl.getLunch_sicdang() == null) {
					rl.setLunch_sicdang("  ");

				}
				l.add(rl);
				// rl.setLunch_ref(result.getInt("lunch_ref"));
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("해당클래스 찾을 수 없음.");
			System.out.println(cnfe.getMessage());
		} catch (SQLException se) {
			System.out.println("db주소랑 아이디 비번확인 ㅇㅇ");
			System.out.println(se.getMessage());
			se.printStackTrace();

		} finally {

			try {
				if (pstmt != null) {
					pstmt.close();// 꼭 닫아줘야함 ㅇㅇ
				}
			} catch (SQLException e) {

			}
			try {
				if (conn != null)
					conn.close();// 꼭 닫아줘야함 ㅇㅇ

			} catch (Exception e) {

			}

		}

		return l;

	}

	// 메뉴의 number만 뽑아서 menu_num넘김
	public ArrayList<Integer> menunumber() {
		ArrayList<Integer> numm = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select lunch_num " + "from lunch where lunch_seq =0 ";
//					"select rnum from (select rownum rnum, lunch_num " + "from lunch where lunch_seq =0"
		// + "and lunch_lev = 0)";

		// System.out.println(sql);
		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "lunch", "1234");

			 System.out.println("데이터 베이스에 접속했습니다.");

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			// 읽어온 쿼리의 내용이 int 형임.

			int count = 0;

			while (rs.next()) {
				if (count++ == 0) {

					numm = new ArrayList<Integer>();
					System.out.println();
				}
				numm.add(rs.getInt(1));

			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("해당클래스 찾을 수 없음.");
			System.out.println(cnfe.getMessage());
		} catch (SQLException se) {
			System.out.println("db주소랑 아이디 비번확인 ㅇㅇ");
			System.out.println(se.getMessage());
			se.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {

			}

			try {
				if (pstmt != null) {
					pstmt.close();// 꼭 닫아줘야함 ㅇㅇ
				}
			} catch (SQLException e) {

			}
			try {
				if (conn != null)
					conn.close();// 꼭 닫아줘야함 ㅇㅇ

			} catch (Exception e) {

			}

		}

		return numm;
	}
}
