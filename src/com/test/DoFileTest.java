package com.test;

public class DoFileTest {

//	String downloadFile = new UpFile().downloadFile("netdisk.file.get", param);
//	file_temp.add(downloadFile);
////	renderFile(new File(downloadFile));
//	
//	
//	
//	 File file = new File(downloadFile); 
		
//        try {  
//            //判断是否是IE11  
//            Boolean flag= getRequest().getHeader("User-Agent").indexOf("like Gecko")>0;  
//             
//             if (getRequest().getHeader("User-Agent").toLowerCase().indexOf("msie") >0||flag){  
//                 filename = URLEncoder.encode(filename, "UTF-8");//IE浏览器  
//             }else {  
//              //先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,  
//              //这个文件名称用于浏览器的下载框中自动显示的文件名  
//              filename = new String(filename.replaceAll(" ", "").getBytes("UTF-8"), "ISO8859-1");  
//              //firefox浏览器  
//              //firefox浏览器User-Agent字符串:   
//              //Mozilla/5.0 (Windows NT 6.1; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0  
//             }  
//          InputStream fis = new BufferedInputStream(new FileInputStream(downloadFile));  
//             byte[] buffer;  
//          buffer = new byte[fis.available()];  
//             fis.read(buffer);  
//             fis.close();  
//             getResponse().reset();  
//             getResponse().addHeader("Content-Disposition", "attachment;filename=" +filename);  
//             getResponse().addHeader("Content-Length", "" + file.length());  
//             OutputStream os = getResponse().getOutputStream();  
//             getResponse().setContentType("application/octet-stream");  
//             os.write(buffer);// 输出文件  
//             os.flush();  
//             os.close();  
//         } catch (IOException e) {  
//          // TODO Auto-generated catch block  
//          e.printStackTrace();  
//         } 
	
}
