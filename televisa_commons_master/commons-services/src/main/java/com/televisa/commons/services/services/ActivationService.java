package com.televisa.commons.services.services;

/**
 * Created by IntelliJ IDEA.
 * User: palecio
 * Date: 30/7/13
 * Time: 15:25
 */
public interface ActivationService {

    public void activatePage(String path);

    public void gsaPush(String path);
    public void setFirstReplicatedDate(String path);
    public void gsaDelete(String path);

}
