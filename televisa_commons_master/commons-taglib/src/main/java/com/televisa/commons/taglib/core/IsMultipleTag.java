package com.televisa.commons.taglib.core;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: palecio
 * Date: 19/3/13
 * Time: 16:36
 */
@JspTag
public class IsMultipleTag extends CqSimpleTagSupport {

    private Object property;

    @Override
    public void doTag() throws JspException, IOException {


        final Object property = getProperty();
        if ("".equals(property)) {
            throw new JspException("property is undefined.");
        }

        String isMultiple = "false";
        if(property != null){
            if(!property.getClass().equals(String.class)){
                isMultiple = "true";
            }
        }
        getJspWriter().write(isMultiple);
    }



    /**
     * The variable to set the property to.
     * @param property the variable name to set the property.
     */
    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setProperty(final Object property) {
        this.property = property;
    }
    public Object getProperty() {
        return property;
    }

}