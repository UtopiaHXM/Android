package com.draw.Tools;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;


public class infoInit {

	private static float[] Points;   //顶点的集合
	private static int[] colors;     //顶点颜色的集合
	private static byte[] faces;     //集合体面的几何
	private static float red,green,blue,alpha;   //初始化颜色
	private List<Float[]> pointList;
	private List<int[]> colorsList;
	private List<byte[]> facesList;
	
	//用于绘制renderder实现的类的可用数据
	
	private static int width,height;  //视窗大小
	//private static int x,y;  //3D视窗位置
	
	//定义Open GL ES 绘制所需要的Buffer对象
	private static FloatBuffer pointsBuffer;    //集合体顶点Buffer
	private static IntBuffer colorBuffer;       //集合体颜色Buffer
	private static ByteBuffer facesBuffer;      //集合体面Buffer
	
	//Surface View组建大小改变时  按比例调整  初始化3D场景
	private static float ratio = (float)infoInit.width/infoInit.height;   //透视窗比例
	private static float xMinRange = -ratio,
			xMaxRange = ratio,
			yMinRange = -1,
			yMaxRange = 1,
			zMinRange = 1,
			zMaxRange = 10;  //透视投影空间大小

	//绘图旋转角度
	//private static float rotate; 
	
	
	
	//构造函数   初始化信息
	public infoInit(int x,int y,/*int width,int height,*/float red,float green,float blue,float alpha,float[] points,int[] colors,byte[] faces/*,float xMinRange,float xMaxRange,float yMinRange,float yMaxRange,float zMinRange,float zMaxRange,float ratio*/){	
		/*infoInit.x = x;
		infoInit.y = y;*/
		/*infoInit.width = width;
		infoInit.height = height;*/
		infoInit.red = red;
		infoInit.green = green;
		infoInit.blue = blue;
		infoInit.alpha = alpha;
		infoInit.Points = points;
		infoInit.pointsBuffer = TransferData.floatBufferToFloatBuffer(points);
		infoInit.colors =colors;
		infoInit.colorBuffer = TransferData.intArrayToIntBuffer(colors);
		infoInit.faces = faces;
		infoInit.facesBuffer = TransferData.byteArrayToByteBuffer(faces);
		/*infoInit.ratio = ratio;
		infoInit.xMinRange = xMinRange;
		infoInit.xMaxRange = xMaxRange;
		infoInit.yMinRange = yMinRange;
		infoInit.yMaxRange = yMaxRange;
		infoInit.zMinRange = zMinRange;
		infoInit.zMaxRange = zMaxRange;*/
		
	}
	
	public static float[] getPoints() {
		return Points;
	}
	public static void setPoints(float[] points) {
		Points = points;
	}
	public static int[] getColors() {
		return colors;
	}
	public static void setColors(int[] colors) {
		infoInit.colors = colors;
	}
	public static byte[] getFaces() {
		return faces;
	}
	public static void setFaces(byte[] faces) {
		infoInit.faces = faces;
	}
	public List<Float[]> getPointList() {
		return pointList;
	}
	public void setPointList(List<Float[]> pointList) {
		this.pointList = pointList;
	}
	public List<int[]> getColorsList() {
		return colorsList;
	}
	public void setColorsList(List<int[]> colorsList) {
		this.colorsList = colorsList;
	}
	public List<byte[]> getFacesList() {
		return facesList;
	}
	public void setFacesList(List<byte[]> facesList) {
		this.facesList = facesList;
	}
	
	/*public static int getWidth() {
		return width;
	}
	public static void setWidth(int width) {
		infoInit.width = width;
	}
	public static int getHeight() {
		return height;
	}
	public static void setHeight(int height) {
		infoInit.height = height;
	}*/
	/*public static int getX() {
		return x;
	}
	public static void setX(int x) {
		infoInit.x = x;
	}
	public static int getY() {
		return y;
	}
	public static void setY(int y) {
		infoInit.y = y;
	}
	public static float getxMinRange() {
		return xMinRange;
	}*/
	public static void setxMinRange(float xMinRange) {
		infoInit.xMinRange = xMinRange;
	}
	public static float getxMaxRange() {
		return xMaxRange;
	}
	public static void setxMaxRange(float xMaxRange) {
		infoInit.xMaxRange = xMaxRange;
	}
	public static float getyMinRange() {
		return yMinRange;
	}
	public static void setyMinRange(float yMinRange) {
		infoInit.yMinRange = yMinRange;
	}
	public static float getyMaxRange() {
		return yMaxRange;
	}
	public static void setyMaxRange(float yMaxRange) {
		infoInit.yMaxRange = yMaxRange;
	}
	public static float getzMinRange() {
		return zMinRange;
	}
	public static void setzMinRange(float zMinRange) {
		infoInit.zMinRange = zMinRange;
	}
	public static float getzMaxRange() {
		return zMaxRange;
	}
	public static void setzMaxRange(float zMaxRange) {
		infoInit.zMaxRange = zMaxRange;
	}
	public static float getRatio() {
		return ratio;
	}
	public static void setRatio(float ratio) {
		infoInit.ratio = ratio;
	}
	public static FloatBuffer getPointsBuffer() {
		return pointsBuffer;
	}
	public static void setPointsBuffer(FloatBuffer pointsBuffer) {
		infoInit.pointsBuffer = pointsBuffer;
	}
	public static IntBuffer getColorBuffer() {
		return colorBuffer;
	}
	public static void setColorBuffer(IntBuffer colorBuffer) {
		infoInit.colorBuffer = colorBuffer;
	}
	public static ByteBuffer getFacesBuffer() {
		return facesBuffer;
	}
	public static void setFacesBuffer(ByteBuffer facesBuffer) {
		infoInit.facesBuffer = facesBuffer;
	}

	public static float getRed() {
		return red;
	}

	public static void setRed(float red) {
		infoInit.red = red;
	}

	public static float getGreen() {
		return green;
	}

	public static void setGreen(float green) {
		infoInit.green = green;
	}

	public static float getBlue() {
		return blue;
	}

	public static void setBlue(float blue) {
		infoInit.blue = blue;
	}

	public static float getAlpha() {
		return alpha;
	}
	/*public static float getRotate() {
		return rotate;
	}

	public static void setRotate(float rotate) {
		infoInit.rotate = rotate;
	}
*/
	public static void setAlpha(float alpha) {
		infoInit.alpha = alpha;
	}
	
	
	
}