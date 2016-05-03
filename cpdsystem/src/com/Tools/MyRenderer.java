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
    private static String type = "�����";
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
	
	// ����Ⱥ�������
	private float[] Five_Side = new float[] {
			// ǰ��5��
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
	// �����̶�������ɫ
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

	// �����ȫ����
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
	
	// ����Ⱥ��������  glm
	private float[] trangles_glm = new float[] {
			// �Ϸ�����
			-0.81f, 2.49f, 0.5f,   // g0
			0.13f, 1.19f, 0.5f,    //m1
			-0.81f, 0.89f, 0.5f,    //l2
			// �·�����
			-0.81f, 2.49f, 0,   // g3
			0.13f, 1.19f, 0,    //m4
			-0.81f, 0.89f, 0,   //l5
    };
	// ������̶�������ɫ  glm
	private int[] trangleColors_glm = new int[] { 
			65535, 0, 0, 0, 
			65535, 65535, 0, 0,
			65535, 0, 65535, 0, 
			65535, 0, 0, 0,
			65535, 65535, 0, 0,
			65535, 0,65535, 0
    };
	// ������ȫ����  glm
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
	
	// ����Ⱥ��������  hno
	private float[] trangles_hno = new float[] {
			// �Ϸ�����
			2.12f, 1.54f, 0.5f,   //h0
			0.59f, 1.04f, 0.5f,   //n1
			1.18f, 0.24f, 0.5f,    //o2
			// �·�����
			2.12f, 1.54f, 0,   //h3
			0.59f, 1.04f, 0,   //n4
			1.18f, 0.24f, 0    //o5
	};
	// ������̶�������ɫ  hno
	private int[] trangleColors_hno = new int[] { 
			65535, 0, 0, 0, 
			65535, 65535, 0, 0,
			65535, 0, 65535, 0,
			65535, 0, 0, 0,
			65535,65535, 0, 0,
			65535, 0,65535, 0 
	};
	// ������ȫ����   hno
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
	// ����Ⱥ��������  piq
	private float[] trangles_piq = new float[] {
			// �Ϸ�����
			1.17f, -0.24f, 0.5f,
			2.12f, -1.53f, 0.5f,
			0.59f, -1.04f, 0.5f,
			// �·�����
			1.17f, -0.24f, 0,
			2.12f, -1.53f, 0,
			0.59f, -1.04f, 0
	};
	// ������̶�������ɫ  piq
	private int[] trangleColors_piq = new int[] { 
			65535, 0, 0, 0, 
			65535, 65535, 0, 0,
			65535, 0, 65535, 0,
			65535, 0, 0, 0,
			65535, 65535, 0, 0,
			65535, 0,65535, 0 
	};
	// ������ȫ����  piq
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
	// ����Ⱥ��������  sjr
	private float[] trangles_sjr = new float[] {
			// �Ϸ�����
			-0.81f, -0.89f, 0.5f,
			-0.81f, -2.49f, 0.5f,
			0.13f, -1.19f, 0.5f,
			// �·�����
			-0.81f, -0.89f, 0,
			-0.81f, -2.49f, 0,
			0.13f, -1.19f, 0
	};
	// ������̶�������ɫ  sjr
	private int[] trangleColors_sjr = new int[] { 
			65535, 0, 0, 0,
			65535, 65535, 0, 0,
			65535, 0, 65535, 0,
			65535, 0, 0, 0, 
			65535,65535, 0, 0,
			65535, 0,65535, 0 
	};
	// ������ȫ����  sjr
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
	// ����Ⱥ��������  fkt
	private float[] trangles_fkt = new float[] {
			// �Ϸ�����
			-2.62f, 0, 0.5f,
			-1.09f, 0.50f, 0.5f,
			-1.09f, -0.50f, 0.5f,
			// �·�����
			-2.62f, 0, 0,
			-1.09f, 0.50f, 0,
			-1.09f, -0.50f, 0
	};
	// ������̶�������ɫ  fkt
	private int[] trangleColors_fkt = new int[] { 
			65535, 0, 0, 0,
			65535, 65535, 0, 0,
			65535, 0, 65535, 0,
			65535, 0, 0, 0, 
			65535,65535, 0, 0,
			65535, 0,65535, 0 
	};
	// ������ȫ����  fkt
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
	// ������
	FloatBuffer fiveBuffer;
	IntBuffer fiveColorsBuffer;
	ByteBuffer fiveFacetsBuffer;
	// ������  glm
	FloatBuffer tranglesBuffer_glm;
	IntBuffer trangleColorsBuffer_glm;
	ByteBuffer trangleFacetsBuffer_glm;
	// ������  hno
	FloatBuffer tranglesBuffer_hno;
	IntBuffer trangleColorsBuffer_hno;
	ByteBuffer trangleFacetsBuffer_hno;
	// ������  piq
	FloatBuffer tranglesBuffer_piq;
	IntBuffer trangleColorsBuffer_piq;
	ByteBuffer trangleFacetsBuffer_piq;
	// ������  sjr
	FloatBuffer tranglesBuffer_sjr;
	IntBuffer trangleColorsBuffer_sjr;
	ByteBuffer trangleFacetsBuffer_sjr;
	// ������  fkt
	FloatBuffer tranglesBuffer_fkt;
	IntBuffer trangleColorsBuffer_fkt;
	ByteBuffer trangleFacetsBuffer_fkt;
	// ������ת�ĽǶ�
	private float rotate;

	public MyRenderer() {
		
		// ����γ�ʼ��
		Five_Side = TransferData.changeArray(Five_Side, 0.5f);
		fiveBuffer = floatBufferUtil(Five_Side);
		fiveColorsBuffer = intBufferUtil(Five_Colors);
		fiveFacetsBuffer = ByteBuffer.wrap(Five_Facets);
		// ��������glm��ʼ��
		trangles_glm = TransferData.changeArray(trangles_glm, 0.5f);
		tranglesBuffer_glm = floatBufferUtil(trangles_glm);
		trangleColorsBuffer_glm = intBufferUtil(trangleColors_glm);
		trangleFacetsBuffer_glm = ByteBuffer.wrap(trangleFacets_glm);
		// ��������hno��ʼ��
		trangles_hno = TransferData.changeArray(trangles_hno, 0.5f);
		tranglesBuffer_hno = floatBufferUtil(trangles_hno);
		trangleColorsBuffer_hno = intBufferUtil(trangleColors_hno);
		trangleFacetsBuffer_hno = ByteBuffer.wrap(trangleFacets_hno);
        //��������piq��ʼ��
		trangles_piq = TransferData.changeArray(trangles_piq, 0.5f);
		tranglesBuffer_piq = floatBufferUtil(trangles_piq);
		trangleColorsBuffer_piq = intBufferUtil(trangleColors_piq);
		trangleFacetsBuffer_piq = ByteBuffer.wrap(trangleFacets_piq);
		 //��������sjr��ʼ��
		trangles_sjr = TransferData.changeArray(trangles_sjr, 0.5f);
		tranglesBuffer_sjr = floatBufferUtil(trangles_sjr);
		trangleColorsBuffer_sjr = intBufferUtil(trangleColors_sjr);
		trangleFacetsBuffer_sjr = ByteBuffer.wrap(trangleFacets_sjr);
		//��������fkt��ʼ��
		trangles_fkt = TransferData.changeArray(trangles_fkt, 0.5f);
		tranglesBuffer_fkt = floatBufferUtil(trangles_fkt);
		trangleColorsBuffer_fkt = intBufferUtil(trangleColors_fkt);
		trangleFacetsBuffer_fkt = ByteBuffer.wrap(trangleFacets_fkt);
		
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// �رտ�����
		gl.glDisable(GL10.GL_DITHER);
		// ����ϵͳ��͸�ӽ�������
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		gl.glClearColor(65525, 65525, 65525, 0);
		// ������Ӱƽ��ģʽ
		gl.glShadeModel(GL10.GL_SMOOTH);
		// ������Ȳ���
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// ������Ȳ��Ե�����
		gl.glDepthFunc(GL10.GL_LEQUAL);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// ����3D�Ӵ��Ĵ�С��λ��
		gl.glViewport(0, 0, width, height);
		// ����ǰ����ģʽ��ΪͶӰ����
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// ��ʼ����λ����
		gl.glLoadIdentity();
		// ����͸���Ӵ��Ŀ�ȡ��߶ȱ�
		float ratio = (float) width / height;
		// ���ô˷�������͸���Ӵ��Ŀռ��С��
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
	}

	// ����ͼ�εķ���
	@Override
	public void onDrawFrame(GL10 gl) {
		// �����Ļ�������Ȼ���
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// ���ö�����������
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// ���ö�����ɫ����
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// ���õ�ǰ����ģʽΪģ����ͼ��
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		
		// --------------------���������-----------------------
		// ���õ�ǰ��ģ����ͼ����
		gl.glLoadIdentity();
System.out.println("����");
		gl.glTranslatef(0f, 0f, -2.5f);
		//gl.glTranslatef(0.0f, 0.0f, -2.2f);
		// ����Y����ת
		//gl.glRotatef(rotate, 0f, 0f, -5f);

		gl.glRotatef(173, 0, -1.5f, -5f);
System.out.println(rotate);
		// ���ö����λ������
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fiveBuffer);
		// ���ö������ɫ����trangleColors
		gl.glColorPointer(4, GL10.GL_FIXED, 0, fiveColorsBuffer);

		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, fiveFacetsBuffer.remaining(),
				GL10.GL_UNSIGNED_BYTE, fiveFacetsBuffer);
		// --------------------����glm-----------------------
		// ���ö����λ������
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, tranglesBuffer_glm);
		// ���ö������ɫ����trangleColors
		gl.glColorPointer(4, GL10.GL_FIXED, 0, trangleColorsBuffer_glm);
		// ��cubeFacetsBufferָ����������������
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, trangleFacetsBuffer_glm.remaining(),
				GL10.GL_UNSIGNED_BYTE, trangleFacetsBuffer_glm);
		//--------------------����hno-----------------------
		// ���ö����λ������
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, tranglesBuffer_hno);
		// ���ö������ɫ����trangleColors
		gl.glColorPointer(4, GL10.GL_FIXED, 0, trangleColorsBuffer_hno);
		// ��cubeFacetsBufferָ����������������
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, trangleFacetsBuffer_hno.remaining(),
		GL10.GL_UNSIGNED_BYTE, trangleFacetsBuffer_hno);
		//--------------------����piq-----------------------
				// ���ö����λ������
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, tranglesBuffer_piq);
				// ���ö������ɫ����trangleColors
				gl.glColorPointer(4, GL10.GL_FIXED, 0, trangleColorsBuffer_piq);
				// ��cubeFacetsBufferָ����������������
				gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, trangleFacetsBuffer_piq.remaining(),
						GL10.GL_UNSIGNED_BYTE, trangleFacetsBuffer_piq);
				//--------------------����sjr-----------------------
				// ���ö����λ������
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, tranglesBuffer_sjr);
				// ���ö������ɫ����trangleColors
				gl.glColorPointer(4, GL10.GL_FIXED, 0, trangleColorsBuffer_sjr);
				// ��cubeFacetsBufferָ����������������
				gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, trangleFacetsBuffer_sjr.remaining(),
						GL10.GL_UNSIGNED_BYTE, trangleFacetsBuffer_sjr);
				//--------------------����fkt-----------------------
				// ���ö����λ������
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, tranglesBuffer_fkt);
				// ���ö������ɫ����trangleColors
				gl.glColorPointer(4, GL10.GL_FIXED, 0, trangleColorsBuffer_fkt);
				// ��cubeFacetsBufferָ����������������
				gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, trangleFacetsBuffer_fkt.remaining(),
						GL10.GL_UNSIGNED_BYTE, trangleFacetsBuffer_fkt);
		// ���ƽ���
		gl.glFinish();
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// ��ת�Ƕ�����1
		Log.d("dnagqian", "wubianxing"+getFive_Colors()[0]+" "+getFive_Colors()[1]);
		rotate += 1;
	}

	// ����һ�����߷�������int[]����ת��ΪOpenGL ES�����IntBuffer
	private IntBuffer intBufferUtil(int[] arr) {
		IntBuffer mBuffer;
		// ��ʼ��ByteBuffer������Ϊarr����ĳ���*4����Ϊһ��intռ4���ֽ�
		ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
		// ����������nativeOrder
		qbb.order(ByteOrder.nativeOrder());
		mBuffer = qbb.asIntBuffer();
		mBuffer.put(arr);
		mBuffer.position(0);
		return mBuffer;
	}

	// ����һ�����߷�������float[]����ת��ΪOpenGL ES�����FloatBuffer
	private FloatBuffer floatBufferUtil(float[] arr) {
		FloatBuffer mBuffer;
		// ��ʼ��ByteBuffer������Ϊarr����ĳ���*4����Ϊһ��intռ4���ֽ�
		ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
		// ����������nativeOrder
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
	



