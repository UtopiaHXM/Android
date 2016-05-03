package com.Tools;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class MyRenderer implements Renderer {
	
	private static int choosen;
    private static String type = "组仿真";
	public static Boolean hasChanged = false; 
	
	public Boolean getHasChanged() {
		return hasChanged;
	}
	public void setHasChanged(Boolean hasChanged) {
		this.hasChanged = hasChanged;
	}
	public static String getType() {
		return type;
	}
	public static void setType(String t) {
		type = t;
	}
	
    public static Map<Integer,List<int[]>> map = new HashMap<Integer, List<int[]>>();
    
	public static Map<Integer, List<int[]>> getMap() {
		return map;
	}
	public static void setMap(Map<Integer, List<int[]>> m) {
		map = m;
	}

	private List<int[]> list = new ArrayList<int[]>();
	public List<int[]> getList() {
		return list;
	}
	public void setList(List<int[]> list) {
		this.list = list;
	}
	public static int getChoosen() {
		return choosen;
	}
	public static void setChoosen(int choosen) {
		MyRenderer.choosen = choosen;
	}
	public void setFiveColors(int[] colors){
		setFive_Colors(colors);
		setFiveColorsBuffer(intBufferUtil(getFive_Colors()));
		fiveColorsBuffer = getFiveColorsBuffer();
	}
	public void setFktColors(int[] colors){
		setTrangleColors_fkt(colors);
		setTrangleColorsBuffer_fkt(intBufferUtil(getTrangleColors_fkt()));
	}
	public void setGlmColors(int[] colors){
		setTrangleColors_glm(colors);
		setTrangleColorsBuffer_glm(intBufferUtil(getTrangleColors_glm()));
	}
	public void setHnoColors(int[] colors){
		setTrangleColors_hno(colors);
		setTrangleColorsBuffer_hno(intBufferUtil(getTrangleColors_hno()));
	}
	public void setPiqColors(int[] colors){
		setTrangleColors_piq(colors);
        setTrangleColorsBuffer_piq(intBufferUtil(getTrangleColors_piq()));
	}
	public void setSjrColors(int[] colors){
		setTrangleColors_sjr(colors);
        setTrangleColorsBuffer_sjr(intBufferUtil(getTrangleColors_sjr()));
	}
	
	// 定义群组五边体
	private float[] Five_Side = new float[] {
			// 前方5点
			1.0f,0,0,//a0
			0.31f,0.95f,0,//b1
			-0.81f,0.59f,0,  //c2
			-0.81f,-0.59f,0,  //d3
			0.31f,-0.95f,0,//e4
			
			1.0f,0,0.5f,//a5
			0.31f,0.95f,0.5f,//b6
			-0.81f,0.59f,0.5f,  //c7
			-0.81f,-0.59f,0.5f,  //d8
			0.31f,-0.95f,0.5f,//e9

	};
	public IntBuffer getFiveColorsBuffer() {
		return fiveColorsBuffer;
	}
	public void setFiveColorsBuffer(IntBuffer fiveColorsBuffer) {
		this.fiveColorsBuffer = fiveColorsBuffer;
	}
	public IntBuffer getTrangleColorsBuffer_glm() {
		return trangleColorsBuffer_glm;
	}
	public void setTrangleColorsBuffer_glm(IntBuffer trangleColorsBuffer_glm) {
		this.trangleColorsBuffer_glm = trangleColorsBuffer_glm;
	}
	public IntBuffer getTrangleColorsBuffer_hno() {
		return trangleColorsBuffer_hno;
	}
	public void setTrangleColorsBuffer_hno(IntBuffer trangleColorsBuffer_hno) {
		this.trangleColorsBuffer_hno = trangleColorsBuffer_hno;
	}
	public IntBuffer getTrangleColorsBuffer_piq() {
		return trangleColorsBuffer_piq;
	}
	public void setTrangleColorsBuffer_piq(IntBuffer trangleColorsBuffer_piq) {
		this.trangleColorsBuffer_piq = trangleColorsBuffer_piq;
	}
	public IntBuffer getTrangleColorsBuffer_sjr() {
		return trangleColorsBuffer_sjr;
	}
	public void setTrangleColorsBuffer_sjr(IntBuffer trangleColorsBuffer_sjr) {
		this.trangleColorsBuffer_sjr = trangleColorsBuffer_sjr;
	}
	public IntBuffer getTrangleColorsBuffer_fkt() {
		return trangleColorsBuffer_fkt;
	}
	public void setTrangleColorsBuffer_fkt(IntBuffer trangleColorsBuffer_fkt) {
		this.trangleColorsBuffer_fkt = trangleColorsBuffer_fkt;
	}
	public int[] getFive_Colors() {
		return Five_Colors;
	}
	public void setFive_Colors(int[] five_Colors) {
		Five_Colors = five_Colors;
	}
	public int[] getTrangleColors_glm() {
		return trangleColors_glm;
	}
	public void setTrangleColors_glm(int[] trangleColors_glm) {
		this.trangleColors_glm = trangleColors_glm;
	}
	public int[] getTrangleColors_hno() {
		return trangleColors_hno;
	}
	public void setTrangleColors_hno(int[] trangleColors_hno) {
		this.trangleColors_hno = trangleColors_hno;
	}
	public int[] getTrangleColors_piq() {
		return trangleColors_piq;
	}
	public void setTrangleColors_piq(int[] trangleColors_piq) {
		this.trangleColors_piq = trangleColors_piq;
	}
	public int[] getTrangleColors_sjr() {
		return trangleColors_sjr;
	}
	public void setTrangleColors_sjr(int[] trangleColors_sjr) {
		this.trangleColors_sjr = trangleColors_sjr;
	}
	public int[] getTrangleColors_fkt() {
		return trangleColors_fkt;
	}
	public void setTrangleColors_fkt(int[] trangleColors_fkt) {
		this.trangleColors_fkt = trangleColors_fkt;
	}
	// 五边体固定顶点颜色
	private int[] Five_Colors = new int[] {
           65535, 0, 0, 0, 
           65535, 65535, 0, 0, 
           65535,0, 65535, 0, 
           65535, 0, 0, 0, 
           65535, 65535, 0, 0,
           65535, 0, 65535,
		   0, 65535, 0, 0,
	       0, 65535, 65535, 0,
		   0, 65535, 0, 65535, 0, 
	       65535,0, 0, 0 
	};

	// 五边体全部面
	private byte[] Five_Facets = new byte[] {
			0, 1, 2,
			0, 2, 3, 
			0, 3, 4,
			5, 6,7,
			5, 7, 8,
			5, 8, 9, 
			0, 1, 5, 
			1, 5, 6, 
			1, 2, 6,
			2, 6, 7,
			2, 3, 7,
			3, 7, 8, 
			3, 4, 9, 
			3, 8, 9, 
			0, 4, 9,
			0, 5, 9
			};
	
	// 定义群组三角体  glm
	private float[] trangles_glm = new float[] {
			// 上方三点
			-0.81f, 2.49f, 0.5f,   // g0
			0.13f, 1.19f, 0.5f,    //m1
			-0.81f, 0.89f, 0.5f,    //l2
			// 下方三点
			-0.81f, 2.49f, 0,   // g3
			0.13f, 1.19f, 0,    //m4
			-0.81f, 0.89f, 0,   //l5
    };
	// 三角体固定顶点颜色  glm
	private int[] trangleColors_glm = new int[] { 
			65535, 0, 0, 0, 
			65535, 65535, 0, 0,
			65535, 0, 65535, 0, 
			65535, 0, 0, 0,
			65535, 65535, 0, 0,
			65535, 0,65535, 0
    };
	// 三角体全部面  glm
	private byte[] trangleFacets_glm = new byte[] { 
			0, 1, 2,
			0, 1, 3, 
			0, 3, 5,
			0, 2, 4, 
			0, 4, 5, 
			1, 2, 4, 
			1, 4, 3, 
			3, 4, 5 
	};
	
	// 定义群组三角体  hno
	private float[] trangles_hno = new float[] {
			// 上方三点
			2.12f, 1.54f, 0.5f,   //h0
			0.59f, 1.04f, 0.5f,   //n1
			1.18f, 0.24f, 0.5f,    //o2
			// 下方三点
			2.12f, 1.54f, 0,   //h3
			0.59f, 1.04f, 0,   //n4
			1.18f, 0.24f, 0    //o5
	};
	// 三角体固定顶点颜色  hno
	private int[] trangleColors_hno = new int[] { 
			65535, 0, 0, 0, 
			65535, 65535, 0, 0,
			65535, 0, 65535, 0,
			65535, 0, 0, 0,
			65535,65535, 0, 0,
			65535, 0,65535, 0 
	};
	// 三角体全部面   hno
	private byte[] trangleFacets_hno = new byte[] { 
			0, 1, 2, 
			0, 1, 3, 
			0, 3, 5,
			0, 2, 4,
			0, 4, 5, 
			1, 2, 4, 
			1, 4, 3, 
			3, 4, 5 
	};
	// 定义群组三角体  piq
	private float[] trangles_piq = new float[] {
			// 上方三点
			1.17f, -0.24f, 0.5f,
			2.12f, -1.53f, 0.5f,
			0.59f, -1.04f, 0.5f,
			// 下方三点
			1.17f, -0.24f, 0,
			2.12f, -1.53f, 0,
			0.59f, -1.04f, 0
	};
	// 三角体固定顶点颜色  piq
	private int[] trangleColors_piq = new int[] { 
			65535, 0, 0, 0, 
			65535, 65535, 0, 0,
			65535, 0, 65535, 0,
			65535, 0, 0, 0,
			65535, 65535, 0, 0,
			65535, 0,65535, 0 
	};
	// 三角体全部面  piq
	private byte[] trangleFacets_piq = new byte[] {
			0, 1, 2,
			0, 1, 3,
			0, 3, 5,
			0, 2, 4, 
			0, 4, 5,
			1, 2, 4,
			1, 4, 3, 
			3, 4, 5 
	};
	// 定义群组三角体  sjr
	private float[] trangles_sjr = new float[] {
			// 上方三点
			-0.81f, -0.89f, 0.5f,
			-0.81f, -2.49f, 0.5f,
			0.13f, -1.19f, 0.5f,
			// 下方三点
			-0.81f, -0.89f, 0,
			-0.81f, -2.49f, 0,
			0.13f, -1.19f, 0
	};
	// 三角体固定顶点颜色  sjr
	private int[] trangleColors_sjr = new int[] { 
			65535, 0, 0, 0,
			65535, 65535, 0, 0,
			65535, 0, 65535, 0,
			65535, 0, 0, 0, 
			65535,65535, 0, 0,
			65535, 0,65535, 0 
	};
	// 三角体全部面  sjr
	private byte[] trangleFacets_sjr = new byte[] { 
			0, 1, 2,
			0, 1, 3,
			0, 3, 5,
			0, 2, 4, 
			0, 4, 5,
			1, 2, 4, 
			1, 4, 3, 
			3, 4, 5
	};
	// 定义群组三角体  fkt
	private float[] trangles_fkt = new float[] {
			// 上方三点
			-2.62f, 0, 0.5f,
			-1.09f, 0.50f, 0.5f,
			-1.09f, -0.50f, 0.5f,
			// 下方三点
			-2.62f, 0, 0,
			-1.09f, 0.50f, 0,
			-1.09f, -0.50f, 0
	};
	// 三角体固定顶点颜色  fkt
	private int[] trangleColors_fkt = new int[] { 
			65535, 0, 0, 0,
			65535, 65535, 0, 0,
			65535, 0, 65535, 0,
			65535, 0, 0, 0, 
			65535,65535, 0, 0,
			65535, 0,65535, 0 
	};
	// 三角体全部面  fkt
	private byte[] trangleFacets_fkt = new byte[] { 
			0, 1, 2,
			0, 1, 3,
			0, 3, 5,
			0, 2, 4, 
			0, 4, 5,
			1, 2, 4, 
			1, 4, 3, 
			3, 4, 5
	};
	// 五面柱
	FloatBuffer fiveBuffer;
	IntBuffer fiveColorsBuffer;
	ByteBuffer fiveFacetsBuffer;
	// 三角体  glm
	FloatBuffer tranglesBuffer_glm;
	IntBuffer trangleColorsBuffer_glm;
	ByteBuffer trangleFacetsBuffer_glm;
	// 三角体  hno
	FloatBuffer tranglesBuffer_hno;
	IntBuffer trangleColorsBuffer_hno;
	ByteBuffer trangleFacetsBuffer_hno;
	// 三角体  piq
	FloatBuffer tranglesBuffer_piq;
	IntBuffer trangleColorsBuffer_piq;
	ByteBuffer trangleFacetsBuffer_piq;
	// 三角体  sjr
	FloatBuffer tranglesBuffer_sjr;
	IntBuffer trangleColorsBuffer_sjr;
	ByteBuffer trangleFacetsBuffer_sjr;
	// 三角体  fkt
	FloatBuffer tranglesBuffer_fkt;
	IntBuffer trangleColorsBuffer_fkt;
	ByteBuffer trangleFacetsBuffer_fkt;
	// 控制旋转的角度
	private float rotate;

	public MyRenderer() {
		
		// 五边形初始化
		Five_Side = TransferData.changeArray(Five_Side, 0.5f);
		fiveBuffer = floatBufferUtil(Five_Side);
		fiveColorsBuffer = intBufferUtil(Five_Colors);
		fiveFacetsBuffer = ByteBuffer.wrap(Five_Facets);
		// 三角柱体glm初始化
		trangles_glm = TransferData.changeArray(trangles_glm, 0.5f);
		tranglesBuffer_glm = floatBufferUtil(trangles_glm);
		trangleColorsBuffer_glm = intBufferUtil(trangleColors_glm);
		trangleFacetsBuffer_glm = ByteBuffer.wrap(trangleFacets_glm);
		// 三角柱体hno初始化
		trangles_hno = TransferData.changeArray(trangles_hno, 0.5f);
		tranglesBuffer_hno = floatBufferUtil(trangles_hno);
		trangleColorsBuffer_hno = intBufferUtil(trangleColors_hno);
		trangleFacetsBuffer_hno = ByteBuffer.wrap(trangleFacets_hno);
        //三角柱体piq初始化
		trangles_piq = TransferData.changeArray(trangles_piq, 0.5f);
		tranglesBuffer_piq = floatBufferUtil(trangles_piq);
		trangleColorsBuffer_piq = intBufferUtil(trangleColors_piq);
		trangleFacetsBuffer_piq = ByteBuffer.wrap(trangleFacets_piq);
		 //三角柱体sjr初始化
		trangles_sjr = TransferData.changeArray(trangles_sjr, 0.5f);
		tranglesBuffer_sjr = floatBufferUtil(trangles_sjr);
		trangleColorsBuffer_sjr = intBufferUtil(trangleColors_sjr);
		trangleFacetsBuffer_sjr = ByteBuffer.wrap(trangleFacets_sjr);
		//三角柱体fkt初始化
		trangles_fkt = TransferData.changeArray(trangles_fkt, 0.5f);
		tranglesBuffer_fkt = floatBufferUtil(trangles_fkt);
		trangleColorsBuffer_fkt = intBufferUtil(trangleColors_fkt);
		trangleFacetsBuffer_fkt = ByteBuffer.wrap(trangleFacets_fkt);
		
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// 关闭抗抖动
		gl.glDisable(GL10.GL_DITHER);
		// 设置系统对透视进行修正
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		gl.glClearColor(65525, 65525, 65525, 0);
		// 设置阴影平滑模式
		gl.glShadeModel(GL10.GL_SMOOTH);
		// 启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// 设置深度测试的类型
		gl.glDepthFunc(GL10.GL_LEQUAL);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// 设置3D视窗的大小及位置
		gl.glViewport(0, 0, width, height);
		// 将当前矩阵模式设为投影矩阵
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// 初始化单位矩阵
		gl.glLoadIdentity();
		// 计算透视视窗的宽度、高度比
		float ratio = (float) width / height;
		// 调用此方法设置透视视窗的空间大小。
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
	}

	// 绘制图形的方法
	@Override
	public void onDrawFrame(GL10 gl) {
		// 清除屏幕缓存和深度缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 启用顶点座标数据
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// 启用顶点颜色数据
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// 设置当前矩阵模式为模型视图。
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		
		// --------------------绘制五边形-----------------------
		// 重置当前的模型视图矩阵
		gl.glLoadIdentity();
System.out.println("重置");
		gl.glTranslatef(0f, 0f, -2.5f);
		//gl.glTranslatef(0.0f, 0.0f, -2.2f);
		// 沿着Y轴旋转
		//gl.glRotatef(rotate, 0f, 0f, -5f);

		gl.glRotatef(173, 0, -1.5f, -5f);
System.out.println(rotate);
		// 设置顶点的位置数据
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fiveBuffer);
		// 设置顶点的颜色数据trangleColors
		gl.glColorPointer(4, GL10.GL_FIXED, 0, fiveColorsBuffer);

		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, fiveFacetsBuffer.remaining(),
				GL10.GL_UNSIGNED_BYTE, fiveFacetsBuffer);
		// --------------------绘制glm-----------------------
		// 设置顶点的位置数据
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, tranglesBuffer_glm);
		// 设置顶点的颜色数据trangleColors
		gl.glColorPointer(4, GL10.GL_FIXED, 0, trangleColorsBuffer_glm);
		// 按cubeFacetsBuffer指定的面绘制五边柱体
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, trangleFacetsBuffer_glm.remaining(),
				GL10.GL_UNSIGNED_BYTE, trangleFacetsBuffer_glm);
		//--------------------绘制hno-----------------------
		// 设置顶点的位置数据
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, tranglesBuffer_hno);
		// 设置顶点的颜色数据trangleColors
		gl.glColorPointer(4, GL10.GL_FIXED, 0, trangleColorsBuffer_hno);
		// 按cubeFacetsBuffer指定的面绘制五边柱体
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, trangleFacetsBuffer_hno.remaining(),
		GL10.GL_UNSIGNED_BYTE, trangleFacetsBuffer_hno);
		//--------------------绘制piq-----------------------
				// 设置顶点的位置数据
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, tranglesBuffer_piq);
				// 设置顶点的颜色数据trangleColors
				gl.glColorPointer(4, GL10.GL_FIXED, 0, trangleColorsBuffer_piq);
				// 按cubeFacetsBuffer指定的面绘制五边柱体
				gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, trangleFacetsBuffer_piq.remaining(),
						GL10.GL_UNSIGNED_BYTE, trangleFacetsBuffer_piq);
				//--------------------绘制sjr-----------------------
				// 设置顶点的位置数据
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, tranglesBuffer_sjr);
				// 设置顶点的颜色数据trangleColors
				gl.glColorPointer(4, GL10.GL_FIXED, 0, trangleColorsBuffer_sjr);
				// 按cubeFacetsBuffer指定的面绘制五边柱体
				gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, trangleFacetsBuffer_sjr.remaining(),
						GL10.GL_UNSIGNED_BYTE, trangleFacetsBuffer_sjr);
				//--------------------绘制fkt-----------------------
				// 设置顶点的位置数据
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, tranglesBuffer_fkt);
				// 设置顶点的颜色数据trangleColors
				gl.glColorPointer(4, GL10.GL_FIXED, 0, trangleColorsBuffer_fkt);
				// 按cubeFacetsBuffer指定的面绘制五边柱体
				gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, trangleFacetsBuffer_fkt.remaining(),
						GL10.GL_UNSIGNED_BYTE, trangleFacetsBuffer_fkt);
		// 绘制结束
		gl.glFinish();
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// 旋转角度增加1
		Log.d("dnagqian", "wubianxing"+getFive_Colors()[0]+" "+getFive_Colors()[1]);
		rotate += 1;
	}

	// 定义一个工具方法，将int[]数组转换为OpenGL ES所需的IntBuffer
	private IntBuffer intBufferUtil(int[] arr) {
		IntBuffer mBuffer;
		// 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
		ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
		// 数组排列用nativeOrder
		qbb.order(ByteOrder.nativeOrder());
		mBuffer = qbb.asIntBuffer();
		mBuffer.put(arr);
		mBuffer.position(0);
		return mBuffer;
	}

	// 定义一个工具方法，将float[]数组转换为OpenGL ES所需的FloatBuffer
	private FloatBuffer floatBufferUtil(float[] arr) {
		FloatBuffer mBuffer;
		// 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
		ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
		// 数组排列用nativeOrder
		qbb.order(ByteOrder.nativeOrder());
		mBuffer = qbb.asFloatBuffer();
		mBuffer.put(arr);
		mBuffer.position(0);
		return mBuffer;
	}	
	
	public void changeColor(int i,int[] colors){
		switch (i) {
		case 0:
			Log.d("changeColor", "colors="+colors[0]);
			setFive_Colors(colors);
			setFiveColorsBuffer(intBufferUtil(getFive_Colors()));
			fiveColorsBuffer = getFiveColorsBuffer();
			break;
		case 1:
			setTrangleColors_fkt(colors);
			setTrangleColorsBuffer_fkt(intBufferUtil(getTrangleColors_fkt()));
			break;
		case 2:
			setTrangleColors_glm(colors);
			setTrangleColorsBuffer_glm(intBufferUtil(getTrangleColors_glm()));
			break;
		case 3:
			setTrangleColors_hno(colors);
			setTrangleColorsBuffer_hno(intBufferUtil(getTrangleColors_hno()));
			break;
		case 4:
			setTrangleColors_piq(colors);
			setTrangleColorsBuffer_piq(intBufferUtil(getTrangleColors_piq()));
			break;
		case 5:
			setTrangleColors_sjr(colors);
            setTrangleColorsBuffer_sjr(intBufferUtil(getTrangleColors_sjr()));
			break;
		default:
			break;
		}
	}
	}
	



