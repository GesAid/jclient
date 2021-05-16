package ges.flash.jclient.obj;

public class WebHook {

	private Issue issue;
	private Comment comment;
	
	public class Issue {

		private String key;
		private Fields fields;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public Fields getFields() {
			return fields;
		}
		public void setFields(Fields fields) {
			this.fields = fields;
		}
		
		
		
	}
	public class Status{
		private String name;
		private String description;
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	public class Resolution{
		private String name;
		private String description;
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	public class Fields {
		private Assignee assignee;
		private Resolution resolution;
		private Status status;
		private String summary;
		private String description;
		private String customfield_10301;
		private String customfield_10300;
		public String getCustomfield_10301() {
			return customfield_10301;
		}
		public void setCustomfield_10301(String customfield_10301) {
			this.customfield_10301 = customfield_10301;
		}
		public String getCustomfield_10300() {
			return customfield_10300;
		}
		public void setCustomfield_10300(String customfield_10300) {
			this.customfield_10300 = customfield_10300;
		}
		public String getSummary() {
			return summary;
		}
		public void setSummary(String summary) {
			this.summary = summary;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Status getStatus() {
			return status;
		}
		public void setStatus(Status status) {
			this.status = status;
		}
		public Resolution getResolution() {
			return resolution;
		}
		public void setResolution(Resolution resolution) {
			this.resolution = resolution;
		}
		public Assignee getAssignee() {
			return assignee;
		}
		public void setAssignee(Assignee assignee) {
			this.assignee = assignee;
		}

	}
	
	public class Assignee {
		private String displayName;

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}


	}

	
	
	public class Comment {
		private String body;

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}


}
