package dbConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDBConnection {

	// ================= singleton 패턴 만들기 시작 =================== //   
	/*
	    !!! === singleton 패턴에서 중요한 것은 다음의 3가지 이다  === !!!
	    
	    == 첫번째,
	       private 변수로 자기 자신의 클래스 인스턴스를 가지도록 해야 한다.
	       접근제한자가 private 이므로 외부 클래스에서는 직접적으로 접근이 불가하다.
	       또한 static 변수로 지정하여 SingletonNumber 클래스를 사용할 때 
	       객체생성은 딱 1번만 생성되도록 해야 한다.  
	*/   
	
	// java 가 읽는 순서는 먼저 필드를 읽은 후 static 초기화 블럭이 실행되고 난 후에 객체가 생성된다.
	// 하지만 처음 한 번은 static 초기화 블럭이 실행되지만 그 후에는 새로운 객체(인스턴스) 를 호출하도라도
	// static 초기화 블럭은 호출되지 않는다!!
	
	// --> field (첫번째로 작동) <-- //
	// static 변수
	private static Connection conn = null;		// = null; 로 초기화를 하지 않더라도 초기치 null임.
	
	// --> static 초기화 블럭(두번째로 작동) <-- //
	static {
		// 중요한 사실은 static 초기화 블럭은 해당 클래스가 객체로 생성되기전에 먼저 실행되어지며,
        // 딱 1번만 호출되어지고 다음번에 새로운 객체(인스턴스)를 매번 생성하더라도 
        // static 초기화 블럭은 호출이 안되어진다.
		
		try {
			/*
			 * // >>> 1. 오라클 드라이버 로딩 <<<
			 * 
			 * === OracleDriver(오라클 드라이버)의 역할 === 1). OracleDriver 를 메모리에 로딩시켜준다. 2).
			 * OracleDriver 객체를 생성해준다. 3). OracleDriver 객체를 DriverManager에 등록시켜준다. -->
			 * DriverManager 는 여러 드라이버들을 Vector 에 저장하여 관리해주는 클래스이다.
			 */
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 드라이버에 올라가게 함. --> 해당 class가 있는 지 업는지 try catch 해야한다.
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "MINI_ORAUSER3", "gclass");
			
		} catch (ClassNotFoundException e) { // --> OracleDriver.class 확인 드라이버가 올라와야한다. 해당 exception을 try-catch 해준다.
			System.out.println(">>> ojdbc8.jar 파일이 없습니다. <<<");
		} catch (SQLException e) { 
			// ip 가 틀렸거나 비밀번호가 틀렸거나 해당 exception을 try-catch 해준다.
			e.printStackTrace();
		}

	}	// end of static
	
	
	// == 두번째,
	// 생성자에 접근제한자를 private 으로 지정하여, 외부에서 절대로 인스턴스를 생성하지 못하도록 막아버린다.
	private MyDBConnection() { } 
	
	
	// == 세번째
	// static 메소드를 생성[지금은 getConn() ] 하여 외부에서 해당 클래스의 객체를 사용할 수 있도록 해준다.
	public static Connection getConn() {
		return conn;
	}
	
	
	// ================= singleton 패턴 만들기 끝 =================== //
	
	
	/////////////////////////////////////////////////////////////////////////////////////
	
	// === Connection conn 객체 자원 반납하기 === //
	
	public static void closeConnection() {
		try {
			if(conn != null)
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	} 
	// === Connection conn 객체 자원 반납하기 끝 === //	
	
}
