package edu.ccsu.cs595.capstone.scadservices.entity;

/**
 * Thread-local security information needed for automatic entity audit.
 */
public class AuditContext {

	public static final String ANONYMOUS_GUID = "anonymous";
	public static final String ANONYMOUS_USER = "anonymous";

	private static final ThreadLocal<AuditContext> AUDIT_CONTEXT = new ThreadLocal<>();

	public static AuditContext getAuditContext() {
		return AUDIT_CONTEXT.get();
	}

	public static void setAuditContext(AuditContext sc) {
		AUDIT_CONTEXT.set(sc);
	}

	private final String userGuid;
	private final String userName;

	public AuditContext(String userGuid, String userName) {
		this.userGuid = userGuid == null ? ANONYMOUS_GUID : userGuid;
		this.userName = (ANONYMOUS_GUID.equals(this.userGuid)) ? ANONYMOUS_USER : userName;
	}

	public String getUserGuid() {
		return userGuid;
	}

	public String getUserName() {
		return userName;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
