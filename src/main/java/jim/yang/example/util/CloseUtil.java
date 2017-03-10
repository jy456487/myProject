package jim.yang.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.nio.channels.FileChannel;

public class CloseUtil {

	public static void close(InputStream is) {
		if(is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(OutputStream os) {
		if(os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Reader reader) {
		if(reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Writer writer) {
		if(writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Socket socket) {
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(FileChannel fc) {
		if(fc != null) {
			try {
				fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(RandomAccessFile rf) {
		if(rf != null) {
			try {
				rf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}