package jim.yang.example.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.apache.commons.io.IOUtils;

public class FileUtil {

	/**
	 * 获取文件后缀名
	 * @param f
	 * @return
	 */
	public static String getFileSuffix(File f) {
		String fileName = f.getName();
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	/**
	 * 获取文件后缀名
	 * @param fileName
	 * @return
	 */
	public static String getFileSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	/**
	 * 以Byte数组形式获取指定url的文件
	 * @param strUrl
	 * @return
	 * @throws IOException
	 */
	public static byte[] getRemoteFileBytes(String strUrl) throws IOException {
		URL url = new URL(strUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		return IOUtils.toByteArray(conn.getInputStream());
	}
	
	/**
	 * 通过nio的方式将指定文件读入到Byte数组中
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getFileBytes(File file) throws IOException {
        FileChannel channel = null;  
        FileInputStream fs = null;
        ByteBuffer byteBuffer = null;  
        try {  
            fs = new FileInputStream(file);  
            channel = fs.getChannel();  
            byteBuffer = ByteBuffer.allocate((int) channel.size());  
            while ((channel.read(byteBuffer)) > 0) {  
                // do nothing  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
        } finally {  
        	CloseUtil.close(channel);
        	CloseUtil.close(fs);
        }  
        return byteBuffer.array();  
	}
	
	/**
	 * 以Byte数组形式获取指定文件(适用于大文件读取 GB级)
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getFileBytes(String file) throws IOException {
		FileChannel fc = null;
		RandomAccessFile rf = null;
		byte[] result = null;
		try {
			rf = new RandomAccessFile(file, "r");
			fc = rf.getChannel();
			MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();
			result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			CloseUtil.close(fc);
			CloseUtil.close(rf);
		}
		return result;
	}
}
