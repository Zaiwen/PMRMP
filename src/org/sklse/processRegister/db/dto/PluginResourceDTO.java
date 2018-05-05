package org.sklse.processRegister.db.dto;

/**
 * Created by Ԭʤ�� on Intellij IDEA
 *
 * @date 2015/3/31
 * @email 745861642@qq.com
 * @decription ����Ҫ������ֶ�  param;
 */
public class PluginResourceDTO extends ResourceDTO {
    private long processId;

    /**
     * resource ���� �μ�{@link  org.sklse.processRegister.db.enums.ResourceTypeEnum}
     */
    private int resourceType;
    /**
     * ��չ�ֶ�
     */
    private String pluginParam;

    public long getProcessId() {
        return processId;
    }

    public void setProcessId(long processId) {
        this.processId = processId;
    }


    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public String getPluginParam() {
        return pluginParam;
    }

    public void setPluginParam(String pluginParam) {
        this.pluginParam = pluginParam;
    }
}
