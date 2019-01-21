/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurotunnel.sciforma.manager;

import com.eurotunnel.sciforma.exeception.TechnicalException;
import com.sciforma.psnext.api.Issue;
import com.sciforma.psnext.api.Project;

/**
 *
 * @author Eric
 */
public interface IssueManager {
    
    Issue findIssueById(String ID) throws TechnicalException;
    
}
