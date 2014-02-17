
package com.televisa.commons.services.utilities;

import java.util.HashMap;
import java.util.Map;

public class ServicesTransferModel {

    private final Map<Class<?>, Object> map;

    public ServicesTransferModel() {
        this.map = new HashMap<Class<?>, Object>();
    }

    public Map<Class<?>, Object> getServices() {
        return map;
    }

    public <T> Object get(Class<T> e) {
        return map.get(e);
    }

    public <T> void put(T e) {
        map.put(e.getClass(), e);
    }

}
