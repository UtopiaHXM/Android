package com.Tools;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;


public class TransferData {

	
	//int[]  -->  IntBuffer
	public static IntBuffer intArrayToIntBuffer(int[] x){
		IntBuffer intBuffer = null;
		//初始化ByteBuffer数组
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(x.length*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		intBuffer = byteBuffer.asIntBuffer();
		intBuffer.put(x);
		intBuffer.position(0);
		return intBuffer;
	}
	
	
	//byte[] -->  ByteBuffer
	public static ByteBuffer byteArrayToByteBuffer(byte[] x){
		ByteBuffer byteBuffer = null;
		//初始化ByteBuffer数组
		byteBuffer = ByteBuffer.wrap(x);
		return byteBuffer;
	}
	
	//float[] -->  FloatBuffer
	public static FloatBuffer floatArrayToFloatBuffer(float[] x){
		FloatBuffer floatBuffer = null;
		//初始化ByteBuffer数组
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(x.length*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		floatBuffer = byteBuffer.asFloatBuffer();
		floatBuffer.put(x);
		floatBuffer.position(0);
		return floatBuffer;
	}
	
	public static List<FloatBuffer> ToFloatBufferList(List<float[]> x){
		List<FloatBuffer> floatBuffersList = new ArrayList<FloatBuffer>();
		for(int i=0;i<x.size();i++){
			float[] currentArray = x.get(i);
			FloatBuffer currentBuffer = floatArrayToFloatBuffer(currentArray);
			floatBuffersList.add(currentBuffer);
		}
		return floatBuffersList;
	}
	public static List<IntBuffer> ToIntBufferList(List<int[]> x){
		List<IntBuffer> intBuffersList = new ArrayList<IntBuffer>();
		for(int i=0;i<x.size();i++){
			int[] currentArray = x.get(i);
			IntBuffer currentBuffer = intArrayToIntBuffer(currentArray);
			intBuffersList.add(currentBuffer);
		}
		return intBuffersList;
	}

	public static List<ByteBuffer> ToByteBufferList(List<byte[]> x){
		List<ByteBuffer> byteBuffersList = new ArrayList<ByteBuffer>();
		for(int i=0;i<x.size();i++){
			byte[] currentArray = x.get(i);
			ByteBuffer currentBuffer = byteArrayToByteBuffer(currentArray);
			byteBuffersList.add(currentBuffer);
		}
		return byteBuffersList;
	}
	
	public static List<ArrayList<Buffer>> getAllLists(List<float[]> points,List<int[]> colors,List<byte[]>faces,int numbers){
		List<ArrayList<Buffer>> allLists =new ArrayList<ArrayList<Buffer>>();
		List<FloatBuffer> pointsListBuffers = ToFloatBufferList(points);
		List<IntBuffer> colorsListBuffers = ToIntBufferList(colors);
		List<ByteBuffer> facesListBuffers = ToByteBufferList(faces);
		for(int i=0;i<numbers;i++){		
			//当前几何体参数   每一个几何体是一个list   包括点集，颜色集，面集
			List<Buffer> currentGeometry = new ArrayList<Buffer>();
			FloatBuffer currentGeometryPoints = pointsListBuffers.get(i);
			IntBuffer currentGeometryColors = colorsListBuffers.get(i);
			ByteBuffer currentGeometryFaces = facesListBuffers.get(i);
			currentGeometry.add(currentGeometryPoints);
			currentGeometry.add(currentGeometryColors);
			currentGeometry.add(currentGeometryFaces);
			allLists.add((ArrayList<Buffer>) currentGeometry);
		}
		return allLists;
	}
	//缩放坐标
	public static float[] changeArray(float[] obj,float number){
		for(int i=0;i<obj.length;i++){
			obj[i] = obj[i]*number;
		}
		return obj;
	}
	
	public static int[] arrayToRGB(int[] color){
		int[] currentColor = new int[4];
		for(int i=0;i<color.length;i++){
			currentColor[i] = color[i]*255;
		}
		return currentColor;
	}
	
	public static int[] ToColors(int[] color,int choosen){
		int[] colors = null;
		if(choosen==0){
			colors = new int[40];
		}else {
			colors = new int[24];
		}
		color = arrayToRGB(color);
		for(int i=0;i<colors.length;i++){
			if(i%4==0){//每组RGB的R值
				colors[i] = color[0];
			}else if (i%4==1) {//每组RGB的G值
				colors[i] = color[1];
			}else if (i%4==2) {//每组RGB的B值
				colors[i] = color[2];
			}else if (i%4==3) {//每组RGB的Alpha值
				colors[i] = color[3];
			}
		}
		return colors;
	}
	
}
