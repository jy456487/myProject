package jim.yang.logback;

public class MsgContext {
	private final String a;

	private final String b;

	private final long c;

	private long d = 120000L;

	private String e = "INFO";

	public MsgContext(String messageId, String channelName, long startTime) {
		this.a = messageId;
		this.b = channelName;
		this.c = startTime;
	}

	public String getMessageId() {
		return this.a;
	}

	public String getChannelName() {
		return this.b;
	}

	public long getStartTime() {
		return this.c;
	}

	public long getTimeOut() {
		return this.d;
	}

	public void setTimeOut(long timeOut) {
		this.d = timeOut;
	}

	public String getLog_level() {
		return this.e;
	}

	public void setLog_level(String log_level) {
		this.e = log_level;
	}
}