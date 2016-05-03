package com.draw.Tools;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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
	public static FloatBuffer floatBufferToFloatBuffer(float[] x){
		FloatBuffer floatBuffer = null;
		//初始化ByteBuffer数组
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(x.length*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		floatBuffer = byteBuffer.asFloatBuffer();
		floatBuffer.put(x);
		floatBuffer.position(0);
		return floatBuffer;
	}
}
