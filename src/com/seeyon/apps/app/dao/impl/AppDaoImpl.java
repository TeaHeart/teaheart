package com.seeyon.apps.app.dao.impl;

import com.seeyon.apps.app.dao.AppDao;
import com.seeyon.apps.app.po.App;
import com.seeyon.ctp.util.DBAgent;
import com.seeyon.ctp.util.FlipInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppDaoImpl implements AppDao {
    @Override
    public void insert(App app) {
        DBAgent.save(app);
    }

    @Override
    public void delete(List<Long> ids) {
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        DBAgent.bulkUpdate("delete from App where id in (:ids)", map);
    }

    @Override
    public void select(FlipInfo flipInfo) {
        DBAgent.find("select new Map(a.id as id, a.key as key, a.value as value) from App a", null, flipInfo);
    }
}
