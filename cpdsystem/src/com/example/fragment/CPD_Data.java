package com.example.fragment;

import java.security.PublicKey;
import java.util.List;

import android.util.Log;

public class CPD_Data {

	float r_X, r_Y, r_R;
	int[] r_C;
	String name, n_url, g_url;
	int id, use, index, index_s, index_Max, index_sMax, indexs_dc;
	List<int[]> c_Data;
	List<byte[]> f_Data;

	CPD_Data(float CoordinateX, float CoordinateY, float Radius, int Available,
			String Name, String Node_URL, String Gateway_URL, int ID)// x坐标、y坐标、半径、可用、颜色、名字、ID、地址
	{
		r_X = CoordinateX;
		r_Y = CoordinateY;
		r_R = Radius;
		use = Available;// 0 控件在布局中 , 1 控件在OnTouch_move中 ,2控件 在SurfaceView中固定
		name = Name;
		n_url = Node_URL;
		g_url = Gateway_URL;
		id = ID;
		r_C = new int[3];
		c_Data = null;
		f_Data = null;
		index = 0;
		index_s = 0;
		indexs_dc = 0; // 单次闪烁
		index_Max = 0;
		index_sMax = 0;
	}

	public List<int[]> set_ColorG(List<int[]> col_Grp) {
		c_Data = col_Grp;
		index_Max = col_Grp.size();
		for (int i = 0; i < index_Max; i++) {
			if (c_Data.get(i)[0] == 2) {
				index_sMax = index_sMax + c_Data.get(i)[7] * c_Data.get(i)[8];
			} else {
				index_sMax = index_sMax + c_Data.get(i)[7];
			}
		}
		return c_Data;
	}

	public List<byte[]> set_FileG(List<byte[]> file_Grp)
	{
		f_Data = file_Grp;
		return f_Data;
	}
	public void init() {
		index = 0;
		index_s = 0;
		indexs_dc = 0;
	}

	public List<int[]> get_ColorG() {
		return c_Data;
	}
	public List<byte[]> get_FileG()
	{
		return f_Data;
	}

	public int[] setColor() {
		if (index < index_Max) {
			if (c_Data != null) {
				if (c_Data.get(index)[0] == 1) {
					Log.d("index_s", "index_s==" + index_s);
					r_C[0] = c_Data.get(index)[1];
					r_C[1] = c_Data.get(index)[2];
					r_C[2] = c_Data.get(index)[3];
					index_s = index_s + 1;
					if (index_s == c_Data.get(index)[7]) {
						index_s = 0;
						index = index + 1;
					}
				} else if (c_Data.get(index)[0] == 2) {
					Log.d("index_s", "index_s==" + index_s);
					if (index_s % 2 == 0) {
						r_C[0] = c_Data.get(index)[1];
						r_C[1] = c_Data.get(index)[2];
						r_C[2] = c_Data.get(index)[3];
						Log.d("aaa", "aaaa!!!!!" + index_s);
					} else if (index_s % 2 == 1) {
						r_C[0] = c_Data.get(index)[4];
						r_C[1] = c_Data.get(index)[5];
						r_C[2] = c_Data.get(index)[6];
						Log.d("bbbb", "bbbbb!!!!!" + index_s);
					}
					indexs_dc = indexs_dc + 1;
					if (indexs_dc == c_Data.get(index)[7]) {
						index_s = index_s + 1;
						indexs_dc = 0;
					}
					if (index_s == c_Data.get(index)[8]) {
						index_s = 0;
						index = index + 1;
					}
				} else if (c_Data.get(index)[0] == 3) {
					Log.d("index_s", "index_s==" + index_s + ",r_C[0]="
							+ r_C[0] + ",r_C[1]=" + r_C[1] + ",r_C[2]="
							+ r_C[2]);
					r_C[0] = c_Data.get(index)[1]
							+ (int) (((c_Data.get(index)[4] - c_Data.get(index)[1]) * index_s) / c_Data
									.get(index)[7]);
					r_C[1] = c_Data.get(index)[2]
							+ (int) (((c_Data.get(index)[5] - c_Data.get(index)[2]) * index_s) / c_Data
									.get(index)[7]);
					r_C[2] = c_Data.get(index)[3]
							+ (int) (((c_Data.get(index)[6] - c_Data.get(index)[3]) * index_s) / c_Data
									.get(index)[7]);
					index_s = index_s + 1;
					if (index_s == c_Data.get(index)[7]) {
						index_s = 0;
						index = index + 1;
					}
				}
			}
		}
		return r_C;
	}
}
