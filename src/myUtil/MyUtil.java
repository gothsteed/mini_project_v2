package myUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtil {



	// === 현재시각을 출력해주는 static 메소드를 생성한다. === //
	public static void current_time_print() {
		
		Date now = new Date(); // 현재시각
		SimpleDateFormat sdfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("== 현재시각 : " + sdfmt.format(now));
	}// end of public static void current_time_print()------
	
	
	// === 소문자3개와 숫자4개로 이루어진 랜덤한 인증키 만들기 === //
	// 예> asa9040   txa2356 
	public static String certification_key() {
		
		Random rnd = new Random();
		// 처음얼마부터 마지막얼마까지 랜덤한 정수
		// ==> rnd.nextInt(마지막수 - 처음수 + 1) + 처음수;
		
		String certification_key = "";
		
		for(int i=0; i<7; i++) {
			
			if(i<3) { // 랜덤한 소문자를 발생시킨다.
				char ch = (char)(rnd.nextInt('z' - 'a' + 1) + 'a'); 
				certification_key += ch;
			}
			
			else { // 랜덤한 숫자 0 부터 9 까지 발생시킨다.
				int n = rnd.nextInt(9 - 0 + 1) + 0;
				certification_key += n;
			}
		}// end of for----------------------
		
		return certification_key;
	}// end of public static String certification_key()--------


	// 입력받은 문자열에서 공백을 제거해주는 메소드 생성하기 
	public static String space_delete(String input_str) {
		
		String result = null;
		
		if(input_str != null) {
			result = "";
			for(int i=0; i<input_str.length(); i++) {
				char ch = input_str.charAt(i);
				if(ch != ' ') 
					result += ch; 
			}// end of for-------------------------
		}
		
		return result;
	}// end of public static String space_delete(String input_str)------
	
	
	// == 비밀번호가 규칙에 맞는지 틀리는지 알려주는 static 메소드 생성하기 == //
	/*
	   비밀번호 규칙은 비밀번호의 길이는 8글자 이상 15글자 이하이어야 하고,
	   또한 비밀번호는 영문대문자, 영문소문자, 숫자, 특수기호가 혼합되어야만 한다.
	   우리는 규칙에 맞으면 true 를 리턴해주고, 규칙에 틀리면 false 를 리턴하도록 만든다.  
	*/
	public static boolean isCheckPasswd(String passwd) {
		
		if(passwd == null)
			return false;
		
		// 예를들어서
		// 입력받은 passwd 가 C5d# 이라면 dsfsdf2342@#$@kfjsASFSFDF 이라면 
		int length = passwd.length();
		if( length < 8 || length > 15 ) {
			return false;
		}
				
		// 이제부터는 입력받은 passwd 의 글자수가 8글자 이상 15글자 이하인 경우이다.
		// 예를들어서
		// 입력받은 passwd 가 C5d#하하호호 이라면  C5d#하하s@! 이라면
		// 또는 
		// 입력받은 passwd 가 C5dawxab 이라면   c5dawxab# 이라면
		// 입력받은 passwd 가 C5dawxab@ 이라면  c5dawxab#T 이라면

		boolean flag_upper   = false; // 영문대문자 표식을 위한 용도
		boolean flag_lower   = false; // 영문소문자 표식을 위한 용도
		boolean flag_number  = false; // 숫자 표식을 위한 용도
		boolean flag_special = false; // 특수문자 표식을 위한 용도
		
		for(int i=0; i<length; i++) {
			// 암호에 한글이 들어가 있는지 알아본다.
			char ch = passwd.charAt(i);
			if('가' <= ch && ch <= '힣') {
				return false;
			}
			 
			if(Character.isUpperCase(ch)) // 영문대문자일 경우
				flag_upper = true;
			
			else if(Character.isLowerCase(ch)) // 영문소문자일 경우
				flag_lower = true;
			
			else if(Character.isDigit(ch) ) // 숫자일 경우
				flag_number = true;
			
			else // 특수문자일 경우  
				flag_special = true;
			
		}// end of for-------------------------------
		
		return flag_upper && flag_lower && flag_number && flag_special;
		
	}// end of public static boolean isCheckPasswd(String passwd)------


	public static long delete_character(String money) {
		
//	      String money_1 = "$2,000,000";
//	      String money_2 = "$500,000";
//	      String money_2 = "$500";
//	      String money_2 = "500";
//	      String money_1 = "$2,$000,$000$";	"2," + "$000,$000$"	"2,000,$000$	"2,000," + $000$"	"2,000,000$"	"2,000,000" + "" ==> "2,000,000"		

			do {
				
				int dollor_index = money.indexOf("$");	// "$" 의 인덱스(위치) 값
				
				if(dollor_index == -1)	// "$" 값이 없을 경우
					break;	// 종료
				
				money = money.substring(0, dollor_index) + money.substring(dollor_index + 1);
				// === 3. "문자열".substring(int 시작인덱스, int 끝인덱스) ===
				// "안녕하세요".substring(1, 4);
				// ==> "안녕하세요" 에서 시작인덱스가 1인 글자("녕") 부터
				// "안녕하세요" 에서 끝인덱스 4인 글자("요") 앞(인덱스 3)에까지("세")만 뽑아온다.
				
			} while (true);
			
//			System.out.println(money);
		//  2,000,000 
			
			
			do {

				int comma_index = money.indexOf(","); // "," 의 인덱스(위치) 값

				if (comma_index == -1) // "," 값이 없을 경우
					break; // 종료

				money = money.substring(0, comma_index) + money.substring(comma_index + 1);

			} while (true);

//			System.out.println(money);
		//  2000000
			
			return Long.parseLong(money);
		//  2000000
			
	}// end of 	public static long delete_character(String str) -------------------------


	// === 숫자에 3자리 마다 , 를 추가해서 문자열로 리턴시켜주는 메소드 생성하기 === //
	public static String append_comma(long num) {

		// >> 숫자를 문자열로 변경시켜주는 메소드 << //
		// 숫자를 문자열로 변경시켜주는 메소드
		// Integer.toString(12345); ==> "12345"
		// Long.toString(1234567890123456789L);	==> "1234567890123456789"
		// String.valueOf(12345); ==> "12345"
		// String.valueOf(1234567890123456789L);	==> "1234567890123456789"
		
		String temp = String.valueOf(num);
		// num 이 2500000 이라면 temp 는 "2500000"(문자열)
		// num 이 892500000 이라면 temp 는 "892500000"(문자열)
		
		char[] origin_arr = temp.toCharArray();
		/*
		   ---------------		
		   |2|5|0|0|0|0|0|	==> 7		
		   ---------------
		   
		   -------------------
		   |8|9|2|5|0|0|0|0|0|	==> 9
		   -------------------
		 */
		
		int comma_len = (origin_arr.length % 3==0) ? (origin_arr.length / 3) - 1 : origin_arr.length / 3;
		char[] result_arr = new char[origin_arr.length + comma_len];
		/*
		   -------------------		
		   |2|,|5|0|0|,|0|0|0|		==> 4번째마다 , 가 있다.
		   -------------------
		   
		   -----------------------
		   |8|9|2|,|5|0|0|,|0|0|0|
		   -----------------------
		 */
		
		
		/*
		   -------------------------------------------------------		
		   | ' ' | ' ' | '2' | '5' | '0' | '0' | '0' | '0' | '0' |		
		   -------------------------------------------------------
		*/

		for(int i = origin_arr.length - 1, j = result_arr.length - 1, count = 1; i >= 0; i--, j--, count++) {
			if(count%4 != 0) {
			result_arr[j] = origin_arr[i];
			}
			else {
				result_arr[j] = ',';
				i++;
			}

			/*
			   -------------------------------------------------------------------		
			   | ' ' | ' ' | '2' | ',' | '5' | '0' | '0' | ',' | '0' | '0' | '0' |		
			   -------------------------------------------------------------------
			 */
			
			
			
			
		}// end of for----------------------

		
		return String.valueOf(result_arr);
	}// end of 	public static String append_comma(long num) --------------------------


	// === 주민번호 7자리를 입력받아서 올바른 데이터 인지 검사해주는 메소드 생성 === //
	public static boolean isCheckJubun(String jubun) {
		
		// jubun 의 길이는 7자리 이면서 마지막 7번째에 들어오는 문자는 "1" "2" "3" "4" 중에 하나 이어야 한다.
		if(jubun.length() != 7) {	// jubun (주민번호) 가 7자리가 아니면 종료
			return false;
		}
		else if ( !( "1".equals(jubun.substring(6)) || "2".equals(jubun.substring(6))
			  || "3".equals(jubun.substring(6)) || "4".equals(jubun.substring(6)) ) ) { 
			// jubun 마지막자리가 1,2,3,4 가 아니라면 종료
			
			return false;
		} 
		else {
			// jubun 마지막자리가 1,2,3,4 라면
			
			String str_birthday = "";
			if ( "1".equals(jubun.substring(6)) || "2".equals(jubun.substring(6)) ) {
				str_birthday = "19" + jubun.substring(0, 6);	// 0 1 2 3 4 5 까지 (생년월일만)
				// 입력값 9510241 이기 때문에 birthday 에 1900 년대라는 것을 알려주기위해 "19" 를 추가해준다.
				
			}
			else {
				// "3".equals(jubun.substring(6)) || "4".equals(jubun.substring(6))
				str_birthday = "20" + jubun.substring(0, 6);
				// 입력값 0010243 이기 때문에 birthday 에 2000 년대라는 것을 알려주기위해 "20" 를 추가해준다.
			}
			
			// 이러면 마지막을 "1" 혹은 "2" 로 할경우 앞에 19가 붙어서
			// 
			
			SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMdd");
			
			sdformat.setLenient(false);
			// false 로 해주어야만 입력한 값을 날짜 타입으로 변경할때 날짜로 되지 못하는 값일 경우 오류가 발생한다.
			// 날짜로 파싱될 때 허술하게 하지 말고 엄격하게 하라고 설정해주는 것이라고 생각하면 된다.
			
			// === 문자열을 날짜 형태로 변환하기 === //
			try {
				Date birthday = sdformat.parse(str_birthday);
				
				// 현재날짜와 회원의생일날짜를 비교해서 회원의생일날짜가 현재날짜보다 이후라면 안된다.
				Date now = new Date();	// 현재날짜시각
				String str_now = sdformat.format(now);	// 20240131 (오늘 날짜) 를 format(Date 타입을 String 타입으로 변환) 
				now = sdformat.parse(str_now);
				
				if (birthday.compareTo(now) > 0) {
					// birthday + " 이 " + now + " 보다 이후날짜 (미래) 인 경우.
					// 생년월일이 현재일 보다 미래인 경우
					return false;
				}
				else {
					// birthday + " 이 " + now + " 보다 현재일보다 과거인 경우.
					return true;
				}
				
				
			} catch (ParseException e) {	// 날짜로 사용할 수 없는 경우(달력에 없는 날짜인 경우)
				return false;
			}
		}
		
	}// end of public static boolean isCheckJubun(String jubun) ------------------------------

	// == 입력받은 주민번호(7자리)를 가지고 만나이를 구해주는 메소드 == //
	public static int age(String jubun) {
		
		MyUtil.isCheckJubun(jubun);
	      
	      Date now = new Date();
	      SimpleDateFormat sdfmt = new SimpleDateFormat("yyyyMMdd");
	      String str_today = sdfmt.format(now);
	      
	      String centry = ( "1".equals(jubun.substring(6)) || "2".equals(jubun.substring(6)) )?"19":"20"; 
	      String str_birthday = centry + jubun.substring(0, jubun.length()-1);
	      //                   "19720910"   "20020320"
	      
	      String str_nowYear_birthday = String.format("%tY", now) + jubun.substring(2, jubun.length()-1);
	       //                                                "2024"+"0910"   "2024"+"0320" 
	      
	      try {
	          Date date_today = sdfmt.parse(str_today);
	          Date date_nowYear_birthday = sdfmt.parse(str_nowYear_birthday);
	          
	          int n_nowYear = Integer.parseInt(String.format("%tY", now)); 
	          int n_birthYear = Integer.parseInt(str_birthday.substring(0, 4));
	          
	          if(date_nowYear_birthday.compareTo(date_today) > 0) {
	             return n_nowYear - n_birthYear - 1;
	          }
	          else {
	             return n_nowYear - n_birthYear;
	          }
	          
	      } catch (ParseException e) {
	         return -1;
	      }
	      
	   }// end of public static int age(String jubun) -----------------------------------
	   
	
	
	public static boolean isTrueDate(String dateStr) {
		      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		      dateFormat.setLenient(false);
		      
		       // 입력된 문자열이 날짜로 유효한 경우 true 반환
		      try {
		         Date date = dateFormat.parse(dateStr);
		         return true;
		      } catch (ParseException e) {
		         return false;
		      }
		       // 유효하지 않은 날짜 형식 false 반환
		      
		   }// end of public static boolean isTrueDate(String dateStr) -----------------------------------
		   
	
	
	public static boolean isTrueId(String IdStr) {					// 아이디 검사하기
		
		
		if (IdStr == null || IdStr.isBlank()) {
			System.out.println("[경고] 아이디는 공백이 아닌 값을 넣어주십시오.");
			return false;
		}

		else {


			Pattern p = Pattern.compile("^[A-Za-z][A-Za-z0-9]{1,9}$");
			
			Matcher m = p.matcher(IdStr); // id 를 p 패턴에 맞춰준 값을 m 값에 맞춰준다/

			// == 3. 판별하도록 한다. == //
			if (m.matches()) { // matches 는 boolean 타입이다. 일치하면 true, 일치하지 않으면 false
				return true;

			} else {
				System.out.println("[경고] 아이디는 첫글자는 영문이어야 하고 나머지는 " + "영문 또는 숫자로만 이루어진 글자길이는 3~10글자만 가능합니다.\n");
				return false;
			} // end of if~else (m.matches() )-------------------------------------

		} // end of if~else------------------------------------------------------
		

	}// end of public static boolean isTrueId(String IdStr) -----------------------------------
	   
	
	
	
	
	public static boolean isTrueEmail(String EmailStr) {					// 이메일 검사하기
		
		if (EmailStr == null || EmailStr.isBlank()) {
			System.out.println("[경고] 회사이메일은 공백이 아닌 값을 넣어주십시오.");
			return false;
		}
		
		else {

			if (EmailStr.length() <= 100) { // matches 는 boolean 타입이다. 일치하면 true, 일치하지 않으면 false
				String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
				Pattern pattern = Pattern.compile(emailRegex);
				
				if(pattern.matcher(EmailStr).matches()) {
					return true;
				}else {
					System.out.println("!!올바른 이메일 형식( ex - abcdef@nnnn.com) 을 입력하시요!!");
					return false;
				}
				
			}
			else {
				System.out.println("[경고] 회사이메일은 100글자 이하이어야 합니다. ");
				return false;
			}
		}
	
	
	      
	   }// end of 	public static boolean isTrueEmail(String EmailStr) -----------------------------------
	
	
	public static boolean isTrueAddr(String AddrStr) {					// 주소 검사하기
		
		if (AddrStr.length() <= 100) { //----   도로명 + 건물 번호 (~~~~로 nnn)
			
			String comp_addrRegex = ".*로.*";

	        Pattern pattern = Pattern.compile(comp_addrRegex);
	        if(pattern.matcher(AddrStr).matches()) {
	        	return true;
	        }else {
	        	System.out.println("!! 주소는 도로명 주소로 입력해주세요 !!");
	        	return false;
	        }

		} else {
			System.out.println("[경고] 회사주소는 100글자 이하이어야합니다.\n");
			return false;

	} // end of if~else (m.matches() )-------------------------------------

        
	}// end of public static boolean isTrueAddr(String AddrStr) -----------------------------------
	
	
	public static boolean isTrueTel(String TelStr) {					// 주소 검사하기
		if (TelStr == null || TelStr.isBlank()) {
			System.out.println("[경고] 이름은 공백이 아닌 값을 넣어주십시오.");
			return false;
		}
		String telRegex = "^[0-9]+-[0-9]+-[0-9]+";

        Pattern pattern = Pattern.compile(telRegex);
        if(pattern.matcher(TelStr).matches()) {
        	return true;
        }else {
        	System.out.println("!!올바른 전화번호 형식(xxx-xxxx-xxxx)을 입력하시요!!");
        	return false;
        }
        
	}// end of public static boolean isTrueTel(String TelStr) -----------------------------------
	
	
	
	public static boolean isTrueCompanyType(String CompanyTypeStr) {					// 주소 검사하기
		
		if (CompanyTypeStr == null || CompanyTypeStr.isBlank()) {
			System.out.println("[경고] 기업규모는 공백이 아닌 값을 넣어주십시오.");
			return false;
		}

		else {

			if (CompanyTypeStr.length() <= 10) { // matches 는 boolean 타입이다. 일치하면 true, 일치하지 않으면 false
				
				String comp_scaleRegex = "^[가-힣]+기업.*";
				
		        Pattern pattern = Pattern.compile(comp_scaleRegex);
		        if(pattern.matcher(CompanyTypeStr).matches()) {
		        	return true;
		        }else {
		        	System.out.println("!!올바른 기업규모 형식( ex - (중소 or 대)기업 (...)  ) 을 입력하시요!!");
		        	return false;
		        }

			} else {
				System.out.println("[경고] 기업규모는 10글자 이하이어야합니다.\n");
				return false;
			} // end of if~else (m.matches() )-------------------------------------

		} // end of if~else------------------------------------------------------
		
	}// end of public static boolean isTrueTel(String TelStr) -----------------------------------
	
	
	
	   
}
