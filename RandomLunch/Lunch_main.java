package RandomLunch;

import java.util.ArrayList;
import java.util.Scanner;

public class Lunch_main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		LunchBoxDAO dao = new LunchBoxDAO();

		do {
			
			int menu = menuselect(sc);
			switch (menu) {
			//랜덤메뉴
			case 1:
				RandomLunchBox(dao);
				break;
			//식당추가
			case 2:
				insert(sc, dao);
				break;
			//식당수정
			case 3:
				updatemenu(sc, dao);
				break;
			//식당삭제
			case 4:
				deletemenu(sc, dao);
				break;
				
			// 답변달기 식당추천
			case 5:
				sicdangrecommend(sc, dao);
				break;
			// 전체 보기
			case 6:
				selectAll(dao);
				break;
			//메뉴만 전체보기
			case 7:
				showmenu(dao);
				break;
			//종료
			case 8:
				sc.close();
				return;
			}
		} while (true);

	}

	//랜덤메뉴
	private static void RandomLunchBox(LunchBoxDAO dao) {
		System.out.println("오늘의 메뉴를 추천해 드리겠습니다.");
		// random으로 뽑을 숫자
		ArrayList<Integer> num = dao.menunumber();
		int mnum = (int) (Math.random() * num.size()) + 1;


		int randpm = num.get(mnum - 1);// 랜덤번호꺼내기

		// 랜덤메뉴 출력

		ArrayList<RLunchBox> random = dao.random(randpm);
		System.out.println("종류\t메뉴\t추천식당");

		for (RLunchBox l : random) {
			if (l == null)
				System.out.println("");
			else {
				System.out.printf("%s\t%s\t%s\n", l.getLunch_kind(), l.getLunch_menu(), l.getLunch_sicdang());

			}
		}

	}

	//메뉴및 식당 삭제
	private static void deletemenu(Scanner sc, LunchBoxDAO dao) {
		System.out.println("삭제할 메뉴및 식당의 번호를 입력하세요 >");
		int num = inputNumber(sc, 0, 1000);
		RLunchBox rl = dao.select(num);
		if (rl != null) {
			System.out.println("확인을 위해 삭제할 메뉴및 식당의 번호를 한번 더 입력하세요>");
			int num2 = inputNumber(sc, 0, 1000);

			if (num == num2) {
				int result = dao.lunchDelete(rl);
				if (result == 1) {
					System.out.println("삭제하였습니다.");
				} else {
					System.out.println("없는 메뉴의 번호입니다.");
				}

			} else {
				System.out.println("삭제할 메뉴의 번호가 다릅니다.");
			}
		} else {
			System.out.println("해당 글이 존재하지 않습니다.");
		}
	}

	// 메뉴아래 댓글 맛집추천
	private static void sicdangrecommend(Scanner sc, LunchBoxDAO dao) {
		System.out.println("추천할 음식 번호를 선택하세요 >");
		int num = inputNumber(sc, 0, 1000);
		RLunchBox rl = dao.select(num);
		if (rl != null) {
			System.out.println("식당이름>");
			rl.setLunch_sicdang(sc.nextLine());

		} else {
			System.out.println("없는 메뉴 번호입니다.");
		}

		int result = dao.sicdangrecommend(rl);
		System.out.println(result);
		if (result != 0)
			System.out.println("식당이 저장되었습니다.");
		else {
			System.out.println("식당 저장 실패");
		}
	}

	//메뉴 및 식당 업데이트
	private static void updatemenu(Scanner sc, LunchBoxDAO dao) {

		System.out.println("수정할 번호를 입력하세요>");
		int no = inputNumber(sc, 0, 1000);

		if (isExist(no, dao)) {// 메뉴번호 유무
								// 식당추가는 무한으로 갈예정 심플하게가려고..
			System.out.println("메뉴>");
			String menu = sc.nextLine();

			System.out.println("메뉴 종류(한식:1, 중식:2, 일식:3, 기타:4)>");
			String menukind = menukind();

			RLunchBox rl = new RLunchBox(menu, menukind);
			rl.setLunch_num(no);
			int result = dao.update(rl);
			if (result == 1)
				System.out.println("수정이 되었습니다.");
			else {
				System.out.println("수정중 오류 발생했습니다.");
			}

		} else {
			System.out.println("메뉴에 없는 번호입니다.");
		}
	}

	//메뉴 및 식당 확인
	private static boolean isExist(int no, LunchBoxDAO dao) {
		RLunchBox result = dao.select(no);
		// System.out.println(result);
		if (result == null) {
			return false;
		} else

			return true;
	}

	//메뉴 및 식당 보기
	private static void showmenu(LunchBoxDAO dao) {
		ArrayList<RLunchBox> rlbox = dao.shwomenu();
		if (rlbox != null) {

			System.out.println("종류\t메뉴");
			for (RLunchBox l : rlbox) {
				if (l == null)
					System.out.println("");
				else {
					System.out.printf("%s\t%s\n", l.getLunch_kind(), l.getLunch_menu());

				}
			}

		} else {
			dao.selectAll();
			System.out.println("테이블에 데이터가 없습니다.");
		}

	}

	//메뉴 및 식당 보기//전체
	private static void selectAll(LunchBoxDAO dao) {
		ArrayList<RLunchBox> rlbox = dao.selectAll();
		if (rlbox != null) {
			printTile();

			for (RLunchBox l : rlbox) {

				if (l == null)
					System.out.println("");
				else {
					System.out.println(l.toString());
				}
			}

		} else {
			dao.selectAll();
			System.out.println("테이블에 데이터가 없습니다.");
		}

	}

	private static void printTile() {
		System.out.println("번호\t메뉴\t종류\t추천식당");

	}

	//메뉴 및 식당추가
	private static void insert(Scanner sc, LunchBoxDAO dao) {

		RLunchBox rlbox = new RLunchBox();

		System.out.println("메뉴>");

		rlbox.setLunch_menu(sc.nextLine());

		System.out.println("메뉴 종류(한식:1, 중식:2, 일식:3, 기타:4)>");
		rlbox.setLunch_kind(menukind());

		int result = dao.rlboxInsert(rlbox);

		if (result == 1)
			System.out.println("글이 작성되었습니다.");
		else if (result == 19909) {
			System.out.println("이미있는 메뉴입니다 ");
		} else {
			System.out.println("오류가 발생되었습니다.");
		}

	}

	//메뉴 종류고르기
	private static String menukind() {
		Scanner sc = new Scanner(System.in);
		int input = inputNumber(sc, 1, 4);

		if (input == 1) {
			return "한";
		} else if (input == 2) {
			return "중";

		} else if (input == 3) {
			return "일";

		} else {
			return "기타";

		}

	}

	//번호선택
	private static int menuselect(Scanner sc) {
		String menus[] = { "오늘의메뉴", "메뉴추가", "메뉴수정", "메뉴및 식당삭제", "식당을 추천해주세요", "식당및 메뉴 전체조회", "메뉴보기", "종료" };

		int i = 0;
		System.out.println("============================");
		for (String m : menus) {
			System.out.println(++i + "." + m);

		}
		System.out.println("==========================");
		System.out.println("RandomLunchBox\n(메뉴를 선택하세요) >");

		return inputNumber(sc, 1, menus.length);
	}

	//번호선택 유효성검사
	private static int inputNumber(Scanner sc, int start, int end) {
		int input = 0;
		while (true) {
			try {
				input = Integer.parseInt(sc.nextLine());
				if (input >= start && input <= end)
					break;
				{
					System.out.println(start + "-" + end + "사이의 숫자를 입력하세요");
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력하세요");

			}
		} // 메인만 만들면 가능해요

		return input;
	}
}
