package net.prasyb.miraimcchat.data;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Map;


public class PluginData {
    private BiMap<Long, String> userMap = HashBiMap.create();
    private BiMap<String, String> clientMap = HashBiMap.create();
    //占位字段
    @JSONField(serialize=false)
    private BiMap<Long, String> userBiMap;
    @JSONField(serialize=false)
    private BiMap<String, String> clientBiMap;

    public BiMap<Long, String> getUserBiMap() {
        return userMap;
    }

    public Map<Long, String> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<Long, String> userMap) {
        this.userMap = HashBiMap.create(userMap);
    }

    public void setUserBiMap(BiMap<Long, String> userMap) {
        this.userMap = userMap;
    }

    public BiMap<String, String> getClientBiMap() {
        return clientMap;
    }

    public Map<String, String> getClientMap() { return clientMap; }

    public void setClientBiMap(BiMap<String, String> clientMap) {
        this.clientMap = clientMap;
    }

    public void setClientMap(Map<String, String> clientMap) { this.clientMap = HashBiMap.create(clientMap); }
}
