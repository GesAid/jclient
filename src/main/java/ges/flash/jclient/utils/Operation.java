package ges.flash.jclient.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import ges.flash.jclient.obj.IssueInfo;

public class Operation {
	String name = "user";
	String pass = "pass";
	String url = "https://jira.flash.ru";

	public ges.flash.jclient.obj.IssueInfo getInfo(Issue issue) {
		IssueInfo iss = new IssueInfo();
		iss.setDescription(issue.getDescription());
		iss.setSummary(issue.getSummary());
		iss.setIdIssue(issue.getKey());

		List<String> aCom = new ArrayList<String>();
		try {
			HttpResponse<JsonNode> responses = Unirest.get(url + "/rest/api/2/issue/" + issue.getKey() + "/comment")
					.basicAuth(name, pass).header("Accept", "application/json").asJson();
			JSONArray jo = responses.getBody().getArray();
			if (jo.length() > 0) {

				for (int i = 0; i < jo.length(); i++) {

					JSONObject jsonobject = jo.getJSONObject(i);
					String comments = jsonobject.get("comments").toString();
					JSONArray jcom = new JSONArray(comments);
					for (int j = 0; j < jcom.length(); j++) {
						JSONObject jcomO = jcom.getJSONObject(j);
						aCom.add(jcomO.get("body").toString());

					}
				}
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		iss.setComments(aCom);

		if (issue.getResolution() != null)
			iss.setStatus(issue.getStatus().getName() + " " + issue.getResolution().getDescription());
		else
			iss.setStatus(issue.getStatus().getName());
		iss.setDateCreate(issue.getCreationDate().toString().substring(0, 10));
		if (issue.getAssignee() != null) {
			iss.setAssigne(issue.getAssignee().getDisplayName());
		} else {
			iss.setAssigne("Ответственный не назначен");
		}
		return iss;
	}
}
