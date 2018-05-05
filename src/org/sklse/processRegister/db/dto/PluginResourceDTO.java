package org.sklse.processRegister.db.dto;

/**
 * Created by 袁胜磊 on Intellij IDEA
 *
 * @date 2015/3/31
 * @email 745861642@qq.com
 * @decription 不需要保存的字段  param;
 */
public class PluginResourceDTO extends ResourceDTO {
    private long processId;

    /**
     * resource 类型 参见{@link  org.sklse.processRegister.db.enums.ResourceTypeEnum}
     */
    private int resourceType;
    /**
     * 扩展字段
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
