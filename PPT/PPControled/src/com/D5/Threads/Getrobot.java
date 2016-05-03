package com.D5.Threads;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Getrobot {

	public static Boolean pressKey(Robot robot, int keyvalue) {
		robot.keyPress(keyvalue); // 按下按键
		robot.keyRelease(keyvalue); // 释放按键
		return true;
	}

	// 鼠标左移
	public static Boolean left(Robot robot) {
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_LEFT);
		return true;
	}

	// 鼠标右移
	public static Boolean right(Robot robot) {
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.keyRelease(KeyEvent.VK_RIGHT);
		return true;
	}

	// 鼠标上移
	public static Boolean up(Robot robot) {
		robot.keyPress(KeyEvent.VK_UP);
		robot.keyRelease(KeyEvent.VK_UP);
		return true;
	}

	// 鼠标下移
	public static Boolean down(Robot robot) {
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		return true;
	}

	// 播放缓灯片
	public static Boolean Maximize(Robot robot) {
		robot.keyPress(KeyEvent.VK_F5);
		robot.keyRelease(KeyEvent.VK_F5);
		return true;
	}

	// 退出播放
	public static Boolean unMaximize(Robot robot) {
		robot.keyPress(KeyEvent.VK_ESCAPE);
		robot.keyRelease(KeyEvent.VK_ESCAPE);
		return true;
	}

	// 关闭PPt
	public static Boolean close(Robot robot) {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_W);
		robot.keyRelease(KeyEvent.VK_W);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		return true;
	}

	// 关闭PPt
	public static Boolean JumpToPage(Robot robot, int[] pages) {
		if (!pages.equals(null)) {
			for (int i = 0; i < pages.length; i++) {
				if (pages[i] == 0) {
					robot.keyPress(KeyEvent.VK_0);
					robot.keyRelease(KeyEvent.VK_0);
					System.out.println("Press-VK_" + pages[i]);
					System.out.println("Release-VK_" + pages[i]);
				}
				if (pages[i] == 1) {
					robot.keyPress(KeyEvent.VK_1);
					robot.keyRelease(KeyEvent.VK_1);
					System.out.println("Press-VK_" + pages[i]);
					System.out.println("Release-VK_" + pages[i]);
				}

				if (pages[i] == 2) {
					robot.keyPress(KeyEvent.VK_2);
					robot.keyRelease(KeyEvent.VK_2);
					System.out.println("Press-VK_" + pages[i]);
					System.out.println("Release-VK_" + pages[i]);
				}
				if (pages[i] == 3) {
					robot.keyPress(KeyEvent.VK_3);
					robot.keyRelease(KeyEvent.VK_3);
					System.out.println("Press-VK_" + pages[i]);
					System.out.println("Release-VK_" + pages[i]);
				}
				if (pages[i] == 4) {
					robot.keyPress(KeyEvent.VK_4);
					robot.keyRelease(KeyEvent.VK_4);
					System.out.println("Press-VK_" + pages[i]);
					System.out.println("Release-VK_" + pages[i]);
				}
				if (pages[i] == 5) {
					robot.keyPress(KeyEvent.VK_5);
					robot.keyRelease(KeyEvent.VK_5);
					System.out.println("Press-VK_" + pages[i]);
					System.out.println("Release-VK_" + pages[i]);
				}
				if (pages[i] == 6) {
					robot.keyPress(KeyEvent.VK_6);
					robot.keyRelease(KeyEvent.VK_6);
					System.out.println("Press-VK_" + pages[i]);
					System.out.println("Release-VK_" + pages[i]);
				}
				if (pages[i] == 7) {
					robot.keyPress(KeyEvent.VK_7);
					robot.keyRelease(KeyEvent.VK_7);
					System.out.println("Press-VK_" + pages[i]);
					System.out.println("Release-VK_" + pages[i]);
				}
				if (pages[i] == 8) {
					robot.keyPress(KeyEvent.VK_8);
					robot.keyRelease(KeyEvent.VK_8);
					System.out.println("Press-VK_" + pages[i]);
					System.out.println("Release-VK_" + pages[i]);
				}
				if (pages[i] == 9) {
					robot.keyPress(KeyEvent.VK_9);
					robot.keyRelease(KeyEvent.VK_9);
					System.out.println("Press-VK_" + pages[i]);
					System.out.println("Release-VK_" + pages[i]);
				}

				System.out.println(" ");
			}

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

		}

		return true;
	}

	// 鼠标左右翻页
	public Boolean robotmake(int number, int[] pages) {
		Boolean isRun = false;
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		switch (number) {
		case 1:
			isRun = up(robot);
			break;
		case 2:
			isRun = down(robot);
			break;
		case 3:
			isRun = left(robot);
			break;
		case 4:
			isRun = right(robot);
			break;
		case 5:
			isRun = Maximize(robot);
			break;
		case 6:
			isRun = unMaximize(robot);
			break;
		case 7:
			isRun = close(robot);
			break;
		case 8:
			isRun = JumpToPage(robot, pages);
			break;
		default:
			break;
		}
		return isRun;
	}
}
