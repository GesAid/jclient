package ges.flash.jclient.obj;

import java.util.List;

public class IssueInfo {
		private String summary;
		private String status;
		private String dateCreate;
		private String assigne;
		private String idIssue;
		private String description;
		private List<String> comments;
		public IssueInfo() {};
		public IssueInfo(String summary, String status, 
				String dateCreate, String assigne) {
			this.assigne = assigne;
			this.dateCreate = dateCreate;
			this.status = status;
			this.summary = summary;
		}
		
		public String getSummary() {
			return summary;
		}
		public void setSummary(String summary) {
			this.summary = summary;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getDateCreate() {
			return dateCreate;
		}
		public void setDateCreate(String dateCreate) {
			this.dateCreate = dateCreate;
		}
		public String getAssigne() {
			return assigne;
		}
		public void setAssigne(String assigne) {
			this.assigne = assigne;
		}
		public String getIdIssue() {
			return idIssue;
		}
		public void setIdIssue(String idIssue) {
			this.idIssue = idIssue;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public List<String> getComments() {
			return comments;
		}
		public void setComments(List<String> comments) {
			this.comments = comments;
		}
		
		
}
