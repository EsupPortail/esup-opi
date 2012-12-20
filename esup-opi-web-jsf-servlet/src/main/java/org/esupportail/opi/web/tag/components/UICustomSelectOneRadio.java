package org.esupportail.opi.web.tag.components;


import org.apache.myfaces.shared.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author cleprous
 */
public class UICustomSelectOneRadio extends UIInput {


    /**
     * Family group component.
     */
    protected static final String COMPONENT_FAMILY = "CustomSelectOneRadio";

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String overrideName;
    /**
     *
     */
    private String styleClass;
    /**
     *
     */
    private String style;

    /**
     *
     */
    private Boolean disabled;

    /**
     *
     */
    private String itemLabel;

    /**
     *
     */
    private String itemValue;

    /**
     *
     */
    private String onClick;

    /**
     *
     */
    private String onMouseOver;

    /**
     *
     */
    private String onMouseOut;

    /**
     *
     */
    private String onFocus;

    /**
     *
     */
    private String onBlur;


    /**
     * Constructors.
     */
    public UICustomSelectOneRadio() {
        super();
    }


    /**
     * @param attr
     * @return String
     */
    public String returnValueBindingAsString(final String attr) {
        ValueBinding valueBinding = getValueBinding(attr);
        if (valueBinding != null) {
            return (String) valueBinding.getValue(this.getFacesContext());
        }

        return null;
    }


    /**
     * @return
     */
    public Boolean getDisabled() {
        if (disabled != null) {
            return disabled;
        }
        ValueBinding valueBinding = getValueBinding("disabled");
        if (valueBinding != null) {
            return (Boolean) valueBinding.getValue(this.getFacesContext());
        }
        return null;
    }

    /**
     * @return
     */
    public String getItemLabel() {
        if (null != itemLabel) {
            return itemLabel;
        }
        return returnValueBindingAsString(JSFAttr.ITEM_LABEL_ATTR);
    }

    /**
     * @return
     */
    public String getItemValue() {
        if (null != itemValue) {
            return itemValue;
        }
        return returnValueBindingAsString(JSFAttr.ITEM_VALUE_ATTR);
    }

    /**
     * @return
     */
    public String getName() {
        if (null != name) {
            return name;
        }
        return returnValueBindingAsString(HTML.NAME_ATTR);
    }

    /**
     * @return
     */
    public String getOnBlur() {
        if (null != onBlur) {
            return onBlur;
        }
        return returnValueBindingAsString(HTML.ONBLUR_ATTR);
    }

    /**
     * @return
     */
    public String getOnClick() {
        if (null != onClick) {
            return onClick;
        }
        return returnValueBindingAsString(HTML.ONCLICK_ATTR);
    }

    /**
     * @return
     */
    public String getOnFocus() {
        if (null != onFocus) {
            return onFocus;
        }
        return returnValueBindingAsString(HTML.ONFOCUS_ATTR);
    }

    /**
     * @return
     */
    public String getOnMouseOut() {
        if (null != onMouseOut) {
            return onMouseOut;
        }
        return returnValueBindingAsString(HTML.ONMOUSEOUT_ATTR);
    }

    /**
     * @return
     */
    public String getOnMouseOver() {
        if (null != onMouseOver) {
            return onMouseOver;
        }
        return returnValueBindingAsString(HTML.ONMOUSEOVER_ATTR);
    }

    /**
     * @return
     */
    public String getOverrideName() {
        if (null != overrideName) {
            return overrideName;
        }
        return returnValueBindingAsString("overrideName");
    }

    /**
     * @return
     */
    public String getStyle() {
        if (null != style) {
            return style;
        }
        return returnValueBindingAsString(JSFAttr.STYLE_ATTR);
    }

    /**
     * @return
     */
    public String getStyleClass() {
        if (null != styleClass) {
            return styleClass;
        }
        return returnValueBindingAsString(JSFAttr.STYLE_CLASS_ATTR);
    }

    /**
     * @param string
     */
    public void setDisabled(final Boolean string) {
        disabled = string;
    }

    /**
     * @param string
     */
    public void setItemLabel(final String string) {
        itemLabel = string;
    }

    /**
     * @param string
     */
    public void setItemValue(final String string) {
        itemValue = string;
    }

    /**
     * @param string
     */
    public void setName(final String string) {
        name = string;
    }

    /**
     * @param string
     */
    public void setOnBlur(final String string) {
        onBlur = string;
    }

    /**
     * @param string
     */
    public void setOnClick(final String string) {
        onClick = string;
    }

    /**
     * @param string
     */
    public void setOnFocus(final String string) {
        onFocus = string;
    }

    /**
     * @param string
     */
    public void setOnMouseOut(final String string) {
        onMouseOut = string;
    }

    /**
     * @param string
     */
    public void setOnMouseOver(final String string) {
        onMouseOver = string;
    }

    /**
     * @param string
     */
    public void setOverrideName(final String string) {
        overrideName = string;
    }

    /**
     * @param string
     */
    public void setStyle(final String string) {
        style = string;
    }

    /**
     * @param string
     */
    public void setStyleClass(final String string) {
        styleClass = string;
    }

    /**
     * @see javax.faces.component.UIInput#saveState(javax.faces.context.FacesContext)
     */
    @Override
    public Object saveState(final FacesContext context) {
        Object[] values = new Object[13];
        values[0] = super.saveState(context);
        values[1] = styleClass;
        values[2] = style;
        values[3] = disabled;
        values[4] = itemLabel;
        values[5] = itemValue;
        values[6] = onClick;
        values[7] = onMouseOver;
        values[8] = onMouseOut;
        values[9] = onFocus;
        values[10] = onBlur;
        values[11] = name;
        values[12] = overrideName;

        return values;
    }

    /**
     * @see javax.faces.component.UIInput#restoreState(javax.faces.context.FacesContext, java.lang.Object)
     */
    @Override
    public void restoreState(final FacesContext context, final Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        styleClass = (String) values[1];
        style = (String) values[2];
        disabled = (Boolean) values[3];
        itemLabel = (String) values[4];
        itemValue = (String) values[5];
        onClick = (String) values[6];
        onMouseOver = (String) values[7];
        onMouseOut = (String) values[8];
        onFocus = (String) values[9];
        onBlur = (String) values[10];
        name = (String) values[11];
        overrideName = (String) values[12];
    }

    /**
     * @see javax.faces.component.UIInput#getFamily()
     */
    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }
}
