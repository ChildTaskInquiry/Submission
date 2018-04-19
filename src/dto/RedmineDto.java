package dto;

import java.sql.Timestamp;

import operationString.StringOperation;

public class RedmineDto {

	private StringOperation strOpr = new StringOperation();

	private String subsystemIdentifier;
	private Integer issue_id;
	private Integer parent_id;
	private String subject;
	private String tracker;
	private String status;
	private String project;
	private String author;
	private String assignedTo;
	private String priority;
	private String importance;
	private Integer key;
	private String value;
	private String epic_flag;
	private String license;
	private String subsystem;
	private String start_date;
	private String due_date;
	private Timestamp created_on;
	private Timestamp updated_on;

	public RedmineDto() {
		super();
	}

	public RedmineDto(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

	public RedmineDto(RedmineDto rDto, Integer parent_id) {
		this.subsystemIdentifier = rDto.getSubsystemIdentifier();
		this.issue_id = rDto.getIssue_id();
		this.parent_id = parent_id;
		this.subject = rDto.getSubject();
		this.tracker = rDto.getTracker();
		this.status = rDto.getStatus();
		this.project = rDto.getProject();
		this.author = rDto.getAuthor();
		this.assignedTo = rDto.getAssignedTo();
		this.priority = rDto.getPriority();
		this.importance = rDto.getImportance();
	}

	public RedmineDto(Integer issue_id, Integer parent_id, String subject, String tracker, String status,
			String project, String author, String assignedTo, String priority, String importance) {
		this.issue_id = issue_id;
		this.parent_id = parent_id;
		this.subject = subject;
		this.tracker = tracker;
		this.status = status;
		this.project = project;
		this.author = author;
		this.assignedTo = assignedTo;
		this.priority = priority;
		this.importance = importance;
	}

	public RedmineDto(Integer issue_id, Integer parent_id, String subject, String tracker, String status,
			String project, String license, String subsystem, String author, String assignedTo, String priority,
			String start_date, String due_date, Timestamp created_on, Timestamp updated_on) {
		this.issue_id = issue_id;
		this.parent_id = parent_id;
		this.subject = subject;
		this.tracker = tracker;
		this.status = status;
		this.project = project;
		this.license = license;
		this.subsystem = subsystem;
		this.author = author;
		this.assignedTo = assignedTo;
		this.priority = priority;
		this.start_date = start_date;
		this.due_date = due_date;
		this.created_on = created_on;
		this.updated_on = updated_on;
	}

	public Integer getIssue_id() {
		return issue_id;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public String getSubsystemIdentifier() {
		return subsystemIdentifier;
	}

	public Integer getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public StringOperation getStrOpr() {
		return strOpr;
	}

	public String getSubject() {
		return subject;
	}

	public String getStatus() {
		return status;
	}

	public String getProject() {
		return project;
	}

	public String getAuthor() {
		return author;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public String getTracker() {
		return tracker;
	}

	public String getPriority() {
		return priority;
	}

	public String getImportance() {
		return importance;
	}

	public String getEpic_flag() {
		return epic_flag;
	}

	public void setEpic_flag(String epic_flag) {
		this.epic_flag = epic_flag;
	}

	public String getLicense() {
		return license;
	}

	public String getSubsystem() {
		return subsystem;
	}

	public String getStart_date() {
		return start_date;
	}

	public String getDue_date() {
		return due_date;
	}

	public Timestamp getCreated_on() {
		return created_on;
	}

	public Timestamp getUpdated_on() {
		return updated_on;
	}

}
