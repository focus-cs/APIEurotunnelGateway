/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurotunnel.sciforma.manager;

import com.eurotunnel.sciforma.Run;
import com.eurotunnel.sciforma.exeception.TechnicalException;
import com.sciforma.psnext.api.Issue;
import com.sciforma.psnext.api.Organization;
import com.sciforma.psnext.api.PSException;
import com.sciforma.psnext.api.Session;
import com.sciforma.psnext.api.SystemData;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Eric
 */
public class IssueManagerImpl implements IssueManager{
    
    private Session session;
    private Boolean find;
    private Issue i;
    
    public IssueManagerImpl(Session session) {
		this.session = session;
	}
    
    @Override
    public Issue findIssueById(String ID) throws TechnicalException {
        i = null;
        find = false;
        Organization rootOrg = null;
        try {
            rootOrg = (Organization) session.getSystemData(SystemData.ORGANIZATIONS);
            displayOrgStructure("", rootOrg, ID);
        } catch (Exception ex) {
           java.util.logging.Logger.getLogger(Run.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return i;
    }
    
    
    private  void displayOrgStructure(String prepend, Organization parent, String ID) {
        try {
            if (parent == null) {
                return;
            }
            List<Issue> lIssue = parent.getIssues();
            for (Issue issue : lIssue) {
                if(issue.getStringField("N° ECR").equals(ID)){
                    i = issue;
                    find = true;
                    break;
                }
            }
            if (parent.getChildren() != null && !find) {
                Iterator it = parent.getChildren().iterator();
                while (it.hasNext()) {
                    displayOrgStructure(prepend + "\t", (Organization) it.next(), ID);
                }
            }
        } catch (PSException ex) {
            java.util.logging.Logger.getLogger(Run.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
