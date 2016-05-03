package com.Tools;

import java.util.List;

import android.R.integer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.StaticLayout;
import android.util.Log;

public class ActionSingle implements Runnable{

	public Boolean isAlive = true;
	int[] colors = null;
	int choosen = 0;
	public Boolean bool = true;
	private Boolean isRun = true;
	private List<int[]> list;
	private static MyRenderer myRenderer;
	Handler handler;
	Bundle b_oneBundle ;
	Message m_oneMessage;
	public ActionSingle(List<int[]> simuList,int choosen,Boolean isSimu,Handler handler){
		this.choosen = choosen;
		list = simuList ;
		bool = isSimu;
		this.handler = handler;
	}
	public Boolean getIsAlive() {
		return isAlive;
	}
	public void setIsAlive(Boolean isAlive) {
		this.isAlive = isAlive;
	}
	public Boolean getIsRun() {
		return isRun;
	}
	public void setIsRun(Boolean isRun) {
		this.isRun = isRun;
	}
	
    public Boolean getBool() {
		return bool;
	}
	public void setBool(Boolean bool) {
		this.bool = bool;
	}

	
	public static MyRenderer getMyRenderer() {
		return myRenderer;
	}
	public static void setMyRenderer(MyRenderer myRenderer) {
		ActionSingle.myRenderer = myRenderer;
		
	}
	
	
	public List<int[]> getList() {
		return list;
	}
	public void setList(List<int[]> list) {
		this.list = list;
	
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
			while(isRun){
				if(bool){
					for(int i=0;i<list.size();i++){
						int state = list.get(i)[0];
						int time = list.get(i)[7];
						int allTime = time;
						int count = 0;
						int RunTime = 0;
						int foreColor[] = {list.get(i)[1],list.get(i)[2],list.get(i)[3]};
					    int backColor[] = {list.get(i)[4],list.get(i)[5],list.get(i)[6]};
					    int[] continueForeColor = null;
						int[] continueBackColor = null;
					  
					    float R = 0;
					    float G = 0;
					    float B = 0;
						final int[] currentforeColor = TransferData.ToColors(foreColor, choosen);
						final int[] currentbackColor = TransferData.ToColors(backColor, choosen);
						int times = 0;
						if(state==2){
							count = list.get(i)[8];
							RunTime = time*2;
							allTime = count*time;
						}
						if(state==3){
							continueForeColor = foreColor;
							continueBackColor = backColor;
							
						}
while(allTime>=0&&isRun){
							
					switch (state) {
							case 1:
								colors = currentforeColor;
								break;
							case 2:
								if(allTime%RunTime>=time){
							    colors = currentforeColor;
								}
								if(allTime%RunTime<time){
								colors = currentbackColor;
								}
								break; 
							case 3:
			
								if(allTime==count*time){
									colors = TransferData.ToColors(continueForeColor, choosen);
								}else if(allTime==0){
									colors = TransferData.ToColors(continueBackColor, choosen);
								}else {
									R = (continueBackColor[0]-continueForeColor[0])/allTime;
									G = (continueBackColor[1]-continueForeColor[1])/allTime;
									B = (continueBackColor[2]-continueForeColor[2])/allTime;
									continueForeColor[0]=(int)(continueForeColor[0]+R);
									continueForeColor[1]=(int)(continueForeColor[1]+G);
									continueForeColor[2]=(int)(continueForeColor[2]+B);
									colors = TransferData.ToColors(continueForeColor, choosen);
									
								}
								
								times++;

								break;
							default:
								break;
				}
							allTime--;

				switch(choosen){
								case 0 :myRenderer.setFiveColors(colors);
								Log.d("choosen", "choosen"+choosen+"colors0"+colors[0]+"colors"+colors[1]);

								        break;
								case 1 :myRenderer.setFktColors(colors);
								Log.d("choosen", "choosen"+choosen+"colors0"+colors[0]+"colors"+colors[1]);

								        break;
								case 2 :myRenderer.setGlmColors(colors);
								Log.d("choosen", "choosen"+choosen+"colors0"+colors[0]+"colors"+colors[1]);

								        break;
								case 3 :myRenderer.setHnoColors(colors);
								Log.d("choosen", "choosen"+choosen+"colors0"+colors[0]+"colors"+colors[1]);

								        break;
								case 4 :myRenderer.setPiqColors(colors);
								Log.d("choosen", "choosen"+choosen+"colors0"+colors[0]+"colors"+colors[1]);

								        break;
								case 5 :myRenderer.setSjrColors(colors);
								Log.d("choosen", "choosen"+choosen+"colors0"+colors[0]+"colors"+colors[1]);

								        break;
			   }
							
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
}
						}
						if(!isRun){
							break;
						}
						isRun = false;
					}
				
					
					
				
					
					
					
			}
			int[] Five_Colors = new int[] {
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
			int[] trangleColors= new int[] { 
					65535, 0, 0, 0, 
					65535, 65535, 0, 0,
					65535, 0, 65535, 0, 
					65535, 0, 0, 0,
					65535, 65535, 0, 0,
					65535, 0,65535, 0
		    };
			switch(choosen){
			case 0 :myRenderer.setFiveColors(Five_Colors);
			        break;
			case 1 :myRenderer.setFktColors(trangleColors);
			        break;
			case 2 :myRenderer.setGlmColors(trangleColors);
			        break;
			case 3 :myRenderer.setHnoColors(trangleColors);
			        break;
			case 4 :myRenderer.setPiqColors(trangleColors);
			        break;
			case 5 :myRenderer.setSjrColors(trangleColors);
			        break;
			}
					
					//isAlive = false;
				b_oneBundle= new Bundle();
				b_oneBundle.putString("result", "END");
				m_oneMessage = new Message();
				m_oneMessage.setData(b_oneBundle);
				handler.sendMessage(m_oneMessage);
		
	}
		
	}
	


