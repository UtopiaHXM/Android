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
	public void onDrawFrame(GL10 gl) {// 绘制图形
		// TODO Auto-generated method stub

		System.out.println("==============绘制准备阶段     初始化数据==============");
		// 清除屏幕缓存和深度缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 启动顶点坐标数据
				gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
				// 启动顶点颜色数组
				gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
				// 设置当前矩阵堆栈为模型堆栈
						gl.glMatrixMode(GL10.GL_MODELVIEW);
						//重置矩阵视图
						gl.glLoadIdentity();
                 		gl.glTranslatef(-0.6f,0.0f,-1.5f);
                 		//System.out.println("第一个坐标x值"+infoInit.getPointsBuffer().get(3));

		/*// 设置当前矩阵堆栈为模型堆栈
		gl.glMatrixMode(GL10.GL_MODELVIEW);*/
		/*
		 * //启动顶点坐标数据 gl.glEnableClientState(GL10.GL_VERTEX_ARRAY); //启动顶点颜色数组
		 * gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		 */
		//设置纯色填充  需要禁用初始化的顶点颜色启动
		/*gl.glColor4f(infoInit.getRed(),infoInit.getGreen(),infoInit.getBlue(),infoInit.getAlpha());
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);*/
		//设置顶点位置数据
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, /*infoInit.getPointsBuffer()*/TransferData.intArrayToIntBuffer(angle2));
		//设置顶点颜色数据
		gl.glColorPointer(4, GL10.GL_FIXED, 0, /*infoInit.getColorBuffer()*/TransferData.floatBufferToFloatBuffer(angle1));
		//gl.glScalef(10.0f,10.0f,10.0f);//表示将当前图形沿x,y,z轴分别放大为原来的10倍
		System.out.println("==================开始绘制======================");
		//按指定面绘制图形
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP,TransferData.byteArrayToByteBuffer(angle3).remaining(), GL10.GL_UNSIGNED_BYTE, TransferData.byteArrayToByteBuffer(angle3)/*infoInit.getFacesBuffer()*/);
		System.out.println("===================结束绘制====================");
		//完成绘制  重置资源
	/*	gl.glFinish();
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);*/

		rotate += 1 ;
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {// 初始化视窗
		// TODO Auto-generated method stub
		System.out.println("初始化视窗");
		gl.glViewport(0,0, width, height);
		// 投影矩阵 显示逼真的3D效果
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// 初始化单位矩阵
		gl.glLoadIdentity();
		// 计算透视窗比例
		float ratio = (float) width / height;
		// 设置透视窗
System.out.println("xminRange  "/*+info.getxMinRange()*/);
		/*gl.glFrustumf(infoInit.getxMinRange(), infoInit.getxMaxRange(),
				infoInit.getyMinRange(), infoInit.getyMaxRange(),
				infoInit.getzMinRange(), infoInit.getzMaxRange());*/
gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
/*gl.glOrthof(-ratio, ratio, -1, 1, -1, 1);
*/
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {// 功能初始化
		// TODO Auto-generated method stub
		System.out.println("初始化功能");
		// 关闭抗抖动 提高性能
		gl.glDisable(GL10.GL_DITHER);
		// 设置系统对透视进行修正
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		gl.glClearColor(0, 0, 0, 0);
		// 设置阴影的平滑模式
		gl.glShadeModel(GL10.GL_SMOOTH);
		// 设置深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// 设置深度测试类型 如果目标像素z值<＝当前像素z值，则绘制目标像素
		gl.glDepthFunc(GL10.GL_LEQUAL);
		
	}

}
