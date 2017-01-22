/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service.options;

/**
 *
 * @author arek
 */
public interface ApplicationOptionsProvider {

    public String getApiKey();

    public void setApiKey(String apiKey);

    public int getMaxResultsPerQuery();

    public void setMaxResultsPerQuery(int maxResultsPerQuery);

    public int getMaxResults();

    public void setMaxResults(int maxResults);

}
