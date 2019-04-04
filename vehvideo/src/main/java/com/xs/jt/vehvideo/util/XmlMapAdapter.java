package com.xs.jt.vehvideo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.xs.jt.vehvideo.util.MyStringMap.Entry;

public class XmlMapAdapter extends XmlAdapter<MyStringMap, Map<String, String>>{
    @Override
    //Map(不可处理)转换为可处理的类(自定义的POJO类，就是模拟Map的一个类)
    public MyStringMap marshal(Map<String, String> v) throws Exception {
            MyStringMap result = new MyStringMap();
            List<MyStringMap.Entry> entries = new ArrayList<MyStringMap.Entry>();
            for(java.util.Map.Entry<String, String> e : v.entrySet()){
                    MyStringMap.Entry entry = new MyStringMap.Entry();
                    entry.setKey(e.getKey());
                    entry.setValue(e.getValue());
                    entries.add(entry);
            }
            result.setEntries(entries);
            return result;
            
    }
    //自定义可处理类转换为Map(不可处理的类型)
    @Override
    public Map<String, String> unmarshal(MyStringMap v) throws Exception {
            Map<String, String> result = new HashMap<String, String>();
            for(MyStringMap.Entry e : v.getEntries()){
                    result.put(e.getKey(), e.getValue());
            }
            return result;
    }
}
