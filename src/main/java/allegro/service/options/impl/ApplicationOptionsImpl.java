/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service.options.impl;

import allegro.service.options.ApplicationOptionsProvider;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

/**
 *
 * @author arek
 */
@Service
public class ApplicationOptionsImpl implements Serializable, ApplicationOptionsProvider {

    @Value("settings.allegro.apikey")
    private String apiKey;
    private int maxResultsPerQuery;
    private int maxResults;

    public String getApiKey() {
        return apiKey="41e7acc7";
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public int getMaxResultsPerQuery() {
        return maxResultsPerQuery;
    }

    public void setMaxResultsPerQuery(int maxResultsPerQuery) {
        this.maxResultsPerQuery = maxResultsPerQuery;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.apiKey != null ? this.apiKey.hashCode() : 0);
        hash = 67 * hash + this.maxResultsPerQuery;
        hash = 67 * hash + this.maxResults;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ApplicationOptionsImpl other = (ApplicationOptionsImpl) obj;
        if (this.maxResultsPerQuery != other.maxResultsPerQuery) {
            return false;
        }
        if (this.maxResults != other.maxResults) {
            return false;
        }
        if ((this.apiKey == null) ? (other.apiKey != null) : !this.apiKey.equals(other.apiKey)) {
            return false;
        }
        return true;
    }

}
