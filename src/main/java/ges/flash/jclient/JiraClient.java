package ges.flash.jclient;

import java.awt.List;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueFieldId;
import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.api.domain.input.LinkIssuesInput;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

public class JiraClient {
	
	
	private JiraRestClient restClient;

	public JiraClient(String username, String password, String jiraUrl) {
	    this.username = username;
	    this.password = password;
	    this.jiraUrl = jiraUrl;
	    this.restClient = getJiraRestClient();
	}
	
	private String jiraUrl;
	private String password;
	private String username;
	
	private JiraRestClient getJiraRestClient() {
	    return new AsynchronousJiraRestClientFactory()
	      .createWithBasicHttpAuthentication(getJiraUri(), this.username, this.password);
	}
	private URI getJiraUri() {
	    return URI.create(this.jiraUrl);
	}

	public String createIssueJira(String summary, String description, String typeIssue) {
	    IssueRestClient issueClient = restClient.getIssueClient();
	    IssueInputBuilder iib = new IssueInputBuilder();
		iib.setProjectKey("ITSUP");
		iib.setSummary(summary);
		iib.setIssueTypeId((long) 10065);
		iib.setDescription(description);
	//	iib.setFieldValue("customfield_10014", "TSUP-55");
		iib.setFieldInput(new FieldInput("customfield_10014", typeIssue));
	    IssueInput newIssue = iib.build();
	    return issueClient.createIssue(newIssue).claim().getKey();
	}
	

}
