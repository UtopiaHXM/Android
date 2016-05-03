package com.draw.Tools;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import android.opengl.GLSurfaceView.Renderer;
import android.view.animation.TranslateAnimation;

public class drawRender implements Renderer {
	
	private float rotate; 
    private infoInit info;
    float[] angle1 = new float[]{
    	0.0f,0.5f,0.0f,
    	-0.5f,-0.5f,-0.2f,
    	0.5f,-0.5f,-0.2f,
    	0.0f,-0.2f,0.2f
    };
    int[] angle2 = new int[]{
    	65535,0,0,0,
    	0,65535,0,0,
    	0,0,65535,0,
    	65535,65535,0,0
    };
    byte[] angle3 = new byte[]{
    	0,1,2,
    	0,1,3,
    	1,2,3,
    	0,2,3
    };
	public infoInit getInfo() {
		return info;
	}

	public void setInfo(infoInit info) {
		this.info = info;
	}

	public drawRender(infoInit info){
		this.info = info;
	}
	public drawRender(){
		super();
	}
	@Override
	public void onDrawFrame(GL10 gl) {// ����ͼ��
		// TODO Auto-generated method stub

		System.out.println("==============����׼���׶�     ��ʼ������==============");
		// �����Ļ�������Ȼ���
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// ����������������
				gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
				// ����������ɫ����
				gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
				// ���õ�ǰ�����ջΪģ�Ͷ�ջ
						gl.glMatrixMode(GL10.GL_MODELVIEW);
						//���þ�����ͼ
						gl.glLoadIdentity();
                 		gl.glTranslatef(-0.6f,0.0f,-1.5f);
                 		//System.out.println("��һ������xֵ"+infoInit.getPointsBuffer().get(3));

		/*// ���õ�ǰ�����ջΪģ�Ͷ�ջ
		gl.glMatrixMode(GL10.GL_MODELVIEW);*/
		/*
		 * //���������������� gl.glEnableClientState(GL10.GL_VERTEX_ARRAY); //����������ɫ����
		 * gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		 */
		//���ô�ɫ���  ��Ҫ���ó�ʼ���Ķ�����ɫ����
		/*gl.glColor4f(infoInit.getRed(),infoInit.getGreen(),infoInit.getBlue(),infoInit.getAlpha());
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);*/
		//���ö���λ������
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, /*infoInit.getPointsBuffer()*/TransferData.intArrayToIntBuffer(angle2));
		//���ö�����ɫ����
		gl.glColorPointer(4, GL10.GL_FIXED, 0, /*infoInit.getColorBuffer()*/TransferData.floatBufferToFloatBuffer(angle1));
		//gl.glScalef(10.0f,10.0f,10.0f);//��ʾ����ǰͼ����x,y,z��ֱ�Ŵ�Ϊԭ����10��
		System.out.println("==================��ʼ����======================");
		//��ָ�������ͼ��
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP,TransferData.byteArrayToByteBuffer(angle3).remaining(), GL10.GL_UNSIGNED_BYTE, TransferData.byteArrayToByteBuffer(angle3)/*infoInit.getFacesBuffer()*/);
		System.out.println("===================��������====================");
		//��ɻ���  ������Դ
	/*	gl.glFinish();
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);*/

		rotate += 1 ;
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {// ��ʼ���Ӵ�
		// TODO Auto-generated method stub
		System.out.println("��ʼ���Ӵ�");
		gl.glViewport(0,0, width, height);
		// ͶӰ���� ��ʾ�����3DЧ��
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// ��ʼ����λ����
		gl.glLoadIdentity();
		// ����͸�Ӵ�����
		float ratio = (float) width / height;
		// ����͸�Ӵ�
System.out.println("xminRange  "/*+info.getxMinRange()*/);
		/*gl.glFrustumf(infoInit.getxMinRange(), infoInit.getxMaxRange(),
				infoInit.getyMinRange(), infoInit.getyMaxRange(),
				infoInit.getzMinRange(), infoInit.getzMaxRange());*/
gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
/*gl.glOrthof(-ratio, ratio, -1, 1, -1, 1);
*/
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {// ���ܳ�ʼ��
		// TODO Auto-generated method stub
		System.out.println("��ʼ������");
		// �رտ����� �������
		gl.glDisable(GL10.GL_DITHER);
		// ����ϵͳ��͸�ӽ�������
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		gl.glClearColor(0, 0, 0, 0);
		// ������Ӱ��ƽ��ģʽ
		gl.glShadeModel(GL10.GL_SMOOTH);
		// ������Ȳ���
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// ������Ȳ������� ���Ŀ������zֵ<����ǰ����zֵ�������Ŀ������
		gl.glDepthFunc(GL10.GL_LEQUAL);
		
	}

}
