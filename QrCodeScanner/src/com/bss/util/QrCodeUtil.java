package com.bss.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

/**
 * 生成二维码工具类
 *
 * @author bss
 */
public class QrCodeUtil {
	/**
	 * 生成二维码图片
	 *
	 * @return void
	 * @param 
	 * @author bss
	 */
	public static void QrcodeImg(String content,String imgPath){
		int width=200;
		int height=200;
		try{
			Qrcode qrcode=new Qrcode();
			//二维码排错率L(7%),M(15%),Q(25%),H(30%)
			qrcode.setQrcodeErrorCorrect('M');
			//如果需要存储英文的，那么必须设置setQrcodeEncodeMode('B')；有"A"和"B"
			qrcode.setQrcodeEncodeMode('B');
			// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
			qrcode.setQrcodeVersion(12);
			//设置图片尺寸
			BufferedImage buffImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
			//绘制二维码图片
			Graphics2D gs=buffImg.createGraphics();
			
			//设置二维码背景
			gs.setBackground(Color.WHITE);
			//绘制矩形区
			gs.clearRect(0, 0, width, height);
			//设置颜色
			gs.setColor(Color.BLACK);
			//获取内容的字节数组，设置内容的编码集
			byte[] contetBytes=content.getBytes("utf-8");
			//设置偏移量
			int pixoff=2;
			//输出二维码
			if(contetBytes.length>0 && contetBytes.length<120){
				boolean[][] codeOut=qrcode.calQrcode(contetBytes);
				for(int i=0;i<codeOut.length;i++){
					for(int j=0;j<codeOut.length;j++){
						if(codeOut[j][i]){
							gs.fillRect(j*3+pixoff, i*3+pixoff, 3, 3);
						}
					}
				}
			}
			else{
				System.out.println("出错了");
			}
			logo(buffImg);
			//生成二维码图片
			File imgFile=new File(imgPath);
			ImageIO.write(buffImg, "jpg", imgFile);
			System.out.print("生成二维码成功");
			gs.dispose();  
			//bufferImage.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void logo(BufferedImage buffImg){
		int width=buffImg.getWidth();
		int height=buffImg.getHeight();
		try{
			Graphics2D g2 = buffImg.createGraphics(); 
			BufferedImage logo = ImageIO.read(new File("D:\\开发工具\\上课相关/logo.jpg"));
			 //开始绘制图片  
	        g2.drawImage(logo,width/5*2,height/5*2, width/5, height/5, null);//绘制       
	        BasicStroke stroke = new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);   
	        g2.setStroke(stroke);// 设置笔画对象  
	        //指定弧度的圆角矩形  
	        RoundRectangle2D.Float round = new RoundRectangle2D.Float(width/5*2, height/5*2, width/5, height/5,20,20);  
	        g2.setColor(Color.white);  
	        g2.draw(round);// 绘制圆弧矩形  
	          
	        //设置logo 有一道灰色边框  
	        BasicStroke stroke2 = new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);   
	        g2.setStroke(stroke2);// 设置笔画对象  
	        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(width/5*2+2, height/5*2+2, width/5-4, height/5-4,20,20);  
	        g2.setColor(new Color(128,128,128));  
	        g2.draw(round2);// 绘制圆弧矩形  
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String content="扫描结论：！";
		String imgPath="D:\\开发工具\\上课相关/bss.jpg";
		QrcodeImg(content, imgPath);
	}

}
