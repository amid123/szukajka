/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.tools;


import java.io.Serializable;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
 
@Component
public class ProviderUtilBean implements Serializable{
    @Autowired
    private ApplicationContext ctx;
 
    public <T> T getBeanByType(final Class<T> my_class) throws UnsupportedOperationException, BeansException {
        Map beansOfType = ctx.getBeansOfType(my_class);
        final int size = beansOfType.size();
        switch (size) {
            case 0:
                throw new UnsupportedOperationException("No bean found of type" + my_class);
            case 1:
                String name = (String) beansOfType.keySet().iterator().next();
                return my_class.cast(ctx.getBean(name, my_class));
            default:
                throw new UnsupportedOperationException("Ambigious beans found of type" + my_class);
        }
    }    
}